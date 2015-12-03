#!/usr/bin/env bash

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

MYNAME="${BASH_SOURCE-$0}"

<<<<<<< HEAD
DEFAULT_LIBEXEC_DIR="$bin"/../libexec
HADOOP_LIBEXEC_DIR=${HADOOP_LIBEXEC_DIR:-$DEFAULT_LIBEXEC_DIR}
. $HADOOP_LIBEXEC_DIR/hdfs-config.sh

# Start balancer daemon.

"$HADOOP_PREFIX"/sbin/hadoop-daemon.sh --config $HADOOP_CONF_DIR --script "$bin"/hdfs start balancer $@
=======
function hadoop_usage
{
  hadoop_add_option "--buildpaths" "attempt to add class files from build tree"
  hadoop_add_option "--loglevel level" "set the log4j level for this command"

  hadoop_add_option "-policy <policy>" "set the balancer's policy"
  hadoop_add_option "-threshold <threshold>" "set the threshold for balancing"
  hadoop_generate_usage "${MYNAME}" false
}

bin=$(cd -P -- "$(dirname -- "${MYNAME}")" >/dev/null && pwd -P)

# let's locate libexec...
if [[ -n "${HADOOP_PREFIX}" ]]; then
  HADOOP_DEFAULT_LIBEXEC_DIR="${HADOOP_PREFIX}/libexec"
else
  HADOOP_DEFAULT_LIBEXEC_DIR="${bin}/../libexec"
fi

HADOOP_LIBEXEC_DIR="${HADOOP_LIBEXEC_DIR:-$HADOOP_DEFAULT_LIBEXEC_DIR}"
# shellcheck disable=SC2034
HADOOP_NEW_CONFIG=true
if [[ -f "${HADOOP_LIBEXEC_DIR}/hdfs-config.sh" ]]; then
  . "${HADOOP_LIBEXEC_DIR}/hdfs-config.sh"
else
  echo "ERROR: Cannot execute ${HADOOP_LIBEXEC_DIR}/hdfs-config.sh." 2>&1
  exit 1
fi

# Start balancer daemon.

exec "${HADOOP_HDFS_HOME}/bin/hdfs" --config "${HADOOP_CONF_DIR}" --daemon start balancer "$@"
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
