/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.daos.jdbc.JDBCUtenteDAO;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Max
 */
public class ShoppingListsServlet extends HttpServlet {
	private JDBCUtenteDAO JdbcUtenteDao;
	private JDBCProdottoDAO JdbcProdottoDao;
    
    @Override
    public void init() throws ServletException {
		JdbcUtenteDao = new JDBCUtenteDAO(super.getServletContext());
		JdbcProdottoDao = new JDBCProdottoDAO(super.getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();

        String userEmail = "g.s@agg.it";
		List<Utente> utenti = new ArrayList<>();
		
        try {
            Utente user = JdbcUtenteDao.getUserAuthentication(userEmail, "123");
            System.out.println("##############" + user.getEmail() + "################");
            String lista = "";
			System.out.println("####### " + user.Liste.size() + " liste per " + user.getName());
			Prodotto prod = JdbcProdottoDao.getByPrimaryKey(5);
			System.out.println("########prodotto" + prod.getNome());
			
/*			for(int i = 0; i < user.Liste.size(); i++){
                lista += "<tr><td>Lista: </td><td><b>" + user.Liste.get(i).getNome() + "</b></td></tr><tr><td colspan='2'><ul>";
				
                for(int j = 0; j < user.Liste.get(i).size(); j++){
                    lista += "<li>" + user.Liste.get(i).get(j).getNome() + "<br/>" + user.Liste.get(i).get(j).getNote() + 
                            "<br/>num foto: " + user.Liste.get(i).get(j).Fotografie.size() + 
                            "<br/>" + user.Liste.get(i).get(j).getCategoria().getNome() + 
                            "<br/>num volte acquistato: " + user.Liste.get(i).get(j).Acquisti.size() +
                            "</li>\n";
                }
                
                lista += "</ul></td></tr>\n";;
            }
*/            
            out.println(
                    "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "	<head>\n"
                    + "		<title>Access Counter example</title>\n"
                    + "	</head>\n"
                    + "	<body>\n"
                    + "		<h1 align=\"center\">" + "Heading" + "</h1>\n"
                    + "		<h2>Information on your session:</h2>\n"
                    + "		<table>\n"
                    + "			<tr><th>Informazioni utente:</th><th></th></tr>\n"
                    + "			<tr><td>ID: </td><td>" + user.getEmail() + "</td></tr>\n"
                    + "			<tr><td>Nome: </td><td>" + user.getName() + "</td></tr>\n"
                    + "			<tr><td>Cognome: </td><td>" + user.getSurname() + "</td></tr>\n"
                    + "			<tr><td>Immagine: </td><td>" + user.getPicture() + "</td></tr>\n"
                    + "			<tr><td>IsAdmin: </td><td>" + user.getIsAdmin() + "</td></tr>\n"
                            + lista
                    + "		</table>\n"
                    + "	</body>\n"
                    + "</html>"
            );
        } catch (Exception ex) {
            System.out.println("##############################catch SQLException");
            out.println(
                    "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
					+ "        <title>Exception caught</title>\n"
                    + "        <!-- Latest compiled and minified CSS -->\n"
                    + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" crossorigin=\"anonymous\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <div class=\"jumbotron\">\n"
                    + "            <div class=\"container\">\n"
                    + "                <div class=\"card border-danger\">\n"
                    + "                    <div class=\"card-header\">\n"
                    + "                        <h3 class=\"card-title bg-danger text-white\">An error occurred</h3>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"card-body\">\n"
                    + "                        Exception: " + ex + "<br>\n"
                    + "                    </div>\n"
					+ "					   <div class=\"card-footer\">\n"
					+ "							Error message: " + ex.getMessage() + "<br>"
					+ "					   </div>\n"
                    + "                </div>\n"
                    + "            </div>\n"
                    + "        </div>\n"
                    + "        <!-- Latest compiled and minified JavaScript -->\n"
                    + "        <script src=\"https://code.jquery.com/jquery-3.2.1.min.js\" crossorigin=\"anonymous\"></script>\n"
                    + "        <script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js\" crossorigin=\"anonymous\"></script>\n"
                    + "    </body>\n"
                    + "</html>"
            );
        }
    }
}
