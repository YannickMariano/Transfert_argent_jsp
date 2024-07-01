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
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;

import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * Servlet implementation class ClientServlet
 */
@WebServlet("/")
public class ClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private ClientDAO clientDAO;
	
    public ClientServlet() {
    	this.clientDAO = new ClientDAO();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
		
		switch(action){
			
		case"/new":
			showNewForm(request, response);
			break;
			
		case"/insert":
			try {
				insertClient(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage());
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println(e.getMessage()); 
				e.printStackTrace();
			}
			break;
			
		case"/delete":
			try {
				deleteClient(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		
		case"/edit":
			try {
				showEditForm(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
			break;
			
		case"/update":
			try {
				updateClient(request, response);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case"/search":
			try {
				searchClient(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
			
		case"/generate":
				generatePDF(request, response);
			break;
			
		case"/info":
			try {
				infoClient(request, response);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ServletException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		break;
					
		default:
			try {
				listClient(request, response);
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
	
	private void infoClient(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException{
		int idClient = Integer.parseInt(request.getParameter("id"));
		System.out.println("Id Client: " + idClient);

        ClientDAO clientDAO = new ClientDAO();
        EnvoyerDAO envoyerDAO = new EnvoyerDAO();
        
        try {
            Client client = clientDAO.selectClientById(idClient);
            List<Envoyer> transactions = null;
			try {
				transactions = envoyerDAO.selectTransactionsByClient(idClient);
			} catch (ClassNotFoundException e) {
				System.out.println("Error select Transaction: " + e);
				e.printStackTrace();
			}    		
			
			System.out.println("List Client: " + client);
			System.out.println("List Envoyer: " + transactions);
			
            request.setAttribute("client", client);
            request.setAttribute("transactions", transactions);
            
            RequestDispatcher dispatcher = request.getRequestDispatcher("info.jsp");
            dispatcher.forward(request, response);
            
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
	
	
	private void listClient(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException{
		List<Client> listClient = clientDAO.selectAllClients();
		request.setAttribute("listClient", listClient);
		RequestDispatcher dispatcher = request.getRequestDispatcher("client-list.jsp");
		dispatcher.forward(request, response);
		System.out.println(listClient);
	}
	
	private void searchClient(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException, ServletException{
		String nom = request.getParameter("nom");
		System.out.println("Client rechercher: " + nom);
		List<Client> listClient;
		try {
			listClient = clientDAO.searchClientByName(nom);
			request.setAttribute("listClient", listClient);
			// request.getRequestDispatcher("client-list.jsp").forward(request, response);
			RequestDispatcher dispatcher = request.getRequestDispatcher("client-list.jsp");
			dispatcher.forward(request, response);
		}catch (SQLException e) {
			System.out.println("Error REcherche Client: " + e);
            throw new ServletException(e);
		}
	}
	
	private void updateClient(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		String numtel = request.getParameter("numtel");
		String nom = request.getParameter("nom");
		String sexe = request.getParameter("sexe");
		String pays = request.getParameter("pays");
		Double solde = Double.parseDouble(request.getParameter("solde"));
		String mail = request.getParameter("mail");
		
		Client client = new Client(id, numtel, nom, sexe, pays, solde, mail);
		clientDAO.updateClient(client);
		System.out.println(client);
		response.sendRedirect("list");
	}

	
	private void deleteClient(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException{
		int id = Integer.parseInt(request.getParameter("id"));
		clientDAO.deleteClient(id);
		response.sendRedirect("list");
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response)
			throws SQLException, IOException, ServletException{
		int id = Integer.parseInt(request.getParameter("id"));
		Client existingClient = clientDAO.selectClientById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("client-form.jsp");
		request.setAttribute("client", existingClient);
		dispatcher.forward(request, response);
	}
	

	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		RequestDispatcher dispatcher = request.getRequestDispatcher("client-form.jsp");
		dispatcher.forward(request, response);
	}
	
	private void insertClient(HttpServletRequest request, HttpServletResponse response)
			throws IOException, SQLException{
		String numtel = request.getParameter("numtel");
		String nom = request.getParameter("nom");
		String sexe = request.getParameter("sexe");
		String pays = request.getParameter("pays");
		Double solde = Double.parseDouble(request.getParameter("solde"));
		String mail = request.getParameter("mail");
		Client newClient = new Client(numtel, nom, sexe, pays, solde, mail);
		clientDAO.insertClient(newClient);
		response.sendRedirect("list");
		
	}
	
	private void generatePDF(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException{
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition", "attachement; filename=example.pdf");		
		
		Document document = new Document();
		try {
			PdfWriter.getInstance(document, response.getOutputStream());
			document.open();
			
			
			float[] columnWidths = {1, 3, 3, 2, 2, 2, 3};
            PdfPTable table = new PdfPTable(columnWidths);
            table.setWidthPercentage(100); // Set width to 100% of page width
            
            // Add headers
            table.addCell(("Date"));
            table.addCell(("Raison"));
            table.addCell(("Nom du récepteur"));
            table.addCell(("Montant"));
            table.addCell(("Mail"));
			
            // Add client data from request attribute
            // @SuppressWarnings("unchecked") // Suppress warning for casting to List<Client>
            // List<Client> listClient = (List<Client>) request.getAttribute("listClient");
            List<Client> listClient = clientDAO.selectAllClients();
    		request.setAttribute("listClient", listClient);
            for (Client client : listClient) {
                table.addCell("date --");
                table.addCell("raison --");
                table.addCell("nom --");
                table.addCell("montant --");
                table.addCell(client.getMail());
                
                document.add(new Paragraph("Relever d'operattion de Mr / Mme: " + client.getNom()));
                document.add(new Paragraph("Contact: " + client.getNumtel()));
                document.add(new Paragraph("Nom: " + client.getNom()));
                document.add(new Paragraph("Sexe: " + client.getSexe()));
                document.add(new Paragraph("Solde: " + client.getSolde()));
                
                document.add(table);
                
                document.add(new Paragraph("Solde: 250000 euros"));
               
                
            }
            
            
			
			document.close();
		}catch(DocumentException e) {
            throw new ServletException(e);
		}
	}


	// Helper method to create header cells with gray background
    private com.itextpdf.text.pdf.PdfPCell getHeaderCell(String text) {
        com.itextpdf.text.pdf.PdfPCell cell = new com.itextpdf.text.pdf.PdfPCell();
        // cell.setBackgroundColor(new Color(220, 220, 220)); // Gray background
        cell.setPhrase(new com.itextpdf.text.Phrase(text));
        return cell;
    }
    	
}
