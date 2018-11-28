-- to get product the user need to buy 

-- se non va:
-- set sql_mode= (SELECT replace(@@sql_mode, 'ONLY_FULL_GROUP_BY',''));
drop procedure if exists add_notifications;
DELIMITER $$
create procedure add_notifications()
begin
insert into Notifiche (ID_list, ID_prodotto, Giorni_mancanti, Quantita_mancanti, Creazione, Mail)
select final.ID_lista, final.ID_prodotto, final.giorni_manc, final.quant_manc, now() as ora, false as Mail from

	(select ID_lista, ID_prodotto, 
		minute(giorni_media - timediff(NOW(), ultimo)) as giorni_manc,
		(quant_media + quant_media - 
			(select Quantita from Liste_Prodotti_Acquistati where ID_lista = medie.ID_lista AND ID_prodotto = medie.ID_prodotto AND Data_acquisto = medie.ultimo)
			) as quant_manc,
		(select count(*) from Notifiche where ID_lista = Notifiche.ID_list and 
			Notifiche.Creazione between subdate(now(), 1) and now()) as tmp_count
        from

		(select ID_lista, ID_prodotto, AVG(Quantita) as quant_media, AVG(diff) as giorni_media, ultimo from

			(select a.ID_lista, a.ID_prodotto, a.Quantita, MIN(minute(timediff(b.Data_acquisto, a.Data_acquisto))) as diff, MAX(b.Data_acquisto) as ultimo from 
				(SELECT * FROM Liste_Prodotti_Acquistati) as a,
				(SELECT * FROM Liste_Prodotti_Acquistati) as b
				
				where DATEDIFF(a.Data_acquisto, b.Data_acquisto) < 0 AND a.ID_lista = b.ID_lista AND a.ID_prodotto = b.ID_prodotto
				
				group by a.Data_acquisto, a.ID_lista, a.ID_prodotto) as tmp
			group by ID_lista, ID_prodotto, ultimo) as medie) as final
	where quant_manc > 0 and giorni_manc > 0 and (ID_lista, ID_prodotto) not in (select ID_lista, ID_prodotto from Liste_Prodotti) 
        and tmp_count = 0;
end $$
delimiter ;
-- giorni_manc: tra quanti giorni deve fare l'acquisto (se negativo doveva farlo giorni fa)
-- quant_manc: quantità che manca da comprare tra "giorni_manc" giorni
