package com.balakumar.pm.datamodel.services;

public enum Service {
    USER("userService"),
    SCRIP("scripService"),
    TRANSACTION("transactionService"),
    PORTFOLIO("portfolioService"),
    SCRIP_HISTORY("scripHistoryService");

    private String serviceName;

    Service(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceName() {
        return serviceName;
    }
}
