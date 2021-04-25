package it.unibs.fp.utilities;
/*
Questa classe rappresenta un menu testuale generico a piu' voci
Si suppone che la voce per uscire sia sempre associata alla scelta 0 
e sia presentata in fondo al menu

*/
public class MyMenu
{
  final private static String FRAME = "--------------------------------";
  final private static String OUTPUT_STRING = "0\tEsci";
  final private static String INPUT_REQUEST = "Digita il numero dell'opzione desiderata > ";

  private String title;
  private String [] voices;

	
  public MyMenu (String title, String [] voices)
  {
	this.title = title;
	this.voices = voices;
  }

  public int choose ()
  {
	printMenu();
	return DataInput.readIntWIthMaxAndMin (INPUT_REQUEST, 0, voices.length);
  }
		
  public void printMenu ()
  {
	System.out.println(FRAME);
	System.out.println(title);
	System.out.println(FRAME);
    for (int i=0; i<voices.length; i++)
	 {
	  System.out.println( (i+1) + "\t" + voices[i]);
	 }
    System.out.println();
	System.out.println(OUTPUT_STRING);
    System.out.println();
  }
		
}

