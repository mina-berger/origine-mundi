/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi.ludior;

import origine_mundi.MidiByte;

/**
 *
 * @author Mina
 */
public class Brev {
    private final int track;
    private final int device;
    private final int command;
    private final int channel;
    private final MidiByte data1;
    private final MidiByte data2;
    private final int talea;
    private final double beat;
    public Brev(int track, int device, int command, int channel, MidiByte data1, MidiByte data2, double beat){
        this(track, device, command, channel, data1, data2, 0, beat);
    }
    public Brev(int track, int device, int command, int channel, MidiByte data1, MidiByte data2, int talea, double beat){
        this.track = track;
        this.device = device;
        this.command = command;
        this.channel = channel;
        this.data1 = data1;
        this.data2 = data2;
        this.talea = talea;
        this.beat = beat;
    }
    //public Brev shift(int talea, double beat){
    //    return new Brev(track, device, command, channel, data1, data2, this.talea + talea, this.beat + beat);
    //}
    public int getTrack() {
        return track;
    }
    public int getDevice() {
        return device;
    }
    public int getCommand() {
        return command;
    }

    public int getChannel() {
        return channel;
    }

    public MidiByte getData1() {
        return data1;
    }

    public MidiByte getData2() {
        return data2;
    }
    public int getTalea() {
        return talea;
    }
    public double getBeat() {
        return beat;
    }
}
