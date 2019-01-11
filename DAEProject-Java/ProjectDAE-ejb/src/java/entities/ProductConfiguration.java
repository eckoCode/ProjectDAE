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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 *
 * @author rubenfilipe
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("ProductConfiguration")
@Table(name = "ProductConfiguration")
@NamedQueries({
    @NamedQuery(name = "getAllproductConfigurations", query = "SELECT a FROM ProductConfiguration a"),
    @NamedQuery(name="getProductsByUsername", query = "Select a From ProductConfiguration a Where a.client.username = :username")
})
public class ProductConfiguration implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CLIENT_USERNAME", nullable = false)
    private Client client;
    
    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "CONTRACT_ID",nullable = false)
    private Contract contract;
    
    @Column(nullable = false)
    private String hardwareRequired; //Na vista por uma dropdown para selecionar tipo cloud ou servidor próprio...
    
    @Column(nullable = false)
    private StateOfSoftware stateOfSoftware;
    
    @OneToMany(cascade = CascadeType.ALL)
    private LinkedList<Modules> modules;
    
    @Column(nullable = false)
    private String license;
    
    @Column(nullable = false)
    private String name; 
    
    @Column(nullable = false)
    private String description;
    
    //TODO: Merge em vez de persist
    @OneToMany(cascade = CascadeType.ALL)
    private LinkedList<Software> softwares;
    
    @Column(nullable = false)
    private LinkedList<String> supportMatterials;
    
    @OneToMany(mappedBy = "prodConfig", cascade = CascadeType.PERSIST)
    private LinkedList<Qa> qaList;
     
    public ProductConfiguration() {
    }

    public ProductConfiguration(Client client,String name, String description, String hardwareRequired, 
            StateOfSoftware stateOfSoftware, String license, Contract contract, LinkedList<Modules> modules, LinkedList<Software> softwares,
            LinkedList<Qa> qaList){
        this.client = client;
        this.contract = contract;
        this.description = description;
        this.name = name;
        this.hardwareRequired = hardwareRequired;
        this.stateOfSoftware = stateOfSoftware;
        this.modules = modules;
        this.license = license;
        this.supportMatterials =  new LinkedList<>();
        this.softwares = softwares;
        this.qaList = new LinkedList<>();
    }

    public LinkedList<Qa> getQa() {
        return qaList;
    }

    public void setQa(Qa qa) {
        this.qaList.add(qa);
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Contract getContract() {
        return contract;
    }

    public void setContract(Contract contract) {
        this.contract = contract;
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

    public LinkedList<Modules> getModules() {
        return modules;
    }

    public void setModules(LinkedList<Modules> modules) {
        this.modules = modules;
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

    public void setSupportMatterials(String supportMatterials) {
        this.supportMatterials.add(supportMatterials);
    }
    
    
   

   
}
