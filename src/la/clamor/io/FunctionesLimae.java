package la.clamor.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import la.clamor.io.Lima.FilumOctorum;
import la.clamor.io.Lima.RiffData;
import la.clamor.Aestima;
import la.clamor.Constantia;
import la.clamor.ExceptioClamoris;
import la.clamor.Functiones;
import la.clamor.Punctum;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import static la.clamor.Constantia.getAudioFormat;
import la.clamor.Legibilis;
import la.clamor.forma.CadentesFormae;


public class FunctionesLimae implements Constantia {
    public static Log log = LogFactory.getLog(FunctionesLimae.class);
    public static void trim(File lima, Aestima min){
        if(min.doubleValue() < 0){
            throw new ExceptioClamoris("minimum must be 0 or positive:" + min);
        }
        LectorLimam ll = new LectorLimam(lima);
        long count = 0;
        Long ab_index = null;
        Long ad_index = null;
       
        Punctum prev = null;
        while(ll.paratusSum()){
            Punctum punctum = ll.lego();
            if(prev == null){
                prev = punctum;
                count++;
                continue;
            }
            if(!punctum.minorAbsTotiSunt(min)){
               if(ab_index == null){
                   ab_index = count;
               }
               ad_index = count;
            }
            count++;
        }
        ll.close();
        long length = count;
        ll = new LectorLimam(lima);
        count = 0;
            
        File tmp_file1 = IOUtil.createTempFile("l_lima1");
        ScriptorLimam sl = new ScriptorLimam(tmp_file1);
        if(ab_index > 0){
            sl.scribo(new Punctum());
        }
        while(ll.paratusSum()){
            Punctum punctum = ll.lego();
            if(count >= ab_index && count <= ad_index){
                sl.scribo(punctum);
            }
            count++;
        }
        if(ad_index < length){
            sl.scribo(new Punctum());
        }
        ll.close();
        sl.close();
        lima.delete();
        tmp_file1.renameTo(lima);
        
        log.info("count=" + count + ":" + ab_index + " - " + ad_index);
    }
    public static void formoWav(File wav, CadentesFormae cf, boolean teneo_source){
        LectorWav lw = new LectorWav(wav);
        File forma_wav = IOUtil.createTempFile("forma_wav");
        ScriptorWav sw = new ScriptorWav(forma_wav);
        Legibilis l = cf.capioLegibilis(lw);
        sw.scribo(l, false);
        l.close();
        if(teneo_source){
            renameBackup(wav, "wav");
        }else{
            wav.delete();
        }
        forma_wav.renameTo(wav);
        
    }
    public static void renameBackup(File file, String suffix){
        String search = "." + suffix;
        String name = file.getName();
        if(name.endsWith(search)){
            name = name.substring(0, name.length() - search.length());
            int index = name.lastIndexOf(".");
            if(index > 0){
                try{
                    int number = Integer.parseInt(name.substring(index + 1));
                    number++;
                    name = name.substring(0, index) + "." + number;
                }catch(Exception e){
                    name += ".0";
                }
            }else{
                name += ".0";
            }
        }else{
            name += ".0";
        }
        File rename  = new File(file.getParentFile(), name + search);
        System.out.println("rename:" + rename.getAbsolutePath());
        file.renameTo(rename);
    }
    /**
     * 
     * @param source .wav
     * @param target .lima
     * @param volume
     * @param teneo_pan true:maximize with pan, false:without
     * @param teneo_sample
     * @return 
     */
    public static AudioFormat facioLimam(File source, File target, Aestima volume, boolean teneo_pan, boolean teneo_sample){
        FileInputStream source_in;
        int longitudo;
        int channel = 0;
        int regula_exampli_fontis = 0;
        int bytes = 0;
        boolean read_fmt = false;
        AudioFormat format;
        try {
            format = AudioSystem.getAudioFileFormat(source).getFormat();
        } catch (UnsupportedAudioFileException | IOException ex) {
            throw new ExceptioClamoris(ex);
        }

        try{
            source_in = new FileInputStream(source);
        }catch(FileNotFoundException e){
            throw new ExceptioClamoris(e);
        }

        FilumOctorum filum_octorum;
        filum_octorum = legoFilumOctorum(source_in, 4);
        if(!filum_octorum.toString().equals(Lima.RIFF)){
            throw new ExceptioClamoris("this is not a RIFF file(" + source.getAbsolutePath() + ")");
        }
        legoFilumOctorum(source_in, 4);//file size
        if(!legoFilumOctorum(source_in, 4).toString().equals(Lima.WAVE)){
            throw new ExceptioClamoris("this is not a WAVE file(" + source.getAbsolutePath() + ")");
        }
        while(true){
            filum_octorum = legoFilumOctorum(source_in, 4);
            String tag = filum_octorum.toString();
            long longitudo1 = legoFilumOctorum(source_in, 4).capioLong();
            if(longitudo1 > Integer.MAX_VALUE){
                throw new IllegalArgumentException("extra longitudo(" + longitudo1 + ")");
            }
            if(tag == null){
            }else if(tag.equals("data")){
                longitudo = (int)longitudo1;
                break;
            }else if(tag.equals("fmt ")){
                read_fmt = true;
                RiffData fmt = new RiffData(tag, legoFilumOctorum(source_in, (int)longitudo1));
                if(fmt.filum_octorum.capioInt(0, 2) != 1){
                    throw new ExceptioClamoris("wav format(" + fmt.filum_octorum.capioInt(0, 2) + ") must be PCM(1).");
                }
                channel = fmt.filum_octorum.capioInt(2, 2);
                if(channel != 1 && channel != 2){
                    throw new ExceptioClamoris("so far monaural and stereo only(channel=" + channel + ").");
                }
                regula_exampli_fontis = fmt.filum_octorum.capioInt(4, 4);
                int bits = fmt.filum_octorum.capioInt(14, 2);
                if(bits % 8 != 0){
                    throw new ExceptioClamoris("bit(" + bits + ") must be multiplr of 8.");
                }
                bytes = bits / 8;
                log.info("bytes=" + bytes + ":channel=" + channel + ":sample rate=" + regula_exampli_fontis);
                if(fmt.filum_octorum.capioInt(12, 2) != bytes * channel){
                    throw new ExceptioClamoris("block size(" + fmt.filum_octorum.capioInt(12, 2) + ") must be byte(" + bytes + ") * channel(" + channel + ").");
                }
                if(fmt.filum_octorum.capioInt(8, 4) != regula_exampli_fontis * channel * bytes){
                    throw new ExceptioClamoris("byte per second(" + fmt.filum_octorum.capioInt(8, 4) + ") must be must be byte(" + bytes + ") * channel(" + channel + ").");
                }
            }
        }
        if(!read_fmt){
            throw new ExceptioClamoris("fmt tag unread.");
        }
        int octets_length = (int)(longitudo / bytes / channel);
        log.info("octets_length=" + octets_length);
        ScriptorLimam sl;
        LectorLimam ll;
        Maximum max = new Maximum(teneo_pan);
        try {
            File tmp_file1 = IOUtil.createTempFile("l_lima1");
            sl = new ScriptorLimam(tmp_file1);
            for(int i = 0;i < octets_length;i++){
                Punctum punctum;
                if(channel == 1){
                    FilumOctorum read = legoFilumOctorum(source_in, bytes);
                    Aestima monoral_data = new Aestima(read.getByteValueInteger(0, bytes));
                    punctum = new Punctum(monoral_data);
                    max.ponoAestimatio(monoral_data);
                }else{
                    punctum = new Punctum();
                    for(int j = 0;j < channel;j++){
                        FilumOctorum read = legoFilumOctorum(source_in, bytes);
                        Aestima datum = new Aestima(read.getByteValueInteger(0, bytes));
                        punctum.ponoAestimatio(j, datum);
                        max.ponoAestimatio(j, datum);
                    }
                }
                sl.scribo(punctum);
            }
            sl.close();
            source_in.close();
            log.info("max=" + max.punctum);
            log.info(tmp_file1.getAbsolutePath());
            ll = new LectorLimam(tmp_file1);
            File tmp_file2 = IOUtil.createTempFile("l_lima2");
            sl = new ScriptorLimam(tmp_file2);
            int scribo = 0;
            while(ll.paratusSum()){
                Punctum p = ll.lego().multiplico(volume).partior(max.punctum);
                sl.scribo(p);
                scribo++;
            }
            log.info("scribo=" + scribo);
            ll.close();
            sl.close();
            tmp_file1.delete();
            if(teneo_sample || regula_exampli_fontis == REGULA_EXAMPLI){
                target.delete();
                tmp_file2.renameTo(target);
                return format;
            }
            octets_length = (int)((double)octets_length * (double)REGULA_EXAMPLI / (double)regula_exampli_fontis);
            log.info("resample octets_length=" + octets_length);
            ll = new LectorLimam(tmp_file2);
            File tmp_file3 = IOUtil.createTempFile("l_lima3");
            sl = new ScriptorLimam(tmp_file3);
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
                    Punctum punctum = ll.paratusSum()?ll.lego():new Punctum();
                    map.put(count, punctum);
                    count++;
                }
                Punctum resampled;
                if(floor == ceil){
                    resampled = map.get(floor);
                }else{
                    Punctum values_f = map.get(floor);
                    Punctum values_c = map.get(ceil);
                    resampled = new Punctum();
                    for(int j = 0;j < channel;j++){
                        resampled.ponoAestimatio(j,
                                values_f.capioAestima(j).multiplico(new Aestima((double)ceil - position)).addo(values_c.capioAestima(j).multiplico(new Aestima(position - (double)floor))));
                    }
                }
                sl.scribo(resampled);
            }
            ll.close();
            sl.close();
            tmp_file2.delete();
            target.delete();
            tmp_file3.renameTo(target);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        return getAudioFormat();
    }
    private static class Maximum {
        Punctum punctum;
        boolean teneo_pan;
        Maximum(boolean teneo_pan){
            this.teneo_pan = teneo_pan;
            punctum = new Punctum();
        }
        void ponoAestimatio(Aestima value){
            for(int i = 0;i < punctum.longitudo();i++){
                punctum.ponoAestimatio(i, punctum.capioAestima(i).max(value));
            }
        }
        void ponoAestimatio(int index, Aestima value){
            if(teneo_pan){
                ponoAestimatio(value);
            }else{
                punctum.ponoAestimatio(index, punctum.capioAestima(index).max(value));
            }
        }
    }
    /*public Clip getClip(double pan, double velocity){
        return new Clip(wave_data, pan, velocity);
    }*/
    public static FilumOctorum legoFilumOctorum(InputStream in, int longitudo){
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
        File src =new File(IOUtil.getDirectory("sample"), "01d.wav");
        File lima = new File(IOUtil.getDirectory("sample"), "01u.lima");
        File trg =new File(IOUtil.getDirectory("sample"), "01udteim.wav");
        
        //System.out.println("s:" + src.length() + ":" + src.lastModified());
        //FunctionesLimae.facioLimam(src, lima, new Aestimatio(1), false);
        //System.out.println("l" + lima.length() + ":" + lima.lastModified());
        //FunctionesLimae.trim(lima, new Aestimatio(0.01));
        //System.out.println("l" + lima.length() + ":" + lima.lastModified());
        
        ScriptorWav sw = new ScriptorWav(trg);
        sw.scribo(new LectorLimam(lima), false);
        Functiones.ludoLimam(trg);

    }

}
