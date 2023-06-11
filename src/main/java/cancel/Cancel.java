package cancel;

/**
 *
 * @author Jelena Basaric
 */
public class Cancel {
    private int idpaketa=2;
    private int len=12;
    private int id;

    @Override
    public String toString() {
        return "Cancel{" + "idpaketa=" + idpaketa + ", len=" + len + ", id=" + id + '}';
    }

    public Cancel(int id) {
        this.id = id;
    }

    public Cancel() {
    }

    public int getIdpaketa() {
        return idpaketa;
    }

    public int getLen() {
        return len;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
}
