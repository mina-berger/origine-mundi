package la.clamor;



import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;

public interface Functiones extends Constantia {
    public static Log log = LogFactory.getLog(Functiones.class);
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
        log.info("audio format:" + audio_format.toString());
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
    public static double NUMERO_OCTAVI = 12;
    public static double HZ_A4 = 440;
    public static double capioHZ(double midi_note_number, double cents){
        return FastMath.pow(2d, (midi_note_number - 69d + cents * 0.01d) * 1d / NUMERO_OCTAVI) * HZ_A4;        
    }
    public static String getArchivePath(){
        return isMac()?"/Users/mina/drive/doc/origine_mundi/archive/":"D:/origine_mundi/archive/";
    }
    public static String getHomePath(){
        return isMac()?"/Users/mina/drive/":"C://drive/";
    }
    public static boolean isMac(){
        return System.getProperty("os.name").toLowerCase().startsWith("mac");        
    }    
    //private static final DecimalFormat df = new DecimalFormat("+0.000000000;-0.000000000");
    public static String toString(double value) {
        final DecimalFormat df = new DecimalFormat("+0.000000000;-0.000000000");
        return df.format(value);
    }
    public static String toString(int value, int digit) {
        String format = "";
        while(format.length() < digit){
            format += "0";
        }
        return new DecimalFormat(format).format(value);
    }
    public static boolean approximateZero(Punctum punctum){
        return approximateZero(punctum, 0.000000001);
    }
    public static boolean approximateZero(Punctum punctum, double threshold){
        return punctum.abs().isLessThan(threshold);
    }
    public static double[] hanningWindow(int length){
        double w[] = new double[length];
        int n;
        double offset = (length % 2 == 0)?0:0.5;
        for (n = 0; n < length; n++){
            w[n] = 0.5 - 0.5 * FastMath.cos(2.0 * FastMath.PI * (n + offset) / length);
        }
        return w;
        
    }
    
}
