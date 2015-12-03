/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.hadoop.hdfs.server.datanode.fsdataset.impl;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.datanode.DataStorage;
<<<<<<< HEAD
=======
import org.apache.hadoop.hdfs.server.datanode.dataset.DatasetSpi;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.hdfs.server.datanode.fsdataset.FsDatasetSpi;

/**
 * A factory for creating {@link FsDatasetImpl} objects.
 */
<<<<<<< HEAD
public class FsDatasetFactory extends FsDatasetSpi.Factory<FsDatasetImpl> {
=======
public class FsDatasetFactory extends DatasetSpi.Factory {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  @Override
  public FsDatasetImpl newInstance(DataNode datanode,
      DataStorage storage, Configuration conf) throws IOException {
    return new FsDatasetImpl(datanode, storage, conf);
  }
}
