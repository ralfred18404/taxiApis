package com.taxi24.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Rider extends SuperClass {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="Rider_ID")
	private int riderId;

	public int getRiderId() {
		return riderId;
	}

	public void setRiderId(int riderId) {
		this.riderId = riderId;
	}
	
	
	
}
