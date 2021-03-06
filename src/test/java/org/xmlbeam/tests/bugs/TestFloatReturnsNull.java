/**
 *  Copyright 2014 Sven Ewald
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
package org.xmlbeam.tests.bugs;

import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.xmlbeam.XBProjector;
import org.xmlbeam.annotation.XBRead;
import org.xmlbeam.types.DefaultTypeConverter;
import org.xmlbeam.types.DefaultTypeConverter.Conversion;

/**
 *
 */
@SuppressWarnings({ "javadoc", "serial" })
public class TestFloatReturnsNull {

    public interface Issue19 {

        @XBRead("/foo/@nonexisting")
        Float getFloat();

    }

    @Test
    public void testIssue19() {
        final XBProjector projector = new XBProjector();
        Conversion<Float> floatConversion = new Conversion<Float>(null) {
            @Override
            public Float convert(final String data) {
                return Float.valueOf(data);
            }
        };
        projector.config().getTypeConverterAs(DefaultTypeConverter.class).setConversionForType(Float.class, floatConversion);
        final Issue19 issue19 = projector.projectXMLString("<foo/>", Issue19.class);
        assertNull(issue19.getFloat());
    }
}
