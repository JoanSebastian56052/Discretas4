package co.edu.udea.compumovil.gr06_20172.lab1;

/**
 * Created by joan.morales on 21/09/17.
 */
public class Apartment {
    private String nombre;
    private String tipo;
    private String descripcion;
    private String area;
    private String direccion;
    private String valor;
    private byte[] foto;

    public Apartment() {
    }

    public Apartment(String nombre, String tipo, String descripcion) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String distancia) {
        this.tipo = tipo;
    }


    public void setArea(String lugar) {
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public void setDireccion(String fecha) {
        this.direccion = direccion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setValor(String telefono) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
