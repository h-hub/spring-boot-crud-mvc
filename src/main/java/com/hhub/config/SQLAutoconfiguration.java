package com.hhub.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.google.common.base.Preconditions;

@Configuration
@ConditionalOnClass(DataSource.class)
public class SQLAutoconfiguration {

	@Autowired
	private Environment env;

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { "com.hhub.models" });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(hibernateProperties());

		return em;
	}

	@Bean
	public DataSource dataSource() {

		final DriverManagerDataSource dataSource = new DriverManagerDataSource();

		dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("db.driver")));
		dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("db.url")));
		dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("db.username")));
		dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("db.password")));

		return dataSource;
	}

	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}

	private final Properties hibernateProperties() {
		final Properties hibernateProperties = new Properties();
		hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));

		return hibernateProperties;
	}

}
