package com.coxey.app.Game;

import com.coxey.app.Board.Board;
import com.coxey.app.Direction.Direction;
import java.util.Random;
public class Game2048 implements Game{
    private GameHelper helper = new GameHelper();
    private Board board;
    private Random random = new Random();
    public Game2048(Board board){
        this.board = board;
    }
    @Override
    public void init() {}
    @Override
    public boolean canMove() {
        return false;
    }
    @Override
    public boolean move(Direction direction) {
        return false;
    }
    @Override
    public void addItem() {}
    @Override
    public Board getGameBoard() {
        return null;
    }
    @Override
    public boolean hasWin() {
        return false;
    }
}