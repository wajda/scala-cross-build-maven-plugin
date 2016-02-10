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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.xml.stream.XMLStreamException;

import org.apache.commons.io.IOUtils;

class FileRewriter {

  private static final String BKP_SUFFIX = ".bkp";

  private List<RewriteRule> rules;

  public FileRewriter(final List<RewriteRule> rules) {
    this.rules = rules;
  }

  public void rewrite(final File file) throws IOException {
    if (!file.exists()) {
      throw new FileNotFoundException("File does not exist: " + file);
    }
    backupFile(file);
    String result = IOUtils.toString(new FileInputStream(file), StandardCharsets.UTF_8);
    for (final RewriteRule rule: rules) {
      result = rule.replace(result);
    }
    IOUtils.write(result, new FileOutputStream(file), StandardCharsets.UTF_8);
  }

  private static void backupFile(final File origFile) throws IOException {
    final File bkpFile = getBackupFileName(origFile);
    Files.copy(origFile.toPath(), bkpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
  }

  static void restoreFile(final File origFile) throws IOException {
    final File bkpFile = getBackupFileName(origFile);
    Files.copy(bkpFile.toPath(), origFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
  }

  private static File getBackupFileName(final File origFile) throws IOException {
    return new File(origFile.getAbsolutePath() + BKP_SUFFIX);
  }

}
