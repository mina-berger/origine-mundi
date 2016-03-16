/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.deinde;

import la.clamor.deinde.bk.Spectrum;
import la.clamor.deinde.bk.FourierSampler2;
import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import la.clamor.Constantia;
import la.clamor.Functiones;
import static la.clamor.Functiones.capioSourceDataLine;
import la.clamor.Legibilis;
import la.clamor.SineOscillatio;
import la.clamor.Punctum;
import origine_mundi.OmUtil;

/**
 *
 * @author mina
 */
public class DeindeTest {

    static Legibilis legibilis = new Legibilis() {
        SineOscillatio o1 = new SineOscillatio();
        SineOscillatio o2 = new SineOscillatio();
        int count = 0;
        double pitch1 = 440;
        double pitch2 = 1760;

        @Override
        public Punctum lego() {
            count++;
            //pitch1 += 0.02;
            //return o1.lego(new Punctum(pitch1), new Punctum(1));
            return o1.lego(new Punctum(pitch1), new Punctum(1)).addo(
                o2.lego(new Punctum(pitch2), new Punctum(1)));
        }

        @Override
        public boolean paratusSum() {
            return count < 144000;
        }

        @Override
        public void close() {
        }
    };

    public static void _main(String[] args) {
        //File out_file = new File(OmUtil.getDirectory("opus"), "oscillatio_multi.wav");
        boolean create = true;
        if(create){
            //FourierSampler2 fs = new FourierSampler2(new Resampler(legibilis, Constantia.REGULA_EXAMPLI_D, Constantia.REGULA_EXAMPLI_D / 2), 4096, 0);
            FourierSampler2 fs = new FourierSampler2(legibilis, 1048576, 0);
            //for (Spectrum spectrum : fs.lego()) {
                //spectrum.optimize();
                //spectrum.print(System.out);

            //}
            Spectrum spectrum = fs.lego().get(0);
            //spectrum.print(System.out, REGULA_EXAMPLI_D);
            //System.out.println(spectrum.getLoudFrequency(REGULA_EXAMPLI_D));
            //ScriptorWav sw = new ScriptorWav(out_file);
            //sw.scribo(new OscillatioMulti(spectrum, 3000.), false);
        }
        //sw.scribo(new LowFi(legibilis, 0.5), false);
        //Functiones.ludoLimam(out_file);

    }
    
    
    public static void main(String[] args) {
        //Constantia.CHANNEL = 4;
        Punctum punctum = new Punctum();
        System.out.println(punctum);
        //String mixer_name = "TASCAM US-4x4 Audio Device";
        //Port TASCAM US-4x4 Audio Device
        //Mixer.Info mixerInfo = capioMixerInfo(mixer_name);
        //System.out.println(mixerInfo);
        //Mixer mixer = AudioSystem.getMixer(mixerInfo);
        
        //AudioFormat format = Constantia.getAudioFormat(48000, 2, 4);
        //SourceDataLine line = capioSourceDataLine(null, format, AudioSystem.NOT_SPECIFIED);
        //System.out.println(line.);
        //audio format:PCM_SIGNED 48000.0 Hz, 16 bit, stereo, 4 bytes/frame, little-endian
        
        /*Mixer.Info[] aInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info aInfo : aInfos) {
            System.out.println(aInfo.getName());
        }*/
    }
    public static void main2(String[] args) {
        //ScriptorWav sw = new ScriptorWav(out_file);
        //sw.scribo(new LowFi(legibilis, 20), false);
        //sw.scribo(new Resampler(legibilis, Constantia.REGULA_EXAMPLI_D, Constantia.REGULA_EXAMPLI_D / 2), false);
        //sw.scribo(legibilis, false);
        //Functiones.ludoLimam(out_file);
        File out_file;
        //out_file = new File(OmUtil.getDirectory("sample/"), "Opus021_0.wav");
        //Functiones.ludoLimam(out_file);
        //out_file = new File(OmUtil.getDirectory("sample/"), "Opus021_1.wav");
        //Functiones.ludoLimam(out_file);
        out_file = new File(OmUtil.getDirectory("sample/"), "Opus021_2.wav");
        Functiones.ludoLimam(out_file);

    }
    public static void __main(String[] args){
        double[] freqs = new double[]{
            421.875, 433.59375, 445.3125, 468.75};
        
        double[] amps = new double[]{
            0.207763944, 0.580138255, 0.690567064, 0.212670797};
        //double[] freqs = new double[]{
        //    433.59375, 445.3125};
        
        //double[] amps = new double[]{
        //    0.580138255, 0.690567064};
        double moment = 0;
        double base = 0;
        for(int i = 0;i < freqs.length;i++){
            moment += (freqs[i] - freqs[0]) * amps[i];
            base += amps[i];
        }
        
        double freq = moment / base + freqs[0];
        System.out.println(freq);
        freq = (freqs[2] - freqs[1]) * amps[2] / (amps[1] + amps[2]) + freqs[1];
        //freq = (freqs[1] - freqs[0]) * amps[1] / (amps[0] + amps[1]) + freqs[0];
        System.out.println(freq);
        
    }

}
