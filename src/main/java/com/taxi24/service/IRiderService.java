package com.taxi24.service;

import java.util.List;
import java.util.Optional;

import com.taxi24.model.Driver;
import com.taxi24.model.Rider;

public interface IRiderService {

	Optional<Rider>findByRiderId(int id);
	Rider updateRider(Rider rider);
	List<Rider>findAllRider();
	List<Driver>closestDriver(int riderId, int numberOfClosest);
	
}
