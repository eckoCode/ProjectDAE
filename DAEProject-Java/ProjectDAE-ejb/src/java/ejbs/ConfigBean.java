
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Artifact;
import entities.Client;
import entities.Contract;
import entities.Modules;
import entities.ProductConfiguration;
import entities.Software;
import entities.StateOfSoftware;
import java.util.Arrays;
import java.util.LinkedList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
public class ConfigBean {

    @EJB
    private AdministratorBean administratorBean;

    @EJB
    private ClientBean clientBean;

    @EJB
    private TemplateBean templateBean;

    @EJB
    private SoftwareBean softwareBean;

    @EJB
    private ContractBean contractBean;
    
    @EJB
    private ProductConfigurationBean configurationBean;
    
    @EJB
    private ArtifactsBean artifactsBean;
    
    @EJB
    private ModuleBean moduleBean;
    
    @EJB
    private QABean qaBean;
  
    @PostConstruct
    public void populateDB() {
        try {
            //Administrator
            administratorBean.create("admin", "rubenlopes", "Ruben Lopes", "rubinholopes1996@gmail.com", "Software Developer");
            administratorBean.create("admin1", "tiagobatista", "Tiago Batista", "tiago.miguel.2@hotmail.com", "Software Developer");
            administratorBean.create("admin2", "joaomarques", "Joao Marques", "joaomarques@gmail.com", "Software Developer");
            
            //Client
            clientBean.create("client1",   "rubenlopes", "Rua fgfg gdf", "Marvel", "Kara - 1293715","rubinholopes1996@gmail.com");
            clientBean.create("client2",  "tiagobatista", "Rua qopwk", "Super Girl", "Dean - 758491453","rubinholopes1996@gmail.com");
            clientBean.create("client3",  "joaomarques", "Rua wkeios", "MrPiracys", "Lena - 1237892","tiago.miguel.2@hotmail.com");
            
            
            //Criar os product configuration
            LinkedList<Modules> listModules = new LinkedList<>();
            LinkedList<Software> extensions = new LinkedList<>();
            LinkedList<Artifact> listArtifact = new LinkedList<>();
            LinkedList<String> listSupportMaterials = new LinkedList<>();
            LinkedList<String> listSupportMaterials_2 = new LinkedList<>();
            LinkedList<Software> listSoftwares = new LinkedList<>();
            LinkedList<Software> listSoftwares_2 = new LinkedList<>();
            
            listSoftwares.add(new Software("Software Gestao", 18.0, listArtifact));
            listSoftwares.add(new Software("Software Maquinaria", 23.0, listArtifact));
            listSoftwares_2.add(new Software("Software Teste", 20.1, listArtifact));
            listSoftwares_2.add(new Software("Software", 25.3, listArtifact));
            
            listModules.add(new Modules("Test/Action parameters", extensions));
          
            listSupportMaterials.add("");
            listSupportMaterials.add("");
            listSupportMaterials_2.add("");
            listSupportMaterials_2.add("");
            listArtifact.add(new Artifact("", "", "", ""));
            listArtifact.add(new Artifact("", "", "", ""));
           
            extensions.add(new Software("Software Logico", 19.0, listArtifact));
      

             //Criar o template
           templateBean.create(1,"Software de gestão", "Template 1", "i5 3.4GHz", "GNU", listSoftwares);
           templateBean.create(2,"Software de gestão", "Template 1", "i5 3.4GHz", "GNU", listSoftwares_2);
            
            configurationBean.create(1,new Client("Rua leiria", "Start","Ruben","Ruben","123456789","rubinholopes1996@gmail.com"),"nome1","vai dar" ,"Cloud", StateOfSoftware.ACTIVE, "Free", new Contract( "ola", "Ruben"), listSoftwares, listModules,null);
            configurationBean.create(2, new Client("Rua leiria", "Start","Tiago","Tiago","123456789","rubinholopes1996@gmail.com"),"nome2","vai dar2","Cloud", StateOfSoftware.ACTIVE, "Free", new Contract( "ol3","Tiago"), listSoftwares_2, listModules,null);
            configurationBean.create(2, new Client("Rua leiria", "Start","Joao","joao","qwerty123456789","rubinholopes1996@gmail.com"),"nome2","vai dar2","Cloud", StateOfSoftware.ACTIVE, "Free", new Contract( "ol3","Tiago"), listSoftwares_2, listModules,null);
            
            //Criar o contrat 10
            contractBean.create(1,"Ruben","Nada");
            contractBean.create(2,"Tiago","Nothing");
            
              
            qaBean.create(1, new ProductConfiguration(new Client("Rua leiria", "Start","Joao","eueu","qwerty123456789","rubinholopes1996@gmail.com"),"nome2","Das Questoes 1","Cloud", StateOfSoftware.ACTIVE, "Free", new Contract( "oleeee","Tiago"), listModules, listSoftwares_2, null), "Isto Funfa?", "Funfa sim senhor");
            qaBean.create(2,
                    new ProductConfiguration(
                            new Client("Rua leiria", "Start","Joao","tutu","qwerty123456789","rubinholopes1996@gmail.com")
                            ,
                            "nome2",
                            "Das Questoes 2",
                            "Cloud",
                            StateOfSoftware.ACTIVE,
                            "Free",
                            new Contract( "oloooo","Tiago"),
                            listModules,
                            listSoftwares_2,
                            null
                    ),
                    "Isto Funfa ainda melhor?",
                    "Ya");
            
           
             
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
