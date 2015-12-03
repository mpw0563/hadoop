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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
<<<<<<< HEAD
import org.apache.hadoop.hdfs.util.LightWeightLinkedSet;
=======

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.hdfs.server.namenode.NameNode;
import org.apache.hadoop.hdfs.util.LightWeightLinkedSet;

/**
 * Keep prioritized queues of under replicated blocks.
 * Blocks have replication priority, with priority {@link #QUEUE_HIGHEST_PRIORITY}
 * indicating the highest priority.
 * </p>
 * Having a prioritised queue allows the {@link BlockManager} to select
 * which blocks to replicate first -it tries to give priority to data
 * that is most at risk or considered most valuable.
 *
 * <p/>
 * The policy for choosing which priority to give added blocks
<<<<<<< HEAD
 * is implemented in {@link #getPriority(int, int, int)}.
=======
 * is implemented in {@link #getPriority(BlockInfo, int, int, int, int)}.
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
 * </p>
 * <p>The queue order is as follows:</p>
 * <ol>
 *   <li>{@link #QUEUE_HIGHEST_PRIORITY}: the blocks that must be replicated
 *   first. That is blocks with only one copy, or blocks with zero live
 *   copies but a copy in a node being decommissioned. These blocks
 *   are at risk of loss if the disk or server on which they
 *   remain fails.</li>
 *   <li>{@link #QUEUE_VERY_UNDER_REPLICATED}: blocks that are very
 *   under-replicated compared to their expected values. Currently
 *   that means the ratio of the ratio of actual:expected means that
 *   there is <i>less than</i> 1:3.</li>. These blocks may not be at risk,
 *   but they are clearly considered "important".
 *   <li>{@link #QUEUE_UNDER_REPLICATED}: blocks that are also under
 *   replicated, and the ratio of actual:expected is good enough that
 *   they do not need to go into the {@link #QUEUE_VERY_UNDER_REPLICATED}
 *   queue.</li>
 *   <li>{@link #QUEUE_REPLICAS_BADLY_DISTRIBUTED}: there are as least as
 *   many copies of a block as required, but the blocks are not adequately
 *   distributed. Loss of a rack/switch could take all copies off-line.</li>
 *   <li>{@link #QUEUE_WITH_CORRUPT_BLOCKS} This is for blocks that are corrupt
 *   and for which there are no-non-corrupt copies (currently) available.
 *   The policy here is to keep those corrupt blocks replicated, but give
 *   blocks that are not corrupt higher priority.</li>
 * </ol>
 */
class UnderReplicatedBlocks implements Iterable<BlockInfo> {
  /** The total number of queues : {@value} */
  static final int LEVEL = 5;
  /** The queue with the highest priority: {@value} */
  static final int QUEUE_HIGHEST_PRIORITY = 0;
  /** The queue for blocks that are way below their expected value : {@value} */
  static final int QUEUE_VERY_UNDER_REPLICATED = 1;
  /** The queue for "normally" under-replicated blocks: {@value} */
  static final int QUEUE_UNDER_REPLICATED = 2;
  /** The queue for blocks that have the right number of replicas,
   * but which the block manager felt were badly distributed: {@value}
   */
  static final int QUEUE_REPLICAS_BADLY_DISTRIBUTED = 3;
  /** The queue for corrupt blocks: {@value} */
  static final int QUEUE_WITH_CORRUPT_BLOCKS = 4;
  /** the queues themselves */
  private final List<LightWeightLinkedSet<BlockInfo>> priorityQueues
      = new ArrayList<>(LEVEL);

  /** The number of corrupt blocks with replication factor 1 */
  private int corruptReplOneBlocks = 0;

  /** Create an object. */
  UnderReplicatedBlocks() {
    for (int i = 0; i < LEVEL; i++) {
      priorityQueues.add(new LightWeightLinkedSet<BlockInfo>());
    }
  }

  /**
   * Empty the queues.
   */
  synchronized void clear() {
    for (int i = 0; i < LEVEL; i++) {
      priorityQueues.get(i).clear();
    }
    corruptReplOneBlocks = 0;
  }

  /** Return the total number of under replication blocks */
  synchronized int size() {
    int size = 0;
    for (int i = 0; i < LEVEL; i++) {
      size += priorityQueues.get(i).size();
    }
    return size;
  }

  /** Return the number of under replication blocks excluding corrupt blocks */
  synchronized int getUnderReplicatedBlockCount() {
    int size = 0;
    for (int i = 0; i < LEVEL; i++) {
      if (i != QUEUE_WITH_CORRUPT_BLOCKS) {
        size += priorityQueues.get(i).size();
      }
    }
    return size;
  }

  /** Return the number of corrupt blocks */
  synchronized int getCorruptBlockSize() {
    return priorityQueues.get(QUEUE_WITH_CORRUPT_BLOCKS).size();
  }

  /** Return the number of corrupt blocks with replication factor 1 */
  synchronized int getCorruptReplOneBlockSize() {
    return corruptReplOneBlocks;
  }

  /** Check if a block is in the neededReplication queue */
  synchronized boolean contains(BlockInfo block) {
    for(LightWeightLinkedSet<BlockInfo> set : priorityQueues) {
      if (set.contains(block)) {
        return true;
      }
    }
    return false;
  }

  /** Return the priority of a block
   * @param curReplicas current number of replicas of the block
   * @param expectedReplicas expected number of replicas of the block
   * @return the priority for the blocks, between 0 and ({@link #LEVEL}-1)
   */
<<<<<<< HEAD
  private int getPriority(int curReplicas,
=======
  private int getPriority(BlockInfo block,
                          int curReplicas,
                          int readOnlyReplicas,
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
                          int decommissionedReplicas,
                          int expectedReplicas) {
    assert curReplicas >= 0 : "Negative replicas!";
    if (curReplicas >= expectedReplicas) {
      // Block has enough copies, but not enough racks
      return QUEUE_REPLICAS_BADLY_DISTRIBUTED;
<<<<<<< HEAD
    } else if (curReplicas == 0) {
=======
    }
    if (block.isStriped()) {
      BlockInfoStriped sblk = (BlockInfoStriped) block;
      return getPriorityStriped(curReplicas, decommissionedReplicas,
          sblk.getRealDataBlockNum(), sblk.getParityBlockNum());
    } else {
      return getPriorityContiguous(curReplicas, readOnlyReplicas,
          decommissionedReplicas, expectedReplicas);
    }
  }

  private int getPriorityContiguous(int curReplicas, int readOnlyReplicas,
      int decommissionedReplicas, int expectedReplicas) {
    if (curReplicas == 0) {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      // If there are zero non-decommissioned replicas but there are
      // some decommissioned replicas, then assign them highest priority
      if (decommissionedReplicas > 0) {
        return QUEUE_HIGHEST_PRIORITY;
<<<<<<< HEAD
=======
      }
      if (readOnlyReplicas > 0) {
        // only has read-only replicas, highest risk
        // since the read-only replicas may go down all together.
        return QUEUE_HIGHEST_PRIORITY;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      }
      //all we have are corrupt blocks
      return QUEUE_WITH_CORRUPT_BLOCKS;
    } else if (curReplicas == 1) {
<<<<<<< HEAD
      //only on replica -risk of loss
=======
      // only one replica, highest risk of loss
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      // highest priority
      return QUEUE_HIGHEST_PRIORITY;
    } else if ((curReplicas * 3) < expectedReplicas) {
      //there is less than a third as many blocks as requested;
      //this is considered very under-replicated
      return QUEUE_VERY_UNDER_REPLICATED;
    } else {
      //add to the normal queue for under replicated blocks
      return QUEUE_UNDER_REPLICATED;
    }
  }

<<<<<<< HEAD
=======
  private int getPriorityStriped(int curReplicas, int decommissionedReplicas,
      short dataBlkNum, short parityBlkNum) {
    if (curReplicas < dataBlkNum) {
      // There are some replicas on decommissioned nodes so it's not corrupted
      if (curReplicas + decommissionedReplicas >= dataBlkNum) {
        return QUEUE_HIGHEST_PRIORITY;
      }
      return QUEUE_WITH_CORRUPT_BLOCKS;
    } else if (curReplicas == dataBlkNum) {
      // highest risk of loss, highest priority
      return QUEUE_HIGHEST_PRIORITY;
    } else if ((curReplicas - dataBlkNum) * 3 < parityBlkNum + 1) {
      // can only afford one replica loss
      // this is considered very under-replicated
      return QUEUE_VERY_UNDER_REPLICATED;
    } else {
      // add to the normal queue for under replicated blocks
      return QUEUE_UNDER_REPLICATED;
    }
  }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /** add a block to a under replication queue according to its priority
   * @param block a under replication block
   * @param curReplicas current number of replicas of the block
   * @param decomissionedReplicas the number of decommissioned replicas
   * @param expectedReplicas expected number of replicas of the block
   * @return true if the block was added to a queue.
   */
  synchronized boolean add(BlockInfo block,
                           int curReplicas,
<<<<<<< HEAD
                           int decomissionedReplicas,
                           int expectedReplicas) {
    assert curReplicas >= 0 : "Negative replicas!";
    int priLevel = getPriority(curReplicas, decomissionedReplicas,
                               expectedReplicas);
=======
                           int readOnlyReplicas,
                           int decomissionedReplicas,
                           int expectedReplicas) {
    assert curReplicas >= 0 : "Negative replicas!";
    final int priLevel = getPriority(block, curReplicas, readOnlyReplicas,
        decomissionedReplicas, expectedReplicas);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    if(priorityQueues.get(priLevel).add(block)) {
      if (priLevel == QUEUE_WITH_CORRUPT_BLOCKS &&
          expectedReplicas == 1) {
        corruptReplOneBlocks++;
      }
      NameNode.blockStateChangeLog.debug(
          "BLOCK* NameSystem.UnderReplicationBlock.add: {}"
              + " has only {} replicas and need {} replicas so is added to" +
              " neededReplications at priority level {}", block, curReplicas,
          expectedReplicas, priLevel);

      return true;
    }
    return false;
  }

  /** remove a block from a under replication queue */
  synchronized boolean remove(BlockInfo block,
                              int oldReplicas,
<<<<<<< HEAD
                              int decommissionedReplicas,
                              int oldExpectedReplicas) {
    int priLevel = getPriority(oldReplicas,
                               decommissionedReplicas,
                               oldExpectedReplicas);
=======
                              int oldReadOnlyReplicas,
                              int decommissionedReplicas,
                              int oldExpectedReplicas) {
    final int priLevel = getPriority(block, oldReplicas, oldReadOnlyReplicas,
        decommissionedReplicas, oldExpectedReplicas);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    boolean removedBlock = remove(block, priLevel);
    if (priLevel == QUEUE_WITH_CORRUPT_BLOCKS &&
        oldExpectedReplicas == 1 &&
        removedBlock) {
      corruptReplOneBlocks--;
      assert corruptReplOneBlocks >= 0 :
          "Number of corrupt blocks with replication factor 1 " +
              "should be non-negative";
    }
    return removedBlock;
  }

  /**
   * Remove a block from the under replication queues.
   *
   * The priLevel parameter is a hint of which queue to query
   * first: if negative or &gt;= {@link #LEVEL} this shortcutting
   * is not attmpted.
   *
   * If the block is not found in the nominated queue, an attempt is made to
   * remove it from all queues.
   *
   * <i>Warning:</i> This is not a synchronized method.
   * @param block block to remove
   * @param priLevel expected privilege level
   * @return true if the block was found and removed from one of the priority queues
   */
  boolean remove(BlockInfo block, int priLevel) {
    if(priLevel >= 0 && priLevel < LEVEL
        && priorityQueues.get(priLevel).remove(block)) {
      NameNode.blockStateChangeLog.debug(
        "BLOCK* NameSystem.UnderReplicationBlock.remove: Removing block {}" +
            " from priority queue {}", block, priLevel);
      return true;
    } else {
      // Try to remove the block from all queues if the block was
      // not found in the queue for the given priority level.
      for (int i = 0; i < LEVEL; i++) {
<<<<<<< HEAD
        if (priorityQueues.get(i).remove(block)) {
          NameNode.blockStateChangeLog.debug(
              "BLOCK* NameSystem.UnderReplicationBlock.remove: Removing block" +
                  " {} from priority queue {}", block, priLevel);
=======
        if (i != priLevel && priorityQueues.get(i).remove(block)) {
          NameNode.blockStateChangeLog.debug(
              "BLOCK* NameSystem.UnderReplicationBlock.remove: Removing block" +
                  " {} from priority queue {}", block, i);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Recalculate and potentially update the priority level of a block.
   *
   * If the block priority has changed from before an attempt is made to
   * remove it from the block queue. Regardless of whether or not the block
   * is in the block queue of (recalculate) priority, an attempt is made
   * to add it to that queue. This ensures that the block will be
   * in its expected priority queue (and only that queue) by the end of the
   * method call.
   * @param block a under replicated block
   * @param curReplicas current number of replicas of the block
   * @param decommissionedReplicas  the number of decommissioned replicas
   * @param curExpectedReplicas expected number of replicas of the block
   * @param curReplicasDelta the change in the replicate count from before
   * @param expectedReplicasDelta the change in the expected replica count from before
   */
  synchronized void update(BlockInfo block, int curReplicas,
<<<<<<< HEAD
                           int decommissionedReplicas,
=======
                           int readOnlyReplicas, int decommissionedReplicas,
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
                           int curExpectedReplicas,
                           int curReplicasDelta, int expectedReplicasDelta) {
    int oldReplicas = curReplicas-curReplicasDelta;
    int oldExpectedReplicas = curExpectedReplicas-expectedReplicasDelta;
<<<<<<< HEAD
    int curPri = getPriority(curReplicas, decommissionedReplicas,
        curExpectedReplicas);
    int oldPri = getPriority(oldReplicas, decommissionedReplicas,
        oldExpectedReplicas);
=======
    int curPri = getPriority(block, curReplicas, readOnlyReplicas,
        decommissionedReplicas, curExpectedReplicas);
    int oldPri = getPriority(block, oldReplicas, readOnlyReplicas,
        decommissionedReplicas, oldExpectedReplicas);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    if(NameNode.stateChangeLog.isDebugEnabled()) {
      NameNode.stateChangeLog.debug("UnderReplicationBlocks.update " + 
        block +
        " curReplicas " + curReplicas +
        " curExpectedReplicas " + curExpectedReplicas +
        " oldReplicas " + oldReplicas +
        " oldExpectedReplicas  " + oldExpectedReplicas +
        " curPri  " + curPri +
        " oldPri  " + oldPri);
    }
    if(oldPri != curPri) {
      remove(block, oldPri);
    }
    if(priorityQueues.get(curPri).add(block)) {
      NameNode.blockStateChangeLog.debug(
          "BLOCK* NameSystem.UnderReplicationBlock.update: {} has only {} " +
              "replicas and needs {} replicas so is added to " +
              "neededReplications at priority level {}", block, curReplicas,
          curExpectedReplicas, curPri);

    }
    if (oldPri != curPri || expectedReplicasDelta != 0) {
      // corruptReplOneBlocks could possibly change
      if (curPri == QUEUE_WITH_CORRUPT_BLOCKS &&
          curExpectedReplicas == 1) {
        // add a new corrupt block with replication factor 1
        corruptReplOneBlocks++;
      } else if (oldPri == QUEUE_WITH_CORRUPT_BLOCKS &&
          curExpectedReplicas - expectedReplicasDelta == 1) {
        // remove an existing corrupt block with replication factor 1
        corruptReplOneBlocks--;
<<<<<<< HEAD
      }
    }
  }

  /**
   * Get a list of block lists to be replicated. The index of block lists
   * represents its replication priority. Iterates each block list in priority
   * order beginning with the highest priority list. Iterators use a bookmark to
   * resume where the previous iteration stopped. Returns when the block count
   * is met or iteration reaches the end of the lowest priority list, in which
   * case bookmarks for each block list are reset to the heads of their
   * respective lists.
   *
   * @param blocksToProcess - number of blocks to fetch from underReplicated
   *                        blocks.
   * @return Return a list of block lists to be replicated. The block list index
   *         represents its replication priority.
   */
  public synchronized List<List<BlockInfo>> chooseUnderReplicatedBlocks(
      int blocksToProcess) {
    // initialize data structure for the return value
    List<List<BlockInfo>> blocksToReplicate = new ArrayList<>(LEVEL);
    for (int i = 0; i < LEVEL; i++) {
      blocksToReplicate.add(new ArrayList<BlockInfo>());
    }

    if (size() == 0) { // There are no blocks to collect.
      return blocksToReplicate;
    }
    
    int blockCount = 0;
    for (int priority = 0; priority < LEVEL; priority++) { 
      // Go through all blocks that need replications with current priority.
      BlockIterator neededReplicationsIterator = iterator(priority);
      // Set the iterator to the first unprocessed block at this priority level.
       neededReplicationsIterator.setToBookmark();

      blocksToProcess = Math.min(blocksToProcess, size());
      
      if (blockCount == blocksToProcess) {
        break;  // break if already expected blocks are obtained
      }
      
      // Loop through all remaining blocks in the list.
      while (blockCount < blocksToProcess
          && neededReplicationsIterator.hasNext()) {
        BlockInfo block = neededReplicationsIterator.next();
        blocksToReplicate.get(priority).add(block);
        blockCount++;
      }
      
      if (!neededReplicationsIterator.hasNext()
          && neededReplicationsIterator.getPriority() == LEVEL - 1) {
        // Reset all priorities' bookmarks to the beginning because there were
        // no recently added blocks in any list.
        for (int i = 0; i < LEVEL; i++) {
          this.priorityQueues.get(i).resetBookmark();
        }
        break;
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      }
    }
    return blocksToReplicate;
  }
<<<<<<< HEAD

  /** returns an iterator of all blocks in a given priority queue */
  synchronized BlockIterator iterator(int level) {
    return new BlockIterator(level);
  }

  /** return an iterator of all the under replication blocks */
  @Override
  public synchronized BlockIterator iterator() {
    return new BlockIterator();
  }

  /**
   * An iterator over blocks.
   */
  class BlockIterator implements Iterator<BlockInfo> {
    private int level;
    private boolean isIteratorForLevel = false;
    private final List<Iterator<BlockInfo>> iterators = new ArrayList<>();

    /**
     * Construct an iterator over all queues.
     */
    private BlockIterator() {
      level=0;
      for(int i=0; i<LEVEL; i++) {
        iterators.add(priorityQueues.get(i).iterator());
=======
  
  /**
   * Get a list of block lists to be replicated. The index of block lists
   * represents its replication priority. Iterates each block list in priority
   * order beginning with the highest priority list. Iterators use a bookmark to
   * resume where the previous iteration stopped. Returns when the block count
   * is met or iteration reaches the end of the lowest priority list, in which
   * case bookmarks for each block list are reset to the heads of their
   * respective lists.
   *
   * @param blocksToProcess - number of blocks to fetch from underReplicated
   *          blocks.
   * @return Return a list of block lists to be replicated. The block list index
   *         represents its replication priority.
   */
  synchronized List<List<BlockInfo>> chooseUnderReplicatedBlocks(
      int blocksToProcess) {
    final List<List<BlockInfo>> blocksToReplicate = new ArrayList<>(LEVEL);
    
    int count = 0;
    int priority = 0;
    for (; count < blocksToProcess && priority < LEVEL; priority++) {
      if (priority == QUEUE_WITH_CORRUPT_BLOCKS) {
        // do not choose corrupted blocks.
        continue;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      }

<<<<<<< HEAD
    /**
     * Constrict an iterator for a single queue level
     * @param l the priority level to iterate over
     */
    private BlockIterator(int l) {
      level = l;
      isIteratorForLevel = true;
      iterators.add(priorityQueues.get(level).iterator());
    }

    private void update() {
      if (isIteratorForLevel) {
        return;
      }
      while(level< LEVEL-1 && !iterators.get(level).hasNext()) {
        level++;
      }
    }

    @Override
    public BlockInfo next() {
      if (isIteratorForLevel) {
        return iterators.get(0).next();
      }
      update();
      return iterators.get(level).next();
    }

    @Override
    public boolean hasNext() {
      if (isIteratorForLevel) {
        return iterators.get(0).hasNext();
      }
      update();
      return iterators.get(level).hasNext();
    }

    @Override
    public void remove() {
      if (isIteratorForLevel) {
        iterators.get(0).remove();
      } else {
        iterators.get(level).remove();
      }
    }

    int getPriority() {
      return level;
    }

    /**
     * Sets iterator(s) to bookmarked elements.
     */
    private synchronized void setToBookmark() {
      if (this.isIteratorForLevel) {
        this.iterators.set(0, priorityQueues.get(this.level)
            .getBookmark());
      } else {
        for (int i = 0; i < LEVEL; i++) {
          this.iterators.set(i, priorityQueues.get(i).getBookmark());
        }
      }
    }
=======
      // Go through all blocks that need replications with current priority.
      // Set the iterator to the first unprocessed block at this priority level.
      final Iterator<BlockInfo> i = priorityQueues.get(priority).getBookmark();
      final List<BlockInfo> blocks = new LinkedList<>();
      blocksToReplicate.add(blocks);
      // Loop through all remaining blocks in the list.
      for(; count < blocksToProcess && i.hasNext(); count++) {
        blocks.add(i.next());
      }
    }

    if (priority == LEVEL) {
      // Reset all bookmarks because there were no recently added blocks.
      for (LightWeightLinkedSet<BlockInfo> q : priorityQueues) {
        q.resetBookmark();
      }
    }

    return blocksToReplicate;
  }

  /** returns an iterator of all blocks in a given priority queue */
  synchronized Iterator<BlockInfo> iterator(int level) {
    return priorityQueues.get(level).iterator();
  }

  /** return an iterator of all the under replication blocks */
  @Override
  public synchronized Iterator<BlockInfo> iterator() {
    final Iterator<LightWeightLinkedSet<BlockInfo>> q = priorityQueues.iterator();
    return new Iterator<BlockInfo>() {
      private Iterator<BlockInfo> b = q.next().iterator();

      @Override
      public BlockInfo next() {
        hasNext();
        return b.next();
      }

      @Override
      public boolean hasNext() {
        for(; !b.hasNext() && q.hasNext(); ) {
          b = q.next().iterator();
        }
        return b.hasNext();
      }

      @Override
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }
}
