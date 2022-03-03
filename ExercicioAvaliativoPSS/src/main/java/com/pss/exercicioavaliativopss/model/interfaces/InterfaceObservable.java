package com.pss.exercicioavaliativopss.model.interfaces;

public interface InterfaceObservable {

    public void registerObserver(InterfaceUsuarioObserver observer);

    public void removeObserver(InterfaceUsuarioObserver observer);

    public void notifyObserver(Object obj);
}
