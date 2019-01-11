/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ClientDTO;
import dtos.UserDTO;
import entities.Administrator;
import entities.Client;
import entities.User;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ecko
 */
@Stateless
@Path("users")
public class UserBean {
    @PersistenceContext
    EntityManager em;   
   
    
    @GET
    @Path("{username}")
    @RolesAllowed({"Administrator", "Client"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getUsername(@PathParam("username") String username)  {
        try {
            
            Client c = em.find(Client.class, username);
            if (c != null) {
               return "Client";
            }else{
                 Administrator a = em.find(Administrator.class, username);
                if(a != null)
                return "Administrator";
            }
          return null;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Path("/getEmail/{username}")
    @RolesAllowed({"Administrator", "Client"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public String getEmail(@PathParam("username") String username)  {
        try {
            
            Client c = em.find(Client.class, username);
            if (c != null) {
               return c.getEmail();
            }else{
                 Administrator a = em.find(Administrator.class, username);
                if(a != null)
                return a.getEmail();
            }
          return null;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
