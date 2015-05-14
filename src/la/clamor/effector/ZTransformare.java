package la.clamor.effector;

import la.clamor.Legibilis;
import la.clamor.Punctum;
//import la.clamor.Tonsus;




public class ZTransformare extends AbstractEffector {
    private Modulus modulus;
    private Terminatum terminatum;
    OrbisAestimationis in;
    OrbisAestimationis ex;
    
    
    public ZTransformare(Modulus modulus) {
        super(null);
        this.modulus = modulus;
        in = null;
        ex = null;
        terminatum = null;
        initializeOrbes();
    }
    
    public void ponoModula(Modulus modulus){
        this.modulus = modulus;
        initializeOrbes();
    }
    private void initializeOrbes(){
        int longitudo = modulus.longitudo();
        longitudo++;
        if(in == null){
            in = new OrbisAestimationis(longitudo);
        }else{
            in.mutoLongitudo(longitudo);
        }
        if(ex == null){
            ex = new OrbisAestimationis(longitudo);
        }else{
            ex.mutoLongitudo(longitudo);
        }
    }
    
    @Override
    public void ponoFons(Legibilis fons) {
        super.ponoFons(fons);
        terminatum = null;
    }
    
    @Override
    public Punctum lego() {
        Punctum lectum;
        if(fonsParatusEst()){
            lectum = legoAFontem();
        }else{
            lectum = new Punctum();
            if(terminatum == null){
                terminatum = new Terminatum(in.longitudo());
            }else if(terminatum.paratusSum()){
                terminatum.ambulo();
            }else{
                return lectum;
            }
        }
        Punctum punctum = modulus.capio(lectum, in, ex);
        in.pono(lectum);
        ex.pono(punctum);
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        return terminatum == null || terminatum.paratusSum();
    }
    
    public static class Terminatum {
        private int numera;
        public Terminatum(int numera){
            this.numera = numera;
        }
        public void ambulo(){
            numera--;
        }
        public boolean paratusSum(){
            return numera > 0;
        }
    }

    /*public static void main(String[] args){
        
        Legibilis fons = new Tonsus(0, 0, 0, 0, 0, 5, 5, 5, 5, 5, 5, 5, 5, 0, 0, 0, 0, 0, 0, 0);
        Modulus mod = new ModAddens(
                new ModCapiens(new Fraction(1d / 1.9), Fons.EX, 0), 
                new ModCapiens(new Fraction(0.9 / 1.9)));
        System.out.println(mod.toString());
        ZTransformare zt = new ZTransformare(mod);
        zt.ponoFons(fons);
        while(zt.paratusSum()){
            //System.out.println("punctum:" + i);
            Punctum punctum = zt.lego();
            System.out.println(punctum.toString());
        }
    }*/
}
