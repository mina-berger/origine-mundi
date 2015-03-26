/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package origine_mundi;

/**
 *
 * @author Mina
 */
public class OmBrev {
    private final int track;
    private final int device;
    private final int command;
    private final int channel;
    private final int data1;
    private final int data2;
    private final double beat;
    public OmBrev(int track, int device, int command, int channel, int data1, int data2, double beat){
        this.track = track;
        this.device = device;
        this.command = command;
        this.channel = channel;
        this.data1 = data1;
        this.data2 = data2;
        this.beat = beat;
    }
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

    public int getData1() {
        return data1;
    }

    public int getData2() {
        return data2;
    }

    public double getBeat() {
        return beat;
    }
}
