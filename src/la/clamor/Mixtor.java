package la.clamor;

import java.util.TreeMap;
import la.clamor.forma.CadentesFormae;

public class Mixtor implements Legibilis {

    private final TreeMap<Integer, TrackInfo> tracks;

    private long index;

    public Mixtor() {
        tracks = new TreeMap<>();
        index = 0;
    }

    private TrackInfo capioTrack(int id, boolean creo) {
        if (!tracks.containsKey(id)) {
            if (!creo) {
                throw new IllegalArgumentException("no track:" + id);
            }
            tracks.put(id, new TrackInfo());
        }
        return tracks.get(id);
    }

    public void ponoLegibilem(int id, Legibilis legibilem) {
        capioTrack(id, true).fons = legibilem;
    }

    public void ponoInitialPan(int id, Punctum pan) {
        Envelope envelope = new Envelope();
        envelope.ponoPunctum(0, pan);
        capioTrack(id, true).panes = envelope;
    }

    public void ponoPan(int id, double temps, Punctum pan) {
        capioTrack(id, false).panes.ponoPunctum(temps, pan);
    }

    protected Punctum capioPan(int id, long index) {
        return capioTrack(id, false).panes.capioPunctum(index);
    }

    @Override
    public void close() {
        for (Integer id : tracks.keySet()) {
            tracks.get(id).fons.close();
        }
    }

    @Override
    public Punctum lego() {
        Punctum punctum = new Punctum();
        for (Integer id : tracks.keySet()) {
            Legibilis legibilis = capioTrack(id, false).fons;
            if (!legibilis.paratusSum()) {
                continue;
            }
            Punctum lectum = legibilis.lego();
            lectum = lectum.multiplico(capioPan(id, index));
            punctum = punctum.addo(lectum);
            //System.out.println("DEBUG:" + id + ":" + capioPan(id) + ":" + lectum + ":" + punctum);
        }
        index++;
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        return tracks.keySet().stream().anyMatch((id) -> (tracks.get(id).fons.paratusSum()));
    }

    private class TrackInfo {

        Legibilis fons = null;
        Envelope panes = null;
        //CadentesFormae formae = null;
    }
}
