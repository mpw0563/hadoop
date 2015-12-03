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
package org.apache.hadoop.hdfs.server.namenode.ha;

<<<<<<< HEAD
=======
import static org.apache.hadoop.fs.CommonConfigurationKeys.HA_HM_RPC_TIMEOUT_DEFAULT;
import static org.apache.hadoop.fs.CommonConfigurationKeys.HA_HM_RPC_TIMEOUT_KEY;
import static org.apache.hadoop.hdfs.DFSConfigKeys.DFS_NAMENODE_LIFELINE_RPC_ADDRESS_KEY;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import static org.junit.Assert.fail;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
<<<<<<< HEAD
import org.apache.hadoop.ha.HealthCheckFailedException;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.server.namenode.NameNodeResourceChecker;
import org.apache.hadoop.hdfs.server.protocol.NamenodeProtocols;
import org.apache.hadoop.test.GenericTestUtils;
=======
import org.apache.hadoop.ha.HAServiceProtocol;
import org.apache.hadoop.ha.HealthCheckFailedException;
import org.apache.hadoop.hdfs.DFSUtil;
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.server.namenode.NameNodeResourceChecker;
import org.apache.hadoop.hdfs.tools.NNHAServiceTarget;
import org.apache.hadoop.ipc.RemoteException;
import org.apache.hadoop.test.GenericTestUtils;
import org.junit.After;
import org.junit.Before;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.junit.Test;
import org.mockito.Mockito;

public class TestNNHealthCheck {

<<<<<<< HEAD
  @Test
  public void testNNHealthCheck() throws IOException {
    MiniDFSCluster cluster = null;
    try {
      Configuration conf = new Configuration();
      cluster = new MiniDFSCluster.Builder(conf)
          .numDataNodes(0)
          .nnTopology(MiniDFSNNTopology.simpleHATopology())
          .build();

      NameNodeResourceChecker mockResourceChecker = Mockito.mock(
          NameNodeResourceChecker.class);
      Mockito.doReturn(true).when(mockResourceChecker).hasAvailableDiskSpace();
      cluster.getNameNode(0).getNamesystem()
          .setNNResourceChecker(mockResourceChecker);
      
      NamenodeProtocols rpc = cluster.getNameNodeRpc(0);
      
      // Should not throw error, which indicates healthy.
      rpc.monitorHealth();
      
      Mockito.doReturn(false).when(mockResourceChecker).hasAvailableDiskSpace();
      
      try {
        // Should throw error - NN is unhealthy.
        rpc.monitorHealth();
        fail("Should not have succeeded in calling monitorHealth");
      } catch (HealthCheckFailedException hcfe) {
        GenericTestUtils.assertExceptionContains(
            "The NameNode has no resources available", hcfe);
      }
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
=======
  private MiniDFSCluster cluster;
  private Configuration conf;

  @Before
  public void setup() {
    conf = new Configuration();
  }

  @After
  public void shutdown() {
    if (cluster != null) {
      cluster.shutdown();
    }
  }

  @Test
  public void testNNHealthCheck() throws IOException {
    cluster = new MiniDFSCluster.Builder(conf)
        .numDataNodes(0)
        .nnTopology(MiniDFSNNTopology.simpleHATopology())
        .build();
    doNNHealthCheckTest();
  }

  @Test
  public void testNNHealthCheckWithLifelineAddress() throws IOException {
    conf.set(DFS_NAMENODE_LIFELINE_RPC_ADDRESS_KEY, "0.0.0.0:0");
    cluster = new MiniDFSCluster.Builder(conf)
          .numDataNodes(0)
          .nnTopology(MiniDFSNNTopology.simpleHATopology())
          .build();
    doNNHealthCheckTest();
  }

  private void doNNHealthCheckTest() throws IOException {
    NameNodeResourceChecker mockResourceChecker = Mockito.mock(
        NameNodeResourceChecker.class);
    Mockito.doReturn(true).when(mockResourceChecker).hasAvailableDiskSpace();
    cluster.getNameNode(0).getNamesystem()
        .setNNResourceChecker(mockResourceChecker);

    NNHAServiceTarget haTarget = new NNHAServiceTarget(conf,
        DFSUtil.getNamenodeNameServiceId(conf), "nn1");
    HAServiceProtocol rpc = haTarget.getHealthMonitorProxy(conf, conf.getInt(
        HA_HM_RPC_TIMEOUT_KEY, HA_HM_RPC_TIMEOUT_DEFAULT));

    // Should not throw error, which indicates healthy.
    rpc.monitorHealth();

    Mockito.doReturn(false).when(mockResourceChecker).hasAvailableDiskSpace();

    try {
      // Should throw error - NN is unhealthy.
      rpc.monitorHealth();
      fail("Should not have succeeded in calling monitorHealth");
    } catch (HealthCheckFailedException hcfe) {
      GenericTestUtils.assertExceptionContains(
          "The NameNode has no resources available", hcfe);
    } catch (RemoteException re) {
      GenericTestUtils.assertExceptionContains(
          "The NameNode has no resources available",
          re.unwrapRemoteException(HealthCheckFailedException.class));
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
  }
}
