/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker2.control;

import charmaker2.core.character.CharacterDescriptor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import charmaker2.util.RSLogger;
import charmaker2.view.CharMakerWindow;
import charmaker2.view.GridPane;

/**
 *
 * @author Richard
 */
public class ControlSetCharacter implements ActionListener
{
  private CharMakerWindow view;
  private ControlCharacterSet listController;
  private GridPane grid;
  
  public ControlSetCharacter(CharMakerWindow view, ControlCharacterSet listController, GridPane grid)
  {
    this.view = view;
    this.listController = listController;
    this.grid = grid;
    view.getButtonSetChar().addActionListener(this);
    view.getButtonSetChar().setText("Set Character");
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    CharacterDescriptor charDesc = listController.getSelectedCharacterDescriptor();
    RSLogger.getLogger().log(Level.INFO, "selected character: " + charDesc.getDescriptor());
    charDesc.setGrid(grid.getGrid());
  }
}
