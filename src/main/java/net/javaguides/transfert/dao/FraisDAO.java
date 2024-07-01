package net.javaguides.transfert.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.javaguides.transfert.model.Frais;


public class FraisDAO {
    private final String url = "jdbc:postgresql://localhost:5432/JSP";
    private final String user = "postgres";
    private final String password = "#01YannicK#";
    

    public Connection connect() throws SQLException, ClassNotFoundException {
    	Class.forName("org.postgresql.Driver");
    	return DriverManager.getConnection(url,user,password);
    }

    public static void main(String[] args) throws ClassNotFoundException {
    	FraisDAO dao = new FraisDAO();
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
    
    private static final String INSERT_FRAIS_SQL = "INSERT INTO frais (montant_1,montant_2,frais) VALUES (?,?,?)";
    private static final String SELECT_FRAIS_BY_ID = "SELECT id_frais,montant_1,montant_2,frais FROM frais WHERE id_frais =?;";
    private static final String SELECT_ALL_FRRAIS = "SELECT * FROM frais";
    private static final String DELETE_FRAIS_SQL = "DELETE FROM frais WHERE id_frais =?;";
    private static final String UPDATE_FRAIS_SQL = "UPDATE frais set montant_1 = ? , montant_2 =? , frais =? WHERE id_frais =?;";
    
    
    public void insertFrais(Frais frais) throws SQLException, ClassNotFoundException {
        System.out.println("Insertion frais: " + frais.getMontant_1() + ", " + frais.getMontant_2() + ", " + frais.getFrais());
        try(Connection connection = connect();
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_FRAIS_SQL)){
            preparedStatement.setDouble(1, frais.getMontant_1());
            preparedStatement.setDouble(2, frais.getMontant_2());
            preparedStatement.setDouble(3, frais.getFrais());
            int rowsInserted = preparedStatement.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted);
        }
        catch(SQLException e){
            e.printStackTrace();
            throw new SQLException("Erreur lors de l'insertion de frais", e);
        }
    }

    
    public Frais selectFrais(int id_frais) throws SQLException, ClassNotFoundException {
    	Frais total = null;
    	try(Connection connection = connect();
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT_FRAIS_BY_ID);) {
    		preparedStatement.setInt(1, id_frais);
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Double montant_1 = rs.getDouble("montant_1");
				Double montant_2 = rs.getDouble("montant_2");
				Double frais = rs.getDouble("frais");
				total = new Frais(id_frais, montant_1, montant_2, frais);
			}
    	}
    	catch(SQLException e){
	          e.printStackTrace();
	          throw new SQLException("erreur de l insertion de frais", e);
    	}
    	return total;
    }
    
    public List<Frais> selectAllFrais() throws ClassNotFoundException {
    	List<Frais> total = new ArrayList<>();
    	try(Connection connection = connect();
    			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_FRRAIS);) {
			System.out.println(preparedStatement);
			// Step 3: Execute the query or update query
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				int id_frais = rs.getInt("id_frais");
				Double montant_1 = rs.getDouble("montant_1");
				Double montant_2 = rs.getDouble("montant_2");
				Double frais = rs.getDouble("frais");
				total.add(new Frais(id_frais, montant_1, montant_2, frais));
			}
    	}
    	
    	catch(SQLException e){
    		printSQLException(e);
    	}
    	return total ;
    }
    
	public boolean deleteFrais(int id_frais) throws SQLException, ClassNotFoundException {
		boolean rowDeleted;
		try (Connection connection = connect();
				PreparedStatement statement = connection.prepareStatement(DELETE_FRAIS_SQL);) {
			statement.setInt(1, id_frais);
			rowDeleted = statement.executeUpdate() > 0;
		}
		return rowDeleted;
	}
	
	public boolean updateFrais(Frais frais) throws SQLException, ClassNotFoundException {
		boolean rowUpdated;
		try (Connection connection = connect();
				PreparedStatement statement = connection.prepareStatement(UPDATE_FRAIS_SQL);) {
			statement.setDouble(1, frais.getMontant_1());
			statement.setDouble(2, frais.getMontant_2());
			statement.setDouble(3, frais.getFrais());
			statement.setInt(4, frais.getId_frais());

			rowUpdated = statement.executeUpdate() > 0;
		}
		return rowUpdated;
	}
	

    public Double getFraisByMontant(Double montant) throws SQLException, ClassNotFoundException {
        String sql = "SELECT frais FROM frais WHERE montant_1 <= ? AND montant_2 >= ?";
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, montant);
            statement.setDouble(2, montant);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("frais");
            }
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