package Frame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

// Classe principale qui gère la fenêtre de l'application
public class AppFrame extends Frame{
    // Panel où le dessin sera effectué
    private DrawingPanel drawingPanel;
    // Liste statique des couleurs disponibles dans l'application
    private static final List<Color> COLORS = Arrays.asList(
            Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.YELLOW, Color.ORANGE, Color.PINK
    );

    // Constructeur de la classe
    public AppFrame() {
        initializeFrame();
    }

    // Méthode d'initialisation de la fenêtre principale
    private void initializeFrame() {
        // Création de la fenêtre principale avec un titre
        JFrame frame = new JFrame("Application de dessins");
        // Configuration de la fermeture de l'application
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Création et configuration du panel de dessin
        drawingPanel = new DrawingPanel();
        drawingPanel.setBackground(Color.WHITE);

        // Création des panels d'outils et de couleurs
        JPanel toolPanel = createToolPanel();
        JPanel colorPanel = createColorPanel();

        // Organisation des panels dans la fenêtre selon le BorderLayout
        frame.add(toolPanel, BorderLayout.NORTH);      // Panel d'outils en haut
        frame.add(colorPanel, BorderLayout.SOUTH);     // Panel de couleurs en bas
        frame.add(drawingPanel, BorderLayout.CENTER);  // Zone de dessin au centre

        // Ajustement et affichage de la fenêtre
        frame.pack();
        frame.setLocationRelativeTo(null);  // Centre la fenêtre sur l'écran
        frame.setVisible(true);
    }

    // Création du panel contenant les outils de dessin
    private JPanel createToolPanel() {
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());

        // Parcours de tous les outils disponibles dans l'énumération Tools
        for (Tools tools : Tools.values()) {
            // Création d'un bouton pour chaque outil
            JButton button = createToolButton(tools.getName());
            toolPanel.add(button);

            // Gestion spéciale pour le bouton "Effacer"
            if (tools.getName().equals("Effacer")) {
                button.addActionListener(e -> drawingPanel.clearCanvas());
            } else {
                // Pour les autres outils, définition de l'outil courant
                button.addActionListener(e -> drawingPanel.setCurrentTool(tools.getName()));
            }
        }

        return toolPanel;
    }

    // Création du panel contenant les boutons de couleurs
    private JPanel createColorPanel() {
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout());

        // Création d'un bouton pour chaque couleur disponible
        for (Color color : COLORS) {
            JButton button = createColorButton(color);
            colorPanel.add(button);
            // Définition de la couleur courante lors du clic
            button.addActionListener(e -> drawingPanel.setCurrentColor(color));
        }

        return colorPanel;
    }

    // Méthode utilitaire pour créer un bouton de couleur
    private JButton createColorButton(Color color) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(30, 30));  // Taille fixe pour les boutons de couleur
        button.setBackground(color);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }

    // Méthode utilitaire pour créer un bouton d'outil
    private JButton createToolButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        // Création d'une bordure composée avec marge intérieure
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.DARK_GRAY,1),
                BorderFactory.createEmptyBorder(5,5,5,5)
        ));
        return button;
    }

    // Point d'entrée de l'application
    public static void main(String[] args) {
        // Création de l'interface graphique dans le thread EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(AppFrame::new);
    }
}