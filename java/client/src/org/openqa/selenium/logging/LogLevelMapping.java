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

package org.openqa.selenium.logging;

import static com.google.common.base.Strings.isNullOrEmpty;

import com.google.common.collect.ImmutableMap;

import java.util.logging.Level;

public class LogLevelMapping {

  /**
   *  WebDriver log level DEBUG which is mapped to Level.FINE.
   */
  private static final String DEBUG = "DEBUG";

  private static ImmutableMap<Integer, Level> levelMap;

  static {
    Level[] supportedLevels = new Level[] {
      Level.ALL,
      Level.FINE,
      Level.INFO,
      Level.WARNING,
      Level.SEVERE,
      Level.OFF
    };
    ImmutableMap.Builder<Integer, Level> builder = ImmutableMap.builder();
    for (Level level : supportedLevels) {
      builder.put(level.intValue(), level);
    }
    levelMap = builder.build();
  }

  /**
   * Normalizes the given level to one of those supported by Selenium.
   */
  public static Level normalize(Level level) {
    if (levelMap.containsKey(level.intValue())) {
      return levelMap.get(level.intValue());
    } else if (level.intValue() >= Level.SEVERE.intValue()) {
      return Level.SEVERE;
    } else if (level.intValue() >= Level.WARNING.intValue()) {
      return Level.WARNING;
    } else if (level.intValue() >= Level.INFO.intValue()) {
      return Level.INFO;
    } else {
      return Level.FINE;
    }
  }

  /**
   * Converts the JDK level to a name supported by Selenium.
   */
  public static String getName(Level level) {
    Level normalized = normalize(level);
    return normalized == Level.FINE ? DEBUG : normalized.getName();
  }

  public static Level toLevel(String logLevelName) {
    if (isNullOrEmpty(logLevelName)) {
      // Default the log level to info.
      return Level.INFO;
    }

    if (logLevelName.equals(DEBUG)) {
      return Level.FINE;
    }
    return levelMap.get(Level.parse(logLevelName).intValue());
  }
}
