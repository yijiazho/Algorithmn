package dataStructure;

public class OddEvenAutomata {


    public boolean validate(String s) {
        if (s == null || s.isEmpty()) {
            return true;
        }
        OddEvenState currentState = OddEvenState.EVEN_EVEN;

        for (char c: s.toCharArray()) {
            if (c == 'a') {
                switch (currentState) {
                    case ODD_ODD:
                        currentState = OddEvenState.EVEN_ODD;
                        break;
                    case EVEN_ODD:
                        currentState = OddEvenState.ODD_ODD;
                        break;
                    case ODD_EVEN:
                        currentState = OddEvenState.EVEN_EVEN;
                        break;
                    case EVEN_EVEN:
                        currentState = OddEvenState.ODD_EVEN;
                        break;
                }
            } else if (c == 'b') {
                switch (currentState) {
                    case ODD_ODD:
                        currentState = OddEvenState.ODD_EVEN;
                        break;
                    case EVEN_ODD:
                        currentState = OddEvenState.EVEN_EVEN;
                        break;
                    case ODD_EVEN:
                        currentState = OddEvenState.ODD_ODD;
                        break;
                    case EVEN_EVEN:
                        currentState = OddEvenState.EVEN_ODD;
                        break;
                }
            } else {
                return false;
            }
        }
        return currentState == OddEvenState.EVEN_EVEN || currentState == OddEvenState.ODD_ODD;
    }

    public static void main(String[] args) {
        String input = "aaaab";
        OddEvenAutomata oddEvenAutomata = new OddEvenAutomata();
        System.out.println(oddEvenAutomata.validate(input));
    }
}

enum OddEvenState {
    EVEN_EVEN,
    EVEN_ODD,
    ODD_EVEN,
    ODD_ODD
}
