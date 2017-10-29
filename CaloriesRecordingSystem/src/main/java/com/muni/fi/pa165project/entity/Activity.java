package com.muni.fi.pa165project.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import com.muni.fi.pa165project.enums.Category;

/**
 *
 * @author Lukáš Císar
 */
@Entity
public class Activity implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, unique=true)
	private String name;
		
	private String description;
	
	@Enumerated(EnumType.STRING)
	private Category category;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "activity", orphanRemoval = true)
	private Set<BurnedCalories> burnedCalories = new HashSet<BurnedCalories>();
		
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public Category getCategory() {
      return category;
    }
    
    public void setCategory(Category category) {
      this.category = category;
    }
    
    public Set<BurnedCalories> getBurnedCalories() {
      return burnedCalories;
    }
    
    public void setBurnedCalories(Set<BurnedCalories> burnedCalories) {
      this.burnedCalories = burnedCalories;
    }
    
	public void addBurnedCaloriesItem(BurnedCalories burnedCaloriesItem){
		this.burnedCalories.add(burnedCaloriesItem);
		burnedCaloriesItem.setActivity(this);
	}
	
	@Override
	public int hashCode() {

		int result = 17;
        result = 31 * result + name.hashCode();
        return result;
	}

    @Override
    public boolean equals(Object obj) {
  
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof Activity))
            return false;
    
        final Activity other = (Activity) obj;
    
        if (name == null) {
            return false;
        } else if (!name.equals(other.name))
            return false;

        return true;
    }
}
