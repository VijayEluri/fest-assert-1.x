/*
 * Created on Feb 16, 2008
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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static java.lang.Double.valueOf;
import static java.lang.Math.abs;
import static org.fest.assertions.ErrorMessages.*;
import static org.fest.assertions.Formatting.format;

/**
 * Assertions for {@code Double}s and {@code double}s.
 * <p/>
 * To create a new instance of this class invoke {@link Assertions#assertThat(Double)} or
 * {@link Assertions#assertThat(double)}.
 *
 * @author Yvonne Wang
 * @author David DIDIER
 * @author Alex Ruiz
 * @author Ansgar Konermann
 */
public class DoubleAssert extends GenericAssert<DoubleAssert, Double> implements NumberAssert {
  private static final double ZERO = 0.0;

  /**
   * Creates a new {@link DoubleAssert}.
   *
   * @param actual the actual value to verify.
   */
  protected DoubleAssert(double actual) {
    super(DoubleAssert.class, actual);
  }

  /**
   * Creates a new {@link DoubleAssert}.
   *
   * @param actual the actual value to verify.
   */
  protected DoubleAssert(@Nullable Double actual) {
    super(DoubleAssert.class, actual);
  }

  /**
   * Creates a new holder for a delta value to be used in
   * {@link DoubleAssert#isEqualTo(double, org.fest.assertions.DoubleAssert.Delta)}.
   *
   * @param d the delta value.
   * @return a new delta value holder.
   * @deprecated use method {@link org.fest.assertions.Delta#delta(double)} instead. This method will be removed in
   *             version 2.0.
   */
  @Deprecated
  public static @NotNull Delta delta(double d) {
    return new Delta(d);
  }

  /**
   * Verifies that the actual {@code Double} is equal to the given one.
   *
   * @param expected the value to compare the actual one to.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not equal to the given one.
   */
  public @NotNull DoubleAssert isEqualTo(double expected) {
    return isEqualTo(valueOf(expected));
  }

  /**
   * Verifies that the actual {@code Double} is equal to the given one, within a positive delta.
   *
   * @param expected the value to compare the actual one to.
   * @param delta    the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not equal to the given one.
   * @deprecated use method {@link #isEqualTo(double, org.fest.assertions.Delta)} instead. This method will be removed
   *             in version 2.0.
   */
  @Deprecated
  public @NotNull DoubleAssert isEqualTo(double expected, Delta delta) {
    return isEqualTo(expected, delta.value);
  }

  /**
   * Verifies that the actual {@code Double} is equal to the given one, within a positive delta.
   *
   * @param expected the value to compare the actual one to.
   * @param delta    the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not equal to the given one.
   * @since 1.1
   */
  public @NotNull DoubleAssert isEqualTo(double expected, org.fest.assertions.Delta delta) {
    return isEqualTo(expected, delta.doubleValue());
  }

  private @NotNull DoubleAssert isEqualTo(double expected, double deltaValue) {
    return isEqualTo(valueOf(expected), deltaValue);
  }

  /**
   * Verifies that the actual {@code Double} is equal to the given one, within a positive delta.
   *
   * @param expected the value to compare the actual one to.
   * @param delta    the given delta.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not equal to the given one.
   * @since 1.3
   */
  public @NotNull DoubleAssert isEqualTo(@Nullable Double expected, @NotNull org.fest.assertions.Delta delta) {
    return isEqualTo(expected, delta.doubleValue());
  }

  private @NotNull DoubleAssert isEqualTo(@Nullable Double expected, double deltaValue) {
    if (actual == null || expected == null) {
      return isEqualTo(expected);
    }
    if (actual.compareTo(expected) == 0) {
      return this;
    }
    if (abs(expected - actual) <= deltaValue) {
      return this;
    }
    failIfCustomMessageIsSet();
    throw failure(unexpectedNotEqual(actual, expected) + format(" using delta:<%s>", deltaValue));
  }

  /**
   * Verifies that the actual {@code Double} is not equal to the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is equal to the given one.
   */
  public @NotNull DoubleAssert isNotEqualTo(double other) {
    if (compareTo(other) != 0) {
      return this;
    }
    failIfCustomMessageIsSet();
    throw failure(unexpectedEqual(actual, other));
  }

  /**
   * Verifies that the actual {@code Double} is greater than the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not greater than the given one.
   */
  public @NotNull DoubleAssert isGreaterThan(double other) {
    if (compareTo(other) > 0) {
      return this;
    }
    failIfCustomMessageIsSet();
    throw failure(unexpectedLessThanOrEqualTo(actual, other));
  }

  /**
   * Verifies that the actual {@code Double} is less than the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not less than the given one.
   */
  public @NotNull DoubleAssert isLessThan(double other) {
    if (compareTo(other) < 0) {
      return this;
    }
    failIfCustomMessageIsSet();
    throw failure(unexpectedGreaterThanOrEqualTo(actual, other));
  }

  /**
   * Verifies that the actual {@code Double} is greater or equal to the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not greater than or equal to the given one.
   */
  public @NotNull DoubleAssert isGreaterThanOrEqualTo(double other) {
    if (compareTo(other) >= 0) {
      return this;
    }
    failIfCustomMessageIsSet();
    throw failure(unexpectedLessThan(actual, other));
  }

  /**
   * Verifies that the actual {@code Double} is less or equal to the given one.
   *
   * @param other the given value.
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not less than or equal to the given one.
   */
  public @NotNull DoubleAssert isLessThanOrEqualTo(double other) {
    if (compareTo(other) <= 0) {
      return this;
    }
    failIfCustomMessageIsSet();
    throw failure(unexpectedGreaterThan(actual, other));
  }

  private int compareTo(double other) {
    return Double.compare(actual, other);
  }

  /**
   * Verifies that the actual {@code Double} is equal to zero.
   *
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not equal to zero.
   */
  @Override
  public @NotNull DoubleAssert isZero() {
    return isEqualTo(ZERO);
  }

  /**
   * Verifies that the actual {@code Double} is positive.
   *
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not positive.
   */
  @Override
  public @NotNull DoubleAssert isPositive() {
    return isGreaterThan(ZERO);
  }

  /**
   * Verifies that the actual {@code Double} is negative.
   *
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not negative.
   */
  @Override
  public @NotNull DoubleAssert isNegative() {
    return isLessThan(ZERO);
  }

  /**
   * Verifies that the actual {@code Double} is equal to {@link Double#NaN}.
   *
   * @return this assertion object.
   * @throws AssertionError if the actual {@code Double} is not equal to {@code NAN}.
   */
  public @NotNull DoubleAssert isNaN() {
    return isEqualTo(Double.NaN);
  }

  /**
   * Holds a delta value to be used in {@link DoubleAssert#isEqualTo(double, org.fest.assertions.DoubleAssert.Delta)}.
   *
   * @deprecated use top-level class {@link org.fest.assertions.Delta} instead. This class will be removed in version
   *             2.0.
   */
  @Deprecated
  public static class Delta {
    final double value;

    private Delta(double value) {
      this.value = value;
    }
  }
}
