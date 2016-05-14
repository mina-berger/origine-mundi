/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.ludior;

import java.util.ArrayList;
import java.util.Arrays;
import origine_mundi.Midi2Bytes;
import origine_mundi.MidiByte;
import origine_mundi.ludior.Expression.SettingHolder;

/**
 *
 * @author user
 */
public class Expression extends ArrayList<SettingHolder> {
    public Expression(SettingHolder... settings){
        addAll(Arrays.asList(settings));
    }
    public static abstract class SettingHolder {
        double temp_ab;
        double temp_ad;
        boolean initial;
        public SettingHolder(double temp_ab, double temp_ad, boolean initial){
            this.temp_ab = temp_ab;
            this.temp_ad = temp_ad;
            this.initial = initial;
        }
        public boolean isInitial(){
            return initial;
        }

        public double getTempAb() {
            return temp_ab;
        }

        public double getTempAd() {
            return temp_ad;
        }
        public abstract int size();
        public abstract int getValue(int index);
        public double getOffset(int index) {
            return temp_ab + (temp_ad - temp_ab) * (double)index / (double)size();
        }
        
    }
    public static class Control extends SettingHolder {
        private final int control;
        private final MidiByte value_ab;
        private final MidiByte value_ad;
        public Control(int control, int value){
            this(control, value, value, 0, 0, true);
        }
        public Control(int control, int value_ab, int value_ad, double temp_ab, double temp_ad){
            this(control, value_ab, value_ad, temp_ab, temp_ad, false);
        }
        private Control(int control, int value_ab, int value_ad, double temp_ab, double temp_ad, boolean initial){
            super(temp_ab, temp_ad, initial);
            this.control = control;
            this.value_ab = new MidiByte(value_ab);
            this.value_ad = new MidiByte(value_ad);
        }

        public int getControl() {
            return control;
        }

        public MidiByte getValueAb() {
            return value_ab;
        }

        public MidiByte getValueAd() {
            return value_ad;
        }

        @Override
        public int size() {
            return Math.abs(value_ad.intValue() - value_ab.intValue()) + 1;
        }

        @Override
        public int getValue(int index) {
            return (int)(value_ab.doubleValue() + (value_ad.doubleValue() - value_ab.doubleValue()) * (double)index / (double)size());
        }

    }
    public static class Command extends SettingHolder {
        int command;
        Midi2Bytes value_ab;
        Midi2Bytes value_ad;
        public Command(int command, int value){
            this(command, value, value, 0, 0, true);
        }
        public Command(int command, int value_ab, int value_ad, double temp_ab, double temp_ad){
            this(command, value_ab, value_ad, temp_ab, temp_ad, false);
        }
        public Command(int command, int value_ab, int value_ad, double temp_ab, double temp_ad, boolean initial){
            super(temp_ab, temp_ad, initial);
            this.command = command;
            this.value_ab = new Midi2Bytes(value_ab);
            this.value_ad = new Midi2Bytes(value_ad);
        }
        public int getCommand(){
            return command;
        }

        @Override
        public int size() {
            return Math.abs(value_ad.intValue() - value_ab.intValue()) + 1;
        }
        @Override
        public int getValue(int index) {
            return (int)(value_ab.doubleValue() + (value_ad.doubleValue() - value_ab.doubleValue()) * (double)index / (double)size());
        }

    }
    
}
