/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.Artifact;
import java.io.Serializable;
import java.util.LinkedList;

/**
 *
 * @author rubenfilipe
 */
public class SoftwareDTO implements Serializable{
    private String name;
    private Double version;
    private int id;
    private LinkedList<Artifact> artifacts;

    public SoftwareDTO() {
    }

    public SoftwareDTO(int id, String name, Double version, LinkedList<Artifact> artifacts) {
        this.name = name;
        this.version = version;
        this.id=id;
        this.artifacts = artifacts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getVersion() {
        return version;
    }

    public void setVersion(Double version) {
        this.version = version;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LinkedList<Artifact> getArtifacts() {
        return artifacts;
    }

    public void setArtifacts(LinkedList<Artifact> artifacts) {
        this.artifacts = artifacts;
    } 
}
