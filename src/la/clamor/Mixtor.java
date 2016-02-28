package la.clamor;

import java.util.TreeMap;

public abstract class Mixtor implements Legibilis {
    TreeMap<Integer, Legibilis> legibiles = new TreeMap<>();
    public void ponoLegibilem(int id, Legibilis legibilem){
        legibiles.put(id, legibilem);
    }
    protected abstract Punctum capioPan(int id);
    @Override
    public Punctum lego(){
        Punctum punctum = new Punctum();
        for(Integer id:legibiles.keySet()){
            Legibilis legibilis = legibiles.get(id);
            if(!legibilis.paratusSum()){
                continue;
            }
            Punctum lectum = legibilis.lego();
            lectum = lectum.multiplico(capioPan(id));
            punctum = punctum.addo(lectum);
            //System.out.println("DEBUG:" + id + ":" + capioPan(id) + ":" + lectum + ":" + punctum);
        }
        return punctum;
    }
    @Override
    public boolean paratusSum() {
        return legibiles.keySet().stream().anyMatch((id) -> (legibiles.get(id).paratusSum()));
    }
    public static class MixtorFixus extends Mixtor {
        TreeMap<Integer, Punctum> panes = new TreeMap<>();

        public void ponoPan(int id, Punctum pan) {
            //System.out.println("DEBUG:" + pan);
            panes.put(id, pan);
        }
        @Override
        protected Punctum capioPan(int id) {
            return panes.get(id);
        }
    }
}
