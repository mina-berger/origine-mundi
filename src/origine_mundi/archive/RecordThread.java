/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.archive;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import origine_mundi.OmException;
import static origine_mundi.OmUtil.FILETYPE;
import static la.clamor.Constantia.getAudioFormat;
 
/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class RecordThread extends Thread {
    private final File out_file;
    private TargetDataLine line;
    private AudioFormat format;
    private Long start_time;
    private Long stop_time;
    public RecordThread(File out_file){
        this(out_file, getAudioFormat());
    }
    public RecordThread(File out_file, AudioFormat format){
        this.out_file = out_file;
        this.format = format;
        start_time = null;
        stop_time = null;
        DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);

        // checks if system supports the data line
        if (!AudioSystem.isLineSupported(info)) {
            throw new OmException("Line not supported");
        }
        try {
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format);
        } catch (LineUnavailableException ex) {
            throw new OmException("Line unavailable:", ex);
        }
    }
    /**
     * Closes the target data line to finish capturing and recording
     */
    public void terminate() {
        stop_time = System.currentTimeMillis();
        line.stop();
        line.close();
    }
 

    @Override
    public void run() {
        try {
            line.open(format);
        } catch (LineUnavailableException ex) {
            throw new OmException("cannot open line", ex);
        }
        line.start();   // start capturing
        AudioInputStream ais = new AudioInputStream(line);
        start_time = System.currentTimeMillis();
        try {
            AudioSystem.write(ais, FILETYPE, out_file);
        } catch (IOException ex) {
            throw new OmException("cannot write file", ex);
        }
    }
    public Long getStartTime(){
        return start_time;
    }
    public Long getStopTime(){
        return stop_time;
    }
    
    public static void main(String[] args){
        String path = "doc/sample/sample01.wav";
        System.out.println(new File(path).exists());
        System.out.println(new File(path).getAbsolutePath());
        try {
            //Functiones.ludoLimam(new File(path));
            FileInputStream fis0 = new FileInputStream(new File(path));
            FileInputStream fis1 = new FileInputStream(new File(path));
            while(fis0.available() > 0){
                System.out.println(fis0.read() + ":" + fis1.read());
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(RecordThread.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RecordThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}