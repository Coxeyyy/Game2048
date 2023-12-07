package com.coxey.app.Game;

import com.coxey.app.Board.Board;
import com.coxey.app.Direction.Direction;

public interface Game<K, V> {
    public void init();

    public boolean canMove();

    public boolean move(Direction direction);

    public void addItem();

    public Board<K, V> getGameBoard();

    public boolean hasWin();
}