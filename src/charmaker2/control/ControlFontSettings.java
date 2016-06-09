/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker2.control.models.FontSettings;
import charmaker2.util.RSLogger;
import charmaker2.view.CharMakerWindow;
import charmaker2.view.PicturePane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import javax.imageio.ImageIO;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Richard
 */
public class ControlFontSettings implements ActionListener
{
  private final ArrayList<JRadioButton> buttons;
  
  private final JPanel panelRotationPreview;
  private BufferedImage previewImage;
  private PicturePane rotationPreview;
  
  private final JCheckBox checkBoxMirrorHorizontally;
  private final JCheckBox checkBoxMirrorVertically;
  private final JCheckBox checkBoxAlignTop;
  
  private final JComboBox comboBoxDataTypes;
  private final ComboBoxDataTypes dataTypes;
  
  private final JLabel labelFontName;
  private final JLabel labelDataType;
  
  private int activeButton;
  private final JTextField fontName;
  
  private FontSettings fontSettings;
  
  public ControlFontSettings(CharMakerWindow view)
  {
    this.buttons = new ArrayList<>();
    
    this.buttons.add(view.getRadioButton0());
    this.buttons.add(view.getRadioButton90());
    this.buttons.add(view.getRadioButton180());
    this.buttons.add(view.getRadioButton270());
    
    this.panelRotationPreview = view.getPanelRotationPreview();
    try {
      this.rotationPreview = new PicturePane("./src/charmaker2/media/preview_FontSettings.png");
    } catch (IOException ex) {
      RSLogger.getLogger().log(Level.SEVERE, null, ex);
    }
    
    this.panelRotationPreview.add(this.rotationPreview);
    
    this.checkBoxMirrorHorizontally = view.getCheckBoxMirrorY();
    this.checkBoxMirrorVertically = view.getCheckBoxMirrorX();
    this.checkBoxAlignTop = view.getCheckBoxAlignAtTop();
    
    this.labelFontName = view.getLabelFontName();
    this.labelDataType = view.getLabelDatatype();
    
    this.comboBoxDataTypes = view.getComboBoxDatatype();
    this.dataTypes = new ComboBoxDataTypes();
    this.comboBoxDataTypes.setModel(dataTypes);
    dataTypes.updateList();

    for (int i=0; i<4; i+=1)
    {
      buttons.get(i).addActionListener(this);
    }
    buttons.get(0).setSelected(true);
    this.fontName = view.getTextFieldFontName();
    
    this.fontSettings = new FontSettings();
  }
  
  public void setLabels()
  {
    this.labelDataType.setText("Data Type");
    this.labelFontName.setText("Font Name");
    
    buttons.get(0).setText("Rotate 0°");
    buttons.get(1).setText("Rotate 90°");
    buttons.get(2).setText("Rotate 180°");
    buttons.get(3).setText("Rotate 270°");
    
    this.checkBoxMirrorHorizontally.setText("Mirror Horizontally");
    this.checkBoxMirrorVertically.setText(("Mirror Vertically"));
    this.checkBoxAlignTop.setText("Align at Top");
    
    this.fontName.setText("font");
  }
  
  public FontSettings getFontSettings()
  {
    fontSettings.bits = this.dataTypes.getSelectedBitDepth();
    fontSettings.dataType = this.dataTypes.getSelectedDatatypeName();
    fontSettings.fontName = this.fontName.getText();
    fontSettings.mirrorHorizontal = this.checkBoxMirrorHorizontally.isSelected();
    fontSettings.mirrorVertical = this.checkBoxMirrorVertically.isSelected();
    fontSettings.alignAtTop = this.checkBoxAlignTop.isSelected();
    fontSettings.rotation = this.getActiveButtonIndex();
    return this.fontSettings;
  }
  
  public String getFontName()
  {
    return this.fontName.getText();
  }
  
  public JRadioButton getActiveButton()
  {
    return buttons.get(activeButton);    
  }
  
  public int getActiveButtonIndex()
  {
    return activeButton;
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
//    if (buttons.contains((JRadioButton)e.getSource()))
    {
      activeButton = buttons.indexOf(e.getSource());
      buttons.stream().forEach((b) ->
      {
        b.setSelected(false);
      });
      buttons.get(activeButton).setSelected(true);
      
      this.rotationPreview.setPreview(this.getFontSettings());
      this.rotationPreview.repaint();
    }
  }
}
