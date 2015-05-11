package la.clamor;



import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.complex.ComplexFormat;
import org.apache.commons.math3.util.FastMath;

public class Functiones implements Constantia {
    private static Integer clavis = null;
    public static Integer deinde(){
        if(clavis == null){
            clavis = 0;
        }else{
            clavis = clavis + 1;
        }
        return clavis;
    }
    
    public static long adPositio(double tempus){
        return (long)(tempus * REGULA_EXAMPLI_D / 1000d);
    }
    public static double adTempus(long positio){
        return (double)positio * 1000d / REGULA_EXAMPLI_D;
    }
    /**
     * say length is 4, solve the equation below
     * c[0] * x^3 + c[1] * x^2 + c[2] * x + c[3] = 0
     * @param coefficientes
     * @return 
     */
    public static Complex[] solvo(double[] coefficientes){
        ArrayUtils.reverse(coefficientes);
        /*for(int i = 0;i < coefficientes.length;i++){
            System.out.println(coefficientes[i]);
        }*/
        return new LaguerreSolver().solveAllComplex(coefficientes, 0.0);
    }
    
    public static void main(String[] args){
        ludoLimam(new File("doc/sample/sample01.wav"));
        
        /*List<Double> list = Arrays.asList(1d, 2d, 3d, 4d, 5d);
        String str = "";
        str = list.stream().map((Double d) -> 
            d.toString().concat(" ")
        ).reduce(str, String::concat);
        System.out.println(str);*/
        /*String[] values = {"A001", "B001", "AB001", "BA001"};
        List<String> list = Arrays.asList(values);
        list.stream().filter(e -> e.contains("A"))      // 「A」で始まるもののみ取得
        .forEach(e -> System.out.println(e));  */

        // p(x) = x^5 - x^4 - 12x^3 + x^2 - x - 12 = (x+1)(x+3)(x-4)(x^2-x+1)
        
        /*
        int count = 12;
        double[] coefficients = new double[count];
        Arrays.fill(coefficients, 1.0 / (double)count);
        ComplexFormat f = ComplexFormat.getInstance();
        */


//Tabula tabula = Tabula.scatter("Zero", new File("c://drive/doc/clamor/spec/pole.html"), 
        //        "Real", "Imaginary", "Zero");
        /*int count = 12;
        for(int i = 0;i < count;i++){
            double rad = FastMath.PI * 2.0 * (double)i / (double)count;
            tabula.addo(FastMath.cos(rad), null, FastMath.sin(rad));
        }*/
        /*for(Complex result:solvo(coefficients)){
            tabula.addo(result.getReal(), result.getImaginary());
            System.out.println(f.format(result));
            System.out.println("gain :" + result.abs());
            System.out.println("phase:" + FastMath.atan2(result.getImaginary(), result.getReal()));
        }
        tabula.imprimo();*/

    }
    public static final int DEFAULT_EXTERNAL_BUFFER_SIZE = 128000;
    public static void ludoLimam(File audio_file) {
        int external_buffer_size = DEFAULT_EXTERNAL_BUFFER_SIZE;
        int internal_buffer_size = AudioSystem.NOT_SPECIFIED;

        AudioInputStream audio_input_stream;
        SourceDataLine line;
        try {
            audio_input_stream = AudioSystem.getAudioInputStream(audio_file);
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new IllegalArgumentException(e);
        }
        AudioFormat audio_format = audio_input_stream.getFormat();
        line = capioSourceDataLine(null, audio_format, internal_buffer_size);
        if (line == null) {
            throw new IllegalArgumentException(
                    "cannot get SourceDataLine for format:" + audio_format);
        }
        line.start();
        int bytes_read = 0;
        byte[] ab_data = new byte[external_buffer_size];
        while (bytes_read != -1) {
            try {
                bytes_read = audio_input_stream
                        .read(ab_data, 0, ab_data.length);
            } catch (IOException e) {
                throw new IllegalArgumentException(e);
            }
            if (bytes_read >= 0)
                line.write(ab_data, 0, bytes_read);
        }
        line.drain();
        line.close();
    }
    public static SourceDataLine capioSourceDataLine(String mixer_name,
            AudioFormat audio_format, int buffer_size) {
        SourceDataLine line = null;
        DataLine.Info info = new DataLine.Info(SourceDataLine.class,
                audio_format, buffer_size);
        try {
            if (mixer_name != null) {
                Mixer.Info mixerInfo = capioMixerInfo(mixer_name);
                if (mixerInfo == null) {
                    throw new IllegalArgumentException(
                            "AudioPlayer: mixer not found: " + mixer_name);
                }
                Mixer mixer = AudioSystem.getMixer(mixerInfo);
                line = (SourceDataLine) mixer.getLine(info);
            } else {
                line = (SourceDataLine) AudioSystem.getLine(info);
            }
            /*
             * The line is there, but it is not yet ready to receive audio data.
             * We have to open the line.
             */
            line.open(audio_format, buffer_size);
        } catch (LineUnavailableException e) {
            throw new IllegalArgumentException(e);
        }
        return line;
    }
    public static Mixer.Info capioMixerInfo(String mixer_name) {
        Mixer.Info[] aInfos = AudioSystem.getMixerInfo();
        for (Mixer.Info aInfo : aInfos) {
            if (aInfo.getName().equals(mixer_name)) {
                return aInfo;
            }
        }
        return null;
    }
}
