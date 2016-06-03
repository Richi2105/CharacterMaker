/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker2.view.CharMakerWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Richard
 */
public class ControlFontSettings implements ActionListener
{
  private ArrayList<JRadioButton> buttons;
  
  private JCheckBox checkBoxMirrorHorizontally;
  private final JCheckBox checkBoxMirrorVertically;
  
  private final JLabel labelFontName;
  private final JLabel labelDataType;
  
  private int activeButton;
  private JTextField fontName;
  
  public ControlFontSettings(CharMakerWindow view)
  {
    this.buttons = new ArrayList<>();
    
    this.buttons.add(view.getRadioButton0());
    this.buttons.add(view.getRadioButton90());
    this.buttons.add(view.getRadioButton180());
    this.buttons.add(view.getRadioButton270());
    
    this.checkBoxMirrorHorizontally = view.getCheckBoxMirrorY();
    this.checkBoxMirrorVertically = view.getCheckBoxMirrorX();
    
    this.labelFontName = view.getLabelFontName();
    this.labelDataType = view.getLabelDatatype();

    for (int i=0; i<4; i+=1)
    {
      buttons.get(i).addActionListener(this);
    }
    buttons.get(0).setSelected(true);
    this.fontName = view.getTextFieldFontName();
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
    
    this.fontName.setText("font");
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
    }
  }
}
