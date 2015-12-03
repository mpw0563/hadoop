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
package org.apache.hadoop.hdfs.server.blockmanagement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

<<<<<<< HEAD
import java.io.File;
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
<<<<<<< HEAD
import java.util.List;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.HdfsConfiguration;
import org.apache.hadoop.hdfs.TestBlockStoragePolicy;
import org.apache.hadoop.hdfs.security.token.block.ExportedBlockKeys;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants.NodeType;
import org.apache.hadoop.hdfs.server.common.StorageInfo;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.protocol.DatanodeRegistration;
import org.apache.hadoop.test.PathUtils;
import org.apache.hadoop.util.VersionInfo;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestReplicationPolicyConsiderLoad {

  private static NameNode namenode;
  private static DatanodeManager dnManager;
  private static List<DatanodeRegistration> dnrList;
  private static DatanodeDescriptor[] dataNodes;
  private static DatanodeStorageInfo[] storages;

  @BeforeClass
  public static void setupCluster() throws IOException {
    Configuration conf = new HdfsConfiguration();
    final String[] racks = {
        "/rack1",
        "/rack1",
        "/rack1",
        "/rack2",
        "/rack2",
        "/rack2"};
    storages = DFSTestUtil.createDatanodeStorageInfos(racks);
    dataNodes = DFSTestUtil.toDatanodeDescriptor(storages);
    FileSystem.setDefaultUri(conf, "hdfs://localhost:0");
    conf.set(DFSConfigKeys.DFS_NAMENODE_HTTP_ADDRESS_KEY, "0.0.0.0:0");
    File baseDir = PathUtils.getTestDir(TestReplicationPolicy.class);
    conf.set(DFSConfigKeys.DFS_NAMENODE_NAME_DIR_KEY,
        new File(baseDir, "name").getPath());
    conf.setBoolean(
        DFSConfigKeys.DFS_NAMENODE_AVOID_STALE_DATANODE_FOR_READ_KEY, true);
    conf.setBoolean(
        DFSConfigKeys.DFS_NAMENODE_AVOID_STALE_DATANODE_FOR_WRITE_KEY, true);
    conf.setBoolean(
        DFSConfigKeys.DFS_NAMENODE_REPLICATION_CONSIDERLOAD_KEY, true);
    DFSTestUtil.formatNameNode(conf);
    namenode = new NameNode(conf);
    int blockSize = 1024;

    dnrList = new ArrayList<DatanodeRegistration>();
    dnManager = namenode.getNamesystem().getBlockManager().getDatanodeManager();

    // Register DNs
    for (int i=0; i < 6; i++) {
      DatanodeRegistration dnr = new DatanodeRegistration(dataNodes[i],
          new StorageInfo(NodeType.DATA_NODE), new ExportedBlockKeys(),
          VersionInfo.getVersion());
      dnrList.add(dnr);
      dnManager.registerDatanode(dnr);
      dataNodes[i].getStorageInfos()[0].setUtilizationForTesting(
          2* HdfsServerConstants.MIN_BLOCKS_FOR_WRITE*blockSize, 0L,
          2* HdfsServerConstants.MIN_BLOCKS_FOR_WRITE*blockSize, 0L);
      dataNodes[i].updateHeartbeat(
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[i]),
          0L, 0L, 0, 0, null);
    }
=======
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSTestUtil;
import org.apache.hadoop.hdfs.TestBlockStoragePolicy;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class TestReplicationPolicyConsiderLoad
    extends BaseReplicationPolicyTest {

  public TestReplicationPolicyConsiderLoad(String blockPlacementPolicy) {
    this.blockPlacementPolicy = blockPlacementPolicy;
  }

  @Parameterized.Parameters
  public static Iterable<Object[]> data() {
    return Arrays.asList(new Object[][] {
        { BlockPlacementPolicyDefault.class.getName() },
        { BlockPlacementPolicyWithUpgradeDomain.class.getName() } });
  }

  @Override
  DatanodeDescriptor[] getDatanodeDescriptors(Configuration conf) {
    conf.setDouble(DFSConfigKeys
        .DFS_NAMENODE_REPLICATION_CONSIDERLOAD_FACTOR, 1.2);
    final String[] racks = {
        "/rack1",
        "/rack1",
        "/rack2",
        "/rack2",
        "/rack3",
        "/rack3"};
    storages = DFSTestUtil.createDatanodeStorageInfos(racks);
    return DFSTestUtil.toDatanodeDescriptor(storages);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  private final double EPSILON = 0.0001;
  /**
   * Tests that chooseTarget with considerLoad set to true correctly calculates
   * load with decommissioned nodes.
   */
  @Test
  public void testChooseTargetWithDecomNodes() throws IOException {
    namenode.getNamesystem().writeLock();
    try {
<<<<<<< HEAD
      String blockPoolId = namenode.getNamesystem().getBlockPoolId();
      dnManager.handleHeartbeat(dnrList.get(3),
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[3]),
          blockPoolId, dataNodes[3].getCacheCapacity(),
          dataNodes[3].getCacheRemaining(),
          2, 0, 0, null);
      dnManager.handleHeartbeat(dnrList.get(4),
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[4]),
          blockPoolId, dataNodes[4].getCacheCapacity(),
          dataNodes[4].getCacheRemaining(),
          4, 0, 0, null);
      dnManager.handleHeartbeat(dnrList.get(5),
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[5]),
          blockPoolId, dataNodes[5].getCacheCapacity(),
          dataNodes[5].getCacheRemaining(),
          4, 0, 0, null);
      // value in the above heartbeats
      final int load = 2 + 4 + 4;
      
      FSNamesystem fsn = namenode.getNamesystem();
=======
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[3],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[3]),
          dataNodes[3].getCacheCapacity(),
          dataNodes[3].getCacheUsed(),
          2, 0, null);
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[4],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[4]),
          dataNodes[4].getCacheCapacity(),
          dataNodes[4].getCacheUsed(),
          4, 0, null);
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[5],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[5]),
          dataNodes[5].getCacheCapacity(),
          dataNodes[5].getCacheUsed(),
          4, 0, null);

      // value in the above heartbeats
      final int load = 2 + 4 + 4;
      
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      assertEquals((double)load/6, dnManager.getFSClusterStats()
        .getInServiceXceiverAverage(), EPSILON);
      
      // Decommission DNs so BlockPlacementPolicyDefault.isGoodTarget()
      // returns false
      for (int i = 0; i < 3; i++) {
<<<<<<< HEAD
        DatanodeDescriptor d = dnManager.getDatanode(dnrList.get(i));
=======
        DatanodeDescriptor d = dataNodes[i];
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
        dnManager.getDecomManager().startDecommission(d);
        d.setDecommissioned();
      }
      assertEquals((double)load/3, dnManager.getFSClusterStats()
        .getInServiceXceiverAverage(), EPSILON);

<<<<<<< HEAD
      // update references of writer DN to update the de-commissioned state
      List<DatanodeDescriptor> liveNodes = new ArrayList<DatanodeDescriptor>();
      dnManager.fetchDatanodes(liveNodes, null, false);
      DatanodeDescriptor writerDn = null;
      if (liveNodes.contains(dataNodes[0])) {
        writerDn = liveNodes.get(liveNodes.indexOf(dataNodes[0]));
      }
=======
      DatanodeDescriptor writerDn = dataNodes[0];
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

      // Call chooseTarget()
      DatanodeStorageInfo[] targets = namenode.getNamesystem().getBlockManager()
          .getBlockPlacementPolicy().chooseTarget("testFile.txt", 3,
              writerDn, new ArrayList<DatanodeStorageInfo>(), false, null,
              1024, TestBlockStoragePolicy.DEFAULT_STORAGE_POLICY);

      assertEquals(3, targets.length);
<<<<<<< HEAD
      Set<DatanodeStorageInfo> targetSet = new HashSet<DatanodeStorageInfo>(
=======
      Set<DatanodeStorageInfo> targetSet = new HashSet<>(
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
          Arrays.asList(targets));
      for (int i = 3; i < storages.length; i++) {
        assertTrue(targetSet.contains(storages[i]));
      }
    } finally {
      dataNodes[0].stopDecommission();
      dataNodes[1].stopDecommission();
      dataNodes[2].stopDecommission();
      namenode.getNamesystem().writeUnlock();
    }
<<<<<<< HEAD
  }

  @AfterClass
  public static void teardownCluster() {
    if (namenode != null) namenode.stop();
  }

=======
    NameNode.LOG.info("Done working on it");
  }

  @Test
  public void testConsiderLoadFactor() throws IOException {
    namenode.getNamesystem().writeLock();
    try {
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[0],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[0]),
          dataNodes[0].getCacheCapacity(),
          dataNodes[0].getCacheUsed(),
          5, 0, null);
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[1],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[1]),
          dataNodes[1].getCacheCapacity(),
          dataNodes[1].getCacheUsed(),
          10, 0, null);
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[2],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[2]),
          dataNodes[2].getCacheCapacity(),
          dataNodes[2].getCacheUsed(),
          5, 0, null);

      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[3],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[3]),
          dataNodes[3].getCacheCapacity(),
          dataNodes[3].getCacheUsed(),
          10, 0, null);
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[4],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[4]),
          dataNodes[4].getCacheCapacity(),
          dataNodes[4].getCacheUsed(),
          15, 0, null);
      dnManager.getHeartbeatManager().updateHeartbeat(dataNodes[5],
          BlockManagerTestUtil.getStorageReportsForDatanode(dataNodes[5]),
          dataNodes[5].getCacheCapacity(),
          dataNodes[5].getCacheUsed(),
          15, 0, null);
      //Add values in above heartbeats
      double load = 5 + 10 + 15 + 10 + 15 + 5;
      // Call chooseTarget()
      DatanodeDescriptor writerDn = dataNodes[0];
      DatanodeStorageInfo[] targets = namenode.getNamesystem().getBlockManager()
          .getBlockPlacementPolicy().chooseTarget("testFile.txt", 3, writerDn,
              new ArrayList<DatanodeStorageInfo>(), false, null,
              1024, TestBlockStoragePolicy.DEFAULT_STORAGE_POLICY);
      for(DatanodeStorageInfo info : targets) {
        assertTrue("The node "+info.getDatanodeDescriptor().getName()+
                " has higher load and should not have been picked!",
            info.getDatanodeDescriptor().getXceiverCount() <= (load/6)*1.2);
      }
    } finally {
      namenode.getNamesystem().writeUnlock();
    }
  }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
}
