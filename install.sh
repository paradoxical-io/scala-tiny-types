#!/bin/bash

target=/usr/local/tiny-types
executable=/usr/local/bin/tiny-types
assemblyOutputFolder=target/scala-2.11/

echo "Clearing old binaries"

rm -rf ${target}
rm -rf ${executable}

echo "Running sbt for assembly"

sbt assembly > /dev/null 2>&1

if [ $? -eq 1 ]; then
    echo "Sbt failed to assemble. Try running 'sbt assembly' for more details"

    exit $?
fi

# get the last assembly by version

assemblyPath=`ls ${assemblyOutputFolder}/*assembly*.jar | sort -r | head -1`
assembly=`basename ${assemblyPath}`

if [ ! -d ${target} ]; then
    echo "Creating $target"

    mkdir ${target}
fi

echo "Copying ${assembly} to ${target}"

cp  ${assemblyOutputFolder}/${assembly} ${target}

echo "Creating executable: $executable"

installText="#!/bin/bash

java -jar ${target}/${assembly} \"\$@\""

echo "${installText}" > ${executable}

chmod +x ${executable}

echo "DONE"