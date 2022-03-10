package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.model.Log;
import com.pss.exercicioavaliativopss.model.Notificacao;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.NotificacoesView;
import java.awt.event.ActionEvent;
import java.time.format.DateTimeFormatter;
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
                new String[]{"ID", "Remetente", "Notificação", "Lida"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };
        view.getTblNotificacoes().setModel(tmNotificacoes);
        preencheTabela();

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

    private void limpaTabela() {
        tmNotificacoes.setRowCount(0);
        view.getTblNotificacoes().setModel(tmNotificacoes);
    }

    private void preencheTabela() {
        try {
            limpaTabela();
            ArrayList<Notificacao> lista = nDao.getNotificacoes(usuario.getId());
            if (!lista.isEmpty()) {
                for (Notificacao n : lista) {
                    String lida = n.isLida() ? "Sim" : "Não";
                    UsuarioModel temp = uDao.findById(n.getRemetente());
                    tmNotificacoes.addRow(new Object[]{n.getId(), temp.getUsername(), n.getMensagem(), lida});
                }
            }
            view.getTblNotificacoes().setModel(tmNotificacoes);
        } catch (RuntimeException e) {
            logger.logFalha(new Log("REduperação de Notificações", usuario.getNome(), usuario.getUsername(), e.getMessage()));
        }
    }

    private void Visualizar() {
        try {
            int id = Integer.parseInt(view.getTblNotificacoes().getValueAt(view.getTblNotificacoes().getSelectedRow(), 0).toString());
            Notificacao n = nDao.getById(id);
            UsuarioModel remetente = uDao.findById(n.getRemetente());

            nDao.marcarComoLida(id);
            notifyObserver(n);
            logger.logUsuarioCRUD(new Log("Visualização de notificação", usuario.getNome(), usuario.getUsername(), "-"));
            JOptionPane.showMessageDialog(view, "Mensagem:\n"
                    + n.getMensagem()
                    + "\nRemetente: "
                    + remetente.getUsername()
                    + "\nEnviada em: "
                    + n.getDataEnvio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        } catch (RuntimeException e) {
            logger.logFalha(new Log("Visualização de Notificações", usuario.getNome(), usuario.getUsername(), e.getMessage()));
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
            o.update((Notificacao) obj);
        }
    }

}
