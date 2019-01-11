/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Contract;
import entities.Modules;
import entities.Qa;
import entities.Software;
import entities.StateOfSoftware;
import java.util.LinkedList;

/**
 *
 * @author rubenfilipe
 */
public class ProductConfigurationDTO{
    private int id;
    
    private String username;
    
    private String hardwareRequired;
    
    private StateOfSoftware stateOfSoftware;
    
    private String license;
   
    private String name; 
   
    private String description;
    
    private LinkedList<Software> softwares;
    
    private LinkedList<String> supportMatterials;
    
     private LinkedList<Modules> modules;
    
    private Contract contract;
    
    private LinkedList<Qa> qa;

    public ProductConfigurationDTO(
            String username,
            String hardwareRequired,
            StateOfSoftware stateOfSoftware,
            String license,
            int id,
            Contract contract,
            String name,
            String description,
            LinkedList<Modules> modules,
            LinkedList<Software> softwares,
            LinkedList<String> supportMatterials,
            LinkedList<Qa> qa) {
        
        this.id = id;
        this.username  = username;
        this.name = name;
        this.description = description;
        this.hardwareRequired = hardwareRequired;
        this.stateOfSoftware = stateOfSoftware;
        this.license = license;
        this.supportMatterials = supportMatterials;
        this.softwares = softwares;
        this.modules = modules;
        this.contract = contract;
        this.qa = qa;
    }

   
    public ProductConfigurationDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHardwareRequired() {
        return hardwareRequired;
    }

    public void setHardwareRequired(String hardwareRequired) {
        this.hardwareRequired = hardwareRequired;
    }

    public StateOfSoftware getStateOfSoftware() {
        return stateOfSoftware;
    }

    public void setStateOfSoftware(StateOfSoftware stateOfSoftware) {
        this.stateOfSoftware = stateOfSoftware;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LinkedList<Software> getSoftwares() {
        return softwares;
    }

    public void setSoftwares(LinkedList<Software> softwares) {
        this.softwares = softwares;
    }

    public LinkedList<String> getSupportMatterials() {
        return supportMatterials;
    }

    public void setSupportMatterials(LinkedList<String> supportMatterials) {
        this.supportMatterials = supportMatterials;
    }

    public LinkedList<Modules> getModules() {
        return modules;
    }

    public void setModules(LinkedList<Modules> modules) {
        this.modules = modules;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }

    public LinkedList<Qa> getQa() {
        return qa;
    }

    public void setQa(LinkedList<Qa> qa) {
        this.qa = qa;
    }

   
    
    
    
}
