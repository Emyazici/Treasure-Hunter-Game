package YaziciVarol;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Random;

/**
 *
 * @author emreyazici
 */
public class Manager {

    private SLinkedList sListFirstLevel;
    private DLinkedList dListSecondLevel;
    private int emptyCounter = 0;
    int moveIndex = 0;

    public SLinkedList getsListFirstLevel() {
        return sListFirstLevel;
    }

    public DLinkedList getdListSecondLevel() {
        return dListSecondLevel;
    }

    public String determineSpotType(int level) {
        String[] spots;
        int emptyIndex;

        if (level == 1) {
            spots = new String[]{"Treasure", "Trap", "Empty"};
            emptyIndex = 2;
        } else {
            spots = new String[]{"Treasure", "Trap", "Forward", "Backward", "Empty"};
            emptyIndex = 4;
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(spots.length);

        if (randomIndex == emptyIndex) {
            emptyCounter++;
            if (emptyCounter > 4) {
                randomIndex = rand.nextInt(spots.length - 1);
                emptyCounter--;
            }
        } else if (randomIndex == 2 || randomIndex == 3) {
            moveIndex++;
            if (moveIndex == 9) {
                randomIndex = rand.nextInt(2);
                moveIndex--;
            }
        }
        return spots[randomIndex];
    }

    public Object createLists(int level) {
        if (level == 1) {
            sListFirstLevel = new SLinkedList();
            sListFirstLevel.add("Start", 0);

            for (int i = 1; i < 31; i++) { // first spot is start,last spot is finish point
                sListFirstLevel.add(determineSpotType(level), i);
            }
            sListFirstLevel.add("Finish", 31);
            System.out.println("List is created for Level 1...");
            return sListFirstLevel;
        } else {
            emptyCounter = 0;
            dListSecondLevel = new DLinkedList();
            dListSecondLevel.add("Start", 0);

            for (int i = 1; i < 31; i++) { // first spot is start,last spot is finish point
                dListSecondLevel.add(determineSpotType(level), i);
            }
            dListSecondLevel.add("Finish", 31);
            System.out.println("List is created for Level 2...");
            return dListSecondLevel;
        }
    }

}
