package it.unibs.fp.codice_fiscale;

//@Author Baresi Marco, Contestabile Martina, Iannella Simone
public class CodiceFiscale {
    public static void main (String[] args) {
        XMLParser streamReader = new XMLParser();
        streamReader.parseTheXMLInputDati();

        XMLParser streamReader2 = new XMLParser();
        streamReader2.parseTheXMLcodiciFiscali();
    }
}
