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

import java.io.IOException;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;

class ChangeVersionMojoHelper {

  public static void changeProjects(
      final List<MavenProject> projects,
      final String scalaBinaryVersionProperty,
      final String scalaVersionProperty,
      final String scalaBinaryVersion,
      final String scalaVersion,
      final Boolean generatePomBackupFiles,
      final Log log) throws MojoExecutionException {
    final RewritePom rewritePom = new RewritePom(generatePomBackupFiles);
    for (final MavenProject subproject: projects) {
      log.debug("Rewriting " + subproject.getFile());
      try {
        rewritePom.rewrite(
            subproject,
            scalaBinaryVersionProperty,
            scalaVersionProperty,
            scalaBinaryVersion,
            scalaVersion);
      } catch (final IOException | XMLStreamException ex) {
        restoreProjects(projects, generatePomBackupFiles);
        throw new MojoExecutionException("Failed to rewrite POM", ex);
      }
    }
  }

  public static void restoreProjects(final List<MavenProject> projects, final Boolean generatePomBackupFiles) throws MojoExecutionException {
    final RewritePom rewritePom = new RewritePom(generatePomBackupFiles);
    for (final MavenProject project: projects) {
      try {
        rewritePom.restorePom(project);
      } catch (final IOException ex) {
        throw new MojoExecutionException("Failed to restore POM", ex);
      }
    }
  }

}
