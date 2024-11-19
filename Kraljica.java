package com.example.igrasah;

import java.util.ArrayList;

public class Kraljica extends Figura {

    Kraljica(String boja, Figura[] sahovskaTabla) {
        super(boja, sahovskaTabla);
    }

    @Override
    public boolean daLiJePotezMoguc(int indexStart, int indexKraj) {
        if (!unutarTablePotez(indexKraj)) return false;
        if (indexStart == indexKraj) return false;

        int startX = indexStart % 8, startY = indexStart / 8;
        int endX = indexKraj % 8, endY = indexKraj / 8;

        int deltaX = endX - startX;
        int deltaY = endY - startY;

        // Check valid queen move (diagonal, horizontal, or vertical)
        if (!(Math.abs(deltaX) == Math.abs(deltaY) || deltaX == 0 || deltaY == 0)) {
            return false;
        }

        // Determine step direction
        int stepX = Integer.compare(deltaX, 0); // 1 for right, -1 for left, 0 for no movement
        int stepY = Integer.compare(deltaY, 0); // 1 for down, -1 for up, 0 for no movement

        // Check for obstructions along the path
        int currentX = startX + stepX;
        int currentY = startY + stepY;

        while (currentX != endX || currentY != endY) {
            int currentIndex = hashKoord(currentX, currentY);
            if (sahovskaTabla[currentIndex] != null) return false; // Path is blocked
            currentX += stepX;
            currentY += stepY;
        }

        // Check destination square
        Figura target = sahovskaTabla[indexKraj];
        return target == null || !target.getBoja().equals(boja);
    }

    @Override
    public ArrayList<Integer> napadnutaPolja(int trPozicija) {
        ArrayList<Integer> napadnutaPoljaNiz = new ArrayList<>();

        int startX = trPozicija % 8;
        int startY = trPozicija / 8;

        // Queen moves in 8 directions: 4 straight + 4 diagonal
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, // Straight
                {1, 1}, {-1, -1}, {1, -1}, {-1, 1} // Diagonal
        };

        for (int[] dir : directions) {
            int stepX = dir[0];
            int stepY = dir[1];
            int currentX = startX + stepX;
            int currentY = startY + stepY;

            while (currentX >= 0 && currentX < 8 && currentY >= 0 && currentY < 8) {
                int currentIndex = hashKoord(currentX, currentY);

                // Add square to attacked squares
                napadnutaPoljaNiz.add(currentIndex);

                // Stop if a piece is encountered
                if (sahovskaTabla[currentIndex] != null) break;

                currentX += stepX;
                currentY += stepY;
            }
        }

        return napadnutaPoljaNiz;
    }
}
