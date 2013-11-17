package lejos.nxt;

public class Button
{
  public static final int ID_ENTER = 0x1;
  public static final int ID_LEFT = 0x2;
  public static final int ID_RIGHT = 0x4;
  public static final int ID_ESCAPE = 0x8;

  private int iCode;
  
  public static final String VOL_SETTING = "lejos.keyclick_volume";
  
  /**
   * The Enter button.
   */
  public static final Button ENTER = new Button (ID_ENTER);
  /**
   * The Left button.
   */
  public static final Button LEFT = new Button (ID_LEFT);
  /**
   * The Right button.
   */
  public static final Button RIGHT = new Button (ID_RIGHT);
  /**
   * The Escape button.
   */
  public static final Button ESCAPE = new Button (ID_ESCAPE);
   
  private Button (int aCode)
  {
    iCode = aCode;
  }

  /**
   * Return the ID of the button. One of 1, 2, 4 or 8.
   * @return the button Id
   */
  public final int getId()
  {
    return iCode;
  }
    
  /**
   * Devuelve false debido a que en el simulador aún no está implementada la interfaz de botones y
   *  existe un boton detener el cual detiene la simulación.
   */
  public final boolean isPressed()
  {
     return false;
  }
  
}
  


