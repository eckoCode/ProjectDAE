/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template String, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;

/**
 *
 * @author ecko
 */
public class ArtifactDTO implements Serializable{
    private int id;
    private String sourceCode;
    private String database;
    private String scripts;
    private String librarys;

    public ArtifactDTO(int id, String sourceCode, String database, String scripts, String librarys) {
        this.id = id;
        this.sourceCode = sourceCode;
        this.database = database;
        this.scripts = scripts;
        this.librarys = librarys;
    }

    public ArtifactDTO() {
    }

    public ArtifactDTO(int id, String sourceCode) {
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

    public String getLibrarys() {
        return librarys;
    }

    public void setLibrarys(String librarys) {
        this.librarys = librarys;
    }
   
    
}
