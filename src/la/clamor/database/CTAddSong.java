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
import com.mina.util.ExplainedObject;
import static la.clamor.database.CTUtil.SELECT_SONG;

/**
 *
 * @author hiyamamina
 */
public class CTAddSong extends TMenu {

    public CTAddSong() {
        super("add song");
    }

    @Override
    public void action(TInfo info) {
        Integer id = null;
        String name = null;
        int channel = 2;
        int sample_rate = 48000;
        int sample_size = 2;
        double metronome = 120;
        while (true) {
            while (true) {
                id = info.readInt("input id", 0, (Integer)null);
                if (!SQLTerminalUtil.getRecords(info, "SELECT ID FROM SONG WHERE ID = ?", id).isEmpty()) {
                    info.warn("id is already used");
                    continue;
                }
                break;
            }
            name = info.readString("input name", name);
            channel = CTUtil.readChannel(info, channel);
            sample_rate = CTUtil.readSampleRate(info, sample_rate);
            sample_size = CTUtil.readSampleSize(info, sample_size);
            metronome = info.readDouble("input metronome", metronome, 30d, 240d);

            ExplainedObject.print(info.getOut(),
                    new ExplainedObject("id", id),
                    new ExplainedObject("name", name),
                    new ExplainedObject("channel", channel),
                    new ExplainedObject("sample_rate(hz)", sample_rate),
                    new ExplainedObject("sample_size(byte)", sample_size),
                    new ExplainedObject("metronome(bpm)", metronome)
            );
            if (info.readBoolean("add song with this info?", true)) {
                SQLTerminalUtil.update(info,
                        "INSERT INTO SONG (ID, NAME, LABOR, CHANNEL, SAMPLE_RATE, SAMPLE_SIZE, METRONOME) VALUES (?, ?, FALSE, ?, ?, ?, ?)",
                        id, name, channel, sample_rate, sample_size, metronome);
                SQLTerminalUtil.update(info,
                        "CREATE TABLE SAMPLE_" + id + " ("
                        + "ID INTEGER,"
                        + "SEARCH VARCHAR(100),"
                        + "CREATE_TIME TIMESTAMP NOT NULL,"
                        + "UPDATE_TIME TIMESTAMP NOT NULL,"
                        + "PRIMARY KEY(ID))");
                SQLTerminalUtil.update(info,
                        "CREATE TABLE TRACK_" + id + " ("
                        + "ID INTEGER,"
                        + "EXPL VARCHAR(100),"
                        + "PRIMARY KEY(ID)"
                        + ")");
                SQLTerminalUtil.update(info,
                        "CREATE TABLE CONSILIUM_" + id + " ("
                        + "ID INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "TRACK INTEGER NOT NULL,"
                        + "TALEA INTEGER NOT NULL,"
                        + "REPENSO DOUBLE NOT NULL,"
                        + "VOLUME VARCHAR(256) NOT NULL,"
                        + "CREATE_TIME TIMESTAMP NOT NULL,"
                        + "UPDATE_TIME TIMESTAMP NOT NULL,"
                        + "FILEPATH VARCHAR(150),"
                        + "PRIMARY KEY(ID),"
                        + "FOREIGN KEY(TRACK) REFERENCES TRACK_" + id + " (ID)"
                        + "  ON DELETE RESTRICT"
                        + "  ON UPDATE RESTRICT"
                        + ")");
                Record song = SQLTerminalUtil.selectRecord(info, SELECT_SONG + " WHERE ID = ?", id);
                CTUtil.setLabor(info, id);
                CTUtil.setSong(info, song);
                //info.put("song", song);
                info.info("added");
                break;
            } else {
                info.info("canceled");
            }
        }
    }

    @Override
    public boolean option(TInfo info) {
        return true;
    }

}
