/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.core;

import charmaker2.core.character.CharacterDescriptor;
import charmaker2.core.character.CharacterSet;
import charmaker2.util.RSLogger;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;

/**
 *
 * @author Richard
 */
public class HeaderWriter
{
  public final static int ROTATION_0 = 0;
  public final static int ROTATION_90 = 1;
  public final static int ROTATION_180 = 2;
  public final static int ROTATION_270 = 3;
  
  public final static int MIRROR_NONE = 0;
  public final static int MIRROR_HORIZONTAL = 1;
  public final static int MIRROR_VERTICAL = 2;
  public final static int MIRROR_HORIZONTAL_VERTICAL = 3;
  
  public final static int DATATYPE_8Bit = 8;
  public final static int DATATYPE_16Bit = 16;
  public final static int DATATYPE_32Bit = 32;
  public final static int DATATYPE_64Bit = 64;
  
  public final static String OUTPUT_HEX_8BIT = "0x%02x";
  public final static String OUTPUT_HEX_16BIT = "0x%04x";
  public final static String OUTPUT_HEX_32BIT = "0x%08x";
  public final static String OUTPUT_HEX_64BIT = "0x%016x";
  
  private final static String DATATYPE_NAME_UCHAR = "unsigned char";
  private final static String DATATYPE_NAME_UINT_8 = "uint8_t";
  private final static String DATATYPE_NAME_UINT_16 = "uint16_t";
  private final static String DATATYPE_NAME_UINT_32 = "uint32_t";
  private final static String DATATYPE_NAME_UINT_64 = "uint64_t";
  
  private final static String COMMENT_BEGIN = "/** ";
  private final static String COMMENT_FURTHER = " * ";
  private final static String COMMENT_END = " */";
  private final static String COMMENT_SIGNLELINE = "// ";
  private final static String PRECOMPILER_IFNDEF = "#ifndef";
  private final static String PRECOMPILER_DEFINE = "#define";
  private final static String PRECOMPILER_ENDIF = "#endif";
  
  
  private final int rotation;
  private final int type;
  private final boolean mirrorHorizontal;
  private final boolean mirrorVertical;
  
  private final String datatype_name;
  private final String output_hex;  
  
  public HeaderWriter(int rotation, int type, boolean mirrorHorizontal, boolean mirrorVertical)
  {
    this.rotation = rotation;
    this.type = type;
    this.mirrorHorizontal = mirrorHorizontal;
    this.mirrorVertical = mirrorVertical;
    
    switch (type) {
      case DATATYPE_8Bit: this.datatype_name = this.DATATYPE_NAME_UINT_8; this.output_hex = this.OUTPUT_HEX_8BIT; break;
      case DATATYPE_16Bit: this.datatype_name = this.DATATYPE_NAME_UINT_16; this.output_hex = this.OUTPUT_HEX_16BIT; break;
      case DATATYPE_32Bit: this.datatype_name = this.DATATYPE_NAME_UINT_32; this.output_hex = this.OUTPUT_HEX_32BIT; break;
      case DATATYPE_64Bit: this.datatype_name = this.DATATYPE_NAME_UINT_64; this.output_hex = this.OUTPUT_HEX_64BIT; break;
      default: this.datatype_name = this.DATATYPE_NAME_UCHAR; this.output_hex = this.OUTPUT_HEX_8BIT; break;
    }

  }
  
  public HeaderWriter(int rotation, int type, String typename, boolean mirrorHorizontal, boolean mirrorVertical)
  {
    this.rotation = rotation;
    this.type = type;
    this.mirrorHorizontal = mirrorHorizontal;
    this.mirrorVertical = mirrorVertical;
    
    this.datatype_name = typename;    
    switch (type) {
      case DATATYPE_8Bit: this.output_hex = this.OUTPUT_HEX_8BIT; break;
      case DATATYPE_16Bit: this.output_hex = this.OUTPUT_HEX_16BIT; break;
      case DATATYPE_32Bit: this.output_hex = this.OUTPUT_HEX_32BIT; break;
      case DATATYPE_64Bit: this.output_hex = this.OUTPUT_HEX_64BIT; break;
      default: this.output_hex = this.OUTPUT_HEX_8BIT; break;
    }
  }
  
  private String aquireCharacter(CharacterDescriptor c)
  {
    long value = 0;
    String output = "{";
    //int x = c.getGrid().getXSize();
    GridIterator it;
    try {
      it = new GridIterator(c.getGrid(), rotation, mirrorHorizontal, mirrorVertical);
      for (it.x=it.xBegin; it.conditionX(); it.x+=it.xDirection)
      {
        //int y = c.getGrid().getYSize();
        int pos = 0;
        for (it.y=it.yBegin; it.conditionY(); it.y+=it.yDirection)
        {
          int b;
          if (c.getGrid().isSetAt(it.getColumnIterator(), it.getRowIterator()))
          {
            b = 1;
          }
          else
          {
            b = 0;
          }
          value += b << pos;
          pos += 1;
        }
        output = output.concat(String.format(this.output_hex, value));
        output = output.concat(", ");
        value = 0;
      }
      output = output.substring(0, output.length()-2);
      output = output.concat("}");
    } catch (Exception ex) {
      RSLogger.getLogger().log(Level.SEVERE, null, ex);
    }
    
    return output;
  }
  
  private String makeComment(CharacterDescriptor c)
  {
    String output = COMMENT_SIGNLELINE + "\t";
    try {
      int rotationComment = (this.rotation + 3) % 4;
      
      GridIterator it = new GridIterator(c.getGrid(), rotationComment, !mirrorVertical, mirrorHorizontal);
      for (it.x=it.xBegin; it.conditionX(); it.x+=it.xDirection)
      {
        for (it.y=it.yBegin; it.conditionY(); it.y+=it.yDirection)
        {
          int b;
          if (c.getGrid().isSetAt(it.getColumnIterator(), it.getRowIterator()))
          {
            output = output.concat("#\t");
          }
          else
          {
            output = output.concat(".\t");
          }
        }
        output = output.concat("\n");
        output = output.concat(COMMENT_SIGNLELINE);
        output = output.concat("\t");      
      }
      output = output.concat("\n");
    } catch (Exception ex) {
      RSLogger.getLogger().log(Level.SEVERE, null, ex);
    }
    return output;
  }
  
  private String declareFontArray(CharacterSet charaSet)
  {
    String output = this.datatype_name;
    output = output.concat(String.format("* %s[%d] = {\n", 
            charaSet.getFontName().replace(' ', '_'),
            charaSet.getSize()));
    return output;
  }
  
  private String closeFontArray()
  {
    String output = "};\n";
    return output;
  }
  
  private String addToFontArray(CharacterDescriptor c, boolean lastone)
  {
    String output = String.format("character_%s", c.getDescriptor());
    if (lastone)
      output = output.concat("\n");
    else
      output = output.concat(",\n");
    return output;
  }
  
  private String declareWidthArray(CharacterSet charaSet)
  {
    String output = "int";
    output = output.concat(String.format("* %s_width[%d] = {\n",
            charaSet.getFontName().replace(' ', '_'),
            charaSet.getSize()));
    return output;
  }
  
  private String closeWidthArray()
  {
    String output = "};\n";
    return output;
  }
  
  private String addToWidthArray(CharacterDescriptor c, boolean lastone)
  {
    String output = String.format("%d", c.getWidth());
    if (lastone)
      output = output.concat("\n");
    else
      output = output.concat(",\n");
    return output;
  }
  
  private String declareCharacter(CharacterDescriptor c)
  {
    String output = this.datatype_name;
    output = output.concat(String.format(" character_%s[%d] = ", c.getDescriptor(), c.getWidth()));
    output = output.concat(this.aquireCharacter(c));
    
    return output;
  }
  
  public void writeHeader(CharacterSet charaSet, File filepath) throws FileNotFoundException, IOException
  {
    String fontName = charaSet.getFontName().replace(' ', '_');
    String fileName = String.format("FONT_%s.h", fontName.toUpperCase());
    DataOutputStream fontHeader = new DataOutputStream(
              new FileOutputStream(filepath + File.separator + fileName));
    
    System.out.println("New File: " + filepath + File.separator + fileName);
    
    String comment = COMMENT_BEGIN + "\n"
                     + COMMENT_FURTHER + "@author: CharMaker by RSoft" + "\n"
                     + COMMENT_FURTHER + "\n"
                     + COMMENT_FURTHER + "This file represents the font " + charaSet.getFontName() + "\n"
                     + COMMENT_FURTHER + "Character count:" + String.format("%d", charaSet.getSize()) + "\n"
                     + COMMENT_FURTHER + "Character height:" + String.format("%d", charaSet.getFontHeight()) + "\n"
                     + COMMENT_FURTHER + "Character width (0 is variable):" + String.format("%d", charaSet.getFontWidth()) + "\n"
                     + COMMENT_FURTHER + "\n"
                     + COMMENT_FURTHER + "Rotation: " + String.format("%d", this.rotation*90)+ "\n"
                     + COMMENT_FURTHER + "Mirrored horizontally: " + Boolean.toString(mirrorHorizontal) + "\n"
                     + COMMENT_FURTHER + "Mirrored vertically: " + Boolean.toString(mirrorVertical) + "\n"
                     + COMMENT_END + "\n";
    
    fontHeader.writeBytes(comment);
    for (CharacterDescriptor c : charaSet.getCharacters())
    {
      fontHeader.writeBytes(this.declareCharacter(c));
      fontHeader.writeBytes("\t// ");
      fontHeader.writeByte(c.getCharacter());
      fontHeader.writeBytes("\n");
      fontHeader.writeBytes(this.makeComment(c));
    }
    
    fontHeader.writeBytes(this.declareFontArray(charaSet));
    for (int i=0; i<charaSet.getSize()-1; i+=1)
    {
      fontHeader.writeBytes(this.addToFontArray(charaSet.getCharacterAt(i), false));
    }
    fontHeader.writeBytes(this.addToFontArray(charaSet.getCharacterAt(charaSet.getSize()-1), true));
    fontHeader.writeBytes(this.closeFontArray());
    
    if (charaSet.getFontWidth() == 0)
    {
      fontHeader.writeBytes(this.declareWidthArray(charaSet));
      
      for (int i=0; i<charaSet.getSize()-1; i+=1)
      {
        fontHeader.writeBytes(this.addToWidthArray(charaSet.getCharacterAt(i), false));
      }
      fontHeader.writeBytes(this.addToWidthArray(charaSet.getCharacterAt(charaSet.getSize()-1), true));
      fontHeader.writeBytes(this.closeWidthArray());
    }
    
    fontHeader.close();
  }
}
