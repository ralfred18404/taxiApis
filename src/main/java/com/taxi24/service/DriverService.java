package com.taxi24.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxi24.model.Driver;
import com.taxi24.model.SuperClass.Status;
import com.taxi24.repository.DriverRepository;

@Service
public class DriverService implements IDriverService {
	
	@Autowired
	private DriverRepository driverRepo;
	

	
	
	@Override
	public List<Driver> findAllAvailableDriver() {
		
		return driverRepo.findByStatus(Status.Available);
	}

	@Override
	public List<Driver> findAllDrivers() {
		
		return driverRepo.findAll();
	}

	@Override
	public Optional<Driver> driverById(int id) {
		
		return driverRepo.findById(id);
	}

	@Override
	public List<Driver> driverWithinKm(Double longitude, Double latitude, Double kilometer) {
		List<Driver>nearestDriver = new ArrayList<Driver>();
		Double [] customerLocaton = {latitude, longitude};
		Double distanceBetween = 0.0;
		for(Driver driver: findAllAvailableDriver()) {
			Double[]driverLocation = {driver.getLatitude(),driver.getLongitude()};
			distanceBetween = CalculationByDistance(customerLocaton, driverLocation);
			if(distanceBetween <= kilometer) {
				nearestDriver.add(driver);
			}
		}
		
		return nearestDriver;
	}
	
	public double CalculationByDistance(Double[] StartP, Double[] EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP[0];
        double lat2 = EndP[0];
        double lon1 = StartP[1];
        double lon2 = EndP[1];
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
       
        return Radius * c;
    }

	@Override
	public Driver updateDriver(Driver driver) {
		
		return driverRepo.save(driver);
	}
	

}
