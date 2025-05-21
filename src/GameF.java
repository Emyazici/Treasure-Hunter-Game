package YaziciVarol;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author emreyazici
 */
public class GameF extends javax.swing.JFrame {

    /**
     * Creates new form GameF
     */
    private Player player;
    private Manager manager;
    private Scores scores;
    private SLinkedList sLinkedList;
    private DLinkedList dLinkedList;
    private SNode temp;
    private SNode header;
    private int rollScore;
    private JButton[] btnsMap = new JButton[32];
    private ImageIcon iconTreasure;
    private ImageIcon iconTrap;
    private ImageIcon iconCharacter;
    private ImageIcon iconFinishFlag;
    private ImageIcon iconStartFlag;
    private ImageIcon iconForward;
    private ImageIcon iconBack;
    int backForwardCounter = 0;

    public GameF(Player player, Manager manager) {
        this.player = player;
        this.manager = manager;
        this.scores = scores = new Scores(player);

        initComponents();
        lblLevelPlayer.setText(String.valueOf(player.getLevel()));
        setResizable(false);
        if (panelMap == null) {
            System.out.println("null");
        }

        panelMap.setLayout(new GridLayout(4, 8, 12, 12));
        loadIcons();
        createButtons();
        if (player.getLevel() == 1) {
            sLinkedList = (SLinkedList) manager.createLists(1);
            temp = sLinkedList.getHeader();
        } else {
            dLinkedList = (DLinkedList) manager.createLists(2);
            temp = dLinkedList.getHeader();
        }
        loadButonIcons();
        lblUsernamePlayer.setText(player.getUsername());
        lblScorePlayer.setText(String.valueOf(player.getScore()));
    }

    private void loadIcons() {
        this.iconTreasure = new ImageIcon(getClass().getResource("/images/treasureBtn.png"));
        this.iconTrap = new ImageIcon(getClass().getResource("/images/dang1.png"));
        this.iconCharacter = new ImageIcon(getClass().getResource("/images/miner.png"));
        this.iconFinishFlag = new ImageIcon(getClass().getResource("/images/finish.png"));
        this.iconStartFlag = new ImageIcon(getClass().getResource("/images/start.png"));
        this.iconForward = new ImageIcon(getClass().getResource("/images/right-arrow.png"));
        this.iconBack = new ImageIcon(getClass().getResource("/images/arrow.png"));
    }

    private void createButtons() {
        for (int i = 0; i < 32; i++) {
            btnsMap[i] = new JButton(String.valueOf(i));
            btnsMap[i].setHorizontalTextPosition(JButton.CENTER);
            btnsMap[i].setVerticalTextPosition(JButton.BOTTOM);
            btnsMap[i].setHorizontalAlignment(JButton.CENTER);
            btnsMap[i].setBorderPainted(false);
            btnsMap[i].setBorder(null);
            btnsMap[i].setContentAreaFilled(false);
            panelMap.add(btnsMap[i]);
        }
    }

    private void loadButonIcons() {
        SNode node = (player.getLevel() == 1) ? sLinkedList.getHeader() : dLinkedList.getHeader();
        int i = 0;

        while (node != null && i < 32) {
            if (node.getSpotType().equals("Start")) {
                btnsMap[i].setIcon(iconCharacter);
            } else if (node.getSpotType().equals("Finish")) {
                btnsMap[i].setIcon(iconFinishFlag);
            } else if (node.getSpotType().equals("Treasure")) {
                btnsMap[i].setIcon(iconTreasure);
                btnsMap[i].setBackground(new Color(225, 229, 9));
            } else if (node.getSpotType().equals("Trap")) {
                btnsMap[i].setIcon(iconTrap);
                btnsMap[i].setBackground(new Color(43, 36, 41));
            } else if (node.getSpotType().equals("Forward")) {
                btnsMap[i].setIcon(iconForward);
            } else if (node.getSpotType().equals("Backward")) {
                btnsMap[i].setIcon(iconBack);
            } else {
                btnsMap[i].setBackground(new Color(240, 248, 255));
            }
            node = node.getNext();
            i++;
        }
        btnsMap[31].setIcon(iconFinishFlag);
    }

    private void updateButtonIcon(SNode node) {
        int index = node.getSpotNum();
        String type = node.getSpotType();

        if (type.equals("Start")) {
            btnsMap[index].setIcon(iconStartFlag);
        } else if (type.equals("Finish")) {
            btnsMap[index].setIcon(iconFinishFlag);
        } else if (type.equals("Treasure")) {
            btnsMap[index].setIcon(iconTreasure);
        } else if (type.equals("Trap")) {
            btnsMap[index].setIcon(iconTrap);
        } else if (type.equals("Forward")) {
            btnsMap[index].setIcon(iconForward);
        } else if (type.equals("Back") || type.equals("Backward")) {
            btnsMap[index].setIcon(iconBack);
        } else {
            btnsMap[index].setIcon(null);
        }
    }

    private void clearForwardBackwardIcons(SNode node) {
        while (node != null) {
            String type = node.getSpotType();
            int index = node.getSpotNum();

            if (type.equals("Forward") || type.equals("Backward")) {
                btnsMap[index].setIcon(null);
                node.setSpotType("Empty");
                btnsMap[index].setBackground(new Color(240, 248, 255));
            }

            node = node.getNext();
        }
    }

    private void move(int dice) {
        rollScore = 0;

        SNode current = temp;
        updateButtonIcon(current);

        for (int i = 0; i < dice && current.getNext() != null; i++) {
            current = current.getNext();
        }

        boolean isBackOrForward = true;

        while (isBackOrForward && backForwardCounter < 5) {

            int randForward = new Random().nextInt(4) + 1;//1-2-3-4
            int randBackward = new Random().nextInt(2) + 1;//1-2

            isBackOrForward = false;

            String type = current.getSpotType();

            if (type.equals("Treasure")) {
                player.setScore(player.getScore() + 10);
                rollScore = 10;
            } else if (type.equals("Trap")) {
                player.setScore(player.getScore() - 5);
                rollScore = -5;
            } else if (type.equals("Forward")) {
                backForwardCounter++;

                JOptionPane.showMessageDialog(this, "Forward Spot: " + current.getSpotNum() + ": " + randForward + " step forward.", "Info", JOptionPane.INFORMATION_MESSAGE);

                for (int i = 0; i < randForward && current.getNext() != null; i++) {
                    current = current.getNext();
                }
                isBackOrForward = true;
            } else if (type.equals("Backward")) {
                backForwardCounter++;

                JOptionPane.showMessageDialog(this, "Backward Spot: " + current.getSpotNum() + ": " + randBackward + " step back.", "Info", JOptionPane.INFORMATION_MESSAGE);

                for (int i = 0; i < randBackward && current.getPrev() != null; i++) {
                    current = current.getPrev();
                }

                isBackOrForward = true;
            }
            if (backForwardCounter == 5) {
                clearForwardBackwardIcons(dLinkedList.getHeader());
                isBackOrForward = false;
            }

        }
        if (backForwardCounter == 5) {

            String type = current.getSpotType();

            if (type.equals("Treasure")) {
                player.setScore(player.getScore() + 10);
                rollScore = 10;
            } else if (type.equals("Trap")) {
                player.setScore(player.getScore() - 5);
                rollScore = -5;
            }

        }
        temp = current;
        btnsMap[temp.getSpotNum()].setIcon(iconCharacter);
        lblScorePlayer.setText(String.valueOf(player.getScore()));

        if (current.getSpotType().equals("Finish") && player.getLevel() == 1) {
            int result = JOptionPane.showOptionDialog(this,
                    "Level 1 is finished with " + player.getScore() + "! Do you want to continiue?",
                    "FINISH",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new String[]{"Continue", "Stop"},
                    "Continue");

            if (result == JOptionPane.NO_OPTION) {
                scores.writeScore();
                new MenuF().setVisible(true);
                this.dispose();
            } else {
                scores.writeScore();
                player.setLevel(2);
                player.setScore(0);
                new GameF(player, manager).setVisible(true);
                this.dispose();
            }
        } else if (current.getSpotType().equals("Finish") && player.getLevel() == 2) {
            JOptionPane.showMessageDialog(this, "Congratulations...You finished the game with " + player.getScore() + " score !!!");
            scores.writeScore();
            new MenuF().setVisible(true);
            this.dispose();
        }
        player.setPosition(temp.getSpotNum());
        if (player.getPosition() < 31) {  
            lblSpotTypePlayer.setText(temp.getSpotType());
        } else {
            lblSpotTypePlayer.setText("Finish");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        panelMap = new javax.swing.JPanel();
        panelInfos = new javax.swing.JPanel();
        btnRoll = new javax.swing.JButton();
        lblUsername = new javax.swing.JLabel();
        lblUsernamePlayer = new javax.swing.JLabel();
        lblScore = new javax.swing.JLabel();
        lblLevel = new javax.swing.JLabel();
        lblScorePlayer = new javax.swing.JLabel();
        lblLevelPlayer = new javax.swing.JLabel();
        lblPosition = new javax.swing.JLabel();
        lblPositionPlayer = new javax.swing.JLabel();
        lblDice = new javax.swing.JLabel();
        lblGainedScore = new javax.swing.JLabel();
        lblScoreGainedPlayer = new javax.swing.JLabel();
        lblSpot = new javax.swing.JLabel();
        lblSpotTypePlayer = new javax.swing.JLabel();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelMap.setLayout(new java.awt.GridLayout(4, 8));

        btnRoll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/dice_17595868.png"))); // NOI18N
        btnRoll.setText("Roll");
        btnRoll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRollActionPerformed(evt);
            }
        });

        lblUsername.setText("Username : ");

        lblUsernamePlayer.setText("null");

        lblScore.setText("Score : ");

        lblLevel.setText("Level :");

        lblScorePlayer.setText("0");

        lblLevelPlayer.setText("1");

        lblPosition.setText("Current Position : ");

        lblPositionPlayer.setText("0");

        lblGainedScore.setText("Score Gained:");

        lblScoreGainedPlayer.setText("null");

        lblSpot.setText("Spot Type:");

        lblSpotTypePlayer.setText("Start");

        javax.swing.GroupLayout panelInfosLayout = new javax.swing.GroupLayout(panelInfos);
        panelInfos.setLayout(panelInfosLayout);
        panelInfosLayout.setHorizontalGroup(
            panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfosLayout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelInfosLayout.createSequentialGroup()
                                .addComponent(lblPosition)
                                .addGap(0, 7, Short.MAX_VALUE))
                            .addComponent(lblGainedScore, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSpot, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSpotTypePlayer)
                            .addComponent(lblPositionPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblScoreGainedPlayer))
                        .addGap(601, 601, 601))
                    .addGroup(panelInfosLayout.createSequentialGroup()
                        .addComponent(btnRoll, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblDice, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfosLayout.createSequentialGroup()
                        .addComponent(lblUsername)
                        .addGap(18, 18, 18)
                        .addComponent(lblUsernamePlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(29, Short.MAX_VALUE))
                    .addGroup(panelInfosLayout.createSequentialGroup()
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblScore)
                            .addComponent(lblLevel, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblScorePlayer, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                            .addComponent(lblLevelPlayer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        panelInfosLayout.setVerticalGroup(
            panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInfosLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInfosLayout.createSequentialGroup()
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRoll, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelInfosLayout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(lblDice, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPosition)
                            .addComponent(lblPositionPlayer, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblGainedScore)
                            .addComponent(lblScoreGainedPlayer))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblSpot)
                            .addComponent(lblSpotTypePlayer))
                        .addContainerGap(16, Short.MAX_VALUE))
                    .addGroup(panelInfosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblUsername)
                            .addComponent(lblUsernamePlayer))
                        .addGap(18, 18, 18)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblScore)
                            .addComponent(lblScorePlayer))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(panelInfosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLevel)
                            .addComponent(lblLevelPlayer))
                        .addGap(37, 37, 37))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelInfos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMap, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 546, Short.MAX_VALUE)
                .addComponent(panelInfos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRollActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRollActionPerformed
        lblLevelPlayer.setText(String.valueOf(player.getLevel()));
        player.setPrevPosition(player.getPosition());

        Random random = new Random();
        int diceRoll = random.nextInt(6) + 1;
        lblDice.setText(String.valueOf(diceRoll));

        move(diceRoll);
        player.setPosition(temp.getSpotNum());

        lblScoreGainedPlayer.setText(String.valueOf(rollScore));
        lblPositionPlayer.setText(String.valueOf(player.getPosition()));
        lblScorePlayer.setText(String.valueOf(player.getScore()));
    }//GEN-LAST:event_btnRollActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GameF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GameF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GameF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GameF.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
//                try { 
//                    new GameF(player, manager).setVisible(true);
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, "null player or manager!!!");
//                }
                //new GameF().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRoll;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel lblDice;
    private javax.swing.JLabel lblGainedScore;
    private javax.swing.JLabel lblLevel;
    private javax.swing.JLabel lblLevelPlayer;
    private javax.swing.JLabel lblPosition;
    private javax.swing.JLabel lblPositionPlayer;
    private javax.swing.JLabel lblScore;
    private javax.swing.JLabel lblScoreGainedPlayer;
    private javax.swing.JLabel lblScorePlayer;
    private javax.swing.JLabel lblSpot;
    private javax.swing.JLabel lblSpotTypePlayer;
    private javax.swing.JLabel lblUsername;
    private javax.swing.JLabel lblUsernamePlayer;
    private javax.swing.JPanel panelInfos;
    private javax.swing.JPanel panelMap;
    // End of variables declaration//GEN-END:variables
}
