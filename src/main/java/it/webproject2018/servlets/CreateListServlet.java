/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.entities.CategoriaListe;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Max
 */
public class CreateListServlet extends HttpServlet {
	private JDBCListaDAO JDBCListaDAO;
	
	@Override
	public void init() throws ServletException {
        Connection conn = (Connection) super.getServletContext().getAttribute("connection");
        JDBCListaDAO = new JDBCListaDAO(conn);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter w = response.getWriter();
		try {
			Utente user = (Utente)request.getSession().getAttribute("User");
			
			String name = request.getParameter("name");
            String description = request.getParameter("description");
            String picture = request.getParameter("picture");
			String listCategoryName = request.getParameter("listCategory");
   //da sistemare         CategoriaListe category = new CategoriaListe(listCategoryName); 
			String owner = user.getEmail();
			
            Lista list = new Lista(null, null, null, null, null, name, description, picture, category, owner);

			Boolean ok = JDBCListaDAO.insert(list);
            response.sendRedirect(request.getContextPath() + (!ok ? "/newList.jsp" : "/myList.jsp"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
    }
}
