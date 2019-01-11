/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.QaDTO;
import entities.ProductConfiguration;
import entities.Qa;
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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Tiago
 */
@Stateless
@Path("qa")
public class QABean {
    @PersistenceContext
    EntityManager em;
    
    public void create(int id, ProductConfiguration prodConfig, String question, String answer) throws EntityExistsException {
        try {
            Qa s = em.find(Qa.class, id);
            if (s != null) {
                throw new EntityExistsException("ERROR: Can't create new QA because it already exists a Qa with this id: " + id);
            }
            Qa qa = new Qa(prodConfig, question, answer);
            em.persist(qa);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @POST
    @RolesAllowed({"Client", "Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(QaDTO qaDTO) throws EntityExistsException {
        try {
            Qa s = em.find(Qa.class, qaDTO.getId());
            if (s != null) {
                throw new EntityExistsException("ERROR: Can't create new QA because it already exists a Qa with this id: " + qaDTO.getId());
            }
            Qa qa = new Qa(qaDTO.getProdConfig(), qaDTO.getQuestion(), qaDTO.getAnswer());
            em.persist(qa);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(QaDTO qaDTO) throws EntityDoesNotExistsException {
        try {
            Qa qa = em.find(Qa.class, qaDTO.getId());

            if (qa == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update that QA because it doesn't exist a QA with this id: " + qaDTO.getId());
            }

            qa.setAnswer(qaDTO.getAnswer());

        } catch (EntityDoesNotExistsException e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<QaDTO> getAll() {
        try {
            List<Qa> allQa = em.createNamedQuery("getAllQa").getResultList();
            return qaToDTOs(allQa);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    public List<QaDTO> qaToDTOs(List<Qa> allQa) {
        List<QaDTO> qaDTOs = new LinkedList<>();

        allQa.forEach((qa) -> {
            qaDTOs.add(qaToDTO(qa));
        });

        return qaDTOs;
    }
    
    public QaDTO qaToDTO(Qa qa) {
        return new QaDTO(qa.getId(), qa.getProdConfig(),qa.getQuestion(), qa.getAnswer());
    }
    
}
