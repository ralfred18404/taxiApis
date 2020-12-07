package com.taxi24.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxi24.model.Driver;
import com.taxi24.model.Rider;
import com.taxi24.model.SuperClass.Status;
import com.taxi24.repository.DriverRepository;
import com.taxi24.repository.RiderRepository;

@Service
public class RiderService implements IRiderService {

	@Autowired
	private RiderRepository riderRepository;

	@Autowired
	private DriverRepository driverRepo;

	@Override
	public Optional<Rider> findByRiderId(int id) {

		return riderRepository.findById(id);
	}

	@Override
	public Rider updateRider(Rider rider) {

		return riderRepository.save(rider);
	}

	@Override
	public List<Rider> findAllRider() {

		return riderRepository.findAll();
	}

	public double CalculationByDistance(Double[] StartP, Double[] EndP) {
		int Radius = 6371;// radius of earth in Km
		double lat1 = StartP[0];
		double lat2 = EndP[0];
		double lon1 = StartP[1];
		double lon2 = EndP[1];
		double dLat = Math.toRadians(lat2 - lat1);
		double dLon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2) * Math.sin(dLon / 2);
		double c = 2 * Math.asin(Math.sqrt(a));

		return Radius * c;
	}

	@Override
	public List<Driver> closestDriver(int riderId, int numberOfClosest) {
		Rider rider = findByRiderId(riderId).get();
		List<Driver> closest = new ArrayList<Driver>();
		Double[] riderltlong = { rider.getLatitude(), rider.getLongitude() };
		List<Driver> driverList = driverRepo.findByStatus(Status.Available);
		double distance = 0.0;
		for (Driver driver : driverList) {
			Double[] driverltlong = {driver.getLatitude(), driver.getLongitude() };
			distance = CalculationByDistance(riderltlong, driverltlong);
			driver.setDistanceBetween(distance);
			closest.add(driver);
		}
		return closest.stream().sorted((x, y) -> x.getDistanceBetween().compareTo(y.getDistanceBetween()))
				.limit(numberOfClosest).collect(Collectors.toList());
	}

}
