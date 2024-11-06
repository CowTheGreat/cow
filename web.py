import requests
from bs4 import BeautifulSoup
import pandas as pd

data = []
current_page = 1

while True:
    url = f"https://books.toscrape.com/catalogue/page-{current_page}.html"
    print(f"Scraping page {current_page}")

    try:
        page = requests.get(url, timeout=10)
        page.raise_for_status()
    except requests.RequestException as e:
        print(f"Request failed: {e}")
        break

    soup = BeautifulSoup(page.text, "html.parser")
    if "404 Not Found" in soup.title.text:
        break

    books = soup.find_all("li", class_="col-xs-6 col-sm-4 col-md-3 col-lg-3")
    for book in books:
        data.append({
            "Title": book.find("img")["alt"],
            "Link": "https://books.toscrape.com/catalogue/" + book.find("a")["href"],
            "Price": book.find("p", class_="price_color").text[2:],
            "Stock": book.find("p", class_="instock availability").text.strip().split("\n")[0]
        })

    current_page += 1

df = pd.DataFrame(data)
df.to_excel("books.xlsx", index=False)
df.to_csv("books.csv", index=False)
print("Scraping complete. Data saved to books.xlsx and books.csv")
