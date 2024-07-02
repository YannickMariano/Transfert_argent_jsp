package net.javaguides.transfert.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.transfert.model.Client;
import net.javaguides.transfert.model.Envoyer;
import net.javaguides.transfert.util.EmailUtil;

public class EnvoyerDAO {
    private final String url = "jdbc:postgresql://localhost:5432/JSP";
    private final String user = "postgres";
    private final String password = "admin";
    

    public Connection connect() throws SQLException, ClassNotFoundException {
    	Class.forName("org.postgresql.Driver");
    	return DriverManager.getConnection(url,user,password);
    }

    public static void main(String[] args) throws ClassNotFoundException {
    	EnvoyerDAO dao = new EnvoyerDAO();
    	try(Connection connection = dao.connect()) {
    		if(connection != null) {
    			System.out.println("Connected to the database!");
    		}
    		else {
                System.out.println("Failed to connect to the database.");
            }
    	}
    	catch(SQLException e) {
            System.err.println(e.getMessage());
    	}
    }
    
    private static final String INSERT_ENVOYER_SQL = "INSERT INTO envoyer (num_envoyeur,num_recepteur,montant,date,raison) VALUES (?,?,?,now(),?)";
    private static final String SELECT_ENVOYER_BY_ID = "SELECT id_env,num_envoyeur,num_recepteur,montant,date,raison FROM envoyer WHERE id_env =?;";
    private static final String SELECT_ALL_ENVOYER = "SELECT * FROM envoyer";
    private static final String DELETE_ENVOYER_SQL = "DELETE FROM envoyer WHERE id_env =?;";
    private static final String UPDATE_ENVOYER_SQL = "UPDATE envoyer SET num_envoyeur = ? , num_recepteur =? , montant =? , raison=?  WHERE id_env = ?;";
    
    //private static final String SELECT_ALL_CLIENT = "SELECT * FROM client";
    
    public List<Envoyer> selectTransactionsByClient(int idClient) throws SQLException, ClassNotFoundException {
        List<Envoyer> transactions = new ArrayList<>();
//      String sql = "SELECT id_env, num_envoyeur, num_recepteur, montant, date, raison FROM envoyer WHERE num_envoyeur = ? OR num_recepteur = ?;";
        String sql = "SELECT envoyer.id_env, envoyer.num_envoyeur, envoyer.num_recepteur, envoyer.montant, envoyer.date, envoyer.raison,"
        		+ "	client.nom AS recepteur_nom FROM envoyer, client"
        		+ "	WHERE envoyer.num_recepteur = client.id_client"
        		+ " AND envoyer.num_envoyeur = ?";
        
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, idClient);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
            	Envoyer envoyer = new Envoyer(rs.getInt("id_env"), rs.getInt("num_envoyeur"), rs.getInt("num_recepteur"),
                        						rs.getDouble("montant"), rs.getString("date"), rs.getString("raison"), rs.getString("recepteur_nom"));
				transactions.add(envoyer);
				System.out.println("Transaction Envoyer DAO: " + envoyer);
            }
        }
        return transactions;
    }
    
    public void insertEnvoyer(Envoyer envoyer) throws SQLException, ClassNotFoundException {
        System.out.println("Insertion envoyer: " + envoyer.getNum_envoyeur() + ", " + envoyer.getNum_recepteur() + ", " + envoyer.getMontant());
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ENVOYER_SQL)) {

            ClientDAO client = new ClientDAO();
            FraisDAO frais = new FraisDAO();
            TauxDAO taux = new TauxDAO();

            String pays_envoyeur = client.selectPays(envoyer.getNum_envoyeur());
            String pays_recepteur = client.selectPays(envoyer.getNum_recepteur());

            System.out.println("Rows envoyeur: " + pays_envoyeur);
            System.out.println("Rows recepteur: " + pays_recepteur);

            Double total = envoyer.getMontant();
            if (pays_envoyeur.equals("Madagascar") && pays_recepteur.equals("France")) {
                Double taux_envoyeur = taux.selectAriary();
                Double taux_recepteur = taux.selectEuro();
                total = envoyer.getMontant() * taux_recepteur / taux_envoyeur;
            } else if (pays_envoyeur.equals("France") && pays_recepteur.equals("Madagascar")) {
                Double taux_envoyeur = taux.selectEuro();
                Double taux_recepteur = taux.selectAriary();
                total = envoyer.getMontant() * taux_recepteur / taux_envoyeur;
            } else {
                total = envoyer.getMontant();
            }

            preparedStatement.setInt(1, envoyer.getNum_envoyeur());
            preparedStatement.setInt(2, envoyer.getNum_recepteur());
            preparedStatement.setDouble(3, total);
            preparedStatement.setString(4, envoyer.getRaison());

            Double frais_1 = frais.getFraisByMontant((Double) envoyer.getMontant());

            try {
                client.decrementMontant(envoyer.getNum_envoyeur(), envoyer.getMontant(), frais_1);
                client.augmentationMontant(envoyer.getNum_recepteur(), total);

                int rowsInserted = preparedStatement.executeUpdate();
                System.out.println("Rows inserted: " + rowsInserted);

                if (rowsInserted > 0) {
                    Client envoyeur = client.selectClientById(envoyer.getNum_envoyeur());
                    Client recepteur = client.selectClientById(envoyer.getNum_recepteur());
                    
                    String subject = "Notification de Transaction";
                    String bodyEnvoyeur = "Cher " + envoyeur.getNom() + ",\n\nVous avez envoyé " + envoyer.getMontant() + " à " + recepteur.getNom() + ".\nMerci d'utiliser notre service.";
                    EmailUtil.sendEmail(envoyeur.getMail(), subject, bodyEnvoyeur);
                    
                    String bodyRecepteur = "Cher " + recepteur.getNom() + ",\n\nVous avez reçu " + envoyer.getMontant() + " de " + envoyeur.getNom() + ".\nMerci d'utiliser notre service.";
                    EmailUtil.sendEmail(recepteur.getMail(), subject, bodyRecepteur);
                }

            } catch (SQLException e) {
                System.err.println("Transaction failed: " + e.getMessage());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'envoie", e);
        }
    }

    public List<Client> selectAllMembres() throws ClassNotFoundException {
        List<Client> clients = new ArrayList<>();
        String SELECT_ALL_CLIENT = "SELECT id_client, num_tel FROM client";
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CLIENT)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id_client = rs.getInt("id_client");
                String num_tel = rs.getString("num_tel");                
            
                clients.add(new Client(id_client, num_tel));
            }
        } catch (SQLException e) {
            printSQLException(e);
        }
        System.out.println("Clients: " + clients);
        return clients;
    }
    
    public Envoyer selectEnvoyer(int id_env) throws SQLException, ClassNotFoundException {
    	Envoyer total = null;
    	try(Connection connection = connect();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ENVOYER_BY_ID);) {
    		preparedStatement.setInt(1, id_env);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int num_envoyeur = rs.getInt("num_envoyeur");
				int num_recepteur = rs.getInt("num_recepteur");
				Double montant = rs.getDouble("montant");
				String date = rs.getString("date");
				String raison = rs.getString("raison");
				total = new Envoyer(id_env, num_envoyeur, num_recepteur, montant,date,raison);
			}
    	}
    	catch(SQLException e){
	          e.printStackTrace();
	          throw new SQLException("erreur d affichage envoyer", e);
    	}
    	return total;
    }
    
    public Envoyer selectEnvoyerById(int id_env) throws SQLException, ClassNotFoundException {
    	Envoyer total = null;
    	try(Connection connection = connect();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ENVOYER_BY_ID);) {
    		preparedStatement.setInt(1, id_env);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id_env1 = rs.getInt("id_env");
				int num_envoyeur = rs.getInt("num_envoyeur");
				int num_recepteur = rs.getInt("num_recepteur");
				Double montant = rs.getDouble("montant");
				String date = rs.getString("date");
				String raison = rs.getString("raison");
				total = new Envoyer(id_env1, num_envoyeur, num_recepteur, montant,date,raison);
			}
    	}
    	catch(SQLException e){
	          e.printStackTrace();
	          throw new SQLException("erreur d affichage envoyer", e);
    	}
    	return total;
    }
    
    public List<Envoyer> selectAllEnvoyer() throws ClassNotFoundException {
    	List<Envoyer> total = new ArrayList<>();
    	try(Connection connection = connect();
    			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ENVOYER);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id_env = rs.getInt("id_env");
				int num_envoyeur = rs.getInt("num_envoyeur");
				int num_recepteur = rs.getInt("num_recepteur");
				Double montant = rs.getDouble("montant");
				String date = rs.getString("date");
				String raison = rs.getString("raison");
				total.add(new Envoyer(id_env, num_envoyeur, num_recepteur, montant,date,raison));
			}
    	}
    	
    	catch(SQLException e){
    		printSQLException(e);
    	}
    	return total ;
    }
    
	public boolean deleteEnvoyer(int id_env) throws SQLException, ClassNotFoundException {
		boolean rowDeleted;
		try (Connection connection = connect();
				PreparedStatement statement = connection.prepareStatement(DELETE_ENVOYER_SQL);) {
			statement.setInt(1, id_env);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	public boolean updateEnvoyer(Envoyer envoyer) throws SQLException, ClassNotFoundException {
		boolean rowUpdated;
		try (Connection connection = connect();
				PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ENVOYER_SQL);) {
            preparedStatement.setInt(1, envoyer.getNum_envoyeur());
            preparedStatement.setInt(2, envoyer.getNum_recepteur());
            preparedStatement.setDouble(3, envoyer.getMontant());
            preparedStatement.setString(4, envoyer.getRaison());
            preparedStatement.setInt(5, envoyer.getId_env());

			rowUpdated = preparedStatement.executeUpdate() > 0;
		}
		return rowUpdated;
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
	
	
	// RECHERCHE ENTRE DEUX DATE
	public List<Envoyer> getEnvoisBetweenDates(String startDate, String endDate) throws ClassNotFoundException {
        List<Envoyer> listEnvoyer = new ArrayList<>();

        // Exemple de code pour interroger la base de données
        // (vous devrez adapter cela à votre propre accès à la base de données)
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(
                 "SELECT id_env, num_envoyeur, num_recepteur, montant, date, raison FROM envoyer WHERE date BETWEEN ? AND ?")) {

            statement.setDate(1, java.sql.Date.valueOf(startDate));
            statement.setDate(2, java.sql.Date.valueOf(endDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                	int id_env = resultSet.getInt("id_env");
    				int num_envoyeur = resultSet.getInt("num_envoyeur");
    				int num_recepteur = resultSet.getInt("num_recepteur");
    				Double montant = resultSet.getDouble("montant");
    				String date = resultSet.getString("date");
    				String raison = resultSet.getString("raison");
    				listEnvoyer.add(new Envoyer(id_env, num_envoyeur, num_recepteur, montant,date,raison));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return listEnvoyer;
    }
	
	// Méthode pour rechercher les transactions par une seule date
    public List<Envoyer> getEnvoisByDate(String singleDate) throws ClassNotFoundException {
        List<Envoyer> listEnvoyer = new ArrayList<>();

        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(
                 "SELECT id_env, num_envoyeur, num_recepteur, montant, date, raison FROM envoyer WHERE DATE(date) = ?")) {

            statement.setDate(1, Date.valueOf(singleDate));

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Envoyer envoyer = new Envoyer(0, 0, 0, null, password, password);
                    envoyer.setId_env(resultSet.getInt("id_env"));
                    envoyer.setNum_envoyeur(resultSet.getInt("num_envoyeur"));
                    envoyer.setNum_recepteur(resultSet.getInt("num_recepteur"));
                    envoyer.setMontant(resultSet.getDouble("montant"));
                    envoyer.setDate(resultSet.getString("date"));
                    envoyer.setRaison(resultSet.getString("raison"));
                    listEnvoyer.add(envoyer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listEnvoyer;
    }

}