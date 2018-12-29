package entities;

import entities.Software;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-12-29T11:23:56")
@StaticMetamodel(Template.class)
public class Template_ { 

    public static volatile ListAttribute<Template, Software> software;
    public static volatile SingularAttribute<Template, String> description;
    public static volatile SingularAttribute<Template, Integer> id;

}