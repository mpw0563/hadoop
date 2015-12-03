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
package org.apache.hadoop.yarn.server.resourcemanager.scheduler.capacity;

import java.io.IOException;
<<<<<<< HEAD
import java.util.HashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.fs.CommonConfigurationKeys;
import org.apache.hadoop.security.GroupMappingServiceProvider;
import org.apache.hadoop.yarn.api.records.ApplicationAccessType;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.resourcemanager.MockRM;
import org.apache.hadoop.yarn.server.resourcemanager.RMContext;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.RMApp;
import org.apache.hadoop.yarn.server.resourcemanager.rmapp.RMAppState;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.SchedulerApplication;
import org.apache.hadoop.yarn.server.resourcemanager.scheduler.fair.SimpleGroupsMapping;
import org.junit.After;
import org.junit.Assert;
=======

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.yarn.conf.YarnConfiguration;
import org.apache.hadoop.yarn.server.resourcemanager.RMContext;
import org.apache.hadoop.yarn.server.resourcemanager.placement.UserGroupMappingPlacementRule;
import org.apache.hadoop.yarn.server.resourcemanager.placement.UserGroupMappingPlacementRule.QueueMapping;
import org.apache.hadoop.yarn.server.resourcemanager.placement.UserGroupMappingPlacementRule.QueueMapping.MappingType;
import org.junit.Assert;
import org.junit.Before;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
import org.junit.Test;

public class TestQueueMappings {

  private static final Log LOG = LogFactory.getLog(TestQueueMappings.class);

  private static final String Q1 = "q1";
  private static final String Q2 = "q2";

  private final static String Q1_PATH =
      CapacitySchedulerConfiguration.ROOT + "." + Q1;
  private final static String Q2_PATH =
      CapacitySchedulerConfiguration.ROOT + "." + Q2;
<<<<<<< HEAD

  private MockRM resourceManager;

  @After
  public void tearDown() throws Exception {
    if (resourceManager != null) {
      LOG.info("Stopping the resource manager");
      resourceManager.stop();
    }
=======
  
  private CapacityScheduler cs;
  private YarnConfiguration conf;
  
  @Before
  public void setup() {
    CapacitySchedulerConfiguration csConf =
        new CapacitySchedulerConfiguration();
    setupQueueConfiguration(csConf);
    conf = new YarnConfiguration(csConf);
    cs = new CapacityScheduler();

    RMContext rmContext = TestUtils.getMockRMContext();
    cs.setConf(conf);
    cs.setRMContext(rmContext);
    cs.init(conf);
    cs.start();
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  private void setupQueueConfiguration(CapacitySchedulerConfiguration conf) {
    // Define top-level queues
    conf.setQueues(CapacitySchedulerConfiguration.ROOT, new String[] { Q1, Q2 });

    conf.setCapacity(Q1_PATH, 10);
    conf.setCapacity(Q2_PATH, 90);

    LOG.info("Setup top-level queues q1 and q2");
  }
<<<<<<< HEAD

  @Test (timeout = 60000)
  public void testQueueMapping() throws Exception {
    CapacitySchedulerConfiguration csConf =
        new CapacitySchedulerConfiguration();
    setupQueueConfiguration(csConf);
    YarnConfiguration conf = new YarnConfiguration(csConf);
    CapacityScheduler cs = new CapacityScheduler();

    RMContext rmContext = TestUtils.getMockRMContext();
    cs.setConf(conf);
    cs.setRMContext(rmContext);
    cs.init(conf);
    cs.start();

    conf.setClass(CommonConfigurationKeys.HADOOP_SECURITY_GROUP_MAPPING,
        SimpleGroupsMapping.class, GroupMappingServiceProvider.class);
    conf.set(CapacitySchedulerConfiguration.ENABLE_QUEUE_MAPPING_OVERRIDE,
        "true");

=======
  
  @Test
  public void testQueueMappingSpecifyingNotExistedQueue() {
    // if the mapping specifies a queue that does not exist, reinitialize will
    // be failed
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING,
        "u:user:non_existent_queue");
    boolean fail = false;
    try {
      cs.reinitialize(conf, null);
    } catch (IOException ioex) {
      fail = true;
    }
    Assert.assertTrue("queue initialization failed for non-existent q", fail);
  }
  
  @Test
  public void testQueueMappingTrimSpaces() throws IOException {
    // space trimming
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "    u : a : " + Q1);
    cs.reinitialize(conf, null);
    checkQMapping(new QueueMapping(MappingType.USER, "a", Q1));
  }

  @Test (timeout = 60000)
  public void testQueueMappingParsingInvalidCases() throws Exception {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    // configuration parsing tests - negative test cases
    checkInvalidQMapping(conf, cs, "x:a:b", "invalid specifier");
    checkInvalidQMapping(conf, cs, "u:a", "no queue specified");
    checkInvalidQMapping(conf, cs, "g:a", "no queue specified");
    checkInvalidQMapping(conf, cs, "u:a:b,g:a",
        "multiple mappings with invalid mapping");
    checkInvalidQMapping(conf, cs, "u:a:b,g:a:d:e", "too many path segments");
    checkInvalidQMapping(conf, cs, "u::", "empty source and queue");
    checkInvalidQMapping(conf, cs, "u:", "missing source missing queue");
    checkInvalidQMapping(conf, cs, "u:a:", "empty source missing q");
<<<<<<< HEAD

    // simple base case for mapping user to queue
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "u:a:" + Q1);
    cs.reinitialize(conf, null);
    checkQMapping("a", Q1, cs);

    // group mapping test
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "g:agroup:" + Q1);
    cs.reinitialize(conf, null);
    checkQMapping("a", Q1, cs);

    // %user tests
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "u:%user:" + Q2);
    cs.reinitialize(conf, null);
    checkQMapping("a", Q2, cs);

    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "u:%user:%user");
    cs.reinitialize(conf, null);
    checkQMapping("a", "a", cs);

    // %primary_group tests
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING,
        "u:%user:%primary_group");
    cs.reinitialize(conf, null);
    checkQMapping("a", "agroup", cs);

    // non-primary group mapping
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING,
        "g:asubgroup1:" + Q1);
    cs.reinitialize(conf, null);
    checkQMapping("a", Q1, cs);

    // space trimming
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "    u : a : " + Q1);
    cs.reinitialize(conf, null);
    checkQMapping("a", Q1, cs);

    csConf = new CapacitySchedulerConfiguration();
    csConf.set(YarnConfiguration.RM_SCHEDULER,
        CapacityScheduler.class.getName());
    setupQueueConfiguration(csConf);
    conf = new YarnConfiguration(csConf);

    resourceManager = new MockRM(csConf);
    resourceManager.start();

    conf.setClass(CommonConfigurationKeys.HADOOP_SECURITY_GROUP_MAPPING,
        SimpleGroupsMapping.class, GroupMappingServiceProvider.class);
    conf.set(CapacitySchedulerConfiguration.ENABLE_QUEUE_MAPPING_OVERRIDE,
        "true");
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "u:user:" + Q1);
    resourceManager.getResourceScheduler().reinitialize(conf, null);

    // ensure that if the user specifies a Q that is still overriden
    checkAppQueue(resourceManager, "user", Q2, Q1);

    // toggle admin override and retry
    conf.setBoolean(
        CapacitySchedulerConfiguration.ENABLE_QUEUE_MAPPING_OVERRIDE,
        false);
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "u:user:" + Q1);
    setupQueueConfiguration(csConf);
    resourceManager.getResourceScheduler().reinitialize(conf, null);

    checkAppQueue(resourceManager, "user", Q2, Q2);

    // ensure that if a user does not specify a Q, the user mapping is used
    checkAppQueue(resourceManager, "user", null, Q1);

    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, "g:usergroup:" + Q2);
    setupQueueConfiguration(csConf);
    resourceManager.getResourceScheduler().reinitialize(conf, null);

    // ensure that if a user does not specify a Q, the group mapping is used
    checkAppQueue(resourceManager, "user", null, Q2);

    // if the mapping specifies a queue that does not exist, the job is rejected
    conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING,
        "u:user:non_existent_queue");
    setupQueueConfiguration(csConf);

    boolean fail = false;
    try {
      resourceManager.getResourceScheduler().reinitialize(conf, null);
    }
    catch (IOException ioex) {
      fail = true;
    }
    Assert.assertTrue("queue initialization failed for non-existent q", fail);
    resourceManager.stop();
  }

  private void checkAppQueue(MockRM resourceManager, String user,
      String submissionQueue, String expected)
      throws Exception {
    RMApp app = resourceManager.submitApp(200, "name", user,
        new HashMap<ApplicationAccessType, String>(), false, submissionQueue, -1,
        null, "MAPREDUCE", false);
    RMAppState expectedState = expected.isEmpty() ? RMAppState.FAILED
        : RMAppState.ACCEPTED;
    resourceManager.waitForState(app.getApplicationId(), expectedState);
    // get scheduler app
    CapacityScheduler cs = (CapacityScheduler)
        resourceManager.getResourceScheduler();
    SchedulerApplication schedulerApp =
        cs.getSchedulerApplications().get(app.getApplicationId());
    String queue = "";
    if (schedulerApp != null) {
      queue = schedulerApp.getQueue().getQueueName();
    }
    Assert.assertTrue("expected " + expected + " actual " + queue,
        expected.equals(queue));
    Assert.assertEquals(expected, app.getQueue());
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  private void checkInvalidQMapping(YarnConfiguration conf,
      CapacityScheduler cs,
      String mapping, String reason)
      throws IOException {
    boolean fail = false;
    try {
      conf.set(CapacitySchedulerConfiguration.QUEUE_MAPPING, mapping);
      cs.reinitialize(conf, null);
    } catch (IOException ex) {
      fail = true;
    }
    Assert.assertTrue("invalid mapping did not throw exception for " + reason,
        fail);
  }

<<<<<<< HEAD
  private void checkQMapping(String user, String expected, CapacityScheduler cs)
          throws IOException {
    String actual = cs.getMappedQueueForTest(user);
    Assert.assertTrue("expected " + expected + " actual " + actual,
        expected.equals(actual));
=======
  private void checkQMapping(QueueMapping expected)
          throws IOException {
    UserGroupMappingPlacementRule rule =
        (UserGroupMappingPlacementRule) cs.getRMContext()
            .getQueuePlacementManager().getPlacementRules().get(0);
    QueueMapping queueMapping = rule.getQueueMappings().get(0);
    Assert.assertEquals(queueMapping, expected);
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }
}
