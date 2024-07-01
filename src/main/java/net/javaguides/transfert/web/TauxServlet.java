package net.javaguides.transfert.web;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.javaguides.transfert.dao.TauxDAO;
import net.javaguides.transfert.model.Taux;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class TauxServlet
 */

@WebServlet("/taux")
public class TauxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private TauxDAO tauxDAO;       
    
    public TauxServlet() {
        this.tauxDAO = new TauxDAO();
        
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		// System.out.println("Action: " + action);
		
		switch(action){
			case"/new_taux":
				showNewForm(request, response);	
				System.out.println("Action: " + action);
				break;
				
			case"/insert_taux":
			try {
				insertTaux(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			case"/delete_taux":
			try {
				deleteTaux(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			case"/edit_taux":
			try {
				showEditForm(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			case"/update_taux":
			try {
				updateTaux(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
			default:
			try {
				listTaux(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				break;
				
		}		
	
	}
	
	private void listTaux(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException{
		List<Taux> listTaux = tauxDAO.selectAllTaux();
		request.setAttribute("listTaux", listTaux);
		RequestDispatcher dispatcher = request.getRequestDispatcher("taux-list.jsp");
		dispatcher.forward(request, response);
		System.out.println(listTaux);
	}
	
//	private void updateTaux(HttpServletRequest request, HttpServletResponse response)
//			throws SQLException, IOException{
//		int montant1 = Integer.parseInt(request.getParameter("montant1"));
//		int montant2 = Integer.parseInt(request.getParameter("montant2"));
//		
//		Taux taux = new Taux(montant1, montant2);
//		tauxDAO.updateTaux(taux);
//	
//		response.sendRedirect("taux");
//	}
	
	private void updateTaux(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, SQLException {
	    try {
	    	int id = Integer.parseInt(request.getParameter("id"));
	    	Double montant1 = Double.parseDouble(request.getParameter("montant1"));
	    	Double montant2 = Double.parseDouble(request.getParameter("montant2"));

	        Taux taux = new Taux(id, montant1, montant2);
	        tauxDAO.updateTaux(taux);

	        response.sendRedirect("taux");
	    } catch (NumberFormatException e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid number format for montant1 or montant2.");
	    } catch (SQLException e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Database error occurred while updating taux.");
	        System.out.println("erreor:: " + e );
	    }
	}

	
	private void deleteTaux(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException{
		int id_taux = Integer.parseInt(request.getParameter("id"));
		tauxDAO.deleteTaux(id_taux);
		response.sendRedirect("taux");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException{
		int id_taux = Integer.parseInt(request.getParameter("id"));
		
		Taux existingTaux = tauxDAO.selectTauxById(id_taux);
		RequestDispatcher dispatcher = request.getRequestDispatcher("taux-form.jsp");		
		request.setAttribute("taux", existingTaux);
		dispatcher.forward(request, response);
	}
	
//	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
//	        throws SQLException, IOException, ServletException {
//	    String idTauxStr = request.getParameter("id");
//
//	    if (idTauxStr != null && !idTauxStr.isEmpty()) {
//	        try {
//	            int id_taux = Integer.parseInt(idTauxStr);
//	            Taux existingTaux = tauxDAO.selectTauxById(id_taux);
//	            if (existingTaux != null) {
//	                RequestDispatcher dispatcher = request.getRequestDispatcher("/taux/taux-form.jsp");
//	                request.setAttribute("taux", existingTaux);
//	                dispatcher.forward(request, response);
//	            } else {
//	                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Taux not found with ID: " + id_taux);
//	            }
//	        } catch (NumberFormatException e) {
//	            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format: " + idTauxStr);
//	        }
//	    } else {
//	        response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing parameter: id_taux");
//	    }
//	}

	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("taux-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertTaux(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException{
		Double montant1 = Double.parseDouble(request.getParameter("montant1"));
		Double montant2 = Double.parseDouble(request.getParameter("montant2"));
		Taux newTaux = new Taux(montant1, montant2);
		tauxDAO.insertTaux(newTaux);
		response.sendRedirect("taux");
		
	}

}
