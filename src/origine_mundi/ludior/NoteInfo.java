/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.ludior;

import origine_mundi.MidiByte;

/**
 *
 * @author user
 */
public class NoteInfo {

    private MidiByte note;
    private MidiByte velocity;
    private double offset_on;
    private double offset_off;

    public NoteInfo(MidiByte note, MidiByte velocity, double offset_on, double offset_off) {
        this.note = note;
        this.velocity = velocity;
        this.offset_on = offset_on;
        this.offset_off = offset_off;
    }

    public MidiByte getNote() {
        return note;
    }

    public MidiByte getVelocity() {
        return velocity;
    }

    public double getOffsetOn() {
        return offset_on;
    }

    public double getOffsetOff() {
        return offset_off;
    }
}
