# Kayttoohje

Lataa tiedosto [kaantelypeli](https://github.com/xylix/ot-harjoitustyo2019/releases/download/v0.3.1/kaantelypeli-ALL-PLATFORMS-0.3.1.jar)

Käynnistä ohjelma komennolla `java -jar kaantelypeli-0.3.1.jar`

Käyttöliittymässä voit valita pelata jotain lokaaleista tasoista, tai avata napilla "cloud-levels" jonkun palvelimella sijaitsevan tason.

Napilla "editor" avataan tasonluontityökalu, jossa voit käyttäen jotain tasoa pohjana luoda uusia tasoja ja tallentaa niitä paikallisesti, tai tallentaa muokatun version palvelimelle. 

Editorissa palikoita lisätään painamalla jotain kohtaa, ja sitten laittamalla lisättävän palikan tiedot aukeavaan dialogiin. Pelioliot määritellään kansiossa kaantelypeli/src/main/resources/entitys . Versiossa 0.3.1 sallittuja oliotyyppejä ovat `wall`, `victory`, `player`, `lava`, `key` sekä `door`. Kaikkien palikkatyyppien olioita voi olla tasolla rajattomasti. Palikkojen oletuskoko on 16 x 16 pikseliä, paitsi ovella ja pelaajalla joiden oletuskoko on 14x14 pikseliä helpottaakseen kolojen läpi pääsyä.

* Wall on pelin peruspalikka, jonka ainut funktio on estää olioita kulkemasta sen läpi ja skaalautua järkevästi vaihtelevan kokoiseksi.
* Victory on voittoruutu. Ilman voittoruutua tasoa ei voi päästä läpi.
* Player on pelaajahahmo. Pelaajahahmon koskiessa voittoruutua pelaaja voittaa tason. Pelaajahahmon koskiessa laava pelaaja häviää tason ja joutuu aloittamaan alusta.
* Laava on estepalikka, joka liikkuu ja voi aiheuttaa pelaajan häviön.
* Key on liikkuva avainpalikka, jolla voi avata ovia.
* Door on avattava palikka.
