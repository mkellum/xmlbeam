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
package org.xmlbeam.io;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmlbeam.XBProjector;
import org.xmlbeam.evaluation.CanEvaluate;
import org.xmlbeam.evaluation.DefaultXPathEvaluator;
import org.xmlbeam.evaluation.DocumentResolver;
import org.xmlbeam.evaluation.XPathEvaluator;
import org.xmlbeam.exceptions.XBDocumentParsingException;
import org.xmlbeam.exceptions.XBException;
import org.xmlbeam.exceptions.XBIOException;
import org.xmlbeam.util.intern.DocScope;
import org.xmlbeam.util.intern.Scope;

/**
 * @author <a href="https://github.com/SvenEwald">Sven Ewald</a>
 */
public class StreamInput implements CanEvaluate {

    private final XBProjector projector;
    private final InputStream is;
    private String systemID;

    /**
     * @param xmlProjector
     * @param is
     */
    public StreamInput(final XBProjector xmlProjector, final InputStream is) {
        this.projector = xmlProjector;
        this.is = is;
    }

    /**
     * Create a new projection by parsing the data provided by the input stream.
     *
     * @param projectionInterface
     *            A Java interface to project the data on.
     * @return a new projection instance pointing to the stream content.
     * @throws IOException
     */
    @Scope(DocScope.IO)
    public <T> T read(final Class<T> projectionInterface) throws IOException {
        Document document = readDocument();
        return projector.projectDOMNode(document, projectionInterface);
    }

    private Document readDocument() throws IOException {
        try {
            DocumentBuilder documentBuilder = projector.config().createDocumentBuilder();
            Document document = systemID == null ? documentBuilder.parse(is) : documentBuilder.parse(is, systemID);
            return document;
        } catch (SAXException e) {
            throw new XBDocumentParsingException(e);
        }
    }

    /**
     * As the system id usually cannot be determined by looking at the stream, this method allows it
     * to be set.
     *
     * @param systemID
     * @return this for convenience.
     */
    @Scope(DocScope.IO)
    public StreamInput setSystemID(final String systemID) {
        this.systemID = systemID;
        return this;
    }

    @Override
    public XPathEvaluator evalXPath(final String xpath) {
        return new DefaultXPathEvaluator(projector, new DocumentResolver() {
            @Override
            public Document resolve(final Class<?>... resourceAwareClass) {
                try {
                    return StreamInput.this.readDocument();
                } catch (IOException e) {
                    throw new XBIOException(e);
                }
            }
        }, xpath);
    }

}
