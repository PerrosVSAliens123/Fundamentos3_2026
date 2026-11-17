package grafos;

import java.util.ArrayList;

public class Nodos {
    String nombre;
    ArrayList<Nodos> adyacentes;
    ArrayList<Integer> pesos; // pesos.get(i) es el peso de la arista hacia adyacentes.get(i)
    boolean visitado;

    public Nodos(String nombre) {
        this.nombre = nombre;
        this.visitado = false;
        this.adyacentes = new ArrayList<>();
        this.pesos = new ArrayList<>();
    }

    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public boolean isVisitado() {
        return visitado;
    }

    public void agregarAdyacente(Nodos nodo, int peso) {
        adyacentes.add(nodo);
        pesos.add(peso);
    }

    public void display() {
        System.out.print(nombre);
        System.out.print("| Adyacentes: ");
        for (int i = 0; i < adyacentes.size(); i++) {
            System.out.print(adyacentes.get(i).nombre + ": " + pesos.get(i) + " ");
        }
        System.out.println();
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Nodos> getAdyacentes() {
        return adyacentes;
    }

    public int getPeso(int indice) {
        return pesos.get(indice);
    }
}