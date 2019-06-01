package py.com.codea.fibertracker.viewobject;

public class Punto {

    private Float x;
    private Float y;
    private TipoPoste tipoPoste;
    private TipoCable tipoCable;
    private CantidadPelos cantidadPelos;
    private TipoManga tipoManga;
    private Integer cantidadSplitters;

    public Punto(Float x, Float y, TipoPoste tipoPoste, TipoCable tipoCable, CantidadPelos cantidadPelos, TipoManga tipoManga, Integer cantidadSplitters) {
        this.x = x;
        this.y = y;
        this.tipoPoste = tipoPoste;
        this.tipoCable = tipoCable;
        this.cantidadPelos = cantidadPelos;
        this.tipoManga = tipoManga;
        this.cantidadSplitters = cantidadSplitters;
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public TipoPoste getTipoPoste() {
        return tipoPoste;
    }

    public void setTipoPoste(TipoPoste tipoPoste) {
        this.tipoPoste = tipoPoste;
    }

    public TipoCable getTipoCable() {
        return tipoCable;
    }

    public void setTipoCable(TipoCable tipoCable) {
        this.tipoCable = tipoCable;
    }

    public CantidadPelos getCantidadPelos() {
        return cantidadPelos;
    }

    public void setCantidadPelos(CantidadPelos cantidadPelos) {
        this.cantidadPelos = cantidadPelos;
    }

    public Integer getCantidadSplitters() {
        return cantidadSplitters;
    }

    public void setCantidadSplitters(Integer cantidadSplitters) {
        this.cantidadSplitters = cantidadSplitters;
    }

    public TipoManga getTipoManga() {
        return tipoManga;
    }

    public void setTipoManga(TipoManga tipoManga) {
        this.tipoManga = tipoManga;
    }
}
