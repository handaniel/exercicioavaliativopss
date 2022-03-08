package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.factory.Logger.LoggerJSON;
import com.pss.exercicioavaliativopss.model.Admin;
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
    private InterfaceLogger logger;
    private NotificacaoDAO nDao;
    
    public PrincipalPresenter() {
        view = new PrincipalView();
        logger = new LoggerJSON();
        nDao = new NotificacaoDAO();
        
        setState(new UsuarioLoggedOutState(this));
        
        view.getMnuListUsuario().addActionListener((ActionEvent ae) -> {
            new ListarUsuariosPresenter((Admin) usuario, view.getDesktop(), logger);
        });
        
        view.getMnuLogs().addActionListener((ActionEvent ae) -> {
            new ConfiguracoesPresenter(view.getDesktop(), logger);
        });
        
        view.getMnuAlterar().addActionListener((ActionEvent ae) -> {
            new CadastroPresenter(view.getDesktop(), Admin.class.isInstance(usuario), usuario, true);
        });
        
        view.getBtnNotificacoes().addActionListener((ActionEvent ae) -> {
            new NotificacoesPresenter(view.getDesktop(), usuario, logger);
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
    
    private void update(InterfaceLogger logger) {
        this.logger = logger;
        
        fechaInternalFrames();
        
        setState(new UsuarioLoggedOutState(this));
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
        } else if (InterfaceLogger.class.isInstance(obj)) {
            update((InterfaceLogger) logger);
        }
        
    }
    
}
