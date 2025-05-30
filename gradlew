#!/bin/sh

#
# Copyright 2015 the original author or authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

##############################################################################
#
#   Gradle start up script for POSIX generated by Gradle.
#
#   Important for running:
#
#   (1) You need a POSIX-compliant shell to run this script. If your /bin/sh is
#       noncompliant, but you have something else like ksh or zsh, then you can
#       set SHELL=/bin/ksh or similar and use that to run this script.
#
#   (2) This script assumes a Java installation in your PATH or JAVA_HOME set.
#
#   (3) This script assumes it is being run from the project directory where
#       gradle/wrapper/gradle-wrapper.jar exists.
#
#   If you have problems running this script, check the Gradle documentation at
#   https://docs.gradle.org
#
##############################################################################

# Attempt to set APP_HOME
# Resolve links: $0 may be a link
app_path=$0

# Need this for relative symlinks.
while [ -h "$app_path" ]; do
    app_path=$(readlink "$app_path")
done

# Get the absolute path of the script
app_path=$(realpath "$app_path" 2>/dev/null || readlink -f "$app_path" 2>/dev/null || echo "$app_path")
APP_HOME=$(dirname "$(dirname "$app_path")")
APP_NAME="Gradle"
APP_BASE_NAME=$(basename "$0")

# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD=maximum

warn () {
    echo "$*"
} >&2

die () {
    echo
    echo "$*"
    echo
    exit 1
} >&2

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "$(uname)" in
    CYGWIN*) cygwin=true ;;
    Darwin*) darwin=true ;;
    MSYS* | MINGW*) msys=true ;;
    NONSTOP*) nonstop=true ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar

# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ]; then
    if [ -x "$JAVA_HOME/jre/sh/java" ]; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD=$JAVA_HOME/jre/sh/java
    else
        JAVACMD=$JAVA_HOME/bin/java
    fi
    if [ ! -x "$JAVACMD" ]; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD=java
    if ! command -v java >/dev/null 2>&1; then
        die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
fi

# Increase the maximum file descriptors if we can.
if ! "$cygwin" && ! "$darwin" && ! "$nonstop"; then
    case $MAX_FD in
        maximum)
            # In POSIX sh, ulimit -H -n is undefined, so use ulimit -n
            MAX_FD=$(ulimit -H -n 2>/dev/null || ulimit -n 2>/dev/null)
            if [ $? -eq 0 ]; then
                if [ "$MAX_FD" != "unlimited" ] && [ -n "$MAX_FD" ]; then
                    ulimit -n "$MAX_FD" >/dev/null 2>&1 || warn "Could not set maximum file descriptor limit: $MAX_FD"
                fi
            else
                warn "Could not query maximum file descriptor limit"
            fi
            ;;
        *) # Use specified MAX_FD
            ulimit -n "$MAX_FD" >/dev/null 2>&1 || warn "Could not set maximum file descriptor limit: $MAX_FD"
            ;;
    esac
fi

# For Cygwin or MSYS, switch paths to Windows format before running java
if "$cygwin" || "$msys"; then
    APP_HOME=$(cygpath --path --mixed "$APP_HOME")
    CLASSPATH=$(cygpath --path --mixed "$CLASSPATH")

    JAVACMD=$(cygpath --unix "$JAVACMD")

    # Now convert the arguments - kludge to limit ourselves to /bin/sh
    for arg; do
        new_arg=$(cygpath --path --mixed "$arg" 2>/dev/null || echo "$arg")
        new_args="$new_args \"$new_arg\""
    done
    # Execute Java with the converted arguments
    eval exec "\"$JAVACMD\"" "$DEFAULT_JVM_OPTS" "$JAVA_OPTS" "$GRADLE_OPTS" \
        -classpath "\"$CLASSPATH\"" \
        org.gradle.wrapper.GradleWrapperMain "$new_args"
    exit
fi

# Escape application args
save () {
    for i; do printf %s\\n "$i" | sed "s/'/'\\\\''/g;1s/^/'/;\$s/\$/' \\\\/" ; done
    echo " "
}
APP_ARGS=$(save "$@")

# Collect all arguments for the java command, stacking in reverse order:
#   * args from the command line
#   * the main class name
#   * -classpath
#   * -Dorg.gradle.appname=$APP_BASE_NAME
#   * --module-path (if defined)
#   * JVM options from various sources
eval set -- "$APP_ARGS" org.gradle.wrapper.GradleWrapperMain -classpath "$CLASSPATH" \
    -Dorg.gradle.appname="$APP_BASE_NAME" \
    "${JAVA_OPTS:+--module-path \"$JAVA_OPTS\"}" \
    "$DEFAULT_JVM_OPTS" "$GRADLE_OPTS"

exec "$JAVACMD" "$@"
