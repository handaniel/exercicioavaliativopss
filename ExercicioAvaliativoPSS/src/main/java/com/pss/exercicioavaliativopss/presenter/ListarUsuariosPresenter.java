package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.view.ListarUsuariosView;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListarUsuariosPresenter {

    private final ListarUsuariosView view;
    private final UsuarioDAO uDao;
    private final NotificacaoDAO nDao;
    private DefaultTableModel tmUsuarios;
    private Admin admin;
    private InterfaceLogger logger;

    public ListarUsuariosPresenter(Admin admin, JDesktopPane desktop, InterfaceLogger logger) {
        view = new ListarUsuariosView();
        uDao = new UsuarioDAO();
        nDao = new NotificacaoDAO();
        this.admin = admin;
        this.logger = logger;

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

        view.getBtnAutorizar().setEnabled(false);
        view.getBtnEnviarNotificacao().setEnabled(false);
        view.getBtnVisualizar().setEnabled(false);

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
                }
            }
        }));

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnAutorizar().addActionListener((ActionEvent ae) -> {
            autorizar();
        });

        view.getBtnEnviarNotificacao().addActionListener(((ActionEvent ae) -> {
            notificacao(desktop);
        }));

        view.getBtnAddUsuario().addActionListener((ActionEvent ae) -> {
            new CadastroPresenter(desktop, true);
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void autorizar() {
        int id = Integer.parseInt(view.getTblUsuarios().getValueAt(view.getTblUsuarios().getSelectedRow(), 0).toString());
        uDao.autorizar(id);
        JOptionPane.showMessageDialog(view, "Usuário autorizado!");
        preencheTabela();
    }

    private void preencheTabela() {
        tmUsuarios.setRowCount(0);
        view.getTblUsuarios().setModel(tmUsuarios);

        ArrayList<UsuarioModel> lista = uDao.listarUsuarios();

        if (!lista.isEmpty()) {
            for (UsuarioModel u : lista) {
                if (!admin.equals(u.getUsername())) {
                    tmUsuarios.addRow(new Object[]{u.getId(), u.getNome(), u.getDataCadastro()});
                }
            }
        }

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
                System.out.println(id);
            }

            new EnviarNotificacaoPresenter(logger, desktop, admin, lista);
        }

    }

    public ListarUsuariosView getView() {
        return view;
    }

}
