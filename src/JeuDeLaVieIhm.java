import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class JeuDeLaVieIhm extends JFrame{

    private static final int TAILLE_PLATEAU = 70;
    private static final float TAILLE_POLICE = 10f;
    private static final int PERIODE_RAFRACHISSEMENT_MILLI_SECONDE = 50;
    private static final String CARACTERE_VIVANT = " O ";
    private static final String CARACTERE_MORT = "   ";

    private boolean[][] etat = plateauInitial();

    private boolean[][] plateauInitial() {
        //initClass.initFile(TAILLE_PLATEAU);




        //InitInterface initClass = new RandomInit();
        InitInterface initClass = new StaticInit("test");


        boolean[][] plateau = initClass.initPlateau(TAILLE_PLATEAU);
        return plateau;
    }

    public static void main(String[] args) {
        JeuDeLaVieIhm ihm = new JeuDeLaVieIhm();
        ihm.demarre();
    }

    private void demarre() {
        SwingUtilities.invokeLater(this::buildAndShow);
    }

    private void buildAndShow() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JTextArea comp = new JTextArea(prochainPlateau());
        Font[] allFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
        System.out.println(Arrays.toString(allFonts));
        Font monospacedFont = Arrays.stream(allFonts)
                .filter(f -> f.getName().equals("Courier New"))
                .findFirst()
                .orElse(comp.getFont());
        comp.setFont(monospacedFont.deriveFont(TAILLE_POLICE));
        add(comp);
        pack();
        Timer timer = new Timer(PERIODE_RAFRACHISSEMENT_MILLI_SECONDE, e -> afficherNouveauPlateau(comp));
        timer.start();
        setVisible(true);
    }

    private void afficherNouveauPlateau(JTextArea comp) {
        comp.setText(prochainPlateau());
        comp.repaint();
    }

    private String prochainPlateau() {
        boolean[][] nouvelEtat = new boolean[TAILLE_PLATEAU][TAILLE_PLATEAU];
        for (int ligne = 0; ligne < TAILLE_PLATEAU; ligne++) {
            System.arraycopy(etat[ligne], 0, nouvelEtat[ligne], 0, TAILLE_PLATEAU);
        }

        // calculer le nouvel état
        //etat = nouvelEtat;
        etat = calculGenerationSuivante(etat);

        String[][] textePlateau = new String[TAILLE_PLATEAU][TAILLE_PLATEAU];
        for (int ligne = 0; ligne < TAILLE_PLATEAU; ligne++) {
            for (int colonne = 0; colonne < TAILLE_PLATEAU; colonne++) {
                textePlateau[ligne][colonne] = etat[ligne][colonne] ? CARACTERE_VIVANT : CARACTERE_MORT;
            }
        }
        String[] elements = new String[TAILLE_PLATEAU];
        for (int i = 0; i < TAILLE_PLATEAU; i++) {
            elements[i] = String.join("", textePlateau[i]);
        }

        return String.join("\n", elements);
    }

    private static boolean[][] calculGenerationSuivante(boolean[][] plateau){
        //boolean newPlateau[][] = new boolean[3][3];//plateau;
        int nbVoisins;
        for(int i=0; i<plateau.length; i++){
            for(int j=0; j<plateau[0].length; j++){
                nbVoisins = compterVoisins(plateau, i, j);
                if(plateau[i][j]==false){
                    if(nbVoisins == 3){
                        plateau[i][j] = true;
                    }
                }else{
                    if(nbVoisins == 2 || nbVoisins == 3){
                        plateau[i][j] = true;
                    }else{
                        plateau[i][j] = false;
                    }
                }
            }
        }
        return(plateau);
    }

    private static int compterVoisins(boolean[][] plateau, int ligne, int colonne){
        int voisins = 0;
        int iMin;
        int jMin;
        int iMax;
        int jMax;
        if(ligne > 0){
            iMin = 0;
        }else{
            iMin=1;
        }
        if(ligne< plateau.length -1){
            iMax = 3;
        }else{
            iMax = 2;
        }
        if(colonne > 0){
            jMin = 0;
        }else{
            jMin=1;
        }
        if(colonne< plateau[0].length -1){
            jMax = 3;
        }else{
            jMax = 2;
        }
        //System.out.println("i = " + iMin + " et j = " + jMin);
        //System.out.println("iMax = " + iMax + " et jMax = " + jMax);
        for(int i=iMin; i<iMax; i++){
            for(int j=jMin; j<jMax; j++){
                if(plateau[ligne-1 + i][colonne-1 + j] == true){
                    //System.out.println("ligne = " + ligne + " et colonne = " + colonne);
                    voisins += 1;
                }
            }
        }
        if(plateau[ligne][colonne] == true){
            voisins -= 1;
        }
        return(voisins);
    }

    private int compterVoisinsVivants(int ligne, int colonne) {
        return compterVoisins(etat, ligne, colonne);
    }
}