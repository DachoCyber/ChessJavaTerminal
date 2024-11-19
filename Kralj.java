package com.example.igrasah;

import java.util.ArrayList;

public class Kralj extends Figura {

    public boolean jeMatiran;
    public boolean jePodSahom;

    boolean beliKraljPomaknut;
    boolean crniKraljPomaknut;


    int pozBelogKralja = 4;
    int pozCrnogKralja = 60;
    int pozBelogLevogTopa;
    int pozBelogDesnogTopa;
    int pozCrnogLevogTopa;
    int pozCrnogDesnogTopa;

    public Kralj(String boja, Figura[] sahovskaTabla, boolean jeMatiran, boolean jePodSahom) {
        super(boja, sahovskaTabla);
        this.jeMatiran = jeMatiran;
        this.jePodSahom = jePodSahom;
    }

    public ArrayList<Integer> napadnutaPolja(int trPozicijaIndex) {
        ArrayList<Integer> napadnutaPoljaNiz = new ArrayList<>(8);
        int[] koordStart = hashIndex(trPozicijaIndex);
        int napadnutnoPoljeInd = 0;
        for(int i = -1, j = -1; i <= 1 && j <= 1; i++, j++) {
            if(i == 0 && j == 0) continue;
            napadnutaPoljaNiz.add(hashKoord(koordStart[0] + i, koordStart[1] + j));
        }
        return napadnutaPoljaNiz;
    }
    public boolean jePodSahom(int trPozicijaIndex) {

        for(int i = 0; i < sahovskaTabla.length; i++) {
            Figura trFigura = sahovskaTabla[i];
            if(trFigura == null) continue;
            if(trFigura.napadnutaPolja(i).contains(trPozicijaIndex) && trFigura.getBoja() != boja) {
                jePodSahom = true;
                return true;
            }
        }
        jePodSahom = false;
        return false;

    }

    public boolean daLiJePotezMoguc(int indexStart, int indexKraj) {

        int[] koordStart = hashIndex(indexStart);
        int[] koordKraj = hashIndex(indexKraj);

        if (koordKraj == koordStart) {
            return false;
        } else if (this.malaRokada(indexStart)) {
            return true;
        } else if (this.velikaRokada(indexStart)) {
            return true;
        } else if (Math.abs(koordStart[0] - koordKraj[0]) <= 1 && Math.abs(koordStart[1] - koordKraj[1]) <= 1) {
            for(int i = 0; i < sahovskaTabla.length; ++i) {
                Figura trFigura = sahovskaTabla[i];
                if(trFigura == null) continue;
                if (!trFigura.getBoja().equals(this.boja) && trFigura.napadnutaPolja(i).contains(indexKraj)) {
                    return false;
                }
            }

            if (sahovskaTabla[indexKraj] != null && sahovskaTabla[indexKraj].getBoja().equals(this.boja)) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
    public boolean malaRokada(int indexStart) {
        if (beliKraljPomaknut && boja.equals("beli")) {
            return false;
        } else if (crniKraljPomaknut && boja.equals("crni")) {
            return false;
        } else if (!this.jePodSahom(indexStart)) {
            return false;
        } else {
            if (boja.equals("beli")) {
                if (((Top)sahovskaTabla[pozBelogDesnogTopa]).jePomaknutDrugiBeli) {
                    return false;
                }
            } else if (boja.equals("crni") && ((Top)sahovskaTabla[pozCrnogDesnogTopa]).jePomaknutDrugiCrni) {
                return false;
            }

            for(int i = 0; i < sahovskaTabla.length; ++i) {
                Figura trFigura = sahovskaTabla[i];
                if(trFigura == null) continue;
                if (trFigura.getBoja().equals("crni") && this.boja.equals("beli")) {
                    if (trFigura.napadnutaPolja(i).contains(5) || trFigura.napadnutaPolja(i).contains(6)) {
                        return false;
                    }
                } else if (trFigura.getBoja().equals("beli") && this.boja.equals("crni")) {
                    if (trFigura.napadnutaPolja(i).contains(61) || trFigura.napadnutaPolja(i).contains(62)) {
                        return false;
                    }
                }
            }

            if (boja.equals("beli") && (sahovskaTabla[5] == null || sahovskaTabla[6] == null)) {
                return false;
            } else if (!boja.equals("crni") || sahovskaTabla[61] == null && sahovskaTabla[62] == null) {
                return true;
            } else {
                return false;
            }
        }
    }
    public boolean velikaRokada(int indexStart) {
        if (beliKraljPomaknut && boja.equals("beli")) {
            return false;
        } else if (crniKraljPomaknut && boja.equals("crni")) {
            return false;
        } else if (!this.jePodSahom(indexStart)) {
            return false;
        } else {
            if (boja.equals("beli")) {
                if (((Top) sahovskaTabla[pozBelogLevogTopa]).jePomaknutPrviBeli) {
                    return false;
                }
            } else if (boja.equals("crni") && ((Top) sahovskaTabla[pozCrnogLevogTopa]).jePomaknutPrviCrni) {
                return false;
            }

            for (int i = 0; i < sahovskaTabla.length; ++i) {
                Figura trFigura = sahovskaTabla[i];
                if (trFigura == null) continue;
                if (trFigura.getBoja().equals("crni") && this.boja.equals("beli")) {
                    if (trFigura.napadnutaPolja(i).contains(1) || trFigura.napadnutaPolja(i).contains(2) || trFigura.napadnutaPolja(i).contains(3)) {
                        return false;
                    }
                } else if (trFigura.getBoja().equals("beli") && this.boja.equals("crni")) {
                    if (trFigura.napadnutaPolja(i).contains(58) || trFigura.napadnutaPolja(i).contains(59) || trFigura.napadnutaPolja(i).contains(60)) {
                        return false;
                    }
                }
            }

            if (boja.equals("beli") && (sahovskaTabla[1] == null || sahovskaTabla[2] == null || sahovskaTabla[3] == null)) {
                return false;
            } else if (!boja.equals("crni") || sahovskaTabla[58] == null && sahovskaTabla[59] == null && sahovskaTabla[60] == null) {
                return true;
            } else {
                return false;
            }
        }
    }
    public boolean jeMatiran(int pozicija) {
        // Provera da li je kralj već pod šahom
        if (!jePodSahom(pozicija)) return false;

        boolean mogucPotez = false;
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                if(daLiJePotezMoguc(pozicija, hashKoord(i, j))) {
                    mogucPotez = true;
                }
            }
        }
        if(!mogucPotez) {
            return false;
        }

        // Pomeraji za kralja u 8 pravaca
        int[][] pomeraj = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},         {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        // Proveravamo svaki mogući potez kralja
        for (int[] p : pomeraj) {
            int noviX = pozicija % 8 + p[0];
            int noviY = pozicija / 8 + p[1];

            // Proveravamo da li su koordinate unutar table
            if (noviX >= 0 && noviX < 8 && noviY >= 0 && noviY < 8) {
                int novaPozicija = hashKoord(noviX, noviY);

                // Proveravamo da li kralj može legalno da se pomeri na novu poziciju
                if (daLiJePotezMoguc(pozicija, novaPozicija)) {
                    // Privremeno pomeramo kralja na novu poziciju
                    Figura originalnaFigura = sahovskaTabla[novaPozicija];
                    sahovskaTabla[novaPozicija] = sahovskaTabla[pozicija];
                    sahovskaTabla[pozicija] = null;

                    // Proveravamo da li kralj ostaje pod šahom nakon poteza
                    boolean podSahom = jePodSahom(novaPozicija);

                    // Vraćamo kralja na originalnu poziciju
                    sahovskaTabla[pozicija] = sahovskaTabla[novaPozicija];
                    sahovskaTabla[novaPozicija] = originalnaFigura;

                    // Ako kralj može pobeći, nije mat
                    if (!podSahom) return false;
                }
            }
        }

        // Ako nijedan potez ne uklanja kralja iz šaha, on je matiran
        return true;
    }

}
