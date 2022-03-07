package com.pss.exercicioavaliativopss.model.interfaces;

public interface InterfaceObservable {

    public void addObserver(InterfaceObserver observer);

    public void removeObserver(InterfaceObserver observer);

    public void notifyObserver(Object obj);
}
