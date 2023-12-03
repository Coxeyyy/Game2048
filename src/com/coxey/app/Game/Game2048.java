package com.coxey.app.Game;

import com.coxey.app.Board.Board;
import com.coxey.app.Direction.Direction;
import com.coxey.app.Key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;
import static java.util.Collections.reverse;

public class Game2048 implements Game{
    private GameHelper helper = new GameHelper();
    private Board board;
    private Random random = new Random();
    public Game2048(Board board){
        this.board = board;
    }

    @Override
    public void init() {
        board.fillBoard(new ArrayList<>(asList(2,2,null,2)));
    }

    @Override
    public boolean canMove() {
        return !board.availableSpace().isEmpty();
    }

    @Override
    public boolean move(Direction direction) {
        switch(direction) {
            case BACK:
                for(int i = 0; i<board.getHeight(); i++){
                    List<Key> keyList = board.getColumn(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    List<Integer> movingList = helper.moveAndMergeEqual(valuesList);
                    reverse(movingList);
                }
                return true;
            case FORWARD:
                for(int i = 0; i<board.getHeight(); i++){
                    List<Key> keyList = board.getColumn(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    helper.moveAndMergeEqual(valuesList);
                }
                return true;
            case RIGHT:
                for(int i =0; i<board.getWidth();i++){
                    List<Key> keyList = board.getRow(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    List<Integer> movingList = helper.moveAndMergeEqual(valuesList);
                    reverse(movingList);
                }
                return true;
            case LEFT:
                for(int i =0; i<board.getWidth();i++){
                    List<Key> keyList = board.getRow(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    helper.moveAndMergeEqual(valuesList);
                }
                return true;
            default:
                return false;
        }
    }

    @Override
    public void addItem() {
        List<Key> nullKeys = board.availableSpace();
        int value = random.nextInt(10) + 1;
        if(value == 10){
            board.addItem(nullKeys.get(0), 4);
        }
        board.addItem(nullKeys.get(0), 2);
    }

    @Override
    public Board getGameBoard() {
        return board;
    }

    @Override
    public boolean hasWin() {
        return board.hasValue(2048);
    }
}