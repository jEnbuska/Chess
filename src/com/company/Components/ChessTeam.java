package com.company.Components;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by WinNabuska on 10.2.2016.
 */
public class ChessTeam {

    private ChessPiece king;
    private ChessPiece queen;
    private List<ChessPiece> rooks;
    private List<ChessPiece> soldiers;
    private ChessBoard board;
    private ChessTeam opponent;
    private final Direction HEADING;
    private Collection<ChessPiece> members;

    public ChessTeam(ChessBoard board, Direction heading){
        HEADING = heading;
        int backRow, frontRow, queensColumn, kingColumn;
        if(HEADING==Direction.NORTH){
            backRow=7;
            frontRow=6;
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
        members = new HashSet<>();
        members.add(king = new KingChessPiece(ChessBoard.coord(kingColumn, backRow), this, board));
        members.add(queen = new QueenChessPiece(ChessBoard.coord(queensColumn, backRow), this, board));
        rooks = Arrays.asList(
                new RookChessPiece(ChessBoard.coord(0, backRow),this,board),
                new RookChessPiece(ChessBoard.coord(7, backRow), this, board)
        );
        members.addAll(rooks);
        soldiers = IntStream.range(0,8).boxed().map(x ->
                new SoldierChessPiece(ChessBoard.coord(x, frontRow), this, board))
                .collect(toList());
        members.addAll(soldiers);
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
        return soldiers;
    }

    public List<ChessPiece> getRooks(){//Tornit
        return rooks;
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

    public void remove(ChessPiece enemy){
        if(members.stream().anyMatch(f -> f == enemy)){
            throw new Error("ones teams should now destroy its team member as enemy");
        }
        board.clear(enemy.position);
        if(HEADING == Direction.NORTH){
            enemy.setPosition(ChessBoard.coord(1000,-1));
        }else{//SOUTH{
            enemy.setPosition(ChessBoard.coord(-1000, 8));
        }
        //opponent.members.remove(enemy);
    }
}
