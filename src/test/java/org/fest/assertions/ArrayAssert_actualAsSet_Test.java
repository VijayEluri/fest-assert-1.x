/*
 * Created on Jan 12, 2011
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 * 
 * Copyright @2011-2013 the original author or authors.
 */
package org.fest.assertions;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.fest.util.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

/**
 * Tests for {@link ObjectArrayAssert#actualAsSet()}.
 *
 * @author Alex Ruiz
 */
public class ArrayAssert_actualAsSet_Test {
  private static IntArrayAssert assertions;

  @BeforeClass
  public static void setUpOnce() {
    int[] actual = {6, 8, 10};
    assertions = new IntArrayAssert(actual);
  }

  @Test
  public void should_return_Set_with_contents_in_actual() {
    List<Object> list = newArrayList(assertions.actualAsSet());
    int size = assertions.actual.length;
    for (int i = 0; i < size; i++) {
      assertEquals(new Integer(assertions.actual[i]), list.get(i));
    }
  }
}
