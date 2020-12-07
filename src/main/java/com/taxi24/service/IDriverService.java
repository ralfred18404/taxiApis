package com.taxi24.service;

import java.util.List;
import java.util.Optional;

import com.taxi24.model.Driver;

public interface IDriverService {

	List<Driver>findAllAvailableDriver();
	List<Driver>findAllDrivers();
	Optional<Driver>driverById(int id);
	List<Driver>driverWithinKm(Double longitude, Double latitude, Double kilometer);
	Driver updateDriver(Driver driver);
}
