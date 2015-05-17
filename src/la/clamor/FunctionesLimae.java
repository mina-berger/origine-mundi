package la.clamor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import la.clamor.Lima.FilumOctorum;
import la.clamor.Lima.RiffData;
import la.clamor.Punctum.Aestimatio;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import origine_mundi.OmUtil;


public class FunctionesLimae implements Constantia {
    public static Log log = LogFactory.getLog(FunctionesLimae.class);
    public static void trim(File lima, Aestimatio min){
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
            
        System.out.println(ab_index + ":" + ad_index + ":" + length);
        try {
            File tmp_file1 = File.createTempFile("l_lima1", Long.toString(System.currentTimeMillis()));
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
            System.out.println("d1:" + lima.exists());
            lima.delete();
            System.out.println("d2:" + lima.exists());
            tmp_file1.renameTo(lima);
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }
        
        log.info("count=" + count + ":" + ab_index + " - " + ad_index);
    }
    public static void facioLimam(File source, File target, Aestimatio volume, boolean teneo_pan){
        FileInputStream in;
        int longitudo;
        int channel = 0;
        int regula_exampli_fontis = 0;
        int bytes = 0;
        boolean read_fmt = false;

        try{
            in = new FileInputStream(source);
        }catch(FileNotFoundException e){
            throw new ExceptioClamoris(e);
        }

        FilumOctorum filum_octorum;
        filum_octorum = legoFilumOctorum(in, 4);
        if(!filum_octorum.toString().equals(Lima.RIFF)){
            throw new ExceptioClamoris("this is not a RIFF file(" + source.getAbsolutePath() + ")");
        }
        legoFilumOctorum(in, 4);//file size
        if(!legoFilumOctorum(in, 4).toString().equals(Lima.WAVE)){
            throw new ExceptioClamoris("this is not a WAVE file(" + source.getAbsolutePath() + ")");
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
                longitudo = (int)longitudo1;
                break;
            }else if(tag.equals("fmt ")){
                read_fmt = true;
                RiffData fmt = new RiffData(tag, legoFilumOctorum(in, (int)longitudo1));
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
        //try {
        //    in.close();
        //} catch (IOException ex) {
        //    throw new ExceptioClamoris(ex);
       // }

        //index = 0;
        if(!read_fmt){
            throw new ExceptioClamoris("fmt tag unread.");
        }
        int octets_length = (int)(longitudo / bytes / channel);
        log.info("octets_length=" + octets_length);
        //puncta = new Puncta(octets_length);
        //ObjectOutputStream o_out;
        //ObjectInputStream  o_in;
        //FileOutputStream f_out;
        ScriptorLimam sl;
        LectorLimam ll;
        Maximum max = new Maximum(teneo_pan);
        //Aestimatio max = new Aestimatio();
        try {
            File tmp_file1 = File.createTempFile("l_lima1", Long.toString(System.currentTimeMillis()));
            //f_out = new FileOutputStream(tmp_file1);
            //o_out = new ObjectOutputStream(f_out);
            sl = new ScriptorLimam(tmp_file1);
            //int init = 0;
            for(int i = 0;i < octets_length;i++){
                Punctum punctum;
                if(channel == 1){
                    FilumOctorum read = legoFilumOctorum(in, bytes);
                    Aestimatio monoral_data = new Aestimatio(read.getByteValueInteger(0, bytes));
                    punctum = new Punctum(monoral_data);
                    //max = max.max(monoral_data);
                    max.ponoAestimatio(monoral_data);
                }else{
                    punctum = new Punctum();
                    for(int j = 0;j < channel;j++){
                        FilumOctorum read = legoFilumOctorum(in, bytes);
                        Aestimatio datum = new Aestimatio(read.getByteValueInteger(0, bytes));
                        punctum.ponoAestimatio(j, datum);
                        //max = max.max(datum);
                        max.ponoAestimatio(j, datum);
                        //if(init < 1000){
                        //    System.out.println(i + ":" + datum.doubleValue());
                        //    init++;
                        //}
                    }
                }
                sl.scribo(punctum);
                //for(int k = 0;k < CHANNEL;k++){
                //    o_out.writeDouble(punctum.capioAestimatio(k).rawValue());
                //}
            }
            //o_out.flush();
            //o_out.close();
            //f_out.close();
            sl.close();
            log.info("max=" + max.punctum);
            log.info(tmp_file1.getAbsolutePath());
            //o_in = new ObjectInputStream(new FileInputStream(tmp_file1));
            ll = new LectorLimam(tmp_file1);
            File tmp_file2 = File.createTempFile("l_lima2", Long.toString(System.currentTimeMillis()));
            //f_out = new FileOutputStream(tmp_file2);
            //o_out = new ObjectOutputStream(f_out);
            sl = new ScriptorLimam(tmp_file2);
            //while(o_in.available() > 0){
                //o_out.writeDouble(new Aestimatio(o_in.readDouble(), true).multiplico(volume).partior(max).rawValue());
            //}
            int scribo = 0;
            while(ll.paratusSum()){
                Punctum p = ll.lego().multiplico(volume).partior(max.punctum);
                sl.scribo(p);
                //if(scribo < 100){
                //    System.out.println(scribo + ":" + p);
               // }
                scribo++;
            }
            log.info("scribo=" + scribo);
            ll.close();
            sl.close();
            //o_in.close();
            //o_out.flush();
            //o_out.close();
            //f_out.close();
            tmp_file1.delete();
            if(regula_exampli_fontis == REGULA_EXAMPLI){
                //puncta_stream = new ObjectInputStream(new FileInputStream(tmp_file2));
                tmp_file1.delete();
                //if(target.exists()){
                    target.delete();
                //}
                tmp_file2.renameTo(target);
                return;
            }
            octets_length = (int)((double)octets_length * (double)REGULA_EXAMPLI / (double)regula_exampli_fontis);
            log.info("resample octets_length=" + octets_length);
            //Puncta resampled = new Puncta(octets_length);
            //o_in = new ObjectInputStream(new FileInputStream(tmp_file2));
            ll = new LectorLimam(tmp_file2);
            File tmp_file3 = File.createTempFile("l_lima3", Long.toString(System.currentTimeMillis()));
            //f_out = new FileOutputStream(tmp_file3);
            //o_out = new ObjectOutputStream(f_out);
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
                    //Punctum punctum = new Punctum();
                    //if(o_in.available() > 0){
                    //    for(int k = 0;k < CHANNEL;k++){
                    //        punctum.ponoAestimatio(k, new Aestimatio(o_in.readDouble(), true));
                    //    }
                    //}
                    Punctum punctum = ll.paratusSum()?ll.lego():new Punctum();
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
                //for(int k = 0;k < CHANNEL;k++){
                //    o_out.writeDouble(resampled.capioAestimatio(k).rawValue());
                //}
                sl.scribo(resampled);
            }
            //o_in.close();
            //o_out.flush();
            //o_out.close();
            ll.close();
            sl.close();
            tmp_file2.delete();
            //if(target.exists()){
                target.delete();
            //}
            tmp_file3.renameTo(target);
        } catch (IOException ex) {
            Logger.getLogger(FunctionesLimae.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException(ex);
        }
    }
    private static class Maximum {
        Punctum punctum;
        boolean teneo_pan;
        Maximum(boolean teneo_pan){
            this.teneo_pan = teneo_pan;
            punctum = new Punctum();
        }
        void ponoAestimatio(Aestimatio value){
            for(int i = 0;i < punctum.longitudo();i++){
                punctum.ponoAestimatio(i, punctum.capioAestimatio(i).max(value));
            }
        }
        void ponoAestimatio(int index, Aestimatio value){
            if(teneo_pan){
                ponoAestimatio(value);
            }else{
                punctum.ponoAestimatio(index, punctum.capioAestimatio(index).max(value));
            }
        }
    }
    /*public Clip getClip(double pan, double velocity){
        return new Clip(wave_data, pan, velocity);
    }*/
    private static FilumOctorum legoFilumOctorum(InputStream in, int longitudo){
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
        File src =new File(OmUtil.getDirectory("sample"), "01d.wav");
        File lima = new File(OmUtil.getDirectory("sample"), "01u.lima");
        File trg =new File(OmUtil.getDirectory("sample"), "01udteim.wav");
        
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
