package com.coxey.app.Board;

import com.coxey.app.Key.Key;

import java.util.*;
import java.util.stream.Collectors;

public class SquareBoard<V> extends Board<Key, V> {
    private final int size;
    public SquareBoard(int size) {
        super(size, size);
        this.size = size;
    }

    /** Заполняем поле элементами из входного списка.
     * Если нужно задать пустой элемент, указываем null.
     */
    @Override
    public void fillBoard(List<V> list) {
        if(list.size() > size*size) {
            throw new RuntimeException(String.format(
                    "Входных значений больше чем размер игрового поля"
            ));
        }
        Iterator<V> iterator = list.iterator();
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                board.put(new Key(i,j), iterator.next());
            }
        }
    }

    /** Возвращаем все ключи, у которых значение null.
     */
    @Override
    public List<Key> availableSpace() {
        List<Key> keys = new ArrayList<>();
        for(Key k : board.keySet()) {
            if( board.get(k) == null ) {
                keys.add(k);
            }
        }
        return keys;
    }

    /** Добавляем элемент {@param value} по ключу {@param key}. */
    @Override
    public void addItem(Key key, V value) {
        board.put(key,value);
    }

    /** Ищем уже существующий ключ по координатам {@param i} {@param j}. */
    @Override
    public Key getKey(int i, int j) {
        for(Key k: board.keySet() ) {
            if( i == k.getI() && j == k.getJ() ) {
                return k;
            }
        }
        return null;
    }

    /** Получаем значение по {@param key} */
    @Override
    public V getValue(Key key) {
        return board.get(key);
    }

    /** Получаем столбец ключей, по которым потом можно будет выбрать значения. */
    @Override
    public List<Key> getColumn(int j) {
        return board.keySet().stream()
                .filter(key -> key.getJ() == j)
                .sorted(Comparator.comparingInt(Key::getI))
                .collect(Collectors.toList());
    }

    /** Получаем строку ключей, по которым потом можно будет выбрать значения. */
    @Override
    public List<Key> getRow(int i) {
        return board.keySet().stream()
                .filter(key -> key.getI() == i)
                .sorted(Comparator.comparingInt(Key::getJ))
                .collect(Collectors.toList());
    }

    /** Проверяем, есть ли такое значение на поле. */
    @Override
    public boolean hasValue(V value) {
        return board.containsValue(value);
    }

    /** Получаем строку значений по строке ключей. */
    @Override
    public List<V> getValues(List<Key> key) {
        List<V> values = new ArrayList<>();
        for(Key k : key) {
            values.add(board.get(k));
        }
        return values;
    }

    /** Очистка игровой доски */
    public void clearBoard() {
        for (int i = 0; i < getWidth(); i++) {
            for(int j = 0; j < getHeight(); j++) {
                addItem(new Key(i,j),null);
            }
        }
    }
}