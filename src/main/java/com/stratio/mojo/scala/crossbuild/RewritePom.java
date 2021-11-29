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
import java.util.Arrays;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.maven.project.MavenProject;

class RewritePom {

  private final Boolean generatePomBackupFiles;

  public RewritePom(final Boolean generatePomBackupFiles) {
    this.generatePomBackupFiles = generatePomBackupFiles;
  }

  public void rewrite(
      final MavenProject pom,
      final String scalaBinaryVersionProperty,
      final String scalaVersionProperty,
      final String newBinaryVersion,
      final String newVersion
  ) throws IOException, XMLStreamException {
    final List<RewriteRule> rewriteRules = Arrays.<RewriteRule>asList(
        new ArtifactIdRewriteRule(newBinaryVersion),
        new PropertyRewriteRule(scalaBinaryVersionProperty, newBinaryVersion),
        new PropertyRewriteRule(scalaVersionProperty, newVersion)
    );
    final FileRewriter rewriter = new FileRewriter(rewriteRules, generatePomBackupFiles);
    rewriter.rewrite(pom.getFile());
  }

  public void restorePom(final MavenProject pom) throws IOException {
    FileRewriter.restoreFile(pom.getFile());
  }
}
