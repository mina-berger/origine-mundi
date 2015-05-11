package la.clamor;

public class ExceptioClamoris extends RuntimeException {

    private static final long serialVersionUID = -7420285634598096568L;
    public ExceptioClamoris(String nuntius){
        super(nuntius);
    }
    public ExceptioClamoris(Throwable iacibilis){
        super(iacibilis);
    }
}
