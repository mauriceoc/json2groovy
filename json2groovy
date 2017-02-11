#!/bin/bash

JAVA="${JAVA_HOME?Need to set JAVA_HOME}"/bin/java

JAR="build/libs/json2groovy-all.jar"

if [ ! -f $JAR ]; then
        echo "The project is not built. Please run \"gradle shadowJar\""
        exit 1
fi

$JAVA -jar $JAR $@
