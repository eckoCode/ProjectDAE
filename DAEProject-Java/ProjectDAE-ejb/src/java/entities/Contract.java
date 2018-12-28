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
    @Column(nullable = false)
    private int id;
    
    @Column(nullable = false)
    private int idClient;
    
    @Column(nullable = false)
    private int idAdmin;
    
    @Column(nullable = false)
    private String contractInfo;
   

    public Contract() {
    }

    public Contract(int id,String contractInfo, int idClient, int idAdmin) {
        this.id = id;
        this.contractInfo = contractInfo;
        this.idClient = idClient;
        this.idAdmin = idAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(int idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(String contractInfo) {
        this.contractInfo = contractInfo;
    }
    
    
    
    
}
