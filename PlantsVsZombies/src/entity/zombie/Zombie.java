package entity.zombie;
import javax.swing.*;

import GamePlayandGUI.Collider;
import GamePlayandGUI.GamePanel;
import GamePlayandGUI.GameWindow;
/** 
* In a game like Plants vs. Zombies, it represents a zombie entity. 
* Controls the movement of zombies, collision detection, and plant interactions.
*/
public class Zombie {
    //Basic zombie attributes
    public int health = 1000;
    public int speed = 1;
    private GamePanel gp;
    public int posX = 1000;
    public int myLane;
    public boolean isMoving = true;
     /**
     * Constructor for creating a new zombie
     * @param parent The GamePanel this zombie belongs to
     * @param lane The lane number where this zombie will move
     */ 
    public Zombie(GamePanel parent, int lane) {
        this.gp = parent;
        myLane = lane;
    }
    /** 
    * Controls the movement of zombies and their collisions with plant life. 
    * The zombie advances to the left until it either: 
    * 1. Strikes a plant, destroying it; 
    * 2. Approaches the left edge, ending the game(game over);
    */
    public void advance() {
        if (isMoving) {
            boolean isCollides = false;
            Collider collided = null;
            // Check the current lane to look for collisions with plants.
            for (int i = myLane * 9; i < (myLane + 1) * 9; i++) {
                if (gp.colliders[i].assignedPlant != null && gp.colliders[i].isInsideCollider(posX)) {
                    isCollides = true;
                    collided = gp.colliders[i];
                }
            }
            if (!isCollides) {
                if (slowInt > 0) {
                    if (slowInt % 2 == 0) {
                        posX--;
                    }
                    slowInt--;
                } else {
                    posX -= 1;
                }
            } else {
                collided.assignedPlant.health -= 10;
                if (collided.assignedPlant.health < 0) {
                    collided.removePlant();
                }
            }
    
            // Khi posX < 0, set isMoving = false và hiển thị hình ảnh zombie
            //When the zombie reaches the left side, the game is ended.
            if (posX < 0) {
                isMoving = false;
                JOptionPane.showMessageDialog(gp, "ZOMBIES ATE YOUR BRAIN !" + '\n' + "Starting the level again");
                GameWindow.gw.dispose();
                GameWindow.gw = new GameWindow();
            }
        }
    }
    

   

    int slowInt = 0; // Counter for the duration of the slower effect
    /**
     * Applies a slowing effect to the zombie
     * When slowed, zombie moves at half speed for 1000 ticks
     */

    public void slow() {
        slowInt = 1000;
    }
    /** 
    * Factory function to generate various zombie types 
    * @param type The type of zombie to create ("NormalZombie" or "ConeHeadZombie")
    * @param parent The GamePanel reference
    * @param lane The lane number for the zombie 
    * @return A new zombie instance of the specified type 
    * @throws IllegalStateException if an invalid zombie type is specified
    */
    public static Zombie getZombie(String type, GamePanel parent, int lane) {
        Zombie z = new Zombie(parent, lane);
        switch (type) {
            case "NormalZombie":
                z = new NormalZombie(parent, lane);
                break;
            case "ConeHeadZombie":
                z = new ConeHeadZombie(parent, lane);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        return z;
    }

}
