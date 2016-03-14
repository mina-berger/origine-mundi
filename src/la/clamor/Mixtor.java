package la.clamor;

import java.util.TreeMap;

public class Mixtor implements Legibilis {

    private final TreeMap<Integer, TrackInfo> tracks;
    private Envelope levels = null;

    private long index;

    public Mixtor() {
        tracks = new TreeMap<>();
        index = 0;
        levels = new Envelope(true);
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

    public void ponoInitialLevel(int id, Punctum pan) {
        Envelope envelope = new Envelope();
        envelope.ponoPunctum(0, pan);
        capioTrack(id, true).levels = envelope;
    }

    public void ponoLevel(int id, double temps, Punctum pan) {
        capioTrack(id, false).levels.ponoPunctum(temps, pan);
    }

    protected Punctum capioLevel(int id, long index) {
        return capioTrack(id, false).levels.capioPunctum(index);
    }
    
    protected Punctum capioMasterLevel(long index) {
        return levels.capioPunctum(index);
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
            lectum = lectum.multiplico(capioLevel(id, index));
            punctum = punctum.addo(lectum);
            //System.out.println("DEBUG:" + id + ":" + capioPan(id) + ":" + lectum + ":" + punctum);
        }
        punctum = punctum.multiplico(capioMasterLevel(index));
        index++;
        return punctum;
    }

    @Override
    public boolean paratusSum() {
        return tracks.keySet().stream().anyMatch((id) -> (tracks.get(id).fons.paratusSum()));
    }

    private class TrackInfo {

        Legibilis fons = null;
        Envelope levels = null;
        //CadentesFormae formae = null;
    }
}
