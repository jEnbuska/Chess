package com.company;

import java.awt.*;
import java.io.File;
import java.util.Map;

/**
 * Created by WinNabuska on 14.2.2016.
 */
public interface GameView2D {
    void setChessPieceImages(Map<Point, File> images);
    void setController(GameController controller);
    void showPlayerOptions(java.util.List<Point> areas);
    void update();
}
