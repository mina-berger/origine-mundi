/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author hiyamamina
 */
public class TestMic {

    public static void main(String[] args) {
        AudioFormat format = new AudioFormat(48000, 16, 2, true, true);

        DataLine.Info targetInfo = new DataLine.Info(TargetDataLine.class, format);
        DataLine.Info sourceInfo = new DataLine.Info(SourceDataLine.class, format);

        try {
            TargetDataLine targetLine = (TargetDataLine) AudioSystem.getLine(targetInfo);
            targetLine.open(format);
            targetLine.start();

            SourceDataLine sourceLine = (SourceDataLine) AudioSystem.getLine(sourceInfo);
            sourceLine.open(format);
            sourceLine.start();

            int numBytesRead;
            int buffer_size = 4;
            //int buffer_size = targetLine.getBufferSize() /5;
            System.out.println(buffer_size);
            byte[] targetData = new byte[buffer_size];

            while (true) {
                numBytesRead = targetLine.read(targetData, 0, targetData.length);

                if (numBytesRead == -1) {
                    break;
                }

                sourceLine.write(targetData, 0, numBytesRead);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
