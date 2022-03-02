package com.pss.exercicioavaliativopss.model.state;

import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.presenter.PrincipalPresenter;

public class UsuarioLoggedOutState extends LoginState {
    
    public UsuarioLoggedOutState(PrincipalPresenter presenter) {
        super(presenter);
    }
    
    @Override
    public void login(UsuarioModel usuario) {
        if (Admin.class.isInstance(usuario)) {
            this.getPresenter().setState(new AdminLoggedInState(this.getPresenter()));
        } else {
            this.getPresenter().setState(new UsuarioLoggedInState(this.getPresenter()));
        }
    }
}
