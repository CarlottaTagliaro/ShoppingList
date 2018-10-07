-- to get product the user need to buy 
select without_owner.ID_lista, without_owner.ID_prodotto, without_owner.giorni_manc ,Liste.Owner from
(select * from

	(select ID_lista, ID_prodotto, 
		(giorni_media - DATEDIFF(NOW(), ultimo)) as giorni_manc,
		(quant_media + quant_media - 
			(select Quantita from Liste_Prodotti_Acquistati where ID_lista = medie.ID_lista AND ID_prodotto = medie.ID_prodotto AND Data_acquisto = medie.ultimo)
			) as quant_manc from

		(select ID_lista, ID_prodotto, AVG(Quantita) as quant_media, AVG(diff) as giorni_media, ultimo from

			(select a.ID_lista, a.ID_prodotto, a.Quantita, MIN(DATEDIFF(b.Data_acquisto, a.Data_acquisto)) as diff, MAX(b.Data_acquisto) as ultimo from 
				(SELECT * FROM Liste_Prodotti_Acquistati) as a,
				(SELECT * FROM Liste_Prodotti_Acquistati) as b
				
				where DATEDIFF(a.Data_acquisto, b.Data_acquisto) < 0 AND a.ID_lista = b.ID_lista AND a.ID_prodotto = b.ID_prodotto
				
				group by a.Data_acquisto, a.ID_lista, a.ID_prodotto) as tmp
			group by ID_lista, ID_prodotto) as medie) as final
	where quant_manc > 0 and (ID_lista, ID_prodotto) not in (select ID_lista, ID_prodotto from Liste_Prodotti)) as without_owner 
    join Liste on Liste.ID = without_owner.ID_lista;

-- giorni_manc: tra quanti giorni deve fare l'acquisto (se negativo doveva farlo giorni fa)
-- quant_manc: quantità che manca da comprare tra "giorni_manc" giorni
