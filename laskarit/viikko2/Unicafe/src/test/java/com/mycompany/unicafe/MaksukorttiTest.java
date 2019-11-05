package com.mycompany.unicafe;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class MaksukorttiTest {

    Maksukortti kortti;

    @Before
    public void setUp() {
        kortti = new Maksukortti(10);
    }

    @Test
    public void luotuKorttiOlemassa() {
        assertTrue(kortti!=null);      
    }
    
    @Test
    public void saldoOikein() {
        assertEquals(10, kortti.saldo());      
    }
    
    @Test
    public void lataaminen() {
        int vanhaSaldo = kortti.saldo();
        kortti.lataaRahaa(10);
        assertEquals(vanhaSaldo + 10, kortti.saldo());
    }
    
    @Test
    public void veloitus() {
        int vanhaSaldo = kortti.saldo();
        kortti.otaRahaa(5);
        assertEquals(vanhaSaldo - 5, kortti.saldo());
        assertTrue(kortti.otaRahaa(5));
    }
    
    @Test
    public void yliVeloitus() {
        int vanhaSaldo = kortti.saldo();
        kortti.otaRahaa(15);
        assertEquals(vanhaSaldo, kortti.saldo());
    }
    
    @Test
    public void veloitusPalautusArvoTesti() {
        assertTrue(kortti.otaRahaa(9));
        assertFalse(kortti.otaRahaa(20));
    }
}
