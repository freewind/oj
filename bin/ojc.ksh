#! /bin/ksh
#
#  ojc
#
#  This translates and compile the files into byte codes.
#
#  Oct 25, 1997  by Michiaki Tatsubori

# Please set up followings to be suitable for your enviroment.
JVM=java


# Get path to this program
PRG=`whence $0` >/dev/null 2>&1
# Resolve symlinks. See 4152645.
while [ -h "$PRG" ]; do
    ls=`/usr/bin/ls -ld "$PRG"`
    link=`/usr/bin/expr "$ls" : '^.*-> \(.*\)$'`
    if /usr/bin/expr "$link" : '^/' > /dev/null; then
        prg="$link"
    else
        prg="`/usr/bin/dirname $PRG`/$link"
    fi
    PRG=`whence "$prg"` > /dev/null 2>&1
done
OPENJAVA_HOME=`dirname ${PRG}`/..

CLASSPATH=${OPENJAVA_HOME}/classes:${CLASSPATH}
export CLASSPATH


# Pick out options for JVM
# i.e. -J"-classpath /home/classes"
JVMOPTS=
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
