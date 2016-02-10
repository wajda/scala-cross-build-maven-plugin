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
import java.util.Arrays;
import java.util.List;

/**
 * Rewrites a JUnit XML report to append Scala version string (e.g. [Scala 2.11])
 * to the end of each test case name.
 *
 * This currently works with regular expressions, since XML parser implementations
 * are not good at preserving original format, specially with some malformed
 * (but widespread) XML.
 */
class RewriteJUnitXML {

  public void rewrite(final File file, final String scalaBinaryVersion) throws IOException {
    final List<RewriteRule> rewriteRules = Arrays.<RewriteRule>asList(
        new JUnitReportRewriteRule(scalaBinaryVersion)
    );
    final FileRewriter rewriter = new FileRewriter(rewriteRules);
    rewriter.rewrite(file);
  }

}
