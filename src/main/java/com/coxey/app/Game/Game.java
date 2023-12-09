package com.coxey.app.Game;

import com.coxey.app.Board.Board;
import com.coxey.app.Direction.Direction;
import com.coxey.app.Exception.NotEnoughSpace;

public interface Game<K, V> {
    public void init();

    public boolean canMove();

    public boolean move(Direction direction);

    public void addItem() throws NotEnoughSpace;

    public Board<K, V> getGameBoard();

    public boolean hasWin();
}