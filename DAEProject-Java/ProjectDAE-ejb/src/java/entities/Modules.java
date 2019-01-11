/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.LinkedList;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "MODULES")
@NamedQuery(name = "getAllModules", query = "SELECT m FROM Modules m")
public class Modules implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String parametrization; //é uma string ou uma classe nova?
    
    @OneToMany(cascade = CascadeType.PERSIST)
    private LinkedList<Software> extension;

    public Modules() {
    }

    public Modules(String parametrization, LinkedList<Software> extension) {
        this.parametrization = parametrization;
        this.extension = extension;
    }

    public LinkedList<Software> getExtension() {
        return extension;
    }

    public void setExtension(LinkedList<Software> extension) {
        this.extension = extension;
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

    
}
