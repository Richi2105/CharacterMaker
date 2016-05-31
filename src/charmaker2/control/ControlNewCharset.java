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

/**
 *
 * @author richard
 */
public class ControlNewCharset implements ActionListener
{
    
    private CharMakerWindow view;
    private ControlCharacterSet list;
    
    public ControlNewCharset(CharMakerWindow view, ControlCharacterSet list)
    {
        this.view = view;
        this.list = list;
        view.getButtonNew().addActionListener(this);
        view.getButtonNew().setText("New Character Set");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        int x = (int) view.getSpinnerColumns().getValue();
        int y = (int) view.getSpinnerRows().getValue();
        String name = view.getTextFieldFontName().getText();
        CharacterSet set = new CharacterSet(x, y, name);
        
        for (char c = ' '; c <= '~'; c += 1)
        {
            set.addCharacter(c, new DataGrid(x, y));
        }
        list.setCurrentCharacterSet(set);
    }
    
    
    
}
