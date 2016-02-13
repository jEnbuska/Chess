package com.company.Components;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by joonaen on 12.2.2016.
 */
public class Knight extends ChessPiece{

    public Knight(Point initialPosition, ChessTeam team, ChessBoard board) {
        super(initialPosition, team, board);
    }

    @Override
    protected Stream<Point> getMovementRange() {
        int [] verticalSteps = {1,-1};
        int [] horizontalSteps = {2,-2};
        Set<Point> steps = new HashSet<>();
        for (int i = 0; i < 2; i++) {
            for (int y = 0; y < verticalSteps.length; y++) {
                for (int x = 0; x < horizontalSteps.length; x++) {
                    Point location = ChessBoard.coord(position.x + verticalSteps[x], position.y + horizontalSteps[y]);
                    if(ChessBoard.validLocation(location)){
                        if(isEmpty.or(hasFoe).test(location)){
                            steps.add(location);
                        }
                    }
                }
            }
            if(i==0){
                int [] placeHolder = verticalSteps;
                verticalSteps=horizontalSteps;
                horizontalSteps=placeHolder;
            }
        }
        return steps.stream();
    }



    @Override
    public String getDescription() {
        return team.getDescription() + "knight";
    }
}
