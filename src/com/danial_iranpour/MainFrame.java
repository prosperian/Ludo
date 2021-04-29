package com.danial_iranpour;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Danial_Iranpour on 7/7/2016.
 */
public class MainFrame extends JFrame implements ActionListener,Serializable{

    private GameBoard gameBoard;

    private MyButton newGame;

    private MyButton exit;

    private MyButton resume;

    private Date date=null;

    private Object object=null;

    private BufferedImage img=null;

    private JPanel tikTakPanel;


    public MainFrame(){

        Thread pictureLoader=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    img =  ImageIO.read(getClass().getResource("/com/danial_iranpour/Images/en.png"));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
        pictureLoader.start();
        try {
            pictureLoader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ///////////////////////////////////////startPosition
        Dimension size=Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation((int)(size.getWidth()-700)/2,((int)size.getHeight()-700)/2);
        ////////////////////////////////////////////////////
        this.setSize(700,700);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ///////////////////////////////////////////////////////////////////backGround
        SpringLayout startLayout;
        startLayout=new SpringLayout();
        JPanel startPanel=new JPanel(startLayout){

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(Color.ORANGE);


                g.drawImage(img,140,50,img.getWidth(),img.getHeight(),null);

                g.setColor(Color.BLACK);
                g.setFont(new Font("Dialog",Font.BOLD,14));


                g.setFont(new Font("Dialog",Font.BOLD,11));

                g.drawString("..................................................",5,650);
                g.drawString("created by danial_iranpour.",5,660);
                g.drawString("..................................................",5,665);


            }
        };
        getContentPane().add(startPanel);
        startPanel.setLayout(startLayout);
        startPanel.setBackground(Color.ORANGE);

        ///////////////////////////////////////////////////////buttons
        newGame=new MyButton("NEW GAME",200,100);
        exit=new MyButton("EXIT",200,100);
        resume=new MyButton("RESUME GAME",200,100);

        newGame.setActionCommand("new");
        newGame.addActionListener(this);

        exit.setActionCommand("exit");
        exit.addActionListener(this);

        resume.setActionCommand("resume");
        resume.addActionListener(this);

        startLayout.putConstraint(SpringLayout.WEST,exit,470,SpringLayout.WEST,getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH,exit,420,SpringLayout.NORTH,getContentPane());

        startLayout.putConstraint(SpringLayout.WEST,newGame,30,SpringLayout.WEST,getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH,newGame,420,SpringLayout.NORTH,getContentPane());

        startLayout.putConstraint(SpringLayout.WEST,resume,250,SpringLayout.WEST,getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH,resume,420,SpringLayout.NORTH,getContentPane());

        ObjectInputStream reader=null;

        try {
            reader=new ObjectInputStream(new BufferedInputStream(new FileInputStream("src/com/danial_iranpour/Documents/file.txt")));
            object=reader.readObject();
            if(object==null){
                resume.setEnabled(false);
                resume.setBorder(BorderFactory.createLineBorder(Color.GRAY,1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally{
            try {
                if(reader!=null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }


        startPanel.add(exit);

        startPanel.add(newGame);

        startPanel.add(resume);

        ///////////////////////////////////////////////////////////////////addClock
        tikTakPanel=new JPanel(){
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                DateFormat format=new SimpleDateFormat("yyyy / MM / dd     HH:mm:ss ");
                date=new Date();

                g.setFont(new Font("Dialog",Font.BOLD,11));
                g.setColor(Color.BLACK);
                g.drawString("......................................................",0,3);
                g.drawString("Time : " + format.format(date),0,14);
                g.drawString("......................................................",0,19);


            }
        };
        this.startClock();
        startLayout.putConstraint(SpringLayout.WEST,tikTakPanel,527,SpringLayout.WEST,getContentPane());
        startLayout.putConstraint(SpringLayout.NORTH,tikTakPanel,645,SpringLayout.NORTH, getContentPane());
        tikTakPanel.setPreferredSize(new Dimension(170,20));
        tikTakPanel.setBackground(Color.ORANGE);
        startPanel.add(tikTakPanel);


        this.setVisible(true);

    }

    ///////////////////////////////////////////////////////

    public static void main(String []args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame();
            }
        });

    }

    ////////////////////////////////////////////////////////
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("exit"))
            System.exit(0);
        else if(e.getActionCommand().equals("new")){
            gameBoard=new GameBoard();
            this.setVisible(false);
        }else if(e.getActionCommand().equals("resume")){
            gameBoard=new GameBoard();
            LogicGameBoard.graphic=gameBoard;
            gameBoard.load(this);
        }

    }

    public Object getObject() {
        return object;
    }

    ////////////////////////////////////////////////////////
    public void startClock(){

        TikTak tiktak=new TikTak();
        tiktak.start();

    }

    ////////////////////////////////////////////////////////
    class TikTak extends Thread implements Serializable{

        @Override
        public void run() {

            while (true) {
                date = new Date();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tikTakPanel.repaint();
            }

        }
    }
    //////////////////////////////////////////////////////////


}
