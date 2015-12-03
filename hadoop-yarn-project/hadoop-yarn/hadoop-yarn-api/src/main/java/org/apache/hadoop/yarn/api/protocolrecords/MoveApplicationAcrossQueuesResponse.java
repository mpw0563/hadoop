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
package org.apache.hadoop.yarn.api.protocolrecords;

import org.apache.hadoop.classification.InterfaceAudience.Private;
import org.apache.hadoop.classification.InterfaceAudience.Public;
import org.apache.hadoop.classification.InterfaceStability.Unstable;
import org.apache.hadoop.yarn.api.ApplicationClientProtocol;
import org.apache.hadoop.yarn.util.Records;

/**
 * <p>
 * The response sent by the <code>ResourceManager</code> to the client moving
 * a submitted application to a different queue.
 * </p>
 * <p>
 * A response without exception means that the move has completed successfully.
 * </p>
 * 
 * @see ApplicationClientProtocol#moveApplicationAcrossQueues(MoveApplicationAcrossQueuesRequest)
 */
@Public
@Unstable
<<<<<<< HEAD
public class MoveApplicationAcrossQueuesResponse {
  @Private
  @Unstable
  public MoveApplicationAcrossQueuesResponse newInstance() {
=======
public abstract class MoveApplicationAcrossQueuesResponse {
  @Private
  @Unstable
  public static MoveApplicationAcrossQueuesResponse newInstance() {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    MoveApplicationAcrossQueuesResponse response =
        Records.newRecord(MoveApplicationAcrossQueuesResponse.class);
    return response;
  }
}
