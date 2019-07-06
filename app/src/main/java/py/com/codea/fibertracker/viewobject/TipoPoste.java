package py.com.codea.fibertracker.viewobject;


public enum TipoPoste {
    HINT("Seleccionar Tipo de Poste"),
    POSTE_HORMIGON_ANDE("Poste Hormigón ANDE"),
    POSTE_HORMIGON_ANDE_CON_RESERVA("Poste Hormigón ANDE con Reserva"),
    TORRE("Torre"),
    POSTE_HORMIGON_COPACO("Poste Hormigón COPACO"),
    POSTE_HORMIGON_COPACO_CON_RESERVA("Poste Hormigón COPACO"),
    POSTE_METALICO_ANDE("Poste Metálico ANDE"),
    POSTE_METALICO_ANDE_CON_RESERVA("Poste Metálico ANDE con Reserva"),
    POSTE_METALICO_COPACO("Poste Metálico COPACO"),
    POSTE_METALICO_COPACO_CON_RESERVA("Poste Metálico COPACO con Reserva");



    public String val;

    TipoPoste(String val) {
        this.val = val;
    }

    @Override
    public String toString(){
        return val;
    }

}