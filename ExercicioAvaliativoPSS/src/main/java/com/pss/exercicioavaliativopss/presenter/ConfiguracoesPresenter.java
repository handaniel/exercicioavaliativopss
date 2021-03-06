package com.pss.exercicioavaliativopss.presenter;

import java.util.ArrayList;

import javax.swing.JDesktopPane;

import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.factory.Logger.LoggerCSV;
import com.pss.exercicioavaliativopss.factory.Logger.LoggerJSON;
import com.pss.exercicioavaliativopss.factory.Logger.LoggerXML;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import java.awt.event.ActionEvent;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.ConfiguracoesView;

public class ConfiguracoesPresenter implements InterfaceObservable {

    private final ConfiguracoesView view;
    private final ArrayList<InterfaceObserver> observers;
    private InterfaceLogger logger;

    public ConfiguracoesPresenter(JDesktopPane desktop, InterfaceLogger logger) {
        view = new ConfiguracoesView();
        observers = new ArrayList<>();
        this.logger = logger;

        if (LoggerCSV.class.isInstance(logger)) {
            view.getRbtCSV().setSelected(true);
        } else if (LoggerJSON.class.isInstance(logger)) {
            view.getRbtJSON().setSelected(true);
        } else {
            view.getRbtXML().setSelected(true);
        }

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            aplicar();
            view.dispose();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void aplicar() {
        if (view.getRbtCSV().isSelected()) {
            notifyObserver(new LoggerCSV());
        } else if (view.getRbtJSON().isSelected()) {
            notifyObserver(new LoggerJSON());
        } else {
            notifyObserver(new LoggerXML());
        }
    }

    @Override
    public void addObserver(InterfaceObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(InterfaceObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Object obj) {
        for (InterfaceObserver o : observers) {
            if (LoggerCSV.class.isInstance(obj)) {
                o.update((LoggerCSV) obj);
            } else if (LoggerJSON.class.isInstance(obj)) {
                o.update((LoggerJSON) obj);
            } else if (LoggerXML.class.isInstance(obj)) {
                o.update((LoggerXML) obj);
            }
        }
    }

}
