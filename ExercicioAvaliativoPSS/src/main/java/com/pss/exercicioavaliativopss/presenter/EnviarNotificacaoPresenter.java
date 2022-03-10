package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.EnviarNotificacaoView;

import com.pss.exercicioavaliativopss.model.Log;
import com.pss.exercicioavaliativopss.model.Notificacao;

import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class EnviarNotificacaoPresenter implements InterfaceObservable {

    private EnviarNotificacaoView view;
    private ArrayList<InterfaceObserver> observers;
    private InterfaceLogger logger;
    private NotificacaoDAO dao;
    private Admin admin;

    public EnviarNotificacaoPresenter(InterfaceLogger logger, JDesktopPane desktop, Admin admin,
            ArrayList<Integer> users) {
        view = new EnviarNotificacaoView();
        observers = new ArrayList<>();
        dao = new NotificacaoDAO();
        this.admin = admin;
        this.logger = logger;

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnEnviar().addActionListener((ActionEvent ae) -> {
            enviar(users);
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void enviar(ArrayList<Integer> users) {
        String msg = view.getTxtMensagem().getText();

        try {
            for (Integer id : users) {
                dao.inserirNotificacao(new Notificacao(id, admin.getId(), msg, false, LocalDate.now()));
            }

            JOptionPane.showMessageDialog(view, "Mensagem enviada com Sucesso!");

            logger.logUsuarioCRUD(new Log("Envio de notificação", admin.getNome(), admin.getUsername(), "-"));
            notifyObserver(null);
            view.dispose();
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
            logger.logFalha(new Log("Envio de notificação", admin.getNome(), admin.getUsername(), e.getMessage()));
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
            o.update(obj);
        }
    }

}
