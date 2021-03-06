/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.sis.internal.storage;

import java.util.Optional;
import org.opengis.util.GenericName;
import org.opengis.metadata.Metadata;
import org.opengis.geometry.Envelope;
import org.opengis.referencing.operation.TransformException;
import org.apache.sis.storage.Resource;
import org.apache.sis.storage.DataStoreException;
import org.apache.sis.storage.event.StoreEvent;
import org.apache.sis.storage.event.StoreListener;
import org.apache.sis.storage.event.StoreListeners;
import org.apache.sis.storage.event.WarningEvent;


/**
 * Base implementation of resources contained in data stores. This class provides a {@link #getMetadata()}
 * which extracts information from other methods. Subclasses shall or should override the following methods:
 *
 * <ul>
 *   <li>{@link #getIdentifier()} (mandatory)</li>
 *   <li>{@link #getEnvelope()} (recommended)</li>
 *   <li>{@link #createMetadata(MetadataBuilder)} (optional)</li>
 * </ul>
 *
 * This class extends {@link StoreListeners} for convenience reasons.
 * This implementation details may change in any future SIS version.
 *
 * <h2>Thread safety</h2>
 * Default methods of this abstract class are thread-safe.
 * Synchronization, when needed, uses {@code this} lock.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 1.0
 * @since   0.8
 * @module
 */
public class AbstractResource extends StoreListeners implements Resource {
    /**
     * A description of this resource as an unmodifiable metadata, or {@code null} if not yet computed.
     * If non-null, this metadata shall contain at least the resource {@linkplain #getIdentifier() identifier}.
     * Those metadata are created by {@link #getMetadata()} when first needed.
     */
    private Metadata metadata;

    /**
     * Creates a new resource.
     *
     * @param  parent  listeners of the parent resource, or {@code null} if none.
     */
    public AbstractResource(final StoreListeners parent) {
        super(parent, null);
    }

    /**
     * Returns the resource persistent identifier if available.
     * The default implementation returns an empty value.
     * Subclasses are strongly encouraged to override if they can provide a value.
     */
    @Override
    public Optional<GenericName> getIdentifier() throws DataStoreException {
        return Optional.empty();
    }

    /**
     * Returns the spatiotemporal envelope of this resource. This information is part of API only in some kinds of resource
     * like {@link org.apache.sis.storage.FeatureSet}. But the method is provided in this base class for convenience and for
     * allowing {@link #getMetadata()} to use this information if available. The default implementation gives an absent value.
     *
     * @return the spatiotemporal resource extent.
     * @throws DataStoreException if an error occurred while reading or computing the envelope.
     */
    public Optional<Envelope> getEnvelope() throws DataStoreException {
        return Optional.empty();
    }

    /**
     * Returns a description of this resource. This method invokes {@link #createMetadata(MetadataBuilder)}
     * the first time it is invoked, then cache the result.
     *
     * @return information about this resource (never {@code null} in this implementation).
     * @throws DataStoreException if an error occurred while reading or computing the envelope.
     */
    @Override
    public final synchronized Metadata getMetadata() throws DataStoreException {
        if (metadata == null) {
            final MetadataBuilder builder = new MetadataBuilder();
            createMetadata(builder);
            metadata = builder.build(true);
        }
        return metadata;
    }

    /**
     * Invoked the first time that {@link #getMetadata()} is invoked. The default implementation populates
     * metadata based on information provided by {@link #getIdentifier()} and {@link #getEnvelope()}.
     * Subclasses should override if they can provide more information.
     *
     * @param  metadata  the builder where to set metadata properties.
     * @throws DataStoreException if an error occurred while reading metadata from the data store.
     */
    protected void createMetadata(final MetadataBuilder metadata) throws DataStoreException {
        getIdentifier().ifPresent((name) -> metadata.addTitle(name.toInternationalString()));
        getEnvelope().ifPresent((envelope) -> {
            try {
                metadata.addExtent(envelope);
            } catch (TransformException | UnsupportedOperationException e) {
                warning(e);
            }
        });
    }

    /**
     * Clears any cache in this resource, forcing the data to be recomputed when needed again.
     * This method should be invoked if the data in underlying data store changed.
     */
    protected synchronized void clearCache() {
        metadata = null;
    }

    /**
     * Registers only listeners for {@link WarningEvent}s on the assumption that most resources
     * (at least the read-only ones) produce no change events.
     */
    @Override
    public <T extends StoreEvent> void addListener(Class<T> eventType, StoreListener<? super T> listener) {
        // If an argument is null, we let the parent class throws (indirectly) NullArgumentException.
        if (listener == null || eventType == null || eventType.isAssignableFrom(WarningEvent.class)) {
            super.addListener(eventType, listener);
        }
    }
}
