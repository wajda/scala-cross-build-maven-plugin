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

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * Restores pom.xml to original Scala version.
 */
@Mojo(name = "restore-version")
public class RestoreVersionMojo extends AbstractCrossBuildMojo {

  /**
   * The projects in the reactor.
   */
  @Parameter(defaultValue = "${reactorProjects}", required = true, readonly = true)
  private List<MavenProject> reactorProjects;

  /**
   * Maven property to use for Scala binary version.
   */
  @Parameter(property = "defaultScalaBinaryVersion", required = true)
  private String defaultScalaBinaryVersion;

  /**
   * Maven property to use for Scala version.
   */
  @Parameter(property = "defaultScalaVersion", required = true)
  private String defaultScalaVersion;

  public void execute(final String scalaBinaryVersion, final String scalaVersion)
      throws MojoExecutionException {
    ChangeVersionMojoHelper.changeProjects(
        reactorProjects,
        scalaBinaryVersionProperty,
        scalaVersionProperty,
        defaultScalaBinaryVersion,
        defaultScalaVersion,
        generatePomBackupFiles,
        getLog());
  }

}
