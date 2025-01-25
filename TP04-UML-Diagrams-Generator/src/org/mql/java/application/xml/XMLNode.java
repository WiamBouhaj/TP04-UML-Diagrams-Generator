package org.mql.java.application.xml;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.mql.java.application.enumerations.Visibility;
import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.PackageModel;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLNode {
    private Node node;

    public XMLNode(Node node) {
        this.node = node;
    }

    public XMLNode(String source) {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(source);
            node = document.getFirstChild();
            while (node.getNodeType() != Node.ELEMENT_NODE) {
                node = node.getNextSibling();
            }
        } catch (Exception e) {
            System.out.println("Erreur " + e.getMessage());
        }
    }
    public XMLNode firstChild() {
		return children().get(0);
	}
    
    public List<XMLNode> children() {
        List<XMLNode> list = new ArrayList<>();
        NodeList n1 = node.getChildNodes();
        int n = n1.getLength();
        for (int i = 0; i < n; i++) {
            Node child = n1.item(i);
            if (child.getNodeType() == Node.ELEMENT_NODE) {
                list.add(new XMLNode(child));
            }
        }
        return list;
    }

    public String getName() {
        return node.getNodeName();
    }

    public String getValue() {
        NodeList list = node.getChildNodes();
        if (list.getLength() == 1 && list.item(0).getNodeType() == Node.TEXT_NODE) {
            return list.item(0).getNodeValue();
        }
        return null;
    }

    public XMLNode child(String name) {
        List<XMLNode> children = children();
        for (XMLNode child : children) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
    }

    public String attribute(String name) {
        NamedNodeMap atts = node.getAttributes();
        return atts.getNamedItem(name) != null ? atts.getNamedItem(name).getNodeValue() : null;
    }

    public int intAttribute(String name) {
        String att = attribute(name);
        try {
            return Integer.parseInt(att);
        } catch (Exception e) {
            return -1;
        }
    }

    public PackageModel parsePackage() {
        PackageModel packageInfo = new PackageModel();
        XMLNode nameNode = child("name");
        if (nameNode != null) {
            packageInfo.setName(nameNode.getValue());
        }

        List<PackageModel> subPackages = new Vector<>();
        List<ClassModel> classes = new Vector<>();

        List<XMLNode> children = children();
        for (XMLNode childNode : children) {
            if ("package".equals(childNode.getName())) {
                subPackages.add(childNode.parsePackage());
            } else if ("class".equals(childNode.getName())) {
                ClassModel classInfo = childNode.parseClass();
                classes.add(classInfo);
            }
        }

        packageInfo.setClasses(classes); 
        return packageInfo;
    }

    public ClassModel parseClass() {
        ClassModel classInfo = new ClassModel();
        XMLNode nameNode = child("name");
        if (nameNode != null) {
            classInfo.setName(nameNode.getValue());
        }

        List<FieldModel> fields = new Vector<>();
        XMLNode fieldsNode = child("fields");
        if (fieldsNode != null) {
            List<XMLNode> fieldNodes = fieldsNode.children();
            for (XMLNode fieldNode : fieldNodes) {
                FieldModel fieldInfo = fieldNode.parseField();
                fields.add(fieldInfo);
            }
        }
        classInfo.setFields(fields);

        List<MethodModel> methods = new Vector<>();
        XMLNode methodsNode = child("methods");
        if (methodsNode != null) {
            List<XMLNode> methodNodes = methodsNode.children();
            for (XMLNode methodNode : methodNodes) {
                MethodModel methodInfo = methodNode.parseMethod();
                methods.add(methodInfo);
            }
        }
        classInfo.setMethods(methods);

        return classInfo;
    }

    private FieldModel generateField(XMLNode fieldNode) {
        String fieldName = fieldNode.child("name").getValue();
        String fieldType = fieldNode.child("type").getValue();
        Visibility fieldVisibility = getVisibilityFromXML(fieldNode);
        boolean isStatic = Boolean.parseBoolean(fieldNode.child("isStatic").getValue());
        boolean isFinal = Boolean.parseBoolean(fieldNode.child("isFinal").getValue());

        FieldModel field = new FieldModel(fieldName, fieldType, fieldVisibility, isStatic, isFinal);
        return field;
    }

    private Visibility getVisibilityFromXML(XMLNode fieldNode) {
        String visibilityValue = fieldNode.child("visibility").getValue();

        switch (visibilityValue.toLowerCase()) {
            case "public":
                return Visibility.PUBLIC;
            case "private":
                return Visibility.PRIVATE;
            case "protected":
                return Visibility.PROTECTED;
            default:
                return Visibility.DEFAULT;
        }
    }

    public MethodModel parseMethod() {
        MethodModel methodInfo = new MethodModel();

        XMLNode nameNode = child("name");
        if (nameNode != null) {
            methodInfo.setName(nameNode.getValue());
        }

        XMLNode returnTypeNode = child("returnType");
        if (returnTypeNode != null) {
            methodInfo.setReturnType(returnTypeNode.getValue());
        }
        
        XMLNode visibilityNode = child("visibility");
        if (visibilityNode != null) {
            methodInfo.setVisibility(getVisibilityFromXML(visibilityNode));
        }

        List<String> parametersList = new ArrayList<>();
        XMLNode parametersNode = child("parameters");
        if (parametersNode != null) {
            List<XMLNode> paramNodes = parametersNode.children();
            for (XMLNode paramNode : paramNodes) {
                String paramType = paramNode.getValue(); 
                parametersList.add(paramType);
            }
        }
        methodInfo.setParameters(parametersList);

        return methodInfo;
    }
    
    public FieldModel parseField() {
        FieldModel fieldInfo = new FieldModel();

        XMLNode nameNode = child("name");
        if (nameNode != null) {
            fieldInfo.setName(nameNode.getValue());
        }

        XMLNode typeNode = child("type");
        if (typeNode != null) {
            fieldInfo.setType(typeNode.getValue());
        }
        
        XMLNode visibilityNode = child("visibility");
        if (visibilityNode != null) {
            fieldInfo.setVisibility(getVisibilityFromXML(visibilityNode));
        }

        XMLNode staticNode = child("isStatic");
        if (staticNode != null) {
            fieldInfo.setStatic(Boolean.parseBoolean(staticNode.getValue()));
        }

        XMLNode finalNode = child("isFinal");
        if (finalNode != null) {
            fieldInfo.setFinal(Boolean.parseBoolean(finalNode.getValue()));
        }

        return fieldInfo;
    }

}
