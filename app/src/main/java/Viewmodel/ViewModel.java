package Viewmodel;

public class ViewModel extends androidx.lifecycle.ViewModel {
    private String resultado;

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }
}
