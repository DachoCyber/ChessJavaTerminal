package com.example.igrasah;

import java.util.ArrayList;

public abstract class Figura {
    String boja;
    Figura[] sahovskaTabla;
    public Figura(String boja, Figura[] sahovskaTabla) {
        this.sahovskaTabla = sahovskaTabla;
        this.boja = boja;
    }
    public String getBoja() {
        return this.boja;
    }
    public abstract boolean daLiJePotezMoguc(int trPozicijaIndex, int targetPozicijaIndex);
    public int hashKoord(int x, int y) {
        return x + 8 * y;
    }
    int[] hashIndex(int figuraIndex) {
        int[] koord = new int[2];
        koord[0] = figuraIndex % 8;
        koord[1] = figuraIndex / 8;
        return koord;
    }

    public boolean unutarTablePotez(int indexKraj) {
        int[] koord = hashIndex(indexKraj);
        return koord[0] >= 0 && koord[1] >= 0 && koord[0] <= 7 && koord[1] <= 7;
    }
    public abstract ArrayList<Integer> napadnutaPolja(int trPozicija);
}
