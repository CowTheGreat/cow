import requests
from bs4 import BeautifulSoup
import pandas as pd 

data = []
current_page = 1

while True:
    url = f"https://books.toscrape.com/catalogue/page-{current_page}.html"

    print(f"Scraping page {current_page}")
    page = requests.get(url)

    soup = BeautifulSoup(page.text,"html.parser")

    if "404 Not Found" in soup.title.text:
        break

    books = soup.find_all("li", class_="col-xs-6 col-sm-4 col-md-3 col-lg-3")

    for book in books:
        data.append({
            "Title":book.find("img")["alt"],
            "Link":"https://books.toscrape.com/catalogue/" +book.find("a")["href"]
        })
    current_page += 1

df = pd.DataFrame(data)
df.to_csv("books.csv")