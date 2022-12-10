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

package org.apache.tuscany.sca.binding.ws.axis2;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMText;
import org.apache.tuscany.sca.binding.ws.WebServiceBinding;
import org.apache.tuscany.sca.binding.ws.axis2.HelloWorldOM;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.apache.tuscany.sca.node.impl.NodeImpl;

public abstract class AbstractHelloWorldOMTestCase extends TestCase {

    protected Node node;
    protected HelloWorldOM helloWorld;

    @Override
    protected void setUp() throws Exception {
        String contribution = "target/classes";
        node = NodeFactory.newInstance().createNode(getCompositeName(), new Contribution("test", contribution));
        // force ws binding on node to use a default of 8085 if an absolute port is not specified
        ((NodeImpl)node).getConfiguration().addBinding(WebServiceBinding.TYPE, "http://localhost:8085/");
        node.start();
        helloWorld = node.getService(HelloWorldOM.class, "HelloWorldComponent");
    }
    
    public void testCalculator() throws Exception {
        OMFactory fac = OMAbstractFactory.getOMFactory();
        OMElement requestOM = fac.createOMElement("getGreetings", "http://helloworld-om", "helloworld");
        OMElement parmE = fac.createOMElement("name", "http://helloworld-om", "helloworld");
        requestOM.addChild(parmE);
        parmE.addChild(fac.createOMText("petra"));
        OMElement responseOM = helloWorld.getGreetings(requestOM);
        OMElement child = (OMElement)responseOM.getFirstElement();
        Assert.assertEquals("Hello petra", ((OMText)child.getFirstOMChild()).getText());
    }    
    
    @Override
    protected void tearDown() throws Exception {
        node.stop();
    }
    
    protected String getCompositeName() {
        String className = this.getClass().getName();
        return className.substring(0, className.length() - 8).replace('.', '/') + ".composite";
    }

}
