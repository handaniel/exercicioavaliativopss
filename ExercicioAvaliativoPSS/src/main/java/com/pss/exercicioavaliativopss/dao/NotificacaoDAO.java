package com.pss.exercicioavaliativopss.dao;

import com.pss.exercicioavaliativopss.factory.Database.DBConnection;
import com.pss.exercicioavaliativopss.model.Notificacao;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class NotificacaoDAO {

    public static void criarTabelaNotificacao() {
        String query = "create table if not exists notificacao("
                + "id integer primary key autoincrement, "
                + "destinatario integer not null references usuario (id), "
                + "remetente integer not null references usuario (id), "
                + "mensagem varchar not null, "
                + "lida integer default 0,"
                + "data date not null)";

        try {
            Connection conn = DBConnection.connect();
            Statement stmt = conn.createStatement();

            stmt.execute(query);

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar tabela notificao!" + e.getMessage());
        }
    }

    public int contaNotificacao(int id) {
        String query = "select count(1) as num from notificacao "
                + "where destinatario = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            int num = 0;

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                num = res.getInt("num");
            }

            res.close();
            stmt.close();
            conn.close();

            return num;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar notificações!" + e.getMessage());
        }

    }

    public int contaNotificacaoLida(int id) {
        String query = "select count(1) as num from notificacao "
                + "where destinatario = ? and lida = 1";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            int num = 0;

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                num = res.getInt("num");
            }

            res.close();
            stmt.close();
            conn.close();

            return num;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar notificações!" + e.getMessage());
        }

    }

    public int contaNotificacaoNaoLida(int id) {
        String query = "select count(1) as num from notificacao "
                + "where destinatario = ? and lida = 0";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);

            int num = 0;

            ResultSet res = stmt.executeQuery();

            if (res.next()) {
                num = res.getInt("num");
            }

            res.close();
            stmt.close();
            conn.close();

            return num;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao contar notificações!" + e.getMessage());
        }

    }

    public void marcarComoLida(int id) {
        String query = "update notificacao "
                + "set lida = 1 "
                + "where id = ? ";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao marcar notificação como lida!" + e.getMessage());
        }
    }

    public void inserirNotificacao(Notificacao notificacao) {
        String query = "insert into notificacao(destinatario, remetente, mensagem, data) "
                + "values( ?, ?, ?, ?)";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);

            stmt.setInt(1, notificacao.getDestinatario());
            stmt.setInt(2, notificacao.getRemetente());
            stmt.setString(3, notificacao.getMensagem());
            stmt.setDate(4, Date.valueOf(notificacao.getDataEnvio()));

            stmt.executeUpdate();

            stmt.close();
            conn.close();

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao inserir notificação!" + e.getMessage());
        }
    }

    public ArrayList<Notificacao> getNotificacoes(int destinatario) {
        String query = "select * from notificacao "
                + "where destinatario = ? "
                + "order by lida asc";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, destinatario);

            ArrayList<Notificacao> lista = new ArrayList<>();

            ResultSet res = stmt.executeQuery();

            while (res.next()) {
                int id = res.getInt("id");
                int des = res.getInt("destinatario");
                int rem = res.getInt("remetente");
                String msg = res.getString("mensagem");
                boolean lida = res.getInt("lida") == 1;
                LocalDate data = res.getDate("data").toLocalDate();

                lista.add(new Notificacao(id, des, rem, msg, lida, data));

            }

            return lista;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar notificações! " + e.getMessage());
        }
    }

    public Notificacao getById(int id) {
        String query = "select * from notificacao "
                + "where id = ?";

        try {
            Connection conn = DBConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);

            ResultSet res = stmt.executeQuery();

            Notificacao notificacao = null;

            if (res.next()) {
                notificacao = new Notificacao(res.getInt("id"),
                        res.getInt("destinatario"),
                        res.getInt("remetente"),
                        res.getString("mensagem"),
                        res.getInt("lida") == 1,
                        res.getDate("data").toLocalDate()
                );
            }

            res.close();
            stmt.close();
            conn.close();

            return notificacao;
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao recuperar notificação!" + e.getMessage());
        }
    }

}
