# Arkkitehtuurikuvaus

## Rakenne

Ohjelma toteuttaa UI - Pelilogiikka erottelun, ja pakkaukset on nimetty sen mukaisesti kaantelypeli.engine ja kaantelypeli.ui. Lisäksi ohjelmassa on paketti utils, joka sisältää staattisia apumetodeja. Luokat paketissa utils on nimetty niiden käyttötarkoituksen mukaan.

UI on käyttöliittymää ja siihen välittömästi liittyviä (esimerkiksi näppäinpainallusten bindaus) toimintoja. Engine on pelin olioiden ja fysiikan käsittelyä.

## Käyttöliittymä

Pelissä on 
* päävalikko
* tasovaihtoehdot
* pilvitasovalikko
* muokkaustasovalikko
* tasonmuokkaustyökalu.

[Kuvia](https://imgur.com/a/CAp9y3w)

Käyttöliittymä on kohtuullisen eroteltu pelilogiikasta. Luokka Game suorittaa vain pelilogiikan kutsumista ja logiikan JavaFX:ään bindausta. Luokka LevelEditor toteuttaa huomattavasti heikompaa erottelua, sillä sen logiikkaa liittyy todella suorasti käyttöliittymään.

## Sovelluslogiikka

Erillaisten näkymien välillä liikutaan kutsumalla JavaFX:n Applicationiin injektoiman Stagen `setScene()` metodia kohtausten asettamiseen.

Tiedostojen lataamiseen minimaaliselta Flaskilla implementoidulta tiedostopalvelimelta käytetään `Unirest` kirjastoa, joka sallii yksinkertaisen verkkopyyntösyntaksin.

Tasojen päivittäminen sidotaan JavaFXn animaatioajastimiin kun tasoja tehdään aktiivisiksi.

## Kenttien ja hahmojen määrittely

Kentät ja hahmot määritellään kaantelypeli/main/java/resources/ alla olevissa kansioissa `entities` ja `levels`. Olentojen välisen interaktion ohjelmointia on abstraktoitu niin, että tiedostojen avulla voi muuttaa esimerkiksi mitkä esteet ovat tappavia ja halutessa lisätä peliin uusia esteitä. Uuden palikan joka ei vaadi uutta ohjelmallista toiminnallisuutta (esimerkiksi sinistä laavaa tai "vettä") implementointi tapahtuisi suoraan lisäämällä entities kansioon kopio tiedostosta lava, jolla on oma nimi ja grafiikkaviittaus. Palikkamäärittelyssä voidaan myös viitata animaatioon, kuten pelaajamäärittely `player.json` tekeekin.

Tasot määritellään JSON muotoisina listoina palikoiden sijainteja ja tyyppejä. Lisäksi tasoon voi kirjoittaa aloituspainovoiman suunnan tai tehdä tason jossa pelaaja on heti alussa hävinnyt, mutta näille ei ole vielä löytynyt mielekästä käyttötarkoitusta kenttäsuunnittelussa.

JSON parsimiseen hyödynnetään Gson kirjastoa, ja tietoluokkaa "Properties" joka toimii palikoiden ominaisuuksien JSON parsinnan ('EntitySerializer') apuvälineenä.

## Arkkitehtuurissa parannettavaa

LevelEditorin logiikan voisi eristää omaan pakettiinsa, ja tehdä sille käyttöliittymäluokan joka vain hyödyntää logiikkakutsuja / oliota. 

Päävalikkotoiminnallisuuden voisi erottaa luokasta Game. Tällä hetkellä Game hoitaa sekä päävalikon alustamisen että pelin tasojen näyttämisen.
