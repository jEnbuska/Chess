package tests;

import com.company.Components.*;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;
import java.util.List;

/**
 * Created by joona on 10/02/2016.
 */
public class QueenTest {

    private ChessBoard board;
    private ChessTeam team1, team2;
    private ChessPiece queen;

    @org.junit.Before
    public void setUp() throws Exception {

        board = new ChessBoard();
        team1 = new ChessTeam(board, Direction.SOUTH);
        team2 = new ChessTeam(board, Direction.NORTH);
        team1.setOpponent(team2);
        team2.setOpponent(team1);
        queen = team1.getQueen();
        team1.setTurn(true);
        team2.setTurn(false);

    }

    @org.junit.Test
    public void testPossibleMoves_EmptyBoard() throws Exception {
        for(ChessPiece member : team2.getMembers()){
            team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers()){
            if(!(member instanceof Queen))
                team2.remove(member);
        }
        //team2.getMembers().forEachOrdered(m -> team1.remove(m));
        //team2.getMembers().forEach(m -> System.out.println("x " + m.getPosition().x + " y " + m.getPosition().y));
        queen.moveTo(p(4,1));
        //System.out.println(ChessTestSuite.stringiFyPieces(team1, team2));
        //System.out.println(ChessTestSuite.stringiFyBoard(board));
        String expected =
                "***###**\n"+
                "####*###\n"+
                "***###**\n"+
                "**#*#*#*\n"+
                "*#**#**#\n"+
                "#***#***\n"+
                "****#***\n"+
                "****#***\n";
        String actual = ChessTestSuite.stringiFy(queen.getOptions());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPossibleMoves_TwoFriendBlocking() throws Exception {
        for(ChessPiece member : team2.getMembers()){
            team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers()){
            if(member instanceof Queen || member instanceof Rook)
                continue;
            else
                team2.remove(member);
        }
        team1.setTurn(true);
        team2.setTurn(false);
        team1.getQueen().moveTo(p(4,1));
        List<ChessPiece> rooks = team1.getRooks();
        rooks.get(0).moveTo(p(4,4));
        rooks.get(1).moveTo(p(0,5));
        String expected =
                        "***###**\n"+
                        "####*###\n"+
                        "***###**\n"+
                        "**#*#*#*\n"+
                        "*#*****#\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        String actual = ChessTestSuite.stringiFy(queen.getOptions());
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleMoves_TwoFoesBlocking() throws Exception {
        for(ChessPiece member : team2.getMembers()){
            if(member instanceof Rook)
                continue;
            else
                team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers()){
            if(member instanceof Queen)
                continue;
            else
                team2.remove(member);
        }
        team1.getQueen().moveTo(p(4,1));
        List<ChessPiece> rooks = team2.getRooks();
        rooks.get(0).moveTo(p(4,3));
        rooks.get(1).moveTo(p(1,4));
        String expected =
                        "***###**\n"+
                        "####*###\n"+
                        "***###**\n"+
                        "**#*#*#*\n"+
                        "*#*****#\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        String actual = ChessTestSuite.stringiFy(queen.getOptions());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testPossibleMoves_ProtectingKing() throws Exception {
        for(ChessPiece member : team2.getMembers()){
            if(member instanceof Rook)
                continue;
            else
                team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers()){
            if(member instanceof Queen || member instanceof King)
                continue;
            else
                team2.remove(member);
        }
        team1.getQueen().moveTo(p(4,1));
        team1.getKing().moveTo(p(4,0));
        List<ChessPiece> rooks = team2.getRooks();
        rooks.get(0).moveTo(p(4,3));
        rooks.get(1).moveTo(p(1,4));
        String expected =
                        "********\n"+
                        "********\n"+
                        "****#***\n"+
                        "****#***\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        String actual = ChessTestSuite.stringiFy(queen.getOptions());
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleMoves_CheckMate() throws Exception {
        for(ChessPiece member : team2.getMembers()){
            if(member instanceof Rook)
                continue;
            else
                team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers()){
            if(member instanceof Queen || member instanceof King)
                continue;
            else
                team2.remove(member);
        }
        team1.getQueen().moveTo(p(4,1));
        team1.getKing().moveTo(p(4,0));
        List<ChessPiece> rooks = team2.getRooks();
        rooks.get(0).moveTo(p(4,3));
        rooks.get(1).moveTo(p(1,0));
        String expected =
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        String actual = ChessTestSuite.stringiFy(queen.getOptions());
        Assert.assertEquals(expected, actual);
    }


    private Point p(int x, int y){
        return new Point(x,y);
    }


}