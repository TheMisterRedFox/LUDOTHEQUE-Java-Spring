package fr.eni.ludotheque.bo;

import java.util.Date;

public class DetailLocation {

	private Integer noLigne;
	private Date dateRetour;
	private float tarifLocation;
	private Location location;

	public DetailLocation() {
	}

	public DetailLocation(Integer noLigne, Location location, Date dateRetour, float tarifLocation) {
		this.noLigne = noLigne;
		this.location = location;
		this.dateRetour = dateRetour;
		this.tarifLocation = tarifLocation;
	}

	public Integer getNoLigne() {
		return noLigne;
	}

	public void setNoLigne(Integer noLigne) {
		this.noLigne = noLigne;
	}

	public Date getDateRetour() {
		return dateRetour;
	}

	public void setDateRetour(Date dateRetour) {
		this.dateRetour = dateRetour;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public float getTarifLocation() {
		return tarifLocation;
	}

	public void setTarifLocation(float tarifLocation) {
		this.tarifLocation = tarifLocation;
	}
}
