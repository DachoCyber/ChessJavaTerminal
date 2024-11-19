package com.example.igrasah;

import java.util.Scanner;

public class Igra {

    Figura[] sahovskaTabla;
    int trPotezBrojac;

    boolean jeCrniKraljMatiran, jeBeliKraljMatiran, jeRemi;

    Igra() {
        sahovskaTabla = new Figura[64];
        inicijalizujFigure();

        jeBeliKraljMatiran = false;
        jeCrniKraljMatiran = false;
        jeRemi = false;
        trPotezBrojac = 0;

        boolean legalanPotezi;

        do {
            if (trPotezBrojac % 2 == 0) {
                // White's turn
                do {
                    legalanPotezi = handlePlayerMove("beli", pozBelogKralja);
                } while (!legalanPotezi);

                Figura crniKralj = sahovskaTabla[pozCrnogKralja];
                jeCrniKraljMatiran = ((Kralj) crniKralj).jeMatiran(pozCrnogKralja);
                if(jeBeliKraljMatiran) {
                    System.out.println("Beli je pobedio!");
                    break;
                }

            } else {
                // Black's turn
                do {
                    legalanPotezi = handlePlayerMove("crni", pozCrnogKralja);
                } while (!legalanPotezi);

                Figura beliKralj = sahovskaTabla[pozBelogKralja];
                jeBeliKraljMatiran = ((Kralj) beliKralj).jeMatiran(pozBelogKralja);
                if(jeBeliKraljMatiran) {
                    System.out.println("Crni je pobedio!");
                    break;
                }
            }

            trPotezBrojac++;
        } while (!jeCrniKraljMatiran && !jeBeliKraljMatiran && !jeRemi);

    }


    void inicijalizujFigure() {
        sahovskaTabla[hashKoord(0, 0)] = new Top("beli", sahovskaTabla, 0);
        sahovskaTabla[hashKoord(1, 0)] = new Skakac("beli", sahovskaTabla);
        sahovskaTabla[hashKoord(2, 0)] = new Lovac("beli", sahovskaTabla);
        sahovskaTabla[hashKoord(3, 0)] = new Kraljica("beli", sahovskaTabla);
        sahovskaTabla[hashKoord(4, 0)] = new Kralj("beli", sahovskaTabla, false, false);
        sahovskaTabla[hashKoord(5, 0)] = new Lovac("beli", sahovskaTabla);
        sahovskaTabla[hashKoord(6, 0)] = new Skakac("beli", sahovskaTabla);
        sahovskaTabla[hashKoord(7, 0)] = new Top("beli", sahovskaTabla, 1);

        for(int i = 0; i < 8; ++i) {
            sahovskaTabla[hashKoord(i, 1)] = new Pesak("beli", sahovskaTabla);
        }

        sahovskaTabla[hashKoord(0, 7)] = new Top("crni", sahovskaTabla, 0);
        sahovskaTabla[hashKoord(1, 7)] = new Skakac("crni", sahovskaTabla);
        sahovskaTabla[hashKoord(2, 7)] = new Lovac("crni", sahovskaTabla);
        sahovskaTabla[hashKoord(3, 7)] = new Kraljica("crni", sahovskaTabla);
        sahovskaTabla[hashKoord(4, 7)] = new Kralj("crni", sahovskaTabla, false, false);
        sahovskaTabla[hashKoord(5, 7)] = new Lovac("crni", sahovskaTabla);
        sahovskaTabla[hashKoord(6, 7)] = new Skakac("crni", sahovskaTabla);
        sahovskaTabla[hashKoord(7, 7)] = new Top("crni", sahovskaTabla, 1);

        for(int i = 0; i < 8; ++i) {
            sahovskaTabla[hashKoord(i, 6)] = new Pesak("crni", sahovskaTabla);
        }
    }


    public boolean handlePlayerMove(String boja, int pozKralja) {
        Scanner sc = new Scanner(System.in);
        int startXChar = sc.next().charAt(0);
        int startX = startXChar - 'a';

        int startY = sc.nextInt();
        startY--;

        int endXChar = sc.next().charAt(0);
        int endX = endXChar - 'a';

        int endY = sc.nextInt();
        endY--;

        System.out.println(boja + " plays: " + startX + ", " + startY + " to " + endX + ", " + endY);

        Figura figuraNaStartu = sahovskaTabla[hashKoord(startX, startY)];
        if (figuraNaStartu == null) {
            System.out.println("No piece at the starting position.");
            return false;
        }

        if (!figuraNaStartu.getBoja().equals(boja)) {
            System.out.println("It's " + boja + "'s turn!");
            return false;
        }

        if (!figuraNaStartu.daLiJePotezMoguc(hashKoord(startX, startY), hashKoord(endX, endY))) {
            System.out.println("Illegal move.");
            return false;
        }

        // Temporarily make the move to check for checks
        Figura targetFigura = sahovskaTabla[hashKoord(endX, endY)];
        sahovskaTabla[hashKoord(startX, startY)] = null;
        sahovskaTabla[hashKoord(endX, endY)] = figuraNaStartu;

        // Check if the move leaves the king in check
        if (((Kralj) sahovskaTabla[pozKralja]).jePodSahom(pozKralja)) {
            System.out.println("Cannot make this move; the king is in check!");
            sahovskaTabla[hashKoord(startX, startY)] = figuraNaStartu;
            sahovskaTabla[hashKoord(endX, endY)] = targetFigura;
            return false;
        }

        return true;
    }


    public int hashKoord(int x, int y) {
        return x + 8 * y;
    }
    int[] hashIndex(int figuraIndex) {
        int[] koord = new int[2];
        koord[0] = figuraIndex % 8;
        koord[1] = figuraIndex / 8;
        return koord;
    }

    boolean beliKraljPomaknut;
    boolean crniKraljPomaknut;


    int pozBelogKralja = 4;
    int pozCrnogKralja = 60;
    int pozBelogLevogTopa;
    int pozBelogDesnogTopa;
    int pozCrnogLevogTopa;
    int pozCrnogDesnogTopa;
}
