/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.pss.exercicioavaliativopss.view;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

/**
 *
 * @author handaniels
 */
public class PrincipalView extends javax.swing.JFrame {

    /**
     * Creates new form PrincipalView
     */
    public PrincipalView() {
        initComponents();
        mnuAdmin.setVisible(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        desktop = new javax.swing.JDesktopPane();
        jLabel1 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblTipoUser = new javax.swing.JLabel();
        btnNotificacoes = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        mnuUsuario = new javax.swing.JMenu();
        mnuLogin = new javax.swing.JMenuItem();
        mnuAlterar = new javax.swing.JMenuItem();
        mnuLogout = new javax.swing.JMenuItem();
        mnuConfiguracoes = new javax.swing.JMenu();
        mnuLogs = new javax.swing.JMenuItem();
        mnuAdmin = new javax.swing.JMenu();
        mnuAddUsuario = new javax.swing.JMenuItem();
        mnuListUsuario = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        javax.swing.GroupLayout desktopLayout = new javax.swing.GroupLayout(desktop);
        desktop.setLayout(desktopLayout);
        desktopLayout.setHorizontalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        desktopLayout.setVerticalGroup(
            desktopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 631, Short.MAX_VALUE)
        );

        jLabel1.setText("Usuário Logado:");

        jLabel3.setText("Tipo:");

        btnNotificacoes.setText("Notificações");

        mnuUsuario.setText("Usuario");

        mnuLogin.setText("Login");
        mnuUsuario.add(mnuLogin);

        mnuAlterar.setText("Alterar dados");
        mnuUsuario.add(mnuAlterar);

        mnuLogout.setText("Logout");
        mnuUsuario.add(mnuLogout);

        jMenuBar1.add(mnuUsuario);

        mnuConfiguracoes.setText("Configurações");

        mnuLogs.setText("Logs");
        mnuConfiguracoes.add(mnuLogs);

        jMenuBar1.add(mnuConfiguracoes);

        mnuAdmin.setText("Admin");

        mnuAddUsuario.setText("Adicionar Usuário");
        mnuAddUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuAddUsuarioActionPerformed(evt);
            }
        });
        mnuAdmin.add(mnuAddUsuario);

        mnuListUsuario.setText("Listar Usuarios");
        mnuAdmin.add(mnuListUsuario);

        jMenuBar1.add(mnuAdmin);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(desktop)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser)
                .addGap(120, 120, 120)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTipoUser)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 705, Short.MAX_VALUE)
                .addComponent(btnNotificacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(desktop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lblUser)
                    .addComponent(jLabel3)
                    .addComponent(lblTipoUser)
                    .addComponent(btnNotificacoes))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuAddUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuAddUsuarioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mnuAddUsuarioActionPerformed

    public JButton getBtnNotificacoes() {
        return btnNotificacoes;
    }

    public void setBtnNotificacoes(JButton btnNotificacoes) {
        this.btnNotificacoes = btnNotificacoes;
    }

    public JDesktopPane getDesktop() {
        return desktop;
    }

    public void setDesktop(JDesktopPane desktop) {
        this.desktop = desktop;
    }

    public JLabel getLblTipoUser() {
        return lblTipoUser;
    }

    public void setLblTipoUser(JLabel lblTipoUser) {
        this.lblTipoUser = lblTipoUser;
    }

    public JLabel getLblUser() {
        return lblUser;
    }

    public void setLblUser(JLabel lblUser) {
        this.lblUser = lblUser;
    }

    public JMenuItem getMnuAddUsuario() {
        return mnuAddUsuario;
    }

    public void setMnuAddUsuario(JMenuItem mnuAddUsuario) {
        this.mnuAddUsuario = mnuAddUsuario;
    }

    public JMenu getMnuAdmin() {
        return mnuAdmin;
    }

    public void setMnuAdmin(JMenu mnuAdmin) {
        this.mnuAdmin = mnuAdmin;
    }

    public JMenuItem getMnuAlterar() {
        return mnuAlterar;
    }

    public void setMnuAlterar(JMenuItem mnuAlterar) {
        this.mnuAlterar = mnuAlterar;
    }

    public JMenu getMnuConfiguracoes() {
        return mnuConfiguracoes;
    }

    public void setMnuConfiguracoes(JMenu mnuConfiguracoes) {
        this.mnuConfiguracoes = mnuConfiguracoes;
    }

    public JMenuItem getMnuListUsuario() {
        return mnuListUsuario;
    }

    public void setMnuListUsuario(JMenuItem mnuListUsuario) {
        this.mnuListUsuario = mnuListUsuario;
    }

    public JMenuItem getMnuLogin() {
        return mnuLogin;
    }

    public void setMnuLogin(JMenuItem mnuLogin) {
        this.mnuLogin = mnuLogin;
    }

    public JMenuItem getMnuLogout() {
        return mnuLogout;
    }

    public void setMnuLogout(JMenuItem mnuLogout) {
        this.mnuLogout = mnuLogout;
    }

    public JMenuItem getMnuLogs() {
        return mnuLogs;
    }

    public void setMnuLogs(JMenuItem mnuLogs) {
        this.mnuLogs = mnuLogs;
    }

    public JMenu getMnuUsuario() {
        return mnuUsuario;
    }

    public void setMnuUsuario(JMenu mnuUsuario) {
        this.mnuUsuario = mnuUsuario;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNotificacoes;
    private javax.swing.JDesktopPane desktop;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel lblTipoUser;
    private javax.swing.JLabel lblUser;
    private javax.swing.JMenuItem mnuAddUsuario;
    private javax.swing.JMenu mnuAdmin;
    private javax.swing.JMenuItem mnuAlterar;
    private javax.swing.JMenu mnuConfiguracoes;
    private javax.swing.JMenuItem mnuListUsuario;
    private javax.swing.JMenuItem mnuLogin;
    private javax.swing.JMenuItem mnuLogout;
    private javax.swing.JMenuItem mnuLogs;
    private javax.swing.JMenu mnuUsuario;
    // End of variables declaration//GEN-END:variables
}
