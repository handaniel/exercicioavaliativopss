package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.view.ListarUsuariosView;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ListarUsuariosPresenter {

    private final ListarUsuariosView view;
    private final UsuarioDAO dao;
    private DefaultTableModel tmUsuarios;
    private String admin;

    public ListarUsuariosPresenter(String admin) {
        view = new ListarUsuariosView();
        dao = new UsuarioDAO();
        this.admin = admin;

        tmUsuarios = new DefaultTableModel(
                new Object[][]{},
                new String[]{"Nome", "Data do Cadastro", "Notificações Enviadas", "Notificações Lidas"}
        ) {
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
            view.getBtnVisualizar().setEnabled(true);
            view.getBtnEnviarNotificacao().setEnabled(true);
            if (view.getTblUsuarios().getSelectedRow() >= 0) {
                String username = view.getTblUsuarios().getValueAt(view.getTblUsuarios().getSelectedRow(), 0).toString();
                if (!UsuarioDAO.isAutorizado(username)) {
                    view.getBtnAutorizar().setEnabled(true);
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

        }));

        view.getBtnAddUsuario().addActionListener((ActionEvent ae) -> {

        });

        view.setVisible(true);
    }

    private void autorizar() {
        String username = view.getTblUsuarios().getValueAt(view.getTblUsuarios().getSelectedRow(), 0).toString();
        dao.autorizar(username);
        JOptionPane.showMessageDialog(view, "Usuário autorizado!");
        preencheTabela();
    }

    private void preencheTabela() {
        tmUsuarios.setRowCount(0);
        view.getTblUsuarios().setModel(tmUsuarios);

        ArrayList<UsuarioModel> lista = dao.listarUsuarios();

        if (!lista.isEmpty()) {
            for (UsuarioModel u : lista) {
                if (!admin.equals(u.getUsername())) {
                    tmUsuarios.addRow(new Object[]{u.getNome(), u.getDataCadastro()});
                }
            }
        }

    }

    public ListarUsuariosView getView() {
        return view;
    }

}
