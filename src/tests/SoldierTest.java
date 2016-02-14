package tests;

import com.company.Components.ChessBoard;
import com.company.Components.ChessTeam;
import com.company.Components.Direction;
import org.junit.Assert;
import org.junit.Test;

import java.awt.*;

import static java.lang.Math.abs;

/**
 * Created by joona on 11/02/2016.
 */
public class SoldierTest {


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
    public void testSafeMoves_basicHasNotMoved() throws Exception {
        String expected =
                        "********\n"+
                        "********\n"+
                        "#*******\n"+
                        "#*******\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(0).getOptions()));
    }

    @Test
    public void testSafeMoves_hasNotMoved_isBlocked() throws Exception {
        team2.getQueen().moveTo(p(0,3));
        String expected =
                        "********\n"+
                        "********\n"+
                        "#*******\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(0).getOptions()));
        team2.getQueen().moveTo(p(0,2));
        expected =
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(0).getOptions()));
    }

    @Test
    public void testSafeMoves_basicHasNotMoved_canAttack() throws Exception {
        team2.getQueen().moveTo(p(1,2));
        String expected =
                "********\n"+
                        "********\n"+
                        "##******\n"+
                        "#*******\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(0).getOptions()));
    }
    @Test
    public void testSafeMoves_shouldNotBeAbleToReachWithAttack() throws Exception {
        team2.getQueen().moveTo(p(1,3));
        String expected =
                        "********\n"+
                        "********\n"+
                        "#*******\n"+
                        "#*******\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(0).getOptions()));
    }
    @Test
    public void testSafeMoves_hasMoved_shouldOnlyAttackOpponent() throws Exception {
        team1.getQueen().moveTo(p(1,3));
        team2.getSoldiers().get(0).moveTo(p(3,3));
        String expected =
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "**##****\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        team1.getSoldiers().get(2).moveTo(p(2,2));
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(2).getOptions()));
    }

    @Test
    public void testSafeMoves_shouldOnlyAttackOpponent() throws Exception {
        Point move = team1.getSoldiers().get(2).getOptions().filter(p -> abs(p.y-team1.getSoldiers().get(2).getPosition().y)==1).findFirst().get();
        team1.getSoldiers().get(2).moveTo(move);
        String expected=
                        "********\n"+
                        "********\n"+
                        "**#*****\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(2).getPosition()));
    }
    @Test
    public void testSafeMoves_shouldProtectKing() throws Exception {
        team2.getQueen().moveTo(p(3,4));
        team2.getSoldiers().get(0).moveTo(p(2,3));
        team1.getSoldiers().get(3).moveTo(p(3,2));
        String expected=
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "***#****\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(3).getOptions()));
    }
    @Test
    public void testSafeMoves_shouldNotBeAbleToMove() throws Exception {
        team1.getSoldiers().get(3).moveTo(p(3,7));
        String expected=
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        Assert.assertEquals(expected, ChessTestSuite.stringiFy(team1.getSoldiers().get(3).getOptions()));
    }
    private Point p(int x, int y){
        return new Point(x,y);
    }
}