package com.epam.vm.techtalk.dbinitialization.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.epam.vm.techtalk.dbinitialization.domain.InitializationStrategy;

public interface InitializationStrategyRepository extends CrudRepository<InitializationStrategy, Long> {

	List<InitializationStrategy> findByName(String name);
}
