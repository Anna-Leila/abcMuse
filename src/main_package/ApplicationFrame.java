package main_package;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.FontUIResource;
import java.awt.*;

public class ApplicationFrame {
    // rules of playing
    private static final String RULES = "The program will randomly show several different consonants. You have to think of as many\n" +
            "words with different available letters as you can. Each available letter used for the\n" +
            "first time will give You + 1 to the score. Afterwards it’ll give You -1 to the score.\n" +
            "Try to get highest score possible. Good luck!";
    private static final Color background = new Color(167, 245, 219); // color of JFrame and it's components (except buttons)
    private static final Color buttonColor = new Color(253, 236, 122, 255); // color of buttons
    //private static final Color rulesBackground = new Color(204, 212, 206); // color of rules JOptionPane
    private static final Color rulesBackground = new Color(231, 239, 235); // color of rules JOptionPane
    private static final Color playingBackground = new Color(252, 229, 189); // color of playing field JOptionPane

    ApplicationFrame() {
        JFrame frame = new JFrame("abcMuse 1.0"); // our main frame
        frame.setBounds(10, 20, 500, 280);
        frame.setResizable(false); // user can't resize the frame
        //frame.setMinimumSize(new Dimension(500, 350));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        JLabel greetingLabel = new JLabel("<html><body style='text-align: center'>Mini-game abcMuse" + // the label with the heading
                "<br>for several players, demo version 1.0");
        greetingLabel.setHorizontalAlignment(JLabel.CENTER);
        greetingLabel.setBorder(BorderFactory.createEmptyBorder(2, 20, 0, 20));
        greetingLabel.setFont(new Font("Monotype Corsiva", Font.ITALIC, 35));
        greetingLabel.setForeground(new Color(10, 63, 222));
        greetingLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel consonantText = new JLabel("Choose the number of consonants:"); // text for the amount of consonants
        consonantText.setFont(new Font("Monotype Corsiva", Font.BOLD, 25));
        consonantText.setBorder(new EmptyBorder(0, 3, 0, 5));
        consonantText.setForeground(new Color(10, 63, 222));

        JSlider consonantSlider = new JSlider(1, 21, 10); // slider for choosing the number of consonants
        consonantSlider.setMajorTickSpacing(1);
        consonantSlider.setMinorTickSpacing(1);
        consonantSlider.setPaintLabels(true);
        consonantSlider.setSnapToTicks(true);
        consonantSlider.setBorder(new EmptyBorder(0, 20, 0, 20));

        JPanel consonantPanel = new JPanel(); // panel with text and slider for the number of consonants
        consonantPanel.setLayout(new GridLayout(2, 1));
        //playersPanel.setSize(new Dimension(200, 300));
        consonantPanel.add(consonantText);
        consonantPanel.add(consonantSlider);


        JButton rulesButton = new JButton("Rules"); // button for showing the rules of the game
        rulesButton.setFont(new Font("Arial", Font.PLAIN, 20));

        UIManager.put("OptionPane.okButtonText", "OK");
        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Arial", Font.PLAIN,18)));

        rulesButton.addActionListener(e -> {
            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Arial", Font.PLAIN, 16)));
            UIManager.put("OptionPane.messageForeground", new Color(0, 0, 0));

            UIManager.put("OptionPane.background", rulesBackground);
            UIManager.put("Panel.background", rulesBackground);

            frame.setVisible(false);
            JOptionPane.showMessageDialog(frame, RULES, "Rules of the game", JOptionPane.PLAIN_MESSAGE);
            frame.setVisible(true);
        });

        JButton playButton = new JButton("Let's win!"); // button for playing (showing the playing field, etc.)
        playButton.setFont(new Font("Arial", Font.PLAIN, 20));
        //playButton.setPreferredSize(new Dimension(200, 50));
        playButton.addActionListener(e -> {
            int consonants = consonantSlider.getValue();

            UIManager.put("OptionPane.background", playingBackground);
            UIManager.put("Panel.background", playingBackground);

            frame.setVisible(false);
            new PlayingField(consonants);
            frame.setVisible(true);
        });

        JPanel mainPanel = new JPanel(); // panel with the buttons on it

        GridLayout mainPanelLayout = new GridLayout(1, 2);
        mainPanelLayout.setVgap(20);
        mainPanelLayout.setHgap(40);
        mainPanel.setLayout(mainPanelLayout);
        mainPanel.setBorder(new EmptyBorder(10, 30, 10, 30));

        mainPanel.add(rulesButton);
        mainPanel.add(playButton);

        Container container = frame.getContentPane(); // container with all the components
        container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
        container.add(greetingLabel);
        container.add(mainPanel);
        container.add(consonantPanel);

        paintComponent(frame); // paint all components
        rulesButton.setBackground(buttonColor); // change rules button color
        playButton.setBackground(buttonColor); // change play button color


        //frame.setIconImage(new ImageIcon(getClass().getResource("/Avatar_Betmidl4.jpg")).getImage()); // add icon
        //frame.pack();
        frame.setVisible(true); // show frame
    }

    public void paintComponent(Component component) { // function for painting background of all components into "background" color
        component.setBackground(background);
        if (component instanceof Container) { // if component is a container
            Container container = (Container) component; // turn it into container
            for (Component c:container.getComponents()) { // run over all inner components
                paintComponent(c); // and paint them
            }
        }
    }

    public static void main(String[] args) {
        new ApplicationFrame();
    }
}
