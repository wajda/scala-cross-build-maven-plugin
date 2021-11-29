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

import static org.fest.assertions.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

public class RewriteJUnitXMLTest {

  private static final String[][] reports = new String[][]{
      new String[] {
          "/basic_junit.xml", "/basic_junit_result.xml"
      },
      new String[] {
          "/TEST-com.stratio.common.utils.components.repository.RepositoryComponentTest.xml",
          "/TEST-com.stratio.common.utils.components.repository.RepositoryComponentTest_result.xml",
      },
      new String[] {
          "/empty_file.xml", "/empty_file.xml"
      }
  };

  @Rule
  public TemporaryFolder tempDir = new TemporaryFolder();

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  @Test
  public void rewriteNonExistentFile() throws IOException {
    final RewriteJUnitXML rewriter = new RewriteJUnitXML(false);
    final String path = "report_does_not_exist.xml";
    final String newBinaryVersion = "2.11";
    thrown.expect(FileNotFoundException.class);
    rewriter.rewrite(new File(path), newBinaryVersion);
  }

  @Test
  public void rewrite() throws IOException {
    for (final String[] reportPair: reports) {
      final String origReport = reportPair[0];
      final String resultReport = reportPair[1];
      final RewriteJUnitXML rewriter = new RewriteJUnitXML(false);
      tempDir.create();
      final File file = tempDir.newFile();
      file.delete();
      Files.copy(getClass().getResourceAsStream(origReport), file.toPath());
      final String newBinaryVersion = "2.11";
      rewriter.rewrite(file, newBinaryVersion);
      assertEqualToResource(file, resultReport);
      file.delete();
    }
  }

  @Test
  public void idempotency() throws IOException {
    for (final String[] reportPair: reports) {
      final String resultReport = reportPair[1];
      final RewriteJUnitXML rewriter = new RewriteJUnitXML(false);
      tempDir.create();
      final File file = tempDir.newFile();
      file.delete();
      Files.copy(getClass().getResourceAsStream(resultReport), file.toPath());
      final String newBinaryVersion = "2.11";
      rewriter.rewrite(file, newBinaryVersion);
      assertEqualToResource(file, resultReport);
      file.delete();
    }
  }

  private void assertEqualToResource(final File actual, final String expected) throws IOException {
    final List<String> actualLines = IOUtils.readLines(new FileInputStream(actual));
    final List<String> expectedLines = new ArrayList<>(actualLines.size());
    try (final BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(expected)))) {
      String line;
      while ((line = reader.readLine()) != null) {
        expectedLines.add(line);
      }
    }
    assertThat(actualLines).isEqualTo(expectedLines);
  }

}
