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

package org.apache.tuscany.sca.test;

import java.net.URI;

import org.apache.tuscany.sca.TuscanyRuntime;
import org.apache.tuscany.sca.http.jetty.JettyServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.oasisopen.sca.NoSuchServiceException;

public class CallBackTwoNodesHzBWSTestCase {

    private org.apache.tuscany.sca.Node serviceNode;
    private org.apache.tuscany.sca.Node clientNode;

    @Before
    public void setUp() throws Exception {
        
        JettyServer.portDefault = 8089;
        serviceNode = TuscanyRuntime.runComposite(URI.create("uri:CallBackTwoNodesHzTestCase"), "BWSCallBackService.composite", "target/classes");
        
        JettyServer.portDefault = 8088;
        clientNode = TuscanyRuntime.runComposite(URI.create("uri:CallBackTwoNodesHzTestCase"), "BWSCallBackReference.composite", "target/classes");
    }
    
    @Test
    public void testCallBackTwoNodes() throws NoSuchServiceException {
        CallBackBasicClient aCallBackClient = clientNode.getService(CallBackBasicClient.class, "CallBackBasicClient");
        aCallBackClient.run();
    }    

    @After
    public void tearDown() throws Exception {
        if (clientNode != null) clientNode.stop();
        if (serviceNode != null) serviceNode.stop();
    }

}
