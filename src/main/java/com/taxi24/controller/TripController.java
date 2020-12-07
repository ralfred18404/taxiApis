package com.taxi24.controller;


import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.taxi24.model.Driver;
import com.taxi24.model.Invoice;
import com.taxi24.model.Rider;
import com.taxi24.model.SuperClass.Status;
import com.taxi24.model.Trip;
import com.taxi24.model.Trip.TripStatus;
import com.taxi24.service.IDriverService;
import com.taxi24.service.IRiderService;
import com.taxi24.service.ITripService;

import io.swagger.annotations.ApiOperation;


@RestController
@RequestMapping("/bk/taxi24/api/")
public class TripController {

	@Autowired
	private ITripService tripService;

	@Autowired
	private IDriverService driverService;

	@Autowired
	private IRiderService riderService;

	@ApiOperation("Create a new ‘Trip’ request by assigning a driver to a rider")
	@SuppressWarnings("rawtypes")
	@PostMapping(value = "createNewTrip")
	public ResponseEntity createTrip(@PathParam("riderId") int riderId, @PathParam("driverId") int driverId) {
		Optional<Rider> rider = riderService.findByRiderId(riderId);
		Optional<Driver> driver = driverService.driverById(driverId);
		
		if (rider.isPresent() == true && driver.isPresent() == true) {
			rider.get().setStatus(Status.Requested);
			driver.get().setStatus(Status.Requested);
			riderService.updateRider(rider.get());
			driverService.updateDriver(driver.get());
			tripService.createTrip(rider.get(), driver.get());
			return ResponseEntity.ok().body(new MessageResponse("new Trip is well successfull created"));
		} else {

			return ResponseEntity.badRequest().body(new MessageResponse("please provide rider and driver"));
		}
	}

	@ApiOperation("Starts created Trip")
	@SuppressWarnings("rawtypes")
	@PostMapping(value="startTrip")
	public ResponseEntity startingTrip(@PathParam("tripId") int tripId,
			@PathParam("fromLongitude") Double fromLongitude, @PathParam("fromLatitude") Double fromLatitude, 
			@PathParam("locationName")String locationName) {
		Optional<Trip> trip = tripService.findTripId(tripId);
		
		if (trip.isPresent() == true) {
			trip.get().setStartLocation(fromLatitude + "," + fromLongitude);
			trip.get().setStartLocationName(locationName);
			trip.get().setStartTime(LocalDateTime.now());
			trip.get().setTripStatus(TripStatus.Starting);
			tripService.update(trip.get());
			return ResponseEntity.ok().body(new MessageResponse("new Trip is started"));
		} else {
			return ResponseEntity.badRequest().body(new MessageResponse("Trip id does not exist"));
		}

	}
	
	@ApiOperation("Complete trip")
	@SuppressWarnings("rawtypes")
	@PostMapping(value="completeTrip")
	public ResponseEntity completeTrip(@PathParam("tripId")int tripId, @PathParam("locationName")String locationName,
			@PathParam("latitudeLongitude")String latitudeLongitude) {
		Double longitude =0.0;
		Double latitude =0.0;
		System.out.println("output "+locationName+" "+ latitudeLongitude);
			if(locationName != null || latitudeLongitude != null) {
				Optional<Trip> trip = tripService.findTripId(tripId);
				if (trip.isPresent() == true && !trip.get().getTripStatus().equals(TripStatus.Completed)) {
					Optional<Rider> rider = riderService.findByRiderId(trip.get().getRider().getRiderId());
					Optional<Driver> driver = driverService.driverById(trip.get().getDriver().getDriverId());
					latitude =Double.parseDouble(latitudeLongitude.split(",")[0]);
					longitude = Double.parseDouble(latitudeLongitude.split(",")[1]);
					driver.get().setLatitude(latitude);
					driver.get().setLongitude(longitude);
					driver.get().setStatus(Status.Available);
					driver.get().setLocationName(locationName);
					
					rider.get().setLatitude(latitude);
					rider.get().setLongitude(longitude);
					rider.get().setLocationName(locationName);
					rider.get().setStatus(Status.Available);
					
					riderService.updateRider(rider.get());
					driverService.updateDriver(driver.get());
					trip.get().setTripStatus(TripStatus.Completed);
					trip.get().setEndLocationName(locationName);
					trip.get().setEndLocation(latitudeLongitude);
					trip.get().setEndTime(LocalDateTime.now());
					trip.get().setUrl("http://localhost:8080/bk/taxi24/api/invoice/"+trip.get().getTripId());
					tripService.update(trip.get());
					
					Double distance =CalculationByDistance(trip.get().getStartLocation(), latitudeLongitude);
					
					Invoice invoice = new Invoice();
					invoice.setCost(distance*1000);
					invoice.setKilometer(distance);
					invoice.setTrip(trip.get());
					
					tripService.saveInvoice(invoice);
					return ResponseEntity.ok().body(new MessageResponse("trip is completed Url for print invoice: "+trip.get().getUrl()));
				}else {
					return ResponseEntity.badRequest().body(new MessageResponse("please check if trip id is exist and it is not completed"));
				}
			}else {
				return ResponseEntity.badRequest().body(new MessageResponse("please provide longitude, latitude and location name"));
			}
			
		
	}
	
	@ApiOperation("print invoice after trip is completed")
	@RequestMapping(value = "invoice/{id}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> invoice(@PathVariable("id")int id) {
		
		try {
			
		
       Optional<Invoice>invoice = tripService.findByTrip(id);
      
        ByteArrayInputStream bis = InvoiceReport.printInvoice(invoice.get());
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=invoice.pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}
	
	public static double CalculationByDistance(String StartP, String EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 =Double.parseDouble(StartP.split(",")[0]);
        double lat2 = Double.parseDouble(EndP.split(",")[0]);
        double lon1 = Double .parseDouble(StartP.split(",")[1]);
        double lon2 = Double.parseDouble(EndP.split(",")[1]);
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
       
        return Radius * c;
    }
	

}
