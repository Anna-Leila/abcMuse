package main_package;


import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;


public class PlayingField {
    int score = 0; // score of the player
    private static final Color markedButton = new Color(73, 212, 108); // color to mark used letters
    private static final Color fieldBackground = new Color(252, 229, 189); // color of the playing field
    HashSet<String> wordsSet; // set of existing words - our dictionary
    JTextArea output; // jTextArea for printing out the results
    String recognised = ""; // all recognised words in one string

    ArrayList<JButton> letterButtons = new ArrayList<>(); // list of button with available letters
    ArrayList<Character> vowels = new ArrayList<>(
            Arrays.asList('a', 'e', 'i', 'o', 'u')
    );
    ArrayList<Character> consonants = new ArrayList<>( // list of available consonants
            Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'q',
                    'r', 's', 't', 'v', 'w', 'x', 'y', 'z'));

    PlayingField(int consonants) { // constructor for playing field
        getWords();
        showField(consonants);
    }

    public void showField(int letters) { // show playing field
        JPanel mainPanel = new JPanel();
        //mainPanel.setLayout(new GridLayout(4, 1));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel letterPanel = new JPanel();
        letterPanel.setLayout(new BoxLayout(letterPanel, BoxLayout.Y_AXIS));

        JPanel vowelPanel = new JPanel();
        vowelPanel.setLayout(new FlowLayout());
        vowelPanel.setPreferredSize(new Dimension(100, 50));
        for (Character vowel:vowels) {
            JButton letterButton = new JButton(String.valueOf(vowel));
            letterButton.setPreferredSize(new Dimension(45, 45));
            letterButton.setBorder(null);
            letterButton.setBackground(new Color(204, 16, 246));
            letterButton.setForeground(new Color(252, 252, 252));
            letterButton.setFont(new Font("Arial", Font.BOLD, 25));
            vowelPanel.add(letterButton);
            letterButtons.add(letterButton);
        }

        JPanel consonantPanel = new JPanel();
        consonantPanel.setLayout(new FlowLayout());
        consonantPanel.setPreferredSize(new Dimension(100, 100));
        Collections.shuffle(consonants);
        ArrayList<Character> randomLetters = new ArrayList<>();
        for (int i=0; i<letters; i++) {
            randomLetters.add(consonants.get(i));
        }
        Collections.sort(randomLetters);
        for (int i=0; i<letters; i++) {
            JButton letterButton = new JButton(String.valueOf(randomLetters.get(i)));
            letterButton.setPreferredSize(new Dimension(45, 45));
            letterButton.setBorder(null);
            letterButton.setFont(new Font("Arial", Font.BOLD, 25));
            letterButton.setBackground(new Color(204, 16, 246));
            letterButton.setForeground(new Color(252, 252, 252));
            //letterButton.addActionListener());
            consonantPanel.add(letterButton);
            letterButtons.add(letterButton);
        }
        //consonantPanel.setBorder(new EmptyBorder(0, 0, 10, 0));

        letterPanel.setBorder(new EmptyBorder(10, 2, 0, 2));
        letterPanel.add(vowelPanel);
        letterPanel.add(consonantPanel);

        JPanel textPanel = new JPanel();
        //textPanel.setLayout(new GridLayout(2, 1));
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        JLabel textLabel = new JLabel("Type words here (separate by a space):"); // label above output
        textLabel.setFont(new Font("Monotype Corsiva", Font.BOLD, 25));
        textLabel.setBorder(new EmptyBorder(10, 0, 5, 5));
        textLabel.setAlignmentX((float) 0.5);
        textLabel.setForeground(new Color(10, 222, 63));

        JTextField userText = new JTextField(30);
        userText.setFont(new Font("Verdana", Font.PLAIN, 22));
        userText.setBorder(new EmptyBorder(0, 0, 0, 0));
        userText.addActionListener(new textActionListener());

        textPanel.add(textLabel);
        textPanel.add(userText);
        textPanel.setBorder(new EmptyBorder(10, 10, 10, 10));


        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BoxLayout(outputPanel, BoxLayout.Y_AXIS));
        //outputPanel.setLayout(new GridLayout(2, 1));

        JLabel outputLabel = new JLabel("Your results are shown here:"); // label above output
        outputLabel.setFont(new Font("Monotype Corsiva", Font.BOLD, 25));
        outputLabel.setBorder(new EmptyBorder(10, 10, 5, 10));
        outputLabel.setAlignmentX((float) 0.5);
        outputLabel.setForeground(new Color(10, 222, 63));

        output = new JTextArea("", 10, 10); // output for results
        output.setLineWrap(false);
        output.setBorder(new EmptyBorder(5, 0, 5, 10));
        output.setLineWrap(true);
        output.setEditable(false);
        output.setFont(new Font("Verdana", Font.PLAIN, 18));
        JScrollPane resultsScroll = new JScrollPane(output);
        resultsScroll.setPreferredSize(new Dimension(50, 100));
        resultsScroll.setBorder(new EmptyBorder(0, 10, 0, 10));

        outputPanel.add(outputLabel);
        outputPanel.add(resultsScroll);


        mainPanel.add(letterPanel);
        mainPanel.add(textPanel);
        mainPanel.add(outputPanel);


        for (Component c:mainPanel.getComponents()) {
            c.setBackground(fieldBackground);
        }
        vowelPanel.setBackground(fieldBackground);
        consonantPanel.setBackground(fieldBackground);

        resultsScroll.setBackground(fieldBackground);

        JOptionPane.showMessageDialog(null, mainPanel, "abcMuse", JOptionPane.PLAIN_MESSAGE);
    }

    public void getWords() { // get words out of the file and put them into wordSet
        Path path = Paths.get("popular_words.txt");
        byte[] readBytes = new byte[0];
        try {
            readBytes = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String wordListContents = new String(readBytes, StandardCharsets.UTF_8);
        String[] words = wordListContents.split("\n");
        wordsSet = new HashSet<>();
        for (String word:words) {
            wordsSet.add(word.substring(0, word.length()-1));
        }
        //System.out.println(wordsSet.contains("hello"));
    }

    public boolean contains(String word) { // check if wordSet contains String word
        return wordsSet.contains(word);
    }

    public void checkLetter(String letter) { // check if needed letter is already used, change score accordingly
        boolean found = false;
        for (JButton button:letterButtons) {
            if (button.getText().equals(letter) && !button.getBackground().equals(markedButton)) {
                button.setBackground(markedButton);
                score++;
                found = true;
                break;
            }
        }
        if (!found) {
            score--;
        }
    }

    class textActionListener implements ActionListener { // actionListener class for JTextField with player's words

        @Override
        public void actionPerformed(ActionEvent e) { // if user presses "Enter", then split given String into words
            JTextField field = (JTextField) e.getSource();
            String text = field.getText();
            String[] words = text.split(" ");
            if (recognised.equals("-")) recognised = "";
            for (String word:words) {
                // check if such word exists
                if (contains(word)) { // if it does exist,
                    for (Character c:word.toCharArray()) { // then check every letter in it
                        checkLetter(String.valueOf(c));
                    }
                    recognised+=", "+word; // add the word to recognised words
                }
            }
            field.setText(""); // remove text that the used typed in
            if (recognised.equals("")) recognised = "-";
            else if (recognised.charAt(0)==',') recognised = recognised.substring(2);

            // print out the results
            output.setText("");
            output.append("Your current score: "+score+"\n");
            output.append("Recognised words: "+recognised+"\n");
        }
    }
}
