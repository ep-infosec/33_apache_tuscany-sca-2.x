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
package org.apache.tuscany.sca.test.corba.generated;

/**
* org/apache/tuscany/sca/test/corba/generated/RichUnionHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from itest_scenario.idl
* niedziela, 17 sierpie? 2008 19:07:14 CEST
*/

public final class RichUnionHolder implements org.omg.CORBA.portable.Streamable
{
  public org.apache.tuscany.sca.test.corba.generated.RichUnion value = null;

  public RichUnionHolder ()
  {
  }

  public RichUnionHolder (org.apache.tuscany.sca.test.corba.generated.RichUnion initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = org.apache.tuscany.sca.test.corba.generated.RichUnionHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    org.apache.tuscany.sca.test.corba.generated.RichUnionHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return org.apache.tuscany.sca.test.corba.generated.RichUnionHelper.type ();
  }

}
