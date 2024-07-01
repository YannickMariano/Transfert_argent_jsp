package net.javaguides.transfert.web;

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

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/exportPDF")
public class PdfServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ClientDAO clientDAO;
    private EnvoyerDAO envoyerDAO;

    @Override
    public void init() {
        clientDAO = new ClientDAO();
        envoyerDAO = new EnvoyerDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int clientId = Integer.parseInt(request.getParameter("id"));

        try {
            Client client = clientDAO.selectClientById(clientId);
            List<Envoyer> transactions = envoyerDAO.selectTransactionsByClient(clientId);
            
            double totalDebit = 0;
            for (Envoyer transaction : transactions) {
                totalDebit += transaction.getMontant();
            }

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition", "attachment; filename=client-transactions.pdf");
            
            Document document = new Document();
            try {
				PdfWriter.getInstance(document, response.getOutputStream());
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            document.open();

            try {
                // Titre principal
                Paragraph title = new Paragraph("Relevé d'opération", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD));
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                // Informations du client
                document.add(new Paragraph("Contact: " + client.getNumtel(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(new Paragraph("Nom: " + client.getNom(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(new Paragraph("Age: 24", FontFactory.getFont(FontFactory.HELVETICA, 12))); // Supposons que l'âge soit fixe pour cet exemple
                document.add(new Paragraph("Sexe: " + client.getSexe(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(new Paragraph("Solde actuel: " + client.getSolde(), FontFactory.getFont(FontFactory.HELVETICA, 12)));
                document.add(Chunk.NEWLINE); // Ajouter un saut de ligne pour espacer les sections

                // Titre du tableau des transactions
                Paragraph transactionTitle = new Paragraph("Transactions", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14));
                transactionTitle.setSpacingBefore(10);
                transactionTitle.setSpacingAfter(10);
                document.add(transactionTitle);

                // Ajouter le tableau des transactions
                float[] columnWidths = {3, 3, 3, 3};
                PdfPTable table = new PdfPTable(columnWidths);
                table.setWidthPercentage(100);
                table.addCell(new PdfPCell(new Phrase("Date", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
                table.addCell(new PdfPCell(new Phrase("Raison", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
                table.addCell(new PdfPCell(new Phrase("Nom du récepteur", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));
                table.addCell(new PdfPCell(new Phrase("Montant", FontFactory.getFont(FontFactory.HELVETICA_BOLD))));

                for (Envoyer transaction : transactions) {
                    table.addCell(transaction.getDate().toString());
                    table.addCell(transaction.getRaison());
                    table.addCell(transaction.getRecepteurNom());
                    table.addCell(String.valueOf(transaction.getMontant()));
                }

                document.add(table);

                // Total des débits
                Paragraph totalDebitParagraph = new Paragraph("Total Débit: " + totalDebit, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                totalDebitParagraph.setSpacingBefore(10); // Ajouter un espacement avant le paragraphe
                document.add(totalDebitParagraph);

            } catch (DocumentException e) {
                e.printStackTrace();
            } finally {
                document.close();
            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new ServletException(e);
        }
    }
}
