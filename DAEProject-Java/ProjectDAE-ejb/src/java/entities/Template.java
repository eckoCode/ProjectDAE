/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *
 * @author rubenfilipe
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name="Template")
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Template")
@NamedQuery(name = "getAllTemplates", query = "SELECT t FROM Template t")
public class Template implements Serializable{
    @Id
    @Column(nullable = false)
    private int id;
     
    @Column(nullable = false)
    private String description;
    
    private LinkedList<Software> software;


    public Template(int id, String description) {
        this.id = id;
        this.description = description;
        this.software = new LinkedList<>();
    }

    public Template() {
        
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedList<Software> getSoftware() {
        return new LinkedList<>(this.software);
    }

    public void setSoftware(Software software) {
        this.software.add(software);
    }
    
}
