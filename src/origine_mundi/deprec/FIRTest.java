/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package origine_mundi.deprec;

/**
 *
 * @author Mina
 */
public class FIRTest {

    public static void main(String[] args) {

        /* パラメータ　求めたい値に書き換えて！ */
        double sFreq = 48000; /* サンプリング周波数 */

        double cFreq = 100; /* カットオフ周波数 */

        int tapn = 21; /* タップ数　奇数   */

        boolean lpfflag = false; /* フィルタ切替LPF=true, HPF=false */

        int windowF = 0; /* 窓関数の切替 hamming=0, hanning=1 */

        /* 変数 */
        int tapM = ((tapn - 1) / 2); /* タップ中央の位置 */

        double omegaLPF = Math.PI * (((cFreq) / (sFreq)) * 2);
        double omegaHPF = Math.PI * (1 - (((cFreq) / (sFreq)) * 2));
        double hCenter = 1 - (cFreq / (sFreq / 2)); /* センターの係数 */

        double tapArray[] = new double[tapn]; /* タップ数 */

        double h = 0; /* 係数 */

        double wHamming = 0; /* ハミング窓初期化 */

        double wHanning = 0; /* ハニング窓初期化 */

        double hWinLPF = 0; /* LPF HPF用 */

        double hWinHPF = 0; /* HPF用 */

        /* LPF */
        if (lpfflag == true) {
            for (int i = 1; i <= tapM; i++) {
                
                h = Math.sin(i * omegaLPF) / (Math.PI * i);
                /* 窓関数の切替 */
                switch (windowF) {
                    case 0://Hamming
                        wHamming = 0.54 + 0.46 * Math.cos((i * Math.PI) / (tapM));
                        hWinLPF = h * wHamming;
                        break;
                    case 1: /* hanning */

                        wHanning = 0.5 + 0.5 * Math.cos((i * Math.PI) / (tapM));
                        hWinLPF = h * wHanning;
                        break;
                }
                tapArray[tapM + i] = hWinLPF; /* 中央から後ろへ */

                tapArray[tapM - i] = hWinLPF; /* 中央から前へ */

            }
            /* 中央値 */
            tapArray[tapM] = 1.0 - hCenter;
        } /* HPF */ else {
            for (int i = 1; i <= tapM; i++) {
                /* 係数を求める */
                
                h = Math.sin(i * omegaHPF) / (Math.PI * i);
                /* 窓関数の切替 */
                switch (windowF) {
                    case 0://Hamming
                        wHamming = 0.54 + 0.46 * Math.cos((i * Math.PI) / (tapM));
                        hWinLPF = h * wHamming;
                        break;
                    case 1:/* hanning */

                        wHanning = 0.5 + 0.5 * Math.cos((i * Math.PI) / (tapM));
                        hWinLPF = h * wHanning;
                        break;
                }
                hWinHPF = hWinLPF * Math.pow(-1, i);/* HPF */
                /* 配列に入れていく */

                tapArray[tapM + i] = hWinHPF;
                tapArray[tapM - i] = hWinHPF;
            }
            /* 中央値 */
            tapArray[tapM] = hCenter;
        }

        /* タップをプロンプトに出力 */
        for (int i = 0; i < tapArray.length; i++) {
            System.out.println(tapArray[i] + ",");
        }
    }
}
