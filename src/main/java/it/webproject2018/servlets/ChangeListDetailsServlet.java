package it.webproject2018.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import it.webproject2018.db.daos.jdbc.JDBCCategoriaListeDAO;
import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.exceptions.DAOException;

/**
 *
 * @author Stefano
 */
public class ChangeListDetailsServlet extends HttpServlet {

    private JDBCListaDAO JDBCLista;
    private JDBCCategoriaListeDAO JDBCCategoriaListe;

    @Override
    public void init() throws ServletException {
        JDBCLista = new JDBCListaDAO(super.getServletContext());
        JDBCCategoriaListe = new JDBCCategoriaListeDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();
        PrintWriter w = response.getWriter();
        try {
            String img = "";
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Integer idList = Integer.parseInt(request.getParameter("idList"));

            Part filePart = request.getPart("file");
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
            
            Lista list = JDBCLista.getByPrimaryKey(idList);
            list.setNome(name);
            list.setDescrizione(description);
            list.setImmagine(img);
            
            list = JDBCLista.update(list);
            Boolean ok = list != null;
            
            JDBCLista.Close();
            JDBCCategoriaListe.Close();
            response.sendRedirect(request.getContextPath() + (!ok ? "/myList" : "/myList"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
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