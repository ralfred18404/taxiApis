package com.taxi24.service;

import java.util.Optional;

import com.taxi24.model.Driver;
import com.taxi24.model.Invoice;
import com.taxi24.model.Rider;
import com.taxi24.model.Trip;

public interface ITripService {
	
	Trip createTrip(Rider rider, Driver driver);
	Trip update(Trip trip);
	Optional<Trip>findTripId(int id);
	Invoice saveInvoice(Invoice invoice);
	Optional<Invoice>findInvoiceId(int id);
	Optional<Invoice>findByTrip(int trip);
}
