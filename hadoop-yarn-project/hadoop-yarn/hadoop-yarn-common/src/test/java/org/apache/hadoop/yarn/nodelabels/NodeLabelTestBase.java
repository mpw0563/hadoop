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

package org.apache.hadoop.yarn.nodelabels;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
<<<<<<< HEAD
import java.util.Set;
import java.util.Map.Entry;
=======
import java.util.Map.Entry;
import java.util.Set;
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

import org.apache.hadoop.yarn.api.records.NodeId;
import org.apache.hadoop.yarn.api.records.NodeLabel;
import org.junit.Assert;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

public class NodeLabelTestBase {
<<<<<<< HEAD
  public static void assertMapEquals(Map<NodeId, Set<String>> m1,
      ImmutableMap<NodeId, Set<String>> m2) {
    Assert.assertEquals(m1.size(), m2.size());
    for (NodeId k : m1.keySet()) {
      Assert.assertTrue(m2.containsKey(k));
      assertCollectionEquals(m1.get(k), m2.get(k));
    }
  }

  public static void assertLabelInfoMapEquals(Map<NodeId, Set<NodeLabel>> m1,
      ImmutableMap<NodeId, Set<NodeLabel>> m2) {
    Assert.assertEquals(m1.size(), m2.size());
    for (NodeId k : m1.keySet()) {
      Assert.assertTrue(m2.containsKey(k));
      assertNLCollectionEquals(m1.get(k), m2.get(k));
    }
  }

  public static void assertLabelsToNodesEquals(Map<String, Set<NodeId>> m1,
      ImmutableMap<String, Set<NodeId>> m2) {
    Assert.assertEquals(m1.size(), m2.size());
    for (String k : m1.keySet()) {
      Assert.assertTrue(m2.containsKey(k));
      Set<NodeId> s1 = new HashSet<NodeId>(m1.get(k));
      Set<NodeId> s2 = new HashSet<NodeId>(m2.get(k));
      Assert.assertEquals(s1, s2);
      Assert.assertTrue(s1.containsAll(s2));
=======
  public static void assertMapEquals(Map<NodeId, Set<String>> expected,
      ImmutableMap<NodeId, Set<String>> actual) {
    Assert.assertEquals(expected.size(), actual.size());
    for (NodeId k : expected.keySet()) {
      Assert.assertTrue(actual.containsKey(k));
      assertCollectionEquals(expected.get(k), actual.get(k));
    }
  }

  public static void assertLabelInfoMapEquals(
      Map<NodeId, Set<NodeLabel>> expected,
      ImmutableMap<NodeId, Set<NodeLabel>> actual) {
    Assert.assertEquals(expected.size(), actual.size());
    for (NodeId k : expected.keySet()) {
      Assert.assertTrue(actual.containsKey(k));
      assertNLCollectionEquals(expected.get(k), actual.get(k));
    }
  }

  public static void assertLabelsToNodesEquals(
      Map<String, Set<NodeId>> expected,
      ImmutableMap<String, Set<NodeId>> actual) {
    Assert.assertEquals(expected.size(), actual.size());
    for (String k : expected.keySet()) {
      Assert.assertTrue(actual.containsKey(k));
      Set<NodeId> expectedS1 = new HashSet<>(expected.get(k));
      Set<NodeId> actualS2 = new HashSet<>(actual.get(k));
      Assert.assertEquals(expectedS1, actualS2);
      Assert.assertTrue(expectedS1.containsAll(actualS2));
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
  }

  public static ImmutableMap<String, Set<NodeId>> transposeNodeToLabels(
      Map<NodeId, Set<String>> mapNodeToLabels) {
<<<<<<< HEAD
    Map<String, Set<NodeId>> mapLabelsToNodes =
        new HashMap<String, Set<NodeId>>();
=======
    Map<String, Set<NodeId>> mapLabelsToNodes = new HashMap<>();
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    for(Entry<NodeId, Set<String>> entry : mapNodeToLabels.entrySet()) {
      NodeId node = entry.getKey();
      Set<String> setLabels = entry.getValue();
      for(String label : setLabels) {
        Set<NodeId> setNode = mapLabelsToNodes.get(label);
        if (setNode == null) {
<<<<<<< HEAD
          setNode = new HashSet<NodeId>();
=======
          setNode = new HashSet<>();
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
        }
        setNode.add(NodeId.newInstance(node.getHost(), node.getPort()));
        mapLabelsToNodes.put(label, setNode);
      }
    }
    return ImmutableMap.copyOf(mapLabelsToNodes);
  }

<<<<<<< HEAD
  public static void assertMapContains(Map<NodeId, Set<String>> m1,
      ImmutableMap<NodeId, Set<String>> m2) {
    for (NodeId k : m2.keySet()) {
      Assert.assertTrue(m1.containsKey(k));
      assertCollectionEquals(m1.get(k), m2.get(k));
    }
  }

  public static void assertCollectionEquals(Collection<String> c1,
      Collection<String> c2) {
    Set<String> s1 = new HashSet<String>(c1);
    Set<String> s2 = new HashSet<String>(c2);
    Assert.assertEquals(s1, s2);
    Assert.assertTrue(s1.containsAll(s2));
  }

  public static void assertNLCollectionEquals(Collection<NodeLabel> c1,
      Collection<NodeLabel> c2) {
    Set<NodeLabel> s1 = new HashSet<NodeLabel>(c1);
    Set<NodeLabel> s2 = new HashSet<NodeLabel>(c2);
    Assert.assertEquals(s1, s2);
    Assert.assertTrue(s1.containsAll(s2));
=======
  public static void assertMapContains(Map<NodeId, Set<String>> expected,
      ImmutableMap<NodeId, Set<String>> actual) {
    for (NodeId k : actual.keySet()) {
      Assert.assertTrue(expected.containsKey(k));
      assertCollectionEquals(expected.get(k), actual.get(k));
    }
  }

  public static void assertCollectionEquals(Collection<String> expected,
      Collection<String> actual) {
    if (expected == null) {
      Assert.assertNull(actual);
    } else {
      Assert.assertNotNull(actual);
    }
    Set<String> expectedSet = new HashSet<>(expected);
    Set<String> actualSet = new HashSet<>(actual);
    Assert.assertEquals(expectedSet, actualSet);
    Assert.assertTrue(expectedSet.containsAll(actualSet));
  }

  public static void assertNLCollectionEquals(Collection<NodeLabel> expected,
      Collection<NodeLabel> actual) {
    if (expected == null) {
      Assert.assertNull(actual);
    } else {
      Assert.assertNotNull(actual);
    }

    Set<NodeLabel> expectedSet = new HashSet<>(expected);
    Set<NodeLabel> actualSet = new HashSet<>(actual);
    Assert.assertEquals(expectedSet, actualSet);
    Assert.assertTrue(expectedSet.containsAll(actualSet));
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  }

  @SuppressWarnings("unchecked")
  public static <E> Set<E> toSet(E... elements) {
    Set<E> set = Sets.newHashSet(elements);
    return set;
  }
  
<<<<<<< HEAD
  @SuppressWarnings("unchecked")
=======
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
  public static Set<NodeLabel> toNodeLabelSet(String... nodeLabelsStr) {
    if (null == nodeLabelsStr) {
      return null;
    }
<<<<<<< HEAD
    Set<NodeLabel> labels = new HashSet<NodeLabel>();
=======
    Set<NodeLabel> labels = new HashSet<>();
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    for (String label : nodeLabelsStr) {
      labels.add(NodeLabel.newInstance(label));
    }
    return labels;
  }

  public NodeId toNodeId(String str) {
    if (str.contains(":")) {
      int idx = str.indexOf(':');
      NodeId id =
          NodeId.newInstance(str.substring(0, idx),
              Integer.valueOf(str.substring(idx + 1)));
      return id;
    } else {
      return NodeId.newInstance(str, CommonNodeLabelsManager.WILDCARD_PORT);
    }
  }

  public static void assertLabelsInfoToNodesEquals(
<<<<<<< HEAD
      Map<NodeLabel, Set<NodeId>> m1, ImmutableMap<NodeLabel, Set<NodeId>> m2) {
    Assert.assertEquals(m1.size(), m2.size());
    for (NodeLabel k : m1.keySet()) {
      Assert.assertTrue(m2.containsKey(k));
      Set<NodeId> s1 = new HashSet<NodeId>(m1.get(k));
      Set<NodeId> s2 = new HashSet<NodeId>(m2.get(k));
      Assert.assertEquals(s1, s2);
      Assert.assertTrue(s1.containsAll(s2));
=======
      Map<NodeLabel, Set<NodeId>> expected,
      ImmutableMap<NodeLabel, Set<NodeId>> actual) {
    Assert.assertEquals(expected.size(), actual.size());
    for (NodeLabel k : expected.keySet()) {
      Assert.assertTrue(actual.containsKey(k));
      Set<NodeId> expectedS1 = new HashSet<>(expected.get(k));
      Set<NodeId> actualS2 = new HashSet<>(actual.get(k));
      Assert.assertEquals(expectedS1, actualS2);
      Assert.assertTrue(expectedS1.containsAll(actualS2));
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f
    }
  }
}
