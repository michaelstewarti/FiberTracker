package py.com.codea.fibertracker.viewobject;

public enum TipoManga {
    HINT("Tipo de Manga"),
    PASANTE("Pasante"),
    DERIVACION("Derivación"),
    DERIVACION_PRIMARIA("Derivación Primaria"),
    NAP("NAP");

    public String val;

    TipoManga(String val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return val;
    }

}
