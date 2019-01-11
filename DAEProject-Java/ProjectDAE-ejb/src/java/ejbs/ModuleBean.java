/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ModuleDTO;
import entities.Modules;
import entities.Software;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rubenfilipe
 */
@Stateless
@Path("modules")
public class ModuleBean {
    @PersistenceContext
    EntityManager em;
    
     public void create(int id, String parametrization , LinkedList<Software> extensions) throws EntityExistsException {
        try {
            Modules m = em.find(Modules.class, id);
            if (m != null) {
                throw new EntityExistsException("ERROR: Can't create new artifact because already exists a artifact with this id: " + id);
            }

            Modules module = new Modules(parametrization, extensions);
            em.merge(module);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ModuleDTO moduleDTO) throws EntityExistsException {
        try {
            ModuleDTO m = em.find(ModuleDTO.class, moduleDTO.getId());
            if (m != null) {
                  throw new EntityExistsException("ERROR: Can't create new artifact because already exists a artifact with this id: " + moduleDTO.getId());
            }

            Modules modules = new Modules(moduleDTO.getParametrization(), moduleDTO.getExtension());
            em.merge(modules);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }



    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ModuleDTO> getAll() {
        try {
            List<Modules> modules = em.createNamedQuery("getAllModules").getResultList();
            return modulesToDTOs(modules);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

  

    public List<ModuleDTO> modulesToDTOs(List<Modules> modules) {
        List<ModuleDTO> modulesDTOs = new LinkedList<>();

        for (Modules m : modules) {
            modulesDTOs.add(moduleToDTO(m));
        }
        return modulesDTOs;
    }

    public ModuleDTO moduleToDTO(Modules modules) {
        return new ModuleDTO(modules.getId(),modules.getParametrization(), modules.getExtension());
    } 
}
