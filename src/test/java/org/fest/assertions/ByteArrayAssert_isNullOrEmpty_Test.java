/*
 * Created on Feb 14, 2008
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
 * Copyright @2008-2013 the original author or authors.
 */
package org.fest.assertions;

import org.fest.test.ExpectedException;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;

import static org.fest.assertions.ArrayFactory.byteArray;
import static org.fest.assertions.EmptyArrays.emptyByteArray;
import static org.fest.test.ExpectedException.none;

/**
 * Tests for {@link ByteArrayAssert#isNullOrEmpty()}.
 *
 * @author Yvonne Wang
 * @author Alex Ruiz
 */
public class ByteArrayAssert_isNullOrEmpty_Test implements GroupAssert_isNullOrEmpty_TestCase {
  @Rule
  public ExpectedException thrown = none();

  private static byte[] array;

  @BeforeClass
  public static void setUpOnce() {
    array = byteArray(6);
  }

  @Override
  @Test
  public void should_pass_if_actual_is_null() {
    new ByteArrayAssert(null).isNullOrEmpty();
  }

  @Override
  @Test
  public void should_pass_if_actual_is_empty() {
    new ByteArrayAssert(emptyByteArray()).isNullOrEmpty();
  }

  @Override
  @Test
  public void should_fail_if_actual_has_content() {
    thrown.expect(AssertionError.class, "expecting null or empty, but was:<[6]>");
    new ByteArrayAssert(array).isNullOrEmpty();
  }

  @Override
  @Test
  public void should_fail_and_display_description_if_actual_has_content() {
    thrown.expect(AssertionError.class, "[A Test] expecting null or empty, but was:<[6]>");
    new ByteArrayAssert(array).as("A Test").isNullOrEmpty();
  }

  @Override
  @Test
  public void should_fail_with_custom_message_if_actual_has_content() {
    thrown.expect(AssertionError.class, "My custom message");
    new ByteArrayAssert(array).overridingErrorMessage("My custom message").isNullOrEmpty();
  }

  @Override
  @Test
  public void should_fail_with_custom_message_ignoring_description_if_actual_has_content() {
    thrown.expect(AssertionError.class, "My custom message");
    new ByteArrayAssert(array).as("A Test").overridingErrorMessage("My custom message").isNullOrEmpty();
  }
}
