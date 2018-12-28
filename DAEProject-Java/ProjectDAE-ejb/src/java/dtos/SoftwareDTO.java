/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author rubenfilipe
 */
public class SoftwareDTO implements Serializable{
    private String name;
    private Double version;
    private int id;

    public SoftwareDTO() {
    }

    public SoftwareDTO(String name, Double version) {
        this.name = name;
        this.version = version;
        this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
}
