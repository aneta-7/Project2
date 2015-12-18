package com.example.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({ 
	@NamedQuery(name = "user.all", query = "Select u from User u"),
	@NamedQuery(name = "user.byNick", query = "Select u from User u where u.nick = :nick")
})
public class User {

	private Long id;
	private String name;
	private String nick;
	private List<Bouquet> bouquets = new ArrayList<Bouquet>();

	public User(String name, String nick){
		this.name=name;
		this.nick=nick;
	}
	public User(String name, String nick, List<Bouquet> bouquet_id) {
		this.name=name;
		this.nick=nick;
		this.bouquets=bouquet_id;
	}

	public User(Long id, String name, String nick) {
		this.id=id;
		this.name=name;
		this.nick=nick;
	}
	public User(){
		
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Bouquet> getBouquets() {
		return bouquets;
	}
	
	public void setBouquets(List<Bouquet> bouquets) {
		this.bouquets =  bouquets;
	}
	
}
