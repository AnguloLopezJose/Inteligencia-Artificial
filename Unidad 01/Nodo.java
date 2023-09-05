public class Nodo {
    int valor;
    Nodo nodoHojaIzquierda, nodoHojaDerecha;

    public Nodo(int valor) {
        this.valor = valor;
        this.nodoHojaIzquierda = null;
        this.nodoHojaDerecha = null;
    }

    public String toString() {
        return "El valor del nodo es " + valor;
    }


}