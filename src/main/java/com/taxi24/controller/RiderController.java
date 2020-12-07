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
import com.taxi24.model.Rider;
import com.taxi24.service.RiderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/bk/taxi24/api/")
public class RiderController {
	
	@Autowired
	private RiderService riderService;

	@ApiOperation("Get a list of all riders")
	@GetMapping(value="findAllRider")
	public List<Rider> findAllRider() {
		List<Rider>list = riderService.findAllRider();
		return list;
	}
	
	@ApiOperation("Get a specific rider by ID")
	@SuppressWarnings("rawtypes")
	@GetMapping(value="findRiderById/{id}")
	public ResponseEntity findRiderById(@PathVariable("id")int id) {
		try {
			Optional<Rider>rider = riderService.findByRiderId(id);
			if(rider.isPresent() == true) {
				return ResponseEntity.ok().body(rider.get());
			}else {
				return ResponseEntity.badRequest().body(new MessageResponse("rider Id not found"));
			}
			
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
		}
	}
	
	@ApiOperation("For a specific driver, get a list of the closest drivers")
	@SuppressWarnings("rawtypes")
	@GetMapping(value="closestDriver")
	public ResponseEntity closestDriverToRider(@PathParam("riderId")int riderId, @PathParam("count")int count){
		Optional<Rider>rider = riderService.findByRiderId(riderId);
		if(rider.isPresent()) {
			List<Driver>driverClosest = riderService.closestDriver(riderId, count);
			if(driverClosest.isEmpty() == false) {
			return ResponseEntity.ok().body(driverClosest);
			}else {
				return ResponseEntity.badRequest().body(new MessageResponse("there is no closest driver"));
			}
		}else {
			return ResponseEntity.badRequest().body(new MessageResponse("rider id is not found"));
		}
	}
	
	
	
}
