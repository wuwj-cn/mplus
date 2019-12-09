/*
 * Copyright 2018-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mplus.common.listeners;

import com.mplus.common.entity.BaseEntity;
import com.mplus.common.enums.DataState;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Slf4j
public class BaseEntityListener {

    @PrePersist
    public void prePersist(BaseEntity entity) {
        log.info("enter prePersist listener...");
        entity.setCreateTime(LocalDateTime.now());
        entity.setModifyTime(LocalDateTime.now());
        entity.setDataState(DataState.NORMAL.code());
    }

    @PreUpdate
    public void preUpdate(BaseEntity entity) {
        log.info("enter preUpdate listener...");
        entity.setModifyTime(LocalDateTime.now());
    }
}
