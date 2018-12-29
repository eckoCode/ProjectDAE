/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template String, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ArtifactDTO;
import entities.Artifact;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
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
 * @author ecko
 */
@Stateless
@Path("artifacts")
public class ArtifactsBean {
     @PersistenceContext
    EntityManager em;
    
     public void create(int id, String sourceCode, String database, String scripts, String libraries) throws EntityExistsException {
        try {
            Artifact a = em.find(Artifact.class, id);
            if (a != null) {
                throw new EntityExistsException("ERROR: Can't create new artifact because already exists a artifact with this id: " + id);
            }

            Artifact artifact = new Artifact(id, sourceCode, database, scripts, libraries);
            em.persist(artifact);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ArtifactDTO artifactDTO) throws EntityExistsException {
        try {
            Artifact a = em.find(Artifact.class, artifactDTO.getId());
            if (a != null) {
                  throw new EntityExistsException("ERROR: Can't create new artifact because already exists a artifact with this id: " + artifactDTO.getId());
            }

            Artifact artifact = new Artifact(artifactDTO.getId(), artifactDTO.getSourceCode(), artifactDTO.getDatabase(), artifactDTO.getScripts(), artifactDTO.getLibrarys());
            em.persist(artifact);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }



    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ArtifactDTO> getAll() {
        try {
            List<Artifact> artifacts = em.createNamedQuery("getAllartifacts").getResultList();
            return artifactsToDTOs(artifacts);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

  

    public List<ArtifactDTO> artifactsToDTOs(List<Artifact> artifacts) {
        List<ArtifactDTO> artifactsDTOs = new LinkedList<ArtifactDTO>();

        for (Artifact a : artifacts) {
            artifactsDTOs.add(artifactToDTO(a));
        }
        return artifactsDTOs;
    }

    public ArtifactDTO artifactToDTO(Artifact artifact) {
        return new ArtifactDTO(artifact.getId(),artifact.getSourceCode());
    } 
}
