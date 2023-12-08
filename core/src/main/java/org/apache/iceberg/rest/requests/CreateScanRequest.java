/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.iceberg.rest.requests;

import java.util.List;
import java.util.Map;
import org.apache.iceberg.relocated.com.google.common.base.MoreObjects;
import org.apache.iceberg.relocated.com.google.common.base.Preconditions;
import org.apache.iceberg.relocated.com.google.common.collect.Maps;
import org.apache.iceberg.rest.RESTRequest;

public class CreateScanRequest implements RESTRequest {
  private List<String> select;
  private String filter;
  private Map<String, String> options = Maps.newHashMap();

  public CreateScanRequest() {}

  private CreateScanRequest(List<String> select, String filter) {
    this.select = select;
    this.filter = filter;
    validate();
  }

  @Override
  public void validate() {
    Preconditions.checkArgument(
        select != null && !select.isEmpty(), "Invalid select: should not be null or empty");
    Preconditions.checkArgument(filter != null, "Invalid filter: null");
    Preconditions.checkArgument(
        options != null && !options.isEmpty(), "Invalid options: should not be null or empty");
  }

  public List<String> select() {
    return select;
  }

  public String filter() {
    return filter;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("select", select)
        .add("filter", filter)
        .add("options", options)
        .toString();
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private List<String> select;
    private String filter;

    private Builder() {}

    public Builder withSelect(List<String> newSelect) {
      Preconditions.checkNotNull(newSelect, "Invalid select: null");
      this.select = newSelect;
      return this;
    }

    public Builder withFilter(String newFilter) {
      this.filter = newFilter;
      return this;
    }

    public CreateScanRequest build() {
      return new CreateScanRequest(select, filter);
    }
  }
}
