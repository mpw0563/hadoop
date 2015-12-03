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

package org.apache.hadoop.io;

import org.junit.Test;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.charset.MalformedInputException;
import java.util.Arrays;

/** Unit tests for NonUTF8. */
<<<<<<< HEAD
public class TestTextNonUTF8 extends TestCase {
=======
public class TestTextNonUTF8 {
>>>>>>> bbe9e8b2d20998edf304b98f2a14f114e975481f

  @Test
  public void testNonUTF8() throws Exception{
    // this is a non UTF8 byte array
    byte b[] = {-0x01, -0x01, -0x01, -0x01, -0x01, -0x01, -0x01};
    boolean nonUTF8 = false;
    Text t = new Text(b);
    try{
      Text.validateUTF8(b);
    }catch(MalformedInputException me){
      nonUTF8 = false;
    }
    // asserting that the byte array is non utf8
    assertFalse(nonUTF8);
    byte ret[] = t.getBytes();
    // asseting that the byte array are the same when the Text
    // object is created.
    assertTrue(Arrays.equals(b, ret));
  }

}
