package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class KassapaateTest {
    Kassapaate kassa;
   
    @Before
    public void setUp() {
        kassa = new Kassapaate();
    }
    
    @Test
    public void alkuraha() {
        assertEquals(100000, kassa.kassassaRahaa());
    }
    @Test
    public void alkumyyntiMaara() {
        assertEquals(0, kassa.maukkaitaLounaitaMyyty());
        assertEquals(0, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void onnistunutEdullinenKateismaksu() {
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(0, kassa.syoEdullisesti(240)); 
        assertEquals(kassasaldo + 240, kassa.kassassaRahaa());
        assertEquals(myydyt + 1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void epaOnnistunutEdullinenKateismaksu() {
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(200, kassa.syoEdullisesti(200)); 
        assertEquals(kassasaldo, kassa.kassassaRahaa());
        assertEquals(myydyt, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void onnistunutMaukasKateismaksu() {
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(0, kassa.syoMaukkaasti(400)); 
        assertEquals(kassasaldo + 400, kassa.kassassaRahaa());
        assertEquals(myydyt + 1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void epaOnnistunutMaukasKateismaksu() {
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(200, kassa.syoMaukkaasti(200)); 
        assertEquals(kassasaldo, kassa.kassassaRahaa());
        assertEquals(myydyt, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void onnistunutEdullinenKorttimaksu() {
        Maksukortti kortti = new Maksukortti(240);
        int korttisaldo = kortti.saldo();
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertTrue(kassa.syoEdullisesti(kortti)); 
        assertEquals(kassasaldo, kassa.kassassaRahaa());
        assertEquals(korttisaldo - 240, kortti.saldo());
        assertEquals(myydyt + 1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void epaOnnistunutEdullinenKorttimaksu() {
        Maksukortti kortti = new Maksukortti(200);
        int korttisaldo = kortti.saldo();
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertFalse(kassa.syoEdullisesti(kortti)); 
        assertEquals(kassasaldo, kassa.kassassaRahaa());
        assertEquals(korttisaldo, kortti.saldo());
        assertEquals(myydyt, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void onnistunutMaukasKorttimaksu() {
        Maksukortti kortti = new Maksukortti(400);
        int korttisaldo = kortti.saldo();
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.maukkaitaLounaitaMyyty();
        assertTrue(kassa.syoMaukkaasti(kortti)); 
        assertEquals(kassasaldo, kassa.kassassaRahaa());
        assertEquals(korttisaldo - 400, kortti.saldo());
        assertEquals(myydyt + 1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void epaOnnistunutMaukasKorttimaksu() {
        Maksukortti kortti = new Maksukortti(200);
        int korttisaldo = kortti.saldo();
        int kassasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertFalse(kassa.syoMaukkaasti(kortti)); 
        assertEquals(kassasaldo, kassa.kassassaRahaa());
        assertEquals(korttisaldo, kortti.saldo());
        assertEquals(myydyt, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void maksukorttiLataus() {
        Maksukortti kortti = new Maksukortti(0);
        int korttisaldo = kortti.saldo();
        int kassasaldo = kassa.kassassaRahaa();
        kassa.lataaRahaaKortille(kortti, 500);
        assertEquals(korttisaldo + 500, kortti.saldo());
        assertEquals(kassasaldo + 500, kassa.kassassaRahaa());
    }
    
    @Test
    public void tyhjaMaksukorttiLataus() {
        Maksukortti kortti = new Maksukortti(0);
        int korttisaldo = kortti.saldo();
        int kassasaldo = kassa.kassassaRahaa();
        kassa.lataaRahaaKortille(kortti, -1);
        assertEquals(korttisaldo, kortti.saldo());
        assertEquals(kassasaldo, kassa.kassassaRahaa());
    }
}
