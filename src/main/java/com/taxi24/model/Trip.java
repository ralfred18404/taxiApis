package com.taxi24.model;


import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

@Entity
public class Trip {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TRIP_ID")
	private int tripId;
	
	@Column(name="START_TIME")
	private LocalDateTime startTime;
	
	@Column(name="END_TIME")
	private LocalDateTime endTime;
	
	@Column(name="START_Location")
	private String startLocation;
	
	@Column(name="END_LOCATION")
	private String endLocation;
	
	@Column(name="START_LOCATION_NAME")
	private String startLocationName;
	
	@Column(name="END_LOCATION_NAME")
	private String endLocationName;
	
	@Transient
	private String url;
	
	
	@Column(name="TRIP_STATUS")
	@Enumerated(EnumType.STRING)
	private TripStatus tripStatus;
	
	@OneToOne
	@JoinColumn(name="RIDER")
	private Rider rider;
	
	@OneToOne
	@JoinColumn(name="DRIVER")
	private Driver driver;
	
	public enum TripStatus{
		Requested,Completed,Starting
	}

	public int getTripId() {
		return tripId;
	}

	public void setTripId(int tripId) {
		this.tripId = tripId;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	

	public String getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(String startLocation) {
		this.startLocation = startLocation;
	}

	public String getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(String endLocation) {
		this.endLocation = endLocation;
	}

	public String getEndLocationName() {
		return endLocationName;
	}

	public void setEndLocationName(String endLocationName) {
		this.endLocationName = endLocationName;
	}

	public TripStatus getTripStatus() {
		return tripStatus;
	}

	public void setTripStatus(TripStatus tripStatus) {
		this.tripStatus = tripStatus;
	}

	public Rider getRider() {
		return rider;
	}

	public void setRider(Rider rider) {
		this.rider = rider;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public String getStartLocationName() {
		return startLocationName;
	}

	public void setStartLocationName(String startLocationName) {
		this.startLocationName = startLocationName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	
	
	
}
