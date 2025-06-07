package model;

public enum Nivel {
    NO_DEFINIDO(0), // Nivel no definido
    PRINCIPIANTE(1), // Nivel principiante
    INTERMEDIO(2), // Nivel intermedio
    AVANZADO(3); // Nivel avanzado

    private final int valor;

    Nivel(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }
}
