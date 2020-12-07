package com.taxi24.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Invoice {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="INVOICE_ID")
	private int invoiceId;
	
	@Column(name="COST")
	private Double cost;
	
	@Column(name="KILOMETER")
	private Double kilometer;
	
	@OneToOne
	@JoinColumn(name="TRIP")
	private Trip trip;

	public int getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(int invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Double getCost() {
		return cost;
	}

	public void setCost(Double cost) {
		this.cost = cost;
	}

	public Double getKilometer() {
		return kilometer;
	}

	public void setKilometer(Double kilometer) {
		this.kilometer = kilometer;
	}

	public Trip getTrip() {
		return trip;
	}

	public void setTrip(Trip trip) {
		this.trip = trip;
	}
	
	
}
