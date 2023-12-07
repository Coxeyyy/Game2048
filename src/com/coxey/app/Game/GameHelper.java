package com.coxey.app.Game;

import java.util.ArrayList;
import java.util.List;

public class GameHelper {
    /**
     * Перемещает в начало все элементы, при это, если 2 соседних элемента равны, то
     * эти элементы должны объединиться в 1.
     * Если входной список пустой, то на выходе должен быть тот же пустой список
     * Кол-во элементов в исходном и результирующем списке должно быть одинаковое.
     * Примеры:
     * 1, 2, null, 3 -> 1, 2, 3, null
     * 2, 2, null, 3 -> 4, 3, null, null
     * 2, 2, 4, 4 -> 4, 8, null, null
     * 2, 2, 2, 3 -> 4, 2, 3, null
     * 2, null, null, 2 -> 4, null, null, null
     * null, null, null, null -> null, null, null, null
     * null, null, null, 2 -> 2, null, null, null
     * null, null, 2, 2 -> 4, null, null, null
     */
    public List<Integer> moveAndMergeEqual(List<Integer> list) {
        List<Integer> returnList = new ArrayList<>(list);
        returnList = sortingList(returnList);
        for(int i = 0; i < returnList.size() - 1; i++) {
            if( returnList.get(i) != null && ( returnList.get(i) == returnList.get(i+1) ) ) {
                returnList.set(i, returnList.get(i) * 2);
                returnList.set(i+1, null);
            } else {
                returnList.set(i, returnList.get(i));
            }
        }
        return sortingList(returnList);
    };

    /**
     * Метод производит сортировку по следующему правилу:
     * В начале идут элементы, которые больше нуля,
     * дальше идут элементы null, если элементов null нет,
     * то результирующий список будет таким же, как и входящий
     * Примеры:
     * 2, 4, 8 , 16 -> 2, 4, 8 , 16
     * 2, 2, null, 3 -> 2, 2, 3, null
     */
    private List<Integer> sortingList(List<Integer> list) {
        List<Integer> returnList = new ArrayList<>(list.size());
        for(var value : list) {
            if (value != null) {
                returnList.add(value);
            }
        }
        if( returnList.size() < list.size() ) {
            var differenceListsSize = list.size() - returnList.size();
            for(int i = 0; i < differenceListsSize; i++) {
                returnList.add(null);
            }
        }
        return returnList;
    }
}