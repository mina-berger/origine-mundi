/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package la.clamor.opus;

import la.clamor.opus.ConstantiaOperis.Causa.Type;

/**
 *
 * @author minae.hiyamae
 */
public interface ConstantiaOperis {
    public static final double DS = 0.25;
    public static final double DE = 0.5;
    public static final double DQ = 1;
    public static final double DH = 2;
    public static final double DW = 4;
    public enum V{RAND, AEST, UNUS};
    public static final Double NIL = null; 
    
    public static double D(double corpus, int dots){
        if(dots <= 0){
            return corpus;
        }
        return corpus + D(corpus / 2d, dots - 1);
    }
    //public static double O3(double clavis){
    //    return O(clavis, -1);
   // }
    public static double O(double clavis, int octave){
        return clavis + (double)octave * 12d;
    }
    public static void main(String[] args){
        System.out.println(D(0.5, 2));
    }
    public static class Causa {
        public enum Type{DIUTURNITAS, PROPORTIO, CLAVIS, ID, TALEA, LUDE, REST};
        Type type;
        Integer id;
        Integer talea;
        Double repenso;
        Double diuturnitas;
        Double proportio;
        Double clavis;
        double[] velocitatis;
        
        public Causa(Type type){
            this.type = type;
            id = null;
            talea = null;
            repenso = null;
            diuturnitas = null;
            proportio = null;
            clavis = null;
            velocitatis = null;
        }
        public Type capioType(){
            return type;
        }
        public int capioId(){
            return id;
        }
        public boolean habeoRepenso(){
            return repenso != null;
        }
        public double capioRepenso(){
            return repenso;
        }
        public int capioTaleam(){
            return talea;
        }
        public boolean habeoDiuturnitas(){
            return diuturnitas != null;
        }
        public double capioDiuturnitas(){
            return diuturnitas;
        }
        public double capioPropotio(){
            return proportio;
        }
        public double capioClavim(){
            return clavis;
        }
        
        public boolean habeoVeclocitatem(){
            return velocitatis != null;
        }
        public double[] capioVelocitatem(){
            return velocitatis;
        }
    }
    public static Causa CD(double diuturnitas){
        Causa c = new Causa(Causa.Type.DIUTURNITAS);
        c.diuturnitas = diuturnitas;
        return c;
        
    }
    public static Causa CP(double proportio){
        Causa c = new Causa(Causa.Type.PROPORTIO);
        c.proportio = proportio;
        return c;
    }
    public static Causa CC(double clavis){
        Causa c = new Causa(Causa.Type.CLAVIS);
        c.clavis = clavis;
        return c;
    }
    public static Causa CC(double clavis, double... velocitatis){
        Causa c = new Causa(Causa.Type.CLAVIS);
        c.clavis = clavis;
        c.velocitatis = velocitatis;
        return c;
    }
    public static Causa C2(double clavis, double... velocitatis){
        return CC(clavis - 24d, velocitatis);
    }
    public static Causa C3(double clavis, double... velocitatis){
        return CC(clavis - 12d, velocitatis);
    }
    public static Causa C4(double clavis, double... velocitatis){
        return CC(clavis, velocitatis);
    }
    public static Causa C5(double clavis, double... velocitatis){
        return CC(clavis + 12d, velocitatis);
    }
    public static Causa C6(double clavis, double... velocitatis){
        return CC(clavis - 24d, velocitatis);
    }
    public static Causa CT(int talea, double repenso){
        Causa c = new Causa(Causa.Type.TALEA);
        c.talea = talea;
        c.repenso = repenso;
        return c;
    }
    public static Causa CT(int talea){
        Causa c = new Causa(Causa.Type.TALEA);
        c.talea = talea;
        c.repenso = 0d;
        return c;
    }
    public static Causa CI(int id){
        Causa c = new Causa(Causa.Type.ID);
        c.id = id;
        return c;
    }
    public static Causa CL(double... velocitatis){
        Causa c = new Causa(Causa.Type.LUDE);
        c.velocitatis = velocitatis;
        return c;
    }
    public static Causa CR(double diuturnitas){
        Causa c = new Causa(Causa.Type.REST);
        c.diuturnitas = diuturnitas;
        return c;
    }
    public static int compare(Causa c1, Causa c2){
        if(c1.capioType() != Type.TALEA || c2.capioType() != Type.TALEA){
            throw new IllegalArgumentException("cannot compare sinon type TALEA sit.");
        }
        int compare = Integer.compare(c1.talea, c2.talea);
        if(compare != 0){
            return compare;
        }
        return Double.compare(c1.repenso, c2.repenso);
        
    }
}
