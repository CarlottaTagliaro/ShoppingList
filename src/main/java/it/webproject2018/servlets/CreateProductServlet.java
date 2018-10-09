package it.webproject2018.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.nio.file.Files;
import it.webproject2018.db.daos.jdbc.JDBCProdottoDAO;
import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author Stefano
 */
public class CreateProductServlet extends HttpServlet {

    private JDBCProdottoDAO JDBCProdotto;

    @Override
    public void init() throws ServletException {
        JDBCProdotto = new JDBCProdottoDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter w = response.getWriter();
        try {
            Utente user = (Utente) request.getSession().getAttribute("User");
            String name = request.getParameter("name");
            String category = request.getParameter("selectCategory");
            String description = request.getParameter("description");

            Prodotto prod = new Prodotto(null, name, description, null, null, new CategoriaProdotti(category));
            prod.setOwner(user);
            prod = JDBCProdotto.insert(prod);
            Boolean ok = prod != null;
            if (ok) {
                List<Object> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName()))
                        .collect(Collectors.toList());

                for (Object oFilePart : fileParts) {
                    Part filePart = (Part) oFilePart;
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    InputStream fileContent = filePart.getInputStream();

                    Path pathToFile = Paths.get(getServletContext().getRealPath(File.separator) + "imagesUpload/" + fileName);

                    Files.copy(fileContent, pathToFile);
                    String img = "imagesUpload/" + fileName;
                    prod.Fotografie.add(img);
                    JDBCProdotto.insertImage(prod, img);
                }
            }
            response.sendRedirect(request.getContextPath() + (!ok ? "/newProduct" : "/myProducts"));
        } catch (DAOException e) {
            w.println(e.getMessage());
        }
    }
}
