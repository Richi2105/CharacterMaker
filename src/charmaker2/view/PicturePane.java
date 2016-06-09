/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.view;

import charmaker2.control.models.FontSettings;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 *
 * @author Richard
 */
public class PicturePane extends JPanel {
  
  private final BufferedImage picture;
  private FontSettings settings;
  
  public PicturePane(String pictureFile) throws IOException
  {
    this.picture = ImageIO.read(new File(pictureFile));    
  }
  
  private void transform(Graphics2D g2)
  {
    if (this.settings != null)
    {
      AffineTransform at = g2.getTransform();
      at.setToQuadrantRotation(-settings.rotation, picture.getWidth()/2.0, picture.getHeight()/2.0);
      at.scale(settings.mirrorHorizontal ? -1 : 1, settings.mirrorVertical ? -1 : 1);
      at.translate(settings.mirrorHorizontal ? -picture.getWidth() : 0, settings.mirrorVertical ? -picture.getHeight() : 0);
      AffineTransformOp op = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
      g2.drawImage(op.filter(picture, null), 0, 0, null);
    }
    else
    {
      g2.drawImage(picture, 0, 0, null);
    }
    this.setSize(this.picture.getWidth(), this.picture.getHeight());
  }
  
  @Override
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    this.transform((Graphics2D) g);
  }
  
  public void setPreview(FontSettings settings)
  {
    this.settings = settings;
  }
  
}
