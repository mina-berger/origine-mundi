/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mina.util;

import com.mina.util.TextBox.MultiColumnDatum;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author mina
 */
public class Yyyymmddhhmiss extends TextUtil implements Comparable<Yyyymmddhhmiss>, MultiColumnDatum {

    private Yyyymmdd _yyyymmdd;
    private Hhmiss _hhmiss;

    public Yyyymmddhhmiss(Yyyymmddhhmiss _yyyymmddhhmiss) {
        set(_yyyymmddhhmiss);
    }

    public Yyyymmddhhmiss(String yyyymmddhhmiss) {
        set(yyyymmddhhmiss);
    }

    public Yyyymmddhhmiss(Timestamp ts) {
        set(toString(ts));
    }

    public Yyyymmddhhmiss(Calendar c) {
        set(toString(c));
    }

    public final void set(Yyyymmddhhmiss _yyyymmddhhmiss) {
        this._yyyymmdd = new Yyyymmdd(_yyyymmddhhmiss._yyyymmdd);
        this._hhmiss = new Hhmiss(_yyyymmddhhmiss._hhmiss);
    }

    public final void set(String yyyymmddhhmiss) {
        this._yyyymmdd = new Yyyymmdd(yyyymmddhhmiss.substring(0, 8));
        this._hhmiss = new Hhmiss(yyyymmddhhmiss.substring(8));
        if (!check()) {
            throw new CoreException("illegal argument(" + yyyymmddhhmiss + " for Yyyymmddhhmiss)");
        }
    }

    public void set(Timestamp ts) {
        set(toString(ts));
    }

    public void set(Calendar c) {
        set(toString(c));
    }

    public boolean before(Yyyymmddhhmiss _yyyymmddhhmiss) {
        return toCalendar().before(_yyyymmddhhmiss.toCalendar());
    }

    public boolean after(Yyyymmddhhmiss _yyyymmddhhmiss) {
        return toCalendar().after(_yyyymmddhhmiss.toCalendar());
    }

    public boolean check() {
        return check(toString());
    }

    public static boolean check(String yyyymmddhhmiss) {
        if (yyyymmddhhmiss.length() != 14) {
            return false;
        }
        return Yyyymmdd.check(yyyymmddhhmiss.substring(0, 8))
                && Hhmiss.check(yyyymmddhhmiss.substring(8));
    }

    public String getYyyy() {
        return _yyyymmdd.getYyyy();
    }

    public String getMm() {
        return _yyyymmdd.getMm();
    }

    public String getDd() {
        return _yyyymmdd.getDd();
    }

    public String getHh() {
        return _hhmiss.getHh();
    }

    public String getMi() {
        return _hhmiss.getMi();
    }

    public String getSs() {
        return _hhmiss.getSs();
    }

    @Override
    public int compareTo(Yyyymmddhhmiss timestamp) {
        return new Integer(hashCode()).compareTo(timestamp.hashCode());
    }

    @Override
    public boolean equals(Object o) {
        return hashCode() == o.hashCode();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this._yyyymmdd != null ? this._yyyymmdd.hashCode() : 0);
        hash = 29 * hash + (this._hhmiss != null ? this._hhmiss.hashCode() : 0);
        return hash;
    }

    public Calendar toCalendar() {
        return toCalendar(toString());
    }

    @Override
    public String toString() {
        return _yyyymmdd.toString() + _hhmiss.toString();
    }

    public String dispString() {
        return _yyyymmdd.dispString() + _hhmiss.dispString();
    }

    /*public Yyyymmdd shift(int shift_dates){
        Calendar c = toCalendar(yyyymmdd);
        c.add(Calendar.DATE, shift_dates);
        return new Yyyymmdd(c);
    }
    public Yyyymmdd[] getYyyymmdds(int day_count){
        if(day_count == 0) throw new CoreException("day_count cannot be 0.");
        int count = Math.abs(day_count);
        boolean positive = (day_count > 0);
        Yyyymmdd[] yyyymmdds = new Yyyymmdd[count];
        Calendar c = toCalendar(yyyymmdd);
        for(int i = 0;i < count;i++){
            if(i > 0) c.add(Calendar.DATE, (positive?1:-1));
            yyyymmdds[i] = new Yyyymmdd(c);
        }
        return yyyymmdds;
    }*/
    public static Yyyymmddhhmiss now() {
        return new Yyyymmddhhmiss(Calendar.getInstance());
    }

    private static String toString(Timestamp timestamp) {
        Calendar c = Calendar.getInstance();
        c.setTime(timestamp);
        return toString(c);
    }

    private static String toString(Calendar c) {
        return TextUtil.fill(Integer.toString(c.get(Calendar.YEAR)), 4, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.MONTH) + 1), 2, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.DATE)), 2, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.HOUR_OF_DAY)), 2, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.MINUTE)), 2, '0', false)
                + TextUtil.fill(Integer.toString(c.get(Calendar.SECOND)), 2, '0', false);
    }

    private static Calendar toCalendar(String yyyymmddhhmiss) {
        String y = yyyymmddhhmiss.substring(0, 4);
        String m = yyyymmddhhmiss.substring(4, 6);
        String d = yyyymmddhhmiss.substring(6, 8);
        String h = yyyymmddhhmiss.substring(8, 10);
        String i = yyyymmddhhmiss.substring(10, 12);
        String s = yyyymmddhhmiss.substring(12);
        return new GregorianCalendar(
                Integer.parseInt(y, 10),
                Integer.parseInt(m, 10) - 1,
                Integer.parseInt(d, 10),
                Integer.parseInt(h, 10),
                Integer.parseInt(i, 10),
                Integer.parseInt(s, 10));
    }

    @Override
    public String getHeader(int h) {
        return "Timestamp";
    }

    @Override
    public StringSquare getValue(int h) {
        return new StringSquare(dispString());
    }

    /* public boolean alignLeft(int h) {
        return true;
    }*/

    /**
     *
     * @return
     */

    @Override
    public int hLength() {
        return 1;
    }
}
