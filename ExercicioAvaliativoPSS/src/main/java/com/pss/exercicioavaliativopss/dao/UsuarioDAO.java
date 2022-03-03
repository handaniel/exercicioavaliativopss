package com.pss.exercicioavaliativopss.dao;

import com.pss.exercicioavaliativopss.factory.Database.DBConnection;
import com.pss.exercicioavaliativopss.model.Admin;
import com.pss.exercicioavaliativopss.model.Usuario;
import com.pss.exercicioavaliativopss.model.UsuarioModel;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class UsuarioDAO {

    public static void criarTabelaUsuario() {
        String query = "create table if not exists usuario("
                + "id integer primary key autoincrement,"
                + "nome varchar not null,"
                + "username varchar not null unique,"
                + "senha varchar not null,"
                + "cadastro date not null,"
                + "admin int default 0,"
                + "autorizado int default 0)";

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar database." + e.getMessage());
        }

    }

    public static int contaUsuarios() {
        String query = "select count(1) as num from usuario";

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            int num = 0;

            ResultSet res = stmt.executeQuery(query);
            if (res.next()) {
                num = res.getInt("num");
            }

            res.close();
            stmt.close();
            conn.close();

            return num;

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao acessar database");

        }
    }

    public boolean verificaUsername(String username) {
        String query = "select count(1) as num from usuario where username = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            int num = 0;

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                num = res.getInt("num");
            }

            res.close();
            stmt.close();
            conn.close();

            return num != 0;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar nome de usuário. " + e.getMessage());
        }

    }

    public void delete(String username) {
        String query = "delete from usuario where lower(username) = lower(?)";

        try {

            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar" + e.getMessage());
        }

    }

    public void inserir(UsuarioModel novo) {
        String query = "insert into usuario(nome, username, senha, cadastro, admin, autorizado)"
                + "values(?,?,?,?,?,?)";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            //Variável para verificar se o perfil é admin e está autorizado
            int aux = 0;

            if (Admin.class.isInstance(novo)) {
                aux = 1;
            }

            stmt.setString(1, novo.getNome());
            stmt.setString(2, novo.getUsername());
            stmt.setString(3, novo.getSenha());
            stmt.setDate(4, Date.valueOf(novo.getDataCadastro()));
            stmt.setInt(5, aux);
            stmt.setInt(6, aux);

            stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao adicionar usuário." + e.getMessage());

        }
    }

    public UsuarioModel login(String username, String senha) {
        String query = "select * from usuario where lower(username) = lower(?)"
                + "and senha = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            UsuarioModel usuario = null;

            stmt.setString(1, username);
            stmt.setString(2, senha);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                int id = res.getInt("id");
                String nome = res.getString("nome");
                username = res.getString("username");
                senha = res.getString("senha");
                LocalDate data = res.getDate("cadastro").toLocalDate();
                boolean admin = res.getInt("admin") == 1;
                boolean autorizado = res.getInt("autorizado") == 1;

                if (admin) {
                    usuario = new Admin(id, nome, username, senha, data);
                } else {
                    usuario = new Usuario(id, nome, username, senha, data, autorizado);
                }
            }

            res.close();
            stmt.close();
            conn.close();

            return usuario;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao fazer login." + e.getMessage());
        }
    }

    public static boolean isAutorizado(String username) {
        String query = "select autorizado as aut from usuario where lower(username) = lower(?)";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            int autorizacao = 0;

            stmt.setString(1, username);

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                autorizacao = res.getInt("aut");
            }

            res.close();
            stmt.close();
            conn.close();

            return autorizacao == 1;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao verificar autorização!" + e.getMessage());
        }
    }

    public void autorizar(String username) {
        String query = "update usuario set autorizado = 1 where lower(username) = lower(?)";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setString(1, username);

            stmt.executeUpdate();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao autorizar!" + e.getMessage());
        }
    }

    public ArrayList<UsuarioModel> listarUsuarios() {
        String query = "select * from usuario";

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);

            ArrayList<UsuarioModel> lista = new ArrayList<>();

            while (res.next()) {
                UsuarioModel usuario = null;

                int id = res.getInt("id");
                String nome = res.getString("nome");
                String username = res.getString("username");
                String senha = res.getString("senha");
                LocalDate data = res.getDate("cadastro").toLocalDate();
                boolean admin = res.getInt("admin") == 1;
                boolean autorizado = res.getInt("autorizado") == 1;

                if (admin) {
                    usuario = new Admin(id, nome, username, senha, data);
                } else {
                    usuario = new Usuario(id, nome, username, senha, data, autorizado);
                }

                lista.add(usuario);

            }
            res.close();
            stmt.close();
            conn.close();

            if (lista.isEmpty()) {

                return null;
            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar usuários." + e.getMessage());
        }
    }

}
