/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.ludior;

import java.util.ArrayList;
import origine_mundi.Integers;
import origine_mundi.MidiByte;

/**
 *
 * @author user
 */
public class ChordStroke {
    private final double vel_rate_ab;
    private final double vel_rate_ad;
    private final double beat_diff_ab;
    private final double beat_diff_ad;
    private final boolean pickup;
    public ChordStroke(double vel_rate_ab, double vel_rate_ad, double beat_diff_ab, double beat_diff_ad, boolean pickup){
        this.vel_rate_ab = vel_rate_ab;
        this.vel_rate_ad = vel_rate_ad;
        this.beat_diff_ab = beat_diff_ab;
        this.beat_diff_ad = beat_diff_ad;
        this.pickup = pickup;
    }
    public ArrayList<NoteInfo> getNotes(Integers notes, int velocity, double offset_off){
        ArrayList<NoteInfo> note_infos = new ArrayList<>();
        int size = notes.size();
        double offset_on = pickup?(beat_diff_ab + (beat_diff_ad - beat_diff_ab) / 2) * size * -1:0;
        for(int i = 0;i < size;i++){
            int note_velocity = (int)((double)velocity * (vel_rate_ab + (vel_rate_ad - vel_rate_ab) / (double)(size - 1) * i));
            offset_on += beat_diff_ab + (beat_diff_ad - beat_diff_ad) / (double)(size - 1) * i;
            note_infos.add(new NoteInfo(new MidiByte(notes.get(i)), new MidiByte(note_velocity), offset_on, offset_off));
        }
        return note_infos;
        
    }
}
