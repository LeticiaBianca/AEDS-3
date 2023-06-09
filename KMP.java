public class KMP {

    //number of operations performed
    int operations;
    //number of times that the pattern was found
    int times;

    //empty contructor
    public KMP(){
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
        System.out.println("KMP: ");
        //time of execution in milliseconds        
        System.out.println((System.currentTimeMillis() - tempoInicial) + " milliseconds");
        System.out.println(operations + " operations");
        System.out.println("The parttern was found "+ times+ " times");
        System.out.println();
    }

    private void InternMatch(String data, String pattern) {
        int dataL = data.length();
        int patternL = pattern.length();

        //basic prefix function
        int[] function = new int[patternL];

        function = funcPref(function, pattern);

        //index in data
        int i = 0;
        //index in pattern
        int j = 0;

        while (i < dataL) {
            //position matched
            if (data.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;

                if (j == patternL) {
                    // Pattern matched
                    times++;
                    j = function[j - 1];
                }
            } else {
                //find position to reset by function
                if (j != 0) {
                    j = function[j - 1];
                } else {
                    i++;
                }
            }
            operations++;
        }
    }

    //find basic prefix function
    private int[] funcPref(int[] function, String pattern) {
        int size = 0;
        int i =1;
        function[0] = 0;

        while(i < pattern.length()){
            if(pattern.charAt(i) == pattern.charAt(size)){
                size++;
                function[i] = size;
                i++;
            }else{
                if(size != 0){
                    size = function[size-1];
                }else{
                    function[i] = 0;
                    i++;
                }
            }
        }

        return function;
    } 
}