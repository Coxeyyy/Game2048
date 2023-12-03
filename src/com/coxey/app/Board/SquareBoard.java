package com.coxey.app.Board;

import com.coxey.app.Key.Key;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SquareBoard extends Board{
    private int size;
    public SquareBoard(int size){
        super(size,size);
        this.size = size;
    }

    @Override
    public void fillBoard(List<Integer> list) {
        Iterator<Integer> iterator = list.iterator();
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                board.put(new Key(i,j), iterator.next());
            }
        }
    }

    @Override
    public List<Key> availableSpace() {
        List<Key> keys = new ArrayList<>();
        for(Key k : board.keySet()){
            if(board.get(k) == null){
                keys.add(k);
            }
        }
        return keys;
    }

    @Override
    public void addItem(Key key, Integer value) {
        board.put(key,value);
    }

    @Override
    public Key getKey(int i, int j) {
        for(Key k: board.keySet()){
            if(i == k.getI() && j == k.getJ()){
                return k;
            }
        }
        return null;
    }

    @Override
    public Integer getValue(Key key) {
        return board.get(key);
    }

    @Override
    public List<Key> getColumn(int j) {
        List<Key> columnsList = new ArrayList<>();
        for(Key k : board.keySet()){
            if(j == k.getJ()){
                columnsList.add(k);
            }
        }
        return columnsList;
    }

    @Override
    public List<Key> getRow(int i) {
        List<Key> rowList = new ArrayList<>();
        for(Key k: board.keySet()){
            if(i == k.getI()){
                rowList.add(k);
            }
        }
        return rowList;
    }

    @Override
    public boolean hasValue(Integer value) {
        return board.containsValue(value);
    }

    @Override
    public List<Integer> getValues(List<Key> key) {
        List<Integer> values = new ArrayList<>();
        for(Key k : key){
            values.add(board.get(k));
        }
        return values;
    }
}