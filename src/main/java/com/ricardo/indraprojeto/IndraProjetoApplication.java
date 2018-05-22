package com.ricardo.indraprojeto;

import javax.persistence.EntityManagerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;

@SpringBootApplication
public class IndraProjetoApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndraProjetoApplication.class, args);
	}
    // inserir na classe de inicialização do spring
	// método de configuracao para banco de dados externo
	// Olhar application.properties
	

	@Bean
	public HibernateJpaSessionFactoryBean sessionFactory(EntityManagerFactory emf) {
	     HibernateJpaSessionFactoryBean factory = new HibernateJpaSessionFactoryBean();
	     factory.setEntityManagerFactory(emf);
	     return factory;
	} 
}
