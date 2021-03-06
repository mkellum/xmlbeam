/**
 *  Copyright 2015 Sven Ewald
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.xmlbeam.evaluation;

import java.util.Date;

import org.xmlbeam.types.CloseableList;
import org.xmlbeam.types.CloseableMap;
import org.xmlbeam.types.CloseableValue;
import org.xmlbeam.util.intern.DocScope;
import org.xmlbeam.util.intern.Scope;

/**
 * Interface to build fluent API for the evaluation API.
 */
public interface XPathBinder {

    /**
     * Evaluates the XPath as a boolean value. This method is just a shortcut for as(Boolean.TYPE);
     *
     * @return true when the selected value equals (ignoring case) 'true'
     */
    @Scope(DocScope.IO)
    CloseableValue<Boolean> asBoolean();

    /**
     * Evaluates the XPath as a int value. This method is just a shortcut for as(Integer.TYPE);
     *
     * @return int value of evaluation result.
     */
    @Scope(DocScope.IO)
    CloseableValue<Integer> asInt();

    /**
     * Evaluates the XPath as a String value. This method is just a shortcut for as(String.class);
     *
     * @return String value of evaluation result.
     */
    @Scope(DocScope.IO)
    CloseableValue<String> asString();

    /**
     * Evaluates the XPath as a Date value. This method is just a shortcut for as(Date.class); You
     * probably want to specify ' using ' followed by some formatting pattern consecutive to the
     * XPAth.
     *
     * @return Date value of evaluation result.
     */
    @Scope(DocScope.IO)
    CloseableValue<Date> asDate();

    /**
     * Evaluate the XPath as a value of the given type.
     *
     * @param returnType
     *            Possible values: primitive types (e.g. Short.Type), Projection interfaces, any
     *            class with a String constructor or a String factory method, and org.w3c.Node
     * @return a value of return type that reflects the evaluation result.
     */
    @Scope(DocScope.IO)
    <T> CloseableValue<T> as(Class<T> returnType);

    /**
     * Evaluate the XPath as a list of the given type.
     *
     * @param componentType
     *            Possible values: primitive types (e.g. Short.Type), Projection interfaces, any
     *            class with a String constructor or a String factory method, and org.w3c.Node
     * @return List of return type that reflects the evaluation result.
     */
    @Scope(DocScope.IO)
    <T> CloseableList<T> asListOf(Class<T> componentType);

    /**
     * Evaluate the XPath as a map with the given value type.
     *
     * @param valueType
     *            Possible values: primitive types (e.g. Short.Type), Projection interfaces, any
     *            class with a String constructor or a String factory method, and org.w3c.Node
     * @return List of return type that reflects the evaluation result.
     */
    @Scope(DocScope.IO)
    <T> CloseableMap<T> asMapOf(Class<T> valueType);

}