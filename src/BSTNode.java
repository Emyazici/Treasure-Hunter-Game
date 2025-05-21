package YaziciVarol;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author emreyazici
 */
public class BSTNode {

    int score;
    String username;
    String level;
    BSTNode left, right;

    public BSTNode(int score, String username, String level) {
        this.score = score;
        this.username = username;
        this.level = level;
        this.left = null;
        this.right = null;
    }
}
