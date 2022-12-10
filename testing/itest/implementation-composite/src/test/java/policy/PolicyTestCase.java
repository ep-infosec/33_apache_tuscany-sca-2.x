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
package policy;

import java.io.File;

import junit.framework.Assert;

import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.impl.CompositeImpl;
import org.apache.tuscany.sca.node.Node;
import org.apache.tuscany.sca.node.impl.NodeImpl;
import org.apache.tuscany.sca.node.NodeFactory;
import org.apache.tuscany.sca.node.Contribution;
import org.apache.tuscany.sca.policy.PolicySet;
import org.apache.tuscany.sca.policy.PolicySubject;
import org.apache.tuscany.sca.runtime.RuntimeComponent;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class PolicyTestCase{

    private Node node;
    private Target targetClient;

    @Before
    public void setUp() throws Exception {
        try {
        	NodeFactory factory = NodeFactory.newInstance();
            node = factory.createNode(new File("src/main/resources/policy/PolicyOuterComposite.composite").toURI().toURL().toString(),
                    new Contribution("TestContribution", new File("src/main/resources/policy/").toURI().toURL().toString()));
            node.start();
            targetClient = node.getService(Target.class, "TargetClientComponent");
        } catch(Exception ex) {
            System.out.println(ex.toString());
        }
    }

    @After
    public void tearDown() throws Exception {
        node.stop();
    }

    @Test
    public void test() throws Exception {
    	//Check that the binding policy sets do flow down to the component but not down to the
    	//component inside implementation.composite
    	Component outerComponent = ((NodeImpl)node).getDomainComposite().getComponent("OuterTargetService1Component");
    	
    	// The outer component service bindings should have policy sets attached
    	Assert.assertEquals(1, outerComponent.getServices().get(0).getPolicySets().size());
    	
    	Component component = ((CompositeImpl)outerComponent.getImplementation()).getComponents().get(0);
    	
    	// The original inner component service binding should not have policy sets attached
    	Assert.assertEquals(0, ((PolicySubject)component.getServices().get(0).getBindings().get(0)).getPolicySets().size());
        
        // The promoted inner component service binding should have policy sets attached
    	Assert.assertEquals(1, outerComponent.getServices().get(0).getEndpoints().get(0).getPolicySets().size());
    	
    	// TUSCANY-3988
        // The inner component should have a single set of policy providers
        Assert.assertEquals(3, ((RuntimeComponent)component).getPolicyProviders().size());
    	
        String result = targetClient.hello("Fred");

        System.out.println(result);
    }
}
