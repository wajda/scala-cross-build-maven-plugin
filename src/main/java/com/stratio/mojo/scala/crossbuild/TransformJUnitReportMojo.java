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

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.shared.utils.io.DirectoryScanner;

/**
 * Transforms JUnit XML reports so that Scala binary version
 * is appended to the end of every test.
 *
 * This ensures that when generating multiple reports for
 * cross builds, results can be aggregated by reporting tools.
 */
@Mojo(name = "transform-junit-reports")
public class TransformJUnitReportMojo extends AbstractCrossBuildMojo {

  @Parameter(defaultValue = "${project.build.directory}", readonly = true, required = true)
  private File target;

  @Parameter(readonly = true, required = false)
  private String[] includes;

  @Parameter(readonly = true, required = false)
  private String[] excludes;

  @Override
  public void execute(final String scalaBinaryVersion, final String scalaVersion)
      throws MojoExecutionException, MojoFailureException {
    final RewriteJUnitXML rewriter = new RewriteJUnitXML();
    for (final String file: getAllFiles(includes, excludes)) {
      try {
        rewriter.rewrite(new File(file), scalaBinaryVersion);
      } catch (final IOException ex) {
        throw new MojoFailureException("Error while rewriting", ex);
      }
    }
  }

  private String[] getAllFiles(String[] includes, final String[] excludes) {
    if (includes == null || includes.length == 0) {
      getLog().debug("No includes were specified, falling back to surefire-reports, failsafe-reports.");
      includes = getDefaultTestReports();
    }
    final DirectoryScanner ds = new DirectoryScanner();
    ds.setBasedir(".");
    ds.setIncludes(includes);
    ds.setExcludes(excludes);
    ds.scan();
    return ds.getIncludedFiles();
  }

  private String[] getDefaultTestReports() {
    return new String[] {
        getSurefireReportsDirectory() + File.pathSeparator + "TEST-*.xml",
        getFailsafeReportsDirectory() + File.pathSeparator + "TEST-*.xml"
    };
  }

  private String getSurefireReportsDirectory() {
    // Guess surefire-reports path.
    // TODO: Inspect surefire plugin configuration.
    return target + File.pathSeparator + "surefire-reports";
  }

  private String getFailsafeReportsDirectory() {
    // Guess failsafe-reports path.
    // TODO: Inspect surefire plugin configuration.
    return target + File.pathSeparator + "failsafe-reports";
  }
}
