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

import org.oasisopen.sca.annotation.Reference;
import org.oasisopen.sca.annotation.Service;

@Service(ClientComponent.class)
public class ClientComponentImpl implements ClientComponent, CallbackInterface {

    @Reference
    protected ServiceComponent aCallBackService;
    private static String callbackValue;
    private static String onewayValue;
    
    public String foo(ParameterObject po) {
        po.field1 = "AComponent";
        return aCallBackService.foo("AComponent");
    }    

    public String foo1(ParameterObject po) {
        po.field1 = "AComponent";
        return aCallBackService.foo1("AComponent");
    }

    public String foo2(String str) throws Exception {
        return str + "AComponent";
    }

    public String foo3(String str, int i) {
        return str + "AComponent" + i;
    }

    public String foo4(int i, String str) throws Exception {
        return str + "AComponent" + i;
    }

    public void callback(String str) {
        aCallBackService.callback(str);
    }

    public void callbackMethod(String str) {
        callbackValue = str;
    }
    
    public void callbackMethod1(String str) {
        callbackValue = str;
    }    

    public void callModifyParameter() {
        this.aCallBackService.modifyParameter();
    }

    public String getCallbackValue() {
        return callbackValue;
    }

    public void onewayMethod(String str) {
        onewayValue = str;
        try {
            Thread.sleep(200);
        } catch (Exception e) {
            //do nothing
        }
    }

    public String getOnewayValue() {
        return onewayValue;
    }

    public void modifyParameter(ParameterObject po) {
        po.field1 = "AComponent";
    }

}
