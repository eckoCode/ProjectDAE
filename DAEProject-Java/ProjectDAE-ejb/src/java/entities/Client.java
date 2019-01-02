/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import entities.UserGroup.GROUP;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Client")
@NamedQueries({
    @NamedQuery(name = "getAllClients",
            query = "SELECT c from Client c ORDER BY c.companyName") 
})
public class Client extends User implements Serializable {

    private String address;
    
    private String companyName;
    
    private String contactPerson;

    @OneToMany(mappedBy = "client", cascade = CascadeType.PERSIST)
    private List<ProductConfiguration> productConfigurations;

    public Client() {
        this.productConfigurations = new LinkedList<>();
    }

    public Client(String address, String companyName, String contactPerson, String username, String password) {
        super(GROUP.Client,username, password);
        this.address = address;
        this.companyName = companyName;
        this.contactPerson = contactPerson;
        this.productConfigurations = new LinkedList<>();
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactPerson() {
        return contactPerson;
    }

    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }

    public List<ProductConfiguration> getConfigurations() {
        return productConfigurations;
    }

    public void setConfigurations(List<ProductConfiguration> configurations) {
        this.productConfigurations = configurations;
    }
    

    public void addConfiguration(ProductConfiguration configuration) {
        if (configuration != null && !productConfigurations.contains(configuration)) {
            productConfigurations.add(configuration);
        }
    }

    public void removeConfiguration(ProductConfiguration configuration) {
        if (configuration != null && productConfigurations.contains(configuration)) {
            productConfigurations.remove(configuration);
        }
    }

}
