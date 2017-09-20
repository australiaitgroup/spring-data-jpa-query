package com.jiangren.springdatajpa.query.springdatajpaquery;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SpringBootApplication
public class SpringDataJpaQueryApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringDataJpaQueryApplication.class, args);
	}

    @Bean
    CommandLineRunner initData(ProductRepository productRepository, CompanyRepository companyRepository) {

	    return args -> {

            Company apple = companyRepository.save(new Company("Apple", Country.US));
            Company samsung = companyRepository.save(new Company("Samsung", Country.KOREA));

            productRepository.save(new Product("iPhone8", new Date(), apple));
            productRepository.save(new Product("iPhone8Plus", new Date(), apple));
            productRepository.save(new Product("iPhoneX", new Date(), apple));
            productRepository.save(new Product("GS7", new Date(), samsung));
            productRepository.save(new Product("GS Note", new Date(), samsung));
        };


    }

}

@Data
@Entity
class Product {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Date publishedDate;

	@ManyToOne
    @JoinColumn(name = "company_id")
	private Company company;

	private Product() {};

	public Product(String name, Date publishedDate, Company company) {
	    this.name = name;
	    this.publishedDate = publishedDate;
	    this.company = company;
    }
}

@Data
@Entity
class Company {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Country country;

    @OneToMany(mappedBy = "company")
    private List<Product> products;

    private Company(){}

    public Company(String name, Country country) {
        this.name = name;
        this.country = country;
    }

}

@RepositoryRestResource
interface ProductRepository extends CrudRepository<Product, Long> {
    List<Product> findByNameIgnoreCase(@Param("q") String name);

    List<Product> findProductsByCompanyNameIgnoreCase(@Param("q") String name);

}

@RepositoryRestResource
interface CompanyRepository extends  CrudRepository<Company, Long> {
    List<Company> findByNameIgnoreCase(@Param("q") String name);

    List<Company> findByCountry(@Param("q") Country country);

}