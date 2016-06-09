/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control;

import charmaker2.control.models.SpinnerDecimalModel;
import charmaker2.core.DataGrid;
import charmaker2.core.character.CharacterDescriptor;
import charmaker2.view.CharMakerWindow;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;

/**
 *
 * @author Richard
 */
public class ControlPreview implements ActionListener {
  
  private final JPanel panelFontPreview;
  private final JTextArea textAreaPreviewText;
  private final JButton buttonAdd;
  private final JButton buttonClear;
  private final SpinnerDecimalModel spinnerPixelSize;
  private final JLabel labelPixelSize;
  
  private final int offset = 2;
  
  private int xPos, yPos;
  
  private final ControlCharacterSet charsetController;
  
  public ControlPreview(CharMakerWindow view, ControlCharacterSet charsetController)
  {
    this.charsetController = charsetController;
    this.panelFontPreview = view.getPanelPreviewFont();
    this.buttonAdd = view.getButtonAddText();
    this.buttonClear = view.getButtonClearText();
    this.spinnerPixelSize = new SpinnerDecimalModel(1, 10);
    view.getSpinnerPreviewPixelSize().setModel(spinnerPixelSize);
    this.textAreaPreviewText = view.getTextAreaPreviewInput();
    this.labelPixelSize = view.getLabelPreviewPixelSize();
    
    this.buttonAdd.addActionListener(this);
    this.buttonClear.addActionListener(this);
    
    this.panelFontPreview.setOpaque(true);
    this.panelFontPreview.setBackground(Color.WHITE);
    
    this.xPos = yPos = this.offset;
  }
  
  public void setLabels()
  {
    this.buttonAdd.setText("add");
    this.buttonClear.setText("clear");
    this.labelPixelSize.setText("Preview Pixel Size");
    this.textAreaPreviewText.setText("Preview Text");
  }
  
  public void addCharacter(DataGrid grid)
  {
    Graphics2D g2 = (Graphics2D)this.panelFontPreview.getGraphics();
    g2.setColor(Color.black);    
    int pixelSize = (int)this.spinnerPixelSize.getValue();

    Rectangle2D.Float pixel = new Rectangle2D.Float(xPos, yPos, pixelSize, pixelSize);

    for (int i=0; i<grid.getXSize(); i+=1)
    {
      for (int j=0; j<grid.getYSize(); j+=1)
      {
        if (grid.isSetAt(i, j))
        {
        //  pixel.setFrame(xPos+(i*pixelSize), yPos+(j*pixelSize), pixelSize, pixelSize);
          g2.fillRect(xPos+(i*pixelSize), yPos+(j*pixelSize), pixelSize, pixelSize);
        //  g2.draw(pixel);

        }
      }
    }
    this.xPos += (grid.getXSize() + 1) * pixelSize;
    
    g2.dispose();
  }
  
  public void addCharacter(char c)
  {
    CharacterDescriptor ch = this.charsetController.getCurrentCharacterSet().getCharacter(c);
    if (ch != null)
    {
      this.addCharacter(ch.getGrid());
    }
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    if (e.getSource() == this.buttonAdd)
    {
      String str = this.textAreaPreviewText.getText();
      for (char c : str.toCharArray())
      {
        if (c=='\n')
        {
          this.yPos += (this.charsetController.getCurrentCharacterSet().getCharacter(c).getGrid().getYSize() + 1) * (int)this.spinnerPixelSize.getValue();
          this.xPos = this.offset;
        }
        else
        {
          this.addCharacter(c);
        }
      }      
    }
    else
    {
      this.panelFontPreview.repaint();
      this.xPos = this.yPos = this.offset;
    }
  }
  
}
