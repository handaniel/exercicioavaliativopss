package com.pss.exercicioavaliativopss.model.state;

import com.pss.exercicioavaliativopss.presenter.PrincipalPresenter;

public class UsuarioLoggedInState extends LoginState {

    public UsuarioLoggedInState(PrincipalPresenter presenter) {
        super(presenter);
        presenter.usuarioLayout();

    }

    @Override
    public void logout() {
        this.getPresenter().setState(new UsuarioLoggedOutState(this.getPresenter()));
    }

}
