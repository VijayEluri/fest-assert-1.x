/*
 * Created on Jul 1, 2010
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
 * Copyright @2010-2013 the original author or authors.
 */
package org.fest.assertions;

import org.fest.test.ExpectedException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;

import static java.util.Collections.emptyList;
import static org.fest.assertions.FailureMessages.actualIsNull;
import static org.fest.test.ExpectedException.none;
import static org.fest.util.Lists.newArrayList;

/**
 * Tests for {@link ObjectGroupAssert#doesNotHaveDuplicates()}.
 *
 * @author Yvonne Wang
 */
public final class ObjectGroupAssert_doesNotHaveDuplicates_Test implements GroupAssert_doesNotHaveDuplicates_TestCase {
  @Rule
  public ExpectedException thrown = none();

  private List<?> actual;
  private TestObjectGroupAssert assertions;

  @Before
  public void setUp() {
    actual = newArrayList("Luke", "Yoda", "Luke");
    assertions = new TestObjectGroupAssert(actual);
  }

  @Override
  @Test
  public final void should_pass_if_actual_does_not_contain_duplicates() {
    new TestObjectGroupAssert(newArrayList("Gandalf", "Frodo", "Sam")).doesNotHaveDuplicates();
  }

  @Override
  @Test
  public final void should_pass_if_actual_is_empty() {
    new TestObjectGroupAssert(emptyList()).doesNotHaveDuplicates();
  }

  @Override
  @Test
  public final void should_fail_if_actual_is_null() {
    thrown.expect(AssertionError.class, actualIsNull());
    new TestObjectGroupAssert(null).doesNotHaveDuplicates();
  }

  @Override
  @Test
  public final void should_fail_and_display_description_if_actual_is_null() {
    thrown.expect(AssertionError.class, actualIsNull("A Test"));
    new TestObjectGroupAssert(null).as("A Test").doesNotHaveDuplicates();
  }

  @Override
  @Test
  public final void should_fail_if_actual_has_duplicates() {
    thrown.expect(AssertionError.class, "<['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>");
    assertions.doesNotHaveDuplicates();
  }

  @Override
  @Test
  public final void should_fail_and_display_description_if_actual_has_duplicates() {
    String message = "[A Test] <['Luke', 'Yoda', 'Luke']> contains duplicate(s):<['Luke']>";
    thrown.expect(AssertionError.class, message);
    assertions.as("A Test").doesNotHaveDuplicates();
  }

  @Override
  @Test
  public final void should_fail_with_custom_message_if_actual_has_duplicates() {
    thrown.expect(AssertionError.class, "My custom message");
    assertions.overridingErrorMessage("My custom message").doesNotHaveDuplicates();
  }

  @Override
  @Test
  public final void should_fail_with_custom_message_ignoring_description_if_actual_has_duplicates() {
    thrown.expect(AssertionError.class, "My custom message");
    assertions.as("A Test").overridingErrorMessage("My custom message").doesNotHaveDuplicates();
  }
}
