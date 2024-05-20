package com.bhuwanupadhyay.demos.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "swagger")
public class SwaggerConfigurationProperties {

    private String apiTitle;
    private String apiDescription;
    private String apiVersion;
    private String apiContactName;
    private String apiContactEmail;
    private String apiLicenseName;
    private String apiLicenseUrl;
    private boolean apiServerFullUrlEnabled;

    public String getApiTitle() {
        return apiTitle;
    }

    public void setApiTitle(String apiTitle) {
        this.apiTitle = apiTitle;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public void setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiContactName() {
        return apiContactName;
    }

    public void setApiContactName(String apiContactName) {
        this.apiContactName = apiContactName;
    }

    public String getApiContactEmail() {
        return apiContactEmail;
    }

    public void setApiContactEmail(String apiContactEmail) {
        this.apiContactEmail = apiContactEmail;
    }

    public String getApiLicenseName() {
        return apiLicenseName;
    }

    public void setApiLicenseName(String apiLicenseName) {
        this.apiLicenseName = apiLicenseName;
    }

    public String getApiLicenseUrl() {
        return apiLicenseUrl;
    }

    public void setApiLicenseUrl(String apiLicenseUrl) {
        this.apiLicenseUrl = apiLicenseUrl;
    }
}
