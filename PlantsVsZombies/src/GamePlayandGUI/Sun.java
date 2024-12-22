package GamePlayandGUI;
import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Sun extends JPanel implements MouseListener {
    // The allusion to the main game panel
    GamePanel gp;
    // The image of the sun will be displayed
    Image sunImage;
    // The current coordinates of x and y
    int myX;
    int myY;
    // The final y position at which the sun stops falling
    int endY;
    
    // Sun disappearance timer (200 ticks)
    int destruct = 200;
    // Constructor initializes a sun with starting and ending positions
    public Sun(GamePanel parent,int startX,int startY,int endY){
        this.gp = parent;
        this.endY = endY;
        setSize(80,80);
        setOpaque(false);
        myX = startX;
        myY = startY;
        setLocation(myX,myY);
        sunImage = new ImageIcon(this.getClass().getResource("/images/sun.png")).getImage();
        addMouseListener(this);
    }
    // Draw the sun image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(sunImage,0,0,null);
    }
    // Manage the lifetime and movement of the sun
    public void advance(){
        if(myY < endY) {
            myY+= 4;
        }else{
            destruct--;
            if(destruct<0){
                gp.remove(this);
                gp.activeSuns.remove(this);
            }
        }
        setLocation(myX,myY);
    }
    // Event handlers for mouse
    @Override
    public void mouseClicked(MouseEvent e) {
    }
    
    @Override
    public void mousePressed(MouseEvent e) {

    }
    // When the sun is shown or clicked
    @Override
    public void mouseReleased(MouseEvent e) {
        gp.setSunScore(gp.getSunScore()+25);
        gp.remove(this);
        gp.activeSuns.remove(this);
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
