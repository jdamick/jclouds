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
package org.jclouds.rackspace.cloudloadbalancers.features;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertNull;

import java.net.URI;

import javax.ws.rs.core.MediaType;

import org.jclouds.http.HttpResponse;
import org.jclouds.rackspace.cloudloadbalancers.CloudLoadBalancersApi;
import org.jclouds.rackspace.cloudloadbalancers.domain.HealthMonitor;
import org.jclouds.rackspace.cloudloadbalancers.internal.BaseCloudLoadBalancerApiExpectTest;
import org.testng.annotations.Test;

/**
 * @author Everett Toews
 */
@Test(groups = "unit")
public class HealthMonitorApiExpectTest extends BaseCloudLoadBalancerApiExpectTest<CloudLoadBalancersApi> {
   public void testGetHealthMonitor() {
      URI endpoint = URI.create("https://dfw.loadbalancers.api.rackspacecloud.com/v1.0/123123/loadbalancers/2000/healthmonitor");
      HealthMonitorApi api = requestsSendResponses(
            rackspaceAuthWithUsernameAndApiKey,
            responseWithAccess, 
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResource("/healthmonitor-get.json")).build()
      ).getHealthMonitorApiForZoneAndLoadBalancer("DFW", 2000);

      HealthMonitor healthMonitor = api.get();
      assertEquals(healthMonitor, getConnectHealthMonitor());
   }

   public void testGetDeletedHealthMonitor() {
      URI endpoint = URI.create("https://dfw.loadbalancers.api.rackspacecloud.com/v1.0/123123/loadbalancers/2000/healthmonitor");
      HealthMonitorApi api = requestsSendResponses(
            rackspaceAuthWithUsernameAndApiKey,
            responseWithAccess, 
            authenticatedGET().endpoint(endpoint).build(),
            HttpResponse.builder().statusCode(200).payload(payloadFromResource("/healthmonitor-get-deleted.json")).build()
      ).getHealthMonitorApiForZoneAndLoadBalancer("DFW", 2000);

      HealthMonitor healthMonitor = api.get();
      assertNull(healthMonitor);
   }

   public void testCreateHealthMonitor() {
      URI endpoint = URI.create("https://dfw.loadbalancers.api.rackspacecloud.com/v1.0/123123/loadbalancers/2000/healthmonitor");
      HealthMonitorApi api = requestsSendResponses(
            rackspaceAuthWithUsernameAndApiKey,
            responseWithAccess, 
            authenticatedGET().method("PUT").endpoint(endpoint).payload(payloadFromResource("/healthmonitor-create.json")).build(),
            HttpResponse.builder().statusCode(200).build()
      ).getHealthMonitorApiForZoneAndLoadBalancer("DFW", 2000);
      
      api.createOrUpdate(getConnectHealthMonitor());
   }

   public void testRemoveHealthMonitor() {
      URI endpoint = URI.create("https://dfw.loadbalancers.api.rackspacecloud.com/v1.0/123123/loadbalancers/2000/healthmonitor");
      HealthMonitorApi api = requestsSendResponses(
            rackspaceAuthWithUsernameAndApiKey,
            responseWithAccess, 
            authenticatedGET().method("DELETE").endpoint(endpoint).replaceHeader("Accept", MediaType.WILDCARD).build(),
            HttpResponse.builder().statusCode(200).build()
      ).getHealthMonitorApiForZoneAndLoadBalancer("DFW", 2000);

      assertTrue(api.remove());
   }

   public void testValidConnectHealthMonitor() {
      assertTrue(getConnectHealthMonitor().isValid());
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testInvalidConnectHealthMonitorWithoutRequirements() {
      HealthMonitor.builder().type(HealthMonitor.Type.CONNECT).build();      
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testInvalidConnectHealthMonitorWithUnrequired() {
      HealthMonitor.builder()
            .type(HealthMonitor.Type.CONNECT)
            .delay(3599)
            .timeout(30)
            .attemptsBeforeDeactivation(2)
            .path("/foobar")
            .build();
   }
   
   public void testValidHTTPHealthMonitor() {
      assertTrue(getHTTPHealthMonitor().isValid());
   }
   
   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testInvalidHTTPHealthMonitorWithoutRequirements() {
      HealthMonitor.builder().type(HealthMonitor.Type.HTTP).build();      
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testInvalidHTTPHealthMonitorWithoutUnrequired() {
      HealthMonitor.builder()
            .type(HealthMonitor.Type.HTTP)
            .delay(3599)
            .path("/foobar")
            .build();
   }

   @Test(expectedExceptions = IllegalArgumentException.class)
   public void testInvalidHTTPHealthMonitorWithoutRegex() {
      HealthMonitor.builder()
      .type(HealthMonitor.Type.HTTP)
      .delay(3599)
      .timeout(30)
      .attemptsBeforeDeactivation(2)
      .path("/foobar")
      .build();
   }

   public static HealthMonitor getConnectHealthMonitor() {
      HealthMonitor healthMonitor = HealthMonitor.builder()
            .type(HealthMonitor.Type.CONNECT)
            .delay(3599)
            .timeout(30)
            .attemptsBeforeDeactivation(2)
            .build();

      return healthMonitor;
   }

   public static HealthMonitor getHTTPHealthMonitor() {
      HealthMonitor healthMonitor = HealthMonitor.builder()
            .type(HealthMonitor.Type.HTTP)
            .delay(3599)
            .timeout(30)
            .attemptsBeforeDeactivation(2)
            .path("/foobar")
            .bodyRegex("foo.*bar")
            .build();

      return healthMonitor;
   }
}
