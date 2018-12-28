/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "MODULES")
@NamedQuery(name = "getAllModules", query = "SELECT m FROM Modules m")
public class Modules implements Serializable{
    @Id
    private int id;
    
    @Column(nullable = false)
    private String parametrization; //é uma string ou uma classe nova?
    
    private List<Software> extension;

    public Modules() {
    }

    public Modules(int id, String parametrization) {
        this.id = id;
        this.parametrization = parametrization;
        this.extension = new LinkedList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParametrization() {
        return parametrization;
    }

    public void setParametrization(String parametrization) {
        this.parametrization = parametrization;
    }

    public List<Software> getExtension() {
        return extension;
    }

    public void setExtension(Software extension) {
        this.extension.add(extension);
    }

    
    
}
