package la.clamor.effector;

import la.clamor.effector.Modulus.DuobusRerumZeta;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.util.FastMath;


public class FunctioTranslata {
    DuobusRerumZeta duobus_rerum;
    public FunctioTranslata(DuobusRerumZeta duobus_rerum){
        this.duobus_rerum = duobus_rerum;
    }
    
    public Complex computo(double freq, double regula_exampli){
        Complex zeta = new Complex(0, 2 * FastMath.PI * freq / regula_exampli).exp();
        /*if(freq == 0){
            System.out.println("computo.zeta=" + zeta);
        }*/
        Complex numer = null;
        for(int i = 0;i < duobus_rerum.longitudoNumer();i++){
            Modulus.ResZeta res = duobus_rerum.capioRemNumer(i);
            numer = numer == null?res.computo(zeta):numer.add(res.computo(zeta));
            /*if(freq == 0){
                System.out.println("computo.numer=" + numer);
            }*/
        }
        Complex denom = null;
        for(int i = 0;i < duobus_rerum.longitudoDenom();i++){
            Modulus.ResZeta res = duobus_rerum.capioRemDenom(i);
            denom = denom == null?res.computo(zeta):denom.add(res.computo(zeta));
            /*if(freq == 0){
                System.out.println("computo.denom=" + denom);
            }*/
        }
        /*if(numer != null){
            System.out.println("freq         =" + freq);
            //System.out.println("computo.numer=" + numer);
            //System.out.println("computo.denom=" + denom);
            System.out.println("computo.devide=" + numer.divide(denom));
            System.out.println("computo.abs   =" + numer.divide(denom).abs());
        }*/
        return numer == null?null:numer.divide(denom);
    }
    
    public static void main(String[] args){
        //ComplexFormat f = ComplexFormat.getInstance();
        DuobusRerumZeta duobus = new DuobusRerumZeta(
                new Modulus.ResZeta[]{new Modulus.ResZeta(1), new Modulus.ResZeta(-1, -2)},
                new Modulus.ResZeta[]{new Modulus.ResZeta(2)});
        System.out.println(duobus.toString());
        FunctioTranslata ft = new FunctioTranslata(duobus);
        double[] frqs = new double[]{0, 1000, 2000, 3000, 4000, 5000};
        double frq_ex = 10000;
        for(double frq:frqs){
            Complex aestimatio = ft.computo(frq, frq_ex);
            //System.out.println(f.format(aestimatio));
            System.out.println("abs=" + aestimatio.abs() + 
                    "/atan=" + FastMath.atan2(aestimatio.getImaginary(), aestimatio.getReal()));
        }
        /*ComplexFormat f = ComplexFormat.getInstance();
        Complex c = new Complex(0, FastMath.PI);
        System.out.println(f.format(c));
        System.out.println(f.format(c.exp()));
        //1 + z-1
        */
    }
}
