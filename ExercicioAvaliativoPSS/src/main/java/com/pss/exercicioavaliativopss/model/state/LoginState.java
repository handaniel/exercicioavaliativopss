package com.pss.exercicioavaliativopss.model.state;

import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.presenter.PrincipalPresenter;

public abstract class LoginState {

    private PrincipalPresenter presenter;

    public LoginState(PrincipalPresenter presenter) {
        this.presenter = presenter;
    }

    public void login(UsuarioModel usuario) {
        throw new RuntimeException("Erro ao fazer login");
    }

    public void logout() {
        throw new RuntimeException("Erro ao fazer logout");
    }

    public PrincipalPresenter getPresenter() {
        return presenter;
    }

    public void setPresenter(PrincipalPresenter presenter) {
        this.presenter = presenter;
    }

}
