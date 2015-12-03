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

package org.apache.hadoop.hdfs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.Collection;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.PrefixFileFilter;
=======
import java.util.Map;

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.ChecksumException;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
<<<<<<< HEAD
import org.apache.hadoop.hdfs.protocol.Block;
=======
import org.apache.hadoop.hdfs.protocol.BlockListAsLongs;
import org.apache.hadoop.hdfs.protocol.BlockListAsLongs.BlockReportReplica;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;
import org.apache.hadoop.hdfs.protocol.ExtendedBlock;
import org.apache.hadoop.hdfs.server.datanode.DataNode;
import org.apache.hadoop.hdfs.server.datanode.DataNodeTestUtils;
import org.apache.hadoop.hdfs.server.namenode.FSNamesystem;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.server.protocol.DatanodeRegistration;
<<<<<<< HEAD
=======
import org.apache.hadoop.hdfs.server.protocol.DatanodeStorage;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.test.GenericTestUtils;
import org.apache.hadoop.test.PathUtils;
import org.apache.log4j.Level;
import org.junit.Test;
import org.slf4j.Logger;

/**
 * A JUnit test for corrupted file handling.
 */
public class TestFileCorruption {
  {
    DFSTestUtil.setNameNodeLogLevel(Level.ALL);
    GenericTestUtils.setLogLevel(DataNode.LOG, Level.ALL);
    GenericTestUtils.setLogLevel(DFSClient.LOG, Level.ALL);
  }
  static Logger LOG = NameNode.stateChangeLog;

  /** check if DFS can handle corrupted blocks properly */
  @Test
  public void testFileCorruption() throws Exception {
    MiniDFSCluster cluster = null;
    DFSTestUtil util = new DFSTestUtil.Builder().setName("TestFileCorruption").
        setNumFiles(20).build();
    try {
      Configuration conf = new HdfsConfiguration();
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(3).build();
      FileSystem fs = cluster.getFileSystem();
      util.createFiles(fs, "/srcdat");
      // Now deliberately remove the blocks
<<<<<<< HEAD
      File storageDir = cluster.getInstanceStorageDir(2, 0);
      String bpid = cluster.getNamesystem().getBlockPoolId();
      File data_dir = MiniDFSCluster.getFinalizedDir(storageDir, bpid);
      assertTrue("data directory does not exist", data_dir.exists());
      Collection<File> blocks = FileUtils.listFiles(data_dir,
          new PrefixFileFilter(Block.BLOCK_FILE_PREFIX),
          DirectoryFileFilter.DIRECTORY);
      assertTrue("Blocks do not exist in data-dir", blocks.size() > 0);
      for (File block : blocks) {
        System.out.println("Deliberately removing file " + block.getName());
        assertTrue("Cannot remove file.", block.delete());
=======
      String bpid = cluster.getNamesystem().getBlockPoolId();
      DataNode dn = cluster.getDataNodes().get(2);
      Map<DatanodeStorage, BlockListAsLongs> blockReports =
          dn.getFSDataset().getBlockReports(bpid);
      assertTrue("Blocks do not exist on data-dir", !blockReports.isEmpty());
      for (BlockListAsLongs report : blockReports.values()) {
        for (BlockReportReplica brr : report) {
          LOG.info("Deliberately removing block {}", brr.getBlockName());
          cluster.getFsDatasetTestUtils(2).getMaterializedReplica(
              new ExtendedBlock(bpid, brr)).deleteData();
        }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      }
      assertTrue("Corrupted replicas not handled properly.",
                 util.checkFiles(fs, "/srcdat"));
      util.cleanup(fs, "/srcdat");
    } finally {
      if (cluster != null) { cluster.shutdown(); }
    }
  }

  /** check if local FS can handle corrupted blocks properly */
  @Test
  public void testLocalFileCorruption() throws Exception {
    Configuration conf = new HdfsConfiguration();
    Path file = new Path(PathUtils.getTestDirName(getClass()), "corruptFile");
    FileSystem fs = FileSystem.getLocal(conf);
    DataOutputStream dos = fs.create(file);
    dos.writeBytes("original bytes");
    dos.close();
    // Now deliberately corrupt the file
    dos = new DataOutputStream(new FileOutputStream(file.toString()));
    dos.writeBytes("corruption");
    dos.close();
    // Now attempt to read the file
    DataInputStream dis = fs.open(file, 512);
    try {
      LOG.info("A ChecksumException is expected to be logged.");
      dis.readByte();
    } catch (ChecksumException ignore) {
      //expect this exception but let any NPE get thrown
    }
    fs.delete(file, true);
  }
  
  /** Test the case that a replica is reported corrupt while it is not
   * in blocksMap. Make sure that ArrayIndexOutOfBounds does not thrown.
   * See Hadoop-4351.
   */
  @Test
  public void testArrayOutOfBoundsException() throws Exception {
    MiniDFSCluster cluster = null;
    try {
      Configuration conf = new HdfsConfiguration();
      cluster = new MiniDFSCluster.Builder(conf).numDataNodes(2).build();
      cluster.waitActive();
      
      FileSystem fs = cluster.getFileSystem();
      final Path FILE_PATH = new Path("/tmp.txt");
      final long FILE_LEN = 1L;
      DFSTestUtil.createFile(fs, FILE_PATH, FILE_LEN, (short)2, 1L);
      
      // get the block
      final String bpid = cluster.getNamesystem().getBlockPoolId();
<<<<<<< HEAD
      File storageDir = cluster.getInstanceStorageDir(0, 0);
      File dataDir = MiniDFSCluster.getFinalizedDir(storageDir, bpid);
      assertTrue("Data directory does not exist", dataDir.exists());
      ExtendedBlock blk = getBlock(bpid, dataDir);
      if (blk == null) {
        storageDir = cluster.getInstanceStorageDir(0, 1);
        dataDir = MiniDFSCluster.getFinalizedDir(storageDir, bpid);
        blk = getBlock(bpid, dataDir);
      }
=======
      ExtendedBlock blk = getFirstBlock(cluster.getDataNodes().get(0), bpid);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      assertFalse("Data directory does not contain any blocks or there was an "
          + "IO error", blk==null);

      // start a third datanode
      cluster.startDataNodes(conf, 1, true, null, null);
      ArrayList<DataNode> datanodes = cluster.getDataNodes();
      assertEquals(datanodes.size(), 3);
      DataNode dataNode = datanodes.get(2);
      
      // report corrupted block by the third datanode
      DatanodeRegistration dnR = 
        DataNodeTestUtils.getDNRegistrationForBP(dataNode, blk.getBlockPoolId());
      FSNamesystem ns = cluster.getNamesystem();
      ns.writeLock();
      try {
        cluster.getNamesystem().getBlockManager().findAndMarkBlockAsCorrupt(
            blk, new DatanodeInfo(dnR), "TEST", "STORAGE_ID");
      } finally {
        ns.writeUnlock();
      }
      
      // open the file
      fs.open(FILE_PATH);
      
      //clean up
      fs.delete(FILE_PATH, false);
    } finally {
      if (cluster != null) {
        cluster.shutdown();
      }
    }
  }
  
<<<<<<< HEAD
  public static ExtendedBlock getBlock(String bpid, File dataDir) {
    List<File> metadataFiles = MiniDFSCluster.getAllBlockMetadataFiles(dataDir);
    if (metadataFiles == null || metadataFiles.isEmpty()) {
      return null;
    }
    File metadataFile = metadataFiles.get(0);
    File blockFile = Block.metaToBlockFile(metadataFile);
    return new ExtendedBlock(bpid, Block.getBlockId(blockFile.getName()),
        blockFile.length(), Block.getGenerationStamp(metadataFile.getName()));
=======
  private static ExtendedBlock getFirstBlock(DataNode dn, String bpid) {
    Map<DatanodeStorage, BlockListAsLongs> blockReports =
        dn.getFSDataset().getBlockReports(bpid);
    for (BlockListAsLongs blockLongs : blockReports.values()) {
      for (BlockReportReplica block : blockLongs) {
        return new ExtendedBlock(bpid, block);
      }
    }
    return null;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

}
