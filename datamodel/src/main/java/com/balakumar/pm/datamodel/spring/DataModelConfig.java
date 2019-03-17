package com.balakumar.pm.datamodel.spring;

import com.balakumar.pm.datamodel.services.PortfolioService;
import com.balakumar.pm.datamodel.services.ScripService;
import com.balakumar.pm.datamodel.services.UserService;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = {"com.balakumar.pm.datamodel.services"})
@PropertySource(value = {"classpath:application-config.properties"})
public class DataModelConfig {

    private final String PROPERTY_SHOW_SQL = "hibernate.show_sql";
    private final String PROPERTY_DIALECT = "hibernate.dialect";
    private final String PROPERTY_META_DEFAULT = "hibernate.temp.use_jdbc_metadata_defaults";
    private final String PROPERTY_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    @Value("${db.driver}")
    private String dbDriver;
    @Value("${db.url}")
    private String dbUrl;
    @Value("${db.username}")
    private String dbUsername;
    @Value("${db.password}")
    private String dbPassword;
    @Value("${" + PROPERTY_SHOW_SQL + "}")
    private String hibernateShowSql;
    @Value("${" + PROPERTY_DIALECT + "}")
    private String hibernateDialect;
    @Value("${" + PROPERTY_META_DEFAULT + "}")
    private String hibernateMetaDefault;
    @Value("${" + PROPERTY_HBM2DDL_AUTO + "}")
    private String hibernateHbm2ddlAuto;

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(dbDriver);
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(dbUsername);
        dataSource.setPassword(dbPassword);
        return dataSource;
    }

    @Bean
    public Properties hibernateProps() {
        Properties properties = new Properties();
        properties.setProperty(PROPERTY_DIALECT, hibernateDialect);
        properties.setProperty(PROPERTY_SHOW_SQL, hibernateShowSql);
        properties.setProperty(PROPERTY_META_DEFAULT, hibernateMetaDefault);
        properties.setProperty(PROPERTY_HBM2DDL_AUTO, hibernateHbm2ddlAuto);
        return properties;
    }


    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource, Properties hibernateProps) {
        LocalContainerEntityManagerFactoryBean lfb = new LocalContainerEntityManagerFactoryBean();
        lfb.setDataSource(dataSource);
        lfb.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        lfb.setPackagesToScan("com.balakumar.pm.datamodel.objects");
        lfb.setJpaProperties(hibernateProps);
        return lfb;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public UserService userService(UserService userService) {
        return userService;
    }

    @Bean
    public ScripService scripService(ScripService scripService) {
        return scripService;
    }

    @Bean
    public PortfolioService portfolioService(PortfolioService portfolioService) {
        return portfolioService;
    }
}
