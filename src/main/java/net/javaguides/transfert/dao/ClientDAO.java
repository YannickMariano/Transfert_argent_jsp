package net.javaguides.transfert.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.transfert.model.Client;

public class ClientDAO {
	
	private String jdbcURL = "jdbc:postgresql://localhost:5432/JSP";
	private String jdbcUsername = "postgres";
	private String jdbcPassword = "admin";
	
	private static final String INSERT_CLIENTS_SQL = "INSERT INTO client (num_tel, nom, sexe, pays, solde, mail) VALUES (?, ?, ?, ?, ?, ?);";
	private static final String SELECT_CLIENT_BY_NAME = "SELECT num_tel, nom, sexe, pays, solde, mail FROM client WHERE nom = ?";
	private static final String SELECT_CLIENT_BY_ID = "SELECT id_client, num_tel, nom, sexe, pays, solde, mail FROM client WHERE id_client = ?";
	private static final String SELECT_ALL_CLIENTS = "SELECT * FROM client";
	private static final String DELETE_CLIENTS_SQL = "DELETE FROM client WHERE id_client = ?";
	private static final String UPDATE_CLIENTS_SQL = "UPDATE client set num_tel = ?, nom = ?, sexe = ?, pays = ?, solde = ?, mail = ? WHERE id_client = ?";
	private static final String SELECT_CLIENT_BY_NUM_TEL = "SELECT num_tel, nom, sexe, pays, solde, mail FROM client WHERE num_tel = ?";
	private static final String SEARCH_CLIENT_BY_NAME = "SELECT * FROM client WHERE nom LIKE ?";
	private static final String SELECT_PAYS_CLIENT = "SELECT pays FROM client WHERE id_client = ?";
	private static final String GET_SOLDE_SQL = "SELECT solde FROM client WHERE id_client = ?";
	private static final String DECREMENT_EXEMPLAIRES_SQL = "UPDATE client SET solde = solde - ? WHERE id_client = ?";
	
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
	
	
	// INSERT CLIENT
	public void insertClient(Client client) throws SQLException {
		try(Connection connection = getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT_CLIENTS_SQL)){
			preparedStatement.setString(1, client.getNumtel());
			preparedStatement.setString(2, client.getNom());
			preparedStatement.setString(3, client.getSexe());
			preparedStatement.setString(4, client.getPays());
			preparedStatement.setDouble(5, client.getSolde());
			preparedStatement.setString(6, client.getMail());
			
			preparedStatement.executeUpdate();			
		}catch(Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
	
	// UPDATE USER
		public boolean updateClient(Client client) throws SQLException {
			boolean rowUpdated;
			try(Connection connection = getConnection();
					PreparedStatement statement = connection.prepareStatement(UPDATE_CLIENTS_SQL)){
				statement.setString(1, client.getNumtel());
				statement.setString(2, client.getNom());
				statement.setString(3, client.getSexe());
				statement.setString(4, client.getPays());
				statement.setDouble(5, client.getSolde());
				statement.setString(6, client.getMail());
				statement.setInt(7, client.getId());
				
				rowUpdated = statement.executeUpdate() > 0;			
			}
			return rowUpdated;
		}
		
		// SELECT USER BY ID
		public Client selectClientById(int id) {
			Client client = null;
			
			// Creer Connection
			try(Connection connection = getConnection();
					
					// Creer statement en utilisant conneciton objet
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_ID);){
				preparedStatement.setInt(1, id);
				// System.out.println(preparedStatement);
				
				// Execute Query or Update Query
				ResultSet rs = preparedStatement.executeQuery();
				
				// Process the ResultSet object
				while(rs.next()) {
					int id1 = rs.getInt("id_client");
					String num_tel = rs.getString("num_tel");
					String nom = rs.getString("nom");
					String sexe = rs.getString("sexe");
					String pays = rs.getString("pays");
					Double solde = rs.getDouble("solde");
					String mail = rs.getString("mail");
					client = new Client(id1, num_tel, nom, sexe, pays, solde, mail);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			// System.out.println(client);
			return client;		
		}
		
		// SELECT USER BY NAME
		public Client selectClientByName(String name) {
			Client client = null;
			 
			// Creer Connection
			try(Connection connection = getConnection();
					
					// Creer statement en utilisant conneciton objet
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_NAME);){
				preparedStatement.setString(2, name);
				System.out.println(preparedStatement);
				
				// Execute Query or Update Query
				ResultSet rs = preparedStatement.executeQuery();
				
				// Process the ResultSet object
				while(rs.next()) {
					String num_tel = rs.getString("num_tel");
					String nom = rs.getString("nom");
					String sexe = rs.getString("sexe");
					String pays = rs.getString("pays");
					Double solde = rs.getDouble("solde");
					String mail = rs.getString("mail");
					client = new Client(num_tel, nom, sexe, pays, solde, mail);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			System.out.println(client);
			return client;		
		}
		
		// SELECT USER BY NUM TEL
		public Client selectClientByNumTel(String numtel) {
			Client client = null;
			
			// Creer Connection
			try(Connection connection = getConnection();
					
					// Creer statement en utilisant conneciton objet
					PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CLIENT_BY_NUM_TEL);){
				preparedStatement.setString(1, numtel);				
				// Execute Query or Update Query
				ResultSet rs = preparedStatement.executeQuery();
				
				// Process the ResultSet object
				while(rs.next()) {
					String num_tel = rs.getString("num_tel");
					String nom = rs.getString("nom");
					String sexe = rs.getString("sexe");
					String pays = rs.getString("pays");
					Double solde = rs.getDouble("solde");
					String mail = rs.getString("mail");
					client = new Client(num_tel, nom, sexe, pays, solde, mail);
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return client;		
		}
		
		// SELECT ALL USER
		public List<Client> selectAllClients() {
			List<Client> clients = new ArrayList<>();
			
			// Creer Connection
			try(Connection connection = getConnection();
					
				// Creer statement en utilisant conneciton objet
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENTS);){
			
				// Execute Query or Update Query
				ResultSet rs = preparedStatement.executeQuery();
				
				// Process the ResultSet object
				while(rs.next()) {
					int id = rs.getInt("id_client");
					String num_tel = rs.getString("num_tel");
					String nom = rs.getString("nom");
					String sexe = rs.getString("sexe");
					String pays = rs.getString("pays");
					Double solde = rs.getDouble("solde");
					String mail = rs.getString("mail");
					
					clients.add(new Client(id, num_tel, nom, sexe, pays, solde, mail));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return clients;		
		}
		
		// DELETE USER
		public boolean deleteClient(int id) throws SQLException{
			boolean rowDeleted;
			try(Connection connection = getConnection();
					PreparedStatement statement = connection.prepareStatement(DELETE_CLIENTS_SQL);){
				statement.setInt(1, id);
				rowDeleted = statement.executeUpdate() > 0;
			}
			return rowDeleted;					
		}
		
		
		// RECHERCHE DE CLIENT PAR LE NOM
		public List<Client> searchClientByName(String nom) throws SQLException{
			List<Client> listClient = new ArrayList<>();
			
			try(Connection connection = getConnection();
					PreparedStatement statement = connection.prepareStatement(SEARCH_CLIENT_BY_NAME);){
				statement.setString(1, "%" + nom + "%");
				ResultSet rs = statement.executeQuery();
				
				while(rs.next()) {
					int id = rs.getInt("id_client");
					String num_tel = rs.getString("num_tel");
					String nom1 = rs.getString("nom");
					String sexe = rs.getString("sexe");
					String pays = rs.getString("pays");
					Double solde = rs.getDouble("solde");
					String mail = rs.getString("mail");
					
					listClient.add(new Client(id, num_tel, nom1, sexe, pays, solde, mail));
				}
			}catch(SQLException e) {
				e.printStackTrace();
				System.out.println("SQL recherche Client: " + e);
			}
			return listClient;				
		}		
		
		
		// ENVOYER
		public String selectPays(int id_client) throws ClassNotFoundException, SQLException{
			
	        try (Connection connection = getConnection();
		             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_PAYS_CLIENT)) {
		            preparedStatement.setInt(1,id_client );
		            ResultSet rs = preparedStatement.executeQuery();
		            if(rs.next()) {
		            	
		            		return rs.getString("pays");
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
		
		
		
		public class InsufficientBalanceException extends Exception {
		    public InsufficientBalanceException(String message) {
		        super(message);
		    }
		}
		
		
		public void decrementMontant(int id_client, Double montant, Double frais_1) throws SQLException, ClassNotFoundException {
		    Double soldeActuel = (double) getSolde(id_client);
		    Double montantTotal = montant + frais_1;
		    
		    if (soldeActuel < montantTotal) {
		        throw new SQLException("Solde insuffisant pour effectuer cette transaction.");
		    }
		    
		    try (Connection connection = getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement(DECREMENT_EXEMPLAIRES_SQL)) {
		        preparedStatement.setDouble(1, montantTotal);
		        preparedStatement.setInt(2, id_client);
		        preparedStatement.executeUpdate();
		        System.out.println("Montant decremented successfully");
		    } catch (SQLException e) {
		        printSQLException(e);
		        throw e;
		    }
		}
		
		
		public int getSolde(int id_client) throws SQLException, ClassNotFoundException {		    
		    try (Connection connection = getConnection();
		         PreparedStatement preparedStatement = connection.prepareStatement(GET_SOLDE_SQL)) {
		        preparedStatement.setInt(1, id_client);
		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            if (resultSet.next()) {
		                return resultSet.getInt("solde");
		            } else {
		                throw new SQLException("Client not found with id: " + id_client);
		            }
		        }
		    }
		}
		
		
		public void augmentationMontant(int id_client, Double montant ) throws SQLException, ClassNotFoundException {
	        
	        try (Connection connection = getConnection();
	             PreparedStatement preparedStatement = connection.prepareStatement(DECREMENT_EXEMPLAIRES_SQL)) {
	            preparedStatement.setDouble(1, montant);
	            preparedStatement.setInt(2, id_client);
	            preparedStatement.executeUpdate();
	            System.out.println("Montant augmenter avec succe");
	        } catch (SQLException e) {
	            printSQLException(e);
	            throw e;
	        }
	    }
		
		
		
		
}
