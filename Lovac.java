package com.example.igrasah;

import java.util.ArrayList;

public class Lovac extends Figura {




    Lovac(String boja, Figura[] sahovskaTabla) {
        super(boja, sahovskaTabla);
    }




    public boolean daLiJePotezMoguc(int indexStart, int indexKraj) {
        if (!unutarTablePotez(indexKraj)) return false;
        if (indexStart == indexKraj) return false;

        int startX = indexStart % 8, startY = indexStart / 8;
        int endX = indexKraj % 8, endY = indexKraj / 8;

        // Ensure the move is diagonal
        if (Math.abs(startX - endX) != Math.abs(startY - endY)) return false;

        int stepX = (endX > startX) ? 1 : -1;
        int stepY = (endY > startY) ? 1 : -1;

        // Check for obstacles in the path
        for (int x = startX + stepX, y = startY + stepY; x != endX; x += stepX, y += stepY) {
            if(sahovskaTabla[hashKoord(x, y)] == null) continue;
            if (sahovskaTabla[hashKoord(x, y)] != null || sahovskaTabla[hashKoord(x, y)].getBoja() == boja) {
                return false;
            }
        }

        // Check destination square
        Figura target = sahovskaTabla[indexKraj];
        return target == null || !target.getBoja().equals(boja);
    }


    public ArrayList<Integer> napadnutaPolja(int trPozicija) {
        ArrayList<Integer> napadnutaPoljaNiz = new ArrayList<>(14);

        int startX = trPozicija % 8, startY = trPozicija / 8;
        int[][] directions = { {1, 1}, {1, -1}, {-1, 1}, {-1, -1} }; // Diagonal directions

        for (int[] dir : directions) {
            int stepX = dir[0], stepY = dir[1];
            int x = startX + stepX, y = startY + stepY;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                int targetIndex = hashKoord(x, y);
                napadnutaPoljaNiz.add(targetIndex);

                // Stop if a piece blocks further attacks
                if (sahovskaTabla[targetIndex] != null) break;

                x += stepX;
                y += stepY;
            }
        }

        return napadnutaPoljaNiz;
    }

}


