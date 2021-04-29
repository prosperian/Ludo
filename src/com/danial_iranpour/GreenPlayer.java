package com.danial_iranpour;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iWin_64bit on 6/4/2016.
 */
public class GreenPlayer extends Player {

    public GreenPlayer(Player.Color color ,LogicGameBoard gameBoard){
        this.gameBoard=gameBoard;
        this.setBackground(java.awt.Color.GREEN);
        this.setColor(color);
        //////////////////////////////////////////////////////////////////////////////////////////////////////makePieces
        for (int i = 0; i <4 ; i++) {
            pieces[i] = new Piece(this);
            pieces[i].setPieceStatus(Piece.PieceStatus.NO_STARTED_GAME);
            pieces[i].setIdCell(i);
            pieces[i].setStartNumber(i);
            pieces[i].setRealId(i);
            pieces[i].setNumber(i);

        }
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////


    }


    @Override
    public int dicePlayer() {

        dice1=gameBoard.dice();
        dice2=gameBoard.dice();


        return ((dice1+dice2)%6)+1;
    }

    @Override
    public void movePlayer() {
        if (!this.getPlayerStatus().equals(PlayerStatus.FINISHED)) {
            step = dicePlayer();
            turn = -1;

            for (int i = 0; i < 4; i++) {
                if (pieces[i].getPieceStatus().equals(Piece.PieceStatus.NO_STARTED_GAME)) {
                    if (dice1 == 6 || dice2 == 6) {
                        turn = i;
                        break;
                    }
                }
            }
            if(turn!=-1) {
                if (pieces[turn].getIdCell() <= 3) {
                    pieces[turn].setIdCell(4);
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


            int max = -2;

            if (turn == -1) {
                for (int i = 0; i < 4; i++) {
                    if (pieces[i].getRealId() > max) {
                        if (pieces[i].getPieceStatus().equals(Piece.PieceStatus.IN_GAME)) {
                            max = pieces[i].getRealId();
                            turn = i;
                        }
                        else max=-1;
                    }

                }

            }

            if(turn!=-1) turn=moveIn(turn);

            if (turn != -1) {

                isLegal();

                howIsIt = gameBoard.move(pieces[turn], pieces[turn].getIdCell(), step);

                if (howIsIt) {

                    pieces[turn].setIdCell(step + pieces[turn].getIdCell());
                    pieces[turn].setRealId(step + pieces[turn].getRealId());
                }
            }
        }
        if(turn!=-1) {
            pieces[turn].paintPiece();
            updatePlayer(pieces[turn]);
        }
    }

/////////////////////////////////////////////////////////////legal
    @Override
    public void isLegal() {

        List<Integer> illegals = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            illegals.add(i + 18);
            illegals.add(i + 36);
            illegals.add(i + 54);

        }
        for (int i = 0; i < 5; i++) {

            illegals.add(i + 14);
            illegals.add(i + 32);
            illegals.add(i + 50);

        }

        if (illegals.contains(pieces[turn].getIdCell() + step)) step += 8;


    }
    @Override
    public void updatePlayer(Piece piece) {

        List<Integer> finished = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            finished.add(68 + i);
        }


        int count = 0;

        if(piece.getIdCell()==71) piece.setPieceStatus(Piece.PieceStatus.FINISHED_GAME);

        if (finished.contains(piece.getIdCell())) {
            if(piece.getIdCell()!=71)
            for (int i = piece.getIdCell()+1 ; i <= 71; i++) {
                if (gameBoard.getIsPieces()[i] == null) {
                    piece.setPieceStatus(Piece.PieceStatus.IN_GAME);
                    break;
                } else count+=1;
            }
        }
        if (count == 71 - piece.getIdCell()) {
            piece.setPieceStatus(Piece.PieceStatus.FINISHED_GAME);
            piece.setRealId(-2);
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
