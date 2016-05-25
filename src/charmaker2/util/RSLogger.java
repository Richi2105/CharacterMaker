/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Richard
 */
public class RSLogger
{
  private static Logger instance = null;
  
  private RSLogger()
  {
  }
  
  public static Logger getLogger()
  {
    if (instance == null)
    {
      instance = Logger.getLogger("RSLogger");
      initLogger();
    }
    return instance;
  }
  
  private static void initLogger()
  {
    try
    {
      RSPropertiesReader_Logger propsReader = new RSPropertiesReader_Logger("logger");
      
      FileHandler fh = new FileHandler(propsReader.getProperty("FILE"), 
              10000, 1, Boolean.parseBoolean(propsReader.getProperty("APPEND")));
      fh.setFormatter(new RSFormatter());
      instance.addHandler(fh);
      instance.setLevel(Level.parse(propsReader.getProperty("LEVEL")));
    }
    catch (IOException ex)
    {
      System.err.println(ex);
    }
    catch (NullPointerException npex)
    {
      System.err.println(npex);
    }
    catch (IllegalArgumentException iae)
    {
      instance.setLevel(Level.ALL);
      instance.severe("Could not parse level from properties file" + iae.toString());
    }
  }
}
