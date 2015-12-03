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

KMS_SILENT=${KMS_SILENT:-true}

HADOOP_LIBEXEC_DIR="${HADOOP_LIBEXEC_DIR:-${BASEDIR}/libexec}"
source ${HADOOP_LIBEXEC_DIR}/kms-config.sh


if [ "x$JAVA_LIBRARY_PATH" = "x" ]; then
  JAVA_LIBRARY_PATH="${HADOOP_LIBEXEC_DIR}/../lib/native/"
else
  JAVA_LIBRARY_PATH="${HADOOP_LIBEXEC_DIR}/../lib/native/:${JAVA_LIBRARY_PATH}"
=======
MYNAME="${BASH_SOURCE-$0}"

function hadoop_usage
{
  hadoop_add_subcommand "run" "Start kms in the current window"
  hadoop_add_subcommand "run -security" "Start in the current window with security manager"
  hadoop_add_subcommand "start" "Start kms in a separate window"
  hadoop_add_subcommand "start -security" "Start in a separate window with security manager"
  hadoop_add_subcommand "status" "Return the LSB compliant status"
  hadoop_add_subcommand "stop" "Stop kms, waiting up to 5 seconds for the process to end"
  hadoop_add_subcommand "top n" "Stop kms, waiting up to n seconds for the process to end"
  hadoop_add_subcommand "stop -force" "Stop kms, wait up to 5 seconds and then use kill -KILL if still running"
  hadoop_add_subcommand "stop n -force" "Stop kms, wait up to n seconds and then use kill -KILL if still running"
  hadoop_generate_usage "${MYNAME}" false
}

# let's locate libexec...
if [[ -n "${HADOOP_PREFIX}" ]]; then
  HADOOP_DEFAULT_LIBEXEC_DIR="${HADOOP_PREFIX}/libexec"
else
  bin=$(cd -P -- "$(dirname -- "${MYNAME}")" >/dev/null && pwd -P)
  HADOOP_DEFAULT_LIBEXEC_DIR="${bin}/../libexec"
fi

HADOOP_LIBEXEC_DIR="${HADOOP_LIBEXEC_DIR:-$HADOOP_DEFAULT_LIBEXEC_DIR}"
# shellcheck disable=SC2034
HADOOP_NEW_CONFIG=true
if [[ -f "${HADOOP_LIBEXEC_DIR}/kms-config.sh" ]]; then
  . "${HADOOP_LIBEXEC_DIR}/kms-config.sh"
else
  echo "ERROR: Cannot execute ${HADOOP_LIBEXEC_DIR}/kms-config.sh." 2>&1
  exit 1
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
fi

# The Java System property 'kms.http.port' it is not used by Kms,
# it is used in Tomcat's server.xml configuration file
#

# Mask the trustStorePassword
<<<<<<< HEAD
KMS_SSL_TRUSTSTORE_PASS=`echo $CATALINA_OPTS | grep -o 'trustStorePassword=[^ ]*' | awk -F'=' '{print $2}'`
CATALINA_OPTS_DISP=`echo ${CATALINA_OPTS} | sed -e 's/trustStorePassword=[^ ]*/trustStorePassword=***/'`
print "Using   CATALINA_OPTS:       ${CATALINA_OPTS_DISP}"

catalina_opts="-Dkms.home.dir=${KMS_HOME}";
catalina_opts="${catalina_opts} -Dkms.config.dir=${KMS_CONFIG}";
catalina_opts="${catalina_opts} -Dkms.log.dir=${KMS_LOG}";
catalina_opts="${catalina_opts} -Dkms.temp.dir=${KMS_TEMP}";
catalina_opts="${catalina_opts} -Dkms.admin.port=${KMS_ADMIN_PORT}";
catalina_opts="${catalina_opts} -Dkms.http.port=${KMS_HTTP_PORT}";
catalina_opts="${catalina_opts} -Dkms.max.threads=${KMS_MAX_THREADS}";
catalina_opts="${catalina_opts} -Dkms.ssl.keystore.file=${KMS_SSL_KEYSTORE_FILE}";
catalina_opts="${catalina_opts} -Djava.library.path=${JAVA_LIBRARY_PATH}";

print "Adding to CATALINA_OPTS:     ${catalina_opts}"
print "Found KMS_SSL_KEYSTORE_PASS:     `echo ${KMS_SSL_KEYSTORE_PASS} | sed 's/./*/g'`"

export CATALINA_OPTS="${CATALINA_OPTS} ${catalina_opts}"

# A bug in catalina.sh script does not use CATALINA_OPTS for stopping the server
#
if [ "${1}" = "stop" ]; then
=======
# shellcheck disable=SC2086
CATALINA_OPTS_DISP="$(echo ${CATALINA_OPTS} | sed -e 's/trustStorePassword=[^ ]*/trustStorePassword=***/')"

hadoop_debug "Using   CATALINA_OPTS:       ${CATALINA_OPTS_DISP}"

# We're using hadoop-common, so set up some stuff it might need:
hadoop_finalize

hadoop_verify_logdir

if [[ $# = 0 ]]; then
  case "${HADOOP_DAEMON_MODE}" in
    status)
      hadoop_status_daemon "${CATALINA_PID}"
      exit
    ;;
    start)
      set -- "start"
    ;;
    stop)
      set -- "stop"
    ;;
  esac
fi

hadoop_finalize_catalina_opts
export CATALINA_OPTS

# A bug in catalina.sh script does not use CATALINA_OPTS for stopping the server
#
if [[ "${1}" = "stop" ]]; then
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  export JAVA_OPTS=${CATALINA_OPTS}
fi

# If ssl, the populate the passwords into ssl-server.xml before starting tomcat
<<<<<<< HEAD
if [ ! "${KMS_SSL_KEYSTORE_PASS}" = "" ] || [ ! "${KMS_SSL_TRUSTSTORE_PASS}" = "" ]; then
  # Set a KEYSTORE_PASS if not already set
  KMS_SSL_KEYSTORE_PASS=${KMS_SSL_KEYSTORE_PASS:-password}
  cat ${CATALINA_BASE}/conf/ssl-server.xml.conf \
    | sed 's/_kms_ssl_keystore_pass_/'${KMS_SSL_KEYSTORE_PASS}'/g' \
    | sed 's/_kms_ssl_truststore_pass_/'${KMS_SSL_TRUSTSTORE_PASS}'/g' > ${CATALINA_BASE}/conf/ssl-server.xml
fi 

exec ${KMS_CATALINA_HOME}/bin/catalina.sh "$@"
=======
#
# KMS_SSL_KEYSTORE_PASS is a bit odd.
# if undefined, then the if test will not enable ssl on its own
# if "", set it to "password".
# if custom, use provided password
#
if [[ -f "${HADOOP_CATALINA_HOME}/conf/ssl-server.xml.conf" ]]; then
  if [[ -n "${KMS_SSL_KEYSTORE_PASS+x}" ]] || [[ -n "${KMS_SSL_TRUSTSTORE_PASS}" ]]; then
      export KMS_SSL_KEYSTORE_PASS=${KMS_SSL_KEYSTORE_PASS:-password}
      sed -e 's/_kms_ssl_keystore_pass_/'${KMS_SSL_KEYSTORE_PASS}'/g' \
          -e 's/_kms_ssl_truststore_pass_/'${KMS_SSL_TRUSTSTORE_PASS}'/g' \
        "${HADOOP_CATALINA_HOME}/conf/ssl-server.xml.conf" \
        > "${HADOOP_CATALINA_HOME}/conf/ssl-server.xml"
      chmod 700 "${HADOOP_CATALINA_HOME}/conf/ssl-server.xml" >/dev/null 2>&1
  fi
fi

exec "${HADOOP_CATALINA_HOME}/bin/catalina.sh" "$@"
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
