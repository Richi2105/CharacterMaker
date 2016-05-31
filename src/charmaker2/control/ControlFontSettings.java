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
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 *
 * @author Richard
 */
public class ControlFontSettings implements ActionListener
{
  private ArrayList<JRadioButton> buttons;
  private int activeButton;
  private JTextField fontName;
  
  public ControlFontSettings(CharMakerWindow view)
  {
    this.buttons = new ArrayList<>();
    
    this.buttons.add(view.getRadioButton0());
    this.buttons.add(view.getRadioButton90());
    this.buttons.add(view.getRadioButton180());
    this.buttons.add(view.getRadioButton270());

    for (int i=0; i<4; i+=1)
    {
      buttons.get(i).addActionListener(this);
    }
    buttons.get(0).setSelected(true);
    this.fontName = view.getTextFieldFontName();
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
