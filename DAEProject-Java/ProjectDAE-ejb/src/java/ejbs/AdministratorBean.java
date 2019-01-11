/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.AdministratorDTO;
import entities.Administrator;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("admins")
public class AdministratorBean {

    @PersistenceContext
    EntityManager em;

    public void create(String username, String password, String name, String email, String role) throws EntityExistsException {
        try {
            Administrator a = em.find(Administrator.class, username);
            if (a != null) {
                throw new EntityExistsException("ERROR: Can't create new administrator because already exists a administrator with the username: " + username);
            }

            Administrator administrator = new Administrator(name, role, email, username, password);
            em.persist(administrator);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(AdministratorDTO admin) throws EntityExistsException {
        try {
            Administrator a = em.find(Administrator.class, admin.getUsername());
            if (a != null) {
                throw new EntityExistsException("ERROR: Can't create new administrator because already exists a administrator with the username: " + admin.getUsername());
            }

            Administrator administrator = new Administrator(admin.getName(), admin.getRole(), admin.getEmail(), admin.getUsername(), admin.getPassword());
            em.persist(administrator);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Path("{username}")
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public AdministratorDTO getAdministrator(@PathParam("username") String username) throws EntityExistsException {
        try {
            Administrator a = em.find(Administrator.class, username);
            if (a == null) {
                // throw entitydoesnotexists
                //   throw new EntityExistsException("ERROR: Can't create new administrator because already exists a administrator with the username: " + admin.getUsername());
            }

            return administratorToDTO(a);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<AdministratorDTO> getAll() {
                System.out.println("consegui");

        try {
            List<Administrator> admins = em.createNamedQuery("getAllAdministrators").getResultList();
            return administratorsToDTOs(admins);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @RolesAllowed({"Administrator"})
    @Path("/{username}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("username") String username) {
        try {
            Administrator admin = (Administrator) em.find(Administrator.class, username);

            if (admin == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't delete that administrator because doesn't exists a administrator with the username: " + username);
            }

            em.remove(admin);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<AdministratorDTO> administratorsToDTOs(List<Administrator> admins) {
        List<AdministratorDTO> administratorsDTO = new LinkedList<>();

        admins.forEach((a) -> {
            administratorsDTO.add(administratorToDTO(a));
        });

        return administratorsDTO;
    }

    public AdministratorDTO administratorToDTO(Administrator admin) {
        return new AdministratorDTO(admin.getName(), admin.getEmail(), admin.getRole(), admin.getUsername(), admin.getPassword());
    }

    @PUT
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(AdministratorDTO admin) throws EntityDoesNotExistsException {
        try {
            Administrator a = em.find(Administrator.class, admin.getUsername());

            if (a == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update the administrator because doesn't exists any administrator with the username: " + admin.getUsername());
            }

            a.setUsername(admin.getUsername());
            a.setName(admin.getName());
            a.setEmail(admin.getEmail());
            a.setRole(admin.getRole());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

}
