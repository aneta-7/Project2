package domain;

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

	private String firstName = "unknown";
	private String nick = "";
	private Date registrationDate = new Date();

	private List<Bouqet> bouqets = new ArrayList<Bouqet>();

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Column(unique = true, nullable = false)
	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	@Temporal(TemporalType.DATE)
	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	// Be careful here, both with lazy and eager fetch type
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Bouqet> getBouqets() {
		return bouqets;
	}

	public void setBouqets(List<Bouqet> bouqets) {
		this.bouqets = bouqets;
	}
	
}
