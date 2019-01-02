package entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-01-02T15:24:47")
@StaticMetamodel(Artifact.class)
public class Artifact_ { 

    public static volatile SingularAttribute<Artifact, String> sourceCode;
    public static volatile SingularAttribute<Artifact, String> database;
    public static volatile SingularAttribute<Artifact, String> libraries;
    public static volatile SingularAttribute<Artifact, Integer> id;
    public static volatile SingularAttribute<Artifact, String> scripts;

}