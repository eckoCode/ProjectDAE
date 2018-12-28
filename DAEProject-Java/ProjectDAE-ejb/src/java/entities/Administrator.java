/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Administrator")
@NamedQueries({
    @NamedQuery(name = "getAllAdministrators",
            query = "SELECT a from Administrator a ORDER BY a.name")
})
public class Administrator extends User implements Serializable {

    private String name;
    private String role;
    private String email;
    private UserGroup userGroup;

    public Administrator() {
        super();
    }

    public Administrator(String name, String role, String email, String username, String password) {
        super(username, password);
        this.name = name;
        this.role = role;
        this.email = email;
        this.userGroup = UserGroup.Administrator;
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
