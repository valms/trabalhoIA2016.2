package algoritmo;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * UNIVERSIDADE DE FORTALEZA - I.A. 2016.2
 *
 * @author Valmar Júnior - 1120793 / 6
 * @author João Pedro - 1211207 / 6
 */
@SuppressWarnings(value = {"all"})
public class Ladrao extends ProgramaLadrao {

    int ultimaAcaoAgente = 0;
    int matrizVisao[][] = new int[5][5];
    int matrizOfalto[][] = new int[3][3];
    int matrizLocaisVisiatados[][] = new int[30][30];
    boolean persinguindo;
    int moedas;
    int posObj;
    Point ultimaPosicaoAgente = new Point();
    Point proximaPosicaoAgente = new Point();
    boolean parado;
    boolean procurandoBanco;
    int acao;
    Double frente;
    Double atras;
    Double direita;
    Double esquerda;
    int dBank;
    int zonaSegura;
    Point posBank = new Point();
    int[] pBank = new int[]{posBank.y, posBank.x};
    int[] pAtual;
    int foraDaZona;
    boolean descubriuBanco = false;

    int objetivo;
    int obstaculo;
    int moeda;
    int espacoVazio;
    int aliado;
    int ultimaAcao;
    int parede;
    int foraMundo;
    int banco;
    int pastilhaPoder;
    int constanteOfalto;
    int ultimoPonto;

    public int acao() {

        HashMap<Double, Integer> hmap = new HashMap<Double, Integer>();
        ArrayList<Double> auxList = new ArrayList<Double>();

        pAtual = new int[]{sensor.getPosicao().y, sensor.getPosicao().x};

        frente = 0d;
        atras = 0d;
        direita = 0d;
        esquerda = 0d;
        zonaSegura = 15;
        foraDaZona = -10000;
        acao = 0;
        persinguindo = false;
        parado = false;
        procurandoBanco = false;

        matrizVisao = getMatrizVisao();
        matrizOfalto = getMatrizOfalto();

        System.out.println("\n" + this.hashCode());

        ponderacoes();

        ultimaPosicaoAgente = sensor.getPosicao();

        frente = calcularFrente(matrizVisao, matrizOfalto);
        atras = calcularAtras(matrizVisao, matrizOfalto);
        direita = calcularDireita(matrizVisao, matrizOfalto);
        esquerda = calcularEsquerda(matrizVisao, matrizOfalto);

        hmap.put(frente, 1);
        hmap.put(atras, 2);
        hmap.put(direita, 3);
        hmap.put(esquerda, 4);

        auxList.add(atras);
        auxList.add(direita);
        auxList.add(esquerda);
        auxList.add(frente);

        Collections.sort(auxList);

        acao = hmap.get(auxList.get(auxList.size() - 1));

        if (sensor.getNumeroDeMoedas() > moedas) {
            moedas = sensor.getNumeroDeMoedas();
        }

        System.out.print("Ação: " + acao);

        ultimaAcaoAgente = acao;

        incrementaPontoVisitado(acao);

        System.out.print(" - ");

        switch (acao) {
            case 1:
                System.out.println("Frente");
                break;
            case 2:
                System.out.println("Atras");
                break;
            case 3:
                System.out.println("Direita");
                break;
            case 4:
                System.out.println("Esquerda");
                break;
            default:
                break;
        }

        System.out.println("Ladrao x: " + pAtual[1] + " y: " + pAtual[0]);

        System.out.println("Banco x: " + pBank[1] + " x: " + pBank[0]);

        return acao;
    }

    public void ponderacoes() {

        if (persinguindo) {

            objetivo = 500;
            obstaculo = -100;
            moeda = 0;
            espacoVazio = 0;
            aliado = 0;
            ultimaAcao = 0;
            parede = 0;
            foraMundo = 0;
            banco = 100;
            pastilhaPoder = 0;
            constanteOfalto = 100;
            ultimoPonto = 0;

            System.out.println("\n" + this.hashCode() + " ----------------- Modo Chasing");

        } else {

            objetivo = 0;
            obstaculo = -200;
            moeda = -2;
            espacoVazio = 5;
            aliado = 0;
            ultimaAcao = -30;
            parede = -10;
            foraMundo = -5;
            banco = 150;
            pastilhaPoder = 3;
            constanteOfalto = 100;
            ultimoPonto = -30;

            System.out.println("\n" + this.hashCode() + " ---------------- Modo Searching");
        }

        if (ultimaPosicaoAgente.equals(sensor.getPosicao())) {
            espacoVazio = 500;
            parede = -100;
        }

        if (procurandoBanco && persinguindo) {
            ultimoPonto = 0;
        }

        if (procurandoBanco || persinguindo) {
            aliado = 100;
            banco = 200;
        }

    }

    public Double calcularFrente(int[][] matrizVisao, int[][] matrizCheiro) {
        Double sum = 0d;

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                sum += buscarValorConstante(matrizVisao[i][j]);
            }

        }

        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < 3; j++) {

                if (matrizCheiro[i][j] > 1) {
                    sum += (1.0 / matrizCheiro[i][j]) * constanteOfalto;
                }
            }

        }

        if (matrizVisao[1][2] != 0 && !(matrizVisao[2][1] >= 100 && matrizVisao[2][1] < 200))
            sum += obstaculo;

        sum += (getQuantidadeVezesVisitadoPonto(1) * ultimoPonto);

        if (descubriuBanco) {
            dBank = distanceManhattan(pAtual[0] - 1, pAtual[1], pBank[0], pBank[1]);


            if (dBank > zonaSegura)
                sum += foraDaZona;
        }
        return sum;

    }

    public Double calcularAtras(int[][] matrizVisao, int[][] matrizCheiro) {
        Double sum = 0d;

        for (int i = 3; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                sum += buscarValorConstante(matrizVisao[i][j]);
            }

        }

        for (int i = 2; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (matrizCheiro[i][j] > 1) {
                    sum += (1.0 / matrizCheiro[i][j]) * constanteOfalto;
                }
            }

        }

        if (matrizVisao[3][2] != 0 && !(matrizVisao[2][1] >= 100 && matrizVisao[2][1] < 200))
            sum += obstaculo;

        if (ultimaAcaoAgente == 1)
            sum += ultimaAcao;

        sum += getQuantidadeVezesVisitadoPonto(2) * ultimoPonto;

        if (descubriuBanco) {
            dBank = distanceManhattan(pAtual[0] + 1, pAtual[1], pBank[0], pBank[1]);

            if (dBank > zonaSegura)
                sum += foraDaZona;
        }

        return sum;
    }

    public Double calcularEsquerda(int[][] matrizVisao, int[][] matrizCheiro) {
        Double sum = 0d;

        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 2; j++) {

                sum += buscarValorConstante(matrizVisao[i][j]);
            }

        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 1; j++) {

                if (matrizCheiro[i][j] > 1) {
                    sum += (1.0 / matrizCheiro[i][j]) * constanteOfalto;
                }
            }

        }

        if (matrizVisao[2][1] != 0 && !(matrizVisao[2][1] >= 100 && matrizVisao[2][1] < 200))
            sum += obstaculo;

        if (ultimaAcaoAgente == 3)
            sum += ultimaAcao;

        sum += getQuantidadeVezesVisitadoPonto(4) * ultimoPonto;

        if (descubriuBanco) {
            dBank = distanceManhattan(pAtual[0], pAtual[1] - 1, pBank[0], pBank[1]);
            if (dBank > zonaSegura)
                sum += foraDaZona;

        }

        return sum;

    }

    public Double calcularDireita(int[][] matrizVisao, int[][] matrizCheiro) {
        Double sum = 0d;

        for (int i = 1; i < 4; i++) {
            for (int j = 3; j < 5; j++) {

                sum += buscarValorConstante(matrizVisao[i][j]);
            }

        }

        for (int i = 0; i < 3; i++) {
            for (int j = 2; j < 3; j++) {

                if (matrizCheiro[i][j] > 1) {
                    sum += (1.0 / matrizCheiro[i][j]) * constanteOfalto;
                }
            }

        }

        if (matrizVisao[2][3] != 0 && !(matrizVisao[2][1] >= 100 && matrizVisao[2][1] < 200))
            sum += obstaculo;

        if (ultimaAcaoAgente == 4)
            sum += ultimaAcao;

        sum += getQuantidadeVezesVisitadoPonto(3) * ultimoPonto;

        if (descubriuBanco) {
            dBank = distanceManhattan(pAtual[0], pAtual[1] + 1, pBank[0], pBank[1]);
            if (dBank > zonaSegura)
                sum += foraDaZona;
        }

        return sum;

    }

    public int buscarValorConstante(int f) {

        if (f == 0)
            return espacoVazio;

        if (f >= 100 && f < 200)
            return objetivo;

        if (f >= 200 && f < 300)
            return aliado;

        if (f == 4)
            return moeda;

        if (f == -2)
            return parede;

        if (f == -1)
            return foraMundo;

        if (f == 3)
            return banco;
        if (f == 5)
            return pastilhaPoder;

        return 0;
    }

    public int[][] getMatrizVisao() {
        int matrizConvertida[][] = new int[5][5];
        int array[] = new int[24];

        array = sensor.getVisaoIdentificacao();

        int indexArray = 0;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {

                if ((array[indexArray] >= 100 && array[indexArray] < 200))
                    persinguindo = true;
                if (array[indexArray] == 3) {
                    procurandoBanco = true;

                    if (!descubriuBanco) {

                        pBank[0] = pAtual[0] + (i - 2);
                        pBank[1] = pAtual[1] + (j - 2);

                        descubriuBanco = true;

                    }

                }

                if (indexArray == 12) {
                    // System.out.println(matrizConvertida[i][j]);
                    j++;
                    matrizConvertida[i][j] = array[indexArray];
                    indexArray++;

                } else {
                    matrizConvertida[i][j] = array[indexArray];
                    indexArray++;
                }

            }

        }
        return matrizConvertida;
    }

    public int[][] getMatrizOfalto() {
        int matrizConvertida[][] = new int[3][3];
        int array[] = new int[8];

        array = sensor.getAmbienteOlfatoLadrao();

        int indexArray = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (indexArray == 4) {
                    j++;
                    matrizConvertida[i][j] = array[indexArray];
                    indexArray++;

                } else {
                    matrizConvertida[i][j] = array[indexArray];
                    indexArray++;
                }

            }

        }
        return matrizConvertida;
    }

    public void incrementaPontoVisitado(int action) {
        int i = 0, j = 0;
        Point matrizHash[][] = new Point[5][5];

        matrizHash[1][2] = new Point(-1, 0);
        matrizHash[2][1] = new Point(0, -1);
        matrizHash[2][3] = new Point(0, +1);
        matrizHash[3][2] = new Point(+1, 0);

        if (action == 1) {
            i = 1;
            j = 2;
        }
        if (action == 2) {
            i = 3;
            j = 2;
        }
        if (action == 3) {
            i = 2;
            j = 3;
        }
        if (action == 4) {
            i = 2;
            j = 1;
        }

        int x = (int) (sensor.getPosicao().getY() + matrizHash[i][j].getX());
        int y = (int) (sensor.getPosicao().getX() + matrizHash[i][j].getY());

        if ((x >= 0 && x < 30) && (y >= 0 && y < 30))
            matrizLocaisVisiatados[x][y]++;

    }

    public int getQuantidadeVezesVisitadoPonto(int action) {

        int i = 0, j = 0;
        Point matrizHash[][] = new Point[5][5];

        matrizHash[1][2] = new Point(-1, 0);
        matrizHash[2][1] = new Point(0, -1);
        matrizHash[2][3] = new Point(0, +1);
        matrizHash[3][2] = new Point(+1, 0);

        if (action == 1) {
            i = 1;
            j = 2;
        }
        if (action == 2) {
            i = 3;
            j = 2;
        }
        if (action == 3) {
            i = 2;
            j = 3;
        }
        if (action == 4) {
            i = 2;
            j = 1;
        }

        int x = (int) (sensor.getPosicao().getY() + matrizHash[i][j].getX());
        int y = (int) (sensor.getPosicao().getX() + matrizHash[i][j].getY());

        if ((x >= 0 && x < 30) && (y >= 0 && y < 30))
            return matrizLocaisVisiatados[x][y];
        else
            return 0;
    }

    public int distanceManhattan(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }
}