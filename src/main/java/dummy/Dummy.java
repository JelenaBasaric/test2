package dummy;


/**
 *
 * @author Jelena Basaric
 */
public class Dummy {
   private final int idPaket=1;
    private  final int len=16;
    private int id=1;
    private int delay=30;
    
    

    @Override
    public String toString() {
        return "Dummy{" + "idPaket=" + idPaket + ", len=" + len + ", id=" + id + ", delay=" + delay + '}';
    }

    public Dummy(int id, int delay) {
        this.id = id;
        this.delay = delay;
    }

    public Dummy() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public int getIdPaket() {
        return idPaket;
    }

    public int getLen() {
        return len;
    }

    public int getId() {
        return id;
    }

    public int getDelay() {
        return delay;
    }
    

    
   

   
    
    
    
    
    
}
