[![Coverage Status](https://coveralls.io/repos/github/Stratio/scala-cross-build-maven-plugin/badge.svg?branch=master)](https://coveralls.io/github/Stratio/scala-cross-build-maven-plugin?branch=master)

scala-cross-build-maven-plugin
==============================

Introduction
------------

scala-cross-build-maven-plugin provides goals that help maintaining Scala cross version builds
with Maven.

Set up
------

See src/it/full-example/pom.xml for an example.

Usage
-----

```
# Change Scala version in pom.xml.
mvn scala-cross-build:change-version -Pscala-2.11

# Transform JUnit XML reports to append Scala binary version to test case names.
mvn scala-cross-build:transform-junit-reports -Pscala-2.11

# Change back to the default Scala version.
mvn scala-cross-build:restore-version

```
