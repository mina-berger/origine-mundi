/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.database;

import com.mina.derby.DerbyManager;
import com.mina.sql.Record;
import com.mina.sql_terminal.SQLTerminalUtil;
import com.mina.terminal.TInfo;
import com.mina.util.CoreException;
import com.mina.util.Integers;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Arrays;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import la.clamor.ExceptioClamoris;
import la.clamor.Punctum;
import la.clamor.Res;

/**
 *
 * @author hiyamamina
 */
public class CTUtil {

    public static String SELECT_SONG = "SELECT NAME, ID, CHANNEL, SAMPLE_RATE, SAMPLE_SIZE, METRONOME FROM SONG";

    public static void setLabor(TInfo info, int id) {
        SQLTerminalUtil.update(info, "UPDATE SONG SET LABOR = FALSE");
        SQLTerminalUtil.update(info, "UPDATE SONG SET LABOR = TRUE WHERE ID = ?", id);
    }

    public static void setSong(TInfo info, Record song) {
        info.put("song", song);
        Res.publica.ponoChannel(song.getValueInt(2));
        Res.publica.ponoSampleRate(song.getValueInt(3));
        Res.publica.ponoSampleSize(song.getValueInt(4));
    }

    public static Record printSong(TInfo info) {
        Record song = (Record) info.get("song");
        if (song == null) {
            info.info("no song");
            return null;
        }
        song.print(info.getOut());
        return song;
    }

    public static Connection getConnection() {
        System.setProperty("derby.system.home", "/Users/minaberger/drive/javadb/");
        DerbyManager.callConnection("origine_mundi", "MINA", "3737", false);
        return DerbyManager.getConnection("origine_mundi");
    }

    public static double getMetronome(TInfo info) {
        if (!info.containsKey("song")) {
            throw new CoreException("no song");
        }
        return ((Record) info.get("song")).getValueDouble(5);
    }
    public static int getSongID(TInfo info) {
        if (!info.containsKey("song")) {
            throw new CoreException("no song");
        }
        return ((Record) info.get("song")).getValueInt(1);
    }

    public static int readChannel(TInfo info, int default_value) {
        return info.readInt("input channel(1, 2 or 4)", default_value, Arrays.asList(1, 2, 4));
    }

    public static int readSampleRate(TInfo info, int default_value) {
        return info.readInt("input sample rate(8000, 48000, 96000)", default_value, Arrays.asList(8000, 48000, 96000));
    }

    public static int readSampleSize(TInfo info, int default_value) {
        return info.readInt("input sample size(2, 3)", default_value, Arrays.asList(2, 3));
    }

    public static void maximize(File file, boolean teneo_pan) {
        try {
            File temp = new File(file.getParentFile(), file.getName() + ".tmp");
            File back = new File(file.getParentFile(), "max_" + file.getName());
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
            AudioFormat af = aff.getFormat();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            int channel = af.getChannels();
            int sample_size = af.getSampleSizeInBits() / 8;
            FileSample sample = new FileSample(channel, sample_size);
            while (sample.read(ais)) {
            }
            ais.close();
            double peak = Math.pow(256, sample_size) / 2 - 1;
            Integers sample_max = sample.getMax();
            double[] rate = new double[channel];
            if (teneo_pan) {
                double sample_max_unit = sample_max.getMax();
                /*double sample_max_unit = 0;
                for (int i = 0; i < channel; i++) {
                    sample_max_unit = i == 0 ? sample_max.get(i) : Math.max(sample_max_unit, sample_max.get(i));
                }*/
                for (int i = 0; i < channel; i++) {
                    rate[i] = peak / sample_max_unit;
                    System.out.println(sample_max_unit + ":" + rate[i]);
                }
            } else {
                for (int i = 0; i < channel; i++) {
                    rate[i] = peak / (double) sample_max.get(i);
                    System.out.println(sample_max.get(i) + ":" + rate[i]);
                }
            }
            //Arrays.fill(rate, 1);

            temp.delete();
            back.delete();
            file.renameTo(back);

            ais = AudioSystem.getAudioInputStream(back);
            FileOutputStream fos = new FileOutputStream(temp);
            sample = new FileSample(channel, sample_size);
            int count = 0;
            while (sample.read(ais)) {
                fos.write(sample.getMultipliedByteArray(rate));
                count++;
            }
            ais.close();
            fos.flush();
            fos.close();
            AudioSystem.write(
                    new AudioInputStream(new FileInputStream(temp), af, count),
                    AudioFileFormat.Type.WAVE, file);
            temp.delete();
        } catch (UnsupportedAudioFileException | IOException ex) {
            throw new CoreException(ex);
        }
    }

    public static void trim(File file, double rate) {
        if (rate <= 0) {
            throw new ExceptioClamoris("trim rate must be positive:" + rate);
        }
        try {
            File temp = new File(file.getParentFile(), file.getName() + ".tmp");
            File back = new File(file.getParentFile(), "trim_" + file.getName());
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
            AudioFormat af = aff.getFormat();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            int channel = af.getChannels();
            int sample_size = af.getSampleSizeInBits() / 8;
            FileSample sample = new FileSample(channel, sample_size);
            Integer minimum
                    = sample_size == 2 ? 256 * 256
                            : sample_size == 3 ? 256 * 256 * 256 : null;
            if (minimum == null) {
                throw new CoreException("unexpected sample_size:" + sample_size);
            }
            minimum = (int) ((double) minimum * rate);

            long count = 0;
            Long ab_index = null;
            Long ad_index = null;
            Integers prev_value = null;
            while (sample.read(ais)) {
                Integers value = sample.getValue();
                if (prev_value == null) {
                    prev_value = value;
                    count++;
                    continue;
                }
                value.absAll();
                //here
                if (!value.isMinorToAll(minimum)) {
                    if (ab_index == null) {
                        ab_index = count;
                    }
                    ad_index = count;
                }
                count++;
            }
            ais.close();
            long length = count;
            count = 0;
            if (ab_index == null) {
                ab_index = 0l;
            }

            if (ad_index == null) {
                ad_index = length;
            }
            System.out.println("length:" + length);
            System.out.println("index:" + ab_index + "->" + ad_index);
            temp.delete();
            back.delete();
            file.renameTo(back);

            ais = AudioSystem.getAudioInputStream(back);
            FileOutputStream fos = new FileOutputStream(temp);
            sample = new FileSample(channel, sample_size);

            long additional = 0;
            if (ab_index > 0) {
                fos.write(sample.getAllZeroByteArray());
                additional++;
            }
            while (sample.read(ais)) {
                if (count >= ab_index && count <= ad_index) {
                    fos.write(sample.getByteArray());
                    additional++;
                }
                count++;
            }
            if (ad_index < length) {
                fos.write(sample.getAllZeroByteArray());
                additional++;
            }
            ais.close();
            fos.flush();
            fos.close();
            AudioSystem.write(
                    new AudioInputStream(new FileInputStream(temp), af, additional),
                    AudioFileFormat.Type.WAVE, file);
            temp.delete();
        } catch (UnsupportedAudioFileException | IOException ex) {
            throw new CoreException(ex);
        }
    }

    /*public static void maximize(File file) {
        try {
            File temp = new File(file.getParentFile(), file.getName() + ".tmp");
            File back = new File(file.getParentFile(), "bk_" + file.getName());
            AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
            AudioFormat af = aff.getFormat();
            AudioInputStream ais = AudioSystem.getAudioInputStream(file);
            byte[] buf = new byte[4];
            int l_max = 0;
            int r_max = 0;
            while (ais.read(buf) > 0) {
                int i0 = ((int) buf[0]);
                int i1 = ((int) buf[1]);
                int i2 = ((int) buf[2]);
                int i3 = ((int) buf[3]);
                i0 = i0 < 0 ? i0 + 256 : i0;
                i2 = i2 < 0 ? i2 + 256 : i2;
                int l = i0 + i1 * 256;
                int r = i2 + i3 * 256;
                l_max = Math.max(l_max, Math.abs(l));
                r_max = Math.max(r_max, Math.abs(r));
            }
            ais.close();
            System.out.println("l=" + l_max + ":r=" + r_max);
            System.out.println((127 * 256 + 255) + ":" + (-128 * 256 - 0));
            double rate = 32767d / (double) Math.max(l_max, r_max);
            
            temp.delete();
            back.delete();
            file.renameTo(back);
            
            ais = AudioSystem.getAudioInputStream(back);
            FileOutputStream fos = new FileOutputStream(temp);
            
            int count = 0;
            while (ais.read(buf) > 0) {
                int i0 = ((int) buf[0]);
                int i1 = ((int) buf[1]);
                int i2 = ((int) buf[2]);
                int i3 = ((int) buf[3]);
                //System.out.println(i0 + " " + i1 + " " + i2 + " " + i3);
                i0 = i0 < 0 ? i0 + 256 : i0;
                //i1 = i1 < 0?i1 + 256:i1;
                i2 = i2 < 0 ? i2 + 256 : i2;
                //i3 = i3 < 0?i3 + 256:i3;
                int l = (int) ((double) (i0 + i1 * 256) * rate);
                int r = (int) ((double) (i2 + i3 * 256) * rate);
                l = l < 0 ? l + 256 * 256 : l;
                r = r < 0 ? r + 256 * 256 : r;
                i0 = l % 256;
                i1 = l / 256;
                i2 = r % 256;
                i3 = r / 256;
                buf[0] = (byte)(i0 >= 128?i0 - 256:i0);
                buf[1] = (byte)(i1 >= 128?i1 - 256:i1);
                buf[2] = (byte)(i2 >= 128?i2 - 256:i2);
                buf[3] = (byte)(i3 >= 128?i3 - 256:i3);
                
                fos.write(buf);
                
                //System.out.println(i0 + " " + i1 + " " + i2 + " " + i3);
                //System.out.println();
                count++;
                
            }
            ais.close();
            fos.flush();
            fos.close();
            AudioSystem.write(
                    new AudioInputStream(new FileInputStream(temp), af, count),
                    AudioFileFormat.Type.WAVE, file);
            temp.delete();
        } catch (UnsupportedAudioFileException | IOException ex) {
            throw new CoreException(ex);
        }
    }*/
    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        String path = "/Users/minaberger/drive/doc/origine_mundi/song/0/sample/1.wav";
        File file = new File(path);
        File temp = new File(path + ".tmp");
        File max = new File(path + ".max.wav");
        AudioFileFormat aff = AudioSystem.getAudioFileFormat(file);
        System.out.println(aff.toString());
        System.out.println(aff.getFrameLength());
        System.out.println(aff.getByteLength());
        AudioFormat af = aff.getFormat();
        System.out.println(af.getChannels());
        System.out.println(af.getEncoding().toString());
        System.out.println(af.getSampleRate());
        System.out.println(af.getFrameRate());
        int sampleSizeInBit = af.getSampleSizeInBits();
        System.out.println(sampleSizeInBit / 8);
        AudioInputStream ais = AudioSystem.getAudioInputStream(file);
        byte[] buf = new byte[4];
        int l_max = 0;
        int r_max = 0;
        while (ais.read(buf) > 0) {
            int i0 = ((int) buf[0]);
            int i1 = ((int) buf[1]);
            int i2 = ((int) buf[2]);
            int i3 = ((int) buf[3]);
            i0 = i0 < 0 ? i0 + 256 : i0;
            //i1 = i1 < 0?i1 + 256:i1;
            i2 = i2 < 0 ? i2 + 256 : i2;
            //i3 = i3 < 0?i3 + 256:i3;
            int l = i0 + i1 * 256;
            int r = i2 + i3 * 256;
            l_max = Math.max(l_max, Math.abs(l));
            r_max = Math.max(r_max, Math.abs(r));
            //System.out.println(l + " " + r);
        }
        ais.close();
        System.out.println("l=" + l_max + ":r=" + r_max);
        System.out.println((127 * 256 + 255) + ":" + (-128 * 256 - 0));
        double rate = 32767d / (double) Math.max(l_max, r_max);

        ais = AudioSystem.getAudioInputStream(file);
        FileOutputStream fos = new FileOutputStream(temp);

        int count = 0;
        while (ais.read(buf) > 0) {
            int i0 = ((int) buf[0]);
            int i1 = ((int) buf[1]);
            int i2 = ((int) buf[2]);
            int i3 = ((int) buf[3]);
            //System.out.println(i0 + " " + i1 + " " + i2 + " " + i3);
            i0 = i0 < 0 ? i0 + 256 : i0;
            //i1 = i1 < 0?i1 + 256:i1;
            i2 = i2 < 0 ? i2 + 256 : i2;
            //i3 = i3 < 0?i3 + 256:i3;
            int l = (int) ((double) (i0 + i1 * 256) * rate);
            int r = (int) ((double) (i2 + i3 * 256) * rate);
            l = l < 0 ? l + 256 * 256 : l;
            r = r < 0 ? r + 256 * 256 : r;
            i0 = l % 256;
            i1 = l / 256;
            i2 = r % 256;
            i3 = r / 256;
            buf[0] = (byte) (i0 >= 128 ? i0 - 256 : i0);
            buf[1] = (byte) (i1 >= 128 ? i1 - 256 : i1);
            buf[2] = (byte) (i2 >= 128 ? i2 - 256 : i2);
            buf[3] = (byte) (i3 >= 128 ? i3 - 256 : i3);

            fos.write(buf);

            //System.out.println(i0 + " " + i1 + " " + i2 + " " + i3);
            //System.out.println();
            count++;

        }
        ais.close();
        fos.flush();
        fos.close();
        AudioSystem.write(
                new AudioInputStream(new FileInputStream(temp), af, count),
                AudioFileFormat.Type.WAVE, max);

        /*double d = 1. / 3000000000000000000.;
        System.out.println(d);
        System.out.println(Double.toHexString(d));
        System.out.println(Double.parseDouble(Double.toHexString(d)));
        System.out.println(Double.toHexString(Double.parseDouble(Double.toHexString(d))));
        System.out.println(Double.toString(d).getBytes().length);
         */
 /*ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/Users/hiyamamina/drive/test"));
        oos.writeDouble(1.);
        oos.writeDouble(1. / 3.);
        oos.writeDouble(1.);
        oos.flush();
        oos.close();
         */
 /*ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/Users/hiyamamina/drive/test"));
        System.out.println(ois.readDouble());
        System.out.println(ois.readDouble());
        ois.close();*/
 /*InputStream is = new FileInputStream("/Users/hiyamamina/drive/test");
        while(is.available() > 0){
            System.out.println(is.read());
        }*/

 /*Punctum punctum = new Punctum(1. / 30000., 1. / 78889998989898.);

        Connection conn = getConnection();
        TInfo info = new TInfo(new BufferedReader(new InputStreamReader(System.in)), System.out, null);
        info.put("conn", conn);
        SQLTerminalUtil.update(info, "INSERT INTO TEST (ID, PUNCTUM) VALUES (0, ?)", Punctum.serialize(punctum));
        SQLTerminalUtil.getRecords(info, "SELECT ID, PUNCTUM FROM TEST").print(false, info.getOut());
         */
        //for(byte b:toByte(new Double(1. / 3.))){
        //    System.out.println(b);
        //}
        //System.out.println(toByte(new Punctum(1. / 3., 1. / 5, 1. / 3., 1. / 5)).length);
        //System.out.println(toByte(new Punctum(1. / 3., 1. / 5, 1. / 3., 1. / 5)).length);
        //Punctum punctum = new Punctum(1. / 30000., 1. / 78889998989898.);
        //System.out.println(Punctum.serialize(punctum));
        //System.out.println(Punctum.serialize(Punctum.deserialize(Punctum.serialize(punctum))));
        //System.out.println(Punctum.serialize(punctum).length());
    }
    /*public static byte[] toByte(Object o) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        //oos.writeDouble((Double)o);
        oos.writeObject(o);
        oos.flush();
        oos.close();
        return baos.toByteArray();
    }*/
}
