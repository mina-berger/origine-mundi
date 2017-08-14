/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.archive;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import static la.clamor.Functiones.capioSourceDataLine;
import static la.clamor.Functiones.log;

/**
 *
 * @author hiyamamina
 */
public class PlayThread extends Thread {

    public static final int DEFAULT_EXTERNAL_BUFFER_SIZE = 128000;
    private AudioInputStream audio_input_stream;
    private SourceDataLine line;
    private int external_buffer_size;
    private int internal_buffer_size;
    private boolean stop;

    public PlayThread(File file) {
        external_buffer_size = DEFAULT_EXTERNAL_BUFFER_SIZE;
        internal_buffer_size = AudioSystem.NOT_SPECIFIED;
        try {
            audio_input_stream = AudioSystem.getAudioInputStream(file);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new IllegalArgumentException(e);
        }
        AudioFormat audio_format = audio_input_stream.getFormat();
        log.info("audio format:" + audio_format.toString());
        line = capioSourceDataLine(null, audio_format, internal_buffer_size);
        if (line == null) {
            throw new IllegalArgumentException(
                    "cannot get SourceDataLine for format:" + audio_format);
        }
        line.start();
        stop = false;
    }

    @Override
    public void run() {

        int bytes_read = 0;
        byte[] ab_data = new byte[external_buffer_size];
        while (!stop && bytes_read != -1) {
            try {
                bytes_read = audio_input_stream
                        .read(ab_data, 0, ab_data.length);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            if (bytes_read >= 0) {
                line.write(ab_data, 0, bytes_read);
            }
        }
        line.drain();
        line.close();
        //line = null;
    }
    public void stopPlay() {
        stop = true;
    }
}
