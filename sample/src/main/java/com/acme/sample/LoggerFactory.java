
package com.acme.sample;

import java.util.logging.Logger;


public interface LoggerFactory {
    static Logger getLogger(Class type) {
        return Logger.getLogger(type.getName());
    }
}
