/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ProductConfigurationDTO;
import entities.Client;
import entities.Contract;
import entities.ProductConfiguration;
import entities.Software;
import entities.StateOfSoftware;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
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
@Path("productConfig")
public class ProductConfigurationBean {
    @PersistenceContext
    EntityManager em;

    public void create(int id, String description, Client client, String hardwareRequired, StateOfSoftware stateOfSoftware, String license, Contract contract) throws EntityExistsException {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, id);
            if (p != null) {
                throw new EntityExistsException("ERROR: Can't create new product configuration because already exists a product configuration with this id: " + id);
            }

            ProductConfiguration productConfiguration = new ProductConfiguration(id, client, hardwareRequired, stateOfSoftware, license, description, contract);
            em.persist(productConfiguration);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ProductConfigurationDTO productConfigurationDTO) throws EntityExistsException {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, productConfigurationDTO.getId());
            if (p != null) {
                throw new EntityExistsException("ERROR: Can't create new product configuration because already exists a product configuration with this id: " + productConfigurationDTO.getId());
            }

            ProductConfiguration productConfiguration = new ProductConfiguration(productConfigurationDTO.getId(),productConfigurationDTO.getClient(),productConfigurationDTO.getHardwareRequired(),productConfigurationDTO.getstateOfSoftware(),productConfigurationDTO.getLicense(),productConfigurationDTO.getDescription(), productConfigurationDTO.getContract());
            em.persist(productConfiguration);
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
    public ProductConfigurationDTO getProductConfiguration(@PathParam("id") int id) throws EntityExistsException {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, id);
            if (p == null) {
                throw new EntityExistsException("ERROR: Can't get a product configuration because doesn't exists a product with this id: " + p.getId());

            }
            return productConfigurationToDTO(p);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }


    @GET
    //@RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Path("all")
    public List<ProductConfigurationDTO> getAll() {
        try {
            List<ProductConfiguration> productConfigurations = em.createNamedQuery("getAllproductConfigurations").getResultList();
            return productConfigurationsToDTOs(productConfigurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @DELETE
    //@RolesAllowed({"Administrator"})
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void remove(@PathParam("id") int id) {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, id);

            if (p == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't delete that product configuration because doesn't exists a product configuration with thid id: " + id);
            }

            em.remove(p);
        } catch (Exception ex) {
            throw new EJBException(ex.getMessage());
        }
    }

    public List<ProductConfigurationDTO> productConfigurationsToDTOs(List<ProductConfiguration> productConfigurations) {
        List<ProductConfigurationDTO> productConfigurationDTOs = new LinkedList<ProductConfigurationDTO>();

        for (ProductConfiguration p : productConfigurations) {
            productConfigurationDTOs.add(productConfigurationToDTO(p));
        }

        return productConfigurationDTOs;
    }

    public ProductConfigurationDTO productConfigurationToDTO(ProductConfiguration productConfiguration) {
        return new ProductConfigurationDTO(productConfiguration.getClient(),productConfiguration.getHardwareRequired(),productConfiguration.getstateOfSoftware(),productConfiguration.getLicense(),productConfiguration.getId(),productConfiguration.getDescription(), productConfiguration.getContract());
    }

    @PUT
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(ProductConfigurationDTO productConfigurationDTO) throws EntityDoesNotExistsException {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, productConfigurationDTO.getId());

            if (p == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update that product configuration because doesn't exists a product configuration with thid id: " + p.getId());
            }

          p.setClient(productConfigurationDTO.getClient());
          p.setDescription(productConfigurationDTO.getDescription());
          p.setHardwareRequired(productConfigurationDTO.getHardwareRequired());
          p.setId(productConfigurationDTO.getId());
          p.setLicense(productConfigurationDTO.getLicense());
          p.setstateOfSoftware(productConfigurationDTO.getstateOfSoftware());
          p.setSupportMatterials(productConfigurationDTO.getSupportMatterials());

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    
}
