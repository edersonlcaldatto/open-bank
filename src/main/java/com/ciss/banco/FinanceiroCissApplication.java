package com.ciss.banco;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@SpringBootApplication
public class FinanceiroCissApplication {

    public static void main(String[] args) throws SQLException {
        SpringApplication.run(FinanceiroCissApplication.class, args);

        // Executa insert via jdbc, pois estava com problema ao executar script no resouces - Fins didaticos
        Connection c = DriverManager.getConnection("jdbc:h2:mem:ciss-banco","sa","");
        PreparedStatement stmt = c.prepareStatement("INSERT INTO usuario (usuario,senha,nome,email,ativo) VALUES\n" +
                "    ('ADMIN','$2a$12$f/4qHhw7cB8AITaNoRSW2O321HV.fur5T9gHVcSiP2kwr1Pks3./2','administrador', 'admin@ciss.com.br',true);");
        stmt.execute();
        stmt.close();
        c.close();

    }

}
