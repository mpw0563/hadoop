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

package org.apache.hadoop.yarn.server.resourcemanager.rmnode;

public enum RMNodeEventType {
  
  STARTED,
  
  // Source: AdminService
  DECOMMISSION,
<<<<<<< HEAD
  DECOMMISSION_WITH_TIMEOUT,
=======
  GRACEFUL_DECOMMISSION,
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  RECOMMISSION,
  
  // Source: AdminService, ResourceTrackerService
  RESOURCE_UPDATE,

  // ResourceTrackerService
  STATUS_UPDATE,
  REBOOTING,
  RECONNECTED,
  SHUTDOWN,

  // Source: Application
  CLEANUP_APP,

  // Source: Container
  CONTAINER_ALLOCATED,
  CLEANUP_CONTAINER,
<<<<<<< HEAD
=======
  DECREASE_CONTAINER,

  // Source: ClientRMService
  SIGNAL_CONTAINER,
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  // Source: RMAppAttempt
  FINISHED_CONTAINERS_PULLED_BY_AM,

  // Source: NMLivelinessMonitor
  EXPIRE
}
