package it.unibs.fp.codice_fiscale;

public class Menu {
    public void codiceFiscaleGestion() {
        XMLParser streamReader = new XMLParser();
        streamReader.parseTheXMLInputDati();

        XMLParser streamReader2 = new XMLParser();
        streamReader2.parseTheXMLcodiciFiscali();

        XMLParser streamReader3 = new XMLParser();
        streamReader3.parseTheXMLComuni();
    }
}
