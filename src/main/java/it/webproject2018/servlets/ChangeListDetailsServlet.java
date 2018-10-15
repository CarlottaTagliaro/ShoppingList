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
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.Utente;
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
        PrintWriter w = response.getWriter();
        try {
            Utente user = (Utente) request.getSession().getAttribute("User");

            String img = "";
            String name = request.getParameter("name");
            String description = request.getParameter("description");
            Integer idList = Integer.parseInt(request.getParameter("idList"));
            String owner = user.getEmail();
            w.println(name);
            w.println(description);
            w.println(idList);
            List<Object> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName()))
                    .collect(Collectors.toList());

            if(!fileParts.isEmpty()) {
                for (Object oFilePart : fileParts) {
                    Part filePart = (Part) oFilePart;
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    InputStream fileContent = filePart.getInputStream();
    
                    try {
                        w.println("FileName:"+fileName);
                        String ext = fileName.substring(fileName.lastIndexOf("."));
                        fileName = randomString(70) + ext;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
    
                    img = "imagesUpload/" + fileName;
                    // w.println(img);
                    w.println(img);
                    w.println(fileContent.toString());
                    Path pathToFile = Paths.get(getServletContext().getRealPath(File.separator) + img);
                    w.println(pathToFile.toString());
                    //Files.copy(fileContent, pathToFile);
                }
            }

            //w.println(img);
            Lista list = JDBCLista.getByPrimaryKey(idList);
            //w.println(list.getNome());
            list.setNome(name);
            list.setDescrizione(description);
            list.setImmagine(img);
            
            list = JDBCLista.update(list);
            Boolean ok = list != null;
            w.println(ok);
            
            //response.sendRedirect(request.getContextPath() + (!ok ? "/myList" : "/myList"));
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