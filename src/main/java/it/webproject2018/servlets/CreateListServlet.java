/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

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

import it.webproject2018.db.daos.jdbc.JDBCCategoriaListeDAO;
import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.daos.jdbc.JDBCListaPermessiDAO;
import it.webproject2018.db.entities.CategoriaListe;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.ListaPermessi;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;

/**
 *
 * @author Stefano
 */
public class CreateListServlet extends HttpServlet {

    private JDBCListaDAO JDBCLista;
    private JDBCListaPermessiDAO JDBCListaPermessi;
    private JDBCCategoriaListeDAO JDBCCategoriaListe;

    @Override
    public void init() throws ServletException {
        JDBCLista = new JDBCListaDAO(super.getServletContext());
        JDBCCategoriaListe = new JDBCCategoriaListeDAO(super.getServletContext());
        JDBCListaPermessi = new JDBCListaPermessiDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            Utente user = (Utente) request.getSession().getAttribute("User");
            String img = "";
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            String category = request.getParameter("category");
            String owner = user.getEmail();

            CategoriaListe cat = JDBCCategoriaListe.getByPrimaryKey(category);
            // w.println(cat);
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
                // w.println(img);
                Path pathToFile = Paths.get(getServletContext().getRealPath(File.separator) + img);

                Files.copy(fileContent, pathToFile);
            }

            Lista list = new Lista(null, name, description, img, cat, owner);
            w.println(list);
            // w.println(list.getNome());
            // w.println("ciao");
            /*
             * w.println(list.getNome()); w.println(list.getDescrizione());
             * w.println(list.getImmagine()); w.println(list.getCategoria().getNome());
             * w.println(list.getOwner());
             */
            list = JDBCLista.insert(list);
            w.println(list);
            Boolean ok = list != null;
            w.println(ok);
            if (ok) {
                ListaPermessi permessi = new ListaPermessi(true, true, true, true, user.getEmail(), list.getId());
                JDBCListaPermessi.insert(permessi);
            }
            response.sendRedirect(request.getContextPath() + (!ok ? "/newList" : "/myList"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
        
        JDBCLista.Close();
        JDBCListaPermessi.Close();
        JDBCCategoriaListe.Close();
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
