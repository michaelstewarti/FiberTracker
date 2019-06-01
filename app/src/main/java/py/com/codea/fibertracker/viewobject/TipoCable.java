package py.com.codea.fibertracker.viewobject;


public enum TipoCable {
    COPACO("COPACO"),
    TIA("TIA");



    public String val;

    TipoCable(String val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return val;
    }

}
