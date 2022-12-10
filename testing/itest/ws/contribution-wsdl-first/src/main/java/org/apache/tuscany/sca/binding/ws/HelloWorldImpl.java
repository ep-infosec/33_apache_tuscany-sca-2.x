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

package org.apache.tuscany.sca.binding.ws;

import org.apache.tuscany.sca.binding.ws.jaxws.external.service.iface.Foo;
import org.apache.tuscany.sca.binding.ws.jaxws.external.service.iface.HelloWorldService;
import org.oasisopen.sca.ServiceRuntimeException;
import org.oasisopen.sca.annotation.Reference;

public class HelloWorldImpl implements HelloWorld {

    @Reference
    public HelloWorldService helloWorldExternal;
    
    public String getGreetings(String s) {
        System.out.println("Entering SCA HelloWorld.getGreetings: " + s);
        String response = helloWorldExternal.getGreetings(s);
        System.out.println("Leaving SCA HelloWorld.getGreetings: " + response);
        return response;
    }
    
    public String getGreetingsException(String s) throws ServiceRuntimeException {
        System.out.println("Entering SCA HelloWorld.getGreetingsException: " + s);
        String response = helloWorldExternal.getGreetings(s);
        System.out.println("Leaving SCA HelloWorld.getGreetings: " + response);
        throw new ServiceRuntimeException(response);
    }  

    public Foo getGreetingsComplex(Foo foo){
        System.out.println("Entering SCA HelloWorld.getGreetingsComplex: " + foo.getBars().get(0).getS());
        Foo response = helloWorldExternal.getGreetingsComplex(foo);
        System.out.println("Leaving SCA HelloWorld.getGreetingsComplex: " + foo.getBars().get(0).getS());
        return response;
    } 
    
    public String getGreetingsCaps(String s) {
        System.out.println("Entering SCA HelloWorld.getGreetingsCaps: " + s);
        String response = helloWorldExternal.getGreetingsCaps(s);
        System.out.println("Leaving SCA HelloWorld.getGreetingsCaps: " + response);
        return response;
    }
   
}
