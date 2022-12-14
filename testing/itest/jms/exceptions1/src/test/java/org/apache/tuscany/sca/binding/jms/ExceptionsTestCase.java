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
package org.apache.tuscany.sca.binding.jms;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * This shows how to test the JMS binding using a simple HelloWorld application.
 */
public class ExceptionsTestCase {

    private static Node node;

    @Before
    public void init() {
        node = NodeFactory.newInstance().createNode().start();
    }

    @Test
    public void testChecked() {
        ExceptionService service = node.getService(ExceptionService.class, "ExceptionServiceClient");
        try {
            service.throwChecked();
            fail();
        } catch (CheckedExcpetion e) {
            assertEquals("foo", e.getMessage());
        } catch (Throwable e) {
            fail();
        }
    }

    @Test
    public void testCheckedNoArgs() {
        ExceptionService service = node.getService(ExceptionService.class, "ExceptionServiceClient");
        try {
            service.throwCheckedNoArgs();
            fail();
        } catch (CheckedExcpetionNoArgs e) {
            // ok
        }
    }

    @Test
    public void testChecked2Args() {
        ExceptionService service = node.getService(ExceptionService.class, "ExceptionServiceClient");
        try {
            service.throwChecked2Args();
            fail();
        } catch (CheckedExcpetion2Args e) {
            assertEquals("foo", e.getMessage());
// FIXME: TUSCANY-2848: lost the cause!            
//            assertNotNull(e.getCause());
//            assertEquals("bla", e.getCause().getMessage());
        }
    }

    @Test
    public void testCheckedChained() {
        ExceptionService service = node.getService(ExceptionService.class, "ExceptionServiceClient");
        try {
            service.throwCheckedChained();
            fail();
        } catch (CheckedExcpetionChained e) {
         // FIXME: TUSCANY-2848: lost the cause!            
//            assertNotNull(e.getCause());
//            assertEquals("bla", e.getCause().getMessage());
        }
    }

    @Test
    public void testUnChecked() {
        ExceptionService service = node.getService(ExceptionService.class, "ExceptionServiceClient");
        try {
            service.throwUnChecked();
            fail();
        } catch (Exception e) {
            assertTrue(e.getCause().getMessage().startsWith("Message = java.lang.RuntimeException: bla"));
        }
    }

    @After
    public void end() {
        if (node != null) {
            node.stop();
        }
    }
}
