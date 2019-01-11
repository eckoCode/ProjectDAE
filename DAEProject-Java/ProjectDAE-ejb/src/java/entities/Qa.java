/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Tiago
 */
@Entity
@Table(name = "Qa")
@NamedQuery(name = "getAllQa", query = "SELECT q FROM Qa q")
public class Qa implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="PROD_CONFIG_ID", nullable = false)
    private ProductConfiguration prodConfig;
    
    @Column(nullable = false)
    private String question;
     
    @Column(nullable = false)
    private String answer;

    public Qa(ProductConfiguration prodConfig, String question, String answer) {
        this.prodConfig = prodConfig;
        this.question = question;
        this.answer = answer;
    }

    public Qa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProductConfiguration getProdConfig() {
        return prodConfig;
    }

    public void setProdConfig(ProductConfiguration prodConfig) {
        this.prodConfig = prodConfig;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    
    
    
}
