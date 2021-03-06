delimiter ;;

CREATE PROCEDURE `getNotifications` (user_email varchar(100))
BEGIN
select testo, tipo, data, id_elem, ifnull(new, true) as new from (

select CONCAT("You have new messages in the list chat '", Nome, "'!") as testo, "chat" as tipo, data, ID_list as id_elem, True as new from 
	(select ID_list, Max(Chat.Data) as data from Chat JOIN 
		(select ID from Utenti_Liste where Email = user_email) as a on ID = ID_list 
			where Chat.Data >= (select Ultima_visualizzazione from Utenti where Email = user_email) group by ID_list) as chat 
				join Liste on Liste.ID = chat.ID_list
union
        
select CONCAT(owner, " has shared '", Nome, "' list with you!") as testo, "list_share" as tipo, Data_inserimento as data, Liste.ID as id_elem, (Data_inserimento >= (select Ultima_visualizzazione from Utenti where Email = user_email)) as new
		from Utenti_Liste join Liste on Liste.ID = Utenti_Liste.ID where Email = user_email and Owner != email
union    
    
select CONCAT(owner, " has shared '", Nome, "' product with you!") as testo, "product_share" as tipo, Data_inserimento as data, Prodotti.ID as id_elem, (Data_inserimento >= (select Ultima_visualizzazione from Utenti where Email = user_email)) as new
		from Utenti_Prodotti join Prodotti on Prodotti.ID = Utenti_Prodotti.ID_prodotto where Email = user_email
            		
union

select CONCAT("Maybe you need to buy ", Quantita_mancanti, " ", Prodotti.Nome, " in ", Giorni_mancanti, " days for the '", Liste.Nome, "' list.") as testo,
	"previsioning" as tipo, Creazione as data, Notifiche.ID as id_elem, (Creazione >= (select Ultima_visualizzazione from Utenti where Email = user_email)) as new
		from Notifiche JOIN Prodotti ON ID_prodotto = Prodotti.ID JOIN Liste ON ID_list = Liste.ID JOIN Utenti_Liste ON Liste.ID = Utenti_Liste.ID
			WHERE Utenti_Liste.Email = user_email            
            ) as res
				order by data desc;
            
END;;

delimiter ;
