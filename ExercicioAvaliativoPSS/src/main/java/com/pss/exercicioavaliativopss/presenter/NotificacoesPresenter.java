package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.NotificacoesView;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class NotificacoesPresenter implements InterfaceObservable {

    private final NotificacoesView view;
    private final ArrayList<InterfaceObserver> observers;
    private final UsuarioModel usuario;
    private DefaultTableModel tmNotificacoes;
    private final NotificacaoDAO nDao;
    private final UsuarioDAO uDao;
    private InterfaceLogger logger;

    public NotificacoesPresenter(JDesktopPane desktop, UsuarioModel usuario, InterfaceLogger logger) {
        view = new NotificacoesView();
        observers = new ArrayList<>();
        this.usuario = usuario;
        nDao = new NotificacaoDAO();
        uDao = new UsuarioDAO();
        this.logger = logger;

        tmNotificacoes = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Id", "Notificação"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        view.getTblNotificacoes().setModel(tmNotificacoes);

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnVisualizar().addActionListener((ActionEvent ae) -> {
            if (view.getTblNotificacoes().getSelectedRowCount() != 1) {
                JOptionPane.showMessageDialog(view, "Selecione pelo menos e exatamente UMA linha!");
            } else {
                Visualizar();
            }
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void Visualizar() {
        
    }

    @Override
    public void addObserver(InterfaceObserver observer) {
        addObserver(observer);
    }

    @Override
    public void removeObserver(InterfaceObserver observer) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void notifyObserver(Object obj) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
