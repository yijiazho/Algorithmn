package linear;

public class Errors {

//    public int errors(String errorString, int x, int y) {
//        char[] charA = errorString.toCharArray();
//        char[] str1 = charA.clone();
//
//        String str1 = null;
//        for(char c : charA){
//            if (c == '!') {
//                c = '1';
//            }
//        }
//        str1 = charA.toString();
//        String s = new String(str1);
//        int l = errorString.length();
//        int[] numberOfOnes = new int[l];
//        int countStr1 = 0;
//        int count = 0;
//        for(int i = l - 1; i>= 0; i--) {
//            numberOfOnes[i] = count;
//            if (str1.charAt(i) == '1') {
//                count++;
//            }
//        }
//
//        int[] numberOfZero = new int[l];
//
//
//        int count1 = 0;
//        for(int i = l - 1; i>= 0; i--) {
//            numberOfZero[i] = count1;
//            if (str1.charAt(i) == '0') {
//                count1++;
//            }
//        }
//        int MOD = 1000000007;
//        long onezero = 0;
//        long zeorone = 0;
//        for (int i = 0; i < l - 1; i++) {
//            if (str1.charAt(i) == '1') {
//                onezero += l - 1 - i - numberOfOnes[i];
//            } else {
//                zeorone += numberOfZero[i];
//            }
//
//
//        }
//        int res1 =  (int) (onezero * x + zeorone * y) % MOD;
//
//        // do string2
//
//        char[] charA2 = errorString.toCharArray();
//        // replace != 0 and 1 to get two string;
//        // str1 change ! to 1
//        String str2 = null;
//        for(char c : charA2 ){
//            if (c == '!') {
//                c = '0';
//            }
//        }
//        str2 = charA2.toString();
//        System.out.println("str1" + str1);
//        int[] numberOfOnesS2 = new int[l];
//
//        int countStr11 = 0;
//        int count11 = 0;
//        for(int i = l - 1; i>= 0; i--) {
//            numberOfOnesS2[i] = count11;
//            if (str1.charAt(i) == '1') {
//                count11++;
//            }
//        }
//
//        int[] numberOfZeroS2 = new int[l];
//
//
//        int count22 = 0;
//        for(int i = l - 1; i>= 0; i--) {
//            numberOfZeroS2[i] = count22;
//            if (str1.charAt(i) == '0') {
//                count22++;
//            }
//        }
//        long onezero2 = 0;
//        long zeorone2 = 0;
//        for (int i = 0; i < l - 1; i++) {
//            if (str1.charAt(i) == '1') {
//                onezero2 += l - 1 - i - numberOfOnes[i];
//            } else {
//                zeorone2 += numberOfZero[i];
//            }
//
//
//        }
//        int res2 =  (int) (onezero2 * x + zeorone2 * y) % MOD;
//
//        return res1 < res2 ? res1 : res2 ;
//    }
//
//
//    public static final void main(String[] args) {
//        Errors instance = new Errors();
//        instance.errors("101!1", 2, 3);
//    }
}