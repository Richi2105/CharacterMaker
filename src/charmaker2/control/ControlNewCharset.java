/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package charmaker2.control;

import charmaker2.core.DataGrid;
import charmaker2.core.character.CharacterSet;
import charmaker2.view.CharMakerWindow;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;

/**
 *
 * @author richard
 */
public class ControlNewCharset implements ActionListener, Observer
{
    
    private CharMakerWindow view;
    private ControlCharacterSet list;
    
    private final JButton buttonNew;
    
    private final ControlGrid gridController;
    
    public ControlNewCharset(CharMakerWindow view, ControlCharacterSet list, ControlGrid gridController)
    {
        this.view = view;
        this.list = list;
        this.buttonNew = view.getButtonNew();
        view.getButtonNew().addActionListener(this);
        this.gridController = gridController;
    }
    
    public void setLabels()
    {
      this.buttonNew.setText("New Character Set");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
      this.gridController.addObserver(this);
      this.gridController.showDialog(this.buttonNew.getLocationOnScreen());

    }    

  @Override
  public void update(Observable o, Object arg) {
    if (this.gridController.getDialogReturn() == this.gridController.DIALOG_OK)
    {
      int x = this.gridController.getXDimension();
      int y = this.gridController.getYDimension();
      String name = view.getTextFieldFontName().getText();
      CharacterSet set = new CharacterSet(x, y, name);

      for (char c = ' '; c <= '~'; c += 1)
      {
          set.addCharacter(c, new DataGrid(x, y));
      }
      list.setCurrentCharacterSet(set);
    }
    this.gridController.deleteObserver(this);
  }
}
