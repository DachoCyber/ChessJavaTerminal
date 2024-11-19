package com.example.igrasah;

import java.util.ArrayList;

public class Top extends Figura {

    // Flags for castling and rook positions
    int topIndex;
    boolean jePomaknutDrugiBeli = false;
    boolean jePomaknutPrviBeli = false;
    boolean jePomaknutDrugiCrni = false;
    boolean jePomaknutPrviCrni = false;
    int pozBelogDesnogTopa;
    int pozBelogLevogTopa;
    int pozCrnogDesnogTopa;
    int pozCrnogLevogTopa;

    Top(String boja, Figura[] sahovskaTabla, int topIndex) {
        super(boja, sahovskaTabla);
        this.topIndex = topIndex;
    }

    @Override
    public boolean daLiJePotezMoguc(int indexStart, int indexKraj) {
        if (indexStart == indexKraj) return false;
        if (!unutarTablePotez(indexKraj)) return false;

        // Validate rook's movement (same row or column)
        boolean isVertical = indexStart % 8 == indexKraj % 8;
        boolean isHorizontal = indexStart / 8 == indexKraj / 8;
        if (!(isVertical || isHorizontal)) return false;

        // Calculate step direction
        int step = isVertical ? 8 : 1;
        int direction = (indexKraj > indexStart) ? 1 : -1;

        // Check for obstructions
        for (int i = indexStart + step * direction; i != indexKraj; i += step * direction) {
            if (sahovskaTabla[i] != null) return false;
        }

        // Check destination square
        Figura target = sahovskaTabla[indexKraj];
        if (target != null && target.getBoja().equals(boja)) return false;

        // Update movement flags only for valid moves
        if (getBoja().equals("beli")) {
            if (topIndex == 1) {
                jePomaknutDrugiBeli = true;
                pozBelogDesnogTopa = indexKraj;
            } else {
                jePomaknutPrviBeli = true;
                pozBelogLevogTopa = indexKraj;
            }
        } else {
            if (topIndex == 1) {
                jePomaknutDrugiCrni = true;
                pozCrnogDesnogTopa = indexKraj;
            } else {
                jePomaknutPrviCrni = true;
                pozCrnogLevogTopa = indexKraj;
            }
        }
        return true;
    }

    @Override
    public ArrayList<Integer> napadnutaPolja(int trPozicija) {
        ArrayList<Integer> napadnutaPoljaNiz = new ArrayList<>();
        int startX = trPozicija % 8;
        int startY = trPozicija / 8;

        // Rook moves in four directions: up, down, left, right
        int[][] directions = { {0, 1}, {0, -1}, {1, 0}, {-1, 0} };

        for (int[] dir : directions) {
            int stepX = dir[0], stepY = dir[1];
            int x = startX + stepX, y = startY + stepY;

            while (x >= 0 && x < 8 && y >= 0 && y < 8) {
                int targetIndex = hashKoord(x, y);
                napadnutaPoljaNiz.add(targetIndex);

                // Stop if a piece is encountered
                if (sahovskaTabla[targetIndex] != null) break;

                x += stepX;
                y += stepY;
            }
        }

        return napadnutaPoljaNiz;
    }
}
