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
import static la.clamor.database.CTUtil.SELECT_SONG;

/**
 *
 * @author hiyamamina
 */
public class CTSelectSong extends TMenu {

    private final boolean use_default;

    public CTSelectSong(boolean use_default) {
        super("select song");
        this.use_default = use_default;
    }

    @Override
    public void action(TInfo info) {
        Record song = null;
        if (use_default) {
            song = SQLTerminalUtil.selectRecord(info, SELECT_SONG + " WHERE LABOR = TRUE");
        }
        if (song == null) {
            song = SQLTerminalUtil.selectRecord(info, SELECT_SONG + " ORDER BY ID");
        }
        if (song == null) {
            info.remove("song");
            info.info("no song");
            return;
            //throw new CoreException("no song");
        }
        CTUtil.setLabor(info, song.getValueInt(1));
        info.info("song selected");
        song.print(info.getOut());
        CTUtil.setSong(info, song);
        //info.put("song", song);
    }

    @Override
    public boolean option(TInfo info) {
        return SQLTerminalUtil.getRecords(info, "SELECT ID FROM SONG").isMulti();
    }

}
