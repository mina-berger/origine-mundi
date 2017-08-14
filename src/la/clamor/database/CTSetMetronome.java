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
public class CTSetMetronome extends TMenu {

    public CTSetMetronome() {
        super("set metronome");
    }

    @Override
    public void action(TInfo info) {
        double metronome = CTUtil.getMetronome(info);
        int id = CTUtil.getSongID(info);
        metronome = info.readDouble("input metronome", metronome, 30d, 240d);
        SQLTerminalUtil.update(info, "UPDATE SONG SET METRONOME = ? WHERE ID = ?", metronome, id);
        Record song = SQLTerminalUtil.selectRecord(info, SELECT_SONG + " WHERE ID = ?", id);
        CTUtil.setSong(info, song);
        info.info("metronome is set to " + metronome);

    }

    @Override
    public boolean option(TInfo info) {
        return !SQLTerminalUtil.getRecords(info, "SELECT ID FROM SONG").isEmpty();
    }

}
