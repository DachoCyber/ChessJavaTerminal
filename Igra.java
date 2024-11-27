package com.example.igrasah;

import java.util.Scanner;

public class Igra {

    Figura[] sahovskaTabla;
    int trPotezBrojac;

    boolean jeCrniKraljMatiran, jeBeliKraljMatiran, jeRemi;
    public boolean jeIgraZavrsena;


    Igra() {
        sahovskaTabla = new Figura[64];
        inicijalizujFigure();


    }



    public void igrajSah() {
        jeBeliKraljMatiran = false;
        jeCrniKraljMatiran = false;
        jeRemi = false;

        trPotezBrojac = 0;


        do {
            if (trPotezBrojac % 2 == 0) {

                Scanner sc = new Scanner(System.in);


                boolean legalanPotez;
                do {
                    String potez = sc.nextLine();
                    trenutniPotez(trPotezBrojac, potez);
                    Boja boja = (trPotezBrojac % 2 == 0) ? Boja.BELI : Boja.CRNI;
                    legalanPotez = handlePlayerMove(boja, pozBelogKralja, potez);
                } while (!legalanPotez);

                Figura crniKralj = sahovskaTabla[pozCrnogKralja];
                jeCrniKraljMatiran = ((Kralj) crniKralj).jeMatiran(pozCrnogKralja);
                jeRemi = jeLiRemi(Boja.CRNI);
            } else {
                // Black's turn
                Scanner sc = new Scanner(System.in);

                boolean legalanPotez;
                do {
                    String potez = sc.nextLine();
                    trenutniPotez(trPotezBrojac, potez);
                    Boja boja = (trPotezBrojac % 2 == 0) ? Boja.BELI : Boja.CRNI;
                    legalanPotez = handlePlayerMove(boja, pozBelogKralja, potez);
                } while (!legalanPotez);

                Figura beliKralj = sahovskaTabla[pozBelogKralja];
                jeBeliKraljMatiran = ((Kralj) beliKralj).jeMatiran(pozBelogKralja);
                jeRemi = jeLiRemi(Boja.BELI);
            }

            trPotezBrojac++;
            jeIgraZavrsena = igraZavrsena();

        } while (!jeIgraZavrsena);
    }



    public void trenutniPotez(int trPotezBrojac, String potez) {
        Boja boja = (trPotezBrojac % 2 == 0) ? Boja.BELI : Boja.CRNI;

        String start = potez.split(" ")[0];
        String end = potez.split(" ")[1];


        int startX = start.charAt(0) - 'a';
        int startY = start.charAt(1) - '0';
        startY--;

        int endX = end.charAt(0) - 'a';
        int endY = end.charAt(1) - '0';
        endY--;
    }

    public boolean igraZavrsena() {
        return jeCrniKraljMatiran || jeBeliKraljMatiran || jeRemi;
    }

    void inicijalizujFigure() {
        sahovskaTabla[hashKoord(0, 0)] = new Top(Boja.BELI, sahovskaTabla, Strana.LEVA_STRANA);
        sahovskaTabla[hashKoord(1, 0)] = new Skakac(Boja.BELI, sahovskaTabla);
        sahovskaTabla[hashKoord(2, 0)] = new Lovac(Boja.BELI, sahovskaTabla);
        sahovskaTabla[hashKoord(3, 0)] = new Kraljica(Boja.BELI, sahovskaTabla);
        sahovskaTabla[hashKoord(4, 0)] = new Kralj(Boja.BELI, sahovskaTabla);
        sahovskaTabla[hashKoord(5, 0)] = new Lovac(Boja.BELI, sahovskaTabla);
        sahovskaTabla[hashKoord(6, 0)] = new Skakac(Boja.BELI, sahovskaTabla);
        sahovskaTabla[hashKoord(7, 0)] = new Top(Boja.BELI, sahovskaTabla, Strana.DESNA_STRANA);

        for(int i = 0; i < 8; ++i) {
            sahovskaTabla[hashKoord(i, 1)] = new Pesak(Boja.BELI, sahovskaTabla);
        }

        sahovskaTabla[hashKoord(0, 7)] = new Top(Boja.CRNI, sahovskaTabla, Strana.LEVA_STRANA);
        sahovskaTabla[hashKoord(1, 7)] = new Skakac(Boja.CRNI, sahovskaTabla);
        sahovskaTabla[hashKoord(2, 7)] = new Lovac(Boja.CRNI, sahovskaTabla);
        sahovskaTabla[hashKoord(3, 7)] = new Kraljica(Boja.CRNI, sahovskaTabla);
        sahovskaTabla[hashKoord(4, 7)] = new Kralj(Boja.CRNI, sahovskaTabla);
        sahovskaTabla[hashKoord(5, 7)] = new Lovac(Boja.CRNI, sahovskaTabla);
        sahovskaTabla[hashKoord(6, 7)] = new Skakac(Boja.CRNI, sahovskaTabla);
        sahovskaTabla[hashKoord(7, 7)] = new Top(Boja.CRNI, sahovskaTabla, Strana.DESNA_STRANA);

        for(int i = 0; i < 8; ++i) {
            sahovskaTabla[hashKoord(i, 6)] = new Pesak(Boja.CRNI, sahovskaTabla);
        }
    }


    public boolean handlePlayerMove(Boja boja, int pozKralja, String potez) {

        String start = potez.split(" ")[0];
        String end = potez.split(" ")[1];


        int startX = start.charAt(0) - 'a';
        int startY = start.charAt(1) - '0';
        startY--;

        int endX = end.charAt(0) - 'a';
        int endY = end.charAt(1) - '0';
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

        for(int i = 0; i < sahovskaTabla.length; i++) {
            if(sahovskaTabla[i] instanceof Kralj && sahovskaTabla[i].getBoja() == boja) {
                pozKralja = i;
                if(boja == Boja.BELI) pozBelogKralja = i;
                else pozCrnogKralja = i;
            }
        }

        // Check if the move leaves the king in check
        if (((Kralj) sahovskaTabla[pozKralja]).jePodSahom(pozKralja)) {
            System.out.println("Cannot make this move; the king is in check!");
            sahovskaTabla[hashKoord(startX, startY)] = figuraNaStartu;
            return false;
        }

        return true;
    }

    boolean jeLiRemi(Boja boja) {
        for(int i = 0; i < sahovskaTabla.length; i++) {
            Figura trFigura = sahovskaTabla[i];
            if(trFigura == null) {
                continue;
            }
            for(int potez : trFigura.napadnutaPolja(i)) {
                if(trFigura.daLiJePotezMoguc(i, potez) && trFigura.getBoja() == boja) {
                    return false;
                }
            }
        }

        if(sahovskaTabla.length == 2) return true;
        return false;
    }


    public int hashKoord(int x, int y) {
        return x + 8 * y;
    }

    int pozBelogKralja = 4;
    int pozCrnogKralja = 60;
}
