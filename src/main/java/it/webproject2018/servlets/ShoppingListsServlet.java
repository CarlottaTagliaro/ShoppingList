/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.entities.DBManager;
import it.webproject2018.entities.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Max
 */
public class ShoppingListsServlet extends HttpServlet {

    private DBManager dbManager;

    @Override
    public void init() throws ServletException {
        dbManager = (DBManager) super.getServletContext().getAttribute("dbmanager");
    }

	// forse meglio in post
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        //String userEmail = String.valueOf(request.getParameter("Email"));
        String userEmail = "cucci@lo";
        try {
            Utente user = dbManager.getUserByEmail(userEmail);
            System.out.println("##############" + user.getEmail() + "################");
            
            String lista = "";
			System.out.println("####### " + user.Liste.size() + " liste per Richard");
            
			for(int i = 0; i < user.Liste.size(); i++){
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
        } catch (SQLException ex) {
            System.out.println("##############################catch SQLException");
            out.println(
                    "<!DOCTYPE html>\n"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>eccezioneLab 06: ToDo List</title>\n"
                    + "        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n"
                    + "        <!-- Latest compiled and minified CSS -->\n"
                    + "        <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" crossorigin=\"anonymous\">\n"
                    + "    </head>\n"
                    + "    <body>\n"
                    + "        <div class=\"jumbotron\">\n"
                    + "            <div class=\"container\">\n"
                    + "                <div class=\"card border-danger\">\n"
                    + "                    <div class=\"card-header\">\n"
                    + "                        <h3 class=\"card-title bg-danger text-white\">ToDos</h3>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"card-body\">\n"
                    + "                        Error in retriving todos list: " + ex.getMessage() + "<br>\n"
                    + "                    </div>\n"
                    + "                    <div class=\"card-footer\">Copyright &copy; 2018 - Stefano Chirico</div>\n"
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
