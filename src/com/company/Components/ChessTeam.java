package com.company.Components;

import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

/**
 * Created by WinNabuska on 10.2.2016.
 */
public class ChessTeam {
    private ChessPiece king, queen;
    private List<ChessPiece> rooks, bishops, soldiers, knights;
    private ChessBoard board;
    private ChessTeam opponent;
    private final Direction HEADING;
    private Set<ChessPiece> members;
    private volatile boolean ourTurn;
    private String image_description;

    public ChessTeam(ChessBoard board, Direction heading){
        ourTurn=false;
        this.board=board;
        HEADING = heading;
        int backRow, frontRow, queensColumn, kingColumn;
        if(HEADING==Direction.NORTH){
            backRow=7;
            frontRow=6;
            queensColumn=3;
            kingColumn=4;
            image_description="black_";
        } else if(HEADING == Direction.SOUTH){
            backRow=0;
            frontRow=1;
            queensColumn=4;
            kingColumn=3;
            image_description="white_";
        } else{
            throw new Error("ChessTeam constructor should receive NORTH or SOUTH as its heading parameter");
        }
        members = new HashSet<>();
        members.add(king = new King(ChessBoard.coord(kingColumn, backRow), this, board));
        members.add(queen = new Queen(ChessBoard.coord(queensColumn, backRow), this, board));
        rooks = Arrays.asList(
                new Rook(ChessBoard.coord(0, backRow),this,board),
                new Rook(ChessBoard.coord(7, backRow), this, board)
        );
        members.addAll(rooks);
        bishops = Arrays.asList(
                new Bishop(ChessBoard.coord(2, backRow), this, board),
                new Bishop(ChessBoard.coord(5, backRow), this, board)
        );

        knights = Arrays.asList(
                new Knight(ChessBoard.coord(1, backRow), this, board),
                new Knight(ChessBoard.coord(6, backRow), this, board)
        );
        members.addAll(knights);
        members.addAll(bishops);
        soldiers = IntStream.range(0,8).boxed().map(x ->
                new Soldier(ChessBoard.coord(x, frontRow), this, board))
                .collect(toList());
        members.addAll(soldiers);
    }

    public void setTurn(boolean turn){
        ourTurn=turn;
    }
    public String getDescription(){
        return image_description;
    }

    public boolean hasTurn(){
        return ourTurn;
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
        return opponent.getMembers().stream()
                .anyMatch(foe -> foe.getMovementRange()
                        .anyMatch(coord -> coord.x==king.position.x && coord.y==king.position.y));
    }

    public List<ChessPiece> getSoldiers(){
        return soldiers;
    }

    public List<ChessPiece> getRooks(){//Tornit
        return rooks;
    }
    public List<ChessPiece> getBishops(){//piispat
        return bishops;
    }

    public List<ChessPiece> getKnights(){//hevoset
        return knights;
    }

    public Set<ChessPiece> getMembers() {
        return members;
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
    }

    /*public void resurrect(ChessPiece teamMember, Point position){
        if(teamMember.team!=this) {
            throw new Error("only ones tean should resurrect a chesspiece");
        }
        chessPiece.setPosition(position);
        board.set(position, teamMember);
    }*/
}
