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
 
    private List<Modules> modules;
    
    @Column(nullable = false)
    private String license;
    
    @OneToMany
    @JoinColumn(name = "filename",nullable = false)
    private List<File> supportMatterials;
     
    public ProductConfiguration() {
    }

    public ProductConfiguration(Client client, String hardwareRequired, StateOfSoftware stateOfSoftware, String license, Contract contract) {
        this.client = client;
        this.contract = contract;
        this.hardwareRequired = hardwareRequired;
        this.stateOfSoftware = stateOfSoftware;
        this.modules = new LinkedList<>();
        this.license = license;
        this.supportMatterials = new LinkedList<>();
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getHardwareRequired() {
        return hardwareRequired;
    }
    
     public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Modules> getModules() {
        return modules;
    }

    public void setModules(Modules modules) {
        this.modules.add(modules);
    }
    
    
    
  
    
}
