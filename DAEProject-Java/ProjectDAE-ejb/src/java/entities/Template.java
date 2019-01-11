/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
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
@DiscriminatorColumn(discriminatorType = DiscriminatorType.INTEGER)
@DiscriminatorValue("Template")
@NamedQuery(name = "getAllTemplates", query = "SELECT t FROM Template as t")
public class Template implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    
    @Column(nullable = false)
    private String description;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String hardware;
    
    @Column(nullable = false)
    private String license;
    
    @OneToMany(cascade = CascadeType.MERGE)
    private LinkedList<Software> software;
    
    @Column(nullable = false)
    private LinkedList<String> supportMaterials;

    public Template(String description, String name, String hardware, String license, 
            LinkedList<Software> software) {
        this.description = description;
        this.name = name;
        this.hardware = hardware;
        this.license = license;
        this.software = software;
        this.supportMaterials = new LinkedList<>();
    }

    public Template() {
        
    }
    
    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<String> getSupportMaterials() {
        return new LinkedList<> (supportMaterials);
    }

    public void setSupportMaterials(String supportMaterials) {
        this.supportMaterials.add(supportMaterials);
    }
    
    public List<Software> getSoftware() {
        return new LinkedList<> (software);
    }

    public void setSoftware(LinkedList<Software> software) {
        this.software = software;
    }
}