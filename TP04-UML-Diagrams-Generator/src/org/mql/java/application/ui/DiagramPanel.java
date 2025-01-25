package org.mql.java.application.ui;

import org.mql.java.application.models.ClassModel;
import org.mql.java.application.models.FieldModel;
import org.mql.java.application.models.MethodModel;
import org.mql.java.application.models.RelationModel;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

class DiagramPanel extends JPanel {
    private static final long serialVersionUID = 1L;
	private Vector<ClassModel> classes;
    private Map<String, Point> classPositions;

    public DiagramPanel(Vector<ClassModel> classes) {
        this.classes = classes;
        this.classPositions = new HashMap<>();
        setLayout(null); 
    }

    @Override
    public Dimension getPreferredSize() {
        int maxWidth = 0;
        int maxHeight = 0;
        int xOffset = 50;
        int yOffset = 50;
        int spacing = 40;

        for (ClassModel classModel : classes) {
            int boxWidth = calculateBoxWidth(classModel);
            int boxHeight = calculateBoxHeight(classModel);

            maxWidth = Math.max(maxWidth, xOffset + boxWidth);
            maxHeight = Math.max(maxHeight, yOffset + boxHeight);

            yOffset += boxHeight + spacing;
            if (yOffset + boxHeight > getHeight()) {
                yOffset = 50;
                xOffset += boxWidth + 100;
            }
        }

        return new Dimension(maxWidth + 100, maxHeight + 100);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawClasses(g);
        drawRelations(g);
    }

    private void drawClasses(Graphics g) {
        int xOffset = 50;
        int yOffset = 50;
        int spacing = 40;

        for (ClassModel classModel : classes) {
            int boxWidth = calculateBoxWidth(classModel);
            int boxHeight = calculateBoxHeight(classModel);

            g.setColor(new Color(173, 216, 230)); 
            g.fillRoundRect(xOffset, yOffset, boxWidth, boxHeight, 15, 15);
            g.setColor(Color.BLUE);
            g.drawRoundRect(xOffset, yOffset, boxWidth, boxHeight, 15, 15);

            g.setColor(Color.BLACK);
            g.drawString(classModel.getName(), xOffset + 10, yOffset + 20);

            g.drawLine(xOffset, yOffset + 25, xOffset + boxWidth, yOffset + 25);

            int fieldY = yOffset + 40;
            for (FieldModel field : classModel.getFields()) {
                g.drawString("+ " + field.getType() + ": " + field.getName(), xOffset + 10, fieldY);
                fieldY += 15;
            }

            if (!classModel.getFields().isEmpty()) {
                g.drawLine(xOffset, fieldY - 5, xOffset + boxWidth, fieldY - 5);
            }

            int methodY = fieldY + (classModel.getFields().isEmpty() ? 0 : 15);
            for (MethodModel method : classModel.getMethods()) {
                g.drawString("+ " + method.getName() + "()", xOffset + 10, methodY);
                methodY += 15;
            }

            classPositions.put(classModel.getName(), new Point(xOffset + (boxWidth / 2), yOffset + (boxHeight / 2)));

            yOffset += boxHeight + spacing;
            if (yOffset + boxHeight > getHeight()) {
                yOffset = 50;
                xOffset += boxWidth + 100;
            }
        }
    }


    private void drawRelations(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2)); 

        for (ClassModel classModel : classes) {
            for (RelationModel relation : classModel.getRelations()) {
                ClassModel targetClass = relation.getTarget();
                Point sourcePosition = classPositions.get(classModel.getName());
                Point targetPosition = classPositions.get(targetClass.getName());

                if (sourcePosition != null && targetPosition != null && sourcePosition != targetPosition) {
                    Rectangle sourceRect = getClassRectangle(sourcePosition, classModel);
                    Rectangle targetRect = getClassRectangle(targetPosition, targetClass);

                    Point sourceEdgePoint = findClosestEdgePoint(sourceRect, targetPosition);
                    Point targetEdgePoint = findClosestEdgePoint(targetRect, sourcePosition);

                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(sourceEdgePoint.x, sourceEdgePoint.y, targetEdgePoint.x, targetEdgePoint.y);

                    double angle = Math.atan2(targetEdgePoint.y - sourceEdgePoint.y, targetEdgePoint.x - sourceEdgePoint.x);
                    int arrowSize = 10;

                    switch (relation.getType()) {
                        case INHERITANCE:
                            drawTriangle(g2d, targetEdgePoint.x, targetEdgePoint.y, angle, arrowSize, false);
                            break;
                        case AGGREGATION:
                            drawDiamond(g2d, targetEdgePoint.x, targetEdgePoint.y, angle, arrowSize, false);
                            break;
                        case COMPOSITION:
                            drawDiamond(g2d, targetEdgePoint.x, targetEdgePoint.y, angle, arrowSize, true);
                            break;
                        case ASSOCIATION:
                            // No additional marker
                            break;
                        case DEPENDENCY:
                            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{5}, 0));
                            g2d.drawLine(sourceEdgePoint.x, sourceEdgePoint.y, targetEdgePoint.x, targetEdgePoint.y);
                            drawArrow(g2d, targetEdgePoint.x, targetEdgePoint.y, angle, arrowSize, false);
                            g2d.setStroke(new BasicStroke(2)); // Reset stroke
                            break;
                    }
                }
            }
        }
    }

    private Rectangle getClassRectangle(Point position, ClassModel classModel) {
        int width = 150; 
        int height = 100; 
        return new Rectangle(position.x - width / 2, position.y - height / 2, width, height);
    }

    private Point findClosestEdgePoint(Rectangle rect, Point externalPoint) {
        int x1 = rect.x; 
        int x2 = rect.x + rect.width; 
        int y1 = rect.y; 
        int y2 = rect.y + rect.height;

        Point[] edgePoints = new Point[]{
            new Point(externalPoint.x, Math.min(Math.max(externalPoint.y, y1), y2)),
            new Point(Math.min(Math.max(externalPoint.x, x1), x2), externalPoint.y), 
        };

        Point closest = edgePoints[0];
        double minDistance = externalPoint.distance(closest);

        for (Point p : edgePoints) {
            double distance = externalPoint.distance(p);
            if (distance < minDistance) {
                minDistance = distance;
                closest = p;
            }
        }

        return closest;
    }


    private void drawTriangle(Graphics2D g2d, int x, int y, double angle, int size, boolean filled) {
        int[] xPoints = {
            x,
            (int) (x - size * Math.cos(angle - Math.PI / 6)),
            (int) (x - size * Math.cos(angle + Math.PI / 6))
        };
        int[] yPoints = {
            y,
            (int) (y - size * Math.sin(angle - Math.PI / 6)),
            (int) (y - size * Math.sin(angle + Math.PI / 6))
        };

        if (filled) {
            g2d.fillPolygon(xPoints, yPoints, 3);
        } else {
            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }

    // (aggregation and composition)
    private void drawDiamond(Graphics2D g2d, int x, int y, double angle, int size, boolean filled) {
        int[] xPoints = {
            x,
            (int) (x - size * Math.cos(angle - Math.PI / 4)),
            (int) (x - 2 * size * Math.cos(angle)),
            (int) (x - size * Math.cos(angle + Math.PI / 4))
        };
        int[] yPoints = {
            y,
            (int) (y - size * Math.sin(angle - Math.PI / 4)),
            (int) (y - 2 * size * Math.sin(angle)),
            (int) (y - size * Math.sin(angle + Math.PI / 4))
        };

        if (filled) {
            g2d.fillPolygon(xPoints, yPoints, 4);
        } else {
            g2d.drawPolygon(xPoints, yPoints, 4);
        }
    }

    private void drawArrow(Graphics2D g2d, int x, int y, double angle, int size, boolean filled) {
        int[] xPoints = {
            x,
            (int) (x - size * Math.cos(angle - Math.PI / 6)),
            (int) (x - size * Math.cos(angle + Math.PI / 6))
        };
        int[] yPoints = {
            y,
            (int) (y - size * Math.sin(angle - Math.PI / 6)),
            (int) (y - size * Math.sin(angle + Math.PI / 6))
        };

        if (filled) {
            g2d.fillPolygon(xPoints, yPoints, 3);
        } else {
            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }


    private int calculateBoxWidth(ClassModel classModel) {
        int width = 150;
        int additionalWidth = Math.max(classModel.getFields().size(), classModel.getMethods().size()) * 70;
        return width;
    }

    private int calculateBoxHeight(ClassModel classModel) {
        int height = 50;
        int additionalHeight = (classModel.getFields().size() + classModel.getMethods().size()) * 20;
        return height + additionalHeight;
    }
}

