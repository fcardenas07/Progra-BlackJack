import java.util.Arrays;

public class Blackjack {
    public static void main(String[] args) {
        jugar();
    }

    private static void jugar() {
        String[][] baraja = crearBaraja();
        String[] manoJugador = crearMano();
        String[] manoDealer = crearMano();
        repartir(baraja, manoJugador);
        repartir(baraja, manoDealer);
        bajarse(manoJugador, manoDealer);
        boolean veintiuno = sePasoDe21(manoJugador);
    }

    private static boolean sePasoDe21(String[] manoJugador) {
        int[] numeros = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        return false;
    }

    private static void bajarse(String[] manoJugador, String[] manoDealer) {
        System.out.print("Jugador = ");
        mostrarMano(manoJugador);
        System.out.print("Dealer = ");
        mostrarMano(manoDealer);
    }

    private static void mostrarMano(String[] mano) {
        System.out.println(Arrays.toString(mano));
    }

    private static void repartir(String[][] baraja, String[] mano) {
        for (int i = 0; i < mano.length; i++) {
            int j = (int) (Math.random() * baraja[0].length);
            int k = (int) (Math.random() * baraja[1].length);
            mano[i] = "".concat(baraja[1][k]).concat(" ").concat(baraja[0][j]);
        }
    }

    private static String[] crearMano() {
        return new String[2];
    }

    private static String[][] crearBaraja() {
        String[] pintas = new String[]{"Corazon", "Diamante", "Trebol", "Pica"};
        String[] numerosCartas = new String[]{"As", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Siete", "Ocho",
                "Nueve", "Diez", "Jota", "Quina", "Kaiser"};
        return new String[][]{pintas, numerosCartas};
    }
}

