package DTO;

import java.util.Date;

import model.EstadoPartido;

public class PartidoDTO {
    private String idPartido;
    private String ubicacion;
    private Date fecha;
    private int duracion;
    private EstadoPartido estado;

    public PartidoDTO(String idPartido, String ubicacion, Date fecha, int duracion, EstadoPartido estado) {
        this.idPartido = idPartido;
        this.ubicacion = ubicacion;
        this.fecha = fecha;
        this.duracion = duracion;
        this.estado = estado;
    }

    public String getIdPartido() { return idPartido; }
    public void setIdPartido(String idPartido) { this.idPartido = idPartido; }
    public String getUbicacion() { return ubicacion; }
    public void setUbicacion(String ubicacion) { this.ubicacion = ubicacion; }
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    public int getDuracion() { return duracion; }
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public EstadoPartido getEstado() { return estado; }
    public void setEstado(EstadoPartido estado) { this.estado = estado; }
}
