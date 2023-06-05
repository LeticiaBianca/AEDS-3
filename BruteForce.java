public class BruteForce {
    int operations;
    int times;

    public BruteForce(){
        operations = 0;
        times = 0;
    }

    public void match(String data, String pattern) {
        long tempoInicial = System.currentTimeMillis();
        InternMatch(data, pattern);
        System.out.println();
        System.out.println("Brute Force: ");
        System.out.println((System.currentTimeMillis() - tempoInicial) + " milliseconds");
        System.out.println(operations + " operations");
        System.out.println("The parttern was found "+ times+ " times");
        System.out.println();
    }

    public void InternMatch(String data, String pattern) {
        int j=0;
        for(int i = 0; i<data.length(); i++){
            if(j == pattern.length()){
                j=0;
                times++;
            }
            char letter = data.charAt(i);
            char p1 = pattern.charAt(j);
            if(letter == p1){
                j++;
            }else{
                if(j>0){
                    j--;
                }
            }
            operations++;
        }
    }
}
