package com.company;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by WinNabuska on 10.2.2016.
 */
public class ChessTeam {

    private KingChessPiece king;
    private QueenChessPiece queen;
    private RookChessPiece rook1;
    private RookChessPiece rook2;
    private ChessBoard board;
    private ChessTeam opponent;
    private final Direction HEADING;
    private Set<ChessPiece> members;

    public ChessTeam(ChessBoard board, Direction heading){
        HEADING = heading;
        int backRow, frontRow, queensColumn, kingColumn;
        if(HEADING==Direction.NORTH){
            backRow=7;
            backRow=6;
            queensColumn=3;
            kingColumn=4;
        } else if(HEADING == Direction.SOUTH){
            backRow=0;
            frontRow=1;
            queensColumn=4;
            kingColumn=3;
        } else{
            throw new Error("ChessTeam constructor should receive NORTH or SOUTH as its heading parameter");
        }
        members = new HashSet<>(Arrays.asList(
                king = new KingChessPiece(ChessBoard.coord(kingColumn, backRow), this, board),
                queen = new QueenChessPiece(ChessBoard.coord(queensColumn, backRow), this, board),
                rook1=new RookChessPiece(ChessBoard.coord(0, backRow),this,board),
                rook2=new RookChessPiece(ChessBoard.coord(7, backRow), this, board)
        ));
        this.board=board;
    }

    public Direction getHeading(){
        return HEADING;
    }

    public void setOpponent(ChessTeam opponent){
        this.opponent=opponent;
    }

    public ChessTeam getOpponent(){
        return opponent;
    }

    public ChessPiece getKing(){
        return king;
    }

    public ChessPiece getQueen(){
        return queen;
    }

    public boolean kingIsThreatened(){
        return opponent.getMembers()
                .anyMatch(foe -> foe.possibleMoves()
                        .anyMatch(coord -> coord.x==king.position.x && coord.y==king.position.y));
    }

    public List<ChessPiece> getSoldiers(){
        throw new NotImplementedException();
    }

    public List<RookChessPiece> getRooks(){//Tornit
        return Arrays.asList(rook1,rook2);
    }
    public List<ChessPiece> getBishops(){//piispat
        throw new NotImplementedException();
    }

    public List<ChessPiece> getKnights(){//hevoset
        throw new NotImplementedException();
    }

    public Stream<ChessPiece> getMembers() {
        return members.stream();
    }

    public void eliminate(ChessPiece enemy){
        if(members.stream().anyMatch(f -> f == enemy)){
            throw new Error("ones teams should now destroy its team member as enemy");
        }
        board.clear(enemy.position);
        if(HEADING == Direction.NORTH){
            enemy.moveTo(ChessBoard.coord(1000,-1));
        }else{//SOUTH{
            enemy.moveTo(ChessBoard.coord(-1000, 8));
        }
        opponent.members.remove(enemy);
    }
}
