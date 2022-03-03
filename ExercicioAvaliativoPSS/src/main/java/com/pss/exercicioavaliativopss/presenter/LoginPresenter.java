package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.view.LoginView;
import java.awt.event.ActionEvent;
import javax.swing.JOptionPane;

public class LoginPresenter {

    private LoginView view;
    private UsuarioDAO dao;

    public LoginPresenter() {
        view = new LoginView();
        dao = new UsuarioDAO();

        view.getBtnLogin().addActionListener(((ActionEvent ae) -> {
            login();
        }));

        view.getBtnCadastrar().addActionListener((ActionEvent ae) -> {
            cadastrar();
        });

        view.setVisible(true);
    }

    private void cadastrar() {
        if (UsuarioDAO.contaUsuarios() == 0) {
            new CadastroLoginPresenter(true);
        } else {
            new CadastroLoginPresenter(false);

        }

        view.dispose();

    }

    private void login() {

        String username = view.getTxtUsername().getText();
        String senha = String.valueOf(view.getTxtSenha().getPassword());

        if (username.isBlank() || username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nome de usuário inválido!");
        } else if (senha.isEmpty() || senha.isBlank()) {
            JOptionPane.showMessageDialog(view, "Senha inválida!");
        } else {
            UsuarioModel usuario = dao.login(username, senha);

            if (usuario == null) {
                JOptionPane.showMessageDialog(view, "Erro ao efetuar login!");
            } else if (UsuarioDAO.isAutorizado(username)) {
                new PrincipalPresenter(usuario);
                view.dispose();
            } else {
                JOptionPane.showMessageDialog(view, "Usuário não autorizado!\n"
                        + "Aguardar autorização de Administrador!");
            }
        }

    }
}
