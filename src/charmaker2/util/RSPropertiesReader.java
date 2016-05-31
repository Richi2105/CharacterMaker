/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.util;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * a class that extends class properties
 * @see Properties
 * 
 * @author Richard
 */
public class RSPropertiesReader extends Properties
{
  protected String filepath;
  protected boolean error;

  /**
   * @param file: the name of the properties file, without the extention ".properties"
   * the file should be located in the folder of the application.
   * If the file does not exist, a default file will be written with the specified name
   * 
   * @exception IOException in case the default file cannot be written
   * 
   */
  public RSPropertiesReader(String file) throws IOException
  {
    this.filepath = file;
    this.error = false;
    FileInputStream fileInputStream;

    try
    {
      fileInputStream = new FileInputStream(file + ".properties");
      this.load(fileInputStream);
    }
    catch (FileNotFoundException ex)
    {
      this.error = true;
      System.err.println(ex.toString());
    }
  }
  
  protected void writeDefaultText(String defaultText) throws IOException
  {
    DataOutputStream ostream = new DataOutputStream(
              new FileOutputStream(filepath + ".properties"));
    ostream.writeBytes(defaultText);
    ostream.close();
    
    FileInputStream fileInputStream = new FileInputStream(filepath + ".properties");
    this.load(fileInputStream);
  }
  
  public void storeToFile()
  {
    try {
      DataOutputStream ostream = new DataOutputStream(
              new FileOutputStream(filepath + ".properties", false));
      this.store(ostream, "");
      ostream.close();
    } catch (Exception ex) {
      RSLogger.getLogger().log(Level.WARNING, null, ex);
    }
  }
}
