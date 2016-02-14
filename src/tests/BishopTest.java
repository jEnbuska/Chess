package tests;

import com.company.Components.ChessBoard;
import com.company.Components.ChessPiece;
import com.company.Components.ChessTeam;
import com.company.Components.Direction;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created by joonaen on 12.2.2016.
 */
public class BishopTest {

    private ChessBoard board;
    private ChessTeam team1, team2;

    @org.junit.Before
    public void setUp() throws Exception {
        abs(-1);
        board = new ChessBoard();
        team1 = new ChessTeam(board, Direction.SOUTH);
        team2 = new ChessTeam(board, Direction.NORTH);
        team1.setOpponent(team2);
        team2.setOpponent(team1);
        team1.setTurn(true);
        team2.setTurn(false);
    }

    @Test
    public void testSafeMoves_BasicMovement() throws Exception {

        ChessPiece bishop = team1.getBishops().get(1);
        bishop.moveTo(p(3,3));
        String expected =
                        "********\n"+
                        "********\n"+
                        "**#*#***\n"+
                        "********\n"+
                        "**#*#***\n"+
                        "*#***#**\n"+
                        "#*****#*\n"+
                        "********\n";
        System.out.println(ChessTestSuite.stringiFy(bishop.getOptions()));
        String actual = ChessTestSuite.stringiFy(bishop.getOptions());
        Assert.assertEquals(expected,actual);

        for(ChessPiece member : team2.getMembers()){
            team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers()){
            team2.remove(member);
        }
        bishop.moveTo(p(3,3));
        expected =
                        "#*****#*\n"+
                        "*#***#**\n"+
                        "**#*#***\n"+
                        "********\n"+
                        "**#*#***\n"+
                        "*#***#**\n"+
                        "#*****#*\n"+
                        "*******#\n";
        System.out.println(ChessTestSuite.stringiFy(bishop.getOptions()));
        actual = ChessTestSuite.stringiFy(bishop.getOptions());
        Assert.assertEquals(expected,actual);
    }

    private Point p(int x, int y){
        return new Point(x,y);
    }
}