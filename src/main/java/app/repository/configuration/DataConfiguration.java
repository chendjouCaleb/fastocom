package app.repository.configuration;

import app.entity.identity.Role;
import app.repository.contract.IRoleRepository;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.NoResultException;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:jpa.properties")
@ComponentScan("app.repository.implementation")
public class DataConfiguration{
    private final Environment env;
    private ApplicationContext applicationContext;

    @Autowired
    public DataConfiguration(ApplicationContext applicationContext, Environment env) {
        this.applicationContext = applicationContext;
        this.env = env;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer(){
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        return new JpaTransactionManager();
    }

    @Bean()
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        LocalContainerEntityManagerFactoryBean factory = new
                LocalContainerEntityManagerFactoryBean();
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setShowSql(Boolean.TRUE);
        factory.setDataSource((DataSource) applicationContext.getBean(env.getProperty("datasource")));

        System.out.println("Datasource: " + factory.getDataSource().getClass());
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("app.entity");
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", "update");
        jpaProperties.put("hibernate.show_sql", false);
        jpaProperties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));

        jpaProperties.put("hibernate.max_fetch_depth", 3);
        jpaProperties.put("hibernate.jdbc.batch_size", 10);
        jpaProperties.put("hibernate.jdbc.fetch_size", 50);
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();

        return factory;
    }



    @Bean
    public HibernateExceptionTranslator hibernateExceptionTranslator()
    {
        return new HibernateExceptionTranslator();
    }

    @Bean("MySQLDataSource")
    public DataSource MySQLDataSource()
    {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/fastocom");
        dataSource.setUsername("root");
        dataSource.setPassword("constitution");
        return dataSource;
    }

    @Bean("H2DataSource")
    DataSource H2DataSource(){
        EmbeddedDatabaseBuilder databaseBuilder = new EmbeddedDatabaseBuilder();
        return databaseBuilder.setType(EmbeddedDatabaseType.H2).build();
    }

    public DataSourceInitializer dataSourceInitializer(DataSource dataSource)
    {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(dataSource);
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript(new ClassPathResource(env.getProperty("init-scripts")));
//        dataSourceInitializer.setDatabasePopulator(databasePopulator);
//        dataSourceInitializer.setEnabled(Boolean.parseBoolean(env.getProperty("init-db",
//                "false")));
        return dataSourceInitializer;
    }
}
