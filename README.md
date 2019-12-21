## Kääntelypeli (ohjelmistotekniikan harjoitustyö 2019)
Pelin ideana on päästä maaliin kääntelemällä erilaisia tasoja nuolinäppäimillä. Versiossa 0.3.1 on 5 tasoa ja tasonmuokkaustyökalu. Tasonmuokkaustyökalu tukee paikallista- ja palvelimelle tallentamista, ja palvelimelta tasojen avaamista muokattaviksi tai pelattaviksi.

## Linkkejä

[Tuntikirjanpito](https://github.com/xylix/ot-harjoitustyo2019/blob/master/dokumentaatio/tuntikirjanpito.md)

[Vaatimusmaarittely](https://github.com/xylix/ot-harjoitustyo2019/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/xylix/ot-harjoitustyo2019/blob/master/dokumentaatio/arkkitehtuuri.md)

[Testaus](https://github.com/xylix/ot-harjoitustyo2019/blob/master/dokumentaatio/testaus.md)

[Lopullinen julkaisu](https://github.com/xylix/ot-harjoitustyo2019/releases/tag/v0.3.1)

## Komentorivitoiminnot

Komennot tulee ajaa kansiossa ot-harjoitustyo2019/kaantelypeli/

Kansiosta kaantelypeli/utils löytyy scriptejä ohjelman ajamiseen / testien suorittamiseen.

## Ohjelman testaaminen
[Testaus](https://github.com/xylix/ot-harjoitustyo2019/blob/master/dokumentaatio/testaus.md)

## Jacoco-testikattavuus raportin generointi
`mvn test jacoco:report`

## Checkstyle raportin generointi
`mvn checkstyle:checkstyle`
`mvn site` tuottaa raporttiin toimivat ikonit. Checkstyle ajetaan myös mavenilla paketoinnin ja testaamisen yhteydessä automaattisesti.

## Suoritettavan .jar:in generointi
`mvn package`

## Ohjelman käynnistäminen
Ohjelma käynnistetään ajamalla komento `mvn compile exec:java -Dexec.mainClass=kaantelypeli.ui.Main`


