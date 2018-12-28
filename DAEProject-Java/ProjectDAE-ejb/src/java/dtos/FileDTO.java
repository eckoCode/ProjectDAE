/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 *
 * @ 
 */
@XmlRootElement(name = "File")
@XmlAccessorType(XmlAccessType.FIELD)
//@XmlSeeAlso(ArtifactDTO.class)
public class FileDTO implements Serializable{
    private String filename;
    private String mimetype;

    public FileDTO() {
    }

    public FileDTO(String filename, String mimetype) {
        this.filename = filename;
        this.mimetype = mimetype;
    }
    
    public void reset(){
        setFilename(null);
        setMimetype(null);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMimetype() {
        return mimetype;
    }

    public void setMimetype(String mimetype) {
        this.mimetype = mimetype;
    }
}
