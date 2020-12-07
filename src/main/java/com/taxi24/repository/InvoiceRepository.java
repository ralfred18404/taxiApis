package com.taxi24.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.taxi24.model.Invoice;
import com.taxi24.model.Trip;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Integer> {
	Optional<Invoice> findByTrip(Trip trip);
}
