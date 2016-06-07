/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control.models;

/**
 *
 * @author Richard
 */
public class FontSettings {
  
  public static final int ROTATION_0 = 0;
  public static final int ROTATION_90 = 1;
  public static final int ROTATION_180 = 2;
  public static final int ROTATION_270 = 3;
  
  public int rotation;
  public boolean mirrorHorizontal;
  public boolean mirrorVertical;
  public boolean alignAtTop;
  public String fontName;
  public String dataType;
  public int bits;
  
  public FontSettings(){}
  
}
