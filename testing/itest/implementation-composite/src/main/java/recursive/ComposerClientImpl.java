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

package recursive;

import org.oasisopen.sca.annotation.Reference;

/**
 * @version $Rev: 905603 $ $Date: 2010-02-02 12:18:47 +0000 (Tue, 02 Feb 2010) $
 */
public class ComposerClientImpl implements Composer {
    @Reference
    protected Composer composerWS;
    
    /**
     * @see recursive.Composer#Compose(java.lang.String)
     */
    public String Compose(String s) {
        return composerWS.Compose(s);
    }

}
