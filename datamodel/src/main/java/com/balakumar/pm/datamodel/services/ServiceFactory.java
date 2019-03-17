package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.spring.DataModelConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class ServiceFactory {

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataModelConfig.class);

    private ServiceFactory() {
    }

    public static ScripService getScripService() {
        return (ScripService) context.getBean("scripService");
    }

    public static UserService getUserService() {
        return (UserService) context.getBean("userService");
    }

    public static PortfolioService getPortfolioService() {
        return (PortfolioService) context.getBean("portfolioService");
    }

}
