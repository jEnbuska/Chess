package com.company;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static java.lang.Math.abs;
import static java.util.stream.Collectors.toList;

/**
 * Created by WinNabuska on 10.2.2016.
 */
public class SoldierChessPiece extends ChessPiece {

    private boolean hasMoved;

    public SoldierChessPiece(Point initialPosition, ChessTeam team, ChessBoard board) {
        super(initialPosition, team, board);
        hasMoved=false;
    }

    @Override
    protected Stream<Point> possibleMoves() {
        Point firstStep = position.getLocation();
        oneStepMoves.get(team.getHeading()).accept(firstStep);
        Set<Point> moves = new HashSet<>();
        if(ChessBoard.validLocation(firstStep) && isEmpty.or(hasFoe).test(firstStep)){
            moves.add(firstStep);
            if(!hasMoved && hasFoe.negate().test(firstStep)){
                hasMoved=true;
                Point secondStep = firstStep.getLocation();
                if(ChessBoard.validLocation(secondStep) && isEmpty.or(hasFoe).test(secondStep)){
                    oneStepMoves.get(team.getHeading()).accept(secondStep);
                }
            }
        }
        return moves.stream();
    }

    @Override
    public Stream<Point> safeMoves() {
        List<Point> steps = possibleMoves().collect(toList());
        Optional<Point> firstStep = steps.stream().filter(p -> abs(p.y-position.y)==1).findFirst();
        Stream<Point> safeSteps;
        if(firstStep.isPresent() && kingStaysSafe.test(firstStep.get())){
            steps.remove(firstStep.get());
            if(steps.size()>0 && kingStaysSafe.test(steps.get(0))){
                safeSteps = Stream.of(firstStep.get(), steps.get(0));
            } else{
                safeSteps = Stream.of(firstStep.get());
            }
        }else {
            safeSteps = Stream.empty();
        }
        return safeSteps;
    }
}
