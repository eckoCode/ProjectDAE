/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import dtos.ContractDTO;
import entities.Contract;
import exceptions.EntityExistsException;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author rubenfilipe
 */
@Stateless
@Path("contract")
public class ContractBean {
    @PersistenceContext
    EntityManager em;
     
    @PreDestroy
    public void destruct()
    {
        em.close();
    }
     
     public void create(int id, int idClient, int idAdmin, String contractInfo) throws EntityExistsException {
        try {
            Contract c = em.find(Contract.class, id);
            if (c != null) {
                throw new EntityExistsException("ERROR: Can't create new contract because already exists a contract with this id: " + id);
            }

            Contract contract = new Contract(id, contractInfo,idClient, idAdmin);
            em.persist(contract);

        } catch (EntityExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public void create(ContractDTO contract) throws EntityExistsException {
        try {
            Contract c = em.find(Contract.class, contract.getId());
            if (c != null) {
                throw new EntityExistsException("ERROR: Can't create new contract because already exists a contract with this id: " + contract.getId());
            }

            Contract contract1 = new Contract(contract.getId(),contract.getContractInfo(),contract.getIdClient(),contract.getIdAdmin());
            em.persist(contract1);
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
    public ContractDTO getContract(@PathParam("id") int id) throws EntityExistsException {
        try {
            Contract c = em.find(Contract.class, id);
            if (c == null) {
                
            }

            return contractToDTO(c);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<ContractDTO> getAll() {
        try {
            // o EntityManager é que sabe como pegar todos os students 
            // este bean vai ao entitymanager que depois vai à BD buscar os dados 
            // o entitymanager sabe que tem que ir à entity Student pois é essa a entidade que tem a NamedQuery getAllStudents 
            // não pode haver duas entidades com NamedQuery iguais 
            List<Contract> contracts = em.createNamedQuery("getAllContracts").getResultList();
            return contractsToDTOs(contracts);
        } catch (Exception e) {
            throw new EJBException(e.getMessage());
        }
    }

        public List<ContractDTO> contractsToDTOs(List<Contract> contracts) {
        List<ContractDTO> contractsDTO = new LinkedList<ContractDTO>();

        for (Contract c : contracts) {
            contractsDTO.add(contractToDTO(c));
        }

        return contractsDTO;
    }

    public ContractDTO contractToDTO(Contract contract) {
        return new ContractDTO(contract.getId(), contract.getIdClient(),contract.getIdAdmin(),contract.getContractInfo());
    }
    
}
