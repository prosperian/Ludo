package com.danial_iranpour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Danial_Iranpour on 7/14/2016.
 */
public class FinishPage extends JFrame implements Serializable {
    Player []players=new Player[4];
    private JPanel winnerPanel;
    static boolean build;
    private int x=80,y=100;
    private Image gold;
    private Image silver;
    private Image bronze;
    public FinishPage(Player ... players){
        this.players=players;
        build=true;
        setResizable(false);
        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(size.getWidth()-500)/2,((int)size.getHeight()-500)/2);
        try {
            gold = ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/gold-medal (1).png"));
            silver = ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/silver-medal.png"));
            bronze = ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/bronze-medal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        winnerPanel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);

                g.setFont(new Font("Dialog", Font.BOLD, 25));
                for (int i = 0; i < players.length; i++) {
                    if (players[i].rankPlayer == 1) {
                        g.drawString(players[i].getColor().toString(),x, y+10);
                        g.drawImage(gold,300,y-30,null);
                        this.setBackground(players[i].getBackground());
                    }
                    else if(players[i].rankPlayer==2){
                        g.drawString(players[i].getColor().toString(),x, y+110);
                        g.drawImage(silver,300,y+100-30,null);

                    }
                    else if(players[i].rankPlayer==3){
                        g.drawString(players[i].getColor().toString(),x,y+210 );
                        g.drawImage(bronze,300,y+200-30,null);
                    }
                }

            }
        };
        setSize(500,500);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().add(winnerPanel,BorderLayout.CENTER);
        setVisible(true);

    }

}
