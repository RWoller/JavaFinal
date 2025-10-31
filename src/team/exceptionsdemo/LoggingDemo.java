package team.exceptionsdemo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoggingDemo {
    private static Logger log = LogManager.getLogger(LoggingDemo.class);
    public static void main(String[] args) {
        log.info("Hello World!");
        try {
            int x = 5/0;
        } catch (Exception e) {
            log.error("Error", e);
        }
        log.warn("Warning example");
        log.info(0);
    }
}
