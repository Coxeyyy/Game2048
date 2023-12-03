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
        int numberOfMoves = 0;
        for(int i = 0; i<board.getWidth(); i++){
            for(int j = 0; j<board.getHeight();j++){
                int count = neighbourValue(i,j);
                if(count>0){
                    numberOfMoves += count;
                }
            }
        }
        if(numberOfMoves>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean move(Direction direction) {
        List<Integer> updatedValues;
        switch(direction) {
            case BACK:
                for(int i = 0; i<board.getHeight(); i++){
                    List<Key> keyList = board.getColumn(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    updatedValues = helper.moveAndMergeEqual(valuesList);
                    if(!valuesList.equals(updatedValues)){
                        reverse(updatedValues);
                    }
                    for(int j = 0; j<board.getHeight(); j++){
                        board.addItem(new Key(j,i),updatedValues.get(j));
                    }
                }
                return true;
            case FORWARD:
                for(int i = 0; i<board.getHeight(); i++){
                    List<Key> keyList = board.getColumn(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    updatedValues = helper.moveAndMergeEqual(valuesList);
                    for(int j = 0;j<board.getHeight();j++){
                        board.addItem(new Key(j,i),updatedValues.get(j));
                    }
                }
                return true;
            case RIGHT:
                for(int i =0; i<board.getWidth();i++){
                    List<Key> keyList = board.getRow(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    updatedValues = helper.moveAndMergeEqual(valuesList);
                    if(!valuesList.equals(updatedValues)){
                        reverse(updatedValues);
                    }
                    for(int j = 0; j<board.getWidth(); j++){
                        board.addItem(new Key(i,j),updatedValues.get(j));
                    }
                }
                return true;
            case LEFT:
                for(int i =0; i<board.getWidth();i++){
                    List<Key> keyList = board.getRow(i);
                    List<Integer> valuesList = board.getValues(keyList);
                    updatedValues = helper.moveAndMergeEqual(valuesList);
                    for(int j = 0; j<board.getWidth(); j++){
                        board.addItem(new Key(i,j),updatedValues.get(j));
                    }
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

    private int neighbourValue(int i, int j){
        var value = board.getValue(new Key(i,j));
        int count = 0;
        count += test(i,j-1,value);
        count += test(i-1,j,value);
        count += test(i+1,j,value);
        count += test(i,j+1,value);
        return count;
    }

    private int test(int i, int j, Integer value){
        try{
            if( board.getValue(new Key(i,j)) == value){
                return 1;
            }
        }catch(ArrayIndexOutOfBoundsException e){

        }
        return 0;
    }
}