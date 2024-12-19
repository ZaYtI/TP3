package Frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Panneau de dessin principal qui permet aux utilisateurs de dessiner différentes formes.
 * Hérite de JPanel pour l'affichage graphique.
 */
public class DrawingPanel extends JPanel {
    // Image tampon pour stocker le dessin
    private BufferedImage canvas;

    // Liste pour stocker toutes les formes dessinées
    private final ArrayList<Shape> shapes = new ArrayList<>();

    // Forme actuellement en cours de dessin
    private Shape currentShape;

    // Couleur actuellement sélectionnée (noir par défaut)
    private Color currentColor = Color.BLACK;

    // Outil actuellement sélectionné (crayon par défaut)
    private Tools currentTool = Tools.PENCIL;

    // Points de début d'une forme
    private Point startPoint;

    // Point de fin d'une forme
    private Point endPoint;

    /**
     * Constructeur qui initialise le panneau de dessin avec ses dimensions
     * et configure les listeners de souris.
     */
    public DrawingPanel() {
        setPreferredSize(new Dimension(800, 600));
        initializeCanvas();
        setupMouseListeners();
    }

    /**
     * Initialise le canvas avec une image vide de fond blanc.
     */
    private void initializeCanvas() {
        canvas = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 800, 600);
        g2d.dispose();
    }

    /**
     * Configure les écouteurs d'événements de la souris pour gérer le dessin.
     */
    private void setupMouseListeners() {
        // Gestion des clics de souris
        addMouseListener(new MouseAdapter() {
            // Quand on appuie sur la souris, on enregistre le point de départ
            @Override
            public void mousePressed(MouseEvent e) {
                startPoint = e.getPoint();
                endPoint = startPoint;
            }

            // Quand on relâche la souris, on finalise la forme si elle existe
            @Override
            public void mouseReleased(MouseEvent e) {
                if (currentShape != null) {
                    // Ajout de la forme a la liste des formes
                    shapes.add(currentShape);
                    // Dessine le forme sur le panel
                    drawShapes();
                    // Remet la forme courante a null pour pouvoir libérer le champ
                    currentShape = null;
                }
            }
        });

        // Gestion du déplacement de la souris
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                endPoint = e.getPoint();
                // Mode crayon : dessine directement sur le canvas
                // Pour le mode crayon on dessine directement sur le canvas
                // A chaque mouvement de souris on créer une mini ligne entre un point a et b
                if (currentTool == Tools.PENCIL) {
                    Graphics2D g2d = canvas.createGraphics();
                    g2d.setColor(currentColor);
                    g2d.drawLine(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
                    g2d.dispose();
                    startPoint = endPoint;
                } else {
                    // Autres outils : crée une forme temporaire
                    currentShape = createShape();
                }
                repaint();
            }
        });
    }

    /**
     * Crée une forme en fonction de l'outil sélectionné et des points de début et fin.
     * @return La forme créée ou null pour les outils spéciaux (crayon, effacer)
     */
    private Shape createShape() {
        // Calcule les dimensions de la forme
        int x = Math.min(startPoint.x, endPoint.x);
        int y = Math.min(startPoint.y, endPoint.y);
        int width = Math.abs(startPoint.x - endPoint.x);
        int height = Math.abs(startPoint.y - endPoint.y);

        // Crée la forme appropriée selon l'outil
        return switch (currentTool) {
            case PENCIL, CLEAR -> null; // Ces outils ont leur propre gestion
            case LINE -> new Line2D.Float(startPoint.x, startPoint.y, endPoint.x, endPoint.y);
            case RECTANGLE -> new Rectangle(x, y, width, height);
            case CIRCLE -> new Ellipse2D.Float(x, y, width, height);
        };
    }

    /**
     * Dessine la forme courante sur le canvas.
     */
    private void drawShapes() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(currentColor);
        if (currentShape != null) {
            g2d.draw(currentShape);
        }
        g2d.dispose();
        repaint();
    }

    /**
     * Surcharge de paintComponent pour gérer l'affichage du dessin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Dessine le canvas
        g.drawImage(canvas, 0, 0, null);
        // Dessine la forme en cours si elle existe
        if (currentShape != null) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(currentColor);
            g2d.draw(currentShape);
        }
    }

    /**
     * Définit la couleur courante pour le dessin.
     */
    public void setCurrentColor(Color color) {
        this.currentColor = color;
    }

    /**
     * Définit l'outil courant à partir de son nom.
     */
    public void setCurrentTool(String toolName) {
        for (Tools tool : Tools.values()) {
            if (tool.getName().equals(toolName)) {
                this.currentTool = tool;
                break;
            }
        }
    }

    /**
     * Efface tout le contenu du canvas.
     */
    public void clearCanvas() {
        Graphics2D g2d = canvas.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.dispose();
        shapes.clear();
        repaint();
    }
}