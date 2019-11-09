//package com.balakumar.pm.datamodel;
//
//import com.balakumar.pm.datamodel.services.*;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.PropertySource;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.transaction.annotation.EnableTransactionManagement;
//
//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories(basePackages = {"com.balakumar.pm.datamodel.services"})
//@EnableAutoConfiguration
//@PropertySource(value = {"classpath:application.properties"})
//public class DataModelConfig {
//
//    @Bean
//    public UserService userService(UserService userService) {
//        return userService;
//    }
//
//    @Bean
//    public ScripService scripService(ScripService scripService) {
//        return scripService;
//    }
//
//    @Bean
//    public PortfolioService portfolioService(PortfolioService portfolioService) {
//        return portfolioService;
//    }
//
//    @Bean
//    public TransactionService transactionService(TransactionService transactionService) {
//        return transactionService;
//    }
//
//    @Bean
//    public ScripHistoryService scripHistoryService(ScripHistoryService scripHistoryService) {
//        return scripHistoryService;
//    }
//}
