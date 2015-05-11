package la.clamor;

import la.clamor.Punctum.Aestimatio;

public interface Constantia {
    public static final int CHANNEL = 2;
    public static final int REGULA_EXAMPLI = 48000;
    //public static final int REGULA_EXAMPLI = 44100;
    public static final double REGULA_EXAMPLI_D = REGULA_EXAMPLI;
    public static final Aestimatio REGULA_MAGISTRI = new Aestimatio(0.88);
    /** 1=8bit, 2=16bit, 3=24bit */
    public static final int BYTE_PER_EXAMPLUM = 2;
    public static final Aestimatio MAX_AMPLITUDO = new Aestimatio(Math.pow(2, BYTE_PER_EXAMPLUM * 8 - 1) - 1);
    public static final Aestimatio MIN_AMPLITUDO = new Aestimatio(Math.pow(2, BYTE_PER_EXAMPLUM * 8 - 1) * -1);
    /** locus terminato (ms) */
    public static final int LOCUS_TERMINATO = 1000;
    //public static final int LOCUS_TERMINATO = 200;
    
    public enum Fons{IN, EX}
    public enum Partes{PRIMO, VCO, VCF, VCA, VCP, FB};
    public enum Parma {FCO, FCA, QCO, QCA, PCO, PCA};
    public enum Unda {SINE, QUAD, TRIA, DENT, FRAG}
    public enum Effector {DIST, CHOR, MORA, COMP};
}
