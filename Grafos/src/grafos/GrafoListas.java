package grafos;

import java.util.ArrayList;

public class GrafoListas {
    ArrayList<Nodos> nodos;

    public GrafoListas(String texto) {
        creaListaVertices(texto);
        agregaAristas(texto);
    }

    private void creaListaVertices(String texto) {
        String[] lineas = texto.split("\\R");
        nodos = new ArrayList<>();
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            if (etiqueta >= 0) {
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                agregarVertice(extremos[0].trim());
                agregarVertice(extremos[1].trim());
            }
        }
    }

    private void agregarVertice(String nombre) {
        if (obtenerNodoPorNombre(nombre) == null) {
            nodos.add(new Nodos(nombre));
        }
    }

    public void display() {
        for (Nodos nodo : nodos) {
            nodo.display();
        }
    }

    private void agregaAristas(String texto) {
        String[] lineas = texto.split("\\R");
        for (String linea : lineas) {
            String arista = linea.trim();
            if (arista.startsWith("{")) {
                arista = arista.substring(1).trim();
            }
            int etiqueta = arista.indexOf('[');
            int peso = 1; // Peso por defecto (igual que en GrafoArrays)
            if (etiqueta >= 0) {
                // Se queda solo con los dígitos: funciona con [5], [label=5] y [label="5"]
                String pesoStr = arista.substring(etiqueta + 1, arista.indexOf(']', etiqueta))
                        .replaceAll("[^0-9]", "");
                try {
                    peso = Integer.parseInt(pesoStr);
                } catch (NumberFormatException e) {
                    System.out.println("Peso inválido en la línea: " + linea);
                }
                arista = arista.substring(0, etiqueta);
            }
            String[] extremos = arista.split("--", 2);
            if (extremos.length == 2) {
                // Se usan los nodos reales de la lista, no copias nuevas
                Nodos nodo1 = obtenerNodoPorNombre(extremos[0].trim());
                Nodos nodo2 = obtenerNodoPorNombre(extremos[1].trim());
                if (nodo1 != null && nodo2 != null) {
                    nodo1.agregarAdyacente(nodo2, peso);
                    nodo2.agregarAdyacente(nodo1, peso); // Para grafos no dirigidos
                }
            }
        }
    }

    private Nodos obtenerNodoPorNombre(String nombre) {
        for (Nodos nodo : nodos) {
            if (nodo.getNombre().equals(nombre)) {
                return nodo;
            }
        }
        return null; // Retorna null si no se encuentra el nodo
    }

    // El estado "visitado" vive dentro de cada nodo, así que hay que limpiarlo antes de cada recorrido
    private void reiniciarVisitados() {
        for (Nodos nodo : nodos) {
            nodo.setVisitado(false);
        }
    }

    public void DFS(String verticeInicial, String verticeFinal) {
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(verticeFinal);

        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        reiniciarVisitados();
        System.out.println("Recorrido DFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        java.util.Stack<Nodos> stack = new java.util.Stack<>();
        stack.push(nodoInicial);
        while (!stack.isEmpty()) {
            Nodos nodoActual = stack.pop();
            if (!nodoActual.isVisitado()) {
                nodoActual.setVisitado(true);
                System.out.print(nodoActual.getNombre() + " ");
                if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    return;
                }
            }
            // Agregar vecinos no visitados al stack
            for (Nodos vecino : nodoActual.getAdyacentes()) {
                if (!vecino.isVisitado()) {
                    stack.push(vecino);
                }
            }
        }
    }

    public void BFS(String verticeInicial, String verticeFinal) {
        Nodos nodoInicial = obtenerNodoPorNombre(verticeInicial);
        Nodos nodoFinal = obtenerNodoPorNombre(verticeFinal);

        if (nodoInicial == null || nodoFinal == null) {
            System.out.println("Uno o ambos vértices no existen en el grafo.");
            return;
        }
        reiniciarVisitados();
        System.out.println("Recorrido BFS desde " + verticeInicial + " hasta " + verticeFinal + ":");
        java.util.Queue<Nodos> queue = new java.util.LinkedList<>();
        queue.add(nodoInicial); // Agregar el vértice inicial a la cola
        while (!queue.isEmpty()) {
            Nodos nodoActual = queue.poll();
            if (!nodoActual.isVisitado()) {
                nodoActual.setVisitado(true);
                System.out.print(nodoActual.getNombre() + " ");
                if (nodoActual.getNombre().equals(nodoFinal.getNombre())) {
                    System.out.println("\nSe ha alcanzado el vértice final.");
                    return;
                }
            }
            // Agregar vecinos no visitados a la cola
            for (Nodos vecino : nodoActual.getAdyacentes()) {
                if (!vecino.isVisitado()) {
                    queue.add(vecino);
                }
            }
        }
    }
}