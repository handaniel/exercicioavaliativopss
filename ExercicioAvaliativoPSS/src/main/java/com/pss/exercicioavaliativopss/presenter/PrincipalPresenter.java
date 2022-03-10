package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.NotificacaoDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.factory.Logger.LoggerCSV;
import com.pss.exercicioavaliativopss.factory.Logger.LoggerJSON;
import com.pss.exercicioavaliativopss.factory.Logger.LoggerXML;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.Notificacao;
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
        //Logger padrão CSV
        logger = new LoggerCSV();
        nDao = new NotificacaoDAO();

        setState(new UsuarioLoggedOutState(this));

        view.getMnuListUsuario().addActionListener((ActionEvent ae) -> {
            new ListarUsuariosPresenter((Admin) usuario, view.getDesktop(), logger).addObserver(this);
        });

        view.getMnuLogs().addActionListener((ActionEvent ae) -> {
            new ConfiguracoesPresenter(view.getDesktop(), logger).addObserver(this);
        });

        view.getMnuAlterar().addActionListener((ActionEvent ae) -> {
            new CadastroPresenter(view.getDesktop(), Admin.class.isInstance(usuario), usuario, true, logger).addObserver(this);
        });

        view.getBtnNotificacoes().addActionListener((ActionEvent ae) -> {
            new NotificacoesPresenter(view.getDesktop(), usuario, logger).addObserver(this);
        });

        view.getMnuLogout().addActionListener((ActionEvent ae) -> {
            logout();
        });

        view.setVisible(true);
    }

    public void usuarioLayout() {
        view.getLblTipoUser().setText("Usuário");
        view.getLblUser().setText(usuario.getUsername());
        view.getBtnNotificacoes().setText("Notificações");
        view.getMnuUsuario().setVisible(true);
        view.getMnuConfiguracoes().setVisible(true);
        view.getBtnNotificacoes().setEnabled(true);
        atualizaNotificacao();
    }

    public void adminLayout() {
        view.getLblTipoUser().setText("Admin");
        view.getLblUser().setText(usuario.getUsername());
        view.getBtnNotificacoes().setText("Notificações");
        view.getMnuAdmin().setVisible(true);
        view.getMnuUsuario().setVisible(true);
        view.getMnuConfiguracoes().setVisible(true);
        view.getBtnNotificacoes().setEnabled(true);
        view.getBtnNotificacoes().setText("Notificações");
        atualizaNotificacao();
    }

    private void login() {
        new LoginPresenter(view.getDesktop(), logger).addObserver(this);
    }

    private void atualizaNotificacao() {
        int qtd = nDao.contaNotificacaoNaoLida(usuario.getId());
        view.getBtnNotificacoes().setText(qtd + " Notificações");
    }

    private void update() {
        atualizaNotificacao();
    }

    private void update(UsuarioModel usuario) {
        this.usuario = usuario;
        state.login(usuario);
    }

    private void update(InterfaceLogger logger) {
        this.logger = logger;

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

    public InterfaceLogger getLogger() {
        return logger;
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
            System.out.println(obj.getClass());
            if (LoggerCSV.class.isInstance(obj)) {
                update((LoggerCSV) obj);
            } else if (LoggerJSON.class.isInstance(obj)) {
                update((LoggerJSON) obj);
            } else if (LoggerXML.class.isInstance(obj)) {
                update((LoggerXML) obj);
            }
        } else if (Notificacao.class.isInstance(obj)) {
            update();
        }

    }
}
