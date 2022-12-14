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

package binding.lifecycle;

import helloworld.StatusImpl;

import org.apache.tuscany.sca.assembly.impl.EndpointImpl;
import org.apache.tuscany.sca.interfacedef.InterfaceContract;
import org.apache.tuscany.sca.provider.ServiceBindingProvider;
import org.apache.tuscany.sca.runtime.RuntimeEndpoint;

public class LifecycleServiceBindingProvider implements ServiceBindingProvider {

    private RuntimeEndpoint endpoint;
    private InterfaceContract contract;

    public LifecycleServiceBindingProvider(RuntimeEndpoint endpoint) {
        this.endpoint = endpoint;
    }

    public void start() {
        StatusImpl.appendStatus("Service binding start", 
                                ((EndpointImpl)endpoint).toStringWithoutHash());
    }

    public void stop() {
        StatusImpl.appendStatus("Service binding stop", 
                                ((EndpointImpl)endpoint).toStringWithoutHash());
    }

    public InterfaceContract getBindingInterfaceContract() {
        return contract;
    }

    public boolean supportsOneWayInvocation() {
        return false;
    }

}
