# Vaatimusmäärittely

Sovelluksen tarkoitus: 
Painovoima-labyrintti peli, jossa pelaaja pelaa "pyörittämällä" labyrinttiä. Hahmo liikkuu ainoastaan painovoiman avulla, ja pelaajan on tarkoitus selvittää labyrinttejä loppuun asti. Peli on käytännössä vuoropohjainen, mutta "valuminen" lienee järkevää renderöidä reaaliaikaisesti.

- Käyttäjäroolit
    - Tavallinen pelaaja

- Suunnitellut toiminnallisuudet
    - MVP toiminnallisuus
        - Sovellus aukeaa päävalikkoon jossa on "pelaa" -nappi
        - Käyttäjä voi pelata (vähintään yhtä) kenttää
        - Pelimekaniikat toimivat (pääosin) bugittomasti
        - Peli käyttää Javaksi kirjoitettuja kenttämäärittelyjä

    - Kurssin loppuun mennessä toivottu toiminnallisuus
      - Peli tukee monimutkaisempia kenttäominaisuuskia (epäonnistumisen aiheuttavia "kuoppia")
      - Yksinkertaisen tekoälyn mukaan liikkuvia esteitä / vastustajia
        - Peli lukee kenttätiedostoja (esimerkiksi ASCII merkeillä määriteltyjä kenttiä)
      - Asetukset valikko
      - Yksinkertaisiä äänitehosteita (esimerkiksi kentän pyöritysääniä, taustamusiikki)

- Jatkokehitysideat
