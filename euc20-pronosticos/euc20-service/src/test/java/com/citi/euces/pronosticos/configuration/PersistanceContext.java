/**
 * 
 */
package com.citi.euces.pronosticos.configuration;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import oracle.jdbc.pool.OracleDataSource;

/**
 * Componente de configuracion para las pruebas unitarias.
 * 
 * @author luis
 */
@Configuration
@EnableJpaRepositories({ "com.citi.euces.pronosticos.repositories" })
public class PersistanceContext {

	@Bean("dataSource")
	public DataSource dataSource() {

		OracleDataSource oracleDS = null;

		try {

			oracleDS = new OracleDataSource();
			oracleDS.setURL("jdbc:oracle:thin:@//34.121.165.20:1521/xe");
			oracleDS.setUser("TSC_EUCS_OWN");
			oracleDS.setPassword("CitiBanamex1");

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return oracleDS;
	}

	@Bean("jpaVendorAdapter")
	public HibernateJpaVendorAdapter jpaVendorAdapter() {

		final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

		vendorAdapter.setGenerateDdl(false);
		vendorAdapter.setShowSql(true);
		vendorAdapter.setDatabasePlatform("org.hibernate.dialect.Oracle12cDialect");

		return vendorAdapter;
	}

	@Bean("entityManagerFactory")
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();

		Properties jpaProperties = new Properties();

		jpaProperties.put("hibernate.format_sql", "true");

		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter());
		entityManagerFactoryBean.setPackagesToScan("com.citi.euces.pronosticos");
		entityManagerFactoryBean.setJpaProperties(jpaProperties);

		return entityManagerFactoryBean;
	}

	@Bean("transactionManager")
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();

		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

		return jpaTransactionManager;
	}

}
