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
package org.apache.tuscany.sca.itest.interfaces;

import java.util.List;

import org.oasisopen.sca.annotation.Callback;
import org.oasisopen.sca.annotation.Service;

@Service(ServiceMissmatchComponent.class)
public class ServiceMissmatchComponentImpl implements ServiceMissmatchComponent {

    @Callback
    protected CallbackInterface callback;

    private static ParameterObject po;

    public String foo(ParameterObject po) {
        return po.field1;
    }
    
    public String foo1(ParameterObject po, String str){
        return str;
    }  
    
    public void callback(String str) {
        callback.callbackMethod(str);
    }

    public void modifyParameter() {
        po = new ParameterObject("CallBack");
        callback.modifyParameter(po);
    }

    public ParameterObject getPO() {
        return po;
    }
    
    public void inArray(String[] stringArray) {
    }  
    
    public String[] outArray() {
        return null;
    } 
    
    public void inCollection(List<String> stringArray) {
    }  
    
    public List<String> outCollection() {
        return null;
    }     
}
