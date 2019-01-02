package entities;

import entities.User;
import entities.UserGroup.GROUP;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-02T15:24:47")
@StaticMetamodel(UserGroup.class)
public class UserGroup_ { 

    public static volatile SingularAttribute<UserGroup, GROUP> group_name;
    public static volatile SingularAttribute<UserGroup, User> user;

}