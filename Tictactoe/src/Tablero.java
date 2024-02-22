public class Tablero {
    private Fichas[][] casillas;

    public Tablero() {
        casillas = new Fichas[3][3];
        reiniciarTablero();
    }

    public void reiniciarTablero() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                casillas[i][j] = Fichas.VACIO;
            }
        }
    }

    public boolean validarMovimiento(int fila, int columna) {
        return fila >= 0 && fila < 3 && columna >= 0 && columna < 3 && casillas[fila][columna] == Fichas.VACIO;
    }

    public void setFicha(int fila, int columna, Fichas ficha) {
        casillas[fila][columna] = ficha;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                builder.append(casillas[i][j] == Fichas.VACIO ? "-" : casillas[i][j]);
                builder.append(" ");
            }
            builder.append("\n");
        }
        return builder.toString();
    }
}
