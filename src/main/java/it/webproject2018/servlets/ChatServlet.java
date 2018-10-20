/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.webproject2018.servlets;

import com.google.gson.Gson;
import it.webproject2018.db.daos.jdbc.JDBCListaDAO;
import it.webproject2018.db.daos.jdbc.JDBCMessaggioChatDAO;
import it.webproject2018.db.entities.Lista;
import it.webproject2018.db.entities.MessaggioChat;
import it.webproject2018.db.entities.Utente;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author davide
 */
@WebServlet(name = "ChatServlet", urlPatterns = {"/ChatServlet"})
public class ChatServlet extends HttpServlet {

    private JDBCListaDAO JdbcListaDao;
    private JDBCMessaggioChatDAO JdbcMessaggioChatDao;

    @Override
    public void init() throws ServletException {
        JdbcListaDao = new JDBCListaDAO(super.getServletContext());
        JdbcMessaggioChatDao = new JDBCMessaggioChatDAO(super.getServletContext());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        init();

        Utente user = (Utente) request.getSession().getAttribute("User");
        try {
            if (user != null) {
                String action = request.getParameter("action");
                if (action.equals("getChatList")) {
                    getChatList(user, response);
                } else if (action.equals("getChatMessages")) {
                    String str_chatId = request.getParameter("list_id");
                    if (str_chatId != null && !str_chatId.equals("")) {
                        int chat_list_id = Integer.parseInt(str_chatId);
                        String str_lasttime = request.getParameter("lastMsgTime");
                        Timestamp lasttime = null;
                        if (str_lasttime != null && !str_lasttime.equals("")) {
                            lasttime = new Timestamp(Long.parseLong(str_lasttime));
                        }
                        getChatMessages(user, chat_list_id, lasttime, response);
                    }
                } else if (action.equals("sendMessage")) {
                    int chat_list_id = Integer.parseInt(request.getParameter("list_id"));
                    String text = request.getParameter("text");
                    sendMessage(user, chat_list_id, text, response);
                }
            } else {
                response.setStatus(401);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500);
        }
            
        JdbcListaDao.Close();
        JdbcMessaggioChatDao.Close();
    }

    protected void getChatList(Utente user, HttpServletResponse response) {
        try {
            ArrayList<Lista> liste = JdbcListaDao.getUserLists(user.getEmail());
            ArrayList<ChatElement> chats = new ArrayList<>();
            for (Lista l : liste) {
                chats.add(new ChatElement(l.getId(), l.getNome(), l.getImmagine()));
            }

            Gson gson = new Gson();
            String json = gson.toJson(chats);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    protected void getChatMessages(Utente user, int chat_list_id, Timestamp lasttime, HttpServletResponse response) {
        try {
            ArrayList<MessaggioChat> messaggi = JdbcMessaggioChatDao.getChatLastMessages(chat_list_id, lasttime);

            ChatView chat = new ChatView();
            chat.id_lista = chat_list_id;
            chat.messages = new ArrayList<>();

            for (MessaggioChat msg : messaggi) {
                String timestamp = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(msg.getDate());
                chat.messages.add(new ChatMessage(msg.getSender().getEmail(), msg.getSender().getPicture(), msg.getSender().getEmail().equals(user.getEmail()), msg.getSender().getName(), msg.getSender().getSurname(), timestamp, msg.getDate().getTime(), msg.getMessage()));
            }

            Gson gson = new Gson();
            String json = gson.toJson(chat);

            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    protected void sendMessage(Utente user, int chat_list_id, String text, HttpServletResponse response) {
        Boolean res;
        try {
            MessaggioChat msg = new MessaggioChat(user, chat_list_id, text, new Timestamp(System.currentTimeMillis()));
            res = JdbcMessaggioChatDao.insert(msg) != null;
        } catch (Exception ex) {
            res = false;
        }

        if (res) {
            response.setStatus(201);// OK created
        } else {
            response.setStatus(500);// server internal error
        }
    }

    class ChatElement {

        public int id;
        public String nome;
        public String immagine;

        public ChatElement(int id, String nome, String immagine) {
            this.id = id;
            this.nome = nome;
            this.immagine = immagine;
        }
    }

    class ChatView {

        public int id_lista;
        public List<ChatMessage> messages;
    }

    class ChatMessage {

        public String email_utente;
        public String immagine;
        public Boolean isMe;
        public String nome;
        public String cognome;
        public String timestamp;
        public String messaggio;
        public Long milliseconds;

        public ChatMessage(String email_utente, String immagine, Boolean isMe, String nome, String cognome, String timestamp, Long milliseconds, String messaggio) {
            this.email_utente = email_utente;
            this.immagine = immagine;
            this.isMe = isMe;
            this.nome = nome;
            this.cognome = cognome;
            this.timestamp = timestamp;
            this.milliseconds = milliseconds;
            this.messaggio = messaggio;
        }
    }
}
