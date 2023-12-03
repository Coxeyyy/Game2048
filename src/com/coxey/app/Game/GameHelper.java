package com.coxey.app.Game;

import java.util.ArrayList;
import java.util.List;

public class GameHelper {
    public List<Integer> moveAndMergeEqual(List<Integer> list){
        List<Integer> returnList = new ArrayList<>(list);
        for(int i = 0; i<list.size(); i++){
            returnList.set(i,null);
        }

        for(int i = 0; i<list.size()-1; i++){
            if(i==0 && (list.get(0) == list.get(list.size()-1)) && list.get(0) != null ){
                returnList.set(0,list.get(0)*2);
                list.set(list.size()-1,null);
                continue;
            }
            if((list.get(i) == list.get(i+1)) && list.get(i) != null){
                returnList.set(i,list.get(i)*2);
                list.set(i+1,null);
            }else{
                returnList.set(i,list.get(i));
                returnList.set(i+1,list.get(i+1));
            }
        }
        return arraySort(returnList);
    };

    private List<Integer> arraySort(List<Integer> list){
        boolean sort;
        int stepCounts = list.size() - 1;
        do{
            sort = false;
            for(int i = 0; i<stepCounts; i++){
                if(list.get(i) == null){
                    var temp = list.get(i);
                    list.set(i,list.get(i+1));
                    list.set(i+1,temp);
                    sort = true;
                }
            }
            stepCounts--;
        }while(sort);
        return list;
    }
}
