package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.Log;
import com.pss.exercicioavaliativopss.model.Usuario;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.CadastroLoginView;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class CadastroLoginPresenter implements InterfaceObservable {

    private final CadastroLoginView view;
    private final UsuarioDAO dao;
    private final ArrayList<InterfaceObserver> observers;
    private InterfaceLogger logger;

    public CadastroLoginPresenter(JDesktopPane desktop, LoginPresenter login, boolean primeiro, InterfaceLogger logger) {
        view = new CadastroLoginView();
        dao = new UsuarioDAO();
        observers = new ArrayList<>();
        this.logger = logger;

        if (primeiro) {
            view.getCkbAdmin().setVisible(true);
            view.getCkbAdmin().setSelected(true);
            view.getCkbAdmin().setEnabled(false);
        } else {
            view.getCkbAdmin().setVisible(false);
        }

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            salvar(login, desktop);
            view.dispose();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void salvar(LoginPresenter login, JDesktopPane desktop) {
        String nome = view.getTxtNome().getText();
        String username = view.getTxtUsername().getText();
        String senha = String.valueOf(view.getTxtSenha().getPassword());
        String senhaR = String.valueOf(view.getTxtSenhaRepeticao().getPassword());
        boolean admin = view.getCkbAdmin().isSelected();
        LocalDate data = LocalDate.now();

        if (!senha.equals(senhaR)) {
            JOptionPane.showMessageDialog(view, "Senhas não coincidem");
        } else if (dao.verificaUsername(username)) {
            JOptionPane.showMessageDialog(view, "Nome de usuário existente.");
        } else {
            try {
                UsuarioModel usuario;
                if (admin) {
                    usuario = new Admin(nome, username, senha, data);
                } else {
                    usuario = new Usuario(nome, username, senha, data, false);
                }
                dao.inserir(usuario);
                logger.logUsuarioCRUD(new Log("Cadastro", nome, username, "-"));
                JOptionPane.showMessageDialog(view, "Usuário " + nome + " cadastrado com sucesso!");

                ArrayList<InterfaceObserver> temp = login.getObservers();
                login = new LoginPresenter(desktop, logger);

                for (InterfaceObserver o : temp) {
                    login.addObserver(o);
                }

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, "Erro ao cadastrar usuário!");
                logger.logFalha(new Log("Cadstro", nome, username, e.getMessage()));
            }
        }

    }

    public CadastroLoginView getView() {
        return view;
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
