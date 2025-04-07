import java.util.Scanner;

public class BigVigenere {
    private int[] key;
    private char[][] Alphabet;
    private char[] alphabet;

    public BigVigenere() {
        Scanner scanner = new Scanner(System.in);

        alphabet = new char[64];
        int aux = 0;

        for (char c = 'a'; c <= 'z'; c++) {
            if (c == 'n') {
                alphabet[aux++] = c;
                alphabet[aux++] = 'ñ';
            } else {
                alphabet[aux++] = c;
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'N') {
                alphabet[aux++] = c;
                alphabet[aux++] = 'Ñ';
            } else {
                alphabet[aux++] = c;
            }
        }

        for (char c = '0'; c <= '9'; c++) {
            alphabet[aux++] = c;
        }

        System.out.print("Ingrese la clave: ");
        String input = scanner.nextLine();
        char[] keys = input.toCharArray();

        key = new int[keys.length];
        for (int i = 0; i < keys.length; i++) {
            key[i] = indiceal(keys[i]);
            if (key[i] == -1) {
                System.out.println("Carácter inválido en la clave: " + keys[i]);
                return;
            }
        }

        Alphabet = new char[64][64];
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                Alphabet[i][j] = alphabet[(j + i) % 64];
            }
        }


        System.out.println("Clave numérica generada:");
        for (int i = 0; i < key.length; i++) {
            System.out.print(key[i] + " ");
        }
        System.out.println();
    }

    public BigVigenere(String numericKey) {
        alphabet = new char[64];
        int aux = 0;

        for (char c = 'a'; c <= 'z'; c++) {

            if (c == 'n') {alphabet[aux++] = c; alphabet[aux++] = 'ñ';}
            else{alphabet[aux++] = c;}
        }
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'n') {alphabet[aux++] = c; alphabet[aux++] = 'ñ';}
            else{alphabet[aux++] = c;}
        }
        for (char c = '0'; c <= '9'; c++) {
            alphabet[aux++] = c;
        }

        char[] chars = numericKey.toCharArray();
        key = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            key[i] = indiceal(chars[i]);
        }

        Alphabet = new char[64][64];
        for (int i = 0; i < 64; i++) {
            for (int j = 0; j < 64; j++) {
                Alphabet[i][j] = alphabet[(j + i) % 64];
            }
        }


        System.out.print("Clave numérica: ");
        for (int i = 0; i < key.length; i++) {
            System.out.print(key[i] + " ");
        }
        System.out.println();
    }

    public int indiceal(char c) {
        for (int i = 0; i < 64; i++) {
            if (alphabet[i] == c) {
                return i;
            }
        }
        return -1;
    }

    public String encrypt(String message) {
        String mensajeCifrado = "";
        int indice = 0;

        for (int i = 0; i < message.length(); i++) {                               //For para recorrer los caracteres del mensaje original
            char caracterOriginal = message.charAt(i);                             //Esto es que el .charAT me da el caracter de un string (en este caso message) en la posicion i.   Ej: mensaje hola; mensaje.charAT(0) devuelve h
            int claveUsar = key[indice % key.length];                              //Es pa saber que numero de la key se usa para encriptar el mensaje
            char caracterCifrado = caracterOriginal;                               //Pa ir guardando el caracter cifrado

            for (int j = 0; j < Alphabet[0].length; j++) {                         //For pa recorrer la primera fila del alfabeto
                if (Alphabet[0][j] == caracterOriginal) {                          //Esto es para saber en que columna está nuestro caracter a desifrar, así despues bajamos hasta la fila que nos diga la key y esa seria nuestro caracter cifrado
                    caracterCifrado = Alphabet[claveUsar % Alphabet.length][j];    //En caso de que la key sea mayor al tamaño del alfabeto, esto lo arregla
                    break;
                }
            }
            indice++;
            mensajeCifrado += caracterCifrado;
        }
        return mensajeCifrado;
    }

    public String decrypt(String encryptedMessage) {
        String mensajeDescifrado = "";
        int indice = 0;

        for (int i = 0; i < encryptedMessage.length(); i++) {
            char caractercifrado = encryptedMessage.charAt(i);
            int claveUsar = key[indice % key.length];
            char caracterOriginal = caractercifrado;

            for (int j = 0; j < Alphabet[claveUsar].length; j++) {
                if (Alphabet[claveUsar][j] == caractercifrado) {
                    caracterOriginal = Alphabet[0][j];
                    break;
                }
            }
            indice++;
            mensajeDescifrado += caracterOriginal;
        }
        return mensajeDescifrado;
    }

    public void reEncrypt(){
        Scanner entrada = new Scanner(System.in);

        System.out.println("Ingrese el mensaje encriptado: ");                  //pedir el mensaje cifrado
        String mensajeCifrado = entrada.nextLine();

        String mensajeDescifrado = decrypt(mensajeCifrado);                     //descifrandolo con la clave actual
        System.out.println("El mensaje descifrado es: " + mensajeDescifrado);

        System.out.println("Ingrese la nueva clave: ");                         //Pidiendo la nueva clave numerica
        String nuevaClave = entrada.nextLine();

        int[] nuevaKey = new int[nuevaClave.length()];                                   //convierte la clave string a int
        for (int i = 0; i < nuevaKey.length; i++) {
            nuevaKey[i] = Character.getNumericValue(nuevaClave.charAt(i));
        }

        key = nuevaKey;                                                         //Actualiza la Key y cifra de nuevo
        String nuevoCifrado = encrypt(mensajeDescifrado);

        System.out.println("El nuevo mensaje cifrado es: " + nuevoCifrado);


    }

    public char search (int position){
        int contador = 0;
        for (int i = 0; i < Alphabet.length; i++) {
            for (int j = 0; j < Alphabet[i].length; j++) {
                if (contador == position) {
                    return Alphabet[i][j];
                }
                contador++;
            }
        }
        System.out.println("No se encontro el caracter de esa posición");
        return '\0';
    }

    public char optimalSearch(int position) {
        int fila = position / 64;
        int columna = position % 64;
        return Alphabet[fila][columna];
    }

    public static void main(String[] args) {
            Scanner entrada = new Scanner(System.in);

            System.out.println(" BigVigenere");
            BigVigenere cifrador = new BigVigenere();

            System.out.println("ENCRIPTAR");
            System.out.print("Ingrese el mensaje a encriptar: ");
            String mensajeOriginal = entrada.nextLine();
            String mensajeCifrado = cifrador.encrypt(mensajeOriginal);
            System.out.println("Mensaje encriptado: " + mensajeCifrado);

            System.out.println("DESENCRIPTAR ");
            String mensajeDescifrado = cifrador.decrypt(mensajeCifrado);
            System.out.println("Mensaje descifrado: " + mensajeDescifrado);

            System.out.println("RE ENCRIPTAR");
            cifrador.reEncrypt();

            System.out.println("BUSCAR LETRA");
            System.out.print("Ingrese una posicion entre (0-4095) para buscar: ");
            int posicion = entrada.nextInt();
            char buscarchar = cifrador.search(posicion);
            System.out.println("Resultado con search(): " + buscarchar);

            System.out.println("BUSCAR LETRA DE MANERA OPTIMA");
            System.out.print("Ingrese una posición entre (0-4095) para buscar: ");
            int posicionoptima = entrada.nextInt();
            char caracter = cifrador.optimalSearch(posicionoptima);
            System.out.println("El caracter es:" + caracter);

        }
    }

