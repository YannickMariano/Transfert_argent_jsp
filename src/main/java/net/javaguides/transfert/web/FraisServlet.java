package net.javaguides.transfert.web;

import net.javaguides.transfert.dao.FraisDAO;
import net.javaguides.transfert.model.Frais;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Servlet implementation class FraisServelet

 */
@WebServlet("/frais")
public class FraisServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private FraisDAO fraisDAO;
       

    public FraisServlet() {
        this.fraisDAO = new FraisDAO();
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doGet(request, response);
	}
	
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		System.out.println("Action: " + action);
		

		try {	
			switch (action) {	
				case "/new_frais":
					showNewForm(request, response);
					break;
			
				case"/insert_frais":
					insertFrais(request, response);
				
					break;
					
				case "/delete_frais":
					deleteFrais(request, response);
					break;
					
				case"/edit_frais":
						showEditForm(request, response);
					break;
					
				case"/update_frais":
						updateFrais(request, response);
					break;
				
				default:
					try {
						listFrais(request, response);
					} catch (SQLException e) {
						System.out.println("Error: " + e);
						e.printStackTrace();
					} catch (IOException e) {
						System.out.println("Error: " + e);
						e.printStackTrace();
					} catch (ServletException e) {
						System.out.println("Error: " + e);
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						System.out.println("Error: " + e);
						e.printStackTrace();
					}
					break;}}
				catch (SQLException ex) {
		            ex.printStackTrace();
		            throw new ServletException(ex);
		        } catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	
			}

	
	private void listFrais(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ClassNotFoundException{
		List<Frais> listFrais = fraisDAO.selectAllFrais();
		request.setAttribute("listFrais", listFrais);
		RequestDispatcher dispatcher = request.getRequestDispatcher("frais-list.jsp");
		dispatcher.forward(request, response);
	}
	
	private void updateFrais(HttpServletRequest request, HttpServletResponse response)
	        throws SQLException, IOException, ClassNotFoundException, ServletException {
	    int id = Integer.parseInt(request.getParameter("id_frais"));
	    Double montant_1 = Double.parseDouble(request.getParameter("montant_1"));
	    Double montant_2 = Double.parseDouble(request.getParameter("montant_2"));
	    Double frais = Double.parseDouble(request.getParameter("frais"));

	    // Vérifier que montant_1 est inférieur à montant_2
	    if (montant_1 < montant_2) {
		    Frais frs = new Frais(id, montant_1, montant_2, frais);
		    fraisDAO.updateFrais(frs);
		    response.sendRedirect("frais");
	    }
	    else {
	        request.setAttribute("errorMessage", "Le premier montant doit être inférieur au deuxième montant.");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
	        dispatcher.forward(request, response);
	        return;

	    }

	    // Si les conditions sont remplies, mettre à jour les frais

	}

	
	private void deleteFrais(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ClassNotFoundException{
		int id = Integer.parseInt(request.getParameter("id_frais"));
		fraisDAO.deleteFrais(id);
		response.sendRedirect("frais");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException, ClassNotFoundException{
		int id = Integer.parseInt(request.getParameter("id_frais"));
		Frais existingFrais = fraisDAO.selectFrais(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("frais-form.jsp");
		request.setAttribute("frais", existingFrais);
		dispatcher.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("frais-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertFrais(HttpServletRequest request, HttpServletResponse response)
	        throws IOException, SQLException, ClassNotFoundException, ServletException{
		Double montant_1 = Double.parseDouble(request.getParameter("montant_1"));
		Double montant_2 = Double.parseDouble(request.getParameter("montant_2"));
		Double frs = Double.parseDouble(request.getParameter("frais"));

	    System.out.println("montant_1: " + montant_1);
	    System.out.println("montant_2: " + montant_2);
	    System.out.println("frais: " + frs);

	    if(montant_1 < montant_2) {
	        Frais newFrais = new Frais(montant_1, montant_2, frs);
	        fraisDAO.insertFrais(newFrais);
	        response.sendRedirect("frais");
	    } else {
	        request.setAttribute("errorMessage", "Le premier montant doit être inférieur au deuxième montant.");
	        RequestDispatcher dispatcher = request.getRequestDispatcher("Error.jsp");
	        dispatcher.forward(request, response);
	        return;
	    }
	}

	

}