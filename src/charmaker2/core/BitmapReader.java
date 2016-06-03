/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.core;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import charmaker2.control.ControlCharacterSet;
import charmaker2.core.character.CharacterSet;
import charmaker2.util.RSLogger;

/**
 *
 * @author Richard
 */
public class BitmapReader
{
  private BitmapReader() {}
  
  public static void readBitmap(File filePath, ControlCharacterSet charaListController)
  {
    Document doc;
    ArrayList<BufferedImage> images = new ArrayList<>();
    try
    {
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      doc = db.parse(filePath);   
    }
    catch (Exception ex)
    {
      RSLogger.getLogger().log(Level.WARNING, "", ex);
      return;
    }
    
    NodeList nd = doc.getDocumentElement().getElementsByTagName("page");
    for (int i=0; i<nd.getLength(); i+=1)
    {
      Node n = nd.item(i);
      NamedNodeMap attributes = n.getAttributes();
      String imageName = attributes.getNamedItem("file").getNodeValue();
      File path = filePath.getParentFile();
      RSLogger.getLogger().log(Level.INFO, String.format("Opening image %s", path.getAbsolutePath() + File.separator + imageName));
      
      try
      {
        images.add(ImageIO.read(new File(path.getAbsolutePath()+File.separator+imageName)));
      }
      catch (IOException ex)
      {
        RSLogger.getLogger().log(Level.SEVERE, null, ex);
      }      
    }
    
    nd = doc.getDocumentElement().getElementsByTagName("info");
    NamedNodeMap info = nd.item(0).getAttributes();
    
    int size = Integer.decode(info.getNamedItem("size").getNodeValue());
    
    CharacterSet charSet = new CharacterSet(
            0,
            size,
            info.getNamedItem("face").getNodeValue());
    
    if (charaListController != null)
    {
      charaListController.setCurrentCharacterSet(charSet);
    }
    
    nd = doc.getDocumentElement().getElementsByTagName("char");
    for (int i=0; i<nd.getLength(); i+=1)
    {
      Node n = nd.item(i);
      NamedNodeMap attributes = n.getAttributes();
      int xStart = Integer.decode(attributes.getNamedItem("x").getNodeValue());
      int yStart = Integer.decode(attributes.getNamedItem("y").getNodeValue());
      int xOffset = Integer.decode(attributes.getNamedItem("xoffset").getNodeValue());
      int yOffset = Integer.decode(attributes.getNamedItem("yoffset").getNodeValue());
      int xEnd = Integer.decode(attributes.getNamedItem("width").getNodeValue());
      int yEnd = Integer.decode(attributes.getNamedItem("height").getNodeValue());
      int page = Integer.decode(attributes.getNamedItem("page").getNodeValue());
      char c = (char) ((Integer.decode(attributes.getNamedItem("id").getNodeValue())) & 0xFF);
      
//      RSLogger.getLogger().log(Level.INFO, String.format("character %s @ %d,%d,%d,%d", c, xStart,yStart,xEnd,yEnd));
      charSet.addCharacter(c, DataGrid.convert(images.get(page).getData(new Rectangle(xStart, yStart, xEnd, yEnd)), xOffset, yOffset, size, 0));
      
    }
  }
  
}
