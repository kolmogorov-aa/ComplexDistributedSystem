package ru.ulmc.school.config

import com.zaxxer.hikari.HikariDataSource
import org.hibernate.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.orm.hibernate5.HibernateTransactionManager
import org.springframework.orm.hibernate5.LocalSessionFactoryBean
import java.util.*
import javax.sql.DataSource


@Configuration
open class JpaConfiguration {
    @Bean
    open fun sessionFactory(dataSource: DataSource): SessionFactory? {
        val factoryBean = LocalSessionFactoryBean()
        factoryBean.setDataSource(dataSource)
        factoryBean.setPackagesToScan("ru.ulmc.school")
        val property = Properties()
        property.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect")
        property.setProperty("hibernate.show_sql", "true")
        property.setProperty("hibernate.hbm2ddl", "validate")
        factoryBean.hibernateProperties = property
        return factoryBean.getObject()
    }

    @Bean
    open fun dataSource(): DataSource {
        val dataSource = HikariDataSource()
        dataSource.driverClassName = "org.h2.Driver"
        dataSource.jdbcUrl = "jdbc:h2:mem:db;DB_CLOSE_DELAY=-1"
        dataSource.username = "sa"
        dataSource.password = "sa"

        return dataSource
    }

    @Bean
    open fun transactionManager(dataSource: DataSource,
                                sessionFactory: SessionFactory): HibernateTransactionManager {
        val tr = HibernateTransactionManager()
        tr.dataSource = dataSource
        tr.sessionFactory = sessionFactory
        return tr
    }


}