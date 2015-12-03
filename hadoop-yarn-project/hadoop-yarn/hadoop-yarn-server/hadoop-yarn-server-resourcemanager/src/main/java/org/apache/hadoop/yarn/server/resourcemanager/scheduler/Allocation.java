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
<<<<<<< HEAD

=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
package org.apache.hadoop.yarn.server.resourcemanager.scheduler;

import java.util.List;
import java.util.Set;

<<<<<<< HEAD
=======
import com.google.common.annotations.VisibleForTesting;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.yarn.api.records.Container;
import org.apache.hadoop.yarn.api.records.ContainerId;
import org.apache.hadoop.yarn.api.records.NMToken;
import org.apache.hadoop.yarn.api.records.Resource;
import org.apache.hadoop.yarn.api.records.ResourceRequest;

public class Allocation {
<<<<<<< HEAD
  
  final List<Container> containers;
  final Resource resourceLimit;
=======

  final List<Container> containers;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  final Set<ContainerId> strictContainers;
  final Set<ContainerId> fungibleContainers;
  final List<ResourceRequest> fungibleResources;
  final List<NMToken> nmTokens;
<<<<<<< HEAD
=======
  final List<Container> increasedContainers;
  final List<Container> decreasedContainers;
  private Resource resourceLimit;

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  public Allocation(List<Container> containers, Resource resourceLimit,
      Set<ContainerId> strictContainers, Set<ContainerId> fungibleContainers,
      List<ResourceRequest> fungibleResources) {
    this(containers,  resourceLimit,strictContainers,  fungibleContainers,
      fungibleResources, null);
  }

  public Allocation(List<Container> containers, Resource resourceLimit,
      Set<ContainerId> strictContainers, Set<ContainerId> fungibleContainers,
      List<ResourceRequest> fungibleResources, List<NMToken> nmTokens) {
<<<<<<< HEAD
=======
    this(containers,  resourceLimit,strictContainers,  fungibleContainers,
      fungibleResources, nmTokens, null, null);
  }
  
  public Allocation(List<Container> containers, Resource resourceLimit,
      Set<ContainerId> strictContainers, Set<ContainerId> fungibleContainers,
      List<ResourceRequest> fungibleResources, List<NMToken> nmTokens,
      List<Container> increasedContainers, List<Container> decreasedContainer) {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    this.containers = containers;
    this.resourceLimit = resourceLimit;
    this.strictContainers = strictContainers;
    this.fungibleContainers = fungibleContainers;
    this.fungibleResources = fungibleResources;
    this.nmTokens = nmTokens;
<<<<<<< HEAD
=======
    this.increasedContainers = increasedContainers;
    this.decreasedContainers = decreasedContainer;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  public List<Container> getContainers() {
    return containers;
  }

  public Resource getResourceLimit() {
    return resourceLimit;
  }

  public Set<ContainerId> getStrictContainerPreemptions() {
    return strictContainers;
  }

  public Set<ContainerId> getContainerPreemptions() {
    return fungibleContainers;
  }

  public List<ResourceRequest> getResourcePreemptions() {
    return fungibleResources;
  }
<<<<<<< HEAD
    
  public List<NMToken> getNMTokens() {
    return nmTokens;
  }

=======

  public List<NMToken> getNMTokens() {
    return nmTokens;
  }
  
  public List<Container> getIncreasedContainers() {
    return increasedContainers;
  }
  
  public List<Container> getDecreasedContainers() {
    return decreasedContainers;
  }

  @VisibleForTesting
  public void setResourceLimit(Resource resource) {
    this.resourceLimit = resource;
  }
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
}
