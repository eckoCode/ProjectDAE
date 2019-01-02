
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejbs;

import entities.Client;
import entities.Contract;
import entities.StateOfSoftware;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
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
  
    @PostConstruct
    public void populateDB() {
        try {
            administratorBean.create("admin", "netbeansmerda", "Thiago Santos", "oiji@email.com", "Administrator Of Things");
            administratorBean.create("admin1", "1234", "Pedro G.", "nhb@email.com", "Administrator Of Things");
            administratorBean.create("admin2", "123", "João Pedro", "ryeb@email.com", "Administrator Of Things");
            administratorBean.create("admin3", "123", "Arlindo", "fasb@email.com", "Administrator Of Things");
            administratorBean.create("admin4", "123", "Messi", "messi@email.com", "The Boss");
            administratorBean.create("admin5", "123", "Neymar", "neymar@email.com", "Beginner admin");
            administratorBean.create("admin6", "123", "Countinho", "coutinho@email.com", "woww");
            administratorBean.create("admin7", "123", "Suarez", "suarez@email.com", "Getting old");
            administratorBean.create("admin8", "123", "Roberto Firmino", "rfirmino@email.com", "Administrator Of Things");
            administratorBean.create("admin9", "123", "David Silva", "dsilva@email.com", "Administrator Of Things");
            administratorBean.create("admin10", "123", "Gabriel Jesus", "gabijesus@email.com", "Administrator Of Things");
            administratorBean.create("admin11", "123", "Dybala", "dybala@email.com", "Administrator Of Things");
            administratorBean.create("admin12", "123", "Arturo Vidal", "vidal@email.com", "Administrator Of Things");
            administratorBean.create("admin13", "123", "Piqué", "shakira@email.com", "Administrator Of Things");

            clientBean.create("client1",   "netbeansmerda", "Rua fgfg gdf", "Marvel", "Kara - 1293715");
            clientBean.create("client2",  "123", "Rua qopwk", "Super Girl", "Dean - 758491453");
            clientBean.create("client3",  "123", "Rua wkeios", "MrPiracy", "Lena - 1237892");
            clientBean.create("client4",  "123", "Rua 20weke", "Amazon", "Harry - 8462781992");
            clientBean.create("client5",  "123", "Rua omoaslp", "Deadpool", "John - 84562145");
            clientBean.create("client6",  "123", "Rua fsdfsd2er", "GearBest", "Metal - 84562145");
            clientBean.create("client7",  "123", "Rua Super persons", "Apple", "Frank - 84562145");
            clientBean.create("client8",  "123", "Rua dkodok", "Trident", "Jen - 84562145");
            clientBean.create("client9",  "123", "Rua bvi90", "Asus", "Sam - 84562145");
            clientBean.create("client10",  "123", "Rua d03r8jer", "MSI", "Rock - 84562145");
            clientBean.create("client11",  "123", "Rua mmaso9a0jd", "Guardian", "Raven - 84562145");
            clientBean.create("client12",  "123", "Rua mdaosodp90q", "Manchester City", "Emily - 84562145");
            
             //Criar o template
            templateBean.create(1,"Software de gestão");
            templateBean.create(2,"Software de finanças");
            
            //Criar os product configuration
            configurationBean.create(1, new Client("Rua leiria", "Start","Ruben","Ruben","123456789"), "Cloud", StateOfSoftware.ACTIVE, "Free", new Contract( "ola", "Ruben"));
            configurationBean.create(2, new Client("Rua leiria", "Start","Tiago","Tiago","123456789"), "Cloud", StateOfSoftware.ACTIVE, "Free", new Contract( "ol3","Tiago"));

            artifactsBean.create(1, "downloads", "downloa", "jdh", "sbjknd");
            artifactsBean.create(2, "jad", "ds", "ssdafs", "sdfas");
            
            //Criar o softwares
            softwareBean.create(1,"Spots",1.0);
            softwareBean.create(2,"Reteck", 1.5);
            softwareBean.create(3,"GitNet", 1.0);

            
            //Criar o contrat 10
            contractBean.create(1,"Ruben","Nada");
            contractBean.create(2,"Tiago","Nothing");

            //Criar modulo
            moduleBean.create(1, "modulo 1");
            moduleBean.create(2, "modulo 2");

            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

}
