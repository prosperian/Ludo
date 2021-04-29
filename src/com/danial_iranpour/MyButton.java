package com.danial_iranpour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;

/**
 * Created by Danial_Iranpour on 7/7/2016.
 */
public class MyButton extends JButton implements Serializable{

    private int x,y;
    public MyButton(String text,int x,int y){

        this.x=x;

        this.y=y;

        this.setText(text);

        this.setForeground(Color.darkGray);

        this.setFont(new Font("Tahoma",0,12));

        this.setFocusPainted(false);

        this.setContentAreaFilled(false);

        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.setPreferredSize(new Dimension(x,y));

        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                requestFocus(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(isEnabled()) {
                    setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
                    setForeground(Color.darkGray);

                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(isEnabled()) {

                    setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter()));
                    setForeground(Color.BLUE.brighter());
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(isEnabled()) {

                    requestFocus(true);
                    setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter()));
                    setForeground(Color.BLUE.brighter());
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if(isEnabled()) {

                    setBorder(BorderFactory.createLineBorder(Color.BLACK.brighter()));
                    setForeground(Color.darkGray);
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if(isEnabled()) {

                    setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter()));
                    setForeground(Color.BLUE);

                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if(isEnabled()) {

                    setBorder(BorderFactory.createLineBorder(Color.BLACK.brighter()));
                    setForeground(Color.darkGray);
                }
            }
        });
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(isEnabled()) {

                    if(e.getKeyCode()== KeyEvent.VK_SPACE) {
                        setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
                        setForeground(Color.darkGray);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if(isEnabled()) {

                    if(e.getKeyCode()== KeyEvent.VK_SPACE) {
                        setBorder(BorderFactory.createLineBorder(Color.BLUE.brighter()));
                        setForeground(Color.BLUE);

                    }
                }
            }
        });





    }
}
