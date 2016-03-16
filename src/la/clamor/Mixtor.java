package la.clamor;

import java.util.TreeMap;

public class Mixtor implements Legibilis {

    private final TreeMap<Integer, TrackInfo> tracks;
    private Envelope<Punctum> levels = null;
    //private Envelope<Cinctum> pans = null;

    private long index;

    public Mixtor() {
        tracks = new TreeMap<>();
        index = 0;
        levels = new Envelope<>(new Punctum(1));
        //pans = new Envelope<>(new Cinctum(false, new Punctum(1)));
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

    public void ponoInitialPan(int id, Cinctum pan) {
        Envelope<Cinctum> envelope = new Envelope<>();
        envelope.ponoValue(0, pan);
        capioTrack(id, true).pans = envelope;
    }

    public void ponoPan(int id, double temps, Cinctum pan) {
        capioTrack(id, false).pans.ponoValue(temps, pan);
    }
    protected Cinctum capioPan(int id, long index) {
        return capioTrack(id, false).pans.capioValue(index);
    }
    
    public void ponoInitialLevel(int id, Punctum level) {
        Envelope<Punctum> envelope = new Envelope<>();
        envelope.ponoValue(0, level);
        capioTrack(id, true).levels = envelope;
    }

    public void ponoLevel(int id, double temps, Punctum level) {
        capioTrack(id, false).levels.ponoValue(temps, level);
    }

    protected Punctum capioLevel(int id, long index) {
        return capioTrack(id, false).levels.capioValue(index);
    }
    
    public void ponoMasterLevel(double temps, Punctum level) {
        levels.ponoValue(temps, level);
    }
    protected Punctum capioMasterLevel(long index) {
        return levels.capioValue(index);
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
            lectum = capioPan(id, index).cingo(lectum).multiplico(capioLevel(id, index));
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
        Envelope<Punctum> levels = null;
        Envelope<Cinctum> pans = null;
        //CadentesFormae formae = null;
    }
}
