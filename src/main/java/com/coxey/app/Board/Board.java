package com.coxey.app.Board;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public  abstract class Board<K, V> {
    private int width;
    private int height;
    protected Map<K, V> board = new HashMap<>();

    public Board(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Board<?, ?> board1 = (Board<?, ?>) o;
        return width == board1.width && height == board1.height && Objects.equals(board, board1.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(width, height, board);
    }

    public int getHeight() {
        return height;
    }

    public abstract void fillBoard(List<V> list);

    public abstract List<K> availableSpace();

    public abstract void addItem(K key, V value);

    public abstract K getKey(int i, int j);

    public abstract V getValue(K key);

    public abstract List<K> getColumn(int j);

    public abstract List<K> getRow(int i);

    public abstract boolean hasValue(V value);

    public abstract List<V> getValues(List<K> key);

    public abstract void clearBoard();
}