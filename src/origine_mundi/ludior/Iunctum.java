/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.ludior;

/**
 *
 * @author user
 */
public class Iunctum {
    private final int track;
    private final int device;
    private final int channel;
    public Iunctum(int track, int device, int channel){
        this.track = track;
        this.device = device;
        this.channel = channel;
    }

    public int getTrack() {
        return track;
    }

    public int getDevice() {
        return device;
    }

    public int getChannel() {
        return channel;
    }
    
    
}
