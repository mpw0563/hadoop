<<<<<<< HEAD
#!/bin/bash
=======
#!/usr/bin/env bash
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
#
#  Licensed under the Apache License, Version 2.0 (the "License");
#  you may not use this file except in compliance with the License.
#  You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
#  Unless required by applicable law or agreed to in writing, software
#  distributed under the License is distributed on an "AS IS" BASIS,
#  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#  See the License for the specific language governing permissions and
#  limitations under the License. See accompanying LICENSE file.
#

<<<<<<< HEAD
###############################################################################
printUsage() {
=======
function hadoop_usage()
{
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  echo "Usage: rumen2sls.sh <OPTIONS>"
  echo "                 --rumen-file=<RUMEN_FILE>"
  echo "                 --output-dir=<SLS_OUTPUT_DIR>"
  echo "                 [--output-prefix=<PREFIX>] (default is sls)"
  echo
}
<<<<<<< HEAD
###############################################################################
parseArgs() {
  for i in $*
  do
    case $i in
    --rumen-file=*)
      rumenfile=${i#*=}
      ;;
    --output-dir=*)
      outputdir=${i#*=}
      ;;
    --output-prefix=*)
      outputprefix=${i#*=}
      ;;
    *)
      echo "Invalid option"
      echo
      printUsage
      exit 1
      ;;
    esac
  done
  if [[ "${rumenfile}" == "" || "${outputdir}" == "" ]] ; then
    echo "Both --rumen-file ${rumenfile} and --output-dir \
          ${outputfdir} must be specified"
    echo
    printUsage
    exit 1
  fi
}
###############################################################################
calculateBasedir() {
  # resolve links - $0 may be a softlink
  PRG="${1}"

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
}
###############################################################################
calculateClasspath() {
  HADOOP_BASE=`which hadoop`
  HADOOP_BASE=`dirname $HADOOP_BASE`
  DEFAULT_LIBEXEC_DIR=${HADOOP_BASE}/../libexec
  HADOOP_LIBEXEC_DIR=${HADOOP_LIBEXEC_DIR:-$DEFAULT_LIBEXEC_DIR}
  . $HADOOP_LIBEXEC_DIR/hadoop-config.sh
  export HADOOP_CLASSPATH="${HADOOP_CLASSPATH}:${TOOL_PATH}"
}
###############################################################################
runSLSGenerator() {
  if [[ "${outputprefix}" == "" ]] ; then
    outputprefix="sls"
  fi

  slsJobs=${outputdir}/${outputprefix}-jobs.json
  slsNodes=${outputdir}/${outputprefix}-nodes.json

  args="-input ${rumenfile} -outputJobs ${slsJobs}";
  args="${args} -outputNodes ${slsNodes}";

  hadoop org.apache.hadoop.yarn.sls.RumenToSLSConverter ${args}
}
###############################################################################

calculateBasedir $0
calculateClasspath
parseArgs "$@"
runSLSGenerator

echo
echo "SLS simulation files available at: ${outputdir}"
echo

exit 0
=======

function parse_args()
{
  for i in "$@"; do
    case $i in
      --rumen-file=*)
        rumenfile=${i#*=}
      ;;
      --output-dir=*)
        outputdir=${i#*=}
      ;;
      --output-prefix=*)
        outputprefix=${i#*=}
      ;;
      *)
        hadoop_error "ERROR: Invalid option ${i}"
        hadoop_exit_with_usage 1
      ;;
    esac
  done

  if [[ -z "${rumenfile}" ]] ; then
    hadoop_error "ERROR: --rumen-file must be specified."
    hadoop_exit_with_usage 1
  fi

  if [[ -z "${outputdir}" ]] ; then
    hadoop_error "ERROR: --output-dir must be specified."
    hadoop_exit_with_usage 1
  fi
}

function calculate_classpath()
{
  hadoop_add_to_classpath_toolspath
}

function run_sls_generator()
{
  if [[ -z "${outputprefix}" ]] ; then
    outputprefix="sls"
  fi

  hadoop_add_param args -input "-input ${rumenfile}"
  hadoop_add_param args -outputJobs "-outputJobs ${outputdir}/${outputprefix}-jobs.json"
  hadoop_add_param args -outputNodes "-outputNodes ${outputdir}/${outputprefix}-nodes.json"

  hadoop_debug "Appending HADOOP_CLIENT_OPTS onto HADOOP_OPTS"
  HADOOP_OPTS="${HADOOP_OPTS} ${HADOOP_CLIENT_OPTS}"

  hadoop_finalize
  # shellcheck disable=SC2086
  hadoop_java_exec rumen2sls org.apache.hadoop.yarn.sls.RumenToSLSConverter ${args}
}

# let's locate libexec...
if [[ -n "${HADOOP_PREFIX}" ]]; then
  HADOOP_DEFAULT_LIBEXEC_DIR="${HADOOP_PREFIX}/libexec"
else
  this="${BASH_SOURCE-$0}"
  bin=$(cd -P -- "$(dirname -- "${this}")" >/dev/null && pwd -P)
  HADOOP_DEFAULT_LIBEXEC_DIR="${bin}/../../../../../libexec"
fi

HADOOP_LIBEXEC_DIR="${HADOOP_LIBEXEC_DIR:-$HADOOP_DEFAULT_LIBEXEC_DIR}"
# shellcheck disable=SC2034
HADOOP_NEW_CONFIG=true
if [[ -f "${HADOOP_LIBEXEC_DIR}/hadoop-config.sh" ]]; then
  . "${HADOOP_LIBEXEC_DIR}/hadoop-config.sh"
else
  echo "ERROR: Cannot execute ${HADOOP_LIBEXEC_DIR}/hadoop-config.sh." 2>&1
  exit 1
fi

if [ $# = 0 ]; then
  hadoop_exit_with_usage 1
fi

parse_args "${@}"
calculate_classpath
run_sls_generator

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
