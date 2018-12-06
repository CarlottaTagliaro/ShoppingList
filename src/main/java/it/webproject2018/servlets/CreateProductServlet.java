package it.webproject2018.servlets;

import it.webproject2018.db.daos.ProdottoDAO;
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
import it.webproject2018.db.entities.CategoriaProdotti;
import it.webproject2018.db.entities.Prodotto;
import it.webproject2018.db.entities.Utente;
import it.webproject2018.db.exceptions.DAOException;
import it.webproject2018.db.exceptions.DAOFactoryException;
import it.webproject2018.db.factories.DAOFactory;
import java.io.File;
import java.nio.file.Path;
import java.security.SecureRandom;

/**
 *
 * @author Stefano
 */
public class CreateProductServlet extends HttpServlet {

    private ProdottoDAO prodottoDao;

    @Override
    public void init() throws ServletException {
		DAOFactory daoFactory = (DAOFactory) super.getServletContext().getAttribute("daoFactory");
		if (daoFactory == null) {
            throw new ServletException("Impossible to get dao factory for storage system");
        }
		try {
			prodottoDao = daoFactory.getDAO(ProdottoDAO.class);
		} catch (DAOFactoryException ex) {
            throw new ServletException("Impossible to get dao factory for prodotto storage system", ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        init();
        
        PrintWriter w = response.getWriter();
        try {
            Utente user = (Utente) request.getSession().getAttribute("User");
            String name = request.getParameter("name");
            String category = request.getParameter("selectCategory");
            String description = request.getParameter("description");

            Prodotto prod = new Prodotto(null, name, description, null, null, new CategoriaProdotti(category), user.getEmail());
            prod = prodottoDao.insert(prod);
            Boolean ok = prod != null;
            if (ok) {
                List<Object> fileParts = request.getParts().stream().filter(part -> "file".equals(part.getName()))
                        .collect(Collectors.toList());

                for (Object oFilePart : fileParts) {
                    Part filePart = (Part) oFilePart;
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    InputStream fileContent = filePart.getInputStream();

                    try {
                        String ext = fileName.substring(fileName.lastIndexOf("."));
                        fileName = randomString(70) + ext; //assign random name
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    String img = "imagesUpload/" + fileName;

                    Path pathToFile = Paths.get(getServletContext().getRealPath(File.separator) + img);

                    Files.copy(fileContent, pathToFile);
                    prod.Fotografie.add(img);
                    prodottoDao.insertImage(prod, img);
                }
            }
        
            response.sendRedirect(request.getContextPath() + (!ok ? "/newProduct" : "/myProducts"));
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
