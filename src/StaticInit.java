import java.io.*;

public class StaticInit implements InitInterface {
    private String fileName;

    public StaticInit(){
        this.fileName = "defaultFileName";
    }


    public StaticInit(String fileName){
        this.fileName = fileName;
    }

    @Override
    public boolean[][] initPlateau(int TAILLE_PLATEAU) {
        boolean[][] booleans = new boolean[TAILLE_PLATEAU][TAILLE_PLATEAU];
        try {
            String lines = readfromFile(this.fileName);
            for (int ligne = 0; ligne < TAILLE_PLATEAU; ligne++) {
                for (int colonne = 0; colonne < TAILLE_PLATEAU; colonne++) {
                    if(lines.charAt(ligne * colonne + colonne) == '1'){
                        booleans[ligne][colonne] = true;
                    }else{
                        booleans[ligne][colonne] = false;
                    }
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return booleans;
    }

    public void initFile(int TAILLE_PLATEAU) {
        BufferedWriter bufferedWriter = null;
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(fileName + ".txt"));
            String content = "";
            for (int ligne = 0; ligne < TAILLE_PLATEAU; ligne++) {
                for (int colonne = 0; colonne < TAILLE_PLATEAU; colonne++) {
                    boolean b = Math.random() > 0.8;
                    if(b){
                        content += "1";
                    }else{
                        content += "0";
                    }
                }
            }

            bufferedWriter.write(content);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readfromFile(String fileName) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName + ".txt"));
        String lines = "";
        while(bufferedReader.read() > 0) {
            lines += bufferedReader.readLine();
            //lines += '\n';
        }
        bufferedReader.close();
        return(lines);
    }
}
