package fr.eni.ludotheque.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jakarta.validation.constraints.NotNull;

public class Location {

	private Integer noLocation;
	
	@NotNull
	private Date dateLocation;
	
	@NotNull
	private Boolean paye;
	
	private float prixTotal;
	
	private List<DetailLocation> lignesLocation;

	public Location() {
		super();
		this.lignesLocation = new ArrayList<DetailLocation>();
	}

	public Location(Integer noLocation, @NotNull Date dateLocation, @NotNull Boolean paye, float prixTotal) {
		this();
		
		this.noLocation = noLocation;
		this.dateLocation = dateLocation;
		this.paye = paye;
		this.prixTotal = prixTotal;
	}

	public Integer getNoLocation() {
		return noLocation;
	}

	public void setNoLocation(Integer noLocation) {
		this.noLocation = noLocation;
	}

	public Date getDateLocation() {
		return dateLocation;
	}

	public void setDateLocation(Date dateLocation) {
		this.dateLocation = dateLocation;
	}

	public Boolean getPaye() {
		return paye;
	}

	public void setPaye(Boolean paye) {
		this.paye = paye;
	}

	public float getPrixTotal() {
		return prixTotal;
	}

	public void setPrixTotal(float prixTotal) {
		this.prixTotal = prixTotal;
	}

	public List<DetailLocation> getLignesLocation() {
		return lignesLocation;
	}

	public void setLignesLocation(List<DetailLocation> lignesLocation) {
		this.lignesLocation = lignesLocation;
	}
	
	public void addLigneLocation(DetailLocation ligneLocation) {
		this.lignesLocation.add(ligneLocation);
	}
	
}
