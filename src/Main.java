import java.io.*;
import java.util.*;

public class Main {

    private static List<Character> letter = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
    public static void main(String[] args) throws Exception {
        ArrayList<Character> book = read("res/voina_i_mir.txt");
        ArrayList<Character> glava = read("res/glava.txt");
        ArrayList<Character> codeglavi = coder(glava);
        Map<Integer, Character> book_frq = frequency(book);
        Map<Integer, Character> glava_frq = frequency(codeglavi);
        for(int i=0;i<codeglavi.size();i++) {
            for (int j = 0; j < 26; j++) {
                if (codeglavi.get(i)==book_frq.get(j)) {
                    codeglavi.set(i, glava_frq.get(j));
                    break;
                }
            }
        }
        BufferedWriter wrbuf = new BufferedWriter(new FileWriter(new File("res/DecodeGlavi.txt")));
        for(int i = 0; i< codeglavi.size();i++) {
            wrbuf.write(codeglavi.get(i));
        }
        wrbuf.close();

        ArrayList<Character> decod1 = read("res/DecodeGlavi.txt");
        Map<Integer, String> book_big = frequency_big(book);
        Map<Integer, String> glava_big = frequency_big(decod1);
        for(int i = 0; i<decod1.size() - 1; i++){
            String s = String.valueOf(decod1.get(i).toString() + decod1.get(i+1).toString());
            for(int j = 0; j < 10; j++){
                if(s.equals(book_big.get(j))){
                    char[] mas = glava_big.get(j).toCharArray();
                    decod1.set(i, mas[0]);
                    decod1.set(i+1, mas[1]);
                    break;
                }
            }
        }
        BufferedWriter wrbuf1 = new BufferedWriter(new FileWriter(new File("res/DecodeGlavi1.txt")));
        for(int i = 0; i< decod1.size();i++) {
            wrbuf1.write(decod1.get(i));
        }
        wrbuf1.close();
    }


    public static ArrayList<Character> read(String filename) throws FileNotFoundException, IOException {
        ArrayList<Character> file = new ArrayList<>();
        BufferedReader scanner = new BufferedReader(new FileReader(new File(filename)));
        int c;
        while((c = scanner.read()) != -1){
            file.add(Character.toLowerCase((char) c));
        }
        scanner.close();
        return file;
    }

    public static Map<Integer, Character> frequency(ArrayList<Character> file){
        Map<Integer, Character> map = new HashMap();
        ArrayList<Integer> val = new ArrayList<>();
        for(int i = 0; i<26;i++){
            val.add(Collections.frequency(file, letter.get(i)));
        }

        for(int i = 0; i<26;i++){
            int temp = val.get(i);
            char c = letter.get(i);
            for(int j = 0; j<26; j++){
                if(val.get(j) > temp){
                    temp = val.get(j);
                    c = letter.get(j);
                }
            }

            map.put(i, c);
            val.set(letter.indexOf(c), -1);
        }
        return map;
    }



    public static Map<Integer, String> frequency_big(ArrayList<Character> file) {
        ArrayList<String> bigramms = new ArrayList<>();
        for (int i = 0; i <= 25; i++) {
            for (int j = 0; j <=25; j++) {
                String crbig = String.valueOf(letter.get(i).toString() + letter.get(j).toString());
                bigramms.add(crbig);
            }
        }
        Map<Integer, String> big_map = new HashMap();
        int[] value = new int[676];
        for(int i=0; i<676;i++){
            value[i] = 0;
        }
        for(int i = 0; i<file.size()-1;i++){
            String buff = String.valueOf(file.get(i).toString() + file.get(i+1).toString());
            for(int j = 0; j<676; j++){
                if(buff.equals(bigramms.get(j))){
                    value[j]++;
                    break;
                }
            }
        }
        for(int i =0; i<10;i++){
            int temp = value[i];
            String c = bigramms.get(i);
            int st = 0;
            for(int j = 0; j<676; j++){
                if(value[j]>temp){
                    temp = value[j];
                    c = bigramms.get(j);
                    st = j;
                }
            }
            big_map.put(i, c);
            value[st] = 0;
        }
        return big_map;
    }


    public static ArrayList<Character> coder(ArrayList<Character> file) throws Exception {
        ArrayList<Character> newFile = (ArrayList<Character>) file.clone();
        for (int i = 0; i < file.size(); i++) {
            for (int j = 0; j < 26; j++) {
                if (file.get(i) == letter.get(j)) {
                    newFile.set(i, letter.get(25 - j));
                }
            }
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("res/ShifrGlavi.txt")));
        for(int i = 0; i< newFile.size();i++) {
            file.set(i, newFile.get(i));
            writer.write(newFile.get(i));
        }
        writer.close();
        return file;
    }
}
