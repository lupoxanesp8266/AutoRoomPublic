package lupoxan.autoroom.com.autoroom11;

public class Valores {
    private String hora;
    private String pir;
    private String exterior;
    private String interior;

    public Valores(){

    }

    public Valores(String hora, String pir, String exterior, String interior){
        this.hora = hora;
        this.pir = pir;
        this.exterior = exterior;
        this.interior = interior;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public void setPir(String pir) {
        this.pir = pir;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getHora() {
        return hora;
    }

    public String getPir() {
        return pir;
    }

    public String getExterior() {
        return exterior;
    }

    public String getInterior() {
        return interior;
    }

    @Override
    public String toString() {
        return "Hora: " + this.hora + "\tPir: " + this.pir + "\tExterior: " + this.exterior + "\tInterior: " + this.interior;
    }
}
