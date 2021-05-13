package com.company.im.chat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum LoggerSystem {
        /**
         * 异常日志
         */
        EXCEPTION;

        public Logger getLogger() {
            return LoggerFactory.getLogger(this.name());
        }

}
