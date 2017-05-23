/**
 *  Copyright 2016 Sven Ewald
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
package org.xmlbeam.refcards;

import java.util.List;

import org.junit.Test;
import org.xmlbeam.XBProjector;
import org.xmlbeam.XBProjector.Flags;
import org.xmlbeam.annotation.XBAutoBind;

@SuppressWarnings({ "javadoc", "null" })
public class XBAutoListRefCard {

    //START SNIPPET: ProjectedListRefCardExample
    public interface Example {

        @XBAutoBind("/xml/list/entry")
        List<String> entries();

    }
    //END SNIPPET: ProjectedListRefCardExample

    private static final String XML = "<xml>\n  <list>\n    <entry>foo</entry>\n    <entry>bar</entry>›\n    <entry>something</entry>\n  </list>\n</xml>\n";

    @Test
    public void autolistdemo() {
        Example example = new XBProjector(Flags.TO_STRING_RENDERS_XML).projectXMLString(XML, Example.class);
    //START SNIPPET: ProjectedListRefCardExample2
       // Remove the first two entries
       example.entries().subList(0, 2).clear();
       // Add a new entry
       example.entries().add("New Entry");
    //END SNIPPET: ProjectedListRefCardExample2
        System.out.println(example);
    }
}
