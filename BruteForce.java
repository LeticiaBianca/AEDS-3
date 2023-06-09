public class BruteForce {
    //number of operations performed
    int operations;
    //number of times that the pattern was found
    int times;

    //empty contructor
    public BruteForce(){
        operations = 0;
        times = 0;
    }

    public void match(String data, String pattern) {
        //set the initial time
        long tempoInicial = System.currentTimeMillis();

        //call the match function
        InternMatch(data, pattern);

        //print the infotmations on the screen 
        System.out.println();
        System.out.println("Brute Force: ");
        //time of execution in milliseconds        
        System.out.println((System.currentTimeMillis() - tempoInicial) + " milliseconds");
        System.out.println(operations + " operations");
        System.out.println("The parttern was found "+ times+ " times");
        System.out.println();
    }

    private void InternMatch(String data, String pattern) {
        //position in the pettern
        int j=0;
        for(int i = 0; i<data.length(); i++){
            //set the position in the file and in the pattern
            char letter = data.charAt(i);
            char p1 = pattern.charAt(j);
            //position matched
            if(letter == p1){
                j++;
                 //parttern found - start looking again 
                if(j == pattern.length()){
                    j=0;
                    times++;
                }
            }else{
                //reastart the pattern
                    i-=j;
                    j = 0;
            }
            operations++;
        }
    }
}
