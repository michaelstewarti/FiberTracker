package py.com.codea.fibertracker.viewobject;

public enum TipoManga {
    HINT("Seleccionar Tipo de Manga"),
    PRIMARIA("Primaria"),
    SECUNDARIA("Secundaria");

    public String val;

    TipoManga(String val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return val;
    }

}
