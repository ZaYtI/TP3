package Frame;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

public class AppFrame {
    private DrawingPanel drawingPanel;
    private static final List<Color> COLORS = Arrays.asList(
            Color.RED, Color.GREEN, Color.BLUE, Color.BLACK, Color.YELLOW, Color.ORANGE, Color.PINK
    );

    public AppFrame() {
        initializeGUI();
    }

    private void initializeGUI() {
        JFrame frame = new JFrame("Application de dessins");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialisation du panel de dessin
        drawingPanel = new DrawingPanel();
        drawingPanel.setBackground(Color.WHITE);

        // Création des panels
        JPanel toolPanel = createToolPanel();
        JPanel colorPanel = createColorPanel();

        // Ajout des panels au frame
        frame.add(toolPanel, BorderLayout.NORTH);
        frame.add(colorPanel, BorderLayout.SOUTH);
        frame.add(drawingPanel, BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createToolPanel() {
        JPanel toolPanel = new JPanel();
        toolPanel.setLayout(new FlowLayout());

        for (Tools tools : Tools.values()) {
            JButton button = createToolButton(tools.getName());
            toolPanel.add(button);

            if (tools.getName().equals("Effacer")) {
                button.addActionListener(e -> drawingPanel.clearCanvas());
            } else {
                button.addActionListener(e -> drawingPanel.setCurrentTool(tools.getName()));
            }
        }

        return toolPanel;
    }

    private JPanel createColorPanel() {
        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new FlowLayout());

        for (Color color : COLORS) {
            JButton button = createColorButton(color);
            colorPanel.add(button);
            button.addActionListener(e -> drawingPanel.setCurrentColor(color));
        }

        return colorPanel;
    }

    private JButton createColorButton(Color color) {
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(30, 30));
        button.setBackground(color);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
        return button;
    }

    private JButton createToolButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setOpaque(true);
        button.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,1),BorderFactory.createEmptyBorder(5,5,5,5)));
        return button;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AppFrame::new);
    }
}