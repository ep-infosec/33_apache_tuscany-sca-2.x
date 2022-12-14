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
package testing;

import java.io.IOException;
import java.io.Writer;
import java.net.URI;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oasisopen.sca.NoSuchDomainException;
import org.oasisopen.sca.NoSuchServiceException;
import org.oasisopen.sca.client.SCAClientFactory;

/**
 */
public class HelloworldServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
       try {

        String component = request.getParameter("component");
        HelloworldService service = SCAClientFactory.newInstance(URI.create("default")).getService(HelloworldService.class, component); 

        String name = request.getParameter("name");
        String greeting = service.sayHello(name);

        Writer out = response.getWriter();
        out.write("<html><head><title>Apache Tuscany Helloworld Servlet Sample</title></head><body>");
        out.write("<h2>Apache Tuscany Helloworld Servlet Sample</h2>");
        out.write("<br><strong>Component " + component + " says: </strong>" + greeting);
        out.write("</body></html>");
        out.flush();
        out.close();

      } catch (NoSuchDomainException e) {
        e.printStackTrace();
      } catch (NoSuchServiceException e) {
        e.printStackTrace();
      }
    }
}
