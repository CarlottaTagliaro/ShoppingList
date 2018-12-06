/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import de.scravy.pair.Pair;
import it.webproject2018.db.daos.CategoriaListeDAO;
import it.webproject2018.db.daos.ListaDAO;
import it.webproject2018.db.daos.ListaPermessiDAO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import it.webproject2018.db.entities.CategoriaListe;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.util.ArrayList;

/**
 *
 * @author Stefano
 */
public class CreateListServlet extends HttpServlet {

    private ListaDAO listaDao;
    private ListaPermessiDAO listaPermessiDao;
    private CategoriaListeDAO categoriaListeDao;

    @Override
    public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			listaDao = daoFactory.getDAO(ListaDAO.class);
			listaPermessiDao = daoFactory.getDAO(ListaPermessiDAO.class);
			categoriaListeDao = daoFactory.getDAO(CategoriaListeDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for lista permessi and categoria liste storage system", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();
        
        PrintWriter w = response.getWriter();

        Utente user = (Utente) request.getSession().getAttribute("User");

        String img = "";
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String category = request.getParameter("category");

        CategoriaListe cat = new CategoriaListe();
        try {
            cat = categoriaListeDao.getByPrimaryKey(category);
        } catch (DAOException e) {
            e.printStackTrace();
        }

        Boolean ok = true;
        if (user != null) {
            try {
                String owner = user.getEmail();
                List<Object> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName()))
                        .collect(Collectors.toList());

                for (Object oFilePart : fileParts) {
                    Part filePart = (Part) oFilePart;
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    InputStream fileContent = filePart.getInputStream();

                    try {
                        String ext = fileName.substring(fileName.lastIndexOf("."));
                        fileName = randomString(70) + ext; // assign random name
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    img = "imagesUpload/" + fileName;
                    Path pathToFile = Paths.get(getServletContext().getRealPath(File.separator) + img);

                    Files.copy(fileContent, pathToFile);
                }

                Lista list = new Lista(null, name, description, img, cat, owner);

                list = listaDao.insert(list);
                ok = list != null;
                
                if (ok) {
                    ListaPermessi permessi = new ListaPermessi(true, true, true, true, user.getEmail(), list.getId());
                    listaPermessiDao.insert(permessi);
                }
            } catch (DAOException e) {
                w.println(e.getMessage());
            }
        } else {
            //creo una lista di default per l'utente non loggato
            ArrayList<Pair<Prodotto, Integer>> defaultList = new ArrayList<>();
            request.getSession().setAttribute("DefaultProductList", defaultList);

            Lista l = new Lista(1, name, description, "images/carrello.png", cat, "");
            request.getSession().setAttribute("DefaultList", l);
        }

        response.sendRedirect(request.getContextPath() + (!ok ? "/newList" : "/myList"));
    }

    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    String randomString(int len) {
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            sb.append(AB.charAt(rnd.nextInt(AB.length())));
        }
        return sb.toString();
    }
}
