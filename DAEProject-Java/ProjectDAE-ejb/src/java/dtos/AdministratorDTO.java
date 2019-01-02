/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtos;

import java.io.Serializable;


public class AdministratorDTO extends UserDTO implements Serializable  {
    private String name;
    private String role;
    private String email;

    public AdministratorDTO() {
    }

    public AdministratorDTO(String name, String email, String role, String username, String password) {
        super(username, password);
        this.name = name;
        this.role = role;
        this.email = email;
    }
    
    @Override
    public void reset() {
        super.reset();
        setName(null);
        setRole(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    
     
    
}
