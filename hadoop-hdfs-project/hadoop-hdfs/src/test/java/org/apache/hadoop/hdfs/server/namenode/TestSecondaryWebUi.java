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
package org.apache.hadoop.hdfs.server.namenode;

import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_CHECKPOINT_TXNS_KEY;
<<<<<<< HEAD

import java.io.IOException;
import java.lang.management.ManagementFactory;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

<<<<<<< HEAD
=======
import javax.management.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.URL;

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
public class TestSecondaryWebUi {
  
  private static MiniDFSCluster cluster;
  private static SecondaryNameNode snn;
  private static final Configuration conf = new Configuration();
  
  @BeforeClass
  public static void setUpCluster() throws IOException {
    conf.set(DFSConfigKeys.DFS_NAMENODE_SECONDARY_HTTP_ADDRESS_KEY,
        "0.0.0.0:0");
    conf.setLong(DFS_NAMENODE_CHECKPOINT_TXNS_KEY, 500);
    cluster = new MiniDFSCluster.Builder(conf).numDataNodes(0)
        .build();
    cluster.waitActive();
    
    snn = new SecondaryNameNode(conf);
  }
  
  @AfterClass
  public static void shutDownCluster() {
    if (cluster != null) {
      cluster.shutdown();
<<<<<<< HEAD
    }
    if (snn != null) {
      snn.shutdown();
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
    if (snn != null) {
      snn.shutdown();
    }
  }

  @Test
  public void testSecondaryWebUi()
          throws IOException, MalformedObjectNameException,
                 AttributeNotFoundException, MBeanException,
                 ReflectionException, InstanceNotFoundException {
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName mxbeanName = new ObjectName(
            "Hadoop:service=SecondaryNameNode,name=SecondaryNameNodeInfo");

    String[] checkpointDir = (String[]) mbs.getAttribute(mxbeanName,
            "CheckpointDirectories");
    Assert.assertArrayEquals(checkpointDir, snn.getCheckpointDirectories());
    String[] checkpointEditlogDir = (String[]) mbs.getAttribute(mxbeanName,
            "CheckpointEditlogDirectories");
    Assert.assertArrayEquals(checkpointEditlogDir,
            snn.getCheckpointEditlogDirectories());
  }

  @Test
  public void testSecondaryWebUi()
          throws IOException, MalformedObjectNameException,
                 AttributeNotFoundException, MBeanException,
                 ReflectionException, InstanceNotFoundException {
    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
    ObjectName mxbeanName = new ObjectName(
            "Hadoop:service=SecondaryNameNode,name=SecondaryNameNodeInfo");

    String[] checkpointDir = (String[]) mbs.getAttribute(mxbeanName,
            "CheckpointDirectories");
    Assert.assertArrayEquals(checkpointDir, snn.getCheckpointDirectories());
    String[] checkpointEditlogDir = (String[]) mbs.getAttribute(mxbeanName,
            "CheckpointEditlogDirectories");
    Assert.assertArrayEquals(checkpointEditlogDir,
            snn.getCheckpointEditlogDirectories());
  }
}
