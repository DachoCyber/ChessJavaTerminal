package com.example.igrasah;

import java.util.ArrayList;

public class Skakac extends Figura {
    public Skakac(String boja, Figura[] sahovskaTabla) {
        super(boja, sahovskaTabla);
    }
    public boolean daLiJePotezMoguc(int indexStart, int indexEnd) {

        if(!unutarTablePotez(indexEnd)) return false;
        if(sahovskaTabla[indexEnd] == null) return true;
        if(sahovskaTabla[indexEnd].getBoja() == this.getBoja()) return false;

        int startX = hashIndex(indexStart)[0];
        int endX = hashIndex(indexEnd)[0];
        int startY = hashIndex(indexStart)[1];
        int endY = hashIndex(indexEnd)[1];

        if((startX - endX) * (startX - endX) + (startY - endY) * (startY - endY) != 5) return false;
        return true;
    }
    public ArrayList<Integer> napadnutaPolja(int trPozicija) {
        ArrayList<Integer> napadnuta = new ArrayList<>();
        int[][] pomeraj = {{1, 2}, {2, 1}, {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2}, {1, -2}, {2, -1}};
        for(int i = 0; i < pomeraj.length; i++) {
            if(daLiJePotezMoguc(trPozicija, hashKoord(hashIndex(trPozicija)[0] + pomeraj[i][0], hashIndex(trPozicija)[1]))) {
                napadnuta.add(hashKoord(hashIndex(trPozicija)[0] + pomeraj[i][0], hashIndex(trPozicija)[1]));
            }
        }
        return napadnuta;
    }
}

