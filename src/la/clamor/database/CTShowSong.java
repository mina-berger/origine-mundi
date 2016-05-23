/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.database;

import com.mina.sql_terminal.SQLTerminalUtil;
import com.mina.terminal.TInfo;
import com.mina.terminal.TMenu;

/**
 *
 * @author hiyamamina
 */
public class CTShowSong extends TMenu {

    public CTShowSong() {
        super("song info");
    }

    @Override
    public void action(TInfo info) {
        CTUtil.printSong(info);
        //((Record)info.get("song")).print(info.getOut());
    }

    @Override
    public boolean option(TInfo info) {
        return !SQLTerminalUtil.getRecords(info, "SELECT ID FROM SONG").isEmpty();
    }

}
