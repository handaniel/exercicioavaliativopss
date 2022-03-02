package com.pss.exercicioavaliativopss.model.state;

import com.pss.exercicioavaliativopss.presenter.PrincipalPresenter;

public class AdminLoggedInState extends LoginState {
    
    public AdminLoggedInState(PrincipalPresenter presenter) {
        super(presenter);
    }
    
    @Override
    public void logout() {
        this.getPresenter().setState(new UsuarioLoggedOutState(this.getPresenter()));
    }
    
}
