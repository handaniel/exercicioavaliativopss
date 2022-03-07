package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.NotificacoesView;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;

public class NotificacoesPresenter implements InterfaceObservable {

    private final NotificacoesView view;
    private final ArrayList<InterfaceObserver> observers;
    private final UsuarioModel usuario;
    private DefaultTableModel tmNotificacoes;
    private final NotificacaoDAO notDao;
    private final UsuarioDAO usuarioDao;

    public NotificacoesPresenter(JDesktopPane desktop, UsuarioModel usuario) {
        view = new NotificacoesView();
        observers = new ArrayList<>();
        this.usuario = usuario;
        notDao = new NotificacaoDAO();
        usuarioDao = new UsuarioDAO();

        tmNotificacoes = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Id", "Notificação"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        desktop.add(view);
        view.setVisible(true);
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
