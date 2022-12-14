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
package org.apache.tuscany.maven.plugin;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.tuscany.sca.Node;
import org.apache.tuscany.sca.TuscanyRuntime;

/**
 * Maven Mojo to start a Tuscany runtime and install the project as an SCA
 * contribution. Invoked with "mvn tuscany:start" usually from configuration in
 * a module pom.xml
 * 
 * @goal start
 * @requiresDependencyResolution runtime
 * @execute phase="test-compile"
 * @description Start a Tuscany directly and run composites from a SCA
 *              conribution maven project
 */
public class TuscanyStartMojo extends AbstractMojo {

    public static Map<String, TuscanyRuntime> runtimes = new HashMap<String, TuscanyRuntime>();

    /**
     * The maven project.
     * 
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The project artifactId.
     * 
     * @parameter expression="${project.artifactId}"
     * @required
     */
    protected String artifactId;

    /**
     * The project packaging.
     * 
     * @parameter expression=".${project.packaging}"
     * @required
     */
    protected String packaging;

    /**
     * The project build output directory
     * 
     * @parameter expression="${project.build.outputDirectory}"
     * @required
     */
    protected File buildDirectory;

    /**
     * The project build output directory
     * 
     * @parameter expression="${project.build.finalName}"
     * @required
     */
    protected File finalName;

    /**
     * @parameter expression="${id}" default-value="defaultId"
     */
    private String id;

    /**
     * @parameter expression="${domainURI}" default-value="uri:default"
     */
    private String domainURI;

    /**
     * @parameter expression="${nodeConfig}"
     */
    private String nodeConfig;

    /**
     * @parameter expression="${contributions}"
     */
    private String[] contributions;

    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("Starting Tuscany Runtime...");
        
        TuscanyRuntime runtime = TuscanyRuntime.newInstance();
        runtimes.put(id, runtime);

        if (nodeConfig != null && nodeConfig.length() > 0) {
            try {
                runtime.createNodeFromXML(nodeConfig);
            } catch (Exception e) {
                throw new MojoExecutionException("Exception creating node", e);
            }
        } else {
            List<String> contributionList = new ArrayList<String>();

            addProjectContribution(contributionList);

            addAdditionalContributions(contributionList);
            
            Node node = runtime.createNode(domainURI);
            for (String c : contributionList) {
                String curi;
                try {
                    curi = node.installContribution(null, c, null, null);
                } catch (Exception e) {
                    throw new MojoExecutionException("Exception installing contribution", e);
                }
                try {
                    node.startDeployables(curi);
                } catch (Exception e) {
                    throw new MojoExecutionException("Exception starting deployables for contribution " + curi, e);
                }
            }
        }
    }

    private void addAdditionalContributions(List<String> contributionList) throws MojoExecutionException {
        if (contributions != null) {
            for (String s : contributions) {
                if (new File(s).exists()) {
                    contributionList.add(s);
                } else {
                    boolean found = false;
                    for (Object o : project.getDependencyArtifacts()) {
                        Artifact a = (Artifact)o;
                        if (a.getId().startsWith(s)) {
                            try {
                                contributionList.add(a.getFile().toURI().toURL().toString());
                            } catch (MalformedURLException e) {
                                throw new MojoExecutionException("", e);
                            }
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        throw new IllegalArgumentException("Contribution not found as file or dependency: " + s);
                    }
                }
            }
        }
    }

    protected void addProjectContribution(List<String> cs) throws MojoExecutionException {
        try {

            File contributionFile = new File(buildDirectory.getParent(), finalName.getName());
            if (!contributionFile.exists()) {
                contributionFile = new File(buildDirectory.getParent(), "classes");
            }
            if (!contributionFile.exists()) {
                contributionFile = new File(buildDirectory.getParent(), finalName.getName() + packaging);
            }

            String contribution = contributionFile.toURI().toURL().toString();
            getLog().info("Project contribution: " + contribution);
            cs.add(contribution);

        } catch (MalformedURLException e) {
            throw new MojoExecutionException("", e);
        }
    }
}
