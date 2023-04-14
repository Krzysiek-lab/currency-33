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
