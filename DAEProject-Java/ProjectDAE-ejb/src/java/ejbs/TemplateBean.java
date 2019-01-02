/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.TemplateDTO;
import entities.Template;
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

/**
 *
 * @author rubenfilipe
 */

@Stateless
@Path("templates")
public class TemplateBean {
    @PersistenceContext
    EntityManager em;

    public Template create(int id,String description) throws EntityExistsException {
        try {
            Template t = em.find(Template.class, id);
            if (t != null) {
                throw new EntityExistsException("ERROR: Can't create new template because already exists a template with this id: " + id);
            }

            Template template = new Template(description);
            em.persist(template);
            return template;

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(TemplateDTO templateDTO) throws EntityExistsException {
        try {
            Template t = em.find(Template.class, templateDTO.getId());
            if (t != null) {
                throw new EntityExistsException("ERROR: Can't create new template because already exists a template with this id: " + templateDTO.getId());
            }

            Template template = new Template(templateDTO.getDescription());
            em.persist(template);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(TemplateDTO templateDTO) throws EntityDoesNotExistsException {
        System.out.println("AQUI" + templateDTO);
        try {
            Template t = em.find(Template.class, templateDTO.getId());

            if (t == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update that template because doesn't exists a template with thid id: " + templateDTO.getId());
            }

           t.setDescription(templateDTO.getDescription());

        } catch (EntityDoesNotExistsException e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Administrator"})
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TemplateDTO getAdministrator(@PathParam("id") int id) throws EntityExistsException {
        try {
            Template t = em.find(Template.class, id);
            if (t == null) {
                throw new EntityExistsException("ERROR: Can't get a template because doesn't exists a template with this id: " + id);

            }
            return templateToDTO(t);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TemplateDTO> getAll() {
        try {
            List<Template> templates = em.createNamedQuery("getAllTemplates").getResultList();
            return templatesToDTOs(templates);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    @RolesAllowed({"Administrator"})
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") int id) {
        try {
            Template tempalte = (Template) em.find(Template.class, id);

            if (tempalte == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't delete that template because doesn't exists a template with thid id: " + id);
            }

            em.remove(tempalte);
        } catch (EntityDoesNotExistsException ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<TemplateDTO> templatesToDTOs(List<Template> templates) {
        List<TemplateDTO> templatesDTO = new LinkedList<>();

        templates.forEach((t) -> {
            templatesDTO.add(templateToDTO(t));
        });

        return templatesDTO;
    }

    public TemplateDTO templateToDTO(Template template) {
        return new TemplateDTO(template.getId(),template.getDescription());
    }
}
