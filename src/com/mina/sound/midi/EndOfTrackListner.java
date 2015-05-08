package com.mina.sound.midi;

import javax.sound.midi.*;
import origine_mundi.MidiMachines;

public class EndOfTrackListner extends Thread implements MetaEventListener {

    private final Sequencer sequencer;
    //private final ArrayList<Receiver> receivers;
    private final MidiMachines machines;
    private boolean is_completed;
    private boolean is_eot;

    /**
     * sequencer NOT_NULL
     *
     * @param sequencer
     * @param machines
     */
    public EndOfTrackListner(Sequencer sequencer, MidiMachines machines) {
        this.sequencer = sequencer;
        this.machines = machines;
        is_completed = false;
        is_eot = false;
        //this.receivers = receivers;
    }

    @Override
    public void meta(MetaMessage event) {
        if (event.getType() == 0x2f) {
            is_eot = true;
            try {
                sleep(1000);
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
    public boolean isEndOfTrack(){
        return is_eot;
    }
}
