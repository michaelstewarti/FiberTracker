package py.com.codea.fibertracker.viewobject;

public enum TipoManga {

    PRIMARIA("PRIMARIA"),
    SECUNDARIA("SECUNDARIA");

    public String val;

    TipoManga(String val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return val;
    }

}
