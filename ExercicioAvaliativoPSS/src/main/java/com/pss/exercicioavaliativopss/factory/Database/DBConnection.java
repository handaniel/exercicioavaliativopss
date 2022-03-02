package com.pss.exercicioavaliativopss.factory.Database;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:sqlite:dados/usuarios.sqlite";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void criaDiretorio() {
        File diretorio = new File("dados/");

        if (!diretorio.exists()) {
            diretorio.mkdirs();
        }

    }

}
