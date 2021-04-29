package com.danial_iranpour;

import javax.swing.*;
import java.io.Serializable;

/**
 * Created by iWin_64bit on 5/22/2016.
 */
public abstract class Player extends JPanel implements Serializable {

    protected int step=0;

    protected int turn=-1;

    protected int dice1, dice2;

    protected   boolean howIsIt ;

    protected int rankPlayer;

    protected Piece[] pieces = new Piece[4];

    protected LogicGameBoard gameBoard;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////color

    protected enum Color{
        GREEN,RED,BLUE,YELLOW
    }

    private Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////playerStatus

    protected enum PlayerStatus{
        FINISHED,PLAYING
    }

    protected PlayerStatus playerStatus;

    public void setPlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public abstract int dicePlayer();

    public abstract void movePlayer();

    public abstract void updatePlayer(Piece piece);

    public abstract void isLegal();

    public int moveIn(int turn){

        if (pieces[turn].getRealId() + step > 71) {
            for (int i = 0; i < 4; i++) {
                if (pieces[i].getRealId() + step <= 71) {
                    if (pieces[i].getPieceStatus().equals(Piece.PieceStatus.IN_GAME)) {
                        if (i != turn) {
                            turn = i;
                            break;
                        }
                    }
                } else turn=-1;
            }
        }
        return turn;
    }

}
