package CRM.clientes;

import Conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Cliente {
    private String name;
    private static String idClient;

    private int phone;
    private static Connection conexion;
    private static Conexion con;

    public Cliente (String name, String idClient) {
        this.name =name;
        this.idClient = idClient;
    }
    public static String getIdClient(String phone) throws SQLException {
        con = new Conexion();
        conexion = con.getConnection();
        PreparedStatement comando;
        comando = conexion.prepareStatement("Select * from clientespotenciales where Telefono="+phone);
        ResultSet dat = comando.executeQuery();
            while (dat.next()) {
                idClient = dat.getString("idCliente");
                System.out.println(idClient);
            }
            return idClient;
    }
}
