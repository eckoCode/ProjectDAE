/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import com.sun.jersey.multipart.FormDataParam;
import dtos.TemplateDTO;
import entities.Software;
import entities.Template;
import exceptions.EntityDoesNotExistsException;
import exceptions.EntityExistsException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
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
import javax.ws.rs.core.Response.ResponseBuilder;

/**
 *
 * @author rubenfilipe
 */

@Stateless
@Path("templates")
public class TemplateBean {
    @PersistenceContext
    EntityManager em;
    
    private static final String UPLOAD_FOLDER = Paths.get(".").toAbsolutePath().normalize().toString();
    
    private LinkedList<String> readCSV(String location){
        String csvFile = location;
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        String[] csv = null;
        LinkedList<String> csvFinal= new LinkedList<>();
        try {

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {

                // use comma as separator
                csv = line.split(cvsSplitBy);
                if(csv.length>3){
                    for(int i = 0; i<csv.length;i++){
                     csvFinal.add(csv[i].toString());
                    }
                }


            }
            System.out.println(csvFinal);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return csvFinal;
    }

    
    @GET
    @Path("getFile/{location}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("location") String location) {
        File file = new File(UPLOAD_FOLDER+"/"+location);
        ResponseBuilder response = Response.ok((Object) file);
        response.header("Content-Disposition", "attachment;filename=classes.jar");
        return response.build();
    }
    
    @POST
    @RolesAllowed({"Administrator"})
    @Path("csv")
    @Consumes({MediaType.MULTIPART_FORM_DATA, MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<TemplateDTO> uploadCSV(@FormDataParam("file") InputStream uploadedInputStream ) throws IOException  {
           
        double x = Math.random();
        
	String uploadedFileLocation = UPLOAD_FOLDER;
        saveToFile(uploadedInputStream, x+".csv")
                ;
        LinkedList<Template> templates = new LinkedList<>();
        LinkedList<String> templateCSV = readCSV(UPLOAD_FOLDER+"/"+x+".csv");
        for(int i=0; i<templateCSV.size(); i+=5){
            Software s = em.find(Software.class, Integer.parseInt(templateCSV.get(i+4)));
            LinkedList<Software> software = new LinkedList<>();
            software.add(s);
            Template template = new Template(templateCSV.get(i), templateCSV.get(i+1), templateCSV.get(i+2), templateCSV.get(i+3), software);
            em.persist(template);
            templates.add(template);
        }
        
       
        return templatesToDTOs(templates);
        
    }

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
         
        Template t = em.find(Template.class, id);
        t.setSupportMaterials(uploadedFileLocation+"/"+id+"_"+x+name);
        em.persist(t);
        
        
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
    

    public Template create(int id, String description, String name, String hardware, String license, 
            LinkedList<Software> software) throws EntityExistsException {
        try {
            Template t = em.find(Template.class, id);
            if (t != null) {
                throw new EntityExistsException("ERROR: Can't create new template because already exists a template with this id: " + id);
            }

            Template template = new Template(description, name, hardware, license, software);
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
    @Path("create")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TemplateDTO create(TemplateDTO templateDTO) throws EntityExistsException {
        try {
            Template t = em.find(Template.class, templateDTO.getId());
            if (t != null) {
                throw new EntityExistsException("ERROR: Can't create new template because already exists a template with this id: " + templateDTO.getId());
            }
               
            Template template = new Template(templateDTO.getDescription(),templateDTO.getName(), templateDTO.getHardware(), templateDTO.getLicense(), templateDTO.getSoftware());
            em.persist(template);
            return templateToDTO(template);
        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }
    
    @PUT
    @RolesAllowed({"Administrator"})
    @Path("update")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void updateRest(TemplateDTO templateDTO) throws EntityDoesNotExistsException {
        System.out.println("AQUI" + templateDTO);
        try {
            Template t = em.find(Template.class, templateDTO.getId());

            if (t == null) {
                throw new EntityDoesNotExistsException("ERROR: Can't update that template because doesn't exists a template with thid id: " + templateDTO.getId());
            }

           t.setDescription(templateDTO.getDescription());
           t.setName(templateDTO.getName());
           t.setHardware(templateDTO.getHardware());
           t.setLicense(templateDTO.getLicense());
           t.setSoftware(templateDTO.getSoftware());
           
           em.merge(t);
        } catch (EntityDoesNotExistsException e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @RolesAllowed({"Administrator"})
    @Path("get/{id}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public TemplateDTO getTemplate(@PathParam("id") int id) throws EntityExistsException {
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
    @Produces({MediaType.APPLICATION_JSON})
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
    @Path("delete/{id}")
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
        return new TemplateDTO(template.getId(),template.getDescription(), template.getName(), template.getHardware(), template.getLicense(),template.getSoftware(),template.getSupportMaterials());
    }
}
