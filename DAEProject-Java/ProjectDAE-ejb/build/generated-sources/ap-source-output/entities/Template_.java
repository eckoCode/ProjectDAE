package entities;

import entities.Software;
import java.util.LinkedList;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-04T23:44:09")
@StaticMetamodel(Template.class)
public class Template_ { 

    public static volatile SingularAttribute<Template, String> license;
    public static volatile ListAttribute<Template, Software> software;
    public static volatile SingularAttribute<Template, LinkedList> supportMaterials;
    public static volatile SingularAttribute<Template, String> name;
    public static volatile SingularAttribute<Template, String> description;
    public static volatile SingularAttribute<Template, Integer> id;
    public static volatile SingularAttribute<Template, String> hardware;

}