package org.mql.java.application.xml;

import org.mql.java.application.enumerations.Cardinality;
import org.mql.java.application.enumerations.RelationType;
import org.mql.java.application.enumerations.Visibility;
import org.mql.java.application.models.AnnotationModel;
import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.models.RelationModel;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;
import java.util.*;

public class XMLParser {
    private XMLNode xmlNode;

    public XMLParser(String path) {
        this.xmlNode = new XMLNode(path); // Charger le fichier XML avec XMLNode
    }

    public PackageModel parse() {
        // Récupérer le nœud principal du package
        PackageModel myPackage = new PackageModel("");        
        XMLNode mainPackage = xmlNode.firstChild(); // Récupère le premier enfant (package principal)
        myPackage = parsePackageRecursively(mainPackage);
        
        return myPackage;
    }

    private PackageModel parsePackageRecursively(XMLNode node) {
        PackageModel myPackage = new PackageModel(node.attribute("name"));
        
        // Initialiser les listes pour contenir les classes, annotations, etc.
        List<ClassModel> packageClasses = new ArrayList<>();
        List<AnnotationModel> packageAnnotations = new ArrayList<>();
        List<RelationModel> relations = new ArrayList<>();
        
        // Récupérer les sous-nœuds enfants du package (classes, annotations, etc.)
        List<XMLNode> children = node.children() != null ? node.children() : new ArrayList<>();

        for (XMLNode child : children) {
            if (child.getName().equals("class")) {
                ClassModel classModel = parseClass(child);
                packageClasses.add(classModel);
            } else if (child.getName().equals("annotation")) {
                AnnotationModel annotationModel = parseAnnotation(child);
                packageAnnotations.add(annotationModel);
            } else if (child.getName().equals("relation")) {
                RelationModel relationModel = parseRelation(child);
                relations.add(relationModel);
            }
        }
        
        myPackage.setClasses(packageClasses);
//        myPackage.setRelations(relations);
//        myPackage.setAnnotations(packageAnnotations);
        
        return myPackage;
    }

    private ClassModel parseClass(XMLNode node) {
        ClassModel classModel = new ClassModel(node.attribute("name"), node.attribute("package"));
        
        // Parsing les champs de la classe
        List<FieldModel> fields = new ArrayList<>();
        XMLNode fieldsNode = node.child("fields");
        List<XMLNode> fieldNodes = fieldsNode != null ? fieldsNode.children() : new ArrayList<>();
        for (XMLNode fieldNode : fieldNodes) {
            String fieldName = fieldNode.child("name").getValue();
            String fieldType = fieldNode.child("type").getValue();
            Visibility visibility = Visibility.valueOf(fieldNode.child("visibility").getValue().toUpperCase());
            boolean isStatic = Boolean.parseBoolean(fieldNode.child("isStatic").getValue());
            boolean isFinal = Boolean.parseBoolean(fieldNode.child("isFinal").getValue());
            FieldModel fieldModel = new FieldModel(fieldName, fieldType, visibility, isStatic, isFinal);
            fields.add(fieldModel);
        }
        classModel.setFields(fields);

        // Parsing les méthodes de la classe
        List<MethodModel> methods = new ArrayList<>();
        XMLNode methodsNode = node.child("methods");
        List<XMLNode> methodNodes = methodsNode != null ? methodsNode.children() : new ArrayList<>();
        for (XMLNode methodNode : methodNodes) {
            String methodName = methodNode.child("name").getValue();
            String returnType = methodNode.child("returnType").getValue();
            Visibility visibility = Visibility.valueOf(methodNode.child("visibility").getValue().toUpperCase());
            List<String> parameters = new ArrayList<>();
            XMLNode paramsNode = methodNode.child("parameters");
            List<XMLNode> paramNodes = paramsNode != null ? paramsNode.children() : new ArrayList<>();
            for (XMLNode paramNode : paramNodes) {
                parameters.add(paramNode.getValue());
            }
            MethodModel methodModel = new MethodModel(methodName, returnType, visibility, parameters);
            methods.add(methodModel);
        }
        classModel.setMethods(methods);
        
        // Parsing des relations de la classe (par exemple, héritage, composition)
        List<RelationModel> relations = new ArrayList<>();
        XMLNode relationsNode = node.child("relations");
        List<XMLNode> relationNodes = relationsNode != null ? relationsNode.children() : new ArrayList<>();
        for (XMLNode relationNode : relationNodes) {
            RelationModel relationModel = parseRelation(relationNode);
            relations.add(relationModel);
        }
        classModel.setRelations(relations);

        return classModel;
    }

    private AnnotationModel parseAnnotation(XMLNode node) {
        String name = node.attribute("name");
        AnnotationModel annotationModel = new AnnotationModel(name);
        
        // Récupération des attributs de l'annotation
        XMLNode attributesNode = node.child("attributes");
        List<XMLNode> attributeNodes = attributesNode != null ? attributesNode.children() : new ArrayList<>();
        for (XMLNode attributeNode : attributeNodes) {
            String attributeName = attributeNode.child("name").getValue();
            String attributeType = attributeNode.child("type").getValue();
            annotationModel.getAttributes().put(attributeName, attributeType);
        }
        
        return annotationModel;
    }

    private RelationModel parseRelation(XMLNode node) {
        String type = node.attribute("type");
        String from = node.attribute("from");
        String to = node.attribute("to");
        RelationModel relationModel = new RelationModel();
        return relationModel;
    }
}
