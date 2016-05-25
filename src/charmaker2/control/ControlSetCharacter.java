/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker.View;

import charmaker.core.character.CharacterDescriptor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import charmaker.util.RSLogger;

/**
 *
 * @author Richard
 */
public class ControlSetCharacter implements ActionListener
{
  private FrameView view;
  private ControlCharacterList listController;
  private GridPane grid;
  
  public ControlSetCharacter(FrameView view, ControlCharacterList listController, GridPane grid)
  {
    this.view = view;
    this.listController = listController;
    this.grid = grid;
    view.getButtonSetCharacter().addActionListener(this);
    view.getButtonSetCharacter().setText("Set Character");
  }

  @Override
  public void actionPerformed(ActionEvent e)
  {
    CharacterDescriptor charDesc = listController.getSelectedCharacterDescriptor();
    RSLogger.getLogger().log(Level.INFO, "selected character: " + charDesc.getDescriptor());
    charDesc.setGrid(grid.getGrid());
  }
}
