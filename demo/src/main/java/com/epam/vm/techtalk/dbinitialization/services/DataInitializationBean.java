package com.epam.vm.techtalk.dbinitialization.services;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epam.vm.techtalk.dbinitialization.domain.InitializationStrategy;
import com.epam.vm.techtalk.dbinitialization.repositories.InitializationStrategyRepository;

@Component
public class DataInitializationBean {
	
	@Autowired
	InitializationStrategyRepository repository;
	
	@PostConstruct
	public void initialize() {
		createStrategy("Vasya Strategy", 44);
	}
	
	private InitializationStrategy createStrategy(String name, Integer rate) {
		InitializationStrategy initializationStrategy = new InitializationStrategy();
		initializationStrategy.setName(name);
		initializationStrategy.setRate(rate);
		return repository.save(initializationStrategy);
	}

}
