/**
 *  Copyright 2013 Sven Ewald
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
package org.xmlbeam.tutorial.e08_api_mimicry;

import java.io.IOException;

import org.junit.Test;
import org.xmlbeam.dom.Projection;
import org.xmlbeam.XBProjector;
import org.xmlbeam.tutorial.TutorialTestCase;

public class TestDom4jMimicry extends TutorialTestCase {

    @Test
    public void testAPIMimicry() throws IOException {
//START SNIPPET:TestDom4jMimicry
        XBProjector projector = new XBProjector();
        
        Document document =projector.io().fromURLAnnotation(Document.class);
        Element element = document.getRootElement();
        System.out.println(element.getName());
        Element element2 = element.element("eelement");
        System.out.println(element2.getText());
        Attribute attribute = element2.attribute("eattribute");
        System.out.println(attribute.getValue());
        
        org.w3c.dom.Element newRootNode = ((Projection)document).getOwnerDocument().createElement("newRoot");
        Element newRootElement = projector.projectDOMNode(newRootNode, Element.class);
        
        document.setRootElement(newRootElement);
        System.out.println(document);
//END SNIPPET:TestDom4jMimicry       
    }
}
