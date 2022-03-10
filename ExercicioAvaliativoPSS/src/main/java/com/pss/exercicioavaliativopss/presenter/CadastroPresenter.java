package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.factory.Logger.InterfaceLogger;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.Log;
import com.pss.exercicioavaliativopss.model.Usuario;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObservable;
import com.pss.exercicioavaliativopss.model.interfaces.InterfaceObserver;
import com.pss.exercicioavaliativopss.view.CadastroView;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class CadastroPresenter implements InterfaceObservable {

    private final CadastroView view;
    private final UsuarioDAO dao;
    private InterfaceLogger logger;
    private ArrayList<InterfaceObserver> observers;

    public CadastroPresenter(JDesktopPane desktop, boolean admin, InterfaceLogger logger) {
        view = new CadastroView();
        dao = new UsuarioDAO();
        this.logger = logger;
        observers = new ArrayList<>();

        view.getCkbAdmin().setVisible(admin);

        view.getBtnRemover().setVisible(false);

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            salvar();
            view.dispose();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    public CadastroPresenter(JDesktopPane desktop, boolean admin, UsuarioModel usuario, boolean editar, InterfaceLogger logger) {
        view = new CadastroView();
        dao = new UsuarioDAO();
        this.logger = logger;
        observers = new ArrayList<>();

        view.getCkbAdmin().setVisible(admin);
        view.getCkbAdmin().setSelected(admin);
        view.getCkbAdmin().setEnabled(false);

        view.getBtnRemover().setVisible(false);
        view.getTxtNome().setText(usuario.getNome());
        view.getTxtUsername().setText(usuario.getUsername());

        view.getTxtSenha().setText(usuario.getSenha());
        view.getTxtSenhaRepeticao().setText(usuario.getSenha());

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            atualizar(usuario);
            view.dispose();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    public CadastroPresenter(JDesktopPane desktop, boolean admin, UsuarioModel usuario, InterfaceLogger logger) {
        view = new CadastroView();
        dao = new UsuarioDAO();
        this.logger = logger;

        view.getCkbAdmin().setVisible(admin);
        view.getCkbAdmin().setSelected(admin);
        view.getCkbAdmin().setEnabled(false);

        view.getBtnSalvar().setText("Editar");

        view.getTxtNome().setText(usuario.getNome());
        view.getTxtUsername().setText(usuario.getUsername());

        view.getTxtSenha().setText(usuario.getSenha());
        view.getTxtSenhaRepeticao().setText(usuario.getSenha());

        view.getTxtNome().setEnabled(false);
        view.getTxtUsername().setEnabled(false);
        view.getTxtSenha().setEnabled(false);
        view.getTxtSenhaRepeticao().setEnabled(false);

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            new CadastroPresenter(desktop, admin, usuario, true, logger);
            view.dispose();
        });

        view.getBtnRemover().addActionListener((ActionEvent ae) -> {
            remover(usuario);
            view.dispose();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void remover(UsuarioModel usuario) {
        try {
            dao.delete(usuario.getId());
            logger.logUsuarioCRUD(new Log("Remoção", usuario.getNome(), usuario.getUsername(), "-"));
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(view, "Erro ao deletar usuário!" + e.getMessage());
            logger.logFalha(new Log("Remoção", usuario.getNome(), usuario.getUsername(), e.getMessage()));
        }
    }

    private void atualizar(UsuarioModel usuario) {
        String nome = view.getTxtNome().getText();
        String username = view.getTxtUsername().getText();
        String senha = String.valueOf(view.getTxtSenha().getPassword());
        String senhaR = String.valueOf(view.getTxtSenhaRepeticao().getPassword());
        boolean admin = view.getCkbAdmin().isSelected();
        LocalDate data = LocalDate.now();

        if (!senha.equals(senhaR)) {
            JOptionPane.showMessageDialog(view, "Senhas não coincidem");
        } else {
            try {

                if (admin) {
                    dao.update(new Admin(usuario.getId(), nome, username, senha, data));
                } else {
                    dao.update(new Usuario(usuario.getId(), nome, username, senha, data, false));
                }

                logger.logUsuarioCRUD(new Log("Atualização", nome, username, "-"));
                JOptionPane.showMessageDialog(view, "Usuário " + nome + " atualizado com sucesso! \n Logue novamente para alterar nickname");

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, "Erro ao Atualizar usuário!" + e.getMessage());
                logger.logFalha(new Log("Atualização", nome, username, e.getMessage()));
            }
        }

    }

    private void salvar() {
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

                if (admin) {
                    dao.inserir(new Admin(nome, username, senha, data));
                } else {
                    dao.inserir(new Usuario(nome, username, senha, data, false));
                }
                logger.logUsuarioCRUD(new Log("Cadastro", nome, username, "-"));
                JOptionPane.showMessageDialog(view, "Usuário " + nome + " cadastrado com sucesso!");

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, "Erro ao cadastrar usuário!");
                logger.logFalha(new Log("Cadastro", nome, username, e.getMessage()));
            }
        }

    }

    public CadastroView getView() {
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
