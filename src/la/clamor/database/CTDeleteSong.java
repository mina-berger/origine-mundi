/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.database;

import com.mina.sql.Record;
import com.mina.sql_terminal.SQLTerminalUtil;
import com.mina.terminal.TInfo;
import com.mina.terminal.TMenu;

/**
 *
 * @author hiyamamina
 */
public class CTDeleteSong extends TMenu {

    public CTDeleteSong() {
        super("delete song");
    }

    @Override
    public void action(TInfo info) {
        Record song = CTUtil.printSong(info);
        if(song == null){
            return;
        }
        if (!info.readBoolean("are you sure to delete?", false)) {
            info.info("canceled");
            return;
        }
        if (!info.readBoolean("are you REALLY sure to delete?", false)) {
            info.info("canceled");
            return;
        }
        
        int id = song.getValueInt(1);
        SQLTerminalUtil.update(info, "DROP TABLE CONSILIUM_" + id);
        SQLTerminalUtil.update(info, "DROP TABLE TRACK_" + id);
        SQLTerminalUtil.update(info, "DROP TABLE SAMPLE_" + id);
        SQLTerminalUtil.update(info, "DELETE FROM SONG WHERE ID = ?", id);
        info.info("deleted");
        new CTSelectSong(false).action(info);
        
    }

    @Override
    public boolean option(TInfo info) {
        return !SQLTerminalUtil.getRecords(info, "SELECT ID FROM SONG").isEmpty();
    }

}
