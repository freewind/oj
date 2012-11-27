#! /bin/sh
#
#  ojc
#
#  This translates and compile the files into byte codes.
#  This is a shell script for linux or some unix-like systems,
#  which do not have ksh.
#
#  Sep 21, 2000  by Michiaki Tatsubori

# Please set up followings to be suitable for your enviroment.
JVM=java

if [ "$OPENJAVA_HOME" = "" ] ; then
    PRG=$0
    progname=`basename $0`
    while [ -L "$PRG" ]; do
	ls=`/bin/ls -ld "$PRG"`
	link=`/usr/bin/expr "$ls" : '.*-> \(.*\)$'`
	if /usr/bin/expr "$link" : '/.*' > /dev/null; then
	    PRG="$link"
	else
	    PRG="`/usr/bin/dirname $PRG`/$link"
	fi
    done
    OPENJAVA_HOME=`dirname ${PRG}`/..
fi

CLASSPATH=${OPENJAVA_HOME}/classes:${CLASSPATH}
export CLASSPATH


# Pick out options for JVM
# i.e. -J"-classpath /home/classes"
JVMOPTS=-classpath ${CLASSPATH}
OJARGS=
for arg in $*; do
    if [ ${arg} != ${arg#-J} ]; then
        JVMOPTS="${JVMOPTS} ${arg#-J}"
    else
        OJARGS="${OJARGS} ${arg}"
    fi
done


# Execute
echo ${JVM} ${JVMOPTS} openjava.ojc.Main ${OJARGS}
${JVM} ${JVMOPTS} openjava.ojc.Main ${OJARGS}
