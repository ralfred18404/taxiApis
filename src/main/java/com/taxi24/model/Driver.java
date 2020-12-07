package com.taxi24.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
public class Driver extends SuperClass {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="DRIVER_ID")
	private int driverId;
	
	@Transient
	private Double distanceBetween;

	public int getDriverId() {
		return driverId;
	}

	public void setDriverId(int driverId) {
		this.driverId = driverId;
	}

	public Double getDistanceBetween() {
		return distanceBetween;
	}

	public void setDistanceBetween(Double distanceBetween) {
		this.distanceBetween = distanceBetween;
	}
	
	
}
