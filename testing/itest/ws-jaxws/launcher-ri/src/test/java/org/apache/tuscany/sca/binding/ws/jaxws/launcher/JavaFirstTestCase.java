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

package org.apache.tuscany.sca.binding.ws.jaxws.launcher;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.apache.tuscany.sca.binding.ws.jaxws.external.client.HelloWorldClientLauncher;
import org.apache.tuscany.sca.binding.ws.jaxws.external.service.HelloWorldServiceLauncher;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JavaFirstTestCase {

    private Node node;
    private HelloWorldServiceLauncher externalService;
    private HelloWorldClientLauncher externalClient;

    @Before
    public void setUp() throws Exception {
        // Start the external service
        externalService = new HelloWorldServiceLauncher();
        externalService.createService();
       
        // Start the SCA contribution
        node = NodeFactory.newInstance().createNode(new Contribution("java-first", "../contribution-java-first/target/classes"));
        node.start();
        
        // start the external client
        try {
            externalClient = new HelloWorldClientLauncher();
            externalClient.createClient();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

/*    
    public void testWait() throws Exception {
        System.out.println("Press a key");
        System.in.read();
    }
*/
    
    @Test
    public void testGetGreetings() throws Exception {
        assertEquals("Hello Fred", externalClient.getGreetings("Fred"));
    }
    
    @Test
    public void testGetGreetingsException() throws Exception {
        try {
            externalClient.getGreetingsException("Fred");
        } catch (Exception ex) {
            return;
        }
        
        fail("expecting exception");
    }
    
    public void testGetGreetingsComplex() throws Exception {    
        
/*        
        Foo f = new Foo();
        Bar b1 = new Bar();
        b1.setS("petra");
        b1.setX(1);
        b1.setY(new Integer(2));
        b1.setB(Boolean.TRUE);
        Bar b2 = new Bar();
        b2.setS("beate");
        b2.setX(3);
        b2.setY(new Integer(4));
        b2.setB(Boolean.FALSE);
        f.setBars(new Bar[] { b1, b2} );
       
        Foo f2 = helloWorld.getGreetingsComplex(f);

        assertEquals("petra", f2.getBars()[0].getS());
        assertEquals(1, f2.getBars()[0].getX());
        assertEquals(2, f2.getBars()[0].getY().intValue());
        assertTrue(f2.getBars()[0].getB().booleanValue());
        assertEquals("simon", f2.getBars()[1].getS());
        assertEquals(4, f2.getBars()[1].getX());
        assertEquals(5, f2.getBars()[1].getY().intValue());
        assertTrue(f2.getBars()[1].getB().booleanValue());
*/
    }  
    
    @After
    public void tearDown() throws Exception {
        node.stop();
        externalClient.destroyClient();
        externalService.destoryService();
    }

}
