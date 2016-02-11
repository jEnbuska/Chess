package tests;
import com.company.Components.ChessBoard;
import com.company.Components.ChessTeam;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import java.awt.*;
import java.util.stream.Stream;

/**
 * Created by joona on 11/02/2016.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        QueenTest.class,
        SoldierTest.class})
public class ChessTestSuite {

    public static String stringiFy(Stream<Point> points){
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

    public static String stringiFyBoard(ChessBoard board){
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

    public static String stringiFyPieces(ChessTeam team1, ChessTeam team2){
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


    public static String stringiFy(Point p){
        String description = "";
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
