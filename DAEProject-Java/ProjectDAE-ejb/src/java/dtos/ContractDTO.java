/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author rubenfilipe
 */
public class ContractDTO implements Serializable{
    private int id;
    
    private int idClient;
    
    private int idAdmin;
    
    private String contractInfo;

    public ContractDTO() {
    }

    public ContractDTO(int id, int idClient, int idAdmin, String contractInfo) {
        this.id = id;
        this.idClient = idClient;
        this.idAdmin = idAdmin;
        this.contractInfo = contractInfo;
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
