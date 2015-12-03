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

package org.apache.hadoop.yarn.server.nodemanager.nodelabels;

import java.util.Set;

<<<<<<< HEAD
import org.apache.hadoop.service.AbstractService;
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.apache.hadoop.yarn.api.records.NodeLabel;

/**
 * Interface which will be responsible for fetching the labels
 * 
 */
<<<<<<< HEAD
public abstract class NodeLabelsProvider extends AbstractService {

  public NodeLabelsProvider(String name) {
    super(name);
  }
=======
public interface NodeLabelsProvider {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  /**
   * Provides the labels. LabelProvider is expected to give same Labels
   * continuously until there is a change in labels. 
   * If null is returned then Empty label set is assumed by the caller.
   * 
   * @return Set of node label strings applicable for a node
   */
  public abstract Set<NodeLabel> getNodeLabels();
}