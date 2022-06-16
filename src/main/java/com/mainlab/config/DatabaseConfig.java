package com.mainlab.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.io.IOException;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DatabaseConfig {

    @Autowired
    ApplicationContext applicationContext;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Bean(name = "dataSource")
    public DataSource dataSource() throws PropertyVetoException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver", false, this.getClass().getClassLoader());
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setDriverClass(driverClassName);
        dataSource.setJdbcUrl(url);
        dataSource.setUser(username);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws PropertyVetoException, ClassNotFoundException {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) throws IOException {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setConfigLocation(applicationContext.getResource("classpath:mybatis-config.xml"));
        factoryBean.setMapperLocations(applicationContext.getResources("classpath:sqlmap/**/*Sql.xml"));
        return factoryBean;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        return sqlSessionFactoryBean(dataSource()).getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

}
