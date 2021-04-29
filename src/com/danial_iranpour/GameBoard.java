package com.danial_iranpour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Created by Danial_Iranpour on 7/7/2016.
 */
public class GameBoard extends JFrame implements ActionListener,Serializable {
    private JPanel gamePanel;
    private MyButton playButton;
    private JPanel detailsPanel;
    private LogicGameBoard logicGameBoard;
    static int rankGame;
    static boolean draw;
    static Thread gamer;
    public GameBoard() {
        if(LogicGameBoard.graphic==null)
            logicGameBoard=new LogicGameBoard(this);
        ///////////////////////////////////////startPosition
        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int) (size.getWidth() - 900) / 2, ((int) size.getHeight() - 700) / 2);
        //////////////////////////////////////////////////////
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        this.setSize(900, 700);
        this.setResizable(false);

        ///////////////////////////////////////////////////////////////////////////////////////////////////////gamePanel
        SpringLayout gameBoardLayout;
        gameBoardLayout = new SpringLayout();
        gamePanel = new JPanel(gameBoardLayout) {

            int x = 0, y = 0;
            static final int HW = 40;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                x = 550;
                y = 100;
                g.setColor(Color.GRAY);
                g.fillOval(x, y, HW, HW);

                int temp = y;

                for (int i = 0; i < 4; i++) {
                    g.setColor(Color.GREEN.darker());
                    g.fillOval(x, temp += HW, HW, HW);
                }

                g.setColor(Color.GRAY);

                g.fillOval(x += HW, y, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x, y += HW, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x += HW, y, HW, HW);

                g.fillOval(x, y += HW, HW, HW);

                temp = x;

                g.setColor(Color.red.darker());

                for (int i = 0; i < 4; i++)
                    g.fillOval(temp -= HW, y, HW, HW);

                g.setColor(Color.GRAY);

                g.fillOval(x, y += HW, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x -= HW, y, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x, y += HW, HW, HW);

                g.fillOval(x -= HW, y, HW, HW);

                temp = y;

                g.setColor(Color.YELLOW.darker());

                for (int i = 0; i < 4; i++)
                    g.fillOval(x, temp -= HW, HW, HW);

                g.setColor(Color.GRAY);

                g.fillOval(x -= HW, y, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x, y -= HW, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x -= HW, y, HW, HW);

                g.fillOval(x, y -= HW, HW, HW);

                temp = x;

                g.setColor(Color.CYAN.darker());

                for (int i = 0; i < 4; i++)
                    g.fillOval(temp += HW, y, HW, HW);

                g.setColor(Color.GRAY);

                g.fillOval(x, y -= HW, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x += HW, y, HW, HW);

                for (int i = 0; i < 4; i++)
                    g.fillOval(x, y -= HW, HW, HW);

                x = 350;
                y = 100;

                g.fillOval(x, y, HW, HW);
                g.fillOval(x + HW, y, HW, HW);
                g.fillOval(x, y + HW, HW, HW);
                g.fillOval(x + HW, y + HW, HW, HW);

                x = 710;

                g.fillOval(x, y, HW, HW);
                g.fillOval(x + HW, y, HW, HW);
                g.fillOval(x, y + HW, HW, HW);
                g.fillOval(x + HW, y + HW, HW, HW);

                x = 350;
                y = 460;

                g.fillOval(x, y, HW, HW);
                g.fillOval(x + HW, y, HW, HW);
                g.fillOval(x, y + HW, HW, HW);
                g.fillOval(x + HW, y + HW, HW, HW);

                x = 710;

                g.fillOval(x, y, HW, HW);
                g.fillOval(x + HW, y, HW, HW);
                g.fillOval(x, y + HW, HW, HW);
                g.fillOval(x + HW, y + HW, HW, HW);

                for (int j = 0; j < 4; j++) {

                    g.setColor(logicGameBoard.getPlayers()[j].getBackground());
                    for (int i = 0; i < 4; i++) {

                        logicGameBoard.getPlayers()[j].pieces[i].paintPiece();
                        g.fillOval(logicGameBoard.getPlayers()[j].pieces[i].getX1(), logicGameBoard.getPlayers()[j].pieces[i].getY1(), HW, HW);

                    }
                }
            }

        };

        gamePanel.setBackground(Color.ORANGE);
        this.add(gamePanel);

        ///////////////////////////////////////////button
        playButton = new MyButton("Play It", 150, 100);
        playButton.setActionCommand("playButton");
        playButton.addActionListener(this);
        gameBoardLayout.putConstraint(SpringLayout.WEST, playButton, 495, SpringLayout.WEST, getContentPane());
        gameBoardLayout.putConstraint(SpringLayout.NORTH, playButton, 550, SpringLayout.NORTH, getContentPane());
        gamePanel.add(playButton);
        //////////////////////////////////////////////////detailPanel
        detailsPanel = new JPanel() {
            transient BufferedImage img1 = null;
            transient BufferedImage img2 = null;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.setFont(new Font("Dialog", Font.BOLD, 14));
                if (!draw) {

                    g.drawString("TURN : NONE", 50, 70);

                    try {
                        img1 = ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/1.png"));
                        img2 = ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/1.png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    draw = true;
                } else {
                    g.drawString("Turn : " + logicGameBoard.getPlayers()[logicGameBoard.getTurn()].getColor().toString(), 50, 70);
                    try {
                        img1 = ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/" + logicGameBoard.getPlayers()[logicGameBoard.getTurn()].dice1 + ".png"));
                        img2 = ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/" + logicGameBoard.getPlayers()[logicGameBoard.getTurn()].dice2 + ".png"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                g.drawImage(img1, 25, 100, null);
                g.drawImage(img2, 100, 100, null);

                g.setColor(Color.BLACK);

                for (int i = 0; i < 4; i++) {

                    if (logicGameBoard.getPlayers()[i].getPlayerStatus().equals(Player.PlayerStatus.FINISHED)) {
                        g.setFont(new Font("Dialog", Font.BOLD, 14));
                        g.drawString("Ranks", 75, 215);
                        g.setFont(new Font("Serif", Font.BOLD, 12));
                        g.drawRect(25, 190, 145, 200);
                        g.drawString(String.valueOf(logicGameBoard.getPlayers()[i].rankPlayer) + " : " + logicGameBoard.getPlayers()[i].getColor().toString()
                                , 40, 220 + (logicGameBoard.getPlayers()[i].rankPlayer * 20));
                    }
                }
            }
        };
        detailsPanel.setBackground(Color.ORANGE);
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Details"));
        detailsPanel.setPreferredSize(new Dimension(200, 660));
        gameBoardLayout.putConstraint(SpringLayout.WEST, detailsPanel, 10, SpringLayout.WEST, getContentPane());
        gameBoardLayout.putConstraint(SpringLayout.NORTH, detailsPanel, 5, SpringLayout.NORTH, getContentPane());
        gamePanel.add(detailsPanel);

        this.setVisible(true);
    }

    /////////////////////////////////////////////////////////////actions

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("playButton")) {
            playButton.setEnabled(false);
            playButton.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
            gamer = new Thread(new Runnable() {
                @Override
                public void run() {
                    logicGameBoard.pass();
                }
            });
            gamer.start();

        }
    }
    /////////////////////////////////////////////////////////////
    public void load(MainFrame mainFrame){

        if(mainFrame.getObject()!=null) {
            logicGameBoard = (LogicGameBoard) mainFrame.getObject();
            logicGameBoard.setTurn(logicGameBoard.getTurn()+1);
        }
    }



}
