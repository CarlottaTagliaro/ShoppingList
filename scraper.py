#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import requests
from bs4 import BeautifulSoup
import os
import urllib

url = "https://www.amazon.co.uk/s/field-keywords="

def getProducts(cat):
    c_url = url + urllib.parse.quote(cat)
    page = requests.get(c_url)

    soup = BeautifulSoup(page.text, 'lxml')


    for img in soup.select('div.a-fixed-left-grid-inner img'):

        pageDesc = requests.get(img.parent['href'])
        soupDesc = BeautifulSoup(pageDesc.text, 'lxml')
        try:
            desc = soupDesc.select_one("#productDescription p").text
        except:
            print("exception")
            continue

        nome = img['alt']
        src = img['src']

        response = requests.get(src)
        if response.status_code == 200:
            with open("images/" + os.path.basename(src), 'wb') as f:
                f.write(response.content)

        with open("insert_query/qry_" + cat + ".sql", 'a') as f:
            sql = 'INSERT INTO Prodotti (Nome, Note, Logo, Categoria, Owner) VALUES("' + nome + '", "' + desc + '", "", "' + cat + '", "g.s@agg.it");\n'
            sql += "INSERT INTO Prodotti_immagini (ID, Fotografia) VALUES((SELECT Max(ID) FROM cucciolo.Prodotti), '" + "imagesUpload/" + os.path.basename(src) + "');\n"
            sql = sql.replace("–", "-")

            f.write(sql)

with open("words.txt") as file:
    line = file.readline()
    while line:
        print(line)
        getProducts(line)
        line = file.readline()
