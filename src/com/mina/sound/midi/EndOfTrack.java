package com.mina.sound.midi;

import java.util.ArrayList;
import java.util.HashMap;
import javax.sound.midi.*;
import origine_mundi.MidiMachine;

public class EndOfTrack extends Thread implements MetaEventListener {

    private final Sequencer sequencer;
    //private final ArrayList<Receiver> receivers;
    private final HashMap<Integer, MidiMachine> machines;
    private boolean is_completed;

    /**
     * sequencer NOT_NULL
     *
     * @param sequencer
     * @param receivers
     */
    public EndOfTrack(Sequencer sequencer, HashMap<Integer, MidiMachine> machines) {
        this.sequencer = sequencer;
        this.machines = machines;
        is_completed = false;
        //this.receivers = receivers;
    }

    @Override
    public void meta(MetaMessage event) {
        if (event.getType() == 0x2f) {
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("EndOfTrack could not sleep");
            }
            sequencer.close();
            for(Integer id:machines.keySet()){
                machines.get(id).finalize();
            }
            is_completed = true;
            /*receivers.stream().forEach((receiver) -> {
                receiver.close();
            });*/
        }
    }
    public boolean isCompleted(){
        return is_completed;
    }
}
