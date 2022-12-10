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

package org.apache.tuscany.sca.binding.rest.provider;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.tuscany.sca.common.http.HTTPContext;
import org.apache.tuscany.sca.common.http.ThreadHTTPContext;
import org.apache.tuscany.sca.core.ExtensionPointRegistry;
import org.apache.tuscany.sca.interfacedef.DataType;
import org.apache.tuscany.sca.interfacedef.impl.DataTypeImpl;

/**
 * The generic JAX-RS message body writer based on Tuscany's databindingframework
 */
@Provider
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML, MediaType.WILDCARD})
public class DataBindingJAXRSWriter<T> extends DataBindingJAXRSProvider implements MessageBodyWriter<T> {

    public static final String FIELDS = "fields";
    public static final String EXCLUDED_FIELDS = "excludedFields";
    public static final String INCLUDED_FIELDS = "includedFields";

    public DataBindingJAXRSWriter(ExtensionPointRegistry registry) {
        super(registry);
    }

    public long getSize(T t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        //        DataType dataType = createDataType(type, genericType);
        return supports(type, genericType, annotations, mediaType);
    }

    public void writeTo(T t,
                        Class<?> type,
                        Type genericType,
                        Annotation[] annotations,
                        MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders,
                        OutputStream entityStream) throws IOException, WebApplicationException {
        DataType dataType = createDataType(type, genericType);
        mediaType = new MediaType(mediaType.getType(), mediaType.getSubtype());
        String dataBinding = OutputStream.class.getName();
        // FIXME: [rfeng] This is a hack to handle application/json
        if (MediaType.APPLICATION_JSON_TYPE.equals(mediaType)) {
            dataBinding = mediaType.toString() + "#" + OutputStream.class.getName();
        } else if (MediaType.APPLICATION_XML_TYPE.equals(mediaType) || MediaType.TEXT_XML_TYPE.equals(mediaType)) {
            dataBinding = OutputStream.class.getName();
        } else if ("application/x-protobuf".equals(mediaType.toString())) {
            dataBinding = mediaType.toString() + "#" + OutputStream.class.getName();
        } else {
            dataBinding = dataType.getDataBinding();
            write(entityStream, t, type);
            return;
        }
        DataType targetDataType =
            new DataTypeImpl(dataBinding, OutputStream.class, OutputStream.class, OutputStream.class);
        // dataBindingExtensionPoint.introspectType(targetDataType, null);

        introspectAnnotations(annotations, targetDataType);

        Map<String, Object> metadata = getFields();
        mediator.mediate(t, entityStream, dataType, targetDataType, metadata);
    }

    private Map<String, Object> getFields() {
        Map<String, Object> metadata = Collections.<String, Object> emptyMap();
        HTTPContext context = ThreadHTTPContext.getHTTPContext();
        if (context != null) {
            metadata = new HashMap<String, Object>();
            String included = context.getHttpRequest().getParameter(INCLUDED_FIELDS);
            String excluded = context.getHttpRequest().getParameter(EXCLUDED_FIELDS);
            Set<String> includedFields = tokenize(included);
            if (includedFields != null) {
                metadata.put(INCLUDED_FIELDS, includedFields);
            }
            Set<String> excludedFields = tokenize(excluded);
            if (excludedFields != null) {
                metadata.put(EXCLUDED_FIELDS, excludedFields);
            }

            // The syntax is fields=f1,f2,-f3
            String fields = (String)context.getHttpRequest().getParameter(FIELDS);
            if (fields != null) {
                Set<String> fieldSet = tokenize(fields);
                for (String f : fieldSet) {
                    if (f.startsWith("-")) {
                        if (excludedFields == null) {
                            excludedFields = new HashSet<String>();
                            metadata.put(EXCLUDED_FIELDS, excludedFields);
                        }
                        excludedFields.add(f.substring(1));
                    } else {
                        if (includedFields == null) {
                            includedFields = new HashSet<String>();
                            metadata.put(INCLUDED_FIELDS, includedFields);
                        }
                        includedFields.add(f);
                    }
                }
            }

        }
        return metadata;
    }

    private Set<String> tokenize(String included) {
        if (included == null) {
            return null;
        }
        String[] fields = included.split("(,| )+");
        Set<String> includedFields = new HashSet<String>();
        for (String f : fields) {
            String field = f.trim();
            if (field.length() > 0) {
                includedFields.add(field);
            }
        }
        return includedFields;
    }

}
