/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package charmaker.util;

import java.sql.Date;
import java.sql.Time;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 *
 * @author Richard
 */
class RSFormatter extends SimpleFormatter
{
  public final static String separator = " | ";
  @Override
  public String format(LogRecord record)
  {
    Date date = new Date(record.getMillis());
    Time time = new Time(record.getMillis());    
    long milliseconds = record.getMillis() % 1000;
    
    String text;
    text = date.toString();
    text += separator + time.toString();
    text += separator + String.format("%4d", milliseconds);
    text += separator + record.getLevel().toString();
    text += separator + record.getSourceClassName();
    text += separator + record.getSourceMethodName();
    text += separator + record.getMessage();
    text += "\n";
    
    return text;
  }
}
