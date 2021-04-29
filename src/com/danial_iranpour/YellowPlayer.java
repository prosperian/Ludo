package com.danial_iranpour;

import com.sun.xml.internal.bind.v2.TODO;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Created by iWin_64bit on 6/4/2016.
 */
public class YellowPlayer extends Player {

    public YellowPlayer(Color color,LogicGameBoard gameBoard){
        this.gameBoard=gameBoard;
        this.setPlayerStatus(Player.PlayerStatus.PLAYING);
        this.setBackground(java.awt.Color.YELLOW);
        this.setColor(color);

        for (int i = 0; i <4 ; i++) {
            pieces[i]=new Piece(this);
            pieces[i].setPieceStatus(Piece.PieceStatus.NO_STARTED_GAME);
            pieces[i].setNumber(i);
            pieces[i].setIdCell(36+i);
            pieces[i].setRealId(i);
            pieces[i].setStartNumber(36+i);

        }


    }

    @Override
    public int dicePlayer() {

        dice1=gameBoard.dice();
        dice2=gameBoard.dice();


        return (((Math.max(dice1,dice2)/Math.min(dice1,dice2))*7)%6)+1;
    }

    @Override
    public void movePlayer() {
        if (!this.getPlayerStatus().equals(PlayerStatus.FINISHED)) {
            step = dicePlayer();
            turn = -1;
            for (int i = 0; i < 4; i++) {
                if (pieces[i].getPieceStatus().equals(Piece.PieceStatus.IN_GAME)) {
                    if(pieces[i].getRealId()!=-1) {
                        turn = i;
                        break;
                    }
                }
            }

            if (turn == -1) {
                if (dice1 == 6 || dice2 == 6) {
                    for (int i = 0; i < 4; i++) {
                        if (pieces[i].getPieceStatus().equals(Piece.PieceStatus.NO_STARTED_GAME)) {
                            turn = i;
                            break;
                        }
                    }
                }
            }

            if(turn!=-1) turn=moveIn(turn);



            if(turn!=-1) {

                if (pieces[turn].getIdCell() <= 39 && pieces[turn].getIdCell() >= 36) {
                    pieces[turn].setIdCell(40);
                    pieces[turn].setRealId(4);
                    pieces[turn].setPieceStatus(Piece.PieceStatus.IN_GAME);
                    howIsIt = gameBoard.move(pieces[turn], pieces[turn].getIdCell(), 0);
                    if (howIsIt) {
                        pieces[turn].paintPiece();
                        updatePlayer(pieces[turn]);
                        return;
                    } else {
                        pieces[turn].setIdCell(pieces[turn].getStartNumber());
                        pieces[turn].setPieceStatus(Piece.PieceStatus.NO_STARTED_GAME);
                        turn = -1;
                    }
                }
            }



            if (turn != -1) {


                int start=-1;
                if(pieces[turn].getIdCell()==67)
                {
                    start=pieces[turn].getIdCell();
                    pieces[turn].setIdCell(3);
                    pieces[turn].setRealId(39);
                }
                if(pieces[turn].getIdCell()+step<=71&&pieces[turn].getIdCell()+step>=68){
                    start=pieces[turn].getIdCell();
                    pieces[turn].setIdCell(2);
                    pieces[turn].setRealId(38);
                }

                isLegal();

                howIsIt = gameBoard.move(pieces[turn], pieces[turn].getIdCell(), step);

                if (howIsIt) {

                    pieces[turn].setIdCell(step + pieces[turn].getIdCell());
                    pieces[turn].setRealId(step + pieces[turn].getRealId());
                    if(start!=-1) gameBoard.getIsPieces()[start]=null;

                }
            }
        }
        if(turn!=-1) {
            pieces[turn].paintPiece();
            updatePlayer(pieces[turn]);
        }
    }

    @Override
    public void isLegal() {

        List<Integer> illegals = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            illegals.add(i + 54);
            illegals.add(i + 18);

        }
        for (int i = 0; i < 5; i++) {

            illegals.add(i + 50);
            illegals.add(i + 14);

        }



        if (illegals.contains(pieces[turn].getIdCell() + step)) step += 8;

    }

    @Override
    public void updatePlayer(Piece piece){
        List <Integer> finished=new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            finished.add(32+i);

        }

        int count =0;
        if(piece.getRealId()==71){

            piece.setPieceStatus(Piece.PieceStatus.FINISHED_GAME);
            piece.setRealId(-1);
            return;
        }
        if(finished.contains(piece.getIdCell())){
            if(piece.getRealId()!=71)
                for (int i = piece.getIdCell()+1; i <= 35; i++) {
                    if(gameBoard.getIsPieces()[i]==null){
                        piece.setPieceStatus(Piece.PieceStatus.IN_GAME);
                        break;
                    }
                    else count++;
                }
        }
        if(count==(71-piece.getRealId())){
            piece.setPieceStatus(Piece.PieceStatus.FINISHED_GAME);
            piece.setRealId(-1);
        }

        for (int i = 0; i < 4; i++) {
            if(!finished.contains(pieces[i].getIdCell()))
            {
                playerStatus=PlayerStatus.PLAYING;
                return;
            }
        }
        playerStatus=PlayerStatus.FINISHED;
        GameBoard.rankGame+=1;
        this.rankPlayer=GameBoard.rankGame;
        return;

    }

}
