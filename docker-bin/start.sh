#!/bin/bash -ex

# This script is meant to be run inside Docker.
# You can run this as bin/start.sh from the project root after a Gradle build

readonly INSTALL_DIR=$(pwd)

echo "using install dir [${INSTALL_DIR}]"

JAVA_MEM="-server -d64 -Xmx350m -Xss512k"
JAVA_LOG4J2="-DLog4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector"
JAVA_LOCALE="-Duser.country=SE -Duser.language=en -Duser.timezone=Europe/Stockholm -Dsun.jnu.encoding=UTF-8 -Dfile.encoding=UTF-8"

JAVA_OPTS="${JAVA_MEM} ${JAVA_LOG4J2} ${JAVA_LOCALE}"

CMD="java ${JAVA_OPTS} -Dloader.path=. -jar ${INSTALL_DIR}/*.jar"

echo "Starting application with command: ${CMD}"
exec ${CMD}