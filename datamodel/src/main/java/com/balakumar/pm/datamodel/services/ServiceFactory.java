package com.balakumar.pm.datamodel.services;

import com.balakumar.pm.datamodel.DataModelConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public final class ServiceFactory {

    private static AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DataModelConfig.class);

    private ServiceFactory() {
    }

    public static <T> T getService(Service service) {
        return (T) context.getBean(service.getServiceName());
    }

}
