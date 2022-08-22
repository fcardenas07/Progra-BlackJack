import java.util.*;

public class Blackjack {
    public static void main(String[] args) {
        jugar();
    }

    public static void jugar() {
        var baraja = crearBaraja();
        barajar(baraja);

        String[] manoJugador = crearMano();
        String[] manoDealer = crearMano();

        repartir(baraja, manoJugador);
        repartir(baraja, manoDealer);

        mostrarMano(manoJugador);
        mostrarMano(manoDealer);

        var manoJugadorSinPinta = quitarPintaACartas(manoJugador);
        var valoresManoJugador = obtenerValoresCartas(manoJugadorSinPinta);
        var manoDealerSinPinta = quitarPintaACartas(manoDealer);
        var valoresManoDealer = obtenerValoresCartas(manoDealerSinPinta);

        int sumaManoJugador = sumarValoresMano(valoresManoJugador);
        int sumaManoDealer = sumarValoresMano(valoresManoDealer);

        if (sePasoDe21(sumaManoJugador)) {
            System.out.println("Ha perdido");
            return;
        }

        if (esBlackjack(sumaManoJugador)) {
            boolean esGanador = verificarGanador(sumaManoJugador, sumaManoDealer);
            if (esGanador) {
                System.out.println("Ha ganado");
                return;
            }
        }

        int jugada = preguntarJugada();
        while (jugada == 1) {
            manoJugador = pedirCarta(baraja, manoJugador);
            mostrarMano(manoJugador);

            manoJugadorSinPinta = quitarPintaACartas(manoJugador);
            valoresManoJugador = obtenerValoresCartas(manoJugadorSinPinta);
            sumaManoJugador = sumarValoresMano(valoresManoJugador);

            if (sePasoDe21(sumaManoJugador)) {
                System.out.println(sumaManoJugador);
                System.out.println("Ha perdido");
                return;
            }
            jugada = preguntarJugada();
        }


        while (sumaManoDealer <= 16) {
            manoDealer = pedirCarta(baraja, manoDealer);
            mostrarMano(manoDealer);
            manoDealerSinPinta = quitarPintaACartas(manoDealer);
            valoresManoDealer = obtenerValoresCartas(manoDealerSinPinta);
            sumaManoDealer = sumarValoresMano(valoresManoDealer);

            if (sePasoDe21(sumaManoDealer)) {
                System.out.println("Ha ganado");
                return;
            }
        }

        System.out.println("Resultados");
        bajarse(manoJugador, manoDealer);

        boolean esGanador = verificarGanador(sumaManoJugador, sumaManoDealer);
        if (esGanador) {
            System.out.println("Ha ganado");
        } else {
            System.out.println("Ha perdido");
        }
    }

    public static boolean verificarGanador(int sumaManoJugador, int sumaManoDealer) {
        if (esBlackjack(sumaManoDealer)) {
            return false;
        } else if (esBlackjack(sumaManoJugador) && !esBlackjack(sumaManoDealer)) {
            return true;
        } else return sumaManoJugador > sumaManoDealer;

    }

    public static int preguntarJugada() {
        mostrarMenu();
        return ingresarOpcion();
    }

    public static String[] pedirCarta(Stack<String> baraja, String[] mano) {
        var manoArrayList = new ArrayList<>(Arrays.asList(mano));
        manoArrayList.add(baraja.pop());
        return manoArrayList.toArray(new String[0]);
    }

    public static int ingresarOpcion() {
        Scanner teclado = new Scanner(System.in);
        boolean esNumero = false;
        int opcion = 0;

        do {
            try {
                opcion = teclado.nextInt();
                esNumero = true;
            } catch (Exception e) {
                teclado.next();
                System.out.println("Ingrese una opcion valida");
            }
        } while (!esNumero);

        if (opcion < 1 || opcion > 2) {
            System.out.println("Ingrese una opcion valida");
            return ingresarOpcion();
        }
        return opcion;
    }

    public static void mostrarMenu() {
        System.out.println("Menu. Seleccione una opcion: ");
        System.out.println("1-> Robar Carta");
        System.out.println("2-> Bajarse");
    }

    public static void bajarse(String[] manoJugador, String[] manoDealer) {
        var manoJugadorSinPinta = quitarPintaACartas(manoJugador);
        var valoresManoJugador = obtenerValoresCartas(manoJugadorSinPinta);
        var manoDealerSinPinta = quitarPintaACartas(manoDealer);
        var valoresManoDealer = obtenerValoresCartas(manoDealerSinPinta);

        int sumaManoJugador = sumarValoresMano(valoresManoJugador);
        int sumaManoDealer = sumarValoresMano(valoresManoDealer);

        System.out.print("Jugador = " + sumaManoJugador + " ");
        mostrarMano(manoJugador);
        System.out.print("Dealer = " + sumaManoDealer + " ");
        mostrarMano(manoDealer);
    }

    public static boolean sePasoDe21(int mano) {
        return mano > 21;
    }

    public static Boolean esBlackjack(int mano) {
        return mano == 21;
    }

    public static void barajar(Stack<String> baraja) {
        Collections.shuffle(baraja);
    }

    public static int sumarValoresMano(List<Integer> mano) {
        int suma = 0;
        for (var valor : mano) {
            suma += valor;
        }
        return suma;
    }

    public static ArrayList<String> quitarPintaACartas(String[] mano) {
        var cartasSinPinta = new ArrayList<String>();
        for (var carta : mano) {
            var valoresCarta = carta.split(" ");
            cartasSinPinta.add(valoresCarta[0]);
        }
        return cartasSinPinta;
    }

    public static List<Integer> obtenerValoresCartas(List<String> mano) {

        var valoresCartas = new ArrayList<Integer>();
        var mapa = crearMapa();

        for (var carta : mano) {
            valoresCartas.add(mapa.get(carta));
        }

        return valoresCartas;
    }

    public static HashMap<String, Integer> crearMapa() {

        var cartas = List.of("AS", "DOS", "TRES", "CUATRO", "CINCO",
                "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER");
        var mapa = new HashMap<String, Integer>();
        var valoresCartas = List.of(11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10);

        for (int i = 0; i < cartas.size(); i++) {
            mapa.put(cartas.get(i), valoresCartas.get(i));
        }
        return mapa;
    }

    public static void mostrarMano(String[] mano) {
        System.out.println(Arrays.toString(mano));
    }

    public static void repartir(Stack<String> baraja, String[] mano) {
        for (int i = 0; i < mano.length; i++) {
            mano[i] = baraja.pop();
        }
    }

    public static String[] crearMano() {
        return new String[2];
    }

    public static Stack<String> crearBaraja() {
        String[] pintas = new String[]{"CORAZON", "DIAMANTE", "TREBOL", "PICA"};
        String[] numerosCartas = new String[]{"AS", "DOS", "TRES", "CUATRO", "CINCO",
                "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER"};
        Stack<String> baraja = new Stack<>();

        for (String pinta : pintas) {
            for (String numerosCarta : numerosCartas) {
                baraja.push(numerosCarta.concat(" ").concat(pinta));
            }
        }
        return baraja;
    }
}

