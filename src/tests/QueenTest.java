package tests;

import com.company.Components.*;
import org.junit.Assert;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

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

    }

    @org.junit.Test
    public void testPossibleMoves_EmptyBoard() throws Exception {
        for(ChessPiece member : team2.getMembers().collect(toList())){
            team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers().collect(toList())){
            if(!(member instanceof QueenChessPiece))
                team2.remove(member);
        }
        for(ChessPiece member : team1.getSoldiers()){
                team2.remove(member);
        }

        for(ChessPiece member : team2.getSoldiers()){
            team1.remove(member);
        }
        //team2.getMembers().forEachOrdered(m -> team1.remove(m));
        //team2.getMembers().forEach(m -> System.out.println("x " + m.getPosition().x + " y " + m.getPosition().y));
        queen.moveTo(p(4,1));
        System.out.println(stringiFyPieces());
        System.out.println(stringiFyBoard());
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
            team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers().collect(toList())){
            if(member instanceof QueenChessPiece || member instanceof RookChessPiece)
                continue;
            else
                team2.remove(member);
        }
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
        String actual = stringiFy(queen.safeMoves());
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleMoves_TwoFoesBlocking() throws Exception {
        for(ChessPiece member : team2.getMembers().collect(toList())){
            if(member instanceof RookChessPiece)
                continue;
            else
                team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers().collect(toList())){
            if(member instanceof QueenChessPiece)
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
        String actual = stringiFy(queen.safeMoves());
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleMoves_ProtectingKing() throws Exception {
        for(ChessPiece member : team2.getMembers().collect(toList())){
            if(member instanceof RookChessPiece)
                continue;
            else
                team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers().collect(toList())){
            if(member instanceof QueenChessPiece || member instanceof KingChessPiece)
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
        String actual = stringiFy(queen.safeMoves());
        Assert.assertEquals(expected, actual);
    }

    @org.junit.Test
    public void testPossibleMoves_CheckMate() throws Exception {
        for(ChessPiece member : team2.getMembers().collect(toList())){
            if(member instanceof RookChessPiece)
                continue;
            else
                team1.remove(member);
        }
        for(ChessPiece member : team1.getMembers().collect(toList())){
            if(member instanceof QueenChessPiece || member instanceof KingChessPiece)
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
        String actual = stringiFy(queen.safeMoves());
        Assert.assertEquals(expected, actual);
    }


    private Point p(int x, int y){
        return new Point(x,y);
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

    private String stringiFyBoard(){
        String description = "";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if(board.get(j,i)!=null){
                    description+="#";
                }else{
                    description+="*";
                }
            }
            description+="\n";
        }
        return description;
    }

    private String stringiFyPieces(){
        String description="\n";
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                final int I = i, J = j;
                if(team1.getMembers().anyMatch(m -> m.getPosition().x==J && m.getPosition().y==I)){
                    description+="#";
                }else if(team2.getMembers().anyMatch(m -> m.getPosition().x==J && m.getPosition().y==I)){
                    description+="&";
                }else{
                    description+="*";
                }
            }
            description+="\n";
        }
        return description;
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