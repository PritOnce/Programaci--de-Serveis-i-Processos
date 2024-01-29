import java.io.Serializable;

public class Libro implements Serializable{

    private String nomLibro;
    private String autorLibro;
    private int precioLibro;
    private int valoracionLibro;

    public Libro(){
        
    }

    public Libro(String nomLibro, String autorLibro, int precioLibro, int valoracionLibro) {
        this.nomLibro = nomLibro;
        this.autorLibro = autorLibro;
        this.precioLibro = precioLibro;
        this.valoracionLibro = valoracionLibro;
    }


    public String getNomLibro() {
        return nomLibro;
    }


    public void setNomLibro(String nomLibro) {
        this.nomLibro = nomLibro;
    }


    public String getAutorLibro() {
        return autorLibro;
    }


    public void setAutorLibro(String autorLibro) {
        this.autorLibro = autorLibro;
    }


    public int getPrecioLibro() {
        return precioLibro;
    }


    public void setPrecioLibro(int precioLibro) {
        this.precioLibro = precioLibro;
    }


    public int getValoracionLibro() {
        return valoracionLibro;
    }


    public void setValoracionLibro(int valoracionLibro) {
        this.valoracionLibro = valoracionLibro;
    }

    
}
