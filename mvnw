#!/bin/sh
# ----------------------------------------------------------------------------
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#    https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
# ----------------------------------------------------------------------------

MAVEN_PROJECTBASEDIR=${MAVEN_PROJECTBASEDIR:-$(cd "$(dirname "$0")"; pwd)}
export MAVEN_PROJECTBASEDIR
MAVEN_OPTS="${MAVEN_OPTS:-}"

if [ -z "$JAVA_HOME" ]; then
  echo "Warning: JAVA_HOME environment variable is not set."
fi

if [ -n "$MAVEN_SKIP_RC" ]; then
  echo "Skipping RC files"
else
  for mavenrc in /etc/mavenrc "$HOME/.mavenrc"; do
    if [ -f "$mavenrc" ]; then
      . "$mavenrc"
    fi
  done
fi

if [ -z "$MAVEN_HOME" ]; then
  MAVEN_HOME="$MAVEN_PROJECTBASEDIR/.mvn/wrapper"
fi

WRAPPER_LAUNCHER="$MAVEN_HOME/maven-wrapper.jar"
WRAPPER_PROPERTIES="$MAVEN_HOME/maven-wrapper.properties"

if [ ! -f "$WRAPPER_PROPERTIES" ]; then
  echo "Missing $WRAPPER_PROPERTIES"
  exit 1
fi

if [ ! -f "$WRAPPER_LAUNCHER" ]; then
  echo "Missing $WRAPPER_LAUNCHER"
  exit 1
fi

JAVA_CMD="java"
if [ -n "$JAVA_HOME" ]; then
  JAVA_CMD="$JAVA_HOME/bin/java"
fi

exec "$JAVA_CMD" $MAVEN_OPTS -classpath "$WRAPPER_LAUNCHER" \
  -Dmaven.multiModuleProjectDirectory="$MAVEN_PROJECTBASEDIR" \
  org.apache.maven.wrapper.MavenWrapperMain "$@"
