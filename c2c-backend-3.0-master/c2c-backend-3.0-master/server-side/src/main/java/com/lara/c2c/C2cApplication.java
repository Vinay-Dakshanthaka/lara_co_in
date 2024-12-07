package com.lara.c2c;

import javax.sql.DataSource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.sql2o.Sql2o;

import com.lara.c2c.model.Learner;
import com.lara.c2c.model.Role;
import com.lara.c2c.model.RoleName;
import com.lara.c2c.repository.LearnerRepository;
import com.lara.c2c.repository.RoleRepository;

@SpringBootApplication
//@PropertySource("file:application.properties")
public class C2cApplication extends SpringBootServletInitializer{
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(C2cApplication.class);
	}
	public static void main(String[] args) {
		SpringApplication.run(C2cApplication.class, args);
	}
	
	@Bean
	Sql2o sql2o(DataSource dataSource) {
		return new Sql2o(dataSource);
	}
	
//	@Bean
//	CommandLineRunner run(LearnerRepository learnerRepo, PasswordEncoder passwordEncoder, RoleRepository roleRepo) {
//		return arg -> {
//			if (roleRepo.findByName(RoleName.ROLE_LEARNER).isEmpty()) roleRepo.save(new Role(RoleName.ROLE_LEARNER));
//			if (roleRepo.findByName(RoleName.ROLE_TPO).isEmpty()) roleRepo.save(new Role(RoleName.ROLE_TPO));
//			if (roleRepo.findByName(RoleName.ROLE_ADMIN).isEmpty()) roleRepo.save(new Role(RoleName.ROLE_ADMIN));
//			if (learnerRepo.findById("1").isEmpty())
//				learnerRepo.save(new Learner("1", "dummy", "dummy@123", passwordEncoder.encode("123456"),2, roleRepo.findByName(RoleName.ROLE_LEARNER).get()));
//			if (learnerRepo.findById("2").isEmpty()) learnerRepo.save(new Learner("2", "dummy1", "tpo@123", passwordEncoder.encode("123456"),2, roleRepo.findByName(RoleName.ROLE_TPO).get()));
//			if (learnerRepo.findById("3").isEmpty()) learnerRepo.save(new Learner("3", "adminn", "admin@123", passwordEncoder.encode("123456"),2, roleRepo.findByName(RoleName.ROLE_ADMIN).get()));
//			
//		};
//		
//	}

}
