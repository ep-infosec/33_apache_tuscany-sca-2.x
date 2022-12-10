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

package org.apache.tuscany.sca.test.exceptions.impl;

import org.apache.tuscany.sca.test.exceptions.Checked;
import org.apache.tuscany.sca.test.exceptions.ExceptionHandler;
import org.apache.tuscany.sca.test.exceptions.ExceptionRemoteThrower;
import org.apache.tuscany.sca.test.exceptions.ExceptionThrower;
import org.apache.tuscany.sca.test.exceptions.UnChecked;
import org.oasisopen.sca.ServiceRuntimeException;
import org.oasisopen.sca.annotation.Reference;
import org.oasisopen.sca.annotation.Scope;

@Scope("COMPOSITE")
public class RemoteWSExceptionHandlerImpl implements ExceptionHandler {
    static final String INIT = "INIT";

    private ExceptionRemoteThrower exceptionThrower;
    
    @Reference
    private ExceptionRemoteThrower exceptionThrowerDuff;

    private String theGood;

    private Checked theBad;

    private UnChecked theUgly;
    
    private ServiceRuntimeException uncheckedException;
    
    private ServiceRuntimeException serviceRuntimeException;
    
    private ServiceRuntimeException bindingException;

    public void testing() {

        assert exceptionThrower != null : "'exceptionThrower' never wired";
        String result = INIT;
        try {
            theGood = result = exceptionThrower.theGood();
            assert result.equals(ExceptionThrower.SO_THEY_SAY);                        
        } catch (Throwable e) {
            assert result == INIT;
            assert false;
            e.printStackTrace();
        }

        result = INIT;
        try {
            result = exceptionThrower.theBad();
            // incredible
            assert false : "Expected 'Check' Exception";

        } catch (Checked e) {
            // This is good...
            assert result == INIT;
            theBad = e;
        } catch (Throwable t) {
            // This is not so good.
            t.printStackTrace();
            assert result == INIT;
            assert false : "Got wrong exception '" + t.getClass().getName();
        }

        result = INIT;
        try {
            result = exceptionThrower.theUgly();
            // incredible
            assert false : "Expected 'UnCheck' Exception";

        } catch (ServiceRuntimeException e) {
            
            uncheckedException = e;

        } catch (Checked e) {
            // This is not so good...
            assert false : "Got wrong exception '" + e.getClass().getName();
            assert result == INIT;
        } catch (UnChecked e) {
            theUgly = e;

        } catch (Throwable t) {
            // This is not good.           
            assert false;
            assert result == INIT;

            System.out.println(ExceptionThrower.SO_THEY_SAY + " " + INIT);            
        }
        
        result = INIT;
        try {
            result = exceptionThrower.serviceRuntimeException();
            // incredible
            assert false : "Expected 'ServiceRuntimeException' Exception";

        } catch (ServiceRuntimeException e) {
            
            serviceRuntimeException = e;

        } catch (UnChecked e) {
            // This is not so good...
            assert false : "Got wrong exception '" + e.getClass().getName();
            assert result == INIT;

        } catch (Throwable t) {
            // This is not good.
            assert false;
            assert result == INIT;

            System.out.println(ExceptionThrower.SO_THEY_SAY + " " + INIT);
        }    
        
        result = INIT;
        try {
            theGood = result = exceptionThrowerDuff.theGood();
            assert result.equals(ExceptionThrower.SO_THEY_SAY);  
        } catch (ServiceRuntimeException e) {
            bindingException = e;
        } catch (Throwable e) {
            assert result == INIT;
            assert false;
            e.printStackTrace();
        }

    }

    @Reference
    public void setExceptionThrower(ExceptionRemoteThrower exceptionThrower) {
        this.exceptionThrower = exceptionThrower;
    }

    public String getTheGood() {
        return theGood;
    }

    public Checked getTheBad() {
        return theBad;
    }

    public UnChecked getTheUgly() {
        return theUgly;
    }

    public ExceptionRemoteThrower getExceptionThrower() {
        return exceptionThrower;
    }
    
    public ServiceRuntimeException getServiceRuntimeException() {
        return serviceRuntimeException;
    }

    public ServiceRuntimeException getBindingException() {
        return bindingException;
    }
    
    public ServiceRuntimeException getUncheckedException() {
        return uncheckedException;
    }
}
