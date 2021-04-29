package com.danial_iranpour;

import java.io.Serializable;

/**
 * Created by iWin_64bit on 5/22/2016.
 */
public class Piece implements Serializable{
    private int idCell;

    private int startNumber;

    private int realId;

    private int number=0;

    private Player player;

    private int x1=100,y1=100;

    public Piece(Player player) {
        this.number+=1;

        this.player=player;
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////pieceStatus

    public enum PieceStatus {
        FINISHED_GAME, IN_GAME, NO_STARTED_GAME
    }

    private PieceStatus pieceStatus = PieceStatus.NO_STARTED_GAME;

    public void setPieceStatus(PieceStatus pieceStatus) {
        this.pieceStatus = pieceStatus;
    }

    public PieceStatus getPieceStatus() {
        return pieceStatus;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////paint

    public void paintPiece(){

        int []a=player.gameBoard.getCellPosition(this.getPieceStatus(),getIdCell());
        this.x1=a[0];
        this.y1=a[1];

    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void setIdCell(int idCell) {
        this.idCell = idCell;
    }

    public int getIdCell() {
        return idCell;
    }

    public Player getPlayer() {
        return player;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public void setStartNumber(int startNumber) {
        this.startNumber = startNumber;
    }

    public int getStartNumber() {
        return startNumber;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }

    public int getRealId() {
        return realId;
    }
}
