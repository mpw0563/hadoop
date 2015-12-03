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

package org.apache.hadoop.yarn.server.resourcemanager.scheduler.common.fica;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.yarn.api.records.ApplicationAttemptId;
import org.apache.hadoop.yarn.api.records.Container;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.ContainerStatus;
<<<<<<< HEAD
=======
import org.apache.hadoop.yarn.api.records.NMToken;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.yarn.api.records.NodeId;
import org.apache.hadoop.yarn.api.records.Priority;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.ResourceRequest;
<<<<<<< HEAD
=======
import org.apache.hadoop.yarn.nodelabels.CommonNodeLabelsManager;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.yarn.server.resourcemanager.RMAuditLogger;
import org.apache.hadoop.yarn.server.resourcemanager.RMAuditLogger.AuditConstants;
import org.apache.hadoop.yarn.server.resourcemanager.RMContext;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.RMApp;
import org.apache.hadoop.yarn.server.resourcemanager.rmcontainer.RMContainer;
import org.apache.hadoop.yarn.server.resourcemanager.rmcontainer.RMContainerEvent;
import org.apache.hadoop.yarn.server.resourcemanager.rmcontainer.RMContainerEventType;
import org.apache.hadoop.yarn.server.resourcemanager.rmcontainer.RMContainerFinishedEvent;
import org.apache.hadoop.yarn.server.resourcemanager.rmcontainer.RMContainerImpl;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.ActiveUsersManager;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.Allocation;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.NodeType;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.Queue;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.ResourceLimits;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.ResourceScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.SchedulerApplicationAttempt;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CSAssignment;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityHeadroomProvider;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.CapacityScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.LeafQueue;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.SchedulingMode;
<<<<<<< HEAD
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.allocator.AllocationState;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.allocator.ContainerAllocator;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.allocator.RegularContainerAllocator;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.allocator.ContainerAllocation;
=======
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.allocator.ContainerAllocator;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity.allocator.AbstractContainerAllocator;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.yarn.util.resource.DefaultResourceCalculator;
import org.apache.hadoop.yarn.util.resource.ResourceCalculator;
import org.apache.hadoop.yarn.util.resource.Resources;

import com.google.common.annotations.VisibleForTesting;

/**
 * Represents an application attempt from the viewpoint of the FIFO or Capacity
 * scheduler.
 */
@Private
@Unstable
public class FiCaSchedulerApp extends SchedulerApplicationAttempt {
  private static final Log LOG = LogFactory.getLog(FiCaSchedulerApp.class);

  private final Set<ContainerId> containersToPreempt =
    new HashSet<ContainerId>();
    
  private CapacityHeadroomProvider headroomProvider;

  private ResourceCalculator rc = new DefaultResourceCalculator();

  private ResourceScheduler scheduler;
  
<<<<<<< HEAD
  private ContainerAllocator containerAllocator;
=======
  private AbstractContainerAllocator containerAllocator;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  public FiCaSchedulerApp(ApplicationAttemptId applicationAttemptId, 
      String user, Queue queue, ActiveUsersManager activeUsersManager,
      RMContext rmContext) {
    this(applicationAttemptId, user, queue, activeUsersManager, rmContext,
        Priority.newInstance(0));
  }

  public FiCaSchedulerApp(ApplicationAttemptId applicationAttemptId,
      String user, Queue queue, ActiveUsersManager activeUsersManager,
      RMContext rmContext, Priority appPriority) {
    super(applicationAttemptId, user, queue, activeUsersManager, rmContext);
    
    RMApp rmApp = rmContext.getRMApps().get(getApplicationId());
<<<<<<< HEAD
    
    Resource amResource;
    if (rmApp == null || rmApp.getAMResourceRequest() == null) {
      //the rmApp may be undefined (the resource manager checks for this too)
      //and unmanaged applications do not provide an amResource request
      //in these cases, provide a default using the scheduler
      amResource = rmContext.getScheduler().getMinimumResourceCapability();
    } else {
      amResource = rmApp.getAMResourceRequest().getCapability();
    }
    
    setAMResource(amResource);
=======

    Resource amResource;
    String partition;

    if (rmApp == null || rmApp.getAMResourceRequest() == null) {
      // the rmApp may be undefined (the resource manager checks for this too)
      // and unmanaged applications do not provide an amResource request
      // in these cases, provide a default using the scheduler
      amResource = rmContext.getScheduler().getMinimumResourceCapability();
      partition = CommonNodeLabelsManager.NO_LABEL;
    } else {
      amResource = rmApp.getAMResourceRequest().getCapability();
      partition =
          (rmApp.getAMResourceRequest().getNodeLabelExpression() == null)
          ? CommonNodeLabelsManager.NO_LABEL
          : rmApp.getAMResourceRequest().getNodeLabelExpression();
    }

    setAppAMNodePartitionName(partition);
    setAMResource(partition, amResource);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    setPriority(appPriority);

    scheduler = rmContext.getScheduler();

    if (scheduler.getResourceCalculator() != null) {
      rc = scheduler.getResourceCalculator();
    }
    
<<<<<<< HEAD
    containerAllocator = new RegularContainerAllocator(this, rc, rmContext);
=======
    containerAllocator = new ContainerAllocator(this, rc, rmContext);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  synchronized public boolean containerCompleted(RMContainer rmContainer,
      ContainerStatus containerStatus, RMContainerEventType event,
      String partition) {

    // Remove from the list of containers
    if (null == liveContainers.remove(rmContainer.getContainerId())) {
      return false;
    }
    
    // Remove from the list of newly allocated containers if found
    newlyAllocatedContainers.remove(rmContainer);

    Container container = rmContainer.getContainer();
    ContainerId containerId = container.getId();

    // Inform the container
    rmContainer.handle(
<<<<<<< HEAD
        new RMContainerFinishedEvent(
            containerId,
            containerStatus, 
            event)
        );
    LOG.info("Completed container: " + rmContainer.getContainerId() + 
        " in state: " + rmContainer.getState() + " event:" + event);

    containersToPreempt.remove(rmContainer.getContainerId());

    RMAuditLogger.logSuccess(getUser(), 
        AuditConstants.RELEASE_CONTAINER, "SchedulerApp", 
=======
        new RMContainerFinishedEvent(containerId, containerStatus, event));

    containersToPreempt.remove(rmContainer.getContainerId());

    RMAuditLogger.logSuccess(getUser(),
        AuditConstants.RELEASE_CONTAINER, "SchedulerApp",
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
        getApplicationId(), containerId);
    
    // Update usage metrics 
    Resource containerResource = rmContainer.getContainer().getResource();
    queue.getMetrics().releaseResources(getUser(), 1, containerResource);
    attemptResourceUsage.decUsed(partition, containerResource);

    // Clear resource utilization metrics cache.
    lastMemoryAggregateAllocationUpdateTime = -1;

    return true;
  }

  synchronized public RMContainer allocate(NodeType type, FiCaSchedulerNode node,
      Priority priority, ResourceRequest request, 
      Container container) {

    if (isStopped) {
      return null;
    }
    
    // Required sanity check - AM can call 'allocate' to update resource 
    // request without locking the scheduler, hence we need to check
    if (getTotalRequiredResources(priority) <= 0) {
      return null;
    }
    
    // Create RMContainer
    RMContainer rmContainer =
        new RMContainerImpl(container, this.getApplicationAttemptId(),
            node.getNodeID(), appSchedulingInfo.getUser(), this.rmContext,
            request.getNodeLabelExpression());

    // Add it to allContainers list.
    newlyAllocatedContainers.add(rmContainer);
    liveContainers.put(container.getId(), rmContainer);    

    // Update consumption and track allocations
    List<ResourceRequest> resourceRequestList = appSchedulingInfo.allocate(
        type, node, priority, request, container);
<<<<<<< HEAD
    attemptResourceUsage.incUsed(node.getPartition(),
        container.getResource());
    
    // Update resource requests related to "request" and store in RMContainer 
=======

    attemptResourceUsage.incUsed(node.getPartition(), container.getResource());

    // Update resource requests related to "request" and store in RMContainer
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    ((RMContainerImpl)rmContainer).setResourceRequests(resourceRequestList);

    // Inform the container
    rmContainer.handle(
        new RMContainerEvent(container.getId(), RMContainerEventType.START));

    if (LOG.isDebugEnabled()) {
      LOG.debug("allocate: applicationAttemptId=" 
          + container.getId().getApplicationAttemptId() 
          + " container=" + container.getId() + " host="
          + container.getNodeId().getHost() + " type=" + type);
    }
<<<<<<< HEAD
    RMAuditLogger.logSuccess(getUser(), 
        AuditConstants.ALLOC_CONTAINER, "SchedulerApp", 
=======
    RMAuditLogger.logSuccess(getUser(),
        AuditConstants.ALLOC_CONTAINER, "SchedulerApp",
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
        getApplicationId(), container.getId());
    
    return rmContainer;
  }

<<<<<<< HEAD
  public boolean unreserve(Priority priority,
      FiCaSchedulerNode node, RMContainer rmContainer) {
    // Done with the reservation?
    if (unreserve(node, priority)) {
=======
  public synchronized boolean unreserve(Priority priority,
      FiCaSchedulerNode node, RMContainer rmContainer) {
    // Cancel increase request (if it has reserved increase request 
    rmContainer.cancelIncreaseReservation();
    
    // Done with the reservation?
    if (internalUnreserve(node, priority)) {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      node.unreserveResource(this);

      // Update reserved metrics
      queue.getMetrics().unreserveResource(getUser(),
<<<<<<< HEAD
          rmContainer.getContainer().getResource());
=======
          rmContainer.getReservedResource());
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      return true;
    }
    return false;
  }

<<<<<<< HEAD
  @VisibleForTesting
  public synchronized boolean unreserve(FiCaSchedulerNode node, Priority priority) {
=======
  private boolean internalUnreserve(FiCaSchedulerNode node, Priority priority) {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    Map<NodeId, RMContainer> reservedContainers =
      this.reservedContainers.get(priority);

    if (reservedContainers != null) {
      RMContainer reservedContainer = reservedContainers.remove(node.getNodeID());

      // unreserve is now triggered in new scenarios (preemption)
      // as a consequence reservedcontainer might be null, adding NP-checks
      if (reservedContainer != null
          && reservedContainer.getContainer() != null
          && reservedContainer.getContainer().getResource() != null) {

        if (reservedContainers.isEmpty()) {
          this.reservedContainers.remove(priority);
        }
        // Reset the re-reservation count
        resetReReservations(priority);

<<<<<<< HEAD
        Resource resource = reservedContainer.getContainer().getResource();
=======
        Resource resource = reservedContainer.getReservedResource();
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
        this.attemptResourceUsage.decReserved(node.getPartition(), resource);

        LOG.info("Application " + getApplicationId() + " unreserved "
            + " on node " + node + ", currently has "
            + reservedContainers.size() + " at priority " + priority
            + "; currentReservation " + this.attemptResourceUsage.getReserved()
            + " on node-label=" + node.getPartition());
        return true;
      }
    }
    return false;
  }

  public synchronized float getLocalityWaitFactor(
      Priority priority, int clusterNodes) {
    // Estimate: Required unique resources (i.e. hosts + racks)
    int requiredResources = 
        Math.max(this.getResourceRequests(priority).size() - 1, 0);
    
    // waitFactor can't be more than '1' 
    // i.e. no point skipping more than clustersize opportunities
    return Math.min(((float)requiredResources / clusterNodes), 1.0f);
  }

  public synchronized Resource getTotalPendingRequests() {
    Resource ret = Resource.newInstance(0, 0);
    for (ResourceRequest rr : appSchedulingInfo.getAllResourceRequests()) {
      // to avoid double counting we count only "ANY" resource requests
      if (ResourceRequest.isAnyLocation(rr.getResourceName())){
        Resources.addTo(ret,
            Resources.multiply(rr.getCapability(), rr.getNumContainers()));
      }
    }
    return ret;
  }

<<<<<<< HEAD
  public synchronized void addPreemptContainer(ContainerId cont){
=======
  public synchronized void addPreemptContainer(ContainerId cont) {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    // ignore already completed containers
    if (liveContainers.containsKey(cont)) {
      containersToPreempt.add(cont);
    }
  }

  /**
   * This method produces an Allocation that includes the current view
   * of the resources that will be allocated to and preempted from this
   * application.
   *
   * @param rc
   * @param clusterResource
   * @param minimumAllocation
   * @return an allocation
   */
  public synchronized Allocation getAllocation(ResourceCalculator rc,
      Resource clusterResource, Resource minimumAllocation) {

    Set<ContainerId> currentContPreemption = Collections.unmodifiableSet(
        new HashSet<ContainerId>(containersToPreempt));
    containersToPreempt.clear();
    Resource tot = Resource.newInstance(0, 0);
    for(ContainerId c : currentContPreemption){
      Resources.addTo(tot,
          liveContainers.get(c).getContainer().getResource());
    }
    int numCont = (int) Math.ceil(
        Resources.divide(rc, clusterResource, tot, minimumAllocation));
    ResourceRequest rr = ResourceRequest.newInstance(
        Priority.UNDEFINED, ResourceRequest.ANY,
        minimumAllocation, numCont);
<<<<<<< HEAD
    ContainersAndNMTokensAllocation allocation =
        pullNewlyAllocatedContainersAndNMTokens();
    Resource headroom = getHeadroom();
    setApplicationHeadroomForMetrics(headroom);
    return new Allocation(allocation.getContainerList(), headroom, null,
      currentContPreemption, Collections.singletonList(rr),
      allocation.getNMTokenList());
=======
    List<Container> newlyAllocatedContainers = pullNewlyAllocatedContainers();
    List<Container> newlyIncreasedContainers = pullNewlyIncreasedContainers();
    List<Container> newlyDecreasedContainers = pullNewlyDecreasedContainers();
    List<NMToken> updatedNMTokens = pullUpdatedNMTokens();
    Resource headroom = getHeadroom();
    setApplicationHeadroomForMetrics(headroom);
    return new Allocation(newlyAllocatedContainers, headroom, null,
        currentContPreemption, Collections.singletonList(rr), updatedNMTokens,
        newlyIncreasedContainers, newlyDecreasedContainers);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }
  
  synchronized public NodeId getNodeIdToUnreserve(Priority priority,
      Resource resourceNeedUnreserve, ResourceCalculator rc,
      Resource clusterResource) {

    // first go around make this algorithm simple and just grab first
    // reservation that has enough resources
    Map<NodeId, RMContainer> reservedContainers = this.reservedContainers
        .get(priority);

    if ((reservedContainers != null) && (!reservedContainers.isEmpty())) {
      for (Map.Entry<NodeId, RMContainer> entry : reservedContainers.entrySet()) {
        NodeId nodeId = entry.getKey();
<<<<<<< HEAD
        Resource containerResource = entry.getValue().getContainer().getResource();
        
        // make sure we unreserve one with at least the same amount of
        // resources, otherwise could affect capacity limits
        if (Resources.lessThanOrEqual(rc, clusterResource,
            resourceNeedUnreserve, containerResource)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("unreserving node with reservation size: "
                + containerResource
=======
        RMContainer reservedContainer = entry.getValue();
        if (reservedContainer.hasIncreaseReservation()) {
          // Currently, only regular container allocation supports continuous
          // reservation looking, we don't support canceling increase request
          // reservation when allocating regular container.
          continue;
        }
        
        Resource reservedResource = reservedContainer.getReservedResource();
        
        // make sure we unreserve one with at least the same amount of
        // resources, otherwise could affect capacity limits
        if (Resources.fitsIn(rc, clusterResource, resourceNeedUnreserve,
            reservedResource)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("unreserving node with reservation size: "
                + reservedResource
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
                + " in order to allocate container with size: " + resourceNeedUnreserve);
          }
          return nodeId;
        }
      }
    }
    return null;
  }
  
  public synchronized void setHeadroomProvider(
    CapacityHeadroomProvider headroomProvider) {
    this.headroomProvider = headroomProvider;
  }

  public synchronized CapacityHeadroomProvider getHeadroomProvider() {
    return headroomProvider;
  }
  
  @Override
  public synchronized Resource getHeadroom() {
    if (headroomProvider != null) {
      return headroomProvider.getHeadroom();
    }
    return super.getHeadroom();
  }
  
  @Override
  public synchronized void transferStateFromPreviousAttempt(
      SchedulerApplicationAttempt appAttempt) {
    super.transferStateFromPreviousAttempt(appAttempt);
    this.headroomProvider = 
      ((FiCaSchedulerApp) appAttempt).getHeadroomProvider();
  }
<<<<<<< HEAD
=======
  
  public boolean reserveIncreasedContainer(Priority priority, 
      FiCaSchedulerNode node,
      RMContainer rmContainer, Resource reservedResource) {
    // Inform the application
    if (super.reserveIncreasedContainer(node, priority, rmContainer,
        reservedResource)) {

      queue.getMetrics().reserveResource(getUser(), reservedResource);

      // Update the node
      node.reserveResource(this, priority, rmContainer);
      
      // Succeeded
      return true;
    }
    
    return false;
  }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  public void reserve(Priority priority,
      FiCaSchedulerNode node, RMContainer rmContainer, Container container) {
    // Update reserved metrics if this is the first reservation
    if (rmContainer == null) {
      queue.getMetrics().reserveResource(
          getUser(), container.getResource());
    }

    // Inform the application
    rmContainer = super.reserve(node, priority, rmContainer, container);

    // Update the node
    node.reserveResource(this, priority, rmContainer);
  }

  @VisibleForTesting
  public RMContainer findNodeToUnreserve(Resource clusterResource,
      FiCaSchedulerNode node, Priority priority,
      Resource minimumUnreservedResource) {
    // need to unreserve some other container first
    NodeId idToUnreserve =
        getNodeIdToUnreserve(priority, minimumUnreservedResource,
            rc, clusterResource);
    if (idToUnreserve == null) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("checked to see if could unreserve for app but nothing "
            + "reserved that matches for this app");
      }
      return null;
    }
    FiCaSchedulerNode nodeToUnreserve =
        ((CapacityScheduler) scheduler).getNode(idToUnreserve);
    if (nodeToUnreserve == null) {
      LOG.error("node to unreserve doesn't exist, nodeid: " + idToUnreserve);
      return null;
    }
    if (LOG.isDebugEnabled()) {
      LOG.debug("unreserving for app: " + getApplicationId()
        + " on nodeId: " + idToUnreserve
        + " in order to replace reserved application and place it on node: "
        + node.getNodeID() + " needing: " + minimumUnreservedResource);
    }

    // headroom
    Resources.addTo(getHeadroom(), nodeToUnreserve
        .getReservedContainer().getReservedResource());

    return nodeToUnreserve.getReservedContainer();
  }

  public LeafQueue getCSLeafQueue() {
    return (LeafQueue)queue;
  }
<<<<<<< HEAD

  private CSAssignment getCSAssignmentFromAllocateResult(
      Resource clusterResource, ContainerAllocation result) {
    // Handle skipped
    boolean skipped =
        (result.getAllocationState() == AllocationState.APP_SKIPPED);
    CSAssignment assignment = new CSAssignment(skipped);
    assignment.setApplication(this);
    
    // Handle excess reservation
    assignment.setExcessReservation(result.getContainerToBeUnreserved());

    // If we allocated something
    if (Resources.greaterThan(rc, clusterResource,
        result.getResourceToBeAllocated(), Resources.none())) {
      Resource allocatedResource = result.getResourceToBeAllocated();
      Container updatedContainer = result.getUpdatedContainer();
      
      assignment.setResource(allocatedResource);
      assignment.setType(result.getContainerNodeType());

      if (result.getAllocationState() == AllocationState.RESERVED) {
        // This is a reserved container
        LOG.info("Reserved container " + " application=" + getApplicationId()
            + " resource=" + allocatedResource + " queue="
            + this.toString() + " cluster=" + clusterResource);
        assignment.getAssignmentInformation().addReservationDetails(
            updatedContainer.getId(), getCSLeafQueue().getQueuePath());
        assignment.getAssignmentInformation().incrReservations();
        Resources.addTo(assignment.getAssignmentInformation().getReserved(),
            allocatedResource);
        assignment.setFulfilledReservation(true);
      } else {
        // This is a new container
        // Inform the ordering policy
        LOG.info("assignedContainer" + " application attempt="
            + getApplicationAttemptId() + " container="
            + updatedContainer.getId() + " queue=" + this + " clusterResource="
            + clusterResource);

        getCSLeafQueue().getOrderingPolicy().containerAllocated(this,
            getRMContainer(updatedContainer.getId()));

        assignment.getAssignmentInformation().addAllocationDetails(
            updatedContainer.getId(), getCSLeafQueue().getQueuePath());
        assignment.getAssignmentInformation().incrAllocations();
        Resources.addTo(assignment.getAssignmentInformation().getAllocated(),
            allocatedResource);
      }
    }
    
    return assignment;
  }
  
  public CSAssignment assignContainers(Resource clusterResource,
      FiCaSchedulerNode node, ResourceLimits currentResourceLimits,
      SchedulingMode schedulingMode) {
=======
  
  public CSAssignment assignContainers(Resource clusterResource,
      FiCaSchedulerNode node, ResourceLimits currentResourceLimits,
      SchedulingMode schedulingMode, RMContainer reservedContainer) {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    if (LOG.isDebugEnabled()) {
      LOG.debug("pre-assignContainers for application "
          + getApplicationId());
      showRequests();
    }

<<<<<<< HEAD
    // Check if application needs more resource, skip if it doesn't need more.
    if (!hasPendingResourceRequest(rc,
        node.getPartition(), clusterResource, schedulingMode)) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Skip app_attempt=" + getApplicationAttemptId()
            + ", because it doesn't need more resource, schedulingMode="
            + schedulingMode.name() + " node-label=" + node.getPartition());
      }
      return CSAssignment.SKIP_ASSIGNMENT;
    }

    synchronized (this) {
      // Schedule in priority order
      for (Priority priority : getPriorities()) {
        ContainerAllocation allocationResult =
            containerAllocator.allocate(clusterResource, node,
                schedulingMode, currentResourceLimits, priority, null);

        // If it's a skipped allocation
        AllocationState allocationState = allocationResult.getAllocationState();

        if (allocationState == AllocationState.PRIORITY_SKIPPED) {
          continue;
        }
        return getCSAssignmentFromAllocateResult(clusterResource,
            allocationResult);
      }
    }

    // We will reach here if we skipped all priorities of the app, so we will
    // skip the app.
    return CSAssignment.SKIP_ASSIGNMENT;
  }


  public synchronized CSAssignment assignReservedContainer(
      FiCaSchedulerNode node, RMContainer rmContainer,
      Resource clusterResource, SchedulingMode schedulingMode) {
    ContainerAllocation result =
        containerAllocator.allocate(clusterResource, node,
            schedulingMode, new ResourceLimits(Resources.none()),
            rmContainer.getReservedPriority(), rmContainer);

    return getCSAssignmentFromAllocateResult(clusterResource, result);
=======
    synchronized (this) {
      return containerAllocator.assignContainers(clusterResource, node,
          schedulingMode, currentResourceLimits, reservedContainer);
    }
  }

  public void nodePartitionUpdated(RMContainer rmContainer, String oldPartition,
      String newPartition) {
    Resource containerResource = rmContainer.getAllocatedResource();
    this.attemptResourceUsage.decUsed(oldPartition, containerResource);
    this.attemptResourceUsage.incUsed(newPartition, containerResource);
    getCSLeafQueue().decUsedResource(oldPartition, containerResource, this);
    getCSLeafQueue().incUsedResource(newPartition, containerResource, this);

    // Update new partition name if container is AM and also update AM resource
    if (rmContainer.isAMContainer()) {
      setAppAMNodePartitionName(newPartition);
      this.attemptResourceUsage.decAMUsed(oldPartition, containerResource);
      this.attemptResourceUsage.incAMUsed(newPartition, containerResource);
      getCSLeafQueue().decAMUsedResource(oldPartition, containerResource, this);
      getCSLeafQueue().incAMUsedResource(newPartition, containerResource, this);
    }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }
}
