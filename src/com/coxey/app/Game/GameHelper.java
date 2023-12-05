package com.coxey.app.Game;

import java.util.ArrayList;
import java.util.List;

public class GameHelper {
    public List<Integer> moveAndMergeEqual(List<Integer> list){
        List<Integer> returnList = new ArrayList<>();
        returnList.addAll(list);
        returnList = sortingList(returnList);
        for(int i = 0; i<returnList.size()-1; i++){
            if(returnList.get(i) != null && (returnList.get(i) == returnList.get(i+1))){
                returnList.set(i,returnList.get(i)*2);
                returnList.set(i+1,null);
            } else{
                returnList.set(i,returnList.get(i));
            }
        }
        return sortingList(returnList);
    };

    private List<Integer> sortingList(List<Integer> list){
        List<Integer> returnList = new ArrayList<>(0);
        for(Integer value : list){
            if (value != null){
                returnList.add(value);
            }
        }
        if(returnList.size() < list.size()){
            var count = list.size() - returnList.size();
            for(int i = 0; i<count; i++){
                returnList.add(null);
            }
        }
        return returnList;
    }
}
