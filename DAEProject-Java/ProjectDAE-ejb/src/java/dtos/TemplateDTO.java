/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Software;
import java.io.Serializable;
import java.util.LinkedList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Template")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateDTO implements Serializable {

    private int id;
    private String description;
    private LinkedList<Software> software;

    public TemplateDTO(int id,String description) {
        this.id = id;
        this.description = description;
        this.software = new LinkedList<>();
    }
    
    public TemplateDTO() {

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
        return software;
    }

    public void setSoftware(Software software) {
        this.software.add(software);
             
    }

    

}
