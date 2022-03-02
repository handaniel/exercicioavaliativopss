package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.state.LoginState;
import com.pss.exercicioavaliativopss.view.PrincipalView;

public class PrincipalPresenter {

    private final PrincipalView view;
    private UsuarioModel usuario;
    private LoginState state;
    private int numUsuarios;

    public PrincipalPresenter(UsuarioModel usuario) {
        view = new PrincipalView();

        view.setVisible(true);
    }

    public void setState(LoginState state) {
        this.state = state;
    }

}
