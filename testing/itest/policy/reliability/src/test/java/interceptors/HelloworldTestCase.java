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

package interceptors;

import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import helloworld.HelloWorld;
import helloworld.HelloWorldException;
import helloworld.StatusImpl;

import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.Composite;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.apache.tuscany.sca.node.impl.NodeImpl;
import org.apache.tuscany.sca.policy.Intent;
import org.apache.tuscany.sca.policy.PolicySet;
import org.junit.Ignore;

public class HelloworldTestCase extends TestCase {

    private Node node;
    private HelloWorld helloWorld;

    @Override
    protected void setUp() throws Exception {
        StatusImpl.statusString = "";
        
        node = NodeFactory.newInstance().createNode("helloworld.composite", new Contribution("test", "target/classes"));
        node.start();
        helloWorld = node.getService(HelloWorld.class, "HelloWorldClient/HelloWorld");
    }
    
    /*
     * We're not testing real reliability policy here. Just checking that the 
     * exactlyOnce profile intent is handled correctly and presented as its 
     * constituent atMostOnce and atLeastOnce intents. 
     */
    public void testReliabilityIntent() throws Exception {
        // check response from application
        assertEquals("Hello fred", helloWorld.getGreetings("fred"));
        
        // check sequences of interceptors
        System.out.println(StatusImpl.statusString);
        assertTrue(StatusImpl.statusString.contains("atmostonce"));
        assertTrue(StatusImpl.statusString.contains("exactlyonce"));
        assertTrue(StatusImpl.statusString.contains("atleastonce"));

                
        // check final intents on endpoint reference to see if the matching process
        // results on the right set
        Composite domainComposite = ((NodeImpl)node).getDomainComposite();
        List<Intent> intents = domainComposite.getComponents().get(0).getReferences().get(0).getEndpointReferences().get(0).getRequiredIntents();
        
        assertEquals(2, intents.size());
        assertEquals("atMostOnce", intents.get(0).getName().getLocalPart());
        assertEquals("atLeastOnce", intents.get(1).getName().getLocalPart());
        
    }    
    
    @Override
    protected void tearDown() throws Exception {
        node.stop();
    }

}
