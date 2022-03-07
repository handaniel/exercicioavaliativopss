package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.state.LoginState;
import com.pss.exercicioavaliativopss.view.PrincipalView;
import java.awt.event.ActionEvent;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.model.state.UsuarioLoggedOutState;
import javax.swing.JInternalFrame;

public class PrincipalPresenter implements InterfaceObserver {

    private final PrincipalView view;
    private UsuarioModel usuario;
    private LoginState state;
    private int numUsuarios;

    public PrincipalPresenter() {
        view = new PrincipalView();

        setState(new UsuarioLoggedOutState(this));

        view.getMnuListUsuario().addActionListener((ActionEvent ae) -> {
            new ListarUsuariosPresenter(usuario.getNome(), view.getDesktop());
        });

        view.getMnuLogs().addActionListener((ActionEvent ae) -> {

        });

        view.getMnuListUsuario().addActionListener((ActionEvent ae) -> {

        });

        view.getBtnNotificacoes().addActionListener((ActionEvent ae) -> {

        });

        view.getMnuLogout().addActionListener((ActionEvent ae) -> {
            logout();
        });

        view.setVisible(true);
    }

    private void login() {
        new LoginPresenter(view.getDesktop()).addObserver(this);
    }

    private void update(UsuarioModel usuario) {
        this.usuario = usuario;

        state.login(usuario);

    }

    private void fechaInternalFrames() {
        for (JInternalFrame j : view.getDesktop().getAllFrames()) {
            j.dispose();
        }
    }

    private void logout() {
        fechaInternalFrames();
        state.logout();
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

    public void setUsuario(UsuarioModel usuario) {
        this.usuario = usuario;
    }

    @Override
    public void update(Object obj) {
        if (UsuarioModel.class.isInstance(obj)) {
            update((UsuarioModel) obj);
        }

    }

}
