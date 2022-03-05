package com.pss.exercicioavaliativopss.dao;

import com.pss.exercicioavaliativopss.factory.Database.DBConnection;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class NotificacaoDAO {

    public static void criarTabelaNotificacao() {
        String query = "create table if not exists notificacao("
                + "id integer primary key autoincrement, "
                + "destinatario integer not null references usuario (id), "
                + "remetente integer not null references usuario (id), "
                + "mensagem varchar not null, "
                + "lida integer not null default 0,"
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

}
