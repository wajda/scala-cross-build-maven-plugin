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

import java.util.regex.Pattern;

class PropertyRewriteRule extends RegexRewriteRule {

  private final static Pattern limitPattern =
      Pattern.compile("<(?:/properties|build|reporting|profiles)>");

  public PropertyRewriteRule(final String property, final String newValue) {
    super(
        Pattern.compile(String.format(
            "(?s)(<properties>.*?<%s>)"
                + "[^<]*?"
                + "(</%s>)",
            Pattern.quote(property), Pattern.quote(property)
        ), Pattern.MULTILINE & Pattern.DOTALL),
        "$1" + newValue + "$2",
        limitPattern
    );
  }
}
