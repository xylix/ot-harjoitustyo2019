# Arkkitehtuurikuvaus

## Rakenne

Ohjelma toteuttaa UI - Pelilogiikka erottelun, ja pakkaukset on nimetty sen mukaisesti kaantelypeli.engine ja kaantelypeli.ui.

UI on käyttöliittymää ja siihen välittömästi liittyviä (esimerkiksi näppäinpainallusten bindaus) toimintoja. Engine
on pelin olioiden ja fysiikan käsittelyä.

## Käyttöliittymä

Pelin alkunäkymä on todella yksinkertainen JavaFX -napeista koostuva kentänvalitsin. Alkunäkymän jälkeen käyttäjä pääsee itse
peliin, jossa renderöityy reaaliaikaisesti kentän oliot, noudattaen yksinkertaista painovoimatoteutusta.

## Sovelluslogiikka

Pelimaailma koostuu JavaFX:n [Rectangle](https://openjfx.io/javadoc/11/javafx.graphics/javafx/scene/shape/Rectangle.html) 
-luokkaa perivistä Entity(String tyyppi, int x, int y) olioista. 

Pelitaso on yksittäinen Level() -olio, joka sisältää listan peliolioita. Fysiikat toteutetaan Level() luokan ja Entity() luokan
yhteistyöllä. Level() luokka sisältää metodin gravitate() jota kutsutaan jokaisella ruudunpiirrolla. Jokainen peliolio
käydään läpi, ja niistä kutsutaan metodia collide(`törmättävä`) joka selvittää törmäävätkö peliolio ja törmättävä kyseisellä
pelihetkellä. Jos törmäys tapahtuu, kutsutaan metodia collideAction(), joka palauttaa törmäyslopputuloksen.

## Kenttien tallennus Jsonina

Peliin on versiossa 0.0.2 implementoitu peliolioiden ja kenttien serialisointi -toiminnallisuus. Kun tälle on toteutettu
tiedostoon tallennus, peli tukee kenttien tallennusta (lähes syntaktisesti oikeassa) JSON -formaatissa.
