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
import com.mina.util.CoreException;
import com.mina.util.Metronome;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import static la.clamor.Constantia.getAudioFormat;
import la.clamor.Res;
import la.clamor.io.IOUtil;
import origine_mundi.archive.PlayThread;
import origine_mundi.archive.RecordThread;

/**
 *
 * @author hiyamamina
 */
public class CTRecordSample extends TMenu {

    public CTRecordSample() {
        super("record sample");
    }

    @Override
    public void action(TInfo info) {
        int song_id = CTUtil.getSongID(info);
        Record max = SQLTerminalUtil.selectRecord(info, "SELECT MAX(ID) FROM SAMPLE_" + song_id);
        int id = max.getValueInt(0) + 1;
        info.info("sample_id=" + id);
        File dir = IOUtil.getDirectory("song/" + song_id + "/sample");
        dir.mkdirs();
        File file = new File(dir, id + ".wav");
        System.out.println(file.getAbsolutePath());

        //play(info, file);
        int channel = CTUtil.readChannel(info, 2);
        AudioFormat format = getAudioFormat(Res.publica.sampleRate(), Res.publica.sampleSize(), channel);
        while (true) {
            record(info, file, format, (float)CTUtil.getMetronome(info));
            CTUtil.maximize(file, true);
            CTUtil.trim(file, 0.01);
            boolean primo = true;
            while(info.readBoolean("play?", primo)) {
                play(info, file);
                primo = false;
            }
            if (info.readBoolean("fix it?", true)) {
                break;
            }
        }
        String search = info.readString("input search word", true);
        SQLTerminalUtil.update(info, "INSERT INTO SAMPLE_" + song_id + " "
                + " (ID, SEARCH, CREATE_TIME, UPDATE_TIME)"
                + "VALUES (?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP)", id, search);
        info.info("recorded " + search);
    }

    public void play(TInfo info, File file) {
        PlayThread play_thread = new PlayThread(file);
        play_thread.start();
        //Thread.sleep(500);
        System.out.println("Start playing...");
        info.prompt("press to stop");
        //play_thread.interrupt();
        play_thread.stopPlay();
        System.out.println("terminated");
    }

    public void record(TInfo info, File file, AudioFormat format, Float metronome_bpm) {
        RecordThread record_thread = new RecordThread(file, format);
        Metronome metronome = null;
        if (metronome_bpm != null) {
            metronome = new Metronome();
            metronome.start(metronome_bpm);
        }
        info.prompt("press to record");
        record_thread.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException ex) {
            throw new CoreException(ex);
        }
        System.out.println("Start recording...");
        info.prompt("press to stop");
        record_thread.terminate();
        if(metronome!= null){
            metronome.stop();
        }
        info.info("terminated");
    }

    @Override
    public boolean option(TInfo info) {
        return info.containsKey("song");
    }

}
