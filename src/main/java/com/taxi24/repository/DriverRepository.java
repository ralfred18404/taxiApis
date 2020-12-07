package com.taxi24.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxi24.model.Driver;
import com.taxi24.model.SuperClass.Status;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Integer> {

	List<Driver>findByStatus(Status status);
	
}
