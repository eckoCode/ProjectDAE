/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template String, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author ecko
 */
@Entity
@Table(name="Artifact")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Artifact")
@NamedQueries({
    @NamedQuery(name = "getAllArtifacts", query = "SELECT a from Artifact a") 
})
public class Artifact implements Serializable{
    @Id
    @Column(nullable = false)
    private int id;
    
    @Column(nullable = false)
    private String sourceCode;
    
    private String database;
    
    private String scripts;
    
    private String libraries;

    public Artifact(int id, String sourceCode, String database, String scripts, String libraries) {
        this.id = id;
        this.sourceCode = sourceCode;
        this.database = database;
        this.scripts = scripts;
        this.libraries = libraries;
    }

    public Artifact() {
    }

    public Artifact(int id, String sourceCode) {
        this.id = id;
        this.sourceCode = sourceCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSourceCode() {
        return sourceCode;
    }

    public void setSourceCode(String sourceCode) {
        this.sourceCode = sourceCode;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getScripts() {
        return scripts;
    }

    public void setScripts(String scripts) {
        this.scripts = scripts;
    }

    public String getLibraries() {
        return libraries;
    }

    public void setLibraries(String libraries) {
        this.libraries = libraries;
    }    
}
