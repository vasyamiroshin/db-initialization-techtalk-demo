package com.epam.vm.techtalk.dbinitialization.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "initialization_strategy")
public class InitializationStrategy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column
	private String name;
	
	@Column
	private Integer rate;
	
	public InitializationStrategy() {}
	
	public InitializationStrategy(String name, Integer rate) {
		this.name = name;
		this.rate = rate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRate() {
		return rate;
	}

	public void setRate(Integer rate) {
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "InitializationStrategy [id=" + id + ", name=" + name + ", rate=" + rate + "]";
	}
	
	

}
