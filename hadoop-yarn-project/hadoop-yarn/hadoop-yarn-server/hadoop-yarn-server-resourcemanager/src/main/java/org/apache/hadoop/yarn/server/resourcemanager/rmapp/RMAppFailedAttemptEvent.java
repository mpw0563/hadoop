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

package org.apache.hadoop.yarn.server.resourcemanager.rmapp;

import org.apache.hadoop.yarn.api.records.ApplicationId;

public class RMAppFailedAttemptEvent extends RMAppEvent {

<<<<<<< HEAD
  private final String diagnostics;
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  private final boolean transferStateFromPreviousAttempt;

  public RMAppFailedAttemptEvent(ApplicationId appId, RMAppEventType event, 
      String diagnostics, boolean transferStateFromPreviousAttempt) {
<<<<<<< HEAD
    super(appId, event);
    this.diagnostics = diagnostics;
    this.transferStateFromPreviousAttempt = transferStateFromPreviousAttempt;
  }

  public String getDiagnostics() {
    return this.diagnostics;
  }

=======
    super(appId, event, diagnostics);
    this.transferStateFromPreviousAttempt = transferStateFromPreviousAttempt;
  }

>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  public boolean getTransferStateFromPreviousAttempt() {
    return transferStateFromPreviousAttempt;
  }
}
