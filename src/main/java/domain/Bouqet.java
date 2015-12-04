package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "bouqet.unsold", query = "Select b from Bouqet b where b.sold = false")
})

public class Bouqet {

	
	private Long id;
	private String name;
	private String type;
	private Boolean sold = false;
	
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Boolean getSold() {
		return sold;
	}
	public void setSold(Boolean sold) {
		this.sold = sold;
	}
	
	
}
