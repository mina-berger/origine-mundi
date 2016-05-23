/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import java.util.ArrayList;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Track;

/**
 *
 * @author hiyamamina
 */
public class Metronome implements MetaEventListener {

    private Sequencer sequencer;
    private float bpm;
    private boolean stop;
    private ArrayList<Long> time = new ArrayList<>();

    public void stop() {
        stop = true;
    }

    public void start(float bpm) {
        try {
            this.bpm = bpm;
            stop = false;
            openSequencer();
            Sequence seq = createSequence();
            startSequence(seq);
        } catch (InvalidMidiDataException | MidiUnavailableException | InterruptedException ex) {
            throw new CoreException(ex);
        }
    }

    private void openSequencer() throws MidiUnavailableException {
        sequencer = MidiSystem.getSequencer();
        sequencer.open();
        sequencer.addMetaEventListener(this);
    }

    private Sequence createSequence() {
        try {
            Sequence seq = new Sequence(Sequence.PPQ, 1);
            Track track = seq.createTrack();

            ShortMessage msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0);
            MidiEvent evt = new MidiEvent(msg, 0);
            track.add(evt);

            addNoteEvent(track, 0, true);
            addNoteEvent(track, 1, false);
            addNoteEvent(track, 2, false);
            addNoteEvent(track, 3, false);

            msg = new ShortMessage(ShortMessage.PROGRAM_CHANGE, 9, 1, 0);
            evt = new MidiEvent(msg, 4);
            track.add(evt);
            return seq;
        } catch (InvalidMidiDataException ex) {
            throw new CoreException(ex);
        }
    }

    private void addNoteEvent(Track track, long tick, boolean head) throws InvalidMidiDataException {
        ShortMessage message = new ShortMessage(ShortMessage.NOTE_ON, 9, head ? 42 : 50, 100);
        MidiEvent event = new MidiEvent(message, tick);
        track.add(event);
    }

    private void startSequence(Sequence seq) throws InvalidMidiDataException, InterruptedException {
        sequencer.setSequence(seq);
        sequencer.setTempoInBPM(bpm);
        Thread.sleep(1000);
        //time.add(System.currentTimeMillis());
        sequencer.start();
    }

    //public ArrayList<Long> getTimeHistory() {
    //    return time;
    //}

    public static void main(String[] args) throws Exception, Throwable {
        int bpm = 240;
        Metronome metronome = new Metronome();
        metronome.start(bpm);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            System.out.println((i + 1) + "sec");
        }
        metronome.stop();
        metronome.start(120);
        for (int i = 0; i < 5; i++) {
            Thread.sleep(1000);
            System.out.println((i + 1) + "sec");
        }
        metronome.stop();
        //for (long time : metronome.getTimeHistory()) {
        //    System.out.println(time);
        //}

    }

    //@Override
    @Override
    public void meta(MetaMessage message) {
        if (message.getType() != 47) {  // 47 is end of track
            return;
        }
        if (stop) {
            sequencer.stop();
            for (Thread t : Thread.getAllStackTraces().keySet()) {
                //System.out.println(t.getName());
                if (t.getName().contains("Java Sound Sequencer")) {
                    t.stop();
                }
            }
            System.out.println("stopped");
            return;
        }

        doLoop();
    }

    private void doLoop() {
        if (sequencer == null || !sequencer.isOpen()) {
            return;
        }
        sequencer.setTickPosition(0);
        //time.add(System.currentTimeMillis());
        sequencer.start();
        sequencer.setTempoInBPM(bpm);
    }
}
