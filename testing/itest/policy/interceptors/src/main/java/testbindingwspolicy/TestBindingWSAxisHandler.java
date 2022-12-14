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

import org.apache.axis2.AxisFault;
import org.apache.axis2.context.MessageContext;
import org.apache.axis2.handlers.AbstractHandler;

/**
 * An Axis handler to show that policy can add one of needs be
 *
 * @version $Rev: 881959 $ $Date: 2009-11-18 22:07:09 +0000 (Wed, 18 Nov 2009) $
 */
public class TestBindingWSAxisHandler  extends AbstractHandler
{
    private String name;
    
    public TestBindingWSAxisHandler(String name)
    {
        this.name = name;
    }
    
    public InvocationResponse invoke(MessageContext msgContext) throws AxisFault {
        StatusImpl.appendStatus("TestAxisHandler.invoke()", name);
        return InvocationResponse.CONTINUE;
    }
}
