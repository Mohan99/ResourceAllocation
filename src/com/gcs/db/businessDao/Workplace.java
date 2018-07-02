package com.gcs.db.businessDao;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@SuppressWarnings("serial")
public class Workplace implements java.io.Serializable {

	@Id
	@GeneratedValue
	private int id;
	private String name;

	@SuppressWarnings("rawtypes")
	private Set employees = new HashSet(0);

	public Workplace() {
	}

	public Workplace(String name) {

		this.name = name;

	}

	@SuppressWarnings("rawtypes")
	public Workplace(String name, Set employees) {

		this.name = name;

		this.employees = employees;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@SuppressWarnings("rawtypes")
	public Set getEmployees() {
		return this.employees;
	}

	@SuppressWarnings("rawtypes")
	public void setEmployees(Set employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Countries [id=" + id + ", name=" + name + ", employees=" + employees + "]";
	}

}
