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
import org.oasisopen.sca.annotation.Remotable;

/**
 * Local be-directional callBackService
 */
@Remotable
@Callback(CallbackInterface.class)
public interface ServiceMissmatchComponent {

    // infrastructure won't detect difference between String and ParameterObject
    String foo(ParameterObject po);
    
    // Infrastructure will detect difference between parameter numbers
    String foo1(ParameterObject po, String str);

    void callback(String str);

    void modifyParameter();

    ParameterObject getPO();
    
    void inArray(String[] stringArray);
    
    String[] outArray();     
    
    void inCollection(List<String> stringArray);
    
    List<String> outCollection();      
}
