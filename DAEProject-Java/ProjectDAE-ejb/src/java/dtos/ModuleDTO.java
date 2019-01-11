/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Software;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

/**
 *
 * @author rubenfilipe
 */
public class ModuleDTO {
    
     private int id;
    
    private String parametrization; 
    
    private LinkedList<Software> extension;

    public ModuleDTO() {
    }

    public ModuleDTO(int id, String parametrization, LinkedList<Software> extension) {
        this.id = id;
        this.parametrization = parametrization;
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

    public LinkedList<Software> getExtension() {
        return extension;
    }

    public void setExtension(LinkedList<Software> extension) {
        this.extension = extension;
    }
}
