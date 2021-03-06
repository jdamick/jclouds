/**
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
package org.jclouds.dynect.v3.internal;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

import org.jclouds.dynect.v3.DynECTApi;
import org.jclouds.http.HttpRequest;
import org.jclouds.io.Payload;
import org.jclouds.io.Payloads;

/**
 * Base class for writing DynECT Expect tests
 * 
 * @author Adrian Cole
 */
public class BaseDynECTApiExpectTest extends BaseDynECTExpectTest<DynECTApi> {

   public static Payload emptyJsonPayload() {
      Payload p = Payloads.newByteArrayPayload(new byte[] {});
      p.getContentMetadata().setContentType(APPLICATION_JSON);
      return p;
   }

   @Override
   protected HttpRequestComparisonType compareHttpRequestAsType(HttpRequest input) {
      return HttpRequestComparisonType.JSON;
   }
}
