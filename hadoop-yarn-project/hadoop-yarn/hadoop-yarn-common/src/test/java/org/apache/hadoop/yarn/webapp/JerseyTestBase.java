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

package org.apache.hadoop.yarn.webapp;

<<<<<<< HEAD
=======
import java.io.IOException;

import org.apache.hadoop.net.ServerSocketUtil;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.junit.Before;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;

public abstract class JerseyTestBase extends JerseyTest {
  public JerseyTestBase(WebAppDescriptor appDescriptor) {
    super(appDescriptor);
  }

  @Before
<<<<<<< HEAD
  public void initializeJerseyPort() {
    int jerseyPort = 9998;
    String port = System.getProperty("jersey.test.port");
    if(null != port) {
      jerseyPort = Integer.parseInt(port) + 10;
      if(jerseyPort > 65535) {
        jerseyPort = 9998;
      }
    }
=======
  public void initializeJerseyPort() throws IOException {
    int jerseyPort = ServerSocketUtil.getPort(9998, 10);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    System.setProperty("jersey.test.port", Integer.toString(jerseyPort));
  }
}
