package entities;

import entities.Client;
import entities.Contract;
import entities.Modules;
import entities.Qa;
import entities.Software;
import entities.StateOfSoftware;
import java.util.LinkedList;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-04T23:44:09")
@StaticMetamodel(ProductConfiguration.class)
public class ProductConfiguration_ { 

    public static volatile ListAttribute<ProductConfiguration, Qa> qaList;
    public static volatile SingularAttribute<ProductConfiguration, String> license;
    public static volatile SingularAttribute<ProductConfiguration, LinkedList> supportMatterials;
    public static volatile SingularAttribute<ProductConfiguration, String> hardwareRequired;
    public static volatile ListAttribute<ProductConfiguration, Software> softwares;
    public static volatile SingularAttribute<ProductConfiguration, StateOfSoftware> stateOfSoftware;
    public static volatile SingularAttribute<ProductConfiguration, Contract> contract;
    public static volatile SingularAttribute<ProductConfiguration, String> name;
    public static volatile SingularAttribute<ProductConfiguration, Client> client;
    public static volatile SingularAttribute<ProductConfiguration, String> description;
    public static volatile SingularAttribute<ProductConfiguration, Integer> id;
    public static volatile ListAttribute<ProductConfiguration, Modules> modules;

}