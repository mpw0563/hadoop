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

package org.apache.hadoop.yarn.server.resourcemanager;

import java.nio.ByteBuffer;
import java.util.concurrent.ConcurrentMap;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.ha.HAServiceProtocol;
import org.apache.hadoop.ha.HAServiceProtocol.HAServiceState;
import org.apache.hadoop.yarn.LocalConfigurationProvider;
import org.apache.hadoop.yarn.api.records.ApplicationId;
import org.apache.hadoop.yarn.api.records.NodeId;
import org.apache.hadoop.yarn.conf.ConfigurationProvider;
import org.apache.hadoop.yarn.event.Dispatcher;
import org.apache.hadoop.yarn.server.resourcemanager.ahs.RMApplicationHistoryWriter;
import org.apache.hadoop.yarn.server.resourcemanager.metrics.SystemMetricsPublisher;
import org.apache.hadoop.yarn.server.resourcemanager.nodelabels.RMNodeLabelsManager;
<<<<<<< HEAD
=======
import org.apache.hadoop.yarn.server.resourcemanager.nodelabels.RMDelegatedNodeLabelsUpdater;
import org.apache.hadoop.yarn.server.resourcemanager.placement.PlacementManager;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.yarn.server.resourcemanager.recovery.RMStateStore;
import org.apache.hadoop.yarn.server.resourcemanager.reservation.ReservationSystem;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.RMApp;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.attempt.AMLivelinessMonitor;
import org.apache.hadoop.yarn.server.resourcemanager.rmcontainer.ContainerAllocationExpirer;
import org.apache.hadoop.yarn.server.resourcemanager.rmnode.RMNode;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.ResourceScheduler;
import org.apache.hadoop.yarn.server.resourcemanager.security.AMRMTokenSecretManager;
import org.apache.hadoop.yarn.server.resourcemanager.security.ClientToAMTokenSecretManagerInRM;
import org.apache.hadoop.yarn.server.resourcemanager.security.DelegationTokenRenewer;
import org.apache.hadoop.yarn.server.resourcemanager.security.NMTokenSecretManagerInRM;
import org.apache.hadoop.yarn.server.resourcemanager.security.RMContainerTokenSecretManager;
import org.apache.hadoop.yarn.server.resourcemanager.security.RMDelegationTokenSecretManager;
import org.apache.hadoop.yarn.util.Clock;

import com.google.common.annotations.VisibleForTesting;

public class RMContextImpl implements RMContext {

  private Dispatcher rmDispatcher;

  private boolean isHAEnabled;

  private HAServiceState haServiceState =
      HAServiceProtocol.HAServiceState.INITIALIZING;

  private AdminService adminService;

  private ConfigurationProvider configurationProvider;

  private RMActiveServiceContext activeServiceContext;

  private Configuration yarnConfiguration;

<<<<<<< HEAD
=======
  private RMApplicationHistoryWriter rmApplicationHistoryWriter;
  private SystemMetricsPublisher systemMetricsPublisher;

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  /**
   * Default constructor. To be used in conjunction with setter methods for
   * individual fields.
   */
  public RMContextImpl() {
<<<<<<< HEAD

=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @VisibleForTesting
  // helper constructor for tests
  public RMContextImpl(Dispatcher rmDispatcher,
      ContainerAllocationExpirer containerAllocationExpirer,
      AMLivelinessMonitor amLivelinessMonitor,
      AMLivelinessMonitor amFinishingMonitor,
      DelegationTokenRenewer delegationTokenRenewer,
      AMRMTokenSecretManager appTokenSecretManager,
      RMContainerTokenSecretManager containerTokenSecretManager,
      NMTokenSecretManagerInRM nmTokenSecretManager,
      ClientToAMTokenSecretManagerInRM clientToAMTokenSecretManager,
<<<<<<< HEAD
      RMApplicationHistoryWriter rmApplicationHistoryWriter,
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
      ResourceScheduler scheduler) {
    this();
    this.setDispatcher(rmDispatcher);
    setActiveServiceContext(new RMActiveServiceContext(rmDispatcher,
        containerAllocationExpirer, amLivelinessMonitor, amFinishingMonitor,
        delegationTokenRenewer, appTokenSecretManager,
        containerTokenSecretManager, nmTokenSecretManager,
<<<<<<< HEAD
        clientToAMTokenSecretManager, rmApplicationHistoryWriter,
=======
        clientToAMTokenSecretManager,
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
        scheduler));

    ConfigurationProvider provider = new LocalConfigurationProvider();
    setConfigurationProvider(provider);
  }
  
  @VisibleForTesting
  // helper constructor for tests
  public RMContextImpl(Dispatcher rmDispatcher,
      ContainerAllocationExpirer containerAllocationExpirer,
      AMLivelinessMonitor amLivelinessMonitor,
      AMLivelinessMonitor amFinishingMonitor,
      DelegationTokenRenewer delegationTokenRenewer,
      AMRMTokenSecretManager appTokenSecretManager,
      RMContainerTokenSecretManager containerTokenSecretManager,
      NMTokenSecretManagerInRM nmTokenSecretManager,
<<<<<<< HEAD
      ClientToAMTokenSecretManagerInRM clientToAMTokenSecretManager,
      RMApplicationHistoryWriter rmApplicationHistoryWriter) {
=======
      ClientToAMTokenSecretManagerInRM clientToAMTokenSecretManager) {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    this(
      rmDispatcher,
      containerAllocationExpirer,
      amLivelinessMonitor,
      amFinishingMonitor,
      delegationTokenRenewer,
      appTokenSecretManager,
      containerTokenSecretManager,
      nmTokenSecretManager,
<<<<<<< HEAD
      clientToAMTokenSecretManager,
      rmApplicationHistoryWriter,
      null);
=======
      clientToAMTokenSecretManager, null);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @Override
  public Dispatcher getDispatcher() {
    return this.rmDispatcher;
  }

  @Override
  public RMStateStore getStateStore() {
    return activeServiceContext.getStateStore();
  }

  @Override
  public ConcurrentMap<ApplicationId, RMApp> getRMApps() {
    return activeServiceContext.getRMApps();
  }

  @Override
  public ConcurrentMap<NodeId, RMNode> getRMNodes() {
    return activeServiceContext.getRMNodes();
  }

  @Override
  public ConcurrentMap<NodeId, RMNode> getInactiveRMNodes() {
    return activeServiceContext.getInactiveRMNodes();
  }

  @Override
  public ContainerAllocationExpirer getContainerAllocationExpirer() {
    return activeServiceContext.getContainerAllocationExpirer();
  }

  @Override
  public AMLivelinessMonitor getAMLivelinessMonitor() {
    return activeServiceContext.getAMLivelinessMonitor();
  }

  @Override
  public AMLivelinessMonitor getAMFinishingMonitor() {
    return activeServiceContext.getAMFinishingMonitor();
  }

  @Override
  public DelegationTokenRenewer getDelegationTokenRenewer() {
    return activeServiceContext.getDelegationTokenRenewer();
  }

  @Override
  public AMRMTokenSecretManager getAMRMTokenSecretManager() {
    return activeServiceContext.getAMRMTokenSecretManager();
  }

  @Override
  public RMContainerTokenSecretManager getContainerTokenSecretManager() {
    return activeServiceContext.getContainerTokenSecretManager();
  }

  @Override
  public NMTokenSecretManagerInRM getNMTokenSecretManager() {
    return activeServiceContext.getNMTokenSecretManager();
  }

  @Override
  public ResourceScheduler getScheduler() {
    return activeServiceContext.getScheduler();
  }

  @Override
  public ReservationSystem getReservationSystem() {
    return activeServiceContext.getReservationSystem();
  }

  @Override
  public NodesListManager getNodesListManager() {
    return activeServiceContext.getNodesListManager();
  }

  @Override
  public ClientToAMTokenSecretManagerInRM getClientToAMTokenSecretManager() {
    return activeServiceContext.getClientToAMTokenSecretManager();
  }

  @Override
  public AdminService getRMAdminService() {
    return this.adminService;
  }

  @VisibleForTesting
  public void setStateStore(RMStateStore store) {
    activeServiceContext.setStateStore(store);
  }

  @Override
  public ClientRMService getClientRMService() {
    return activeServiceContext.getClientRMService();
  }

  @Override
  public ApplicationMasterService getApplicationMasterService() {
    return activeServiceContext.getApplicationMasterService();
  }

  @Override
  public ResourceTrackerService getResourceTrackerService() {
    return activeServiceContext.getResourceTrackerService();
  }

  void setHAEnabled(boolean isHAEnabled) {
    this.isHAEnabled = isHAEnabled;
  }

  void setHAServiceState(HAServiceState haServiceState) {
    synchronized (haServiceState) {
      this.haServiceState = haServiceState;
    }
  }

  void setDispatcher(Dispatcher dispatcher) {
    this.rmDispatcher = dispatcher;
  }

  void setRMAdminService(AdminService adminService) {
    this.adminService = adminService;
  }

  @Override
  public void setClientRMService(ClientRMService clientRMService) {
    activeServiceContext.setClientRMService(clientRMService);
  }

  @Override
  public RMDelegationTokenSecretManager getRMDelegationTokenSecretManager() {
    return activeServiceContext.getRMDelegationTokenSecretManager();
  }

  @Override
  public void setRMDelegationTokenSecretManager(
      RMDelegationTokenSecretManager delegationTokenSecretManager) {
    activeServiceContext
        .setRMDelegationTokenSecretManager(delegationTokenSecretManager);
  }

  void setContainerAllocationExpirer(
      ContainerAllocationExpirer containerAllocationExpirer) {
    activeServiceContext
        .setContainerAllocationExpirer(containerAllocationExpirer);
  }

  void setAMLivelinessMonitor(AMLivelinessMonitor amLivelinessMonitor) {
    activeServiceContext.setAMLivelinessMonitor(amLivelinessMonitor);
  }

  void setAMFinishingMonitor(AMLivelinessMonitor amFinishingMonitor) {
    activeServiceContext.setAMFinishingMonitor(amFinishingMonitor);
  }

  void setContainerTokenSecretManager(
      RMContainerTokenSecretManager containerTokenSecretManager) {
    activeServiceContext
        .setContainerTokenSecretManager(containerTokenSecretManager);
  }

  void setNMTokenSecretManager(NMTokenSecretManagerInRM nmTokenSecretManager) {
    activeServiceContext.setNMTokenSecretManager(nmTokenSecretManager);
  }

  @VisibleForTesting
  public void setScheduler(ResourceScheduler scheduler) {
    activeServiceContext.setScheduler(scheduler);
  }

  void setReservationSystem(ReservationSystem reservationSystem) {
    activeServiceContext.setReservationSystem(reservationSystem);
  }

  void setDelegationTokenRenewer(DelegationTokenRenewer delegationTokenRenewer) {
    activeServiceContext.setDelegationTokenRenewer(delegationTokenRenewer);
  }

  void setClientToAMTokenSecretManager(
      ClientToAMTokenSecretManagerInRM clientToAMTokenSecretManager) {
    activeServiceContext
        .setClientToAMTokenSecretManager(clientToAMTokenSecretManager);
  }

  void setAMRMTokenSecretManager(AMRMTokenSecretManager amRMTokenSecretManager) {
    activeServiceContext.setAMRMTokenSecretManager(amRMTokenSecretManager);
  }

  void setNodesListManager(NodesListManager nodesListManager) {
    activeServiceContext.setNodesListManager(nodesListManager);
  }

  void setApplicationMasterService(
      ApplicationMasterService applicationMasterService) {
    activeServiceContext.setApplicationMasterService(applicationMasterService);
  }

  void setResourceTrackerService(ResourceTrackerService resourceTrackerService) {
    activeServiceContext.setResourceTrackerService(resourceTrackerService);
  }

  @Override
  public boolean isHAEnabled() {
    return isHAEnabled;
  }

  @Override
  public HAServiceState getHAServiceState() {
    synchronized (haServiceState) {
      return haServiceState;
    }
  }

  public void setWorkPreservingRecoveryEnabled(boolean enabled) {
    activeServiceContext.setWorkPreservingRecoveryEnabled(enabled);
  }

  @Override
  public boolean isWorkPreservingRecoveryEnabled() {
    return activeServiceContext.isWorkPreservingRecoveryEnabled();
  }

  @Override
  public RMApplicationHistoryWriter getRMApplicationHistoryWriter() {
<<<<<<< HEAD
    return activeServiceContext.getRMApplicationHistoryWriter();
=======
    return this.rmApplicationHistoryWriter;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @Override
  public void setSystemMetricsPublisher(
      SystemMetricsPublisher systemMetricsPublisher) {
<<<<<<< HEAD
    activeServiceContext.setSystemMetricsPublisher(systemMetricsPublisher);
=======
    this.systemMetricsPublisher = systemMetricsPublisher;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @Override
  public SystemMetricsPublisher getSystemMetricsPublisher() {
<<<<<<< HEAD
    return activeServiceContext.getSystemMetricsPublisher();
=======
    return this.systemMetricsPublisher;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @Override
  public void setRMApplicationHistoryWriter(
      RMApplicationHistoryWriter rmApplicationHistoryWriter) {
<<<<<<< HEAD
    activeServiceContext
        .setRMApplicationHistoryWriter(rmApplicationHistoryWriter);
=======
    this.rmApplicationHistoryWriter = rmApplicationHistoryWriter;

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @Override
  public ConfigurationProvider getConfigurationProvider() {
    return this.configurationProvider;
  }

  public void setConfigurationProvider(
      ConfigurationProvider configurationProvider) {
    this.configurationProvider = configurationProvider;
  }

  @Override
  public long getEpoch() {
    return activeServiceContext.getEpoch();
  }

  void setEpoch(long epoch) {
    activeServiceContext.setEpoch(epoch);
  }

  @Override
  public RMNodeLabelsManager getNodeLabelManager() {
    return activeServiceContext.getNodeLabelManager();
  }

  @Override
  public void setNodeLabelManager(RMNodeLabelsManager mgr) {
    activeServiceContext.setNodeLabelManager(mgr);
  }

<<<<<<< HEAD
=======
  @Override
  public RMDelegatedNodeLabelsUpdater getRMDelegatedNodeLabelsUpdater() {
    return activeServiceContext.getRMDelegatedNodeLabelsUpdater();
  }

  @Override
  public void setRMDelegatedNodeLabelsUpdater(
      RMDelegatedNodeLabelsUpdater delegatedNodeLabelsUpdater) {
    activeServiceContext.setRMDelegatedNodeLabelsUpdater(
        delegatedNodeLabelsUpdater);
  }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  public void setSchedulerRecoveryStartAndWaitTime(long waitTime) {
    activeServiceContext.setSchedulerRecoveryStartAndWaitTime(waitTime);
  }

  public boolean isSchedulerReadyForAllocatingContainers() {
    return activeServiceContext.isSchedulerReadyForAllocatingContainers();
  }

  @Private
  @VisibleForTesting
  public void setSystemClock(Clock clock) {
    activeServiceContext.setSystemClock(clock);
  }

  public ConcurrentMap<ApplicationId, ByteBuffer> getSystemCredentialsForApps() {
    return activeServiceContext.getSystemCredentialsForApps();
  }

  @Private
  @Unstable
  public RMActiveServiceContext getActiveServiceContext() {
    return activeServiceContext;
  }

  @Private
  @Unstable
  void setActiveServiceContext(RMActiveServiceContext activeServiceContext) {
    this.activeServiceContext = activeServiceContext;
  }

  @Override
  public Configuration getYarnConfiguration() {
    return this.yarnConfiguration;
  }

  public void setYarnConfiguration(Configuration yarnConfiguration) {
    this.yarnConfiguration=yarnConfiguration;
  }
<<<<<<< HEAD
=======

  @Override
  public PlacementManager getQueuePlacementManager() {
    return this.activeServiceContext.getQueuePlacementManager();
  }
  
  @Override
  public void setQueuePlacementManager(PlacementManager placementMgr) {
    this.activeServiceContext.setQueuePlacementManager(placementMgr);
  }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
}
