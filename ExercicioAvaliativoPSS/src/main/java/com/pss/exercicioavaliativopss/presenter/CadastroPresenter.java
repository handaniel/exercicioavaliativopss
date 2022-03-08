package com.pss.exercicioavaliativopss.presenter;

import com.pss.exercicioavaliativopss.dao.UsuarioDAO;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.Usuario;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import com.pss.exercicioavaliativopss.view.CadastroView;
import java.awt.event.ActionEvent;
import java.time.LocalDate;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

public class CadastroPresenter {
    
    private final CadastroView view;
    private final UsuarioDAO dao;
    
    public CadastroPresenter(JDesktopPane desktop, boolean admin) {
        view = new CadastroView();
        dao = new UsuarioDAO();
        
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
    
    public CadastroPresenter(JDesktopPane desktop, boolean admin, UsuarioModel usuario) {
        view = new CadastroView();
        dao = new UsuarioDAO();
        
        view.getCkbAdmin().setVisible(admin);
        
        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });
        
        view.getBtnSalvar().addActionListener((ActionEvent ae) -> {
            salvar();
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
    
    public CadastroView getView() {
        return view;
    }
    
}
