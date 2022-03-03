package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.Usuario;
import com.pss.exercicioavaliativopss.view.CadastroLoginView;
import com.pss.exercicioavaliativopss.view.CadastroView;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JOptionPane;

public class CadastroLoginPresenter {

    private final CadastroLoginView view;
    private final UsuarioDAO dao;

    public CadastroLoginPresenter(boolean primeiro) {
        view = new CadastroLoginView();
        dao = new UsuarioDAO();

        if (primeiro) {
            view.getCkbAdmin().setVisible(true);
            view.getCkbAdmin().setSelected(true);
            view.getCkbAdmin().setEnabled(false);
        } else {
            view.getCkbAdmin().setVisible(false);
        }

        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            new LoginPresenter();
            view.dispose();
        });

        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            salvar();
            new LoginPresenter();
            view.dispose();
        });

        view.setVisible(true);
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

                JOptionPane.showMessageDialog(view, "Usuário " + nome + " cadastrado com sucesso!");

            } catch (RuntimeException e) {
                JOptionPane.showMessageDialog(view, "Erro ao cadastrar usuário!");
            }
        }

    }

    public CadastroLoginView getView() {
        return view;
    }

}
