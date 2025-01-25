package org.mql.java.application.xml;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.PackageModel;
import org.mql.java.application.models.ProjectModel;
import org.mql.java.application.models.RelationModel;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.List;

public class XmlGenerator {

	public static Document createEmptyDocument() throws ParserConfigurationException {
	    DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	    Document doc = docBuilder.newDocument();

	    // Ajout d'un élément racine si le document est vide
	    Element rootElement = doc.createElement("project");  // Racine par exemple "project"
	    doc.appendChild(rootElement);

	    return doc;
	}
    // Ajoute une classe au document XML sous forme d'élément
//    public static void addClassToXML(Document doc, ClassModel classModel) {
//        Element classElement = doc.createElement("class");
//        doc.getDocumentElement().appendChild(classElement);
//        
//        classElement.setAttribute("name", classModel.getName());
//        classElement.setAttribute("visibility", classModel.getVisibility().toString());
//
//        if (classModel.getSuperClass() != null) {
//            Element superClassElement = doc.createElement("superClass");
//            superClassElement.appendChild(doc.createTextNode(classModel.getSuperClass().getName()));
//            classElement.appendChild(superClassElement);
//        }
//
//        // Ajouter les champs, méthodes, relations, etc.
//        createFieldsElement(doc, classModel, classElement);
//        createMethodsElement(doc, classModel, classElement);
//        createRelationsElement(doc, classModel, classElement);
//    }
    
	public static void addClassToXML(Document doc, ClassModel classModel) {
	    System.out.println("Ajout de la classe : " + classModel.getName());

	    if (doc.getDocumentElement() == null) {
	        System.out.println("Ajout d'un élément racine...");
	        Element rootElement = doc.createElement("project");
	        doc.appendChild(rootElement);
	    }

	    Element classElement = doc.createElement("class");
	    doc.getDocumentElement().appendChild(classElement);
	    classElement.setAttribute("name", classModel.getName());

	    System.out.println("Classe ajoutée au XML : " + classModel.getName());

	    // Ajouter des champs, méthodes, etc.
	    createFieldsElement(doc, classModel, classElement);
	    createMethodsElement(doc, classModel, classElement);
	    createRelationsElement(doc, classModel, classElement);
	}

	 // Méthode pour créer un élément de champ
    private static void createFieldsElement(Document doc, ClassModel classModel, Element classElement) {
        Element fieldsElement = doc.createElement("fields");
        classElement.appendChild(fieldsElement);

        // Ajouter les champs pour la classe
        for (FieldModel field : classModel.getFields()) {
            Element fieldElement = doc.createElement("field");
            fieldElement.setAttribute("name", field.getName());
            fieldElement.setAttribute("type", field.getType());
            fieldElement.setAttribute("visibility", field.getVisibility().toString());
            fieldElement.setAttribute("static", String.valueOf(field.isStatic()));
            fieldElement.setAttribute("final", String.valueOf(field.isFinal()));
            fieldsElement.appendChild(fieldElement);
        }
    }

    // Crée et ajoute les méthodes d'une classe dans le XML
    private static void createMethodsElement(Document doc, ClassModel classModel, Element classElement) {
        Element methodsElement = doc.createElement("methods");
        classElement.appendChild(methodsElement);
        for (MethodModel method : classModel.getMethods()) {
            Element methodElement = doc.createElement("method");
            methodElement.setAttribute("name", method.getName());
            methodElement.setAttribute("returnType", method.getReturnType());
            methodElement.setAttribute("visibility", method.getVisibility().toString());
            methodsElement.appendChild(methodElement);
        }
    }

 // Méthode pour créer un élément de relation
    private static void createRelationsElement(Document doc, ClassModel classModel, Element classElement) {
        Element relationsElement = doc.createElement("relations");
        classElement.appendChild(relationsElement);

        // Ajouter les relations pour la classe
        for (RelationModel relation : classModel.getRelations()) {
            Element relationElement = doc.createElement("relation");
            relationElement.setAttribute("type", relation.getType().toString());
            relationElement.setAttribute("source", relation.getSource().getName());
            relationElement.setAttribute("target", relation.getTarget().getName());
            relationElement.setAttribute("cardinality", relation.getCardinality().toString());
            relationsElement.appendChild(relationElement);
        }
    }
    // Sauvegarde le document XML dans un fichier
 // Exemple de sauvegarde dans un fichier
    public static void saveXMLToFile(Document doc, String filePath) throws TransformerException {
        File file = new File(filePath);
        file.getParentFile().mkdirs();  // Crée les répertoires si nécessaires

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");

        StreamResult result = new StreamResult(file);
        transformer.transform(new DOMSource(doc), result);

        System.out.println("XML file saved to: " + file.getAbsolutePath());
    }


    // Méthode pour afficher le XML à la console (pour débogage)
    public static void printXML(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(System.out);
        transformer.transform(source, result);
    }
}
