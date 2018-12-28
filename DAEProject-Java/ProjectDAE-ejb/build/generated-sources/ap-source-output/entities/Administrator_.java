package entities;

import entities.UserGroup;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-28T23:06:39")
@StaticMetamodel(Administrator.class)
public class Administrator_ extends User_ {

    public static volatile SingularAttribute<Administrator, String> role;
    public static volatile SingularAttribute<Administrator, String> name;
    public static volatile SingularAttribute<Administrator, UserGroup> userGroup;
    public static volatile SingularAttribute<Administrator, String> email;

}