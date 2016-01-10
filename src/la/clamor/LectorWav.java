package la.clamor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import la.clamor.Lima.FilumOctorum;
import la.clamor.Lima.RiffData;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class LectorWav implements Constantia, Legibilis {
    public static Log log = LogFactory.getLog(LectorWav.class);
    private int longitudo;
    //private int index;
    private int channel;
    private int regula_exampli_fontis;
    private int bytes;
    ObjectInputStream  puncta_stream;

    //RiffData format;
    //private Puncta puncta;
    public LectorWav(File file, Aestimatio volume){
        FileInputStream in;
        try{
            in = new FileInputStream(file);
        }catch(FileNotFoundException e){
            throw new ExceptioClamoris(e);
        }

        FilumOctorum filum_octorum;
        filum_octorum = legoFilumOctorum(in, 4);
        if(!filum_octorum.toString().equals(Lima.RIFF)){
            throw new ExceptioClamoris("this is not a RIFF file(" + file.getAbsolutePath() + ")");
        }
        legoFilumOctorum(in, 4);//file size
        if(!legoFilumOctorum(in, 4).toString().equals(Lima.WAVE)){
            throw new ExceptioClamoris("this is not a WAVE file(" + file.getAbsolutePath() + ")");
        }
        //System.out.println(type);
        while(true){
            filum_octorum = legoFilumOctorum(in, 4);
            //if(octets == null) break;
            String tag = filum_octorum.toString();
            long longitudo1 = legoFilumOctorum(in, 4).capioLong();
            if(longitudo1 > Integer.MAX_VALUE){
                throw new IllegalArgumentException("extra longitudo(" + longitudo1 + ")");
            }
            if(tag == null){
            }else if(tag.equals("data")){
                this.longitudo = (int)longitudo1;
                break;
            }else if(tag.equals("fmt ")){
                RiffData fmt = new RiffData(tag, legoFilumOctorum(in, (int)longitudo1));
                if(fmt.filum_octorum.capioInt(0, 2) != 1){
                    throw new ExceptioClamoris("wav format(" + fmt.filum_octorum.capioInt(0, 2) + ") must be PCM(1).");
                }
                channel = fmt.filum_octorum.capioInt(2, 2);
                if(channel != 1 && channel != 2){
                    throw new ExceptioClamoris("so far monaural and stereo only(channel=" + channel + ").");
                }
                log.info("channel=" + channel);
                regula_exampli_fontis = fmt.filum_octorum.capioInt(4, 4);
                int bits = fmt.filum_octorum.capioInt(14, 2);
                if(bits % 8 != 0){
                    throw new ExceptioClamoris("bit(" + bits + ") must be multiplr of 8.");
                }
                bytes = bits / 8;
                log.info("bytes=" + bytes);
                if(fmt.filum_octorum.capioInt(12, 2) != bytes * channel){
                    throw new ExceptioClamoris("block size(" + fmt.filum_octorum.capioInt(12, 2) + ") must be byte(" + bytes + ") * channel(" + channel + ").");
                }
                if(fmt.filum_octorum.capioInt(8, 4) != regula_exampli_fontis * channel * bytes){
                    throw new ExceptioClamoris("byte per second(" + fmt.filum_octorum.capioInt(8, 4) + ") must be must be byte(" + bytes + ") * channel(" + channel + ").");
                }
            }
        }

        //index = 0;
        int octets_length = (int)(longitudo / bytes / channel);
        log.info("octets_length=" + octets_length);
        //puncta = new Puncta(octets_length);
        ObjectOutputStream o_out;
        ObjectInputStream  o_in;
        FileOutputStream f_out;
        Aestimatio max = new Aestimatio();
        try {
            File tmp_file1 = File.createTempFile("l_lima1", Long.toString(System.currentTimeMillis()));
            f_out = new FileOutputStream(tmp_file1);
            o_out = new ObjectOutputStream(f_out);
            for(int i = 0;i < octets_length;i++){
                Punctum punctum;
                if(channel == 1){
                    FilumOctorum read = legoFilumOctorum(in, bytes);
                    Aestimatio monoral_data = new Aestimatio(read.getByteValueInteger(0, bytes));
                    punctum = new Punctum(monoral_data);
                    max = max.max(monoral_data);
                }else{
                    punctum = new Punctum();
                    for(int j = 0;j < channel;j++){
                        FilumOctorum read = legoFilumOctorum(in, bytes);
                        Aestimatio datum = new Aestimatio(read.getByteValueInteger(0, bytes));
                        punctum.ponoAestimatio(j, datum);
                        max = max.max(datum);
                    }
                }
                for(int k = 0;k < CHANNEL;k++){
                    o_out.writeDouble(punctum.capioAestimatio(k).rawValue());
                }
            }
            o_out.flush();
            o_out.close();
            f_out.close();
            log.info("max=" + max);
            log.info(tmp_file1.getAbsolutePath());
            o_in = new ObjectInputStream(new FileInputStream(tmp_file1));
            File tmp_file2 = File.createTempFile("l_lima2", Long.toString(System.currentTimeMillis()));
            f_out = new FileOutputStream(tmp_file2);
            o_out = new ObjectOutputStream(f_out);
            while(o_in.available() > 0){
                o_out.writeDouble(new Aestimatio(o_in.readDouble(), true).multiplico(volume).partior(max).rawValue());
                /*Punctum punctum = (Punctum)o_in.readObject();
                System.out.println("write:" + punctum.toString());
                punctum = punctum.multiplico(volume / max);
                o_out.writeObject(punctum);*/
            }
            o_out.flush();
            o_out.close();
            f_out.close();
            if(regula_exampli_fontis == REGULA_EXAMPLI){
                puncta_stream = new ObjectInputStream(new FileInputStream(tmp_file2));
                return;
            }
            octets_length = (int)((double)octets_length * (double)REGULA_EXAMPLI / (double)regula_exampli_fontis);
            log.info("resample octets_length=" + octets_length);
            //Puncta resampled = new Puncta(octets_length);
            o_in = new ObjectInputStream(new FileInputStream(tmp_file2));
            File tmp_file3 = File.createTempFile("l_lima3", Long.toString(System.currentTimeMillis()));
            f_out = new FileOutputStream(tmp_file3);
            o_out = new ObjectOutputStream(f_out);
            TreeMap<Integer, Punctum> map = new TreeMap<>();
            int count = 0;
            for(int i = 0;i < octets_length;i++){
                double position = (double)regula_exampli_fontis * (double)i / (double)REGULA_EXAMPLI;
                int floor = (int)Math.floor(position);
                int ceil  = (int)Math.ceil (position);
                TreeMap<Integer, Punctum> map2 = new TreeMap<>();
                for(Integer key:map.keySet()){
                    if(key >= floor){
                        map2.put(key, map.get(key));
                    }
                }
                map = map2;
                while(count <= ceil){
                    Punctum punctum = new Punctum();
                    if(o_in.available() > 0){
                        for(int k = 0;k < CHANNEL;k++){
                            punctum.ponoAestimatio(k, new Aestimatio(o_in.readDouble(), true));
                        }
                    }
                    map.put(count, punctum);
                    count++;
                }
                //log.info("map_size=" + map.size());
                Punctum resampled;
                if(floor == ceil){
                    resampled = map.get(floor);
                }else{
                    Punctum values_f = map.get(floor);
                    Punctum values_c = map.get(ceil);
                    resampled = new Punctum();
                    for(int j = 0;j < channel;j++){
                        resampled.ponoAestimatio(j,
                                values_f.capioAestimatio(j).multiplico(new Aestimatio((double)ceil - position)).addo( 
                                values_c.capioAestimatio(j).multiplico(new Aestimatio(position - (double)floor))));
                    }
                }
                for(int k = 0;k < CHANNEL;k++){
                    o_out.writeDouble(resampled.capioAestimatio(k).rawValue());
                }
            }
            o_out.flush();
            o_out.close();
            puncta_stream = new ObjectInputStream(new FileInputStream(tmp_file3));
        } catch (IOException ex) {
            Logger.getLogger(LectorWav.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex);
        }
    }
    /*public Clip getClip(double pan, double velocity){
        return new Clip(wave_data, pan, velocity);
    }*/
    private FilumOctorum legoFilumOctorum(InputStream in, int longitudo){
        byte[] buffer = new byte[longitudo];
        try {
            if(in.read(buffer) != longitudo){
                return null;
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        return new FilumOctorum(buffer);
    }
    public static void main(String[] arg){

        //WavFileWriter wavfile = new WavFileWriter(new File(Functiones.getHomePath() + "phthongos/test.wav"));
        //wavfile.write(new LectorLimam("drum" ,"bd1", 1).getClip(0, 1));
        //AudioUtil.playAudio(wavfile.getFile());

    }
    @Override
    public Punctum lego() {
        try {
            Punctum punctum = new Punctum();
            for(int i = 0;i < CHANNEL;i++){
                punctum.ponoAestimatio(i, new Aestimatio(puncta_stream.readDouble(), true));
            }
            return punctum;
        } catch (IOException ex) {
            Logger.getLogger(LectorWav.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex);
        }
    }
    @Override
    public boolean paratusSum() {
        try {
            return puncta_stream.available() > 0;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

}
