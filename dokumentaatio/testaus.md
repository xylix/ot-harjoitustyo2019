# Testausdokumentti

Ohjelmaa on testattu JUnitilla kirjoitetuilla yksikkö- ja integraatiotesteillä. LevelEditor -luokka ohjelmoitiin niin
myöhään ja kiireisesti että sille ei valitettavasti ole muuta kuin integraatiotestejä.

## Testit käytännössä

### Yksikkötestit
Ajetaan komennolla `mvn test`.

Yksikkötestejä löytyy joka luokalle niiden pakettia vastaavasta testipaketista. (Poikkeuksina EntitySerializer ja Properties,
joiden toiminnallisuus on yksikäsitteistä eikä erityisesti kaipaa testausta.) Pelilogiikkaa testataan importtailemalla, luomalla
ja muokkaamalla peliolioita ja tarkistaen että niiden tila on oikea. Pelilogiikalle on myös muutama kokonaisempi testi jotka
tarkastavat että testikentissä peli tapahtuu oikein. (Tarkastetaan että kentässä jossa pelaaja luodaan voittopalikan päälle 
voitto tapahtuu välittömästi, ja tarkastetaan että pelaaja ei voi mennä läpipääsemättömien pelipalikoiden läpi.

Testikattavuus (jättäen huomiotta LevelEditor luokan joka on lähinnä käyttöliittymäkoodia) on 
[testikattavuus.png]

### Integraatiotestit (Lähinnä LevelEditorille)
Ajetaan komennolla `mvn verify`.

Integraatiotestit testaavat LevelEditorin käyttöliittymää, tiedostojen avaamista palvelimelta ja tiedostojen tallentamista.


### Järjestelmätestaus
Sovelluksen toimivuus kokonaisuutena on testattu käsin. Sovellus toimii kuten pitää. 

## Sovellukseen jääneet viat
Internet-yhteyden puute (tai tiedostopalvelimen poist päältä olo) estää editoria aukeamasta, ja tätä ei ilmoiteta käyttäjälle
helpossa formaatissa.

