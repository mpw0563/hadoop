<<<<<<< HEAD
=======
#
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
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
# User for YARN daemons
export HADOOP_YARN_USER=${HADOOP_YARN_USER:-yarn}

# resolve links - $0 may be a softlink
export YARN_CONF_DIR="${YARN_CONF_DIR:-$HADOOP_YARN_HOME/conf}"

# some Java parameters
# export JAVA_HOME=/home/y/libexec/jdk1.6.0/
if [ "$JAVA_HOME" != "" ]; then
  #echo "run java in $JAVA_HOME"
  JAVA_HOME=$JAVA_HOME
fi
  
if [ "$JAVA_HOME" = "" ]; then
  echo "Error: JAVA_HOME is not set."
  exit 1
fi

JAVA=$JAVA_HOME/bin/java
JAVA_HEAP_MAX=-Xmx1000m 

# For setting YARN specific HEAP sizes please use this
# Parameter and set appropriately
# YARN_HEAPSIZE=1000

# check envvars which might override default args
if [ "$YARN_HEAPSIZE" != "" ]; then
  JAVA_HEAP_MAX="-Xmx""$YARN_HEAPSIZE""m"
fi

# Resource Manager specific parameters

# Specify the max Heapsize for the ResourceManager using a numerical value
# in the scale of MB. For example, to specify an jvm option of -Xmx1000m, set
# the value to 1000.
# This value will be overridden by an Xmx setting specified in either YARN_OPTS
# and/or YARN_RESOURCEMANAGER_OPTS.
# If not specified, the default value will be picked from either YARN_HEAPMAX
# or JAVA_HEAP_MAX with YARN_HEAPMAX as the preferred option of the two.
#export YARN_RESOURCEMANAGER_HEAPSIZE=1000

# Specify the max Heapsize for the timeline server using a numerical value
# in the scale of MB. For example, to specify an jvm option of -Xmx1000m, set
# the value to 1000.
# This value will be overridden by an Xmx setting specified in either YARN_OPTS
# and/or YARN_TIMELINESERVER_OPTS.
# If not specified, the default value will be picked from either YARN_HEAPMAX
# or JAVA_HEAP_MAX with YARN_HEAPMAX as the preferred option of the two.
#export YARN_TIMELINESERVER_HEAPSIZE=1000

# Specify the JVM options to be used when starting the ResourceManager.
# These options will be appended to the options specified as YARN_OPTS
# and therefore may override any similar flags set in YARN_OPTS
#export YARN_RESOURCEMANAGER_OPTS=

# Node Manager specific parameters

# Specify the max Heapsize for the NodeManager using a numerical value
# in the scale of MB. For example, to specify an jvm option of -Xmx1000m, set
# the value to 1000.
# This value will be overridden by an Xmx setting specified in either YARN_OPTS
# and/or YARN_NODEMANAGER_OPTS.
# If not specified, the default value will be picked from either YARN_HEAPMAX
# or JAVA_HEAP_MAX with YARN_HEAPMAX as the preferred option of the two.
#export YARN_NODEMANAGER_HEAPSIZE=1000

# Specify the JVM options to be used when starting the NodeManager.
# These options will be appended to the options specified as YARN_OPTS
# and therefore may override any similar flags set in YARN_OPTS
#export YARN_NODEMANAGER_OPTS=

# so that filenames w/ spaces are handled correctly in loops below
IFS=


# default log directory & file
if [ "$YARN_LOG_DIR" = "" ]; then
  YARN_LOG_DIR="$HADOOP_YARN_HOME/logs"
fi
if [ "$YARN_LOGFILE" = "" ]; then
  YARN_LOGFILE='yarn.log'
fi

# default policy file for service-level authorization
if [ "$YARN_POLICYFILE" = "" ]; then
  YARN_POLICYFILE="hadoop-policy.xml"
fi

# restore ordinary behaviour
unset IFS


YARN_OPTS="$YARN_OPTS -Dhadoop.log.dir=$YARN_LOG_DIR"
YARN_OPTS="$YARN_OPTS -Dyarn.log.dir=$YARN_LOG_DIR"
YARN_OPTS="$YARN_OPTS -Dhadoop.log.file=$YARN_LOGFILE"
YARN_OPTS="$YARN_OPTS -Dyarn.log.file=$YARN_LOGFILE"
YARN_OPTS="$YARN_OPTS -Dyarn.home.dir=$YARN_COMMON_HOME"
YARN_OPTS="$YARN_OPTS -Dyarn.id.str=$YARN_IDENT_STRING"
YARN_OPTS="$YARN_OPTS -Dhadoop.root.logger=${YARN_ROOT_LOGGER:-INFO,console}"
YARN_OPTS="$YARN_OPTS -Dyarn.root.logger=${YARN_ROOT_LOGGER:-INFO,console}"
if [ "x$JAVA_LIBRARY_PATH" != "x" ]; then
  YARN_OPTS="$YARN_OPTS -Djava.library.path=$JAVA_LIBRARY_PATH"
fi  
YARN_OPTS="$YARN_OPTS -Dyarn.policy.file=$YARN_POLICYFILE"


=======
##
## THIS FILE ACTS AS AN OVERRIDE FOR hadoop-env.sh FOR ALL
## WORK DONE BY THE yarn AND RELATED COMMANDS.
##
## Precedence rules:
##
## yarn-env.sh > hadoop-env.sh > hard-coded defaults
##
## YARN_xyz > HADOOP_xyz > hard-coded defaults
##

###
# Resource Manager specific parameters
###

# Specify the max heapsize for the ResourceManager.  If no units are
# given, it will be assumed to be in MB.
# This value will be overridden by an Xmx setting specified in either
# HADOOP_OPTS and/or YARN_RESOURCEMANAGER_OPTS.
# Default is the same as HADOOP_HEAPSIZE_MAX
#export YARN_RESOURCEMANAGER_HEAPSIZE=

# Specify the JVM options to be used when starting the ResourceManager.
# These options will be appended to the options specified as HADOOP_OPTS
# and therefore may override any similar flags set in HADOOP_OPTS
#
# Examples for a Sun/Oracle JDK:
# a) override the appsummary log file:
# export YARN_RESOURCEMANAGER_OPTS="-Dyarn.server.resourcemanager.appsummary.log.file=rm-appsummary.log -Dyarn.server.resourcemanager.appsummary.logger=INFO,RMSUMMARY"
#
# b) Set JMX options
# export YARN_RESOURCEMANAGER_OPTS="-Dcom.sun.management.jmxremote=true -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.port=1026"
#
# c) Set garbage collection logs from hadoop-env.sh
# export YARN_RESOURCE_MANAGER_OPTS="${HADOOP_GC_SETTINGS} -Xloggc:${HADOOP_LOG_DIR}/gc-rm.log-$(date +'%Y%m%d%H%M')"
#
# d) ... or set them directly
# export YARN_RESOURCEMANAGER_OPTS="-verbose:gc -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -Xloggc:${HADOOP_LOG_DIR}/gc-rm.log-$(date +'%Y%m%d%H%M')"
#
#
# export YARN_RESOURCEMANAGER_OPTS=

###
# Node Manager specific parameters
###

# Specify the max heapsize for the NodeManager.  If no units are
# given, it will be assumed to be in MB.
# This value will be overridden by an Xmx setting specified in either
# HADOOP_OPTS and/or YARN_NODEMANAGER_OPTS.
# Default is the same as HADOOP_HEAPSIZE_MAX.
#export YARN_NODEMANAGER_HEAPSIZE=

# Specify the JVM options to be used when starting the NodeManager.
# These options will be appended to the options specified as HADOOP_OPTS
# and therefore may override any similar flags set in HADOOP_OPTS
#
# See ResourceManager for some examples
#
#export YARN_NODEMANAGER_OPTS=

###
# TimeLineServer specifc parameters
###

# Specify the max heapsize for the timelineserver.  If no units are
# given, it will be assumed to be in MB.
# This value will be overridden by an Xmx setting specified in either
# HADOOP_OPTS and/or YARN_TIMELINESERVER_OPTS.
# Default is the same as HADOOP_HEAPSIZE_MAX.
#export YARN_TIMELINE_HEAPSIZE=

# Specify the JVM options to be used when starting the TimeLineServer.
# These options will be appended to the options specified as HADOOP_OPTS
# and therefore may override any similar flags set in HADOOP_OPTS
#
# See ResourceManager for some examples
#
#export YARN_TIMELINESERVER_OPTS=

###
# Web App Proxy Server specifc parameters
###

# Specify the max heapsize for the web app proxy server.  If no units are
# given, it will be assumed to be in MB.
# This value will be overridden by an Xmx setting specified in either
# HADOOP_OPTS and/or YARN_PROXYSERVER_OPTS.
# Default is the same as HADOOP_HEAPSIZE_MAX.
#export YARN_PROXYSERVER_HEAPSIZE=

# Specify the JVM options to be used when starting the proxy server.
# These options will be appended to the options specified as HADOOP_OPTS
# and therefore may override any similar flags set in HADOOP_OPTS
#
# See ResourceManager for some examples
#
#export YARN_PROXYSERVER_OPTS=

###
# Shared Cache Manager specific parameters
###
# Specify the JVM options to be used when starting the
# shared cache manager server.
# These options will be appended to the options specified as HADOOP_OPTS
# and therefore may override any similar flags set in HADOOP_OPTS
#
# See ResourceManager for some examples
#
#export YARN_SHAREDCACHEMANAGER_OPTS=
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
