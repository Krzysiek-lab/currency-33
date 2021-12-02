1. Aplikacje uruchamia sie n porcie 8090,
2. Aplikacja ma 5 widoków nawigowalnych przyciskami:

- do uzyskania kursu wybranej waluty (adres: /)
- do przeliczenia podanej wartosci pieniedzy (Calculate) (ilosc i waluta) na wybrana walute, wynik bedzie podany w
  postaci calosci i reszty za ktoreą nie mozna juz kupi wymaganej waluty np. 100 EUR na USD = 109.78 USD i .09 EUR,
  przeliczenia sa wykonane na podstawie aktualnych kursow walut jednak mozna wykonac przeliczenia na podstawie starszych
  kursow jesli w pola dateFrom i DateTo poda sie zakres dat z ktorych chce sie uzyskac wartosci kursow przeliczyc wynik
  ,o przycisnieciu przucisku calculate skierowani zostaniemy na strone z wynikiem
- do pokazania wszystkich dosttepnych kursow walut (allRates) klikajac na rzycisk bez podania dat w polach uzyskamy
  aktualne kursy walut a jesli podamy zakre dat to uzyskamy wartoci kursów z podanych dat
- do pokazania wszytkich wczesniej dokonanych obliczn i ich wynikow, widok posiada paginacje

Wszystkie wyniki zapisywane sa do bazy danych.