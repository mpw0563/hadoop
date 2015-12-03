#!/usr/bin/env bash
#
# Licensed to the Apache Software Foundation (ASF) under one or more
# contributor license agreements.  See the NOTICE file distributed with
# this work for additional information regarding copyright ownership.
# The ASF licenses this file to You under the Apache License, Version 2.0
# (the "License"); you may not use this file except in compliance with
# the License.  You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

<<<<<<< HEAD
# included in all the hadoop scripts with source command
# should not be executable directly
# also should not be passed any arguments, since we need original $*

# Resolve links ($0 may be a softlink) and convert a relative path
# to an absolute path.  NB: The -P option requires bash built-ins
# or POSIX:2001 compliant cd and pwd.

#   HADOOP_CLASSPATH Extra Java CLASSPATH entries.
#
#   HADOOP_USER_CLASSPATH_FIRST      When defined, the HADOOP_CLASSPATH is 
#                                    added in the beginning of the global
#                                    classpath. Can be defined, for example,
#                                    by doing 
#                                    export HADOOP_USER_CLASSPATH_FIRST=true
#
#   HADOOP_USE_CLIENT_CLASSLOADER    When defined, HADOOP_CLASSPATH and the jar
#                                    as the hadoop jar argument are handled by
#                                    by a separate isolated client classloader.
#                                    If it is set, HADOOP_USER_CLASSPATH_FIRST
#                                    is ignored. Can be defined by doing
#                                    export HADOOP_USE_CLIENT_CLASSLOADER=true
#
#   HADOOP_CLIENT_CLASSLOADER_SYSTEM_CLASSES
#                                    When defined, it overrides the default
#                                    definition of system classes for the client
#                                    classloader when
#                                    HADOOP_USE_CLIENT_CLASSLOADER is enabled.
#                                    Names ending in '.' (period) are treated as
#                                    package names, and names starting with a
#                                    '-' are treated as negative matches.
#                                    For example,
#                                    export HADOOP_CLIENT_CLASSLOADER_SYSTEM_CLASSES="-org.apache.hadoop.UserClass,java.,javax.,org.apache.hadoop."

this="${BASH_SOURCE-$0}"
common_bin=$(cd -P -- "$(dirname -- "$this")" && pwd -P)
script="$(basename -- "$this")"
this="$common_bin/$script"

[ -f "$common_bin/hadoop-layout.sh" ] && . "$common_bin/hadoop-layout.sh"

HADOOP_COMMON_DIR=${HADOOP_COMMON_DIR:-"share/hadoop/common"}
HADOOP_COMMON_LIB_JARS_DIR=${HADOOP_COMMON_LIB_JARS_DIR:-"share/hadoop/common/lib"}
HADOOP_COMMON_LIB_NATIVE_DIR=${HADOOP_COMMON_LIB_NATIVE_DIR:-"lib/native"}
HDFS_DIR=${HDFS_DIR:-"share/hadoop/hdfs"}
HDFS_LIB_JARS_DIR=${HDFS_LIB_JARS_DIR:-"share/hadoop/hdfs/lib"}
YARN_DIR=${YARN_DIR:-"share/hadoop/yarn"}
YARN_LIB_JARS_DIR=${YARN_LIB_JARS_DIR:-"share/hadoop/yarn/lib"}
MAPRED_DIR=${MAPRED_DIR:-"share/hadoop/mapreduce"}
MAPRED_LIB_JARS_DIR=${MAPRED_LIB_JARS_DIR:-"share/hadoop/mapreduce/lib"}

# the root of the Hadoop installation
# See HADOOP-6255 for directory structure layout
HADOOP_DEFAULT_PREFIX=$(cd -P -- "$common_bin"/.. && pwd -P)
HADOOP_PREFIX=${HADOOP_PREFIX:-$HADOOP_DEFAULT_PREFIX}
export HADOOP_PREFIX

#check to see if the conf dir is given as an optional argument
if [ $# -gt 1 ]
then
    if [ "--config" = "$1" ]
	  then
	      shift
	      confdir=$1
	      if [ ! -d "$confdir" ]; then
                echo "Error: Cannot find configuration directory: $confdir"
                exit 1
             fi
	      shift
	      HADOOP_CONF_DIR=$confdir
    fi
fi

# Set log level. Default to INFO.
if [ $# -gt 1 ]
then
  if [ "--loglevel" = "$1" ]
  then
    shift
    HADOOP_LOGLEVEL=$1
    shift
  fi
fi
HADOOP_LOGLEVEL="${HADOOP_LOGLEVEL:-INFO}"
 
# Allow alternate conf dir location.
if [ -e "${HADOOP_PREFIX}/conf/hadoop-env.sh" ]; then
  DEFAULT_CONF_DIR="conf"
else
  DEFAULT_CONF_DIR="etc/hadoop"
fi
=======
####
# IMPORTANT
####
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

## The hadoop-config.sh tends to get executed by non-Hadoop scripts.
## Those parts expect this script to parse/manipulate $@. In order
## to maintain backward compatibility, this means a surprising
## lack of functions for bits that would be much better off in
## a function.
##
## In other words, yes, there is some bad things happen here and
## unless we break the rest of the ecosystem, we can't change it. :(


<<<<<<< HEAD
# Process command line options that specify hosts or file with host
# list
if [ $# -gt 1 ]
then
    if [ "--hosts" = "$1" ]
    then
        shift
        export HADOOP_SLAVES="${HADOOP_CONF_DIR}/$1"
        shift
    elif [ "--hostnames" = "$1" ]
    then
        shift
        export HADOOP_SLAVE_NAMES=$1
        shift
    fi
fi
=======
# included in all the hadoop scripts with source command
# should not be executable directly
# also should not be passed any arguments, since we need original $*
#
# after doing more config, caller should also exec finalize
# function to finish last minute/default configs for
# settings that might be different between daemons & interactive
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

# you must be this high to ride the ride
if [[ -z "${BASH_VERSINFO}" ]] || [[ "${BASH_VERSINFO}" -lt 3 ]]; then
  echo "Hadoop requires bash v3 or better. Sorry."
  exit 1
fi

<<<<<<< HEAD
if [ -f "${HADOOP_CONF_DIR}/hadoop-env.sh" ]; then
  . "${HADOOP_CONF_DIR}/hadoop-env.sh"
fi

cygwin=false
case "$(uname)" in
CYGWIN*) cygwin=true;;
esac

# check if net.ipv6.bindv6only is set to 1
bindv6only=$(/sbin/sysctl -n net.ipv6.bindv6only 2> /dev/null)
if [ -n "$bindv6only" ] && [ "$bindv6only" -eq "1" ] && [ "$HADOOP_ALLOW_IPV6" != "yes" ]
then
  echo "Error: \"net.ipv6.bindv6only\" is set to 1 - Java networking could be broken"
  echo "For more info: http://wiki.apache.org/hadoop/HadoopIPv6"
  exit 1
fi

# Newer versions of glibc use an arena memory allocator that causes virtual
# memory usage to explode. This interacts badly with the many threads that
# we use in Hadoop. Tune the variable down to prevent vmem explosion.
export MALLOC_ARENA_MAX=${MALLOC_ARENA_MAX:-4}

# Attempt to set JAVA_HOME if it is not set
if [[ -z $JAVA_HOME ]]; then
  # On OSX use java_home (or /Library for older versions)
  if [ "Darwin" == "$(uname -s)" ]; then
    if [ -x /usr/libexec/java_home ]; then
      export JAVA_HOME=($(/usr/libexec/java_home))
    else
      export JAVA_HOME=(/Library/Java/Home)
    fi
  fi

  # Bail if we did not detect it
  if [[ -z $JAVA_HOME ]]; then
    echo "Error: JAVA_HOME is not set and could not be found." 1>&2
    exit 1
  fi
fi

JAVA=$JAVA_HOME/bin/java
# some Java parameters
JAVA_HEAP_MAX=-Xmx1000m 

# check envvars which might override default args
if [ "$HADOOP_HEAPSIZE" != "" ]; then
  #echo "run with heapsize $HADOOP_HEAPSIZE"
  JAVA_HEAP_MAX="-Xmx""$HADOOP_HEAPSIZE""m"
  #echo $JAVA_HEAP_MAX
fi

# CLASSPATH initially contains $HADOOP_CONF_DIR
CLASSPATH="${HADOOP_CONF_DIR}"

# so that filenames w/ spaces are handled correctly in loops below
IFS=

if [ "$HADOOP_COMMON_HOME" = "" ]; then
  if [ -d "${HADOOP_PREFIX}/$HADOOP_COMMON_DIR" ]; then
    export HADOOP_COMMON_HOME=$HADOOP_PREFIX
  fi
fi

# for releases, add core hadoop jar & webapps to CLASSPATH
if [ -d "$HADOOP_COMMON_HOME/$HADOOP_COMMON_DIR/webapps" ]; then
  CLASSPATH=${CLASSPATH}:$HADOOP_COMMON_HOME/$HADOOP_COMMON_DIR
fi

if [ -d "$HADOOP_COMMON_HOME/$HADOOP_COMMON_LIB_JARS_DIR" ]; then
  CLASSPATH=${CLASSPATH}:$HADOOP_COMMON_HOME/$HADOOP_COMMON_LIB_JARS_DIR'/*'
fi

CLASSPATH=${CLASSPATH}:$HADOOP_COMMON_HOME/$HADOOP_COMMON_DIR'/*'

# default log directory & file
if [ "$HADOOP_LOG_DIR" = "" ]; then
  HADOOP_LOG_DIR="$HADOOP_PREFIX/logs"
fi
if [ "$HADOOP_LOGFILE" = "" ]; then
  HADOOP_LOGFILE='hadoop.log'
fi
=======
# In order to get partially bootstrapped, we need to figure out where
# we are located. Chances are good that our caller has already done
# this work for us, but just in case...

if [[ -z "${HADOOP_LIBEXEC_DIR}" ]]; then
  _hadoop_common_this="${BASH_SOURCE-$0}"
  HADOOP_LIBEXEC_DIR=$(cd -P -- "$(dirname -- "${_hadoop_common_this}")" >/dev/null && pwd -P)
fi

# get our functions defined for usage later
if [[ -n "${HADOOP_COMMON_HOME}" ]] &&
   [[ -e "${HADOOP_COMMON_HOME}/libexec/hadoop-functions.sh" ]]; then
  . "${HADOOP_COMMON_HOME}/libexec/hadoop-functions.sh"
elif [[ -e "${HADOOP_LIBEXEC_DIR}/hadoop-functions.sh" ]]; then
  . "${HADOOP_LIBEXEC_DIR}/hadoop-functions.sh"
else
  echo "ERROR: Unable to exec ${HADOOP_LIBEXEC_DIR}/hadoop-functions.sh." 1>&2
  exit 1
fi

# allow overrides of the above and pre-defines of the below
if [[ -n "${HADOOP_COMMON_HOME}" ]] &&
   [[ -e "${HADOOP_COMMON_HOME}/libexec/hadoop-layout.sh" ]]; then
  . "${HADOOP_COMMON_HOME}/libexec/hadoop-layout.sh"
elif [[ -e "${HADOOP_LIBEXEC_DIR}/hadoop-layout.sh" ]]; then
  . "${HADOOP_LIBEXEC_DIR}/hadoop-layout.sh"
fi

#
# IMPORTANT! We are not executing user provided code yet!
#
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

# Let's go!  Base definitions so we can move forward
hadoop_bootstrap

# let's find our conf.
#
# first, check and process params passed to us
# we process this in-line so that we can directly modify $@
# if something downstream is processing that directly,
# we need to make sure our params have been ripped out
# note that we do many of them here for various utilities.
# this provides consistency and forces a more consistent
# user experience

<<<<<<< HEAD
# setup 'java.library.path' for native-hadoop code if necessary

if [ -d "${HADOOP_PREFIX}/build/native" -o -d "${HADOOP_PREFIX}/$HADOOP_COMMON_LIB_NATIVE_DIR" ]; then
    
  if [ -d "${HADOOP_PREFIX}/$HADOOP_COMMON_LIB_NATIVE_DIR" ]; then
    if [ "x$JAVA_LIBRARY_PATH" != "x" ]; then
      JAVA_LIBRARY_PATH=${JAVA_LIBRARY_PATH}:${HADOOP_PREFIX}/$HADOOP_COMMON_LIB_NATIVE_DIR
    else
      JAVA_LIBRARY_PATH=${HADOOP_PREFIX}/$HADOOP_COMMON_LIB_NATIVE_DIR
    fi
  fi
fi

# setup a default TOOL_PATH
TOOL_PATH="${TOOL_PATH:-$HADOOP_PREFIX/share/hadoop/tools/lib/*}"

HADOOP_OPTS="$HADOOP_OPTS -Dhadoop.log.dir=$HADOOP_LOG_DIR"
HADOOP_OPTS="$HADOOP_OPTS -Dhadoop.log.file=$HADOOP_LOGFILE"
HADOOP_HOME=$HADOOP_PREFIX
if [ "$cygwin" = true ]; then
  HADOOP_HOME=$(cygpath -w "$HADOOP_HOME" 2>/dev/null)
fi
export HADOOP_HOME
HADOOP_OPTS="$HADOOP_OPTS -Dhadoop.home.dir=$HADOOP_HOME"
HADOOP_OPTS="$HADOOP_OPTS -Dhadoop.id.str=$HADOOP_IDENT_STRING"
HADOOP_OPTS="$HADOOP_OPTS -Dhadoop.root.logger=${HADOOP_ROOT_LOGGER:-${HADOOP_LOGLEVEL},console}"
if [ "x$JAVA_LIBRARY_PATH" != "x" ]; then
  if [ "$cygwin" = true ]; then
    JAVA_LIBRARY_PATH=$(cygpath -w "$JAVA_LIBRARY_PATH" 2>/dev/null)
  fi
  HADOOP_OPTS="$HADOOP_OPTS -Djava.library.path=$JAVA_LIBRARY_PATH"
  export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:$JAVA_LIBRARY_PATH
fi  
HADOOP_OPTS="$HADOOP_OPTS -Dhadoop.policy.file=$HADOOP_POLICYFILE"
=======

# save these off in case our caller needs them
# shellcheck disable=SC2034
HADOOP_USER_PARAMS=("$@")

hadoop_parse_args "$@"
shift "${HADOOP_PARSE_COUNTER}"

#
# Setup the base-line environment
#
hadoop_find_confdir
hadoop_exec_hadoopenv
hadoop_import_shellprofiles
hadoop_exec_userfuncs

#
# IMPORTANT! User provided code is now available!
#

hadoop_exec_hadooprc
hadoop_verify_confdir
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

# do all the OS-specific startup bits here
# this allows us to get a decent JAVA_HOME,
# call crle for LD_LIBRARY_PATH, etc.
hadoop_os_tricks

<<<<<<< HEAD
# put hdfs in classpath if present
if [ "$HADOOP_HDFS_HOME" = "" ]; then
  if [ -d "${HADOOP_PREFIX}/$HDFS_DIR" ]; then
    export HADOOP_HDFS_HOME=$HADOOP_PREFIX
  fi
fi

if [ -d "$HADOOP_HDFS_HOME/$HDFS_DIR/webapps" ]; then
  CLASSPATH=${CLASSPATH}:$HADOOP_HDFS_HOME/$HDFS_DIR
fi

if [ -d "$HADOOP_HDFS_HOME/$HDFS_LIB_JARS_DIR" ]; then
  CLASSPATH=${CLASSPATH}:$HADOOP_HDFS_HOME/$HDFS_LIB_JARS_DIR'/*'
fi

CLASSPATH=${CLASSPATH}:$HADOOP_HDFS_HOME/$HDFS_DIR'/*'

# put yarn in classpath if present
if [ "$HADOOP_YARN_HOME" = "" ]; then
  if [ -d "${HADOOP_PREFIX}/$YARN_DIR" ]; then
    export HADOOP_YARN_HOME=$HADOOP_PREFIX
  fi
fi

if [ -d "$HADOOP_YARN_HOME/$YARN_DIR/webapps" ]; then
  CLASSPATH=${CLASSPATH}:$HADOOP_YARN_HOME/$YARN_DIR
fi

if [ -d "$HADOOP_YARN_HOME/$YARN_LIB_JARS_DIR" ]; then
  CLASSPATH=${CLASSPATH}:$HADOOP_YARN_HOME/$YARN_LIB_JARS_DIR'/*'
fi

CLASSPATH=${CLASSPATH}:$HADOOP_YARN_HOME/$YARN_DIR'/*'

# put mapred in classpath if present AND different from YARN
if [ "$HADOOP_MAPRED_HOME" = "" ]; then
  if [ -d "${HADOOP_PREFIX}/$MAPRED_DIR" ]; then
    export HADOOP_MAPRED_HOME=$HADOOP_PREFIX
  fi
fi

if [ "$HADOOP_MAPRED_HOME/$MAPRED_DIR" != "$HADOOP_YARN_HOME/$YARN_DIR" ] ; then
  if [ -d "$HADOOP_MAPRED_HOME/$MAPRED_DIR/webapps" ]; then
    CLASSPATH=${CLASSPATH}:$HADOOP_MAPRED_HOME/$MAPRED_DIR
  fi

  if [ -d "$HADOOP_MAPRED_HOME/$MAPRED_LIB_JARS_DIR" ]; then
    CLASSPATH=${CLASSPATH}:$HADOOP_MAPRED_HOME/$MAPRED_LIB_JARS_DIR'/*'
  fi

  CLASSPATH=${CLASSPATH}:$HADOOP_MAPRED_HOME/$MAPRED_DIR'/*'
fi

# Add the user-specified CLASSPATH via HADOOP_CLASSPATH
# Add it first or last depending on if user has
# set env-var HADOOP_USER_CLASSPATH_FIRST
# if the user set HADOOP_USE_CLIENT_CLASSLOADER, HADOOP_CLASSPATH is not added
# to the classpath
if [[ ( "$HADOOP_CLASSPATH" != "" ) && ( "$HADOOP_USE_CLIENT_CLASSLOADER" = "" ) ]]; then
  # Prefix it if its to be preceded
  if [ "$HADOOP_USER_CLASSPATH_FIRST" != "" ]; then
    CLASSPATH=${HADOOP_CLASSPATH}:${CLASSPATH}
  else
    CLASSPATH=${CLASSPATH}:${HADOOP_CLASSPATH}
  fi
fi

=======
hadoop_java_setup

hadoop_basic_init

# inject any sub-project overrides, defaults, etc.
if declare -F hadoop_subproject_init >/dev/null ; then
  hadoop_subproject_init
fi

hadoop_shellprofiles_init

# get the native libs in there pretty quick
hadoop_add_javalibpath "${HADOOP_PREFIX}/build/native"
hadoop_add_javalibpath "${HADOOP_PREFIX}/${HADOOP_COMMON_LIB_NATIVE_DIR}"

hadoop_shellprofiles_nativelib

# get the basic java class path for these subprojects
# in as quickly as possible since other stuff
# will definitely depend upon it.

hadoop_add_common_to_classpath
hadoop_shellprofiles_classpath

#
# backwards compatibility. new stuff should
# call this when they are ready
#
if [[ -z "${HADOOP_NEW_CONFIG}" ]]; then
  hadoop_finalize
fi
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
