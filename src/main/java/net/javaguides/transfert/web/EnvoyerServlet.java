package net.javaguides.transfert.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.javaguides.transfert.dao.ClientDAO;
import net.javaguides.transfert.dao.EnvoyerDAO;
import net.javaguides.transfert.model.Client;
import net.javaguides.transfert.model.Envoyer;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet("/envoyer")
public class EnvoyerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private EnvoyerDAO EnvoyerDAO;
	
	public void init() {
		EnvoyerDAO = new EnvoyerDAO();
	}
       
   
    public EnvoyerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		

		try {	
			
		switch (action) {	
		
		case "/new_envoyer":
				showNewForm(request, response);
			break;
			
		case"/insert_envoyer":	
				insertEnvoyer(request, response);		
			break;
			
		case "/delete_envoyer":
				deleteEnvoyer(request, response);
			break;
			
		case"/edit_envoyer":
				showEditForm(request, response);
			break;
			
		case"/update_envoyer":
				updateEnvoyer(request, response);
			break;
			
		case"/search_envoyer":
				searchEnvoyer(request, response);
		break;
		
		default:
			try {
				listEnvoyer(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			}
		}catch (SQLException ex) {
            ex.printStackTrace();
            throw new ServletException(ex);
        } catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
			
	
	private void searchEnvoyer(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException, ClassNotFoundException {
	    
	    String searchType = request.getParameter("searchType");
	    List<Envoyer> listEnvoyer = null;
	    
	    try {
	        if ("single".equals(searchType)) {
	            String singleDateStr = request.getParameter("singleDate");
	            if (singleDateStr != null && !singleDateStr.isEmpty()) {
	                String singleDate = singleDateStr;
	                listEnvoyer = EnvoyerDAO.getEnvoisByDate(singleDate);
	            }
	        } else if ("range".equals(searchType)) {
	            String startDateStr = request.getParameter("startDate");
	            String endDateStr = request.getParameter("endDate");

	            if (startDateStr != null && !startDateStr.isEmpty() && endDateStr != null && !endDateStr.isEmpty()) {
	                String startDate = startDateStr;
	                String endDate = endDateStr;
	                listEnvoyer = EnvoyerDAO.getEnvoisBetweenDates(startDate, endDate);
	            }
	        }

	        if (listEnvoyer != null) {
	            Map<Integer, String> envoyeurMap = new HashMap<>();
	            Map<Integer, String> recepteurMap = new HashMap<>();
	            mapEnvoyeursAndRecepteurs(listEnvoyer, envoyeurMap, recepteurMap);

	            request.setAttribute("listEnvoyer", listEnvoyer);
	            request.setAttribute("envoyeurMap", envoyeurMap);
	            request.setAttribute("recepteurMap", recepteurMap);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    
	    request.getRequestDispatcher("/envoyer-list.jsp").forward(request, response);
	}
	
	private void mapEnvoyeursAndRecepteurs(List<Envoyer> listEnvoyer, Map<Integer, String> envoyeurMap, Map<Integer, String> recepteurMap) throws SQLException, ClassNotFoundException {
	    ClientDAO clientDAO = new ClientDAO();
	    
	    for (Envoyer envoyer : listEnvoyer) {
	        int envoyeurId = envoyer.getNum_envoyeur();
	        Client envoyeurClient = clientDAO.selectClientById(envoyeurId);
	        if (envoyeurClient != null) {
	            String envoyeurTel = envoyeurClient.getNumtel();
	            envoyeurMap.put(envoyeurId, envoyeurTel);
	        } else {
	            envoyeurMap.put(envoyeurId, "Unknown");
	        }

	        int recepteurId = envoyer.getNum_recepteur();
	        Client recepteurClient = clientDAO.selectClientById(recepteurId);
	        if (recepteurClient != null) {
	            String recepteurTel = recepteurClient.getNumtel();
	            recepteurMap.put(recepteurId, recepteurTel);
	        } else {
	            recepteurMap.put(recepteurId, "Unknown");
	        }
	    }
	}


	
	private void listEnvoyer(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ServletException, ClassNotFoundException {
	    EnvoyerDAO envoyerDAO = new EnvoyerDAO();
	    ClientDAO clientDAO = new ClientDAO();
	    List<Envoyer> listEnvoyer = envoyerDAO.selectAllEnvoyer();
	    
	    // Create maps to store envoyeur and recepteur IDs and their corresponding phone numbers
	    Map<Integer, String> envoyeurMap = new HashMap<>();
	    Map<Integer, String> recepteurMap = new HashMap<>();
	    
	    for (Envoyer envoyer : listEnvoyer) {
	        int envoyeurId = envoyer.getNum_envoyeur();
	        Client envoyeurClient = clientDAO.selectClientById(envoyeurId);
	        if (envoyeurClient != null) {
	            String envoyeurTel = envoyeurClient.getNumtel();
	            envoyeurMap.put(envoyeurId, envoyeurTel);
	        } else {
	            System.out.println("Envoyeur with ID " + envoyeurId + " not found.");
	            envoyeurMap.put(envoyeurId, "Unknown");
	        }

	        int recepteurId = envoyer.getNum_recepteur();
	        Client recepteurClient = clientDAO.selectClientById(recepteurId);
	        if (recepteurClient != null) {
	            String recepteurTel = recepteurClient.getNumtel();
	            recepteurMap.put(recepteurId, recepteurTel);
	        } else {
	            System.out.println("Recepteur with ID " + recepteurId + " not found.");
	            recepteurMap.put(recepteurId, "Unknown");
	        }
	    }
	    
	    request.setAttribute("listEnvoyer", listEnvoyer);
	    request.setAttribute("envoyeurMap", envoyeurMap);
	    request.setAttribute("recepteurMap", recepteurMap);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("envoyer-list.jsp");
	    dispatcher.forward(request, response);
	}
	
	private void updateEnvoyer(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ClassNotFoundException{
		int id_env = Integer.parseInt(request.getParameter("id_env"));
	    int num_envoyeur =Integer.parseInt(request.getParameter("num_envoyeur"));
	    int num_recepteur = Integer.parseInt(request.getParameter("num_recepteur"));
	    Double montant = Double.parseDouble(request.getParameter("montant"));
	    String raison = (request.getParameter("raison"));
		
		Envoyer frs = new Envoyer(id_env, num_envoyeur, num_recepteur, montant,null,raison);
		EnvoyerDAO envoyerDAO = new EnvoyerDAO();
		envoyerDAO.updateEnvoyer(frs);
		response.sendRedirect("envoyer");
	}
	
	private void deleteEnvoyer(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ClassNotFoundException{
		int id = Integer.parseInt(request.getParameter("id_envoyer"));
		EnvoyerDAO envoyerDAO = new EnvoyerDAO();
		envoyerDAO.deleteEnvoyer(id);
		response.sendRedirect("envoyer");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ClassNotFoundException{
		int id = Integer.parseInt(request.getParameter("id_env"));
		EnvoyerDAO envoyerDAO = new EnvoyerDAO();
		Envoyer existingEnvoyer = envoyerDAO.selectEnvoyer(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("envoyer-form.jsp");
		request.setAttribute("envoyer", existingEnvoyer);
		dispatcher.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException, ClassNotFoundException {
	    List<Client> listClient = EnvoyerDAO.selectAllMembres();
	    
	    System.out.println(listClient);
	    
	    request.setAttribute("listClient", listClient);
	    RequestDispatcher dispatcher = request.getRequestDispatcher("envoyer-form.jsp");
	    dispatcher.forward(request, response);
	}

	
	private void insertEnvoyer(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, SQLException, ClassNotFoundException, ServletException {
	    
	    try {
	        int num_envoyeur = Integer.parseInt(request.getParameter("num_envoyeur")) ;
	        int num_recepteur = Integer.parseInt(request.getParameter("num_recepteur"));
	        Double montant = Double.parseDouble(request.getParameter("montant"));
	        String raison = request.getParameter("raison");

	        System.out.println("num_envoyeur: " + num_envoyeur);
	        System.out.println("num_recepteur: " + num_recepteur);
	        System.out.println("montant: " + montant);

	        if (montant > 0 && raison != null) {
	            EnvoyerDAO envoyerDAO = new EnvoyerDAO();

	            Envoyer newEnvoyer = new Envoyer(num_envoyeur, num_recepteur, montant, null,raison);
	            envoyerDAO.insertEnvoyer(newEnvoyer);
	            
	            response.sendRedirect("envoyer");
	        } else {
	            System.out.println("Invalid input parameters.");
	            request.setAttribute("errorMessage", "Invalid input parameters.");
	            RequestDispatcher dispatcher = request.getRequestDispatcher("clientError.jsp");
	            dispatcher.forward(request, response);
	        }
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        request.setAttribute("exception", e);
	        request.setAttribute("errorMessage", "Invalid number format.");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("clientError.jsp");
	        dispatcher.forward(request, response);
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	        request.setAttribute("exception", e);
	        request.setAttribute("errorMessage", "Database error: " + e.getMessage());
	        RequestDispatcher dispatcher = request.getRequestDispatcher("clientError.jsp");
	        dispatcher.forward(request, response);
	    } catch (Exception e) {
	        e.printStackTrace();
	        request.setAttribute("exception", e);
	        request.setAttribute("errorMessage", "An unexpected error occurred: " + (e != null ? e.getMessage() : "Unknown error."));
	        RequestDispatcher dispatcher = request.getRequestDispatcher("clientError.jsp");
	        dispatcher.forward(request, response);
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}