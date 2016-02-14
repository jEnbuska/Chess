package com.company;

import java.awt.*;
import java.util.stream.Stream;

/**
 * Created by WinNabuska on 12.2.2016.
 */
public interface GameActor {
    Point getPosition();
    Stream<Point> getOptions();
    void moveTo(Point p);
    String getDescription();
    boolean hasTurn();
}
