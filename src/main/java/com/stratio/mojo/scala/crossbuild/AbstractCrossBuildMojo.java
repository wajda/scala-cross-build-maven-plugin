/**
 * Copyright (C) 2015 Stratio (http://stratio.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.stratio.mojo.scala.crossbuild;

import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

abstract class AbstractCrossBuildMojo extends AbstractMojo {

  /**
   * Main project.
   */
  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  /**
   * The projects in the reactor.
   */
  @Parameter(defaultValue = "${reactorProjects}", required = true, readonly = true)
  protected List<MavenProject> reactorProjects;

  /**
   * Maven property to use for Scala binary version.
   */
  @Parameter(property = "scalaBinaryVersionProperty", defaultValue = "scala.binary.version" , required = true)
  protected String scalaBinaryVersionProperty;

  /**
   * Maven property to use for Scala version.
   */
  @Parameter(property = "scalaVersionProperty", defaultValue = "scala.version", required = true)
  protected String scalaVersionProperty;

  /**
   * Enable/disable POM backup files generation
   */
  @Parameter(property = "generatePomBackupFiles", defaultValue = "true", required = true)
  protected Boolean generatePomBackupFiles;

  /**
   * Maven property to use for Scala binary version.
   */
  @Parameter(property = "defaultScalaBinaryVersion", required = false)
  private String defaultScalaBinaryVersion;

  /**
   * Maven property to use for Scala version.
   */
  @Parameter(property = "defaultScalaVersion", required = false)
  private String defaultScalaVersion;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    final String scalaBinaryVersion = project.getProperties().getProperty(scalaBinaryVersionProperty);
    if (scalaBinaryVersion == null) {
      throw new MojoExecutionException("Property " + scalaBinaryVersionProperty + " not defined");
    }
    getLog().debug("Scala binary version: " + scalaBinaryVersion);
    final String scalaVersion = project.getProperties().getProperty(scalaVersionProperty);
    if (scalaVersion == null) {
      throw new MojoExecutionException("Property " + scalaVersionProperty + " not defined");
    }
    getLog().debug("Scala version: " + scalaVersion);
    execute(scalaBinaryVersion, scalaVersion);
  }

  abstract void execute(final String scalaBinaryVersion, final String scalaVersion)
      throws MojoExecutionException, MojoFailureException;
}
