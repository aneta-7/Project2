package com.example.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "bouquet.unsold", query = "Select b from Bouquet b where b.sold = false"),
		@NamedQuery(name = "bouquet.color", query = "Select b from Bouquet b where b.color= :color"),
		@NamedQuery(name = "bouquet.all", query = "Select b from Bouquet b")
})

public class Bouquet {

	
	private Long id;
	private String type;
	private String color;
	private Boolean sold = false;
	
	private List<User> users = new ArrayList<User>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Boolean getSold() {
		return sold;
	}
	public void setSold(Boolean sold) {
		this.sold = sold;
	}
	
	public List<User> getUsers() {
		// TODO Auto-generated method stub
		return users;
	}	
}
