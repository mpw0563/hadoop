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

<<<<<<< HEAD
import java.util.LinkedList;

import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants.BlockUCState;
import org.apache.hadoop.util.LightWeightGSet;

/**
 * BlockInfo class maintains for a given block
 * the {@link INodeFile} it is part of and datanodes where the replicas of 
 * the block are stored.
 * BlockInfo class maintains for a given block
 * the {@link BlockCollection} it is part of and datanodes where the replicas of
 * the block are stored.
 */
@InterfaceAudience.Private
public abstract class  BlockInfo extends Block
    implements LightWeightGSet.LinkedElement {
  public static final BlockInfo[] EMPTY_ARRAY = {};

  private BlockCollection bc;
=======
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import com.google.common.base.Preconditions;
import org.apache.hadoop.classification.InterfaceAudience;
import org.apache.hadoop.hdfs.protocol.Block;
import org.apache.hadoop.hdfs.server.common.HdfsServerConstants.BlockUCState;
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.util.LightWeightGSet;

import static org.apache.hadoop.hdfs.server.namenode.INodeId.INVALID_INODE_ID;

/**
 * For a given block (or an erasure coding block group), BlockInfo class
 * maintains 1) the {@link BlockCollection} it is part of, and 2) datanodes
 * where the replicas of the block, or blocks belonging to the erasure coding
 * block group, are stored.
 */
@InterfaceAudience.Private
public abstract class BlockInfo extends Block
    implements LightWeightGSet.LinkedElement {

  public static final BlockInfo[] EMPTY_ARRAY = {};

  /**
   * Replication factor.
   */
  private short replication;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  /**
   * Block collection ID.
   */
  private long bcId;

  /** For implementing {@link LightWeightGSet.LinkedElement} interface. */
  private LightWeightGSet.LinkedElement nextLinkedElement;

  /**
   * This array contains triplets of references. For each i-th storage, the
   * block belongs to triplets[3*i] is the reference to the
   * {@link DatanodeStorageInfo} and triplets[3*i+1] and triplets[3*i+2] are
   * references to the previous and the next blocks, respectively, in the list
   * of blocks belonging to this storage.
   *
   * Using previous and next in Object triplets is done instead of a
   * {@link LinkedList} list to efficiently use memory. With LinkedList the cost
   * per replica is 42 bytes (LinkedList#Entry object per replica) versus 16
   * bytes using the triplets.
   */
  protected Object[] triplets;
<<<<<<< HEAD
=======

  private BlockUnderConstructionFeature uc;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  /**
   * Construct an entry for blocksmap
   * @param size the block's replication factor, or the total number of blocks
   *             in the block group
   */
<<<<<<< HEAD
  public BlockInfo(short replication) {
    this.triplets = new Object[3*replication];
    this.bc = null;
  }

  public BlockInfo(Block blk, short replication) {
    super(blk);
    this.triplets = new Object[3*replication];
    this.bc = null;
  }

  /**
   * Copy construction.
   * This is used to convert BlockInfoUnderConstruction
   * @param from BlockInfo to copy from.
   */
  protected BlockInfo(BlockInfo from) {
    super(from);
    this.triplets = new Object[from.triplets.length];
    this.bc = from.bc;
  }

  public BlockCollection getBlockCollection() {
    return bc;
  }

  public void setBlockCollection(BlockCollection bc) {
    this.bc = bc;
  }

  public boolean isDeleted() {
    return (bc == null);
=======
  public BlockInfo(short size) {
    this.triplets = new Object[3 * size];
    this.bcId = INVALID_INODE_ID;
    this.replication = isStriped() ? 0 : size;
  }

  public BlockInfo(Block blk, short size) {
    super(blk);
    this.triplets = new Object[3*size];
    this.bcId = INVALID_INODE_ID;
    this.replication = isStriped() ? 0 : size;
  }

  public short getReplication() {
    return replication;
  }

  public void setReplication(short repl) {
    this.replication = repl;
  }

  public long getBlockCollectionId() {
    return bcId;
  }

  public void setBlockCollectionId(long id) {
    this.bcId = id;
  }

  public boolean isDeleted() {
    return bcId == INVALID_INODE_ID;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  public DatanodeDescriptor getDatanode(int index) {
    DatanodeStorageInfo storage = getStorageInfo(index);
    return storage == null ? null : storage.getDatanodeDescriptor();
  }

  DatanodeStorageInfo getStorageInfo(int index) {
    assert this.triplets != null : "BlockInfo is not initialized";
    assert index >= 0 && index*3 < triplets.length : "Index is out of bound";
    return (DatanodeStorageInfo)triplets[index*3];
  }

  BlockInfo getPrevious(int index) {
    assert this.triplets != null : "BlockInfo is not initialized";
    assert index >= 0 && index*3+1 < triplets.length : "Index is out of bound";
    BlockInfo info = (BlockInfo)triplets[index*3+1];
    assert info == null ||
        info.getClass().getName().startsWith(BlockInfo.class.getName()) :
<<<<<<< HEAD
              "BlockInfo is expected at " + index*3;
=======
        "BlockInfo is expected at " + index*3;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    return info;
  }

  BlockInfo getNext(int index) {
    assert this.triplets != null : "BlockInfo is not initialized";
    assert index >= 0 && index*3+2 < triplets.length : "Index is out of bound";
    BlockInfo info = (BlockInfo)triplets[index*3+2];
    assert info == null || info.getClass().getName().startsWith(
        BlockInfo.class.getName()) :
        "BlockInfo is expected at " + index*3;
    return info;
  }

  void setStorageInfo(int index, DatanodeStorageInfo storage) {
    assert this.triplets != null : "BlockInfo is not initialized";
    assert index >= 0 && index*3 < triplets.length : "Index is out of bound";
    triplets[index*3] = storage;
  }

  /**
   * Return the previous block on the block list for the datanode at
   * position index. Set the previous block on the list to "to".
   *
   * @param index - the datanode index
   * @param to - block to be set to previous on the list of blocks
   * @return current previous block on the list of blocks
   */
  BlockInfo setPrevious(int index, BlockInfo to) {
    assert this.triplets != null : "BlockInfo is not initialized";
    assert index >= 0 && index*3+1 < triplets.length : "Index is out of bound";
<<<<<<< HEAD
    BlockInfo info = (BlockInfo)triplets[index*3+1];
=======
    BlockInfo info = (BlockInfo) triplets[index*3+1];
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    triplets[index*3+1] = to;
    return info;
  }

  /**
   * Return the next block on the block list for the datanode at
   * position index. Set the next block on the list to "to".
   *
   * @param index - the datanode index
   * @param to - block to be set to next on the list of blocks
<<<<<<< HEAD
   *    * @return current next block on the list of blocks
=======
   * @return current next block on the list of blocks
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
   */
  BlockInfo setNext(int index, BlockInfo to) {
    assert this.triplets != null : "BlockInfo is not initialized";
    assert index >= 0 && index*3+2 < triplets.length : "Index is out of bound";
<<<<<<< HEAD
    BlockInfo info = (BlockInfo)triplets[index*3+2];
=======
    BlockInfo info = (BlockInfo) triplets[index*3+2];
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    triplets[index*3+2] = to;
    return info;
  }

  public int getCapacity() {
    assert this.triplets != null : "BlockInfo is not initialized";
    assert triplets.length % 3 == 0 : "Malformed BlockInfo";
    return triplets.length / 3;
  }

  /**
<<<<<<< HEAD
   * Count the number of data-nodes the block belongs to.
=======
   * Count the number of data-nodes the block currently belongs to (i.e., NN
   * has received block reports from the DN).
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
   */
  public abstract int numNodes();

  /**
<<<<<<< HEAD
   * Add a {@link DatanodeStorageInfo} location for a block.
   */
  abstract boolean addStorage(DatanodeStorageInfo storage);
=======
   * Add a {@link DatanodeStorageInfo} location for a block
   * @param storage The storage to add
   * @param reportedBlock The block reported from the datanode. This is only
   *                      used by erasure coded blocks, this block's id contains
   *                      information indicating the index of the block in the
   *                      corresponding block group.
   */
  abstract boolean addStorage(DatanodeStorageInfo storage, Block reportedBlock);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  /**
   * Remove {@link DatanodeStorageInfo} location for a block
   */
  abstract boolean removeStorage(DatanodeStorageInfo storage);

<<<<<<< HEAD

  /**
   * Replace the current BlockInfo with the new one in corresponding
   * DatanodeStorageInfo's linked list
   */
  abstract void replaceBlock(BlockInfo newBlock);

  /**
   * Find specified DatanodeStorageInfo.
   * @return DatanodeStorageInfo or null if not found.
   */
=======
  public abstract boolean isStriped();

  /** @return true if there is no datanode storage associated with the block */
  abstract boolean hasNoStorage();

  /**
   * Find specified DatanodeStorageInfo.
   * @return DatanodeStorageInfo or null if not found.
   */
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  DatanodeStorageInfo findStorageInfo(DatanodeDescriptor dn) {
    int len = getCapacity();
    for(int idx = 0; idx < len; idx++) {
      DatanodeStorageInfo cur = getStorageInfo(idx);
<<<<<<< HEAD
      if(cur == null)
        break;
      if(cur.getDatanodeDescriptor() == dn)
        return cur;
=======
      if(cur != null && cur.getDatanodeDescriptor() == dn) {
        return cur;
      }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
    return null;
  }

  /**
   * Find specified DatanodeStorageInfo.
   * @return index or -1 if not found.
   */
  int findStorageInfo(DatanodeStorageInfo storageInfo) {
    int len = getCapacity();
    for(int idx = 0; idx < len; idx++) {
      DatanodeStorageInfo cur = getStorageInfo(idx);
      if (cur == storageInfo) {
        return idx;
      }
<<<<<<< HEAD
      if (cur == null) {
        break;
      }
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
    return -1;
  }

  /**
   * Insert this block into the head of the list of blocks
   * related to the specified DatanodeStorageInfo.
   * If the head is null then form a new list.
   * @return current block as the new head of the list.
   */
<<<<<<< HEAD
  BlockInfo listInsert(BlockInfo head,
      DatanodeStorageInfo storage) {
    int dnIndex = this.findStorageInfo(storage);
    assert dnIndex >= 0 : "Data node is not found: current";
    assert getPrevious(dnIndex) == null && getNext(dnIndex) == null :
            "Block is already in the list and cannot be inserted.";
    this.setPrevious(dnIndex, null);
    this.setNext(dnIndex, head);
    if(head != null)
      head.setPrevious(head.findStorageInfo(storage), this);
=======
  BlockInfo listInsert(BlockInfo head, DatanodeStorageInfo storage) {
    int dnIndex = this.findStorageInfo(storage);
    assert dnIndex >= 0 : "Data node is not found: current";
    assert getPrevious(dnIndex) == null && getNext(dnIndex) == null :
        "Block is already in the list and cannot be inserted.";
    this.setPrevious(dnIndex, null);
    this.setNext(dnIndex, head);
    if (head != null) {
      head.setPrevious(head.findStorageInfo(storage), this);
    }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    return this;
  }

  /**
   * Remove this block from the list of blocks
   * related to the specified DatanodeStorageInfo.
   * If this block is the head of the list then return the next block as
   * the new head.
   * @return the new head of the list or null if the list becomes
   * empy after deletion.
   */
<<<<<<< HEAD
  BlockInfo listRemove(BlockInfo head,
      DatanodeStorageInfo storage) {
    if(head == null)
      return null;
    int dnIndex = this.findStorageInfo(storage);
    if(dnIndex < 0) // this block is not on the data-node list
=======
  BlockInfo listRemove(BlockInfo head, DatanodeStorageInfo storage) {
    if (head == null) {
      return null;
    }
    int dnIndex = this.findStorageInfo(storage);
    if (dnIndex < 0) { // this block is not on the data-node list
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      return head;
    }

    BlockInfo next = this.getNext(dnIndex);
    BlockInfo prev = this.getPrevious(dnIndex);
    this.setNext(dnIndex, null);
    this.setPrevious(dnIndex, null);
<<<<<<< HEAD
    if(prev != null)
      prev.setNext(prev.findStorageInfo(storage), next);
    if(next != null)
      next.setPrevious(next.findStorageInfo(storage), prev);
    if(this == head)  // removing the head
=======
    if (prev != null) {
      prev.setNext(prev.findStorageInfo(storage), next);
    }
    if (next != null) {
      next.setPrevious(next.findStorageInfo(storage), prev);
    }
    if (this == head) { // removing the head
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      head = next;
    }
    return head;
  }

  /**
   * Remove this block from the list of blocks related to the specified
   * DatanodeDescriptor. Insert it into the head of the list of blocks.
   *
   * @return the new head of the list.
<<<<<<< HEAD
   */
  public BlockInfo moveBlockToHead(BlockInfo head,
      DatanodeStorageInfo storage, int curIndex, int headIndex) {
    if (head == this) {
      return this;
    }
    BlockInfo next = this.setNext(curIndex, head);
    BlockInfo prev = this.setPrevious(curIndex, null);

    head.setPrevious(headIndex, this);
    prev.setNext(prev.findStorageInfo(storage), next);
    if (next != null) {
      next.setPrevious(next.findStorageInfo(storage), prev);
    }
    return this;
  }

  /**
   * BlockInfo represents a block that is not being constructed.
   * In order to start modifying the block, the BlockInfo should be converted
   * to {@link BlockInfoContiguousUnderConstruction}.
   * @return {@link BlockUCState#COMPLETE}
   */
  public BlockUCState getBlockUCState() {
    return BlockUCState.COMPLETE;
  }

  /**
   * Is this block complete?
   *
   * @return true if the state of the block is {@link BlockUCState#COMPLETE}
   */
  public boolean isComplete() {
    return getBlockUCState().equals(BlockUCState.COMPLETE);
  }

  /**
   * Convert a complete block to an under construction block.
   * @return BlockInfoUnderConstruction -  an under construction block.
   */
  public BlockInfoContiguousUnderConstruction convertToBlockUnderConstruction(
      BlockUCState s, DatanodeStorageInfo[] targets) {
    if(isComplete()) {
      BlockInfoContiguousUnderConstruction ucBlock =
          new BlockInfoContiguousUnderConstruction(this,
          getBlockCollection().getPreferredBlockReplication(), s, targets);
      ucBlock.setBlockCollection(getBlockCollection());
      return ucBlock;
    }
    // the block is already under construction
    BlockInfoContiguousUnderConstruction ucBlock =
        (BlockInfoContiguousUnderConstruction)this;
    ucBlock.setBlockUCState(s);
    ucBlock.setExpectedLocations(targets);
    ucBlock.setBlockCollection(getBlockCollection());
    return ucBlock;
=======
   */
  public BlockInfo moveBlockToHead(BlockInfo head, DatanodeStorageInfo storage,
      int curIndex, int headIndex) {
    if (head == this) {
      return this;
    }
    BlockInfo next = this.setNext(curIndex, head);
    BlockInfo prev = this.setPrevious(curIndex, null);

    head.setPrevious(headIndex, this);
    prev.setNext(prev.findStorageInfo(storage), next);
    if (next != null) {
      next.setPrevious(next.findStorageInfo(storage), prev);
    }
    return this;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @Override
  public int hashCode() {
    // Super implementation is sufficient
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    // Sufficient to rely on super's implementation
    return (this == obj) || super.equals(obj);
  }

  @Override
  public LightWeightGSet.LinkedElement getNext() {
    return nextLinkedElement;
  }

  @Override
  public void setNext(LightWeightGSet.LinkedElement next) {
    this.nextLinkedElement = next;
  }
<<<<<<< HEAD
=======

  /* UnderConstruction Feature related */

  public BlockUnderConstructionFeature getUnderConstructionFeature() {
    return uc;
  }

  public BlockUCState getBlockUCState() {
    return uc == null ? BlockUCState.COMPLETE : uc.getBlockUCState();
  }

  /**
   * Is this block complete?
   *
   * @return true if the state of the block is {@link BlockUCState#COMPLETE}
   */
  public boolean isComplete() {
    return getBlockUCState().equals(BlockUCState.COMPLETE);
  }

  /**
   * Add/Update the under construction feature.
   */
  public void convertToBlockUnderConstruction(BlockUCState s,
      DatanodeStorageInfo[] targets) {
    if (isComplete()) {
      uc = new BlockUnderConstructionFeature(this, s, targets,
          this.isStriped());
    } else {
      // the block is already under construction
      uc.setBlockUCState(s);
      uc.setExpectedLocations(this, targets, this.isStriped());
    }
  }

  /**
   * Convert an under construction block to complete.
   */
  void convertToCompleteBlock() {
    assert getBlockUCState() != BlockUCState.COMPLETE :
        "Trying to convert a COMPLETE block";
    uc = null;
  }

  /**
   * Process the recorded replicas. When about to commit or finish the
   * pipeline recovery sort out bad replicas.
   * @param genStamp  The final generation stamp for the block.
   */
  public void setGenerationStampAndVerifyReplicas(long genStamp) {
    Preconditions.checkState(uc != null && !isComplete());
    // Set the generation stamp for the block.
    setGenerationStamp(genStamp);

    // Remove the replicas with wrong gen stamp
    List<ReplicaUnderConstruction> staleReplicas = uc.getStaleReplicas(genStamp);
    for (ReplicaUnderConstruction r : staleReplicas) {
      r.getExpectedStorageLocation().removeBlock(this);
      NameNode.blockStateChangeLog.debug("BLOCK* Removing stale replica {}"
          + " of {}", r, Block.toString(r));
    }
  }

  /**
   * Commit block's length and generation stamp as reported by the client.
   * Set block state to {@link BlockUCState#COMMITTED}.
   * @param block - contains client reported block length and generation
   * @throws IOException if block ids are inconsistent.
   */
  void commitBlock(Block block) throws IOException {
    if (getBlockId() != block.getBlockId()) {
      throw new IOException("Trying to commit inconsistent block: id = "
          + block.getBlockId() + ", expected id = " + getBlockId());
    }
    Preconditions.checkState(!isComplete());
    uc.commit();
    this.setNumBytes(block.getNumBytes());
    // Sort out invalid replicas.
    setGenerationStampAndVerifyReplicas(block.getGenerationStamp());
  }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
}
