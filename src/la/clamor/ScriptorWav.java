package la.clamor;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import static la.clamor.Constantia.BYTE_PER_EXAMPLUM;
import static la.clamor.Constantia.CHANNEL;
import static la.clamor.Constantia.REGULA_EXAMPLI;
import la.clamor.Punctum.Aestimatio;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ScriptorWav implements Constantia {
    public static Log log = LogFactory.getLog(ScriptorWav.class);
    private final File file;
    private Long index_ab;
    private Long index_ad;

    public ScriptorWav(File file) {
        this.file = file;
        index_ab = null;
        index_ad = null;
    }
    public void ponoIndexAb(double tempus_ab){
        System.out.println("tempus_ab=" + tempus_ab);
        this.index_ab = Functiones.adPositio(tempus_ab);
    }
    public void ponoIndexAd(double tempus_ad){
        System.out.println("tempus_ad=" + tempus_ad);
        this.index_ad = Functiones.adPositio(tempus_ad);
    }

    public File getFile() {
        return file;
    }

    public void scribo(Legibilis legibilis) {
        ObjectOutputStream o_out;
        //FileOutputStream   f_out;
        ObjectInputStream  o_in;
        Aestimatio ratio;
        int longitudo = 0;
        File tmp_file;
        try {
            tmp_file = File.createTempFile("s_lima", Long.toString(System.currentTimeMillis()));
            //tmp_file.deleteOnExit();
            o_out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(tmp_file)));

            long locus = Functiones.adPositio(LOCUS_TERMINATO);
            Aestimatio max = new Aestimatio();
            Aestimatio min = new Aestimatio();
            log.info("mixdown start:" + file.getAbsolutePath());
            for (long i = 0; i < locus * CHANNEL;i++) {
                o_out.writeObject(new Aestimatio(0));
                longitudo++;
                if(longitudo % (REGULA_EXAMPLI * CHANNEL) == 0){
                    log.info("lecti   : " + (longitudo / REGULA_EXAMPLI / CHANNEL) + " sec.(locus)");
                }
            }
            long index = 0;
            //log.info("ante lego:index_ab=" + index_ab + ":index_ad=" + index_ad);
            while (legibilis.paratusSum()) {
                Punctum punctum = legibilis.lego();
                if((index_ab != null && index_ab > index)){
                    index++;
                    continue;
                }else if(index_ad != null && index_ad < index){
                    break;
                }
                index++;
                for (int i = 0;i < CHANNEL;i++) {
                    Aestimatio aestimatio = punctum.capioAestimatio(i);
                    o_out.writeObject(aestimatio);
                    max = max.max(aestimatio);
                    min = min.min(aestimatio);
                    longitudo++;
                    if(longitudo % (REGULA_EXAMPLI * CHANNEL) == 0){
                        log.info("lecti   : " + (longitudo / REGULA_EXAMPLI / CHANNEL) + " sec.");
                        //log.info("index_ab=" + index_ab + ":index_ad=" + index_ad + ":index" + index);
                    }
                }
                if(index % 1000 == 0){
                    o_out.reset();
                }

            }
            for (long i = 0; i < locus * CHANNEL;i++) {
                o_out.writeObject(new Aestimatio(0));
                longitudo++;
                if(longitudo % (REGULA_EXAMPLI * CHANNEL) == 0){
                    log.info("lecti   : " + (longitudo / REGULA_EXAMPLI / CHANNEL) + " sec.(locus)");
                }
            }
            o_out.flush();
            o_out.close();
            log.info("longitudo=" + longitudo);
            log.info("maximum  =" + max);
            log.info("minimum  =" + min);
            ratio = 
                    REGULA_MAGISTRI.multiplico(MAX_AMPLITUDO).partior(max).abs().min(
                    REGULA_MAGISTRI.multiplico(MIN_AMPLITUDO).partior(min).abs());
            longitudo += locus * 2;
            log.info("ratio   =" + ratio);
            log.info("tmp_file=" + tmp_file.getAbsolutePath());
            o_in = new ObjectInputStream(new FileInputStream(tmp_file));
        } catch (IOException ex) {
            Logger.getLogger(ScriptorWav.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalArgumentException(ex);
        }
            
        try{
            scriboSub(o_in, file, ratio, longitudo);
        } catch (IOException ex) {
            Logger.getLogger(ScriptorWav.class.getName()).log(Level.WARNING, file.getAbsolutePath(), ex);
            try{
                File new_file = new File(file.getParentFile(), System.currentTimeMillis() + "_" + file.getName());
            Logger.getLogger(ScriptorWav.class.getName()).log(Level.WARNING, "scribo {0}", new_file.getAbsolutePath());
                scriboSub(o_in, new_file, ratio, longitudo);
            } catch (IOException ex2) {
                Logger.getLogger(ScriptorWav.class.getName()).log(Level.SEVERE, null, ex2);
                throw new IllegalArgumentException(ex2);
            }
        }finally{
            tmp_file.delete();
        }
    }
    private void scriboSub(ObjectInputStream o_in, File file, Aestimatio ratio, int longitudo) throws IOException{
        AudioFormat af = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED, 
                (float)REGULA_EXAMPLI, 
                8 * BYTE_PER_EXAMPLUM, 
                CHANNEL,
                CHANNEL * BYTE_PER_EXAMPLUM, 
                (float)REGULA_EXAMPLI, 
                true);
        file.getParentFile().mkdirs();
        AudioSystem.write(
                new AudioInputStream(new LegibilisInputStream(o_in, ratio), af, (longitudo) / CHANNEL), 
                AudioFileFormat.Type.WAVE, file);
    }
    /*private void scriboSub(ObjectInputStream o_in, File file, Aestimatio ratio, int longitudo, int locus) throws FileNotFoundException, IOException{
        try (FileOutputStream f_out = new FileOutputStream(file)) {
            f_out.write(new FilumOctorum(Lima.RIFF).capioBytes());
            f_out.write(new FilumOctorum(longitudo + 8, 4).capioBytes());
            f_out.write(new FilumOctorum(Lima.WAVE).capioBytes());

            //int channel = 1;
            FilumOctorum octets = new FilumOctorum();
            octets.addo(new FilumOctorum(1, 2));// formatID PCM
            octets.addo(new FilumOctorum(CHANNEL, 2));// channel
            octets.addo(new FilumOctorum(REGULA_EXAMPLI, 4));// sampling rate
            octets.addo(new FilumOctorum(REGULA_EXAMPLI * CHANNEL * BYTE_PER_EXAMPLUM, 4));// byte
            // per
            // second
            octets.addo(new FilumOctorum(CHANNEL * BYTE_PER_EXAMPLUM, 2));// block size
            octets.addo(new FilumOctorum(8 * BYTE_PER_EXAMPLUM, 2));// bit
            RiffData data = new RiffData("fmt ", octets);
            f_out.write(new FilumOctorum(data.getTag()).capioBytes());
            f_out.write(new FilumOctorum(data.longitudoDatorum(), 4).capioBytes());
            FilumOctorum data_data = data.capioData();
            f_out.write(data_data.capioBytes());

            f_out.write(new FilumOctorum(Lima.DATA_TAG).capioBytes());
            f_out.write(new FilumOctorum(longitudo * BYTE_PER_EXAMPLUM, 4).capioBytes());

            for (int i = 0; i < locus; i++) {
                f_out.write(new FilumOctorum(convertoBytes(0, BYTE_PER_EXAMPLUM)).capioBytes());
            }
            int written = 0;
            //FileWriter data_csv = new FileWriter(new File(file.getParentFile(), file.getName() + ".csv"));
            while (true) {
                try{
                    if(written % (REGULA_EXAMPLI * CHANNEL)  == 0){
                        log.info("written : " + (written / REGULA_EXAMPLI / CHANNEL) + " sec.");
                    }
                    Aestimatio value = (Aestimatio)o_in.readObject();
                    f_out.write(new FilumOctorum(convertoBytes(value.multiplico(ratio).intValue(), BYTE_PER_EXAMPLUM)).capioBytes());
                    //Aestimatio value = new Aestimatio(o_in.readDouble(), true);
                    //f_out.write(new FilumOctorum(convertoBytes(value.multiplico(ratio).intValue(), BYTE_PER_EXAMPLUM)).capioBytes());

                    //if(written % CHANNEL == 0){
                    //    data_csv.append(Double.toString(value) + "\n");
                    //}
                    written++;
                }catch(EOFException | ClassNotFoundException ex) {
                    //Logger.getLogger(ScriptorLimam.class.getName()).log(Level.SEVERE, null, ex);
                    break;
                }
            }
            log.info("written=" + written);

            //data_csv.flush();
            //data_csv.close();
            o_in.close();
            f_out.flush();
        }
    }*/

    /*FilumOctorum logo(InputStream in, int length) {
        byte[] bytes = new byte[length];
        try {
            if (in.read(bytes) != length) {
                return null;
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return new FilumOctorum(bytes);
    }*/

    /*private static byte[] convertoBytes(int aestimatio, int longitudo) {
        if (longitudo < 1 && longitudo > 3) {
            throw new IllegalArgumentException("so far length(" + longitudo + ") must be 1, 2, or 3.");
        }
        boolean positive = aestimatio >= 0;

        byte[] bytes = new byte[longitudo];
        if (longitudo == 1) {
            int byte_value_1 = (aestimatio + 128) % 256;
            bytes[0] = new Integer(byte_value_1).byteValue();
        } else if (longitudo == 2) {
            int byte_value_1 = aestimatio % 256;
            int byte_value_2 = (aestimatio / 256) % 256;
            if (byte_value_2 <= 0 && !positive) {
                byte_value_2 -= 1;
            }
            bytes[0] = new Integer(byte_value_1).byteValue();
            bytes[1] = new Integer(byte_value_2).byteValue();
        } else {
            int byte_value_1 = aestimatio % 256;
            int byte_value_2 = (aestimatio / 256) % 256;
            int byte_value_3 = (aestimatio / 256 / 256) % 256;
            if (byte_value_3 <= 0 && byte_value_2 < 0 && !positive) {
                byte_value_3 -= 1;
            }
            bytes[0] = new Integer(byte_value_1).byteValue();
            bytes[1] = new Integer(byte_value_2).byteValue();
            bytes[2] = new Integer(byte_value_3).byteValue();
        }
        return bytes;
    }*/
    /*public static void main(String[] args){
        for(int i = MIN_AMPLITUDO.intValue();i < MAX_AMPLITUDO.intValue();i++){
            byte[] data = convertoBytes(i, 2);
            System.out.println(i + ":" + Integer.toHexString(data[0] & 0xff) + ":" + Integer.toHexString(data[1] & 0xff));
        }
        
    }*/
    public static void main(String[] args){
        LectorWav ll = new LectorWav(new File("doc/sample/sample01.wav"), new Aestimatio(1));
        ScriptorWav sl = new ScriptorWav(new File("doc/sample/sample02.wav"));
        /*ZTransformare zt = new ZTransformare(new ModAddens(
                new ModCapiens(1), 
                new ModCapiens(1, Fons.IN, 12000)/*, 
                new ModCapiens(1, Fons.IN, 2), 
                new ModCapiens(1, Fons.IN, 3), 
                new ModCapiens(1, Fons.IN, 4), 
                new ModCapiens(1, Fons.IN, 5), 
                new ModCapiens(1, Fons.IN, 6), 
                new ModCapiens(1, Fons.IN, 7), 
                new ModCapiens(1, Fons.IN, 8), 
                new ModCapiens(1, Fons.IN, 9), 
                new ModCapiens(1, Fons.IN, 10), 
                new ModCapiens(1, Fons.IN, 11), 
                new ModCapiens(1, Fons.IN, 12), 
                new ModCapiens(1, Fons.IN, 13), 
                new ModCapiens(1, Fons.IN, 14), 
                new ModCapiens(1, Fons.IN, 15), 
                new ModCapiens(1, Fons.IN, 16), 
                new ModCapiens(1, Fons.IN, 17), 
                new ModCapiens(1, Fons.IN, 18), 
                new ModCapiens(1, Fons.IN, 19), 
                new ModCapiens(1, Fons.IN, 20), 
                new ModCapiens(1, Fons.IN, 21), 
                new ModCapiens(1, Fons.IN, 22), 
                new ModCapiens(1, Fons.IN, 23), 
                new ModCapiens(1, Fons.IN, 24), 
                new ModCapiens(1, Fons.IN, 25)*//*));
        /*ZTransformare zt = new ZTransformare(new ModAddens(
                new ModCapiens(1), 
                new ModCapiens(Fons.EX, 0, -0.9999)));*//*
        zt.ponoFons(ll);*/
        sl.scribo(ll);
    }
    public static class LegibilisInputStream extends InputStream {
        ObjectInputStream o_in;
        ArrayList<Byte> list;
        Aestimatio ratio;
        Integer read;
        long count = 0;
        public LegibilisInputStream(ObjectInputStream o_in, Aestimatio ratio){
            this.o_in = o_in;
            this.ratio = ratio;
            list = new ArrayList<>();
            read = null;
        }

        @Override
        public int read() throws IOException {
            if(read == null){
                read = lego();
            }
            //System.out.println(count + ";" + read);
            //count++;
            int ret = read;
            read = null;
            return ret;
        }

        public int lego() throws IOException{
            if(list.isEmpty()){
                try {
                    Aestimatio value = (Aestimatio)o_in.readObject();
                    
                    ByteBuffer buffer = ByteBuffer.allocate(8);
                    buffer.putLong(value.multiplico(ratio).longValue());
                    byte[] array = buffer.array();
                    for (int i = 8 - BYTE_PER_EXAMPLUM;i < 8;i++) {
                        list.add(array[i]);
                    }
                    
                    /*ByteBuffer buffer = ByteBuffer.allocate(2 * BYTE_PER_EXAMPLUM);
                    buffer.putInt(value.multiplico(ratio).intValue());
                    for (int i = BYTE_PER_EXAMPLUM;i < buffer.array().length;i++) {
                        list.add(buffer.array()[i]);
                    }*/
                    
                    count++;
                    if(count % (REGULA_EXAMPLI * CHANNEL) == 0){
                        log.info("scripti : " + (count / REGULA_EXAMPLI / CHANNEL) + " sec.");
                    }
                } catch (EOFException ex) {
                    return -1;
                } catch (IOException | ClassNotFoundException | RuntimeException ex) {
                    Logger.getLogger(ScriptorWav.class.getName()).log(Level.SEVERE, "count=" + count, ex);
                    throw new IOException(ex);
                }
            }
            int ret = list.remove(0).intValue();
            if(ret < 0){
                ret += 256;
            }
            return ret;
            
        }
        @Override
        public int available() throws IOException {
            if(read == null){
                read = lego();
            }
            if(read == -1){
                return 0;
            }
            return 1;
        }
        @Override
        public void close() throws IOException {
            o_in.close();
        }
    }
    /*public static byte[] toBytes(long value, int byte){
        String str = Long.toBinaryString(value);
        while()
    }*/
    /*public static String toString(byte[] bytes){
        String str = "";
        for(byte _byte:bytes){
            str += str.isEmpty()?"":",";
            str += Byte.toString(_byte);
        }
        return str;
    }
    public static void main(String[] args){
        ByteBuffer lb = ByteBuffer.allocate(8);
        lb.putLong(1);
        for(byte _byte:lb.array()){
            System.out.println(_byte);
        }
    }*/
}
