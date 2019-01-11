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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 *
 * @author rubenfilipe
 */
@Entity
@Table(name="Software")
@NamedQuery(name = "getAllSoftware", query = "SELECT s FROM Software s")
public class Software implements Serializable{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String name;
     
    @Column(nullable = false)
    private Double version;

    @OneToMany(cascade = CascadeType.PERSIST)
    private  LinkedList<Artifact> artifacts;
    
    
    public Software(String name, Double version, LinkedList<Artifact> artifacts) {
        this.name = name;
        this.version = version;
        this.artifacts = artifacts;
    }

    public Software() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public LinkedList<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(LinkedList<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
    
}
