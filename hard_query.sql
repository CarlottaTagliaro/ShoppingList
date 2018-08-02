-- to get product the user need to buy 

select medie.ID_lista, medie.ID_prodotto, 
	(medie.giorni_media - DATEDIFF(NOW(), ultimo.Data_acquisto)) as giorni_manc,
    (medie.quant_media + medie.quant_media - ultimo.Quantita) as quant_manc from

	(select ID_lista, ID_prodotto, AVG(Quantita) as quant_media, AVG(diff) as giorni_media from 
		(select a.ID_lista, a.ID_prodotto, a.Quantita, MIN(DATEDIFF(a.Data_acquisto, b.Data_acquisto)) as diff from 
			(SELECT * FROM Liste_Prodotti_Acquistati order by Data_acquisto desc limit 5) as a,
			(SELECT * FROM Liste_Prodotti_Acquistati order by Data_acquisto desc limit 1, 5) as b
			
			where DATEDIFF(a.Data_acquisto, b.Data_acquisto) > 0 group by a.Data_acquisto) as tmp) as medie
	join 
		(SELECT * FROM Liste_Prodotti_Acquistati order by Data_acquisto desc limit 1) as ultimo
	on medie.ID_lista = ultimo.ID_lista AND medie.ID_prodotto = ultimo.ID_prodotto;
