/*
 * Licensed to jclouds, Inc. (jclouds) under one or more
 * contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  jclouds licenses this file
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

package org.jclouds.googlecompute.parse;

import com.google.common.collect.ImmutableSet;
import org.jclouds.googlecompute.domain.ListPage;
import org.jclouds.googlecompute.domain.Network;
import org.jclouds.googlecompute.domain.Resource;
import org.jclouds.googlecompute.internal.BaseGoogleComputeParseTest;

import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import java.net.URI;

/**
 * @author David Alves
 */
public class ParseNetworkListTest extends BaseGoogleComputeParseTest<ListPage<Network>> {

   @Override
   public String resource() {
      return "/network_list.json";
   }

   @Override
   @Consumes(MediaType.APPLICATION_JSON)
   public ListPage<Network> expected() {
      return ListPage.<Network>builder()
              .kind(Resource.Kind.NETWORK_LIST)
              .id("projects/myproject/networks")
              .selfLink(URI.create("https://www.googleapis.com/compute/v1beta13/projects/myproject/networks"))
              .items(ImmutableSet.of(new ParseNetworkTest().expected()))
              .build();

   }
}
