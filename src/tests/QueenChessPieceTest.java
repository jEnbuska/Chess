package tests;

import com.company.*;
import org.junit.Assert;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by joona on 10/02/2016.
 */
public class QueenChessPieceTest {

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
    }

    @org.junit.Test
    public void testPossibleMoves_EmptyBoard() throws Exception {
        for(ChessPiece member : team2.getMembers().collect(toList())){
            team1.eliminate(member);
        }
        for(ChessPiece member : team1.getMembers().collect(toList())){
            if(!(member instanceof QueenChessPiece))
                team2.eliminate(member);
        }
        //team2.getMembers().forEachOrdered(m -> team1.eliminate(m));
        queen.moveTo(p(4,1));
        String expected =
                "***###**\n"+
                "####*###\n"+
                "***###**\n"+
                "**#*#*#*\n"+
                "*#**#**#\n"+
                "#***#***\n"+
                "****#***\n"+
                "****#***\n";
        String actual = stringiFy(queen.safeMoves());
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleMoves_TwoFriendBlocking() throws Exception {
        for(ChessPiece member : team2.getMembers().collect(toList())){
            team1.eliminate(member);
        }
        for(ChessPiece member : team1.getMembers().collect(toList())){
            if(member instanceof QueenChessPiece || member instanceof RookChessPiece)
                continue;
            else
                team2.eliminate(member);
        }
        team1.getQueen().moveTo(p(4,1));
        List<RookChessPiece> rooks = team1.getRooks();
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
        String actual = stringiFy(queen.safeMoves());
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleMoves_TwoFoesBlocking() throws Exception {
        /*int x = 4, y = 1;
        QueenChessPiece queen = new QueenChessPiece(board, p(x,y), king,friends,foes);
        foes.add(new QueenChessPiece(board, p(4,4), king,foes,friends));
        foes.add(new QueenChessPiece(board, p(1,4), king,foes,friends));
        String expected =
                "***###**\n"+
                        "####*###\n"+
                        "***###**\n"+
                        "**#*#*#*\n"+
                        "*#**#**#\n"+
                        "********\n"+
                        "********\n"+
                        "********\n";
        String actual = stringiFy(queen.possibleMoves());
        Assert.assertEquals(expected, actual);*/
    }

    private String stringiFy(Stream<Point> points){
        boolean [][] grid = new boolean[8][8];
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                grid[i][j]=false;
        points.forEach(p -> grid[p.x][p.y]=true);
        String description = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(grid[j][i]){
                    description+="#";
                }else{
                    description+="*";
                }
            }
            description+="\n";
        }
        return description;
    }

    private Point p(int x, int y){
        return new Point(x,y);
    }
    private String stringiFy(Point p){
        String description = "\n";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(p.x==j && p.y==i)
                    description+="#";
                else
                    description+="*";
            }
            description+="\n";
        }
        return description;
    }
}