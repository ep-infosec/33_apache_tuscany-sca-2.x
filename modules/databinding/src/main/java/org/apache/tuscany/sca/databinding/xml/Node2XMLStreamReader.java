/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
package org.apache.tuscany.sca.databinding.xml;

import javax.xml.stream.XMLStreamReader;

import org.apache.tuscany.sca.common.xml.stax.reader.DOMXMLStreamReader;
import org.apache.tuscany.sca.databinding.BaseTransformer;
import org.apache.tuscany.sca.databinding.PullTransformer;
import org.apache.tuscany.sca.databinding.TransformationContext;
import org.apache.tuscany.sca.databinding.TransformationException;
import org.w3c.dom.Node;

/**
 * Transform DOM Node to XML XMLStreamReader
 *
 * @version $Rev$ $Date$
 */
public class Node2XMLStreamReader extends BaseTransformer<Node, XMLStreamReader> implements
    PullTransformer<Node, XMLStreamReader> {

    public XMLStreamReader transform(Node source, TransformationContext context) {
        if (source == null) {
            return null;
        }
        try {
            DOMXMLStreamReader reader = new DOMXMLStreamReader(source);
            return reader;
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }

    @Override
    protected Class<Node> getSourceType() {
        return Node.class;
    }

    @Override
    protected Class<XMLStreamReader> getTargetType() {
        return XMLStreamReader.class;
    }

    @Override
    public int getWeight() {
        return 40;
    }

}
