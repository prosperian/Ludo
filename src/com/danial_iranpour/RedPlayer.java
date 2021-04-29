package com.danial_iranpour;

import java.awt.event.FocusAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by iWin_64bit on 6/4/2016.
 */
public class RedPlayer extends Player  {

    public RedPlayer(Color color,LogicGameBoard gameBoard){
        this.gameBoard=gameBoard;
        this.setPlayerStatus(Player.PlayerStatus.PLAYING);
        this.setBackground(java.awt.Color.RED);
        this.setColor(color);
        for (int i = 0; i <4 ; i++) {
            pieces[i]=new Piece(this);
            pieces[i].setPieceStatus(Piece.PieceStatus.NO_STARTED_GAME);
            pieces[i].setNumber(i);
            pieces[i].setRealId(i);
            pieces[i].setStartNumber(18+i);
            pieces[i].setIdCell(18+i);
        }

    }

    @Override
    public int dicePlayer() {
        dice1=gameBoard.dice();
        dice2=gameBoard.dice();


        return ((dice1*dice2)%6)+1;
    }

    int turn;
    @Override
    public void movePlayer() {
          if(!this.getPlayerStatus().equals(PlayerStatus.FINISHED)) {
              step = dicePlayer();
              turn = -1;

              for (int i = 0; i < 4; i++) {
                  if(pieces[i].getIdCell() + step<=71) {
                      if (gameBoard.getIsPieces()[pieces[i].getIdCell() + step] != null)
                          if (!gameBoard.getIsPieces()[pieces[i].getIdCell() + step].getPlayer().equals(this)) {
                              if (pieces[i].getPieceStatus().equals(Piece.PieceStatus.IN_GAME)) {
                                  turn = i;
                                  break;
                              }
                          } else turn = -1;
                  }else turn=-1;
              }


              if (turn == -1) {
                  for (int i = 0; i < 4; i++) {
                      if (pieces[i].getPieceStatus().equals(Piece.PieceStatus.NO_STARTED_GAME)) {
                          if (dice1 == 6 || dice2 == 6) {
                              turn = i;
                              break;
                          }
                      }
                  }
              }
              if(turn!=-1) {
                  if (pieces[turn].getIdCell() <= 21 && pieces[turn].getIdCell() >= 18) {
                      if(dice1==6||dice2==6) {
                          pieces[turn].setIdCell(22);
                          pieces[turn].setRealId(4);
                          pieces[turn].setPieceStatus(Piece.PieceStatus.IN_GAME);
                          howIsIt = gameBoard.move(pieces[turn], pieces[turn].getIdCell(), 0);
                          if (howIsIt) {
                              pieces[turn].paintPiece();
                              updatePlayer(pieces[turn]);
                              return;
                          } else {
                              pieces[turn].setIdCell(pieces[turn].getStartNumber());
                              pieces[turn].setRealId(pieces[turn].getStartNumber());
                              pieces[turn].setPieceStatus(Piece.PieceStatus.NO_STARTED_GAME);
                              turn = -1;
                          }
                      }
                  }
              }







              if (turn == -1) {
                  int max = -1;
                  for (int i = 0; i < 4; i++) {
                      if (pieces[i].getRealId() > max) {
                          if(pieces[i].getPieceStatus().equals(Piece.PieceStatus.IN_GAME)) {
                              max = pieces[i].getRealId();
                              turn = i;
                              break;
                          }
                      }
                      else max=-1;
                  }
              }


              if(turn!=-1) turn=moveIn(turn);


              if (turn != -1) {


                  int start=-1;
                  if(pieces[turn].getIdCell()==67)
                  {
                      start=pieces[turn].getIdCell();
                      pieces[turn].setIdCell(3);
                      pieces[turn].setRealId(57);
                  }
                  if(pieces[turn].getIdCell()+step<=71&&pieces[turn].getIdCell()+step>=68){
                      start=pieces[turn].getIdCell();
                      pieces[turn].setIdCell(2);
                      pieces[turn].setRealId(56);
                  }




                  isLegal();

                  howIsIt = gameBoard.move(pieces[turn], pieces[turn].getIdCell(), step);
                  if (howIsIt) {

                      pieces[turn].setRealId(step + pieces[turn].getRealId());
                      pieces[turn].setIdCell(step + pieces[turn].getIdCell());
                      if(start!=-1) gameBoard.getIsPieces()[start]=null;

                  }

              }
          }
        if(turn!=-1) {
            pieces[turn].paintPiece();
            updatePlayer(pieces[turn]);
        }


    }
//////////////////////////////////////////////////////////////////////////////////isLegal
    @Override
    public void isLegal() {

        List<Integer> illegals = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            illegals.add(i + 36);
            illegals.add(i + 54);
        }
        for (int i = 0; i < 5; i++) {
            illegals.add(i + 32);
            illegals.add(i + 50);
        }



        if (illegals.contains(pieces[turn].getIdCell() + step)) step += 8;


    }

    @Override
    public void updatePlayer(Piece piece){
        List <Integer> finished=new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            finished.add(14+i);

        }

        int count =0;
        if(piece.getRealId()==71){

            piece.setPieceStatus(Piece.PieceStatus.FINISHED_GAME);
            piece.setRealId(-1);
            return;
        }
        if(finished.contains(piece.getIdCell())){
            if(piece.getRealId()!=71)
            for (int i = piece.getIdCell()+1; i <= 17; i++) {
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
