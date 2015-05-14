package la.clamor.effector;

import java.io.File;
import java.util.ArrayList;
import java.util.TreeMap;

import la.clamor.Constantia.Fons;
import la.clamor.ExceptioClamoris;
import la.clamor.Punctum;
import la.clamor.effector.Tabula.Collocatio;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.fraction.Fraction;
import org.apache.commons.math3.util.FastMath;

public abstract class Modulus {
    public abstract Punctum capio(Punctum lectum, OrbisAestimationis in, OrbisAestimationis ex);
    public abstract int longitudo();
    public static class ModCapiens extends Modulus {
        private Fons fons;
        private int mutati;
        private Number factor;
        public ModCapiens(Number factor) {
            this(factor, null, 0);
        }
        public ModCapiens(Number factor, Fons fons, int mutati) {
            this.fons = fons;
            this.mutati = mutati;
            this.factor = factor;
            if(mutati < 0){
                throw new ExceptioClamoris(String.format("non possum creare Modulus(%s, %d)", fons, mutati));
            }
        }
        @Override
        public Punctum capio(Punctum lectum, OrbisAestimationis in, OrbisAestimationis ex) {
            Punctum modulum = fons == null?lectum:(fons == Fons.IN?in:ex).capio(mutati);
            return modulum.multiplico(factor.doubleValue());
        }
        @Override
        public int longitudo() {
            return mutati;
        }
        @Override
        public String toString(){
            if(factor.equals(0)){
                return "0";
            }
            String fraction_str;
            //try{
            //    fraction_str = new Fraction(-factor.doubleValue()).toString();
            //}catch(FractionConversionException e){
                fraction_str = Double.toString(-factor.doubleValue());
            //}
            
            String factor_str = factor.equals(1)?"":
                (factor.doubleValue() > 0?"+ " + factor.toString():"- " + fraction_str) + " * ";
            if(fons == null){
                return factor_str + "x";
            }
            return factor_str + (fons == Fons.IN?"x":"y") + "[" + mutati + "]";
        }
    }
    
    public static class ModAddens extends Modulus {
        private ModCapiens[] moduli;
        public ModAddens(ModCapiens... moduli){
            this.moduli = moduli;
        }
        @Override
        public Punctum capio(Punctum lectum, OrbisAestimationis in, OrbisAestimationis ex) {
            Punctum punctum = null;
            for(Modulus modulus:moduli){
                punctum = punctum == null?modulus.capio(lectum, in, ex):punctum.addo(modulus.capio(lectum, in, ex));
            }
            return punctum;
        }
        @Override
        public int longitudo() {
            int longitudo = 0;
            for(Modulus modulus:moduli){
                longitudo = Math.max(longitudo, modulus.longitudo());
            }            
            return longitudo;
        }
        public void addoModulum(ModCapiens modulus){
            moduli = ArrayUtils.add(moduli, modulus);
        }
        @Override
        public String toString(){
            String ret = "";
            for(ModCapiens mod:moduli){
                if(mod.factor.doubleValue() == 0){
                    continue;
                }
                if(!ret.isEmpty()){
                   ret += " ";
                }
                ret += mod.toString();
            }
            return ret;
        }
    }
    public static class DuobusRerumZeta {
        private  ResZeta[] numer_res;
        private  ResZeta[] denom_res;
        public DuobusRerumZeta(ResZeta[] numer_res){
            this(numer_res, new ResZeta[]{new ResZeta(1)});
        }
        
        public DuobusRerumZeta(ResZeta[] numer_res, ResZeta[] denom_res){
            this.numer_res = sort(numer_res);
            this.denom_res = sort(denom_res);
            if(denom_res[0].getMutati() != 0){
                throw new IllegalArgumentException("denom_res is required to have mutati-0 res");
            }
        }
        private static ResZeta[] sort(ResZeta[] res_array){
            TreeMap<Integer, ResZeta> map = new TreeMap<>();
            for(ResZeta res:res_array){
                int key = -res.getMutati();
                if(map.containsKey(key)){
                    ResZeta prev = map.get(key);
                    prev.setFactor(new Fraction(prev.getFactor().doubleValue() + res.getFactor().doubleValue()));
                }else{
                    map.put(key, res);
                }
            }
            return map.values().toArray(new ResZeta[]{});
        }
        public ResZeta capioRemNumer(int index){
            return numer_res[index];
        }
        public ResZeta capioRemDenom(int index){
            return denom_res[index];
        }
        public int longitudoNumer(){
            return numer_res.length;
        }
        public int longitudoDenom(){
            return denom_res.length;
        }
        @Override
        public String toString(){
            boolean no_denom = denom_res.length == 1 && denom_res[0].getFactor().equals(1);
            String numer_str = "";
            for(ResZeta res:numer_res){
                if(!numer_str.isEmpty()){
                    numer_str += " ";
                }
                numer_str += res.toString();
            }
            if(no_denom){
                return numer_str;
            }
            if(numer_res.length > 1){
                numer_str = "(" + numer_str + ")";
            }
            String denom_str = "";
            for(ResZeta res:denom_res){
                if(!denom_str.isEmpty()){
                    denom_str += " ";
                }
                denom_str += res.toString();
            }
            if(denom_res.length > 1){
                denom_str = "(" + denom_str + ")";
            }
            return numer_str + " / " + denom_str;
        }
    }
    public static class ResZeta {
        private Number factor;
        private int mutati;
        public ResZeta(Number factor){
            this(factor, 0);
        }
        public ResZeta(Number factor, int mutati){
            if(mutati > 0){
                throw new IllegalArgumentException(String.format("mutati debet negativus.(mutati=%d)", mutati));
            }
            this.setFactor(factor);
            this.setMutati(mutati);
        }
        public Number getFactor() {
            return factor;
        }
        public final void setFactor(Number factor) {
            this.factor = factor;
        }
        public int getMutati() {
            return mutati;
        }
        public final void setMutati(int mutati) {
            this.mutati = mutati;
        }
        public Complex computo(Complex zeta){
            return zeta.pow(mutati).multiply(factor.doubleValue());
        }
        @Override
        public String toString(){
            if(factor.equals(0)){
                return "0";
            }else if(factor.equals(1)){
                return mutati == 0?"+ 1":"+ z^(" + mutati + ")";
            }else if(factor.equals(-1)){
                return mutati == 0?"- 1":"- z^(" + mutati + ")";
            }if(factor.doubleValue() > 0){
                return "+ " + factor.toString() + (mutati == 0?"":" * z^(" + mutati + ")");
            }else{
                return "- " + new Fraction(-factor.doubleValue()).toString() + (mutati == 0?"":" * z^(" + mutati + ")");
            }
        }
    }
    public static Modulus convert(DuobusRerumZeta duobus){
        double partitor = duobus.capioRemDenom(0).getFactor().doubleValue();
        ModAddens mod = new ModAddens();
        for(int i = 1;i < duobus.longitudoDenom();i++){
            ResZeta res = duobus.capioRemDenom(i);
            Fraction factor = new Fraction(-res.getFactor().doubleValue());
            mod.addoModulum(new ModCapiens(factor, Fons.EX, (-res.getMutati()) - 1));
        }
        for(int i = 0;i < duobus.longitudoNumer();i++){
            ResZeta res = duobus.capioRemNumer(i);
            Number factor;
            //try{
            //    factor = new Fraction(res.getFactor().doubleValue() / partitor);
            //}catch(FractionConversionException e){
                factor = res.getFactor().doubleValue() / partitor;
            //}
            mod.addoModulum(
                    res.getMutati() == 0?new ModCapiens(factor):new ModCapiens(factor, Fons.IN, (-res.getMutati()) - 1));
        }
        return mod;
    }
    public static DuobusRerumZeta convert(Modulus modulus){
        ArrayList<ResZeta> res_numer = new ArrayList<>();
        ArrayList<ResZeta> res_denom = new ArrayList<>();
        res_denom.add(new ResZeta(1));
        ModCapiens[] moduli;
        if(modulus instanceof ModCapiens){
            moduli = new ModCapiens[]{(ModCapiens)modulus};
        }else if(modulus instanceof ModAddens){
            moduli = ((ModAddens)modulus).moduli;
        }else{
            throw new IllegalArgumentException("unknown modulus");
        }
        for(ModCapiens mod:moduli){
            if(mod.fons == null){
                res_numer.add(new ResZeta(mod.factor));
            }else if(mod.fons == Fons.IN){
                res_numer.add(new ResZeta(mod.factor, -(mod.mutati + 1)));
            }else{
                res_denom.add(new ResZeta(new Fraction(-mod.factor.doubleValue()), -(mod.mutati + 1)));
            }
        }
        return res_denom.isEmpty()?
                new DuobusRerumZeta(res_numer.toArray(new ResZeta[]{})):
                new DuobusRerumZeta(res_numer.toArray(new ResZeta[]{}), res_denom.toArray(new ResZeta[]{}));
    }
    public static void spec(DuobusRerumZeta duobus, File file){
        spec(duobus, file, 10000);
    }
    public static void spec(DuobusRerumZeta duobus, File file, double frq_ex){
        Tabula tabula = Tabula.combo(
                "translata:" + duobus.toString() + "    recurrence:" + convert(duobus).toString(), file, "Hz", 
                new String[]{"Gain", "Phase"},
                new Collocatio("Gain", true),
                new Collocatio("Phase", false));
        FunctioTranslata ft = new FunctioTranslata(duobus);
        for(int i = 0;i < frq_ex / 2;i += 10){
            Complex aestimatio = ft.computo(i, frq_ex);
            /*if(i == 2000 || i == 1990){
                System.out.println(aestimatio);
                System.out.println(aestimatio.abs());
                System.out.println(FastMath.atan2(aestimatio.getImaginary(), aestimatio.getReal()));
            }*/
            tabula.addo(i, aestimatio.abs(), FastMath.atan2(aestimatio.getImaginary(), aestimatio.getReal()));
        }
        tabula.imprimo();
    }
    public static void main(String[] args){
        DuobusRerumZeta duobus;
        Modulus mod;        
        //bandpass
        //duobus = new DuobusRerumZeta(
        //        new ResZeta[]{new ResZeta(1), new ResZeta(-1, -2)},
        //        new ResZeta[]{new ResZeta(2)});
        //spec(duobus, new File("c://drive/doc/clamor/spec/bpf.html"));
        
        //lowpass
        //mod = new ModAddens(
        //        new ModCapiens(new Fraction(0.9 / 1.9), Fons.EX, 0), 
        //        new ModCapiens(new Fraction(1.0 / 1.9)));
        //uobus = new DuobusRerumZeta(
        //        new ResZeta[]{new ResZeta(5), new ResZeta(-5, -4)},
        //        new ResZeta[]{new ResZeta(1), new ResZeta(-1, -1)});
        //spec(duobus, new File("c://drive/doc/clamor/spec/lpf_iir.html"));
        //mod = new ModAddens(
        //        new ModCapiens(new Fraction(0.5)),
        //        new ModCapiens(new Fraction(0.5), Fons.IN, 0));
        /*mod = new ModAddens(
                new ModCapiens(new Fraction(1d / 9d)),
                new ModCapiens(new Fraction(2d / 9d), Fons.IN, 0),
                new ModCapiens(new Fraction(3d / 9d), Fons.IN, 1),
                new ModCapiens(new Fraction(2d / 9d), Fons.IN, 2),
                new ModCapiens(new Fraction(1d / 9d), Fons.IN, 3));*/
        mod = new ModAddens(
                new ModCapiens(new Fraction(1d / 3d)),
                new ModCapiens(new Fraction(-1d / 3d), Fons.IN, 1),
                new ModCapiens(new Fraction(1d / 3d), Fons.IN, 3));
        spec(convert(mod), new File("c://drive/doc/clamor/spec/fir.html"));
        
        //highpass
        //mod = new ModAddens(
        //        new ModCapiens(new Fraction(0.8), Fons.EX, 0), 
        //        new ModCapiens(new Fraction(1.0)), 
        //        new ModCapiens(new Fraction(-1.0), Fons.IN, 0));
        //spec(convert(mod), new File("c://drive/doc/clamor/spec/hpf_iir.html"));
        //mod = new ModAddens(
        //        new ModCapiens(new Fraction(0.5)),
        //        new ModCapiens(new Fraction(-0.5), Fons.IN, 0));
        //spec(convert(mod), new File("c://drive/doc/clamor/spec/hpf_fir.html"));

        /*int count = 12;
        ModAddens mod_addens = new ModAddens();
        double factor = 1.0 / (double)count;
        for(int i = 0;i < count;i++){
            if(i == 0){
                mod_addens.addoModulum(new ModCapiens(new Fraction(factor)));
            }else{
                mod_addens.addoModulum(new ModCapiens(new Fraction(factor), Fons.IN, i - 1));
            }
        }
        mod = mod_addens;
        spec(convert(mod), new File("c://drive/doc/clamor/spec/fir.html"));*/
    }
}