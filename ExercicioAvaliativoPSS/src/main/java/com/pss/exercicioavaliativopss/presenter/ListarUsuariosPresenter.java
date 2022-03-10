package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.Log;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.ListarUsuariosView;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListarUsuariosPresenter implements InterfaceObservable {

    private final ListarUsuariosView view;
    private final UsuarioDAO uDao;
    private final NotificacaoDAO nDao;
    private final DefaultTableModel tmUsuarios;
    private Admin admin;
    private InterfaceLogger logger;
    private ArrayList<InterfaceObserver> observers;

    public ListarUsuariosPresenter(Admin admin, JDesktopPane desktop, InterfaceLogger logger) {
        view = new ListarUsuariosView();
        uDao = new UsuarioDAO();
        nDao = new NotificacaoDAO();
        this.admin = admin;
        this.logger = logger;
        observers = new ArrayList<>();

        tmUsuarios = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID", "Nome", "Data do Cadastro", "Notificações Enviadas", "Notificações Lidas"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        view.getTblUsuarios().setModel(tmUsuarios);
        preencheTabela();

        resetaBotoes();

        view.getTblUsuarios().getSelectionModel().addListSelectionListener(((lse) -> {

            if (view.getTblUsuarios().getSelectedRowCount() >= 1) {
                view.getBtnEnviarNotificacao().setEnabled(true);

                if (view.getTblUsuarios().getSelectedRowCount() == 1) {
                    view.getBtnVisualizar().setEnabled(true);
                    int id = Integer.parseInt(view.getTblUsuarios().getValueAt(view.getTblUsuarios().getSelectedRow(), 0)
                            .toString());
                    if (!UsuarioDAO.isAutorizado(id)) {
                        view.getBtnAutorizar().setEnabled(true);

                    }
                } else {
                    view.getBtnVisualizar().setEnabled(false);
                    view.getBtnAutorizar().setEnabled(false);
                }
            }
        }));

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnAutorizar().addActionListener((ActionEvent ae) -> {
            int id = Integer.parseInt(view.getTblUsuarios().getValueAt(view.getTblUsuarios().getSelectedRow(), 0).toString());
            UsuarioModel temp = uDao.findById(id);
            autorizar(temp);
            resetaBotoes();
            limpaTabela();
            preencheTabela();
        });

        view.getBtnBuscar().addActionListener((ActionEvent ae) -> {
            resetaBotoes();
            limpaTabela();
            busca();
        });

        view.getBtnVisualizar().addActionListener((ActionEvent ae) -> {
            int row = view.getTblUsuarios().getSelectedRow();
            int id = Integer.parseInt(view.getTblUsuarios().getValueAt(row, 0).toString());
            if (id >= 1) {
                UsuarioModel temp = uDao.findById(id);
                view.dispose();
                new CadastroPresenter(desktop, Admin.class.isInstance(temp), temp, logger);
            }
            resetaBotoes();
            limpaTabela();
            preencheTabela();
        });

        view.getBtnEnviarNotificacao().addActionListener(((ActionEvent ae) -> {
            notificacao(desktop);
            resetaBotoes();
            limpaTabela();
            preencheTabela();
        }));

        view.getBtnAddUsuario().addActionListener((ActionEvent ae) -> {
            new CadastroPresenter(desktop, true, logger);
            limpaTabela();
            preencheTabela();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void autorizar(UsuarioModel temp) {
        try {
            uDao.autorizar(temp.getId());
            logger.logUsuarioCRUD(new Log("Autorização", temp.getNome(), admin.getUsername(), "-"));
            JOptionPane.showMessageDialog(view, "Usuário autorizado!");
            preencheTabela();
        } catch (RuntimeException e) {
            logger.logFalha(new Log("Autorização", temp.getNome(), temp.getUsername(), e.getMessage()));
        }
    }

    private void resetaBotoes() {
        view.getBtnAutorizar().setEnabled(false);
        view.getBtnEnviarNotificacao().setEnabled(false);
        view.getBtnVisualizar().setEnabled(false);
    }

    private void limpaTabela() {
        tmUsuarios.setRowCount(0);
        view.getTblUsuarios().setModel(tmUsuarios);
    }

    private void busca() {
        String texto = view.getTxtBusca().getText();

        if (!texto.isEmpty() || !texto.isBlank()) {

            ArrayList<UsuarioModel> lista = uDao.procura(texto);

            if (!lista.isEmpty()) {
                for (UsuarioModel u : lista) {
                    int lida = nDao.contaNotificacao(u.getId());
                    int nLida = nDao.contaNotificacaoLida(u.getId());
                    if (!admin.equals(u.getUsername())) {
                        tmUsuarios.addRow(new Object[]{u.getId(), u.getNome(), u.getDataCadastro(), lida, nLida});
                    }
                }
                view.getTblUsuarios().setModel(tmUsuarios);
            }

        } else {
            JOptionPane.showMessageDialog(view, "Busca inválida!");
        }
    }

    private void preencheTabela() {

        ArrayList<UsuarioModel> lista = uDao.listarUsuarios();

        if (!lista.isEmpty()) {
            for (UsuarioModel u : lista) {
                int lida = nDao.contaNotificacao(u.getId());
                int nLida = nDao.contaNotificacaoLida(u.getId());
                if (admin.getId() != u.getId()) {
                    tmUsuarios.addRow(new Object[]{u.getId(), u.getNome(), u.getDataCadastro(), lida, nLida});
                }
            }
        }
        view.getTblUsuarios().setModel(tmUsuarios);

    }

    private void notificacao(JDesktopPane desktop) {
        ArrayList<Integer> lista = new ArrayList<>();

        int rows[] = view.getTblUsuarios().getSelectedRows();

        if (rows.length == 0) {
            JOptionPane.showMessageDialog(view, "Selecione usuários para notificar!");
        } else {
            for (int row : rows) {
                int id = Integer.parseInt(view.getTblUsuarios().getValueAt(row, 0).toString());
                lista.add(id);
            }

            new EnviarNotificacaoPresenter(logger, desktop, admin, lista);
        }

    }

    public ListarUsuariosView getView() {
        return view;
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
