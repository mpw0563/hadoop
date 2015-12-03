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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
<<<<<<< HEAD

=======
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.base.Supplier;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileUtil;
<<<<<<< HEAD
=======
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.DFSConfigKeys;
import org.apache.hadoop.hdfs.DFSUtilClient;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.hdfs.MiniDFSCluster;
import org.apache.hadoop.hdfs.MiniDFSNNTopology;
import org.apache.hadoop.hdfs.server.namenode.CheckpointSignature;
import org.apache.hadoop.hdfs.server.namenode.FSImageTestUtil;
import org.apache.hadoop.hdfs.server.namenode.NNStorage;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.namenode.NameNodeAdapter;
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.GenericTestUtils.LogCapturer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class TestBootstrapStandby {
  private static final Log LOG = LogFactory.getLog(TestBootstrapStandby.class);
<<<<<<< HEAD
  
  private MiniDFSCluster cluster;
  private NameNode nn0;
  
=======

  private static final int maxNNCount = 3;
  private static final int STARTING_PORT = 20000;

  private MiniDFSCluster cluster;
  private NameNode nn0;

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  @Before
  public void setupCluster() throws IOException {
    Configuration conf = new Configuration();

<<<<<<< HEAD
    MiniDFSNNTopology topology = new MiniDFSNNTopology()
      .addNameservice(new MiniDFSNNTopology.NSConf("ns1")
        .addNN(new MiniDFSNNTopology.NNConf("nn1").setHttpPort(20001))
        .addNN(new MiniDFSNNTopology.NNConf("nn2").setHttpPort(20002)));
    
    cluster = new MiniDFSCluster.Builder(conf)
      .nnTopology(topology)
      .numDataNodes(0)
      .build();
    cluster.waitActive();
    
    nn0 = cluster.getNameNode(0);
    cluster.transitionToActive(0);
    cluster.shutdownNameNode(1);
  }
  
=======
    // duplicate code with MiniQJMHACluster#createDefaultTopology, but don't want to cross
    // dependencies or munge too much code to support it all correctly
    MiniDFSNNTopology.NSConf nameservice = new MiniDFSNNTopology.NSConf("ns1");
    for (int i = 0; i < maxNNCount; i++) {
      nameservice.addNN(new MiniDFSNNTopology.NNConf("nn" + i).setHttpPort(STARTING_PORT + i + 1));
    }

    MiniDFSNNTopology topology = new MiniDFSNNTopology().addNameservice(nameservice);

    cluster = new MiniDFSCluster.Builder(conf)
        .nnTopology(topology)
        .numDataNodes(0)
        .build();
    cluster.waitActive();

    nn0 = cluster.getNameNode(0);
    cluster.transitionToActive(0);
    // shutdown the other NNs
    for (int i = 1; i < maxNNCount; i++) {
      cluster.shutdownNameNode(i);
    }
  }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  @After
  public void shutdownCluster() {
    if (cluster != null) {
      cluster.shutdown();
    }
  }
<<<<<<< HEAD
  
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /**
   * Test for the base success case. The primary NN
   * hasn't made any checkpoints, and we copy the fsimage_0
   * file over and start up.
   */
  @Test
  public void testSuccessfulBaseCase() throws Exception {
    removeStandbyNameDirs();
<<<<<<< HEAD
    
    try {
      cluster.restartNameNode(1);
      fail("Did not throw");
    } catch (IOException ioe) {
      GenericTestUtils.assertExceptionContains(
          "storage directory does not exist or is not accessible",
          ioe);
    }
    
    int rc = BootstrapStandby.run(
        new String[]{"-nonInteractive"},
        cluster.getConfiguration(1));
    assertEquals(0, rc);
    
    // Should have copied over the namespace from the active
    FSImageTestUtil.assertNNHasCheckpoints(cluster, 1,
        ImmutableList.of(0));
    FSImageTestUtil.assertNNFilesMatch(cluster);

    // We should now be able to start the standby successfully.
    cluster.restartNameNode(1);
  }
  
=======

    // skip the first NN, its up
    for (int index = 1; index < maxNNCount; index++) {
      try {
        cluster.restartNameNode(index);
        fail("Did not throw");
      } catch (IOException ioe) {
        GenericTestUtils.assertExceptionContains(
            "storage directory does not exist or is not accessible", ioe);
      }

      int expectedCheckpointTxId = (int)NameNodeAdapter.getNamesystem(nn0)
          .getFSImage().getMostRecentCheckpointTxId();

      int rc = BootstrapStandby.run(new String[] { "-nonInteractive" },
          cluster.getConfiguration(index));
      assertEquals(0, rc);

      // Should have copied over the namespace from the active
      FSImageTestUtil.assertNNHasCheckpoints(cluster, index,
          ImmutableList.of(expectedCheckpointTxId));
    }

    // We should now be able to start the standbys successfully.
    restartNameNodesFromIndex(1);
  }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /**
   * Test for downloading a checkpoint made at a later checkpoint
   * from the active.
   */
  @Test
  public void testDownloadingLaterCheckpoint() throws Exception {
    // Roll edit logs a few times to inflate txid
    nn0.getRpcServer().rollEditLog();
    nn0.getRpcServer().rollEditLog();
    // Make checkpoint
    NameNodeAdapter.enterSafeMode(nn0, false);
    NameNodeAdapter.saveNamespace(nn0);
    NameNodeAdapter.leaveSafeMode(nn0);
    long expectedCheckpointTxId = NameNodeAdapter.getNamesystem(nn0)
<<<<<<< HEAD
      .getFSImage().getMostRecentCheckpointTxId();
    assertEquals(6, expectedCheckpointTxId);

    int rc = BootstrapStandby.run(
        new String[]{"-force"},
        cluster.getConfiguration(1));
    assertEquals(0, rc);
    
    // Should have copied over the namespace from the active
    FSImageTestUtil.assertNNHasCheckpoints(cluster, 1,
        ImmutableList.of((int)expectedCheckpointTxId));
    FSImageTestUtil.assertNNFilesMatch(cluster);

    // We should now be able to start the standby successfully.
    cluster.restartNameNode(1);
=======
        .getFSImage().getMostRecentCheckpointTxId();
    assertEquals(6, expectedCheckpointTxId);

    for (int i = 1; i < maxNNCount; i++) {
      assertEquals(0, forceBootstrap(i));

      // Should have copied over the namespace from the active
      LOG.info("Checking namenode: " + i);
      FSImageTestUtil.assertNNHasCheckpoints(cluster, i,
          ImmutableList.of((int) expectedCheckpointTxId));
    }
    FSImageTestUtil.assertNNFilesMatch(cluster);

    // We should now be able to start the standby successfully.
    restartNameNodesFromIndex(1);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  /**
   * Test for the case where the shared edits dir doesn't have
   * all of the recent edit logs.
   */
  @Test
  public void testSharedEditsMissingLogs() throws Exception {
    removeStandbyNameDirs();
<<<<<<< HEAD
    
    CheckpointSignature sig = nn0.getRpcServer().rollEditLog();
    assertEquals(3, sig.getCurSegmentTxId());
    
    // Should have created edits_1-2 in shared edits dir
    URI editsUri = cluster.getSharedEditsDir(0, 1);
    File editsDir = new File(editsUri);
    File editsSegment = new File(new File(editsDir, "current"),
        NNStorage.getFinalizedEditsFileName(1, 2));
    GenericTestUtils.assertExists(editsSegment);

    // Delete the segment.
    assertTrue(editsSegment.delete());
    
=======

    CheckpointSignature sig = nn0.getRpcServer().rollEditLog();
    assertEquals(3, sig.getCurSegmentTxId());

    // Should have created edits_1-2 in shared edits dir
    URI editsUri = cluster.getSharedEditsDir(0, maxNNCount - 1);
    File editsDir = new File(editsUri);
    File currentDir = new File(editsDir, "current");
    File editsSegment = new File(currentDir,
        NNStorage.getFinalizedEditsFileName(1, 2));
    GenericTestUtils.assertExists(editsSegment);
    GenericTestUtils.assertExists(currentDir);

    // Delete the segment.
    assertTrue(editsSegment.delete());

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    // Trying to bootstrap standby should now fail since the edit
    // logs aren't available in the shared dir.
    LogCapturer logs = GenericTestUtils.LogCapturer.captureLogs(
        LogFactory.getLog(BootstrapStandby.class));
    try {
<<<<<<< HEAD
      int rc = BootstrapStandby.run(
          new String[]{"-force"},
          cluster.getConfiguration(1));
      assertEquals(BootstrapStandby.ERR_CODE_LOGS_UNAVAILABLE, rc);
=======
      assertEquals(BootstrapStandby.ERR_CODE_LOGS_UNAVAILABLE, forceBootstrap(1));
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    } finally {
      logs.stopCapturing();
    }
    GenericTestUtils.assertMatches(logs.getOutput(),
        "FATAL.*Unable to read transaction ids 1-3 from the configured shared");
  }
<<<<<<< HEAD
  
=======

  /**
   * Show that bootstrapping will fail on a given NameNode if its directories already exist. Its not
   * run across all the NN because its testing the state local on each node.
   * @throws Exception on unexpected failure
   */
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  @Test
  public void testStandbyDirsAlreadyExist() throws Exception {
    // Should not pass since standby dirs exist, force not given
    int rc = BootstrapStandby.run(
        new String[]{"-nonInteractive"},
        cluster.getConfiguration(1));
    assertEquals(BootstrapStandby.ERR_CODE_ALREADY_FORMATTED, rc);

    // Should pass with -force
<<<<<<< HEAD
    rc = BootstrapStandby.run(
        new String[]{"-force"},
        cluster.getConfiguration(1));
    assertEquals(0, rc);
  }
  
=======
    assertEquals(0, forceBootstrap(1));
  }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /**
   * Test that, even if the other node is not active, we are able
   * to bootstrap standby from it.
   */
  @Test(timeout=30000)
  public void testOtherNodeNotActive() throws Exception {
    cluster.transitionToStandby(0);
<<<<<<< HEAD
    int rc = BootstrapStandby.run(
        new String[]{"-force"},
        cluster.getConfiguration(1));
    assertEquals(0, rc);
  }

  private void removeStandbyNameDirs() {
    for (URI u : cluster.getNameDirs(1)) {
      assertTrue(u.getScheme().equals("file"));
      File dir = new File(u.getPath());
      LOG.info("Removing standby dir " + dir);
      assertTrue(FileUtil.fullyDelete(dir));
=======
    assertSuccessfulBootstrapFromIndex(1);
  }

  /**
   * Test that bootstrapping standby NN is not limited by
   * {@link DFSConfigKeys#DFS_IMAGE_TRANSFER_RATE_KEY}, but is limited by
   * {@link DFSConfigKeys#DFS_IMAGE_TRANSFER_BOOTSTRAP_STANDBY_RATE_KEY}
   * created by HDFS-8808.
   */
  @Test(timeout=30000)
  public void testRateThrottling() throws Exception {
    cluster.getConfiguration(0).setLong(
        DFSConfigKeys.DFS_IMAGE_TRANSFER_RATE_KEY, 1);
    cluster.restartNameNode(0);
    cluster.waitActive();
    nn0 = cluster.getNameNode(0);
    cluster.transitionToActive(0);
    // Any reasonable test machine should be able to transfer 1 byte per MS
    // (which is ~1K/s)
    final int minXferRatePerMS = 1;
    int imageXferBufferSize = DFSUtilClient.getIoFileBufferSize(
        new Configuration());
    File imageFile = null;
    int dirIdx = 0;
    while (imageFile == null || imageFile.length() < imageXferBufferSize) {
      for (int i = 0; i < 5; i++) {
        cluster.getFileSystem(0).mkdirs(new Path("/foo" + dirIdx++));
      }
      nn0.getRpcServer().rollEditLog();
      NameNodeAdapter.enterSafeMode(nn0, false);
      NameNodeAdapter.saveNamespace(nn0);
      NameNodeAdapter.leaveSafeMode(nn0);
      imageFile = FSImageTestUtil.findLatestImageFile(FSImageTestUtil
          .getFSImage(nn0).getStorage().getStorageDir(0));
    }

    final int timeOut = (int)(imageFile.length() / minXferRatePerMS) + 1;
    // A very low DFS_IMAGE_TRANSFER_RATE_KEY value won't affect bootstrapping
    final AtomicBoolean bootStrapped = new AtomicBoolean(false);
    new Thread(
        new Runnable() {
          @Override
          public void run() {
            try {
              testSuccessfulBaseCase();
              bootStrapped.set(true);
            } catch (Exception e) {
              fail(e.getMessage());
            }
          }
        }
    ).start();
    GenericTestUtils.waitFor(new Supplier<Boolean>() {
      public Boolean get() {
        return bootStrapped.get();
      }
    }, 50, timeOut);

    shutdownCluster();
    setupCluster();
    cluster.getConfiguration(0).setLong(
        DFSConfigKeys.DFS_IMAGE_TRANSFER_BOOTSTRAP_STANDBY_RATE_KEY, 1);
    cluster.restartNameNode(0);
    cluster.waitActive();
    nn0 = cluster.getNameNode(0);
    cluster.transitionToActive(0);
    // A very low DFS_IMAGE_TRANSFER_BOOTSTRAP_STANDBY_RATE_KEY value should
    // cause timeout
    bootStrapped.set(false);
    new Thread(
        new Runnable() {
          @Override
          public void run() {
            try {
              testSuccessfulBaseCase();
              bootStrapped.set(true);
            } catch (Exception e) {
              LOG.info(e.getMessage());
            }
          }
        }
    ).start();
    try {
      GenericTestUtils.waitFor(new Supplier<Boolean>() {
        public Boolean get() {
          return bootStrapped.get();
        }
      }, 50, timeOut);
      fail("Did not timeout");
    } catch (TimeoutException e) {
      LOG.info("Encountered expected timeout.");
    }
  }
  private void removeStandbyNameDirs() {
    for (int i = 1; i < maxNNCount; i++) {
      for (URI u : cluster.getNameDirs(i)) {
        assertTrue(u.getScheme().equals("file"));
        File dir = new File(u.getPath());
        LOG.info("Removing standby dir " + dir);
        assertTrue(FileUtil.fullyDelete(dir));
      }
    }
  }

  private void restartNameNodesFromIndex(int start) throws IOException {
    for (int i = start; i < maxNNCount; i++) {
      // We should now be able to start the standby successfully.
      cluster.restartNameNode(i, false);
    }

    cluster.waitClusterUp();
    cluster.waitActive();
  }

  /**
   * Force boot strapping on a namenode
   * @param i index of the namenode to attempt
   * @return exit code
   * @throws Exception on unexpected failure
   */
  private int forceBootstrap(int i) throws Exception {
    return BootstrapStandby.run(new String[] { "-force" },
        cluster.getConfiguration(i));
  }

  private void assertSuccessfulBootstrapFromIndex(int start) throws Exception {
    for (int i = start; i < maxNNCount; i++) {
      assertEquals(0, forceBootstrap(i));
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
  }
}
