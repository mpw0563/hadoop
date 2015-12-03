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
  if [ "${KMS_SILENT}" != "true" ]; then
    echo "$@"
  fi
}

# if KMS_HOME is already set warn it will be ignored
#
if [ "${KMS_HOME}" != "" ]; then
  echo "WARNING: current setting of KMS_HOME ignored"
fi

print

# setting KMS_HOME to the installation dir, it cannot be changed
#
export KMS_HOME=${BASEDIR}
kms_home=${KMS_HOME}
print "Setting KMS_HOME:          ${KMS_HOME}"

# if the installation has a env file, source it
# this is for native packages installations
#
if [ -e "${KMS_HOME}/bin/kms-env.sh" ]; then
  print "Sourcing:                    ${KMS_HOME}/bin/kms-env.sh"
  source ${KMS_HOME}/bin/kms-env.sh
  grep "^ *export " ${KMS_HOME}/bin/kms-env.sh | sed 's/ *export/  setting/'
fi

# verify that the sourced env file didn't change KMS_HOME
# if so, warn and revert
#
if [ "${KMS_HOME}" != "${kms_home}" ]; then
  print "WARN: KMS_HOME resetting to ''${KMS_HOME}'' ignored"
  export KMS_HOME=${kms_home}
  print "  using KMS_HOME:        ${KMS_HOME}"
fi

if [ "${KMS_CONFIG}" = "" ]; then
  export KMS_CONFIG=${KMS_HOME}/etc/hadoop
  print "Setting KMS_CONFIG:        ${KMS_CONFIG}"
else
  print "Using   KMS_CONFIG:        ${KMS_CONFIG}"
fi
kms_config=${KMS_CONFIG}

# if the configuration dir has a env file, source it
#
if [ -e "${KMS_CONFIG}/kms-env.sh" ]; then
  print "Sourcing:                    ${KMS_CONFIG}/kms-env.sh"
  source ${KMS_CONFIG}/kms-env.sh
  grep "^ *export " ${KMS_CONFIG}/kms-env.sh | sed 's/ *export/  setting/'
fi

# verify that the sourced env file didn't change KMS_HOME
# if so, warn and revert
#
if [ "${KMS_HOME}" != "${kms_home}" ]; then
  echo "WARN: KMS_HOME resetting to ''${KMS_HOME}'' ignored"
  export KMS_HOME=${kms_home}
fi

# verify that the sourced env file didn't change KMS_CONFIG
# if so, warn and revert
#
if [ "${KMS_CONFIG}" != "${kms_config}" ]; then
  echo "WARN: KMS_CONFIG resetting to ''${KMS_CONFIG}'' ignored"
  export KMS_CONFIG=${kms_config}
fi

if [ "${KMS_LOG}" = "" ]; then
  export KMS_LOG=${KMS_HOME}/logs
  print "Setting KMS_LOG:           ${KMS_LOG}"
else
  print "Using   KMS_LOG:           ${KMS_LOG}"
fi

if [ ! -f ${KMS_LOG} ]; then
  mkdir -p ${KMS_LOG}
fi

if [ "${KMS_TEMP}" = "" ]; then
  export KMS_TEMP=${KMS_HOME}/temp
  print "Setting KMS_TEMP:           ${KMS_TEMP}"
else
  print "Using   KMS_TEMP:           ${KMS_TEMP}"
fi

if [ ! -f ${KMS_TEMP} ]; then
  mkdir -p ${KMS_TEMP}
fi

if [ "${KMS_HTTP_PORT}" = "" ]; then
  export KMS_HTTP_PORT=16000
  print "Setting KMS_HTTP_PORT:     ${KMS_HTTP_PORT}"
else
  print "Using   KMS_HTTP_PORT:     ${KMS_HTTP_PORT}"
fi

if [ "${KMS_ADMIN_PORT}" = "" ]; then
  export KMS_ADMIN_PORT=`expr $KMS_HTTP_PORT +  1`
  print "Setting KMS_ADMIN_PORT:     ${KMS_ADMIN_PORT}"
else
  print "Using   KMS_ADMIN_PORT:     ${KMS_ADMIN_PORT}"
fi

if [ "${KMS_MAX_THREADS}" = "" ]; then
  export KMS_MAX_THREADS=1000
  print "Setting KMS_MAX_THREADS:     ${KMS_MAX_THREADS}"
else
  print "Using   KMS_MAX_THREADS:     ${KMS_MAX_THREADS}"
fi

if [ "${KMS_SSL_KEYSTORE_FILE}" = "" ]; then
  export KMS_SSL_KEYSTORE_FILE=${HOME}/.keystore
  print "Setting KMS_SSL_KEYSTORE_FILE:     ${KMS_SSL_KEYSTORE_FILE}"
else
  print "Using   KMS_SSL_KEYSTORE_FILE:     ${KMS_SSL_KEYSTORE_FILE}"
fi

# If KMS_SSL_KEYSTORE_PASS is explicitly set to ""
# then reset to "password". DO NOT set to "password" if
# variable is NOT defined.
if [ "${KMS_SSL_KEYSTORE_PASS}" = "" ]; then
  if [ -n "${KMS_SSL_KEYSTORE_PASS+1}" ]; then
    export KMS_SSL_KEYSTORE_PASS=password
    print "Setting KMS_SSL_KEYSTORE_PASS:     ********"
  fi
else
  KMS_SSL_KEYSTORE_PASS_DISP=`echo ${KMS_SSL_KEYSTORE_PASS} | sed 's/./*/g'`
  print "Using   KMS_SSL_KEYSTORE_PASS:     ${KMS_SSL_KEYSTORE_PASS_DISP}"
fi

if [ "${CATALINA_BASE}" = "" ]; then
  export CATALINA_BASE=${KMS_HOME}/share/hadoop/kms/tomcat
  print "Setting CATALINA_BASE:       ${CATALINA_BASE}"
else
  print "Using   CATALINA_BASE:       ${CATALINA_BASE}"
fi

if [ "${KMS_CATALINA_HOME}" = "" ]; then
  export KMS_CATALINA_HOME=${CATALINA_BASE}
  print "Setting KMS_CATALINA_HOME:       ${KMS_CATALINA_HOME}"
else
  print "Using   KMS_CATALINA_HOME:       ${KMS_CATALINA_HOME}"
fi

if [ "${CATALINA_OUT}" = "" ]; then
  export CATALINA_OUT=${KMS_LOG}/kms-catalina.out
  print "Setting CATALINA_OUT:        ${CATALINA_OUT}"
else
  print "Using   CATALINA_OUT:        ${CATALINA_OUT}"
fi

if [ "${CATALINA_PID}" = "" ]; then
  export CATALINA_PID=/tmp/kms.pid
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

  if [[ -z "${HADOOP_KMS_ENV_PROCESSED}" ]]; then
    if [[ -e "${HADOOP_CONF_DIR}/kms-env.sh" ]]; then
      . "${HADOOP_CONF_DIR}/kms-env.sh"
      export HADOOP_KMS_ENV_PROCESSED=true
    fi
  fi

  export HADOOP_CATALINA_PREFIX=kms

  export HADOOP_CATALINA_TEMP="${KMS_TEMP:-${HADOOP_PREFIX}/temp}"

  hadoop_deprecate_envvar KMS_CONFIG HADOOP_CONF_DIR

  hadoop_deprecate_envvar KMS_LOG HADOOP_LOG_DIR

  export HADOOP_CATALINA_CONFIG="${HADOOP_CONF_DIR}"
  export HADOOP_CATALINA_LOG="${HADOOP_LOG_DIR}"

  export HADOOP_CATALINA_HTTP_PORT="${KMS_HTTP_PORT:-16000}"
  export HADOOP_CATALINA_ADMIN_PORT="${KMS_ADMIN_PORT:-$((HADOOP_CATALINA_HTTP_PORT+1))}"
  export HADOOP_CATALINA_MAX_THREADS="${KMS_MAX_THREADS:-1000}"

  export HADOOP_CATALINA_SSL_KEYSTORE_FILE="${KMS_SSL_KEYSTORE_FILE:-${HOME}/.keystore}"

  # this is undocumented, but older versions would rip the TRUSTSTORE_PASS out of the
  # CATALINA_OPTS
  # shellcheck disable=SC2086
  export KMS_SSL_TRUSTSTORE_PASS=${KMS_SSL_TRUSTSTORE_PASS:-"$(echo ${CATALINA_OPTS} | grep -o 'trustStorePassword=[^ ]*' | cut -f2 -d= )"}

  export CATALINA_BASE="${CATALINA_BASE:-${HADOOP_PREFIX}/share/hadoop/kms/tomcat}"
  export HADOOP_CATALINA_HOME="${KMS_CATALINA_HOME:-${CATALINA_BASE}}"

  export CATALINA_OUT="${CATALINA_OUT:-${HADOOP_LOG_DIR}/hadoop-${HADOOP_IDENT_STRING}-kms-${HOSTNAME}.out}"

  export CATALINA_PID="${CATALINA_PID:-${HADOOP_PID_DIR}/hadoop-${HADOOP_IDENT_STRING}-kms.pid}"

  if [[ -n "${HADOOP_SHELL_SCRIPT_DEBUG}" ]]; then
    varlist=$(env | egrep '(^KMS|^CATALINA)' | cut -f1 -d= | grep -v _PASS)
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
