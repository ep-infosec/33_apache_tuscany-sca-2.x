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
package itest;

import static org.junit.Assert.assertEquals;

import java.net.URI;

import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.NodeFactory;
import org.junit.Test;
import org.oasisopen.sca.client.SCAClientFactory;

/**
 * This shows how to test the Calculator service component.
 */
public class StandaloneTestCase{

    @Test
    public void test1() throws Exception {
        Node node = NodeFactory.newInstance().createNode(URI.create("tuscany:foo"),"../helloworld/target/itest-domains-helloworld.zip");
        node.start();
        assertEquals(1, node.getServiceNames().size());
        assertEquals("HelloworldComponent/Helloworld", node.getServiceNames().get(0));
        Helloworld helloworld = SCAClientFactory.newInstance(URI.create("tuscany:foo")).getService(Helloworld.class, "HelloworldComponent");
        assertEquals("Hello petra", helloworld.sayHello("petra"));
    }

}

