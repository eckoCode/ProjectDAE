/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Client;
import entities.Contract;
import entities.File;
import entities.StateOfSoftware;
import java.util.LinkedList;
import java.util.List;

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
    
    private List<File> supportMatterials;
    
    private Contract contract;

    public ProductConfigurationDTO(String username, String hardwareRequired, StateOfSoftware stateOfSoftware, String license, int id, Contract contract) {
       this.id = id;
        this.username  = username;
        this.hardwareRequired = hardwareRequired;
        this.stateOfSoftware = stateOfSoftware;
        this.license = license;
        this.supportMatterials = new LinkedList<>();
        this.contract = contract;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
   

    public ProductConfigurationDTO() {
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

    public StateOfSoftware getstateOfSoftware() {
        return stateOfSoftware;
    }

    public void setstateOfSoftware(StateOfSoftware stateOfSoftware) {
        this.stateOfSoftware = stateOfSoftware;
    }

    public String getLicense() {
        return license;
    }

    public void setLicense(String license) {
        this.license = license;
    }

    public List<File> getSupportMatterials() {
        return supportMatterials;
    }

    public void setSupportMatterials(List<File> supportMatterials) {
        this.supportMatterials = supportMatterials;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
    }
    
    
    
}
