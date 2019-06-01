package py.com.codea.fibertracker.viewobject;


public enum TipoPoste {
    POSTE_HORMIGON_ANDE("POSTE HORMIGON ANDE"),
    POSTE_HORMIGON_ANDE_CON_RESERVA("POSTE HORMIGON ANDE CON RESERVA"),
    TORRE("TORRE"),
    POSTE_HORMIGON_COPACO("POSTE HORMIGON COPACO"),
    POSTE_HORMIGON_COPACO_CON_RESERVA("POSTE HORMIGON COPACO CON RESERVA"),
    POSTE_METALICO_ANDE("POSTE METALICO ANDE"),
    POSTE_METALICO_ANDE_CON_RESERVA("POSTE METALICO ANDE CON RESERVA"),
    POSTE_METALICO_COPACO("POSTE METALICO COPACO"),
    POSTE_METALICO_COPACO_CON_RESERVA("POSTE METALICO COPACO CON RESERVA");



    public String val;

    TipoPoste(String val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return val;
    }

}