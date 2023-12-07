package com.coxey.app.Game;

import com.coxey.app.Board.Board;
import com.coxey.app.Board.SquareBoard;
import com.coxey.app.Direction.Direction;
import com.coxey.app.Key.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game2048 implements Game<Key, Integer> {
    private GameHelper helper = new GameHelper();
    private Random random = new Random();
    public static final int GAME_SIZE = 4;
    private final Board <Key, Integer> board = new SquareBoard<>(GAME_SIZE);

    /** Метод инициализирует начало игры */
    @Override
    public void init() {
        board.clearBoard();
        for(int i = 0; i < 2; i++) {
            addItem();
        }
    }

    /** Метод проверяет, можем ли мы делать игровой ход. */
    @Override
    public boolean canMove() {
        int optionsMoveNumber = 0;
        if(board.hasValue(null)) {
            return true;
        }
        for(int i = 0; i < board.getWidth(); i++) {
            for(int j = 0; j < board.getHeight(); j++) {
                int counterIdenticalNeighbors = identicalNeighborsNumber(i,j);
                if(counterIdenticalNeighbors > 0) {
                    optionsMoveNumber += counterIdenticalNeighbors;
                }
            }
        }
        if(optionsMoveNumber > 0){
            return true;
        }
        return false;
    }

    /** Метод делает игровой ход в направлении {@param direction}. */
    @Override
    public boolean move(Direction direction) {
        if(!canMove()) {
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

    /** Добавляет новый элемент в игру. */
    @Override
    public void addItem() {
        List<Key> nullKeys = board.availableSpace();
        try {
            int value = random.nextInt(10) + 1;
            int settingValue = random.nextInt(nullKeys.size());
            if(value == 10) {
                board.addItem(nullKeys.get(settingValue), 4);
            }
            board.addItem(nullKeys.get(settingValue), 2);
        } catch(ArrayIndexOutOfBoundsException e) {

        }
    }

    /** Получение игрового поля. */
    @Override
    public Board getGameBoard() {
        return board;
    }

    /** Случилось ли событие победы в игре. */
    @Override
    public boolean hasWin() {
        return board.hasValue(2048);
    }

    /** Метод считает количество валидных соседей элемента  */
    private int identicalNeighborsNumber(int i, int j) {
        var value = board.getValue(new Key(i,j) );
        int counterValidNeighbour = 0;
        counterValidNeighbour += checkNeighbourValue(i,j-1,value);
        counterValidNeighbour += checkNeighbourValue(i-1,j,value);
        counterValidNeighbour += checkNeighbourValue(i+1,j,value);
        counterValidNeighbour += checkNeighbourValue(i,j+1,value);
        return counterValidNeighbour;
    }

    /** Метод проверяет совпадает ли значение элемента со значением соседа  */
    private int checkNeighbourValue(int i, int j, Integer value) {
        try {
            if( board.getValue(new Key(i,j)) == value) {
                return 1;
            }
        } catch(ArrayIndexOutOfBoundsException e) {

        }
        return 0;
    }

    /** Метод двигает вниз все значения на игровой доске */
    private boolean moveBack() {
        List<Integer> updatedValues;
        for(int i = 0; i < board.getHeight(); i++) {
            List<Integer> valuesList = board.getValues(board.getColumn(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            updatedValues = reverseListForBackAndRightMove(updatedValues);
            for(int j = 0; j < board.getHeight(); j++) {
                board.addItem(new Key(j,i), updatedValues.get(j));
            }
        }
        if (!board.availableSpace().isEmpty()) {
            addItem();
        }
        return true;
    }

    /** Метод двигает вверх все значения на игровой доске */
    private boolean moveForward() {
        List<Integer> updatedValues;
        for(int i = 0; i < board.getHeight(); i++) {
            List<Integer> valuesList = board.getValues(board.getColumn(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            for(int j = 0;j < board.getHeight(); j++) {
                board.addItem(new Key(j,i), updatedValues.get(j));
            }
        }
        if (!board.availableSpace().isEmpty()) {
            addItem();
        }
        return true;
    }

    /** Метод двигает направо все значения на игровой доске */
    private boolean moveRight() {
        List<Integer> updatedValues;
        for(int i =0; i < board.getWidth(); i++) {
            List<Integer> valuesList = board.getValues(board.getRow(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            updatedValues = reverseListForBackAndRightMove(updatedValues);
            for(int j = 0; j < board.getWidth(); j++){
                board.addItem(new Key(i,j), updatedValues.get(j));
            }
        }
        if (!board.availableSpace().isEmpty()) {
            addItem();
        }
        return true;
    }

    /** Метод двигает налево все значения на игровой доске */
    private boolean moveLeft() {
        List<Integer> updatedValues;
        for(int i =0; i < board.getWidth(); i++) {
            List<Integer> valuesList = board.getValues(board.getRow(i));
            updatedValues = helper.moveAndMergeEqual(valuesList);
            for(int j = 0; j < board.getWidth(); j++) {
                board.addItem(new Key(i,j), updatedValues.get(j));
            }
        }
        if (!board.availableSpace().isEmpty()) {
            addItem();
        }
        return true;
    }

    /** Метод позволяет корректно перевернуть значения игрового поля,
     *  которые получаются после движения игрового поля направо или вниз
     *  Пример:
     *  Была строка 2 2 null 3, двигаем её вправо и получаем результат
     *              null null 4 3
     *  */
    private List<Integer> reverseListForBackAndRightMove(List<Integer> list) {
        List<Integer> returnList = new ArrayList<>();
        var counterNullElementsInList = 0;
        for(Integer value : list) {
            if(value == null) {
                counterNullElementsInList++;
            }
        }
        for(int i = 0; i < counterNullElementsInList; i++) {
            returnList.add(null);
        }
        var counterNotNullElementsInList = list.size() - counterNullElementsInList;
        for(int i = 0; i < counterNotNullElementsInList; i++) {
            returnList.add(list.get(i));
        }
        return returnList;
    }
}