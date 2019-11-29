package com.mplus.common.listeners;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.PrePersist;

@Slf4j
public class BaseEntityListener {

    @PrePersist
    public void prePersist(Object source) {
      log.info("enter prePersist listener...");
    }
}
