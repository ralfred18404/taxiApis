package com.taxi24.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taxi24.model.Driver;
import com.taxi24.model.Invoice;
import com.taxi24.model.Rider;
import com.taxi24.model.Trip;
import com.taxi24.model.Trip.TripStatus;
import com.taxi24.repository.InvoiceRepository;
import com.taxi24.repository.TripRepository;

@Service
public class TripService implements ITripService {
	
	@Autowired
	private TripRepository tripRepo;
	
	@Autowired
	private InvoiceRepository invoiceRepo;

	@Override
	public Trip createTrip(Rider rider, Driver driver) {
		Trip trip = new Trip();
		trip.setDriver(driver);
		trip.setRider(rider);
		trip.setTripStatus(TripStatus.Requested);
		return tripRepo.save(trip);
	}

	@Override
	public Trip update(Trip trip) {
		
		return tripRepo.save(trip);
	}

	@Override
	public Optional<Trip> findTripId(int id) {
		return tripRepo.findById(id);
	}

	@Override
	public Invoice saveInvoice(Invoice invoice) {
		
		return invoiceRepo.save(invoice);
	}

	@Override
	public Optional<Invoice> findInvoiceId(int id) {
		
		return invoiceRepo.findById(id);
	}

	@Override
	public Optional<Invoice> findByTrip(int trip) {
		Trip t =tripRepo.findById(trip).get();
		return invoiceRepo.findByTrip(t);
	}

}
