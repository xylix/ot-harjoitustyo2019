## Kääntelypeli (ohjelmistotekniikan harjoitustyö 2019)
Pelin ideana on päästä maaliin kääntelemällä erilaisia tasoja nuolinäppäimillä. Versiossa 0.2.2 on 5 tasoa ja tasonmuokkaustyökalu.

## Linkkejä

[Tuntikirjanpito](https://github.com/xylix/ot-harjoitustyo2019/blob/master/tuntikirjanpito.md)

[Vaatimusmaarittely](https://github.com/xylix/ot-harjoitustyo2019/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Arkkitehtuuri](https://github.com/xylix/ot-harjoitustyo2019/blob/master/dokumentaatio/arkkitehtuuriKuvaus.md)

[Julkaisu viikko 5](https://github.com/xylix/ot-harjoitustyo2019/releases/tag/v0.0.1)

## Komentorivitoiminnot

Komennot tulee ajaa kansiossa ot-harjoitustyo2019/kaantelypeli/

## Ohjelman testaaminen
`mvn test`

## Jacoco-testikattavuus raportin generointi
`mvn test jacoco:report`

## Checkstyle raportin generointi
`mvn checkstyle:checkstyle`
`mvn site` tuottaa raporttiin toimivat ikonit. Checkstyle ajetaan myös mavenilla paketoinnin ja testaamisen yhteydessä automaattisesti.

## Suoritettavan .jar:in generointi
`mvn package`

## Ohjelman käynnistäminen
Ohjelma käynnistetään ajamalla komento `mvn compile exec:java -Dexec.mainClass=kaantelypeli.ui.Game`


