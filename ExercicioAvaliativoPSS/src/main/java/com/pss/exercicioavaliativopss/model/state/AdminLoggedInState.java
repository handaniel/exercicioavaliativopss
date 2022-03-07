package com.pss.exercicioavaliativopss.model.state;

import com.pss.exercicioavaliativopss.presenter.PrincipalPresenter;

public class AdminLoggedInState extends LoginState {

    public AdminLoggedInState(PrincipalPresenter presenter) {
        super(presenter);
        presenter.getView().getLblTipoUser().setText("Admin");
        presenter.getView().getLblUser().setText(presenter.getUsuario().getUsername());
        presenter.getView().getBtnNotificacoes().setText("Notificações");
        presenter.getView().getMnuAdmin().setVisible(true);
        presenter.getView().getMnuUsuario().setVisible(true);
        presenter.getView().getMnuConfiguracoes().setVisible(true);
        presenter.getView().getBtnNotificacoes().setEnabled(true);
        

    }

    @Override
    public void logout() {
        this.getPresenter().setState(new UsuarioLoggedOutState(this.getPresenter()));
    }

}
