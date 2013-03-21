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
package org.apache.sis.metadata.iso.quality;

import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlRootElement;
import org.opengis.metadata.quality.TopologicalConsistency;


/**
 * Correctness of the explicitly encoded topological characteristics of the dataset as
 * described by the scope.
 *
 * @author  Martin Desruisseaux (IRD, Geomatys)
 * @author  Touraïvane (IRD)
 * @since   0.3 (derived from geotk-2.1)
 * @version 0.3
 * @module
 */
@XmlType(name = "DQ_TopologicalConsistency_Type")
@XmlRootElement(name = "DQ_TopologicalConsistency")
public class DefaultTopologicalConsistency extends AbstractLogicalConsistency
        implements TopologicalConsistency
{
    /**
     * Serial number for inter-operability with different versions.
     */
    private static final long serialVersionUID = -255014076679068944L;

    /**
     * Constructs an initially empty topological consistency.
     */
    public DefaultTopologicalConsistency() {
    }

    /**
     * Returns a SIS metadata implementation with the same values than the given arbitrary
     * implementation. If the given object is {@code null}, then this method returns {@code null}.
     * Otherwise if the given object is already a SIS implementation, then the given object is
     * returned unchanged. Otherwise a new SIS implementation is created and initialized to the
     * property values of the given object, using a <cite>shallow</cite> copy operation
     * (i.e. properties are not cloned).
     *
     * @param  object The object to get as a SIS implementation, or {@code null} if none.
     * @return A SIS implementation containing the values of the given object (may be the
     *         given object itself), or {@code null} if the argument was null.
     */
    public static DefaultTopologicalConsistency castOrCopy(final TopologicalConsistency object) {
        if (object == null || object instanceof DefaultTopologicalConsistency) {
            return (DefaultTopologicalConsistency) object;
        }
        final DefaultTopologicalConsistency copy = new DefaultTopologicalConsistency();
        copy.shallowCopy(object);
        return copy;
    }
}
