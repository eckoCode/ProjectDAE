/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.SoftwareDTO;
import entities.Software;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PreDestroy;
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

/**
 *
 * @author rubenfilipe
 */
@Stateless
@Path("software")
public class SoftwareBean {
    
    @PersistenceContext
    EntityManager em;
    
    @PreDestroy
    public void destruct()
    {
        em.close();
    }

    public void create(int id, String name, Double version) throws EntityExistsException {
        try {
            Software s = em.find(Software.class, id);
            if (s != null) {
                throw new EntityExistsException("ERROR: Can't create new software because already exists a software with this id: " + id);
            }

            Software software = new Software( name, version);
            em.persist(software);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(SoftwareDTO softwareDTO) throws EntityExistsException {
        try {
            Software p = em.find(Software.class, softwareDTO.getId());
            if (p != null) {
                throw new EntityExistsException("ERROR: Can't create new software because already exists a software with this id: " + softwareDTO.getId());
            }

            Software software = new Software(softwareDTO.getName(), softwareDTO.getVersion());
            em.persist(software);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public SoftwareDTO getSoftwareDTO(@PathParam("id") int id) throws EntityExistsException {
        try {
            Software s = em.find(Software.class, id);
            if (s == null) {
                throw new EntityExistsException("ERROR: Can't get a software because doesn't exists a software with this id: " + id);

            }
            return softwareToDTO(s);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }


    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<SoftwareDTO> getAll() {
        try {
            List<Software> softwares = em.createNamedQuery("getAllsoftware").getResultList();
            return softwareToDTOs(softwares);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") int id) {
        try {
            Software s = em.find(Software.class, id);

            if (s == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't delete that software because doesn't exists a software with thid id: " + id);
            }

            em.remove(s);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<SoftwareDTO> softwareToDTOs(List<Software> softwares) {
        List<SoftwareDTO> softwareDTOs = new LinkedList<>();

        softwares.forEach((s) -> {
            softwareDTOs.add(softwareToDTO(s));
        });

        return softwareDTOs;
    }

    public SoftwareDTO softwareToDTO(Software software) {
        return new SoftwareDTO(software.getName(),software.getVersion());
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(SoftwareDTO softwareDTO) throws EntityDoesNotExistsException {
        try {
            Software s = em.find(Software.class, softwareDTO.getId());

            if (s == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update that software because doesn't exists a product software with thid id: " + softwareDTO.getId());
            }

         s.setVersion(softwareDTO.getVersion());
         s.setName(softwareDTO.getName());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
}
