/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  davide
 * Created: 06-Jun-2018
 */

CREATE DATABASE ShoppingList;
USE ShoppingList;

CREATE TABLE Utenti(
    Nome varchar(70) not null,
    Cognome varchar(70) not null,
    Email varchar(100) not null,
    Immagine varchar(150) not null,
    Password varchar(256) not null,
    IsAdmin boolean not null,
    Ultima_visualizzazione Timestamp,
    conf_string varchar(120) unique,
    primary key(Email)
);


CREATE TABLE Liste_categorie(
    Nome varchar(80) not null,
    Descrizione varchar(150),
    primary key(Nome)
);


CREATE TABLE Liste_categorie_immagini(
    Nome varchar(80) not null,
    Immagine varchar(150) not null,
    primary key(Nome, Immagine),
    foreign key(Nome) references Liste_categorie(Nome)    
);


CREATE TABLE Prodotti_categorie(
    Nome varchar(80) not null,
    Descrizione varchar(150),
    Logo varchar(80) not null,
    Nome_liste_cat varchar(80) not null,
    primary key(Nome),
    foreign key(Nome_liste_cat) references Liste_categorie(Nome)
);


CREATE TABLE Liste(
    ID integer not null auto_increment,
    Nome varchar(80) not null,
    Descrizione varchar(200),
    Immagine varchar(150) not null,
    Categoria varchar(80) not null,
    Owner varchar(100) not null,
    primary key(ID),
    foreign key(Categoria) references Liste_categorie(Nome),
    foreign key(Owner) references Utenti(Email)
);

CREATE TABLE Utenti_Liste(
    Email varchar(100) not null,
    ID integer not null,
    Perm_edit boolean not null, -- permesso di modificare le caratteristiche della lista
    Perm_add_rem boolean not null, -- permesso di aggiungere o rimuovere prodotti dalla lista
    Perm_del boolean not null, -- permesso di eliminare la lista
    Accettato boolean not null, -- per capire se utente ha accettato l invito o è in attesa
    Data_inserimento Timestamp not null,
    primary key(Email, ID),
    foreign key(Email) references Utenti(Email),
    foreign key(ID) references Liste(ID)
);

CREATE TABLE Prodotti(
    ID integer not null auto_increment,
    Nome varchar(80) not null,
    Note varchar(200),
    Logo varchar(80) not null,
    Categoria varchar(80) not null,
    Owner varchar(100) not null, -- se admin allora lo vedono tutti, altrimenti guardo in tabella Utenti_Prodotti
    primary key(ID),
    foreign key(Categoria) references Prodotti_categorie(Nome),
    foreign key(Owner) references Utenti(Email)
);

CREATE TABLE Prodotti_immagini(
    ID integer not null,
    Fotografia varchar(150) not null,
    primary key(ID, Fotografia),
    foreign key(ID) references Prodotti(ID)
);

CREATE TABLE Liste_Prodotti(
    ID_lista integer not null,
    ID_prodotto integer not null,
    Quantita integer not null, 
    primary key(ID_lista, ID_prodotto),
    foreign key(ID_lista) references Liste(ID),
    foreign key(ID_prodotto) references Prodotti(ID)
);


CREATE TABLE Liste_Prodotti_Acquistati(
    ID_lista integer not null,
    ID_prodotto integer not null,
    Data_acquisto date not null,
    Quantita integer not null, 
    primary key(ID_lista, ID_prodotto, Data_acquisto),
    foreign key(ID_lista) references Liste(ID),
    foreign key(ID_prodotto) references Prodotti(ID)
);

CREATE TABLE Chat(
    Email_sender varchar(100) not null,
    ID_list integer not null,
    Data Timestamp not null,
    Message varchar(200) not null,
    primary key(Email_sender, ID_list, Data),
    foreign key(Email_sender) references Utenti(Email),
    foreign key(ID_list) references Liste(ID)
);

-- prodotti non creati da admin che possono essere visti da determinati utenti
CREATE TABLE Utenti_Prodotti(
    Email varchar(100) not null,
    ID_prodotto integer not null,
    Data_inserimento Timestamp not null,
    primary key(Email, ID_prodotto),
    foreign key(Email) references Utenti(Email),
    foreign key(ID_prodotto) references Prodotti(ID)
);

CREATE TABLE Notifiche(
  ID integer auto_increment not null,
  ID_list integer not null,
  ID_prodotto integer not null,
  Giorni_mancanti integer not null,
  Quantita_mancanti integer not null,
  Creazione timestamp not null,
  Mail boolean not null,
  primary key(ID),
  foreign key(ID_list) references Liste(ID),
  foreign key(ID_prodotto) references Prodotti(ID)
);
