/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.sampler;
import javax.sound.sampled.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import la.clamor.Functiones;
import origine_mundi.OmException;
import static origine_mundi.OmUtil.FILETYPE;
import static origine_mundi.OmUtil.getAudioFormat;
 
/**
 * A sample program is to demonstrate how to record sound in Java
 * author: www.codejava.net
 */
public class RecordThread extends Thread {
    private final File out_file;
    private TargetDataLine line;
    private AudioFormat format;
    public RecordThread(File out_file){
        this.out_file = out_file;
        format = getAudioFormat();
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
        try {
            AudioSystem.write(ais, FILETYPE, out_file);
        } catch (IOException ex) {
            throw new OmException("cannot write file", ex);
        }
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