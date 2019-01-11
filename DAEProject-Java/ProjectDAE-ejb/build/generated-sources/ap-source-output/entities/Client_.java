package entities;

import entities.ProductConfiguration;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-04T23:44:09")
@StaticMetamodel(Client.class)
public class Client_ extends User_ {

    public static volatile SingularAttribute<Client, String> address;
    public static volatile SingularAttribute<Client, String> companyName;
    public static volatile SingularAttribute<Client, String> contactPerson;
    public static volatile ListAttribute<Client, ProductConfiguration> productConfigurations;

}