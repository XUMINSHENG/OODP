/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.edu.nus.iss.vmcs.store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 *
 * @author A0134434m
 */
public class StoreItemChangeManager  {

    private static StoreItemChangeManager instance = new StoreItemChangeManager();
    private HashMap<Observable,List<Observer>> subjectObserverMapping = new HashMap();
    
    private StoreItemChangeManager(){}
    
    public synchronized static StoreItemChangeManager getInstance(){
//        if (instance == null) {
//            instance = new StoreItemChangeManager();
//        }
        
        return instance;
    }

    /**
     * 
     * @param subject
     * @param observer 
     */
    public synchronized void register(Observable subject, Observer observer){
        List<Observer> oList;
        if(!subjectObserverMapping.containsKey(subject)){
            oList = new ArrayList();
            subjectObserverMapping.put(subject, oList);
        }else{
            oList = subjectObserverMapping.get(subject);
        }
        oList.add(observer);
    };
    
    /**
     * 
     * @param subject
     * @param observer 
     */
    public synchronized void unregister(Observable subject, Observer observer){
        List<Observer> oList;
        if(!subjectObserverMapping.containsKey(subject)){
            throw new NullPointerException();
        }else{
            oList = subjectObserverMapping.get(subject);
            oList.remove(observer);
        }
    };
    
    /**
     * 
     * @param o
     * @param arg 
     */
    public void notifyObservers(Observable o, Object arg) {
        List<Observer> oList = subjectObserverMapping.get(o);
        oList.stream().forEach((Observer) -> {
            Observer.update(o, arg);
        });
    }
    
}
