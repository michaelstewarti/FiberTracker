package py.com.codea.fibertracker.viewobject;

public enum CantidadPelos {
    HINT("Cantidad de Pelos del Cable"),
    _6("6"),
    _12("12"),
    _24("24"),
    _36("36"),
    _48("48"),
    _72("72"),
    _96("96"),
    _144("144");



    public String val;

    CantidadPelos(String val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return val;
    }

}
