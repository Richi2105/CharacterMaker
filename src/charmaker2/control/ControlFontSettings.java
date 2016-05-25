/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker.View;

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
  
  public ControlFontSettings(JRadioButton buttons[], int size, JTextField fontName)
  {
    this.buttons = new ArrayList<>();
    buttons[0].setSelected(true);
    for (int i=0; i<buttons.length; i+=1)
    {
      this.buttons.add(buttons[i]);
      buttons[i].addActionListener(this);
    }
    this.fontName = fontName;
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
