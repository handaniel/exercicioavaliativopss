package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceUsuarioObserver;
import com.pss.exercicioavaliativopss.model.state.LoginState;
import com.pss.exercicioavaliativopss.view.PrincipalView;
import java.awt.event.ActionEvent;

public class PrincipalPresenter implements InterfaceUsuarioObserver {

    private final PrincipalView view;
    private UsuarioModel usuario;
    private LoginState state;
    private int numUsuarios;

    public PrincipalPresenter(UsuarioModel usuario) {
        view = new PrincipalView();

        if (Admin.class.isInstance(usuario)) {
            view.getLblTipoUser().setText("Admin");
            view.getMnuAdmin().setVisible(true);
        } else {
            view.getLblTipoUser().setText("Usuário");
        }
        view.getLblUser().setText(usuario.getUsername());

        view.getMnuListUsuario().addActionListener((ActionEvent ae) -> {
            view.getDesktop().add(new ListarUsuariosPresenter(usuario.getUsername()).getView());
        });

        view.getMnuLogout().addActionListener((ActionEvent ae) -> {
            new LoginPresenter();
            view.dispose();
        });

        view.setVisible(true);
    }

    public void setState(LoginState state) {
        this.state = state;
    }

    public PrincipalView getView() {
        return view;
    }

    public UsuarioModel getUsuario() {
        return usuario;
    }

}
