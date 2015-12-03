<<<<<<< HEAD
#!/bin/bash
=======
#!/usr/bin/env bash
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License. See accompanying LICENSE file.
#

# Set kms specific environment variables here.
<<<<<<< HEAD

# Settings for the Embedded Tomcat that runs KMS
# Java System properties for KMS should be specified in this variable
#
# export CATALINA_OPTS=

# KMS logs directory
#
# export KMS_LOG=${KMS_HOME}/logs

# KMS temporary directory
#
# export KMS_TEMP=${KMS_HOME}/temp
=======
#
# hadoop-env.sh is read prior to this file.
#

# KMS temporary directory
#
# export KMS_TEMP=${HADOOP_PREFIX}/temp
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

# The HTTP port used by KMS
#
# export KMS_HTTP_PORT=16000

# The Admin port used by KMS
#
<<<<<<< HEAD
# export KMS_ADMIN_PORT=`expr ${KMS_HTTP_PORT} + 1`
=======
# export KMS_ADMIN_PORT=$((KMS_HTTP_PORT + 1))
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

# The maximum number of Tomcat handler threads
#
# export KMS_MAX_THREADS=1000

# The location of the SSL keystore if using SSL
#
# export KMS_SSL_KEYSTORE_FILE=${HOME}/.keystore

<<<<<<< HEAD
=======
#
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
# The password of the SSL keystore if using SSL
#
# export KMS_SSL_KEYSTORE_PASS=password

<<<<<<< HEAD
# The full path to any native libraries that need to be loaded
# (For eg. location of natively compiled tomcat Apache portable
# runtime (APR) libraries
#
# export JAVA_LIBRARY_PATH=${HOME}/lib/native
=======
#
# The password of the truststore
#
# export KMS_SSL_TRUSTSTORE_PASS=


##
## Tomcat specific settings
##
#
# Location of tomcat
#
# export KMS_CATALINA_HOME=${HADOOP_PREFIX}/share/hadoop/kms/tomcat

# Java System properties for KMS should be specified in this variable.
# The java.library.path and hadoop.home.dir properties are automatically
# configured.  In order to supplement java.library.path,
# one should add to the JAVA_LIBRARY_PATH env var.
#
# export CATALINA_OPTS=

# PID file
#
# export CATALINA_PID=${HADOOP_PID_DIR}/hadoop-${HADOOP_IDENT_STRING}-kms.pid

# Output file
#
# export CATALINA_OUT=${KMS_LOG}/hadoop-${HADOOP_IDENT_STRING}-kms-${HOSTNAME}.out

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
