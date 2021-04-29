package com.danial_iranpour;

import java.io.*;
import java.util.*;

/**
 * Created by Danial_Iranpour on 7/18/2016.
 */
public class LogicGameBoard implements Serializable {
    private int turn;
    private Player[] players = new Player[4];
    private Piece[] isPieces = new Piece[72];
    transient static GameBoard graphic=null;
    transient private FinishPage finishPage;
    public LogicGameBoard(GameBoard graphic) {
        this.graphic=graphic;
        players[0] = new GreenPlayer(Player.Color.GREEN, this);
        players[0].setPlayerStatus(Player.PlayerStatus.PLAYING);
        players[1] = new RedPlayer(Player.Color.RED, this);
        players[1].setPlayerStatus(Player.PlayerStatus.PLAYING);
        players[2] = new YellowPlayer(Player.Color.YELLOW, this);
        players[2].setPlayerStatus(Player.PlayerStatus.PLAYING);
        players[3] = new BluePlayer(Player.Color.BLUE, this);
        players[3].setPlayerStatus(Player.PlayerStatus.PLAYING);

    }

    public void pass() {
        if (turn > 3)
            turn %= 4;
        if (!isFinished()) {
            if (players[turn].getPlayerStatus().equals(Player.PlayerStatus.PLAYING)) {
                players[turn].movePlayer();
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                }
                graphic.repaint();
            }
            save();
            turn++;
            pass();
        } else {
            try {
                GameBoard.gamer.sleep(2000);
            } catch (InterruptedException e) {
            }
            if (GameBoard.gamer.isAlive()) {
                GameBoard.gamer.interrupt();
                try {
                    GameBoard.gamer.join();
                } catch (InterruptedException e) {
                }
            }
            if (!FinishPage.build) {
                graphic.setVisible(false);
                finishPage=new FinishPage(players);
            }
        }
    }

        public boolean isFinished() {

            for (int i = 0; i < 4; i++) {
                if (players[i].getPlayerStatus().equals(Player.PlayerStatus.PLAYING))
                    return false;
            }
            return true;
        }

    ///////////////////////////////////////////dice
    public int dice() {
        Random rnd = new Random();
        return rnd.nextInt(6) + 1;
    }

    /////////////////////////////////////////move
    public boolean move(Piece piece, int start, int step) {
        if (start + step <= 71) {
            if (isPieces[start + step] == null) {
                if (isPieces[start] != null)
                    isPieces[start] = null;
                isPieces[start + step] = piece;
                return true;
            } else {
                if (isPieces[start + step].getPlayer().equals(piece.getPlayer())) {
                    piece.setIdCell(start);
                    return false;
                } else {
                    isPieces[start + step].setIdCell(isPieces[start + step].getStartNumber());
                    isPieces[start + step].setRealId(isPieces[start + step].getNumber());
                    isPieces[start + step].setPieceStatus(Piece.PieceStatus.NO_STARTED_GAME);
                    if (isPieces[start] != null)
                        isPieces[start] = null;
                    isPieces[start + step] = piece;
                    return true;
                }
            }
        } else {
            turn += 1;
            pass();
            return false;
        }
    }

    /////////////////////////////////////////////
    public void save(){
        ObjectOutputStream writer=null;
        try {
            writer=new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("src/com/danial_iranpour/Documents/file.txt")));
           if(!isFinished()) writer.writeObject(this);
            else writer.writeObject(null);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                if(writer!=null)
                    writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //////////////////////////////////////////cellPosition
    public int[] getCellPosition(Piece.PieceStatus pieceStatus, int position) {

        int[] array = new int[2];

        Map<Integer, List<Integer>> map = new LinkedHashMap<>();

        for (int i = 0; i < 16; i++) {
            map.put(i, new ArrayList<>());
        }


        for (int i = 0; i < 16; i++) {
            if (i < 8) {
                for (int j = 0; j < 4; j++) {
                    if (i == 0) map.get(i).add(j);
                    if (i == 1) map.get(i).add(j + 18);
                    if (i == 2) map.get(i).add(j + 36);
                    if (i == 3) map.get(i).add(j + 54);
                    if (i == 4) map.get(i).add(j + 63);
                    if (i == 5) map.get(i).add(j + 9);
                    if (i == 6) map.get(i).add(j + 27);
                    if (i == 7) map.get(i).add(j + 45);
                }
            } else {
                for (int j = 0; j < 5; j++) {
                    if (i == 8) map.get(i).add(j + 67);
                    if (i == 9) map.get(i).add(j + 4);
                    if (i == 10) map.get(i).add(j + 13);
                    if (i == 11) map.get(i).add(j + 22);
                    if (i == 12) map.get(i).add(j + 31);
                    if (i == 13) map.get(i).add(j + 40);
                    if (i == 14) map.get(i).add(j + 49);
                    if (i == 15) map.get(i).add(j + 58);
                }
            }
        }

        if (map.get(0).contains(position)) {

            if (position == 0) {
                array[1] = 100;
                array[0] = 710;
                return array;
            }
            if (position == 1) {
                array[1] = 100;
                array[0] = 750;
                return array;
            }
            if (position == 2) {
                array[1] = 140;
                array[0] = 710;
                return array;
            }
            if (position == 3) {
                array[1] = 140;
                array[0] = 750;
                return array;
            }

        }

        if (map.get(1).contains(position)) {

            if (position == 18) {
                array[1] = 460;
                array[0] = 710;
                return array;
            }
            if (position == 19) {
                array[1] = 460;
                array[0] = 750;
                return array;
            }
            if (position == 20) {
                array[1] = 500;
                array[0] = 710;
                return array;
            }
            if (position == 21) {
                array[1] = 500;
                array[0] = 750;
                return array;
            }


        }
        if (map.get(2).contains(position)) {
            if (position == 36) {
                array[1] = 460;
                array[0] = 350;
                return array;
            }
            if (position == 37) {
                array[1] = 460;
                array[0] = 390;
                return array;
            }
            if (position == 38) {
                array[1] = 500;
                array[0] = 350;
                return array;
            }
            if (position == 39) {
                array[1] = 500;
                array[0] = 390;
                return array;
            }
        }
        if (map.get(3).contains(position)) {
            if (position == 54) {
                array[1] = 100;
                array[0] = 350;
                return array;
            }
            if (position == 55) {
                array[1] = 100;
                array[0] = 390;
                return array;
            }
            if (position == 56) {
                array[1] = 140;
                array[0] = 350;
                return array;
            }
            if (position == 57) {
                array[1] = 140;
                array[0] = 390;
                return array;
            }
        }
        int n = position;
        if (map.get(4).contains(position)) {
            array[0] = 510;
            n = 66 - n;
            array[1] = 100 + (n * 40);
            return array;

        }
        if (map.get(5).contains(position)) {
            array[1] = 260;
            n -= 9;
            array[0] = 630 + (n * 40);
            return array;
        }
        if (map.get(6).contains(position)) {
            array[0] = 590;
            n -= 27;
            array[1] = 380 + (n * 40);
            return array;
        }
        if (map.get(7).contains(position)) {
            array[1] = 340;
            n -= 45;
            array[0] = 470 - (n * 40);
            return array;
        }
        if (map.get(8).contains(position)) {
            array[0] = 550;
            n -= 67;
            array[1] = 100 + (n * 40);
            return array;
        }
        if (map.get(9).contains(position)) {
            array[0] = 590;
            n -= 4;
            array[1] = 100 + (n * 40);
            return array;
        }
        if (map.get(10).contains(position)) {
            array[1] = 300;
            n -= 13;
            array[0] = 750 - (n * 40);
            return array;
        }
        if (map.get(11).contains(position)) {
            array[1] = 340;
            n -= 22;
            array[0] = 750 - (n * 40);
            return array;
        }
        if (map.get(12).contains(position)) {
            array[0] = 550;
            n -= 31;
            array[1] = 500 - (n * 40);
            return array;
        }
        if (map.get(13).contains(position)) {
            array[0] = 510;
            n -= 40;
            array[1] = 500 - (n * 40);
            return array;
        }
        if (map.get(14).contains(position)) {
            array[1] = 300;
            n -= 49;
            array[0] = 350 + (n * 40);
            return array;
        }
        if (map.get(15).contains(position)) {
            array[1] = 260;
            n -= 58;
            array[0] = 350 + (n * 40);
            return array;
        } else {
            System.out.print(position);
            System.out.println(" not exist");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return array;
        }
    }

    public Piece[] getIsPieces() {
        return isPieces;
    }


    public Player[] getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }
}
