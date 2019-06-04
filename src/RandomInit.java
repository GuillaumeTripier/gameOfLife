public class RandomInit implements InitInterface {
    public RandomInit(){

    }

    @Override
    public boolean[][] initPlateau(int TAILLE_PLATEAU) {
        boolean[][] booleans = new boolean[TAILLE_PLATEAU][TAILLE_PLATEAU];
        for (int ligne = 0; ligne < TAILLE_PLATEAU; ligne++) {
            for (int colonne = 0; colonne < TAILLE_PLATEAU; colonne++) {
                booleans[ligne][colonne] = Math.random() > 0.8;
            }
        }
        return booleans;
    }
}
