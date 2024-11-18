import java.util.Random;
import java.util.Scanner;

public class HyperSpace {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Random rnd = new Random();
        boolean menuActivo = true;

        while (menuActivo) {
            System.out.print("\033[H\033[2J");
            System.out.flush();

            System.out.println("Bienvenido a:");
            System.out.println("""
                _   ___   _______ ___________   ___________  ___  _____  _____ 
                | | | \\ \\ / / ___ \\  ___| ___ \\ /  ___| ___ \\/ _ \\/  __ \\|  ___|
                | |_| |\\ V /| |_/ / |__ | |_/ / \\ `--.| |_/ / /_\\ \\ /  \\/| |__  
                |  _  | \\ / |  __/|  __||    /   `--. \\  __/|  _  | |    |  __| 
                | | | | | | | |   | |___| |\\ \\  /\\__/ / |   | | | | \\__/\\| |___ 
                \\_| |_/ \\_/ \\_|   \\____/\\_| \\_| \\____/\\_|   \\_| |_\\/____/\\____/
            """);
            System.out.println("1. Iniciar juego\n2. Salir");
            System.out.print("Selecciona una opción: ");
            String opcion = sc.nextLine();

            if (opcion.equals("2")) {
                menuActivo = false;
                System.out.println("¡Hasta luego!");
                continue;
            }

            if (!opcion.equals("1")) {
                System.out.println("Opción inválida. Intenta de nuevo.");
                continue;
            }

            // Instrucciones
            System.out.println("""
                Instrucciones:
                1. Mueve tu nave con 'a' (izquierda) y 'd' (derecha).
                2. Evita los asteroides (*) que caen hacia ti.
                3. Tienes 3 vidas. Pierdes una vida si te golpean.
                4. Sobrevive el mayor tiempo posible.
                Presiona ENTER para empezar...
            """);
            sc.nextLine();

            // Variables del juego
            int ancho = 20, alto = 10;
            int nave = ancho / 2, vidas = 3;
            int[][] asteroides = new int[5][2];
            boolean jugando = true;

            for (int i = 0; i < asteroides.length; i++) {
                asteroides[i][0] = rnd.nextInt(ancho - 2) + 1;
                asteroides[i][1] = rnd.nextInt(alto / 2);
            }

            while (jugando) {
                // Dibujar pantalla
                System.out.print("\033[H\033[2J");
                System.out.flush();
                for (int y = 0; y < alto; y++) {
                    for (int x = 0; x < ancho; x++) {
                        if (y == alto - 1 && x == nave) {
                            System.out.print("\033[34mA\033[0m");
                        } else if (x == 0 || x == ancho - 1) {
                            System.out.print("|");
                        } else {
                            boolean esAsteroide = false;
                            for (int[] asteroide : asteroides) {
                                if (asteroide[0] == x && asteroide[1] == y) {
                                    esAsteroide = true;
                                    break;
                                }
                            }
                            System.out.print(esAsteroide ? "\033[31m*\033[0m" : " ");
                        }
                    }
                    System.out.println();
                }
                System.out.println("Vidas: " + vidas);

                // Mover asteroides
                for (int[] asteroide : asteroides) {
                    asteroide[1]++;
                    if (asteroide[1] >= alto) {
                        asteroide[0] = rnd.nextInt(ancho - 2) + 1;
                        asteroide[1] = 0;
                    }
                }

                // Detectar colisión
                for (int[] asteroide : asteroides) {
                    if (asteroide[1] == alto - 1 && asteroide[0] == nave) {
                        vidas--;
                        System.out.println("¡Impacto! Pierdes una vida.");
                        asteroide[0] = rnd.nextInt(ancho - 2) + 1;
                        asteroide[1] = 0;
                    }
                }

                if (vidas <= 0) {
                    System.out.println("Game Over. Volviendo al menú...");
                    jugando = false;
                }

                // Mover la nave
                if (sc.hasNextLine()) {
                    String mov = sc.nextLine();
                    if (mov.equals("a") && nave > 1) nave--;
                    if (mov.equals("d") && nave < ancho - 2) nave++;
                }
            }
        }
        sc.close();
    }
}
