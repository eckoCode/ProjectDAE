package entities;

import entities.Client;
import entities.Contract;
import entities.File;
import entities.Modules;
import entities.StateOfSoftware;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-29T11:23:56")
@StaticMetamodel(ProductConfiguration.class)
public class ProductConfiguration_ extends Template_ {

    public static volatile SingularAttribute<ProductConfiguration, String> license;
    public static volatile ListAttribute<ProductConfiguration, File> supportMatterials;
    public static volatile SingularAttribute<ProductConfiguration, String> hardwareRequired;
    public static volatile SingularAttribute<ProductConfiguration, StateOfSoftware> stateOfSoftware;
    public static volatile SingularAttribute<ProductConfiguration, Contract> contract;
    public static volatile SingularAttribute<ProductConfiguration, Client> client;
    public static volatile ListAttribute<ProductConfiguration, Modules> modules;

}