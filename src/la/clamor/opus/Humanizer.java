/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package la.clamor.opus;

import java.util.TreeMap;
import la.clamor.Ludum;
import la.clamor.Punctum;
import la.clamor.Velocitas;
import org.apache.commons.math3.util.FastMath;

/**
 *
 * @author mina
 */
public class Humanizer {

    private Randomizer r_clevis;
    private Randomizer r_repenso;
    private Randomizer r_velocitas;
    private TreeMap<Integer, Randomizer> r_velocitatis;
    private TreeMap<Double, Convertum> converti;

    public Humanizer() {
        converti = new TreeMap<>();
        r_clevis = null;
        r_repenso = null;
        r_velocitas = null;
        r_velocitatis = new TreeMap<>();
        pono(0, 0, 1);
        pono(1, 0, 1);
    }

    public Humanizer ponoRandomClevis(double solum, double tectum) {
        r_clevis = new Randomizer(solum, tectum);
        return this;
    }

    public Humanizer ponoRandomRepenso(double solum, double tectum) {
        r_repenso = new Randomizer(solum, tectum);
        return this;
    }

    public Humanizer ponoRandomVelocitas(double solum, double tectum) {
        r_velocitas = new Randomizer(solum, tectum);
        return this;
    }

    public Humanizer ponoRandomVelocitas(int ordo, double solum, double tectum) {
        r_velocitatis.put(ordo, new Randomizer(solum, tectum));
        return this;
    }

    public final Humanizer pono(double repenso, double mutatum, double velocitas) {
        if (repenso < 0 || repenso > 1) {
            throw new IllegalArgumentException("repenso must be between 0 and 1(" + repenso + ")");
        }
        converti.put(repenso, new Convertum(repenso, mutatum, velocitas));
        return this;
    }

    public Ludum humanize(Taleae taleae, int talea, double repenso, double diutius, double clevis, Velocitas velocitas) {
    /*    return new Ludum(
            talea, repenso,
            clevis,
            taleae.capioTempus(talea, repenso + diutius) - taleae.capioTempus(talea, repenso),
            velocitas);
    }

    private void creoSub(Taleae taleae, int talea, double repenso, double diutius, double clevis, Velocitas velocitas) {
    */
        double m_repenso = repenso - FastMath.floor(repenso);
        Convertum solum = converti.floorEntry(m_repenso).getValue();
        double _mutatum;
        double _velocitas;
        //double solum_velocitas = velocitas.multiplico(_mutatum) * solum.velocitas;
        if (converti.containsKey(m_repenso)) {
            _mutatum = solum.mutatum;
            _velocitas = solum.velocitas;
        } else {
            Convertum tectum = converti.ceilingEntry(m_repenso).getValue();
            double ratio = (m_repenso - solum.repenso) / (tectum.repenso - solum.repenso);
            _mutatum = solum.mutatum + (tectum.mutatum - solum.mutatum) * ratio;
            _velocitas = solum.velocitas + (tectum.velocitas - solum.velocitas) * ratio;
        }

        double terminum = repenso + diutius;
        double m_terminum = terminum - FastMath.floor(terminum);
        Convertum t_solum = converti.floorEntry(m_terminum).getValue();

        double t_mutatum;
        if (converti.containsKey(m_terminum)) {
            t_mutatum = t_solum.mutatum;
        } else {
            Convertum t_tectum = converti.ceilingEntry(m_terminum).getValue();
            double t_ratio = (m_terminum - t_solum.repenso) / (t_tectum.repenso - t_solum.repenso);
            t_mutatum = t_solum.mutatum + (t_tectum.mutatum - t_solum.mutatum) * t_ratio;
        }
        double mut_clevis = r_clevis == null ? 0 : r_clevis.next();
        double mut_repenso = r_repenso == null ? 0 : r_repenso.next();
        double mut_velocitas = (r_velocitas == null ? 0 : r_velocitas.next()) + 1.;

        double _clevis = clevis + mut_clevis;
        double _repenso = repenso + _mutatum + mut_repenso;
        if (talea == 0 && _repenso < 0) {
            _repenso = 0d;
        }
                
        return new Ludum(
            talea,
            _repenso,
            _clevis,
            diutius - _mutatum + t_mutatum,
            velocitas.multiplico(_velocitas * mut_velocitas));
/*
        new Ludum(
            ludum.capioTrack(),
            ludum.capioTaleam(),
            _repenso,
            ludum.capioDiutium() - _mutatum + t_mutatum,
            _clevis,
            _velocitas + mut_velocitas).creo();
        //System.out.println("CREO LUDUM!!");
        r_velocitatis.keySet().stream().forEach((key) -> {
            double _velocitas_ordi = r_velocitatis.get(key).next();
            creoVelocitas(key, new Punctum(_velocitas_ordi));
            //System.out.println("CREO VELOCITAS:" + key + ":" + _velocitas_ordi);
        });
*/
    }

    private static class Randomizer {

        double tectum;
        double solum;

        Randomizer(double solum, double tectum) {
            this.solum = solum;
            this.tectum = tectum;
        }

        double next() {
            return FastMath.random() * (tectum - solum) + solum;
        }

    }

    public static class Convertum {

        double repenso;
        double mutatum;
        double velocitas;

        Convertum(double repenso, double mutatum, double velocitas) {
            this.repenso = repenso;
            this.mutatum = mutatum;
            this.velocitas = velocitas;
        }
    }
}
