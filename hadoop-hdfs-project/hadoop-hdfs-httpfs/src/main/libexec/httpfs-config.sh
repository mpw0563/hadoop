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
#  http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License.
#

<<<<<<< HEAD
# resolve links - $0 may be a softlink
PRG="${0}"

while [ -h "${PRG}" ]; do
  ls=`ls -ld "${PRG}"`
  link=`expr "$ls" : '.*-> \(.*\)$'`
  if expr "$link" : '/.*' > /dev/null; then
    PRG="$link"
  else
    PRG=`dirname "${PRG}"`/"$link"
  fi
done

BASEDIR=`dirname ${PRG}`
BASEDIR=`cd ${BASEDIR}/..;pwd`


function print() {
  if [ "${HTTPFS_SILENT}" != "true" ]; then
    echo "$@"
  fi
}

# if HTTPFS_HOME is already set warn it will be ignored
#
if [ "${HTTPFS_HOME}" != "" ]; then
  echo "WARNING: current setting of HTTPFS_HOME ignored"
fi

print

# setting HTTPFS_HOME to the installation dir, it cannot be changed
#
export HTTPFS_HOME=${BASEDIR}
httpfs_home=${HTTPFS_HOME}
print "Setting HTTPFS_HOME:          ${HTTPFS_HOME}"

# if the installation has a env file, source it
# this is for native packages installations
#
if [ -e "${HTTPFS_HOME}/bin/httpfs-env.sh" ]; then
  print "Sourcing:                    ${HTTPFS_HOME}/bin/httpfs-env.sh"
  source ${HTTPFS_HOME}/bin/httpfs-env.sh
  grep "^ *export " ${HTTPFS_HOME}/bin/httpfs-env.sh | sed 's/ *export/  setting/'
fi

# verify that the sourced env file didn't change HTTPFS_HOME
# if so, warn and revert
#
if [ "${HTTPFS_HOME}" != "${httpfs_home}" ]; then
  print "WARN: HTTPFS_HOME resetting to ''${HTTPFS_HOME}'' ignored"
  export HTTPFS_HOME=${httpfs_home}
  print "  using HTTPFS_HOME:        ${HTTPFS_HOME}"
fi

if [ "${HTTPFS_CONFIG}" = "" ]; then
  export HTTPFS_CONFIG=${HTTPFS_HOME}/etc/hadoop
  print "Setting HTTPFS_CONFIG:        ${HTTPFS_CONFIG}"
else
  print "Using   HTTPFS_CONFIG:        ${HTTPFS_CONFIG}"
fi
httpfs_config=${HTTPFS_CONFIG}

# if the configuration dir has a env file, source it
#
if [ -e "${HTTPFS_CONFIG}/httpfs-env.sh" ]; then
  print "Sourcing:                    ${HTTPFS_CONFIG}/httpfs-env.sh"
  source ${HTTPFS_CONFIG}/httpfs-env.sh
  grep "^ *export " ${HTTPFS_CONFIG}/httpfs-env.sh | sed 's/ *export/  setting/'
fi

# verify that the sourced env file didn't change HTTPFS_HOME
# if so, warn and revert
#
if [ "${HTTPFS_HOME}" != "${httpfs_home}" ]; then
  echo "WARN: HTTPFS_HOME resetting to ''${HTTPFS_HOME}'' ignored"
  export HTTPFS_HOME=${httpfs_home}
fi

# verify that the sourced env file didn't change HTTPFS_CONFIG
# if so, warn and revert
#
if [ "${HTTPFS_CONFIG}" != "${httpfs_config}" ]; then
  echo "WARN: HTTPFS_CONFIG resetting to ''${HTTPFS_CONFIG}'' ignored"
  export HTTPFS_CONFIG=${httpfs_config}
fi

if [ "${HTTPFS_LOG}" = "" ]; then
  export HTTPFS_LOG=${HTTPFS_HOME}/logs
  print "Setting HTTPFS_LOG:           ${HTTPFS_LOG}"
else
  print "Using   HTTPFS_LOG:           ${HTTPFS_LOG}"
fi

if [ ! -f ${HTTPFS_LOG} ]; then
  mkdir -p ${HTTPFS_LOG}
fi

if [ "${HTTPFS_TEMP}" = "" ]; then
  export HTTPFS_TEMP=${HTTPFS_HOME}/temp
  print "Setting HTTPFS_TEMP:           ${HTTPFS_TEMP}"
else
  print "Using   HTTPFS_TEMP:           ${HTTPFS_TEMP}"
fi

if [ ! -f ${HTTPFS_TEMP} ]; then
  mkdir -p ${HTTPFS_TEMP}
fi

if [ "${HTTPFS_HTTP_PORT}" = "" ]; then
  export HTTPFS_HTTP_PORT=14000
  print "Setting HTTPFS_HTTP_PORT:     ${HTTPFS_HTTP_PORT}"
else
  print "Using   HTTPFS_HTTP_PORT:     ${HTTPFS_HTTP_PORT}"
fi

if [ "${HTTPFS_ADMIN_PORT}" = "" ]; then
  export HTTPFS_ADMIN_PORT=`expr $HTTPFS_HTTP_PORT +  1`
  print "Setting HTTPFS_ADMIN_PORT:     ${HTTPFS_ADMIN_PORT}"
else
  print "Using   HTTPFS_ADMIN_PORT:     ${HTTPFS_ADMIN_PORT}"
fi

if [ "${HTTPFS_HTTP_HOSTNAME}" = "" ]; then
  export HTTPFS_HTTP_HOSTNAME=`hostname -f`
  print "Setting HTTPFS_HTTP_HOSTNAME: ${HTTPFS_HTTP_HOSTNAME}"
else
  print "Using   HTTPFS_HTTP_HOSTNAME: ${HTTPFS_HTTP_HOSTNAME}"
fi

if [ "${HTTPFS_SSL_ENABLED}" = "" ]; then
  export HTTPFS_SSL_ENABLED="false"
  print "Setting HTTPFS_SSL_ENABLED: ${HTTPFS_SSL_ENABLED}"
else
  print "Using   HTTPFS_SSL_ENABLED: ${HTTPFS_SSL_ENABLED}"
fi

if [ "${HTTPFS_SSL_KEYSTORE_FILE}" = "" ]; then
  export HTTPFS_SSL_KEYSTORE_FILE=${HOME}/.keystore
  print "Setting HTTPFS_SSL_KEYSTORE_FILE:     ${HTTPFS_SSL_KEYSTORE_FILE}"
else
  print "Using   HTTPFS_SSL_KEYSTORE_FILE:     ${HTTPFS_SSL_KEYSTORE_FILE}"
fi

if [ "${HTTPFS_SSL_KEYSTORE_PASS}" = "" ]; then
  export HTTPFS_SSL_KEYSTORE_PASS=password
  print "Setting HTTPFS_SSL_KEYSTORE_PASS:     ${HTTPFS_SSL_KEYSTORE_PASS}"
else
  print "Using   HTTPFS_SSL_KEYSTORE_PASS:     ${HTTPFS_SSL_KEYSTORE_PASS}"
fi

if [ "${CATALINA_BASE}" = "" ]; then
  export CATALINA_BASE=${HTTPFS_HOME}/share/hadoop/httpfs/tomcat
  print "Setting CATALINA_BASE:       ${CATALINA_BASE}"
else
  print "Using   CATALINA_BASE:       ${CATALINA_BASE}"
fi

if [ "${HTTPFS_CATALINA_HOME}" = "" ]; then
  export HTTPFS_CATALINA_HOME=${CATALINA_BASE}
  print "Setting HTTPFS_CATALINA_HOME:       ${HTTPFS_CATALINA_HOME}"
else
  print "Using   HTTPFS_CATALINA_HOME:       ${HTTPFS_CATALINA_HOME}"
fi

if [ "${CATALINA_OUT}" = "" ]; then
  export CATALINA_OUT=${HTTPFS_LOG}/httpfs-catalina.out
  print "Setting CATALINA_OUT:        ${CATALINA_OUT}"
else
  print "Using   CATALINA_OUT:        ${CATALINA_OUT}"
fi

if [ "${CATALINA_PID}" = "" ]; then
  export CATALINA_PID=/tmp/httpfs.pid
  print "Setting CATALINA_PID:        ${CATALINA_PID}"
else
  print "Using   CATALINA_PID:        ${CATALINA_PID}"
fi

print
=======
function hadoop_subproject_init
{
  local this
  local binparent
  local varlist

  if [[ -z "${HADOOP_HTTPFS_ENV_PROCESSED}" ]]; then
    if [[ -e "${HADOOP_CONF_DIR}/httpfs-env.sh" ]]; then
      . "${HADOOP_CONF_DIR}/httpfs-env.sh"
      export HADOOP_HTTPFS_ENV_PROCESSED=true
    fi
  fi

  export HADOOP_CATALINA_PREFIX=httpfs

  export HADOOP_CATALINA_TEMP="${HTTPFS_TEMP:-${HADOOP_PREFIX}/temp}"

  hadoop_deprecate_envvar HTTPFS_CONFIG HADOOP_CONF_DIR

  hadoop_deprecate_envvar HTTPFS_LOG HADOOP_LOG_DIR

  export HADOOP_CATALINA_CONFIG="${HADOOP_CONF_DIR}"
  export HADOOP_CATALINA_LOG="${HADOOP_LOG_DIR}"

  export HTTPFS_HTTP_HOSTNAME=${HTTPFS_HTTP_HOSTNAME:-$(hostname -f)}

  export HADOOP_CATALINA_HTTP_PORT="${HTTPFS_HTTP_PORT:-14000}"
  export HADOOP_CATALINA_ADMIN_PORT="${HTTPFS_ADMIN_PORT:-$((HADOOP_CATALINA_HTTP_PORT+1))}"
  export HADOOP_CATALINA_MAX_THREADS="${HTTPFS_MAX_THREADS:-150}"

  export HTTPFS_SSL_ENABLED=${HTTPFS_SSL_ENABLED:-false}

  export HADOOP_CATALINA_SSL_KEYSTORE_FILE="${HTTPFS_SSL_KEYSTORE_FILE:-${HOME}/.keystore}"

  export CATALINA_BASE="${CATALINA_BASE:-${HADOOP_PREFIX}/share/hadoop/httpfs/tomcat}"
  export HADOOP_CATALINA_HOME="${HTTPFS_CATALINA_HOME:-${CATALINA_BASE}}"

  export CATALINA_OUT="${CATALINA_OUT:-${HADOOP_LOG_DIR}/hadoop-${HADOOP_IDENT_STRING}-httpfs-${HOSTNAME}.out}"

  export CATALINA_PID="${CATALINA_PID:-${HADOOP_PID_DIR}/hadoop-${HADOOP_IDENT_STRING}-httpfs.pid}"

  if [[ -n "${HADOOP_SHELL_SCRIPT_DEBUG}" ]]; then
    varlist=$(env | egrep '(^HTTPFS|^CATALINA)' | cut -f1 -d= | grep -v _PASS)
    for i in ${varlist}; do
      hadoop_debug "Setting ${i} to ${!i}"
    done
  fi
}

if [[ -n "${HADOOP_COMMON_HOME}" ]] &&
   [[ -e "${HADOOP_COMMON_HOME}/libexec/hadoop-config.sh" ]]; then
  . "${HADOOP_COMMON_HOME}/libexec/hadoop-config.sh"
elif [[ -e "${HADOOP_LIBEXEC_DIR}/hadoop-config.sh" ]]; then
  . "${HADOOP_LIBEXEC_DIR}/hadoop-config.sh"
elif [[ -e "${HADOOP_PREFIX}/libexec/hadoop-config.sh" ]]; then
  . "${HADOOP_PREFIX}/libexec/hadoop-config.sh"
else
  echo "ERROR: Hadoop common not found." 2>&1
  exit 1
fi
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
