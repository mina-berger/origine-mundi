/*
 * CLAMOR project
 * by MINA BERGER
 */
package la.clamor.effector;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.UnsupportedAudioFileException;
import la.clamor.Constantia.Fons;
import static la.clamor.Constantia.REGULA_EXAMPLI_D;
import la.clamor.effector.Modulus.ModAddens;
import la.clamor.effector.Modulus.ModCapiens;
import static la.clamor.effector.Modulus.convert;
import static la.clamor.effector.Modulus.spec;
import org.apache.commons.math3.util.FastMath;
/**
 *
 * @author minae.hiyamae
 */
public class Butterworth {

    /**
     * Generate a Butterworth low/high pass filter at the given cutoff frequency
     *
     * @param order
     * @param freq in Hz
     * @param lpfEst true for lowpass, false for high pass
     * @return 
     */
    public static Modulus capioModulum(int order, double freq, boolean lpfEst) {


        double omega = 2. * Math.PI * freq / REGULA_EXAMPLI_D;

        double scale = computeScale(order, omega, lpfEst);

        double[] b = computoB(order, lpfEst);
        for (int i = 0; i < b.length; ++i) {
            b[i] *= scale;
        }

        double[] a = computeA(order, omega);
        return capioModulum(a, b);
    }

    /**
     * Generate a Butterworth band pass/reject filter at the given cutoff
     * frequencies.
     *
     * @param order
     * @param freq1 in Hz
     * @param freq2 in Hz
     * @param pass true for bandpass, false for bandreject
     * @return 
     */
    public static Modulus capioModulum(int order, double freq1, double freq2, boolean pass) {

        //double ff1 = 2. * freq1 / source.getSampleRate();
        //double ff2 = 2. * freq2 / source.getSampleRate();

        double scale = computeScale(order, freq1, freq2, pass);

        double[] b = computeB(order, freq1, freq2, pass);
        for (int i = 0; i < b.length; ++i) {
            b[i] *= scale;
        }

        double[] a = computeA(order, freq1, freq2, pass);
        return capioModulum(a, b);
    }
    private static Modulus capioModulum(double[] a, double[] b){
        ModAddens mod = new ModAddens();
        for(int i = 0;i < a.length;i++){
            if(i == 0){
                mod.addoModulum(new ModCapiens(a[i]));
            }else{
                mod.addoModulum(new ModCapiens(a[i], Fons.IN, i));
            }
        }
        for(int i = 0;i < b.length;i++){
            mod.addoModulum(new ModCapiens(b[i], Fons.EX, i));
        }
        return mod;
        /*ResZeta[] numer_res = new ResZeta[a.length];
        for(int i = 0;i < a.length;i++){
            numer_res[i] = new ResZeta(a[i], -i);
        }
        ResZeta[] denom_res = new ResZeta[b.length];
        for(int i = 0;i < b.length;i++){
            denom_res[i] = new ResZeta(b[i], -i);
        }
        return new DuobusRerumZeta(numer_res, denom_res);
        */
    }

    /**
     * Compute the B coefficients for low/high pass. The cutoff frequency is not
     * required.
     *
     * @param n
     * @param lpfEst
     * @return
     */
    private static double[] computoB(int n, boolean lpfEst) {
        double[] coefficients = new double[n + 1];

        coefficients[0] = 1;
        coefficients[1] = n;

        for (int i = 2; i < n / 2 + 1; ++i) {
            coefficients[i]     = (n - i + 1) * coefficients[i - 1] / i;
            coefficients[n - i] = coefficients[i];
        }

        coefficients[n - 1] = n;
        coefficients[n]     = 1;

        if (!lpfEst) {
            for (int i = 1; i < n + 1; i += 2) {
                coefficients[i] = -coefficients[i];
            }
        }

        return coefficients;

    }

    /**
     * Compute the B coefficients for a band pass/reject. Both cutoff
     * frequencies need to be specified as radians.
     *
     * @param n
     * @param f1 frequency in radians (2 * hz / samplef)
     * @param f2
     * @param pass
     * @return
     */
    private static double[] computeB(int n, double freq1, double freq2, boolean pass) {
        double[] coefficients = new double[2 * n + 1];
        if (pass) {
            double[] tcof = computoB(n, false);

            for (int i = 0; i < n; ++i) {
                coefficients[2 * i] = tcof[i];
                coefficients[2 * i + 1] = 0.;
            }

            coefficients[2 * n] = tcof[n];
        } else {
            double alpha = -2. * FastMath.cos(FastMath.PI * (freq2 + freq1) / REGULA_EXAMPLI_D) 
                               / FastMath.cos(FastMath.PI * (freq2 - freq1) / REGULA_EXAMPLI_D);

            coefficients[0] = 1.;
            coefficients[1] = alpha;
            coefficients[2] = 1.;

            for (int i = 1; i < n; ++i) {
                coefficients[2 * i + 2] += coefficients[2 * i];
                for (int j = 2 * i; j > 1; --j) {
                    coefficients[j + 1] += alpha * coefficients[j] + coefficients[j - 1];
                }

                coefficients[2] += alpha * coefficients[1] + 1.0;
                coefficients[1] += alpha;
            }
        }

        return coefficients;
    }

    /**
     * Compute the A coefficients for a low/high pass for the given frequency
     *
     * @param n
     * @param f frequency in radians (2 * hz / samplef)
     * @return
     */
    private static double[] computeA(int n, double theta) {
        
        double[] binom_coefficients = new double[2 * n]; // binomial coefficients
        double sin_theta = Math.sin(theta);
        double cos_theta = Math.cos(theta);

        for (int k = 0; k < n; ++k) {
            double pole_angle = Math.PI * (double) (2 * k + 1) / (double) (2 * n);
            double sin_pa = Math.sin(pole_angle);
            double cos_pa = Math.cos(pole_angle);
            double denom = 1. + sin_theta * sin_pa;
            binom_coefficients[2 * k]     = -cos_theta / denom;
            binom_coefficients[2 * k + 1] = -sin_theta * cos_pa / denom;
        }

        // compute the binomial
        double[] temp = binomialMult(binom_coefficients);

        // we only need the n+1 coefficients
        double[] dcof = new double[n + 1];
        dcof[0] = 1.0;
        dcof[1] = temp[0];
        dcof[2] = temp[2];
        for (int k = 3; k < n + 1; ++k) {
            dcof[k] = temp[2 * k - 2];
        }
        return dcof;
    }

    /**
     * Compute the A coefficients for a band pass/reject
     *
     * @param n
     * @param f1 frequency in radians (2 * hz / samplef)
     * @param f2
     * @param pass
     * @return
     */
    private static double[] computeA(int n, double freq1, double freq2, boolean pass) {
        double cos_p = Math.cos(Math.PI * (freq2 + freq1) / REGULA_EXAMPLI_D);
        double theta = Math.PI * (freq2 - freq1) / REGULA_EXAMPLI_D;
        double sin_theta = Math.sin(theta);
        double cos_theta = Math.cos(theta);
        double sin_2theta = 2. * sin_theta * cos_theta; // sine of 2*theta
        double cos_2theta = 2. * cos_theta * cos_theta - 1.0; // cosine of 2*theta

        double[] rcof = new double[2 * n]; // z^-2 coefficients
        double[] tcof = new double[2 * n]; // z^-1 coefficients

        for (int k = 0; k < n; ++k) {
            double pole_angle = Math.PI * (double) (2 * k + 1) / (double) (2 * n);
            double sine_pa = Math.sin(pole_angle);
            double cos_pa = Math.cos(pole_angle);
            double denom = 1.0 + sin_2theta * sine_pa;
            rcof[2 * k]     = cos_2theta / denom;
            rcof[2 * k + 1] = (pass ? 1. : -1.) * sin_2theta * cos_pa / denom;
            tcof[2 * k]     = -2.0 * cos_p * (cos_theta + sin_theta * sine_pa) / denom;
            tcof[2 * k + 1] = (pass ? -2. : 2.) * cos_p * sin_theta * cos_pa / denom;
        }

        // compute trinomial
        double[] temp = trinomialMult(tcof, rcof);

        // we only need the 2n+1 coefficients
        double[] dcof = new double[2 * n + 1];
        dcof[0] = 1.0;
        dcof[1] = temp[0];
        dcof[2] = temp[2];
        for (int k = 3; k < 2 * n + 1; ++k) {
            dcof[k] = temp[2 * k - 2];
        }

        return dcof;
    }

    /**
     * Compute the scale factor for the b coefficients for given low/high pass
     * filter.
     *
     * @param n
     * @param f
     * @param lowp
     * @return
     */
    private static double computeScale(int n, double omega, boolean lowp) {
        double sine_omega = FastMath.sin(omega);
        double pole_denom = FastMath.PI / (double) (2 * n);

        double scale = 1.;
        for (int k = 0; k < n / 2; ++k) {
            scale *= 1.0 + sine_omega * FastMath.sin((double) (2 * k + 1) * pole_denom);
        }

        sine_omega = FastMath.sin(omega / 2.0);

        if (n % 2 == 1) {
            scale *= sine_omega + (lowp ? FastMath.cos(omega / 2.0) : FastMath.sin(omega / 2.));
        }
        scale = FastMath.pow(sine_omega, n) / scale;

        return scale;
    }

    /**
     * Compute the scale factor for the b coefficients for the given band
     * pass/reject filter
     *
     * @param n
     * @param f1
     * @param f2
     * @param pass
     * @return
     */
    private static double computeScale(int n, double freq1, double freq2, boolean pass) {
        double tan_theta = Math.tan(Math.PI * (freq2 - freq1) / REGULA_EXAMPLI_D);
        if (pass) {
            tan_theta = 1. / tan_theta;
        }

        double scale_re = 1.;
        double scale_im = 0.;

        for (int k = 0; k < n; ++k) {
            double pole_angle = Math.PI * (double) (2 * k + 1) / (double) (2 * n);
            double sin_pa = tan_theta + Math.sin(pole_angle);
            double cos_pa = Math.cos(pole_angle);
            double a = (scale_re + scale_im) * (sin_pa - cos_pa);
            double b = scale_re * sin_pa;
            double c = -scale_im * cos_pa;
            scale_re = b - c;
            scale_im = a - b - c;
        }

        return 1. / scale_re;
    }

    /**
     * Multiply a series of binomials and returns the coefficients of the
     * resulting polynomial. The multiplication has the following form:<b/>
     *
     * (x+p[0])*(x+p[1])*...*(x+p[n-1]) <b/>
     *
     * The p[i] coefficients are assumed to be complex and are passed to the
     * function as an array of doubles of length 2n.<b/>
     *
     * The resulting polynomial has the following form:<b/>
     *
     * x^n + a[0]*x^n-1 + a[1]*x^n-2 + ... +a[n-2]*x + a[n-1] <b/>
     *
     * The a[i] coefficients can in general be complex but should in most cases
     * turn out to be real. The a[i] coefficients are returned by the function
     * as an array of doubles of length 2n.
     *
     * @param p array of doubles where p[2i], p[2i+1] (i=0...n-1) is assumed to
     * be the real, imaginary part of the i-th binomial.
     * @return coefficients a: x^n + a[0]*x^n-1 + a[1]*x^n-2 + ... +a[n-2]*x +
     * a[n-1]
     */
    private static double[] binomialMult(double[] p) {
        int      n = p.length / 2;
        double[] a = new double[2 * n];

        for (int i = 0; i < n; ++i) {
            for (int j = i; j > 0; --j) {
                a[2 * j]     += p[2 * i]     * a[2 * (j - 1)] - 
                                p[2 * i + 1] * a[2 * (j - 1) + 1];
                a[2 * j + 1] += p[2 * i]     * a[2 * (j - 1) + 1] + 
                                p[2 * i + 1] * a[2 * (j - 1)];
            }
            a[0] += p[2 * i];
            a[1] += p[2 * i + 1];
        }
        return a;
    }

    /**
     * Multiply a series of trinomials and returns the coefficients of the
     * resulting polynomial. The multiplication has the following form:<b/>
     *
     * (x^2 + b[0]x + c[0])*(x^2 + b[1]x + c[1])*...*(x^2 + b[n-1]x + c[n-1])
     * <b/>
     *
     * The b[i], c[i] coefficients are assumed to be complex and are passed to
     * the function as an array of doubles of length 2n.<b/>
     *
     * The resulting polynomial has the following form:<b/>
     *
     * x^2n + a[0]*x^2n-1 + a[1]*x^2n-2 + ... +a[2n-2]*x + a[2n-1] <b/>
     *
     * The a[i] coefficients can in general be complex but should in most cases
     * turn out to be real. The a[i] coefficients are returned by the function
     * as an array of doubles of length 2n.
     *
     * @param b array of doubles where b[2i], b[2i+1] (i=0...n-1) is assumed to
     * be the real, imaginary part of the i-th binomial.
     * @param c
     * @return coefficients a: x^2n + a[0]*x^2n-1 + a[1]*x^2n-2 + ... +a[2n-2]*x
     * + a[2n-1]
     */
    private static double[] trinomialMult(double[] b, double[] c) {
        int n = b.length / 2;
        double[] a = new double[4 * n];

        a[0] = b[0];
        a[1] = b[1];
        a[2] = c[0];
        a[3] = c[1];

        for (int i = 1; i < n; ++i) {
            a[2 * (2 * i + 1)    ] += c[2 * i    ] * a[2 * (2 * i - 1)    ] - 
                                      c[2 * i + 1] * a[2 * (2 * i - 1) + 1];
            a[2 * (2 * i + 1) + 1] += c[2 * i    ] * a[2 * (2 * i - 1) + 1] + 
                                      c[2 * i + 1] * a[2 * (2 * i - 1)    ];

            for (int j = 2 * i; j > 1; --j) {
                a[2 * j    ] += b[2 * i    ] * a[2 * (j - 1)    ] - 
                                b[2 * i + 1] * a[2 * (j - 1) + 1] + 
                                c[2 * i    ] * a[2 * (j - 2)    ] - 
                                c[2 * i + 1] * a[2 * (j - 2) + 1];
                a[2 * j + 1] += b[2 * i    ] * a[2 * (j - 1) + 1] + 
                                b[2 * i + 1] * a[2 * (j - 1)    ] + 
                                c[2 * i    ] * a[2 * (j - 2) + 1] + 
                                c[2 * i + 1] * a[2 * (j - 2)    ];
            }

            a[2] += b[2 * i] * a[0] - b[2 * i + 1] * a[1] + c[2 * i];
            a[3] += b[2 * i] * a[1] + b[2 * i + 1] * a[0] + c[2 * i + 1];
            a[0] += b[2 * i];
            a[1] += b[2 * i + 1];
        }

        return a;
    }

    public static final String SYNOPSIS
        = "sikoried, 7/5/2011\n"
        + "Apply a Butterworth lowpass/bandpass filter of given order.\n"
        + "usage: sampled.filters.Butterworth format order cutoff1 [cutoff2] < input > output\n"
        + "  format : ssg/8 or ssg/16\n"
        + "  order  : typically 3\n"
        + "  cutoff1: cutoff frequency (lowpass)\n"
        + "  cutoff2: cutoff frequency (bandpass)";

    public static void main(String[] args) throws IOException, UnsupportedAudioFileException {
        File f = new File("c://drive/doc/clamor/spec/bw.html");
        f.getParentFile().mkdirs();
        spec(convert(Butterworth.capioModulum(2, 1, false)), f);
        
        double[] a = new double[]{
            1};
        double[] b = new double[]{
            1, 1};
        //spec(convert(Butterworth.capioModulum(a, b)), new File("c://drive/doc/clamor/spec/bw.html"), 1000);
    }
}
