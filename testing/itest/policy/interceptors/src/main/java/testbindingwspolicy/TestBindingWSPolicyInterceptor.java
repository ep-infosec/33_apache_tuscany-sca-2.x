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
package testbindingwspolicy;

import helloworld.StatusImpl;

import java.util.List;

import org.apache.tuscany.sca.assembly.Component;
import org.apache.tuscany.sca.assembly.Endpoint;
import org.apache.tuscany.sca.assembly.EndpointReference;
import org.apache.tuscany.sca.core.invocation.InterceptorAsyncImpl;
import org.apache.tuscany.sca.interfacedef.Operation;
import org.apache.tuscany.sca.invocation.Message;
import org.apache.tuscany.sca.invocation.PhasedInterceptor;
import org.apache.tuscany.sca.policy.PolicySubject;


public class TestBindingWSPolicyInterceptor extends InterceptorAsyncImpl implements PhasedInterceptor {

    private Operation operation;
    private List<TestBindingWSPolicy> policies;
    private PolicySubject subject;
    private String context;
    private String phase;

    public TestBindingWSPolicyInterceptor(PolicySubject subject,
                                       String context,
                                       Operation operation,
                                       List<TestBindingWSPolicy> policies,
                                       String phase) {
        super();
        this.operation = operation;
        this.policies = policies;
        this.subject = subject;
        this.phase = phase;
        this.context = getContext();
    }

    private String getContext() {
        if (subject instanceof Endpoint) {
            Endpoint endpoint = (Endpoint)subject;
            return endpoint.getURI();
        } else if (subject instanceof EndpointReference) {
            EndpointReference endpointReference = (EndpointReference)subject;
            return endpointReference.getURI();
        } else if (subject instanceof Component) {
            Component component = (Component)subject;
            return component.getURI();
        }
        return null;
    }

    public Message processRequest(Message msg) {
        StatusImpl.appendStatus("TestBindingWSPolicyInterceptor.processRequest()", context + " @ " + phase);
        return msg;
    }
    
    public Message processResponse(Message msg) {
        StatusImpl.appendStatus("TestBindingWSPolicyInterceptor.processResponse()", context + " @ " + phase);
        return msg;
    }

    public String getPhase() {
        return phase;
    }

}
