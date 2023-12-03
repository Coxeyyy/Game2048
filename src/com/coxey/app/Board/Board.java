package com.coxey.app.Board;

import com.coxey.app.Key.Key;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public  abstract class Board {
    private int width;
    private int height;
    Map<Key,Integer> board = new LinkedHashMap<>();

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Board(int width, int height){
        this.width = width;
        this.height = height;
    }
    public abstract void fillBoard(List<Integer> list);
    public abstract List<Key> availableSpace();
    public abstract void addItem(Key key, Integer value);
    public abstract Key getKey(int i, int j);
    public abstract Integer getValue(Key key);
    public abstract List<Key> getColumn(int j);
    public abstract List<Key> getRow(int i);
    public abstract boolean hasValue(Integer value);
    public abstract List<Integer> getValues(List<Key> key);
}
