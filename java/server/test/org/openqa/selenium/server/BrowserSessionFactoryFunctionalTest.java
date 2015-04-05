// Licensed to the Software Freedom Conservancy (SFC) under one
// or more contributor license agreements.  See the NOTICE file
// distributed with this work for additional information
// regarding copyright ownership.  The SFC licenses this file
// to you under the Apache License, Version 2.0 (the
// "License"); you may not use this file except in compliance
// with the License.  You may obtain a copy of the License at
//
//   http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.


package org.openqa.selenium.server;


import static org.junit.Assert.fail;

import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.server.browserlaunchers.BrowserLauncherFactory;
import org.openqa.selenium.server.browserlaunchers.BrowserOptions;

public class BrowserSessionFactoryFunctionalTest {

  @Test
  public void testBrowserIsAutomaticallyCloseWhenTimingOutOnBrowserLaunch() {
    final BrowserSessionFactory factory;
    final RemoteControlConfiguration configuration;
    final Capabilities options = BrowserOptions.newBrowserOptions();

    factory = new BrowserSessionFactory(new BrowserLauncherFactory());
    configuration = new RemoteControlConfiguration();
    configuration.setTimeoutInSeconds(1);
    try {
      factory.createNewRemoteSession("*chrome", "http://amazon.com", "", options, false,
          configuration);
      fail("Did not catch a RemoteCommandException when timing out on browser launch.");
    } catch (RemoteCommandException e) {
      /* As expected */
    }
  }

}
