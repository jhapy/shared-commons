/*
 * Copyright 2020-2020 the original author or authors from the JHapy project.
 *
 * This file is part of the JHapy project, see https://www.jhapy.org/ for more information.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jhapy.commons.config;

/**
 * JHapyConstants constants.
 */
public interface JHapyConstants {

  /**
   * Constant <code>SPRING_PROFILE_DEVELOPMENT="dev"</code>
   */
  String SPRING_PROFILE_DEVELOPMENT = "dev";
  /**
   * Constant <code>SPRING_PROFILE_TEST="test"</code>
   */
  String SPRING_PROFILE_TEST = "test";
  /**
   * Constant <code>SPRING_PROFILE_PRODUCTION="prod"</code>
   */
  String SPRING_PROFILE_PRODUCTION = "prod";
  /**
   * Spring profile used when deploying to Docker Swarm Constant <code>SPRING_PROFILE_DOCKER_SWARM="docker-swarm"</code>
   */
  String SPRING_PROFILE_DOCKER_SWARM = "docker-swarm";
  /**
   * Spring profile used to enable swagger Constant <code>SPRING_PROFILE_SWAGGER="swagger"</code>
   */
  String SPRING_PROFILE_SWAGGER = "swagger";
}
