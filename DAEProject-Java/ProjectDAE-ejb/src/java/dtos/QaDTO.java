/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import entities.ProductConfiguration;
import java.io.Serializable;

/**
 *
 * @author Tiago
 */
public class QaDTO implements Serializable{
    private int id;
    
    private ProductConfiguration prodConfig;
    
    private String question;
     
    private String answer;

    public QaDTO() {
    }

    public QaDTO(int id, ProductConfiguration prodConfig, String question, String answer) {
        this.id = id;
        this.prodConfig = prodConfig;
        this.question = question;
        this.answer = answer;
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
