delimiter ;;

CREATE PROCEDURE `getNotifications` (user_email varchar(100))
BEGIN
select * from (

select CONCAT("You have new messages in the list chat '", Nome, "'!") as testo, "chat" as tipo, data, ID_list as id_elem from 
	(select ID_list, Max(Chat.Data) as data from Chat JOIN 
		(select ID from Utenti_Liste where Email = user_email) as a on ID = ID_list 
			where Chat.Data >= (select Ultima_visualizzazione from Utenti where Email = user_email) group by ID_list) as chat 
				join Liste on Liste.ID = chat.ID_list
union
        
select CONCAT(owner, " has shared '", Nome, "' list with you!") as testo, "list_share" as tipo, Data_inserimento, Liste.ID as id_elem
		from Utenti_Liste join Liste on Liste.ID = Utenti_Liste.ID where Email = user_email and Owner != email and
			Data_inserimento >= (select Ultima_visualizzazione from Utenti where Email = user_email)
union    
    
select CONCAT(owner, " has shared '", Nome, "' product with you!") as testo, "product_share" as tipo, Data_inserimento, Prodotti.ID as id_elem
		from Utenti_Prodotti join Prodotti on Prodotti.ID = Utenti_Prodotti.ID_prodotto where Email = user_email and
			Data_inserimento >= (select Ultima_visualizzazione from Utenti where Email = user_email)) as res
            
            order by data desc;
            

END;;

delimiter ;
