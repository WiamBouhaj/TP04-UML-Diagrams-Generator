package org.mql.java.application.ui;import javax.swing.*;  
import java.awt.*;  
import java.util.Vector;  
import org.mql.java.application.models.ClassModel;  
import org.mql.java.application.models.FieldModel;  
import org.mql.java.application.models.MethodModel;     

class DiagramPanel extends JPanel {  
    private Vector<ClassModel> classes;  

    public DiagramPanel(Vector<ClassModel> classes) {  
        this.classes = classes;  
        setPreferredSize(new Dimension(800, 600));  
    }  

    @Override  
    protected void paintComponent(Graphics g) {  
        super.paintComponent(g);  
        drawClasses(g);  
    }  

    private void drawClasses(Graphics g) {  
        int xOffset = 50;  
        int yOffset = 50;  
        int spacing = 40; // Espacement vertical entre les classes  

        for (ClassModel classModel : classes) {  
            // Calculer la taille des rectangles en fonction du contenu  
            int boxWidth = calculateBoxWidth(classModel);  
            int boxHeight = calculateBoxHeight(classModel);  

            // Définir la couleur de remplissage et de contour  
            g.setColor(new Color(173, 216, 230)); // Bleu clair  
            g.fillRoundRect(xOffset, yOffset, boxWidth, boxHeight, 15, 15); // Coins arrondis  
            g.setColor(Color.BLUE); // Couleur du contour  
            g.drawRoundRect(xOffset, yOffset, boxWidth, boxHeight, 15, 15);  

            // Définir la couleur noire pour le text  
            g.setColor(Color.BLACK);  
            g.drawString(classModel.getName(), xOffset + 10, yOffset + 20);  

            // Affichage des champs  
            int fieldY = yOffset + 40;  
            for (FieldModel field : classModel.getFields()) {  
                g.drawString("+ "  + field.getType() + ": " + field.getName() , xOffset + 10, fieldY);  
                fieldY += 15; // Espacement entre les lignes  
            }  

            // Affichage des méthodes  
            int methodY = fieldY;  
            for (MethodModel method : classModel.getMethods()) {  
                g.drawString("+ " + method.getName() + "()", xOffset + 10, methodY);  
                methodY += 15;  
            }  

            // Mise à jour de la position pour la prochaine classe  
            yOffset += boxHeight + spacing;  
            if (yOffset + boxHeight > getHeight()) {  
                yOffset = 50; // Réinitialisation si on atteint le bas  
                xOffset += boxWidth + 100; // Se déplacer à droite pour la prochaine colonne  
            }  
        }  
    }  

    private int calculateBoxWidth(ClassModel classModel) {  
        int width = 150; // Largeur de base  
        int additionalWidth = Math.max(classModel.getFields().size(), classModel.getMethods().size()) * 70;   
        return width;  // On peut ajuster pour plus de largeur si nécessaire  
    }  

    private int calculateBoxHeight(ClassModel classModel) {  
        int height = 50; // Hauteur de base  
        int additionalHeight = (classModel.getFields().size() + classModel.getMethods().size()) * 20;   
        return height + additionalHeight;  // Ajustement basé sur le contenu  
    }  
}