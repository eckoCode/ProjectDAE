/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Software;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Template")
@XmlAccessorType(XmlAccessType.FIELD)
public class TemplateDTO implements Serializable {

    private int id;
    private String description;
    private String name;
    private String hardware;
    private String license;
    private List<Software> software;
    private List<String> supportMaterials;
        

    public TemplateDTO(int id,String description,String name,String hardware,String license,List<Software> software,List<String> supportMaterials) {
        this.id = id;
        this.description = description;
        this.name = name;
        this.hardware = hardware;
        this.license = license;
        this.software = software;
        this.supportMaterials = supportMaterials;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public LinkedList<Software> getSoftware() {
        return new LinkedList<>(software);
    }

    public void setSoftware(List<Software> software) {
        this.software = software;
    }

    public LinkedList<String> getSupportMaterials() {
        return new LinkedList<>(supportMaterials);
    }

    public void setSupportMaterials(List<String> supportMaterials) {
        this.supportMaterials = supportMaterials;
    }
}
