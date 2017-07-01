package br.edu.satc.ec.erp.utils;

import java.util.logging.Logger;

import static java.util.logging.Level.WARNING;
import static java.util.logging.Logger.GLOBAL_LOGGER_NAME;

/**
 * Created by BRUNO-PC on 25/06/2017.
 */
public class LoggerUtils {

    public static void info(String info) {
        Logger.getLogger(GLOBAL_LOGGER_NAME).info(info);
    }

    public static void error(Exception e, String info) {
        Logger.getLogger(GLOBAL_LOGGER_NAME).log(WARNING, e.getMessage() + " - " + info);
    }

    public static void severe(String severe) {
        Logger.getLogger(GLOBAL_LOGGER_NAME).severe(severe);
    }
}
