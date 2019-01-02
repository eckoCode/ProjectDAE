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
    
    private String idClient;
    
    
    private String contractInfo;

    public ContractDTO() {
    }

    public ContractDTO(int id, String idClient, String contractInfo) {
        this.id = id;
        this.idClient = idClient;
        this.contractInfo = contractInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public String getContractInfo() {
        return contractInfo;
    }

    public void setContractInfo(String contractInfo) {
        this.contractInfo = contractInfo;
    }
    
    
    
}
