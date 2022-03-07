package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.LoginView;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class LoginPresenter implements InterfaceObservable {

    private final LoginView view;
    private final UsuarioDAO dao;
    private final ArrayList<InterfaceObserver> observers;

    public LoginPresenter(JDesktopPane desktop) {
        view = new LoginView();
        dao = new UsuarioDAO();
        observers = new ArrayList<>();

        view.getBtnLogin().addActionListener(((ActionEvent ae) -> {
            login();
        }));

        view.getBtnCadastrar().addActionListener((ActionEvent ae) -> {
            cadastrar(desktop);
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void cadastrar(JDesktopPane desktop) {
        if (UsuarioDAO.contaUsuarios() == 0) {
            new CadastroLoginPresenter(desktop, this, true);
        } else {
            new CadastroLoginPresenter(desktop, this, false);
        }

        view.dispose();

    }

    private void login() {

        String username = view.getTxtUsername().getText();
        String senha = String.valueOf(view.getTxtSenha().getPassword());

        if (username.isBlank() || username.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nome de usuário inválido!");
        } else if (senha.isEmpty() || senha.isBlank()) {
            JOptionPane.showMessageDialog(view, "Senha inválida!");
        } else {

            try {
                UsuarioModel usuario = dao.login(username, senha);

                if (usuario == null) {
                    JOptionPane.showMessageDialog(view, "Erro ao efetuar login!");
                } else if (UsuarioDAO.isAutorizado(usuario.getId())) {
                    notifyObserver((UsuarioModel) usuario);
                    view.dispose();
                } else {
                    JOptionPane.showMessageDialog(view, "Usuário não autorizado!\n"
                            + "Aguardar autorização de Administrador!");
                }

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, e.getMessage());
            }
        }

    }

    public ArrayList<InterfaceObserver> getObservers() {
        return observers;
    }

    @Override
    public void addObserver(InterfaceObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(InterfaceObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver(Object obj) {
        for (InterfaceObserver o : observers) {
            o.update((UsuarioModel) obj);
        }
    }
}
