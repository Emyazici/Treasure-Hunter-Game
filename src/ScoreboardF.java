package YaziciVarol;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardF extends JPanel {

    private Image backgroundImage;
    BinarySearchTree bst;

    public ScoreboardF() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/images/ScoreboardBack.png"));
        backgroundImage = icon.getImage();
        bst = new BinarySearchTree();
        // Layout
        setLayout(null);
        JLabel titleLabel = new JLabel("SCOREBOARD");
        titleLabel.setFont(new Font("DialogInput", Font.BOLD, 38));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(245, 115, 400, 60); 
        add(titleLabel);

        
        setOpaque(false);

        add(Box.createVerticalStrut(50)); 

        add(Box.createVerticalStrut(30)); 

        JPanel formPanel = new JPanel();
        formPanel.setLayout(null); 
        formPanel.setOpaque(false); 

        // Label 1
        JLabel lblUser = new JLabel("Username: ");
        lblUser.setBounds(100, 40, 300, 30); 
        formPanel.add(lblUser);
        lblUser.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Label 2
        JLabel lblBest = new JLabel("Best Score: ");
        lblBest.setBounds(100, 80, 300, 30);
        formPanel.add(lblBest);
        lblBest.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // Label 3
        JLabel lblWorst = new JLabel("Worst Score: ");
        lblWorst.setBounds(100, 120, 300, 30);
        formPanel.add(lblWorst);
        lblWorst.setFont(new Font("Segoe UI", Font.BOLD, 15));

        // TextArea 4
        String allScores = "";
        JTextArea txtAllScores = new JTextArea("All Scores:\n" + allScores);
        txtAllScores.setBounds(100, 160, 575, 100);
        txtAllScores.setFont(new Font("Segoe UI", Font.BOLD, 15));
        txtAllScores.setEditable(false);
        txtAllScores.setFocusable(false);
        txtAllScores.setLineWrap(true);
        txtAllScores.setWrapStyleWord(true);
        txtAllScores.setOpaque(false);
        txtAllScores.setBackground(new Color(0, 0, 0, 0));  
        txtAllScores.setBorder(null);

        formPanel.add(txtAllScores);

        add(formPanel);
        formPanel.setBounds(0, 125, 800, 400);

        // MenuBtn
        JButton btnMenu = new JButton("Menu");
        btnMenu.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnMenu.setBounds(500, 390, 100, 30); 
        btnMenu.setFocusPainted(true);
        btnMenu.setContentAreaFilled(true);   
        btnMenu.setBorderPainted(true);       
        btnMenu.setForeground(Color.BLACK);    
        this.add(btnMenu);

        btnMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(ScoreboardF.this, "Returning to the main menu...");
                new MenuF().setVisible(true);
                SwingUtilities.getWindowAncestor(ScoreboardF.this).dispose();
            }
        });

        String usernameFilter = JOptionPane.showInputDialog(this, "Username:");
        try (BufferedReader br = new BufferedReader(new FileReader("score.txt"))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String username = parts[0].trim();
                    String level = parts[1].trim();
                    int score = Integer.parseInt(parts[2].trim());

                    if (username.equals(usernameFilter)) {
                        bst.insert(score, username, level);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        allScores = bst.inorder();

        lblUser.setText("Username: " + usernameFilter);
        lblBest.setText("Best Score: " + bst.max());
        lblWorst.setText("Worst Score: " + bst.min());
        txtAllScores.setText("All Scores: " + allScores);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
    }

}
