package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.view.LoginView;
import java.awt.event.ActionEvent;

public class LoginPresenter {

    private LoginView view;
    private UsuarioDAO dao;

    public LoginPresenter() {
        view = new LoginView();
        dao = new UsuarioDAO();

        view.getBtnLogin().addActionListener(((ActionEvent ae) -> {

        }));

        view.getBtnCadastrar().addActionListener((ActionEvent ae) -> {

        });

        view.getCkbMostrarSenha().addActionListener((ActionEvent ae) -> {
            if (view.getCkbMostrarSenha().isSelected()) {
                view.getTxtSenha().setEchoChar((char) 0);
            } else {
                view.getTxtSenha().setEchoChar('*');
            }
        });

        view.setVisible(true);
    }

}
