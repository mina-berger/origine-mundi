/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.mina.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author mina
 */
public class Yyyymm extends TextUtil implements Comparable<Yyyymm> {
    private String yyyymm;
    public Yyyymm(Yyyymm _yyyymm){
        set(_yyyymm);
    }
    public Yyyymm(String yyyymm){
        set(yyyymm);
    }
    public Yyyymm (Yyyymmdd yyyymmdd){
        set(yyyymmdd.getYyyy() + yyyymmdd.getMm());
    }
    public Yyyymm (Calendar c){
        set(toString(c));
    }
    public final void set(Yyyymm _yyyymm){
        set(_yyyymm.yyyymm);
    }
    public final void set(String yyyymm){
        this.yyyymm = yyyymm;
        if(!check()) throw new CoreException("illegal argument(" + yyyymm + " for Yyyymm)");
    }
    public boolean before(Yyyymm _yyyymm){
        return toCalendar().before(_yyyymm.toCalendar());
    }
    public boolean after(Yyyymmdd _yyyymm){
        return toCalendar().after(_yyyymm.toCalendar());
    }
    public Yyyymmdd getFirstYyyymmdd(){
        return new Yyyymmdd(yyyymm + "01");
    }
    public Yyyymmdd getLastYyyymmdd(){
        Calendar c = toCalendar();
        c.add(Calendar.MONTH, 1);
        c.add(Calendar.DATE, -1);
        return new Yyyymmdd(c);
    }
    public Yyyymm getNext(){return getShifted(1);}
    public Yyyymm getPrev(){return getShifted(-1);}
    public Yyyymm getShifted(int shift_month){
        Calendar c = toCalendar();
        c.add(Calendar.MONTH, shift_month);
        return new Yyyymm(c);
    }

    public boolean check(){
        return check(yyyymm);
        //if(yyyymmdd.length() != 8) return false;
        //return yyyymmdd.equals(toString(toCalendar(yyyymmdd)));
    }
    public static boolean check(String yyyymm){
        if(yyyymm.length() != 6) return false;
        return yyyymm.equals(toString(toCalendar(yyyymm)));
    }
    public String getYyyy(){return yyyymm.substring(0, 4);}
    public String getMm(){return yyyymm.substring(4, 6);}
    public String getDd(){return yyyymm.substring(6, 8);}

    public Calendar toCalendar(){
        return toCalendar(yyyymm);
    }
    @Override
    public String toString(){
        return yyyymm;
    }
    public String dispString(){
        return dispYM(yyyymm);
    }
    private static String toString(Calendar c){
        return TextUtil.fill(Integer.toString(c.get(Calendar.YEAR)),      4, '0', false) +
               TextUtil.fill(Integer.toString(c.get(Calendar.MONTH) + 1), 2, '0', false);
    }
    private static Calendar toCalendar(String yyyymmdd){
        String y = yyyymmdd.substring(0, 4);
        String m = yyyymmdd.substring(4, 6);
        return new GregorianCalendar(
                Integer.parseInt(y, 10),
                Integer.parseInt(m, 10) - 1,
                1);
    }
    public static Yyyymm now(){
        return new Yyyymm(Calendar.getInstance());
    }
    private static String dispYM(String yyyymm){
        String y = yyyymm.substring(0, 4);
        String m = yyyymm.substring(4, 6);
        if(m.startsWith("0")) m = " " + m.substring(1);
        return y + "/" + m + "";
    }
    @Override
    public int compareTo(Yyyymm o) {
        return new Integer(hashCode()).compareTo(o.hashCode());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.yyyymm != null ? this.yyyymm.hashCode() : 0);
        return hash;
    }
    @Override
    public boolean equals(Object o){
        return hashCode() == o.hashCode();
    }

}
