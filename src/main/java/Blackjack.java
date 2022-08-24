import java.util.*;

public class Blackjack {
    public static void main(String[] args) {
        jugarBlackjack();
    }

    private static void jugarBlackjack() {
        int volverAJugar;
        do {
            var baraja = crearBaraja();
            barajar(baraja);

            String[] manoJugador = crearMano();
            String[] manoDealer = crearMano();

            repartir(baraja, manoJugador);
            repartir(baraja, manoDealer);

            mostrarManos(manoJugador, manoDealer, 0);

            int sumaManoJugador = sumarMano(manoJugador);

            if (!esBlackjack(sumaManoJugador)) {
                manoJugador = turnoJugador(baraja, manoJugador);
            }

            if (!sePasoDe21(sumarMano(manoJugador))) {
                manoDealer = turnoDealer(baraja, manoDealer);
            }

            bajarse(manoJugador, manoDealer);
            volverAJugar = preguntarSiVuelveAJugar();

        } while (volverAJugar == 1);
    }

    private static void mostrarManos(String[] manoJugador, String[] manoDealer, int numeroJugada) {
        System.out.print("Jugador = " + sumarMano(manoJugador) + "; ");
        mostrarMano(manoJugador);

        if (numeroJugada == 0) {
            System.out.print("Dealer = ");
            mostrarMano(ocultarManoDealer(manoDealer));
        } else {
            System.out.print("Dealer = " + sumarMano(manoDealer) + "; ");
            mostrarMano(manoDealer);
        }
    }

    private static String[] ocultarManoDealer(String[] manoDealer) {
        return new String[]{"**************", manoDealer[1]};
    }

    private static int preguntarSiVuelveAJugar() {
        mostrarMenuVolverAJugar();
        return ingresarOpcion();
    }
    private static String[] turnoDealer(Stack<String> baraja, String[] manoDealer) {
        while (sumarMano(manoDealer) <= 16) {
            manoDealer = pedirCarta(baraja, manoDealer);
        }
        return manoDealer;
    }

    private static int sumarMano(String[] mano) {
        var manoSinPinta = quitarPintaACartas(mano);
        var valoresMano = obtenerValoresCartas(manoSinPinta);
        return sumarValoresMano(valoresMano);
    }

    private static String[] turnoJugador(Stack<String> baraja, String[] manoJugador) {
        int jugada = preguntarJugada();
        int sumaManoJugador = sumarMano(manoJugador);

        while (jugada == 1 && !sePasoDe21(sumaManoJugador)) {
            manoJugador = pedirCarta(baraja, manoJugador);
            mostrarMano(manoJugador);
            sumaManoJugador = sumarMano(manoJugador);

            if (!sePasoDe21(sumaManoJugador) && !esBlackjack(sumaManoJugador)) {
                jugada = preguntarJugada();
            }
            if (esBlackjack(sumaManoJugador)) {
                jugada = 0;
            }
        }
        return manoJugador;
    }

    private static boolean verificarGanador(int sumaManoJugador, int sumaManoDealer) {
        if (sePasoDe21(sumaManoJugador) || esBlackjack(sumaManoDealer)) {
            return false;
        } else if (esBlackjack(sumaManoJugador) || sePasoDe21(sumaManoDealer)) {
            return true;
        } else {
            return sumaManoJugador > sumaManoDealer;
        }
    }

    private static int preguntarJugada() {
        mostrarMenuJugada();
        return ingresarOpcion();
    }

    private static String[] pedirCarta(Stack<String> baraja, String[] mano) {
        var manoArrayList = new ArrayList<>(Arrays.asList(mano));
        manoArrayList.add(baraja.pop());
        return manoArrayList.toArray(new String[0]);
    }

    private static int ingresarOpcion() {
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

    private static void mostrarMenuJugada() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Su turno. Seleccione una opcion: ");
        System.out.println("1-> Robar Carta");
        System.out.println("2-> Bajarse");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void mostrarMenuVolverAJugar() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("Desea volver a jugar?. Seleccione una opcion: ");
        System.out.println("1-> SI");
        System.out.println("2-> NO");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

    }

    private static void bajarse(String[] manoJugador, String[] manoDealer) {
        int sumaManoJugador = sumarMano(manoJugador);
        int sumaManoDealer = sumarMano(manoDealer);
        boolean esJugadorGanador = verificarGanador(sumaManoJugador, sumaManoDealer);

        mostrarResultados(manoJugador, manoDealer, sumaManoDealer, esJugadorGanador);
    }

    private static void mostrarResultados(String[] manoJugador, String[] manoDealer, int sumaManoDealer, boolean esJugadorGanador) {
        mostrarManos(manoJugador, manoDealer, sumaManoDealer);
        mostrarMensaje(esJugadorGanador);
    }

    private static void mostrarMensaje(boolean esJugadorGanador) {
        if (esJugadorGanador) {
            System.out.println("Ha Ganado!");
        } else {
            System.out.println("Ha Perdido!");
        }
    }

    private static boolean sePasoDe21(int mano) {
        return mano > 21;
    }

    private static Boolean esBlackjack(int mano) {
        return mano == 21;
    }

    private static void barajar(Stack<String> baraja) {
        Collections.shuffle(baraja);
    }

    private static int sumarValoresMano(List<Integer> mano) {
        int suma = 0;
        for (var valor : mano) {
            suma += valor;
        }
        return suma;
    }

    private static ArrayList<String> quitarPintaACartas(String[] mano) {
        var cartasSinPinta = new ArrayList<String>();
        for (var carta : mano) {
            var valoresCarta = carta.split(" ");
            cartasSinPinta.add(valoresCarta[0]);
        }
        return cartasSinPinta;
    }

    private static List<Integer> obtenerValoresCartas(List<String> mano) {
        var valoresCartas = new ArrayList<Integer>();
        var mapa = crearMapa();

        for (var carta : mano) {
            valoresCartas.add(mapa.get(carta));
        }
        return valoresCartas;
    }

    private static HashMap<String, Integer> crearMapa() {
        var cartas = List.of("AS", "DOS", "TRES", "CUATRO", "CINCO",
                "SEIS", "SIETE", "OCHO", "NUEVE", "DIEZ", "JOTA", "QUINA", "KAISER");
        var mapa = new HashMap<String, Integer>();
        var valoresCartas = List.of(11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10);

        for (int i = 0; i < cartas.size(); i++) {
            mapa.put(cartas.get(i), valoresCartas.get(i));
        }
        return mapa;
    }

    private static void mostrarMano(String[] mano) {
        System.out.println(Arrays.toString(mano));
    }

    private static void repartir(Stack<String> baraja, String[] mano) {
        for (int i = 0; i < mano.length; i++) {
            mano[i] = baraja.pop();
        }
    }

    private static String[] crearMano() {
        return new String[2];
    }

    private static Stack<String> crearBaraja() {
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

