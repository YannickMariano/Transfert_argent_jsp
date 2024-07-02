package net.javaguides.transfert.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.transfert.model.Taux;

public class TauxDAO {
	
	private String jdbcURL = "jdbc:postgresql://localhost:5432/JSP";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "admin";
	
	private static final String INSERT_TAUX_SQL = "INSERT INTO taux (montant_1, montant_2) VALUES (?, ?);";
	private static final String SELECT_TAUX_BY_ID = "SELECT id_taux, montant_1, montant_2 FROM taux WHERE id_taux = ?";
	private static final String SELECT_ALL_TAUX = "SELECT * FROM taux ORDER BY id_taux ASC";
	private static final String DELETE_TAUX_SQL = "DELETE FROM taux WHERE id_taux = ?";
	private static final String UPDATE_TAUX_SQL = "UPDATE taux set montant_1 = ?, montant_2 = ? WHERE id_taux = ?";
	private static final String SELECT_TAUX_EURO = "SELECT montant_1 FROM taux ";
    private static final String SELECT_TAUX_ARIARY = "SELECT montant_2 FROM taux ";
	
	
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
	
	
	// INSERT TAUX	
	public void insertTaux(Taux taux) throws SQLException {
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TAUX_SQL)){
			preparedStatement.setDouble(1, taux.getMontant1());
			preparedStatement.setDouble(2, taux.getMontant2());
			
			preparedStatement.executeUpdate();			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
		
	// UPDATE TAUX
	public boolean updateTaux(Taux taux) throws SQLException {
		boolean rowUpdated;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(UPDATE_TAUX_SQL)){
			statement.setDouble(1, taux.getMontant1());
			statement.setDouble(2, taux.getMontant2());
			statement.setInt(3, taux.getId());
			
			rowUpdated = statement.executeUpdate() > 0;			
		}
		return rowUpdated;
	}
	
	// SELECT TAUX BY ID
	public Taux selectTauxById(int id) {
		Taux taux = null;
		
		// Creer Connection
		try(Connection connection = getConnection();
				
				// Creer statement en utilisant conneciton objet
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAUX_BY_ID);){
			preparedStatement.setInt(1, id);
			System.out.println("SelectBy ID: " + preparedStatement);
			
			// Execute Query or Update Query
			ResultSet rs = preparedStatement.executeQuery();
			
			// Process the ResultSet object
			while(rs.next()) {
				int id1 = rs.getInt("id_taux");
				Double montant1 = rs.getDouble("montant_1");
				Double montant2 = rs.getDouble("montant_2");
				
				taux = new Taux(id1, montant1, montant2);
			}
		}catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Selct Taux By ID: " + e);
		}
		
		System.out.println("edit_taux: " + taux);
		return taux;		
	}
	
	
	// SELECT ALL TAUX
	public List<Taux> selectAllTaux() {
		List<Taux> taux = new ArrayList<>();
		
		// Creer Connection
		try(Connection connection = getConnection();
				
			// Creer statement en utilisant conneciton objet
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_TAUX);){
		
			// Execute Query or Update Query
			ResultSet rs = preparedStatement.executeQuery();
			
			// Process the ResultSet object
			while(rs.next()) {
				int id = rs.getInt("id_taux");
				Double montant1 = rs.getDouble("montant_1");
				Double montant2 = rs.getDouble("montant_2");
				
				taux.add(new Taux(id, montant1, montant2));
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
//		System.out.println("Taux from DB: " + taux);
		return taux;		
	}
	
	// DELETE USER
	public boolean deleteTaux(int id) throws SQLException{
		boolean rowDeleted;
		try(Connection connection = getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE_TAUX_SQL);){
			statement.setInt(1, id);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;					
	}
	
	
	
	// ENVOYER DAO
	
	public Double selectEuro() throws ClassNotFoundException, SQLException{
		
        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAUX_EURO)) {
	            ResultSet rs = preparedStatement.executeQuery();
	            if(rs.next()) {
	            	
	            		return rs.getDouble("montant_1");
	            }
	           
	        } catch (SQLException e) {
	            printSQLException(e);
	            throw e;
	        }
		return null;
	}
	
	public Double selectAriary() throws ClassNotFoundException, SQLException{
		
        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TAUX_ARIARY)) {
	            ResultSet rs = preparedStatement.executeQuery();
	            if(rs.next()) {
	            	
	            		return rs.getDouble("montant_2");
	            }
	           
	        } catch (SQLException e) {
	            printSQLException(e);
	            throw e;
	        }
		return null;
	}
	
	
	private void printSQLException(SQLException ex) {
		for (Throwable e : ex) {
			if (e instanceof SQLException) {
				e.printStackTrace(System.err);
				System.err.println("SQLState: " + ((SQLException) e).getSQLState());
				System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
				System.err.println("Message: " + e.getMessage());
				Throwable t = ex.getCause();
				while (t != null) {
					System.out.println("Cause: " + t);
					t = t.getCause();
				}
			}
		}
	}

}
