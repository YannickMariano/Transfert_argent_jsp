package net.javaguides.transfert.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import net.javaguides.transfert.model.Client;

public class TransfertDAO {
	private String jdbcURL = "jdbc:postgresql://localhost:5432/JSP";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "#01YannicK#";
	
	private static final String UPDATE_SOLDE = "UPDATE client SET solde = ? WHERE num_tel = ?";
	
	protected Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
		}catch (SQLException e) {
			e.printStackTrace();
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void updateSolde(String numtel, Double newSolde) throws SQLException{
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_SOLDE);){
			statement.setDouble(1, newSolde);
			statement.setString(2, numtel);
			statement.executeUpdate();
		}
	}
	
	public Client getClientByNumTel(String numTel) throws SQLException {
		Connection connection = getConnection();
        String query = "SELECT * FROM client WHERE num_tel = ?";
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, numTel);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            Client client = new Client(0, query, query, query, query, null, query);
            
            client.setId(resultSet.getInt("id_client"));
            client.setNumtel(resultSet.getString("num_tel"));
            client.setNom(resultSet.getString("nom"));
            client.setSexe(resultSet.getString("sexe"));
            client.setPays(resultSet.getString("pays"));
            client.setSolde(resultSet.getDouble("solde"));
            client.setMail(resultSet.getString("mail"));
            return client;
        }
        return null;
    }

}
