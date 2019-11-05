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
        int vanhasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(0, kassa.syoEdullisesti(240)); 
        assertEquals(vanhasaldo + 240, kassa.kassassaRahaa());
        assertEquals(myydyt + 1, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void epaOnnistunutEdullinenKateismaksu() {
        int vanhasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(200, kassa.syoEdullisesti(200)); 
        assertEquals(vanhasaldo, kassa.kassassaRahaa());
        assertEquals(myydyt, kassa.edullisiaLounaitaMyyty());
    }
    
    @Test
    public void onnistunutMaukasKateismaksu() {
        int vanhasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(0, kassa.syoMaukkaasti(400)); 
        assertEquals(vanhasaldo + 400, kassa.kassassaRahaa());
        assertEquals(myydyt + 1, kassa.maukkaitaLounaitaMyyty());
    }
    
    @Test
    public void epaOnnistunutMaukasKateismaksu() {
        int vanhasaldo = kassa.kassassaRahaa();
        int myydyt = kassa.edullisiaLounaitaMyyty();
        assertEquals(200, kassa.syoMaukkaasti(200)); 
        assertEquals(vanhasaldo, kassa.kassassaRahaa());
        assertEquals(myydyt, kassa.maukkaitaLounaitaMyyty());
    }
    
}
