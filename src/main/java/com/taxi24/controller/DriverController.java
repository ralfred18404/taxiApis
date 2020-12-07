package com.taxi24.controller;

import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.taxi24.model.Driver;
import com.taxi24.service.IDriverService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/bk/taxi24/api/")
public class DriverController {

	@Autowired
	private IDriverService driverService;

	@ApiOperation("Get a list of all drivers")
	@GetMapping(value = "findAllDrivers")
	public List<Driver> findAllDrivers() {
		return driverService.findAllDrivers();
	}

	@ApiOperation("Get a list of all available drivers")
	@GetMapping(value = "findAvailableDrivers")
	public List<Driver> findAvailableDrivers() {
		return driverService.findAllAvailableDriver();
	}
	
	@ApiOperation("Get a specific driver by ID")
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "findDriverById/{id}")
	public ResponseEntity findDriverById(@PathVariable("id") int id) throws Exception {
		Optional<Driver> d = driverService.driverById(id);
		if (d.isPresent() == true) {
			System.out.println("out put" + d);
			return ResponseEntity.ok().body(d);

		} else {

			return ResponseEntity.badRequest().body("driver id not found");
		}

	}

	@ApiOperation("Get a list of all available drivers within kilometers for a specific location")
	@SuppressWarnings("rawtypes")
	@GetMapping(value = "findNearestDriver")
	public ResponseEntity findDriverNearest(@PathParam("longitude") Double longitude,
			@PathParam("latitude") Double latitude, @PathParam("kilometer") Double kilometer) {
		try {

			List<Driver> list = driverService.driverWithinKm(longitude, latitude, kilometer);
			if (list.isEmpty() == true) {
				System.out.println("list empty");
				return ResponseEntity.ok().body(new MessageResponse("no nearest driver within " + kilometer + " KM"));
			} else {
				System.out.println("list done");
				return ResponseEntity.ok().body(list);
			}
		} catch (Exception e) {
			System.out.println("output exception");
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
		}

	}

}
