public class Arbol {
    Nodo raiz;

    public Arbol() {
        raiz = null;
    }

    public boolean esVacio() {
        return raiz == null;
    }

    public void insetarNodo(int valor) {
        Nodo nuevo = new Nodo(valor);
        if(raiz == null) {
            raiz = nuevo;
        } else {
            Nodo auxiliar = raiz;
            Nodo padre;
            while(true) {
                padre = auxiliar;
                if(valor < auxiliar.valor) {
                    auxiliar = auxiliar.nodoHojaIzquierda;
                    if(auxiliar == null) {
                        padre.nodoHojaIzquierda = nuevo;
                        return;
                    }
                }
                else {
                    auxiliar = auxiliar.nodoHojaDerecha;
                    if(auxiliar == null) {
                        padre.nodoHojaDerecha = nuevo;
                        return;
                    }
                }
            }
        }
    }

    public void inOrden(Nodo nodo) {
        if(nodo != null) {
            inOrden(nodo.nodoHojaIzquierda);
            System.out.print(nodo.valor + " ");
            inOrden(nodo.nodoHojaDerecha);
        }
    }

    public void preOrden(Nodo nodo) {
        if(nodo != null) {
            System.out.print(nodo.valor + " ");
            preOrden(nodo.nodoHojaIzquierda);
            preOrden(nodo.nodoHojaDerecha);
        }
    }

    public void postOrden(Nodo nodo) {
        if(nodo != null) {
            postOrden(nodo.nodoHojaIzquierda);
            postOrden(nodo.nodoHojaDerecha);
            System.out.print(nodo.valor + " ");
        }
    }

    public Nodo buscarNodo(int valor) {
        Nodo aux = raiz;
        while(aux.valor != valor) {
            if(valor < aux.valor) {
                aux = aux.nodoHojaIzquierda;
            }
            else {
                aux = aux.nodoHojaDerecha;
            }
            if(aux == null) {
                return null;
            }
        }
        return aux;
    }

    public boolean removerNodo(int valor) {
        Nodo padre = raiz;
        Nodo aux = raiz;
        boolean esNodoHojaIzquierda = true;

        while(aux.valor != valor) {
            padre = aux;
            if(valor < aux.valor) {
                esNodoHojaIzquierda = true;
                aux = aux.nodoHojaIzquierda;
            }
            else {
                esNodoHojaIzquierda = false;
                aux = aux.nodoHojaDerecha;
            }
            if(aux == null) {
                return false;
            }
        }
        if(aux.nodoHojaIzquierda == null && aux.nodoHojaDerecha == null) {
            if(aux == raiz) {
                raiz = null;
            }
            else if(esNodoHojaIzquierda) {
                padre.nodoHojaIzquierda = null;
            }
            else {
                padre.nodoHojaDerecha = null;
            }
        }
        else if(aux.nodoHojaDerecha == null) {
            if(aux == raiz) {
                raiz=aux.nodoHojaIzquierda;
            }
            else if(esNodoHojaIzquierda) {
                padre.nodoHojaIzquierda = aux.nodoHojaIzquierda;
            }
            else {
                padre.nodoHojaDerecha = aux.nodoHojaIzquierda;
            }
        }
        else if(aux.nodoHojaIzquierda == null) {
            if(aux == raiz) {
                raiz = aux.nodoHojaDerecha;
            }
            else if(esNodoHojaIzquierda) {
                padre.nodoHojaIzquierda = aux.nodoHojaDerecha;
            }
            else {
                padre.nodoHojaDerecha = aux.nodoHojaIzquierda;
            }
        }
        else {
            Nodo reemplazo= remplazarNodo(aux);
            if(aux == raiz) {
                raiz = reemplazo;
            }
            else if(esNodoHojaIzquierda) {
                padre.nodoHojaIzquierda = reemplazo;
            }
            else {
                padre.nodoHojaDerecha = reemplazo;
            }
            reemplazo.nodoHojaIzquierda = aux.nodoHojaIzquierda;
        }
        return true;
    }

    public Nodo remplazarNodo(Nodo nodo) {
        Nodo reemplazo = nodo;
        Nodo reemplazoPadre = nodo;
        Nodo aux = nodo.nodoHojaDerecha;

        while(aux != null) {
            reemplazoPadre = reemplazo;
            reemplazo = aux;
            aux = aux.nodoHojaIzquierda;
        }
        if(reemplazo != nodo.nodoHojaDerecha) {
            reemplazoPadre.nodoHojaIzquierda = reemplazo.nodoHojaDerecha;
            reemplazo.nodoHojaDerecha = nodo.nodoHojaDerecha;
        }
        System.out.println("Nodo de reemplazo es " + reemplazo);
        return reemplazo;
    }
}
