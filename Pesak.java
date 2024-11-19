package com.example.igrasah;

import java.util.ArrayList;

public class Pesak extends Figura {

    public Pesak(String boja, Figura[] sahovskaTabla) {
        super(boja, sahovskaTabla);
    }

    @Override
    public boolean daLiJePotezMoguc(int indexStart, int indexKraj) {
        if (!unutarTablePotez(indexKraj)) return false;

        int[] start = hashIndex(indexStart);
        int[] end = hashIndex(indexKraj);

        int direction = this.getBoja().equals("beli") ? 1 : -1; // Forward direction for white or black pawns
        int startRow = this.getBoja().equals("beli") ? 1 : 6;   // Starting row for white or black pawns

        // Forward move (1 step)
        if (end[0] == start[0] && end[1] == start[1] + direction) {
            return sahovskaTabla[indexKraj] == null;
        }

        // Forward move (2 steps from starting position)
        if (end[0] == start[0] && end[1] == start[1] + 2 * direction && start[1] == startRow) {
            return sahovskaTabla[indexKraj] == null && sahovskaTabla[hashKoord(start[0], start[1] + direction)] == null;
        }

        // Diagonal attack
        if (Math.abs(end[0] - start[0]) == 1 && end[1] == start[1] + direction) {
            return sahovskaTabla[indexKraj] != null && !sahovskaTabla[indexKraj].getBoja().equals(this.getBoja());
        }

        return false;
    }

    @Override
    public ArrayList<Integer> napadnutaPolja(int trPozicijaIndex) {
        ArrayList<Integer> napadnutaPoljaNiz = new ArrayList<>();
        int[] start = hashIndex(trPozicijaIndex);

        int direction = this.getBoja().equals("beli") ? 1 : -1;

        // Add both diagonal attack positions if valid
        if (unutarTablePotez(hashKoord(start[0] - 1, start[1] + direction))) {
            napadnutaPoljaNiz.add(hashKoord(start[0] - 1, start[1] + direction));
        }
        if (unutarTablePotez(hashKoord(start[0] + 1, start[1] + direction))) {
            napadnutaPoljaNiz.add(hashKoord(start[0] + 1, start[1] + direction));
        }

        return napadnutaPoljaNiz;
    }
}
