/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shardingsphere.sharding.route.spi;

import org.apache.shardingsphere.spi.NewInstanceServiceLoader;

import java.util.Collection;
import java.util.Date;

/**
 * Time service for SPI.
 *
 * @author chenchuangliu
 */
public final class SPITimeService implements TimeService {
    
    private final Collection<TimeService> timeServices = NewInstanceServiceLoader.newServiceInstances(TimeService.class);
    
    static {
        NewInstanceServiceLoader.register(TimeService.class);
    }
    
    @Override
    public Date getTime() {
        Date result = null;
        for (TimeService server : timeServices) {
            result = server.getTime();
            if (!(server instanceof DefaultTimeService) && null != result) {
                return result;
            }
        }
        return result;
    }
}
