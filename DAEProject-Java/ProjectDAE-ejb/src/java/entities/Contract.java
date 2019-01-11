/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author rubenfilipe
 */
@Entity
@Table(name = "CONTRACT")
@NamedQuery(name = "getAllContract", query = "SELECT c FROM Contract c")
public class Contract implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @Column(nullable = false)
    private String idClient;
    
    @Column(nullable = false)
    private String contractInfo;
   

    public Contract() {
    }

    public Contract(String contractInfo, String idClient) {
        this.contractInfo = contractInfo;
        this.idClient = idClient;
    }

    public int getId() {
        return id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }
    
  
    public void setId(int id) {
        this.id = id;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(String contractInfo) {
        this.contractInfo = contractInfo;
    }
    
    
    
    
}
