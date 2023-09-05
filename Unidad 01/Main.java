import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scr = new Scanner(System.in);
        int opcion = 0, valor;
        Arbol arbol = new Arbol();

        do {
            System.out.println("""
                Ingresar opcion:
                1.- Insertar nodo
                2.- Mostrar arbol por el metodo de busqueda inOrden
                3.- Mostrar arbol por el metodo de busqueda preOrden
                4.- Mostrar arbol por el metodo de busqueda postOrden
                5.- Buscar un nodo que se encuentre en el arbol
                6.- Eliminar un nodo que se encuentre en el arbol
                7.- Salir del programa
                """);
            opcion = scr.nextInt();

            switch (opcion){
                case 1:
                    System.out.println("Ingresar valor para el nodo:");
                    arbol.insetarNodo(scr.nextInt());
                    System.out.println("El nodo ha sido creado");
                    break;
                case 2:
                    arbol.inOrden(arbol.raiz);
                    break;
                case 3:
                    arbol.preOrden(arbol.raiz);
                    break;
                case 4:
                    arbol.postOrden(arbol.raiz);
                    break;
                case 5:
                    if(!arbol.esVacio()) {
                        System.out.println("Ingresar el valor del nodo a buscar:");
                        scr.nextLine();
                        valor = scr.nextInt();
                        if(arbol.buscarNodo(valor) == null) {
                            System.out.println("No se encontro el nodo en el arbol");
                        }
                        else {
                            System.out.println("Nodo encontrado");
                        }
                    }
                    else {
                        System.out.println("El arbol se encuentra vacio");
                    }
                    break;
                case 6:
                    if(!arbol.esVacio()) {
                        System.out.println("Ingresar el valor del nodo a remover:");
                        scr.nextLine();
                        valor = scr.nextInt();
                        if(!arbol.removerNodo(valor)) {
                            System.out.println("No se encontro el nodo en el arbol");
                        }
                        else {
                            System.out.println("El nodo ha sido removido del arbol");
                        }
                    } else {
                        System.out.println("El arbol se encuentra vacio");
                    }
                    break;

                default:
                    System.out.println("Opcion no valida");
            }
            System.out.println();
        }while(opcion != 7);
        System.out.println("Que tenga un buen dia :)");
    }
}
