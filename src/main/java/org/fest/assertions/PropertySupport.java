/*
 * Created on Jun 26, 2010
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

import static java.util.Collections.emptyList;
import static org.fest.util.Collections.isNullOrEmpty;
import static org.fest.util.Collections.nonNullElementsIn;
import static org.fest.util.Introspection.getProperty;
import static org.fest.util.Strings.quote;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.fest.util.IntrospectionError;
import org.fest.util.Preconditions;
import org.fest.util.VisibleForTesting;

/**
 * Utility methods for properties access.
 * 
 * @author Joel Costigliola
 * @author Alex Ruiz
 * 
 * @since 1.3
 */
final class PropertySupport {
  private static final String SEPARATOR = ".";

  private static final PropertySupport INSTANCE = new PropertySupport();

  static @Nonnull PropertySupport instance() {
    return INSTANCE;
  }

  private final JavaBeanDescriptor javaBeanDescriptor;

  private PropertySupport() {
    this(new JavaBeanDescriptor());
  }

  PropertySupport(@Nonnull JavaBeanDescriptor javaBeanDescriptor) {
    this.javaBeanDescriptor = javaBeanDescriptor;
  }

  /**
   * <p>
   * Returns a list containing the values of the given property name, from the elements of the given collection. If the
   * given collection is empty or {@code null}, this method will return an empty collection.
   * </p>
   * 
   * <p>
   * For example, given the nested property "address.street.number", this method will:
   * <ol>
   * <li>extract a collection of "address" from the given collection (remaining property is 'street.number')</li>
   * <li>extract a collection of "street" from the "address" collection (remaining property is 'number')</li>
   * <li>extract a collection of "number" from the "street" collection</li>
   * </ol>
   * </p>
   * 
   * @param propertyName the name of the property. It may be a nested property.
   * @param target the given collection.
   * @return a list containing the values of the given property name, from the elements of the given collection.
   * @throws NullPointerException if given property name is {@code null}.
   * @throws IntrospectionError if an element in the given collection does not have a matching property.
   */
  @Nonnull List<Object> propertyValues(@Nonnull String propertyName, @Nullable Collection<?> target) {
    if (isNullOrEmpty(target)) {
      return emptyList();
    }
    // ignore null elements as we can't extract a property from a null object
    Collection<?> nonNullElements = nonNullElementsIn(target);
    if (isNestedProperty(propertyName)) {
      String firstProperty = firstPropertyIfNested(propertyName);
      List<Object> firstPropertyValues = propertyValues(firstProperty, nonNullElements);
      // extract next sub property values until reaching a last sub property
      return propertyValues(removeFirstPropertyIfNested(propertyName), firstPropertyValues);
    }
    return simplePropertyValues(propertyName, nonNullElements);
  }

  private List<Object> simplePropertyValues(String propertyName, Collection<?> target) {
    List<Object> propertyValues = new ArrayList<Object>();
    for (Object e : target) {
      propertyValues.add(propertyValue(propertyName, e));
    }
    return propertyValues;
  }

  /**
   * <p>
   * Returns {@code true} if property is nested, {@code false} otherwise.
   * </p>
   * 
   * <p>
   * Examples:
   * <pre>
   * isNestedProperty(&quot;address.street&quot;); // true
   * isNestedProperty(&quot;address.street.name&quot;); // true
   * isNestedProperty(&quot;person&quot;); // false
   * isNestedProperty(&quot;.name&quot;); // false
   * isNestedProperty(&quot;person.&quot;); // false
   * isNestedProperty(&quot;person.name.&quot;); // false
   * isNestedProperty(&quot;.person.name&quot;); // false
   * isNestedProperty(&quot;.&quot;); // false
   * isNestedProperty(&quot;&quot;); // false
   * </pre>
   * </p>
   * 
   * @param propertyName the given property name.
   * @return {@code true} if property is nested, {@code false} otherwise.
   * @throws NullPointerException if given property name is {@code null}.
   */
  boolean isNestedProperty(String propertyName) {
    Preconditions.checkNotNull(propertyName);
    return propertyName.contains(SEPARATOR) && !propertyName.startsWith(SEPARATOR) && !propertyName.endsWith(SEPARATOR);
  }

  /**
   * Removes the first property from the given property name only if the given property name belongs to a nested
   * property. For example, given the nested property "address.street.name", this method will return "street.name". This
   * method returns an empty {@code String} if the given property name does not belong to a nested property.
   * 
   * @param propertyName the given property name.
   * @return the given property name without its first property, if the property name belongs to a nested property;
   *         otherwise, it will return an empty {@code String}.
   * @throws NullPointerException if given property name is {@code null}.
   */
  @Nonnull String removeFirstPropertyIfNested(@Nonnull String propertyName) {
    if (!isNestedProperty(propertyName)) {
      return "";
    }
    return propertyName.substring(propertyName.indexOf(SEPARATOR) + 1);
  }

  /**
   * Returns the first property from the given property name only if the given property name belongs to a nested
   * property. For example, given the nested property "address.street.name", this method will return "address". This
   * method returns the given property name unchanged if it does not belong to a nested property.
   * 
   * @param propertyName the given property name.
   * @return the first property from the given property name, if the property name belongs to a nested property;
   *         otherwise, it will return the given property name unchanged.
   * @throws NullPointerException if given property name is {@code null}.
   */
  @Nonnull String firstPropertyIfNested(@Nonnull String propertyName) {
    if (!isNestedProperty(propertyName)) {
      return propertyName;
    }
    return propertyName.substring(0, propertyName.indexOf(SEPARATOR));
  }

  private Object propertyValue(@Nonnull String propertyName, @Nonnull Object target) {
    PropertyDescriptor descriptor = getProperty(propertyName, target);
    return propertyValue(descriptor, propertyName, target);
  }

  @VisibleForTesting
  @Nullable Object propertyValue(
      @Nonnull PropertyDescriptor descriptor, @Nonnull String propertyName, @Nonnull Object target) {
    try {
      return javaBeanDescriptor.invokeReadMethod(descriptor, target);
    } catch (Exception e) {
      throw new IntrospectionError("Unable to obtain the value in property " + quote(propertyName), e);
    }
  }
}
