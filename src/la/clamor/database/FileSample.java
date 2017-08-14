/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.database;

import com.mina.util.CoreException;
import com.mina.util.Integers;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.AudioInputStream;

/**
 *
 * @author hiyamamina
 */
public class FileSample {

    private final int channel;
    private final int sample_size;
    private final byte[] b_buffer;
    private final int[] i_buffer;
    private final Integers value;
    private final Integers max;

    public FileSample(int channel, int sample_size) {
        this.channel = channel;
        this.sample_size = sample_size;
        b_buffer = new byte[channel * sample_size];
        i_buffer = new int[sample_size];
        value = Integers.getFilled(channel, 0);
        max = Integers.getFilled(channel, 0);
    }

    public Integers getMax() {
        return max;
    }

    public boolean read(AudioInputStream ais) throws IOException {
        if (ais.read(b_buffer) < b_buffer.length) {
            value.setAll(0);
            return false;
        }
        byte[] buffer = new byte[sample_size];
        for (int i = 0; i < channel; i++) {
            for (int j = 0; j < sample_size; j++) {
                buffer[j] = b_buffer[i * sample_size + j];
            }
            int i_value = toInt(buffer);
            value.set(i, i_value);
            max.setIfMajor(i, Math.abs(i_value));
        }
        return true;
    }

    public Integers getValue() {
        return value;
    }

    private static int toInt(byte[] bytes) {
        int[] buffer = new int[bytes.length];
        switch (bytes.length) {
            case 2:
                buffer[0] = (int) bytes[0];
                buffer[1] = (int) bytes[1];
                buffer[0] = buffer[0] < 0 ? buffer[0] + 256 : buffer[0];
                return buffer[0] + buffer[1] * 256;
            case 3:
                buffer[0] = (int) bytes[0];
                buffer[1] = (int) bytes[1];
                buffer[2] = (int) bytes[2];
                buffer[0] = buffer[0] < 0 ? buffer[0] + 256 : buffer[0];
                buffer[1] = buffer[1] < 0 ? buffer[1] + 256 : buffer[1];
                return buffer[0] + buffer[1] * 256 + buffer[2] * 256 * 256;
            default:
                throw new CoreException("unexpected sample size(" + bytes.length + ")");
        }

    }

    private static byte[] toByteArray(int value, int sample_size) {
        byte[] b_buffer = new byte[sample_size];
        int[] i_buffer = new int[sample_size];
        switch (sample_size) {
            case 2:
                value = value < 0 ? value + 256 * 256 : value;
                i_buffer[0] = value % 256;
                i_buffer[1] = value / 256;
                b_buffer[0] = (byte) (i_buffer[0] >= 128 ? i_buffer[0] - 256 : i_buffer[0]);
                b_buffer[1] = (byte) (i_buffer[1] >= 128 ? i_buffer[1] - 256 : i_buffer[1]);
                break;
            case 3:
                value = value < 0 ? value + 256 * 256 * 256 : value;
                i_buffer[0] = value % 256;
                i_buffer[1] = (value / 256) % 256;
                i_buffer[2] = value / 256 / 256;
                b_buffer[0] = (byte) (i_buffer[0] >= 128 ? i_buffer[0] - 256 : i_buffer[0]);
                b_buffer[1] = (byte) (i_buffer[1] >= 128 ? i_buffer[1] - 256 : i_buffer[1]);
                b_buffer[2] = (byte) (i_buffer[2] >= 128 ? i_buffer[2] - 256 : i_buffer[2]);
                break;
            default:
                throw new CoreException("unexpected sample size(" + sample_size + ")");
        }
        return b_buffer;

    }

    public byte[] getAllZeroByteArray() {
        byte[] ret = new byte[channel * sample_size];
        Arrays.fill(ret, (byte) 0);
        return ret;
    }

    public byte[] getByteArray() {
        byte[] ret = new byte[channel * sample_size];
        byte[] buffer = new byte[sample_size];
        for (int i = 0; i < channel; i++) {
            buffer = toByteArray(value.get(i), sample_size);
            for (int j = 0; j < sample_size; j++) {
                ret[i * sample_size + j] = buffer[j];
            }
        }
        return ret;
    }

    public byte[] getMultipliedByteArray(double[] rate) {
        byte[] ret = new byte[channel * sample_size];
        byte[] buffer = new byte[sample_size];
        for (int i = 0; i < channel; i++) {
            buffer = toByteArray((int) ((double) value.get(i) * rate[i]), sample_size);
            for (int j = 0; j < sample_size; j++) {
                ret[i * sample_size + j] = buffer[j];
            }
        }
        return ret;
    }
    /*
    byte[] b = new byte[]{12, 24, 19, 17};
float f =  ByteBuffer.wrap(b).getFloat();
float -> byte[]

Reverse operation (knowing the result of above):

float f =  1.1715392E-31f;
byte[] b = ByteBuffer.allocate(4).putFloat(f).array();  //[12, 24, 19, 17]
    
     */
}
