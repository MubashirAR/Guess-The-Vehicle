package com.xmlscanner.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

@Configuration
@ComponentScan("com.xmlscanner")
@EnableTransactionManagement
public class ApplicationContextConfig {
	
	@Bean(name = "dataSource")
	public DataSource getH2DataSource() {

		DriverManagerDataSource dataSource = new DriverManagerDataSource();
			
		dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");

		dataSource.setDriverClassName("oracle.jdbc.driver.OracleDriver");

		dataSource.setUsername("demo");
		dataSource.setPassword("demo");
		
		
		return dataSource;
	}
	
	private Properties getHibernateProperties() {
		Properties properties = new Properties();
		
		properties.put("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
		properties.put("hibernate.show_sql", "true");
		properties.put("hibernate.format_sql", "true");
		return properties;
	}
	
	@Autowired
	@Bean(name = "sessionFactory")
	public SessionFactory getSessionFactory(DataSource dataSource) {

		LocalSessionFactoryBuilder sessionBuilder = new LocalSessionFactoryBuilder(dataSource);
		sessionBuilder.addProperties(getHibernateProperties());
		sessionBuilder.scanPackages("com.xmlscanner");

		
		return sessionBuilder.buildSessionFactory();
	}
	
	@Autowired
	@Bean(name = "transactionManager")
	public HibernateTransactionManager getTransactionManager(SessionFactory sessionFactory) {
		HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);

		return transactionManager;
	}

	/*@Bean
	  public MappingJackson2HttpMessageConverter messageConverter() {
	    MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
	    converter.setObjectMapper(getObjectMapper());
	    return converter;
	  }
	
	@Bean
	  public ObjectMapper getObjectMapper() {
	    return new ObjectMapper();
	  }*/
}
