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
        if(board.hasValue(null)){
            return true;
        }
        for(int i = 0; i<board.getWidth(); i++){
            for(int j = 0; j<board.getHeight();j++){
                int countIdenticalNeighbors = numberOfIdenticalNeighbors(i,j);
                if(countIdenticalNeighbors>0){
                    numberOfMoves += countIdenticalNeighbors;
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
        if(!canMove()){
            return false;
        }
        switch(direction) {
            case BACK:
                return moveBack();
            case FORWARD:
                return moveForward();
            case RIGHT:
                return moveRight();
            case LEFT:
                return moveLeft();
            default:
                return false;
        }
    }

    @Override
    public void addItem() {
        List<Key> nullKeys = board.availableSpace();
        try{
            int value = random.nextInt(10) + 1;
            int setValue = random.nextInt(nullKeys.size());
            if(value == 10){
                board.addItem(nullKeys.get(setValue), 4);
            }
            board.addItem(nullKeys.get(setValue), 2);
        }catch(ArrayIndexOutOfBoundsException e){

        }
    }

    @Override
    public Board getGameBoard() {
        return board;
    }

    @Override
    public boolean hasWin() {
        return board.hasValue(2048);
    }

    private int numberOfIdenticalNeighbors(int i, int j){
        var value = board.getValue(new Key(i,j));
        int count = 0;
        count += checkNeighbourValue(i,j-1,value);
        count += checkNeighbourValue(i-1,j,value);
        count += checkNeighbourValue(i+1,j,value);
        count += checkNeighbourValue(i,j+1,value);
        return count;
    }

    private int checkNeighbourValue(int i, int j, Integer value){
        try{
            if( board.getValue(new Key(i,j)) == value){
                return 1;
            }
        }catch(ArrayIndexOutOfBoundsException e){

        }
        return 0;
    }

    private boolean moveBack(){
        List<Integer> updatedValues;
        for(int i = 0; i<board.getHeight(); i++){
            List<Integer> valuesList = board.getValues(board.getColumn(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            updatedValues = reverseListForBackAndRightMove(updatedValues);
            for(int j = 0; j<board.getHeight(); j++){
                board.addItem(new Key(j,i),updatedValues.get(j));
            }
        }
        return true;
    }

    private boolean moveForward(){
        List<Integer> updatedValues;
        for(int i = 0; i<board.getHeight(); i++){
            List<Integer> valuesList = board.getValues(board.getColumn(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            for(int j = 0;j<board.getHeight();j++){
                board.addItem(new Key(j,i),updatedValues.get(j));
            }
        }
        return true;
    }

    private boolean moveRight(){
        List<Integer> updatedValues;
        for(int i =0; i<board.getWidth();i++){
            List<Integer> valuesList = board.getValues(board.getRow(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            updatedValues = reverseListForBackAndRightMove(updatedValues);
            for(int j = 0; j<board.getWidth(); j++){
                board.addItem(new Key(i,j),updatedValues.get(j));
            }
        }
        return true;
    }

    private boolean moveLeft(){
        List<Integer> updatedValues;
        for(int i =0; i<board.getWidth();i++){
            List<Integer> valuesList = board.getValues(board.getRow(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            for(int j = 0; j<board.getWidth(); j++){
                board.addItem(new Key(i,j),updatedValues.get(j));
            }
        }
        return true;
    }

    private List<Integer> reverseListForBackAndRightMove(List<Integer> list){
        List<Integer> returnList = new ArrayList<>();
        var count = 0;
        for(Integer value : list){
            if(value == null){
                count++;
            }
        }
        for(int i = 0; i<count; i++){
            returnList.add(null);
        }
        count = list.size() - count;
        for(int i = 0; i<count; i++){
            returnList.add(list.get(i));
        }
        return returnList;
    }
}