/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import com.sun.jersey.multipart.FormDataParam;
import dtos.ProductConfigurationDTO;
import entities.Client;
import entities.Contract;
import entities.Modules;
import entities.ProductConfiguration;
import entities.Qa;
import entities.Software;
import entities.StateOfSoftware;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityExistsException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
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
import javax.ws.rs.core.Response;

/**
 *
 * @author rubenfilipe
 */
@Stateless
@Path("productConfig")
public class ProductConfigurationBean {
    @PersistenceContext
    EntityManager em;
    
    
    private static final String UPLOAD_FOLDER = Paths.get(".").toAbsolutePath().normalize().toString();

    @POST
    @RolesAllowed({"Administrator"})
    @Path("supportMaterials/{name}/{id}")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response uploadFile(@FormDataParam("file") InputStream uploadedInputStream,@PathParam("name") String name, @PathParam("id") int id ) throws IOException {
        if (uploadedInputStream == null )
            return Response.status(400).entity("Invalid form data").build();

         double x = Math.random();
        
	String uploadedFileLocation = UPLOAD_FOLDER;
        saveToFile(uploadedInputStream, id+"_"+x+name);
        ProductConfiguration p = em.find(ProductConfiguration.class, id);
       
        p.setSupportMatterials(uploadedFileLocation+"/"+id+"_"+x+name);
        em.persist(p);
        
        return Response.status(200).entity("File saved to " + uploadedFileLocation).build();
    }    
    
    
     private void saveToFile(InputStream inStream, String target)
            throws IOException {
        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];
        out = new FileOutputStream(new File(target));
        while ((read = inStream.read(bytes)) != -1) {
            out.write(bytes, 0, read);
        }
        out.flush();
        out.close();
    }
    
    

    public void create(int id, Client client,String name, String description,String hardwareRequired, StateOfSoftware stateOfSoftware, String license, Contract contract, LinkedList<Software> softwares, LinkedList<Modules> modules, LinkedList<Qa> qa) throws EntityExistsException {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, id);
            if (p != null) {
                throw new EntityExistsException("ERROR: Can't create new product configuration because already exists a product configuration with this id: " + id);
            }

            ProductConfiguration productConfiguration = new ProductConfiguration( client, name, description , hardwareRequired, stateOfSoftware, license, contract, modules, softwares, qa);
            em.persist(productConfiguration);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public ProductConfigurationDTO create(ProductConfigurationDTO productConfigurationDTO) throws EntityExistsException {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, productConfigurationDTO.getId());
            if (p != null) {
                throw new EntityExistsException("ERROR: Can't create new product configuration because already exists a product configuration with this id: " + productConfigurationDTO.getId());
            }
             Client c = em.find(Client.class, productConfigurationDTO.getUsername());
            
            ProductConfiguration productConfiguration = new ProductConfiguration(c, productConfigurationDTO.getName(), productConfigurationDTO.getDescription() ,productConfigurationDTO.getHardwareRequired(),productConfigurationDTO.getStateOfSoftware(),productConfigurationDTO.getLicense(), productConfigurationDTO.getContract(), productConfigurationDTO.getModules(),productConfigurationDTO.getSoftwares(), productConfigurationDTO.getQa());
            em.persist(productConfiguration);
            c.addConfiguration(productConfiguration);
             return productConfigurationToDTO(productConfiguration);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @GET
    @RolesAllowed({"Administrator", "Client"})
    @Path("getFile/{location}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("location") String location) {
        File file = new File(UPLOAD_FOLDER+"/"+location);
        Response.ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename=classes.jar");
        return response.build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"Administrator"})
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
    @Path("client/{username}")
    @RolesAllowed({"Client"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ProductConfigurationDTO> getProductConfigurationForClient(@PathParam("username") String username) throws EntityExistsException {
        try {
            List<ProductConfiguration> productConfigurations = em.createNamedQuery("getProductsByUsername").setParameter("username", username).getResultList();
            return productConfigurationsToDTOs(productConfigurations);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }



    @GET
    @RolesAllowed({"Administrator"})
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ProductConfigurationDTO> getAll() {
        try {
            List<ProductConfiguration> productConfigurations = em.createNamedQuery("getAllproductConfigurations").getResultList();
            return productConfigurationsToDTOs(productConfigurations);
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
        List<ProductConfigurationDTO> productConfigurationDTOs = new LinkedList<>();

        for (ProductConfiguration p : productConfigurations) {
            productConfigurationDTOs.add(productConfigurationToDTO(p));
        }

        return productConfigurationDTOs;
    }

    public ProductConfigurationDTO productConfigurationToDTO(ProductConfiguration productConfiguration) {
        return new ProductConfigurationDTO(productConfiguration.getClient().getUsername(),productConfiguration.getHardwareRequired(),productConfiguration.getStateOfSoftware(),productConfiguration.getLicense(),productConfiguration.getId(), productConfiguration.getContract(),productConfiguration.getName(), productConfiguration.getDescription(),productConfiguration.getModules(), productConfiguration.getSoftwares(), productConfiguration.getSupportMatterials(), productConfiguration.getQa());
    }

    @PUT
    @RolesAllowed({"Administrator"})
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(ProductConfigurationDTO productConfigurationDTO) throws EntityDoesNotExistsException {
        try {
            ProductConfiguration p = em.find(ProductConfiguration.class, productConfigurationDTO.getId());

            if (p == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update that product configuration because doesn't exists a product configuration with thid id: " + productConfigurationDTO.getId());
            }
          Client c = em.find(Client.class, productConfigurationDTO.getUsername());
            
          p.setClient(c);
          p.setHardwareRequired(productConfigurationDTO.getHardwareRequired());
          p.setId(productConfigurationDTO.getId());
          p.setLicense(productConfigurationDTO.getLicense());
          p.setStateOfSoftware(productConfigurationDTO.getStateOfSoftware());
          p.setName(productConfigurationDTO.getName());
          p.setDescription(productConfigurationDTO.getDescription());
          p.setSoftwares(productConfigurationDTO.getSoftwares());
          p.setModules(productConfigurationDTO.getModules());
          
          em.merge(p);

        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
}
