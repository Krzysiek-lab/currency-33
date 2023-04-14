Aplikacje uruchamia sie n porcie 8090, Aplikacja ma 5 widoków nawigowalnych przyciskami:

- do uzyskania kursu wybranej waluty (adres: /)
- do przeliczenia podanej wartosci pieniedzy (Calculate) (ilosc i waluta) na wybrana walute, wynik bedzie podany w
  postaci calosci i reszty za ktorą nie mozna juz kupić wymaganej waluty np. 100 EUR na USD = 109.78 USD i .09 EUR,
  przeliczenia sa wykonane na podstawie aktualnych kursow walut jednak mozna wykonac przeliczenia na podstawie starszych
  kursow jesli w pola dateFrom i DateTo poda sie zakres dat z ktorych chce sie uzyskac wartosci kursow przeliczyc wynik
  ,o przycisnieciu przucisku calculate skierowani zostaniemy na strone z wynikiem
- do pokazania wszystkich dosttepnych kursow walut (allRates) klikajac na przycisk bez podania dat w polach uzyskamy
  aktualne kursy walut a jesli podamy zakres dat to uzyskamy wartosci kursów z podanych dat
- do pokazania wszytkich wczesniej dokonanych obliczen i ich wynikow, widok posiada paginacje
- do wyswietlenia historii podanej waluty 7 dni wstecz

Wszystkie wyniki zapisywane sa do bazy danych.


To run this application using docker you need to:
- docker pull docker pull ignatiuk/currency-33:baza
- docker pull docker pull ignatiuk/currency-33:curr
- docker run -p 3307:3306 --name currency -e MYSQL_ROOT_PASSWORD=ZADD -td ignatiuk/currency-33:baza (where "ZADD" is a password for an user in the mySQL database and "currency" is the name we give to a container)
- docker exec -it currency bash
- mysql -U root ZADD
- CREATE DATABASE currency; (steps 3-5 creating a database in ignatiuk/currency-33:baza container)
- docker run -p 8090:8090 -d ignatiuk/currency-33:curr
- application is now available at http://localhost:8090/all
