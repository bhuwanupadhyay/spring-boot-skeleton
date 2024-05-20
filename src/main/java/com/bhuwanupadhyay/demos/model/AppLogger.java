package com.bhuwanupadhyay.demos.model;

import com.bhuwanupadhyay.demos.AppProperties;
import com.google.common.base.Throwables;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@SuppressWarnings("preview")
@Component
@RequestScope
public class AppLogger {

    private final static Logger log = LoggerFactory.getLogger(AppLogger.class);
    private final AppHeaders headers;
    private final String logPrefix;

    public AppLogger(AppHeaders headers, AppProperties configurationProperties) {
        this.headers = headers;
        this.logPrefix = configurationProperties.getLogPrefix();
    }

    public void audit(final String message) {
        this.writeEntry(Level.INFO, STR."\{logPrefix}.audit", message);
    }

    public void request(final Request request) {
        String text = String.format("#RequestLog %s", request);
        this.writeEntry(Level.INFO, STR."\{logPrefix}.request", text);
    }

    public void info(final String message) {
        this.writeEntry(Level.INFO, STR."\{logPrefix}.app", message);
    }

    public void debug(final String message) {
        this.writeEntry(Level.DEBUG, STR."\{logPrefix}.app", message);
    }

    public void warning(final String message) {
        this.writeEntry(Level.WARN, STR."\{logPrefix}.app", message);
    }

    public void warning(final String message, final Exception ex) {
        String exString = Throwables.getStackTraceAsString(ex);
        String text = String.format("%s\n%s", message, exString);
        this.writeEntry(Level.WARN, STR."\{logPrefix}.app", text);
    }

    public void error(final String message) {
        this.writeEntry(Level.ERROR, STR."\{logPrefix}.app", message);
    }

    public void error(final String message, final Exception ex) {
        String exString = Throwables.getStackTraceAsString(ex);
        String text = String.format("%s\n%s", message, exString);
        this.writeEntry(Level.ERROR, STR."\{logPrefix}.app", text);
    }

    private void writeEntry(Level severity, String loggerName, String text) {
        Map<String, String> labels = AppHeaders.createStandardLabelsFromMap(headers.getHeaders());
        log.atLevel(severity).log(() -> String.format("%s: %s %s", loggerName, text, labels));
    }

}
