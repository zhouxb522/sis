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

/**
 * Takes the source HTML files in the {@code http://svn.apache.org/repos/asf/sis/site/trunk/book/} directory
 * and assembles them in a single file to be published in the
 * {@code http://svn.apache.org/repos/asf/sis/site/trunk/content/book/} directory.
 *
 * <p>The main class in this package is {@link org.apache.sis.internal.book.Assembler}.
 * Other classes are helper classes that should be ignored. Assuming the following directory layout:</p>
 *
 * <pre>&lt;current directory&gt;
 * ├─ trunk
 * └─ site
 *     ├─ book
 *     └─ content
 * </pre>
 *
 * Then the command can be used as below on Unix systems:
 *
 * <pre>cd site
 * java -classpath ../trunk/core/sis-build-helper/target/classes org.apache.sis.internal.book.Assembler en
 * </pre>
 *
 * Replace {@code en} by {@code fr} for generating the French version.
 *
 * @author  Martin Desruisseaux (Geomatys)
 * @version 0.8
 * @since   0.7
 */
package org.apache.sis.internal.book;
