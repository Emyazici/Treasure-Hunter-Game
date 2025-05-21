package YaziciVarol;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 *
 * @author emreyazici
 */
public class Scores {
    private Player player;
    public Scores(Player player) {
        this.player=player;
    }
    
    public void writeScore() {
        String line = player.getUsername() + ",level" + player.getLevel() + "," + player.getScore();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("score.txt", true))) {
            writer.write(line);
            writer.newLine();
            System.out.println("Score has written: " + line);
        } catch (IOException e) {
            System.err.println("Score has written: " + e.getMessage());
        }
    }
}
