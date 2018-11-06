#!/usr/local/bin/python3
# -*- coding: utf-8 -*-

import requests
from bs4 import BeautifulSoup
import os
import urllib
import time

url = "https://www.amazon.co.uk/s/field-keywords="
headers = {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.95 Safari/537.36'}


def getProducts(cat):
    c_url = url + urllib.parse.quote(cat)
    time.sleep(2)
    page = requests.get(c_url, headers = headers)
    
    soup = BeautifulSoup(page.text, 'lxml')


    for img in soup.select('div.a-fixed-left-grid-inner img.s-access-image.cfMarker'):
        url_prod = img.parent['href']
        if url_prod[:8] != 'https://':
            url_prod = "https://www.amazon.co.uk/" + img.parent['href']
        time.sleep(2)
        pageDesc = requests.get(url_prod, headers = headers)
        soupDesc = BeautifulSoup(pageDesc.text, 'lxml')
        try:
            desc = soupDesc.select_one("#productDescription p").text
        except Exception as e:
            print(e)
            continue

        nome = img['alt']
        src = img['src']

        time.sleep(2)
        response = requests.get(src, headers = headers)
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
