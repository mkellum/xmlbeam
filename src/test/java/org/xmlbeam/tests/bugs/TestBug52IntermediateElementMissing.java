/**
 *  Copyright 2018 Sven Ewald
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

import static org.junit.Assert.*;

import org.junit.Test;
import org.xmlbeam.XBProjector;
import org.xmlbeam.XBProjector.Flags;
import org.xmlbeam.annotation.XBWrite;
import org.xmlbeam.testutils.DOMDiagnoseHelper;

/**
 * @author Sven
 *
 */
public class TestBug52IntermediateElementMissing {
    public interface Child {
        @XBWrite("ChildData")
        void setData(String data);
    }
    
    public interface ParentWithRootElement {
        @XBWrite("Root/ParentData")
        void setData(String data);
        
        @XBWrite("Root/Child")
        void setChild(Child child);
    }

    @Test
    public void testSetChildProjectionEmptyParent() {
        XBProjector projector = new XBProjector(Flags.TO_STRING_RENDERS_XML);
        
        ParentWithRootElement parent = projector.projectEmptyDocument(ParentWithRootElement.class);
        
        //Child child = projector.projectEmptyDocument(Child.class);
        Child child = projector.projectEmptyElement("Child",Child.class);
        child.setData("child data...");     
        
        parent.setChild(child);  
        
        System.out.println("Result: " + parent);
        
        // Doesn't generate <Child> intermediate level
        DOMDiagnoseHelper.assertXMLStringsEquals( 
                  "<Root>\n"
                + "  <Child>\n"
                + "    <ChildData>child data...</ChildData>\n"
                + "  </Child>\n"
                + "</Root>".replaceAll(" ", ""),
                parent.toString());
    }
}
