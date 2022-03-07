package com.pss.exercicioavaliativopss.model.state;

import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.presenter.LoginPresenter;
import com.pss.exercicioavaliativopss.presenter.PrincipalPresenter;

public class UsuarioLoggedOutState extends LoginState {

    public UsuarioLoggedOutState(PrincipalPresenter presenter) {
        super(presenter);
        presenter.setUsuario(null);
        presenter.getView().getLblUser().setText("");
        presenter.getView().getLblTipoUser().setText("");
        presenter.getView().getBtnNotificacoes().setText("Notificações");
        presenter.getView().getMnuAdmin().setVisible(false);
        presenter.getView().getMnuUsuario().setVisible(false);
        presenter.getView().getMnuConfiguracoes().setVisible(false);
        presenter.getView().getBtnNotificacoes().setEnabled(false);
        new LoginPresenter(presenter.getView().getDesktop()).addObserver(presenter);
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
