import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OS {

    private ArrayList<PCB> readyQueue = new ArrayList<>(), waitingQueue = new ArrayList<>();
    private Memory memory = new Memory();

    public OS(){
        try {
            Scanner scanner = new Scanner(new File("input.txt"));
            while (scanner.hasNextLine()){
                String[] data = scanner.nextLine().split(",");

                // converts process instructions to an array for easy reading
                char[] burstData = data[2].toCharArray();
                int[] code = new int[burstData.length];
                for (int i=0; i < code.length; i ++) code[i] = Character.getNumericValue(burstData[i]);

                int numberOfPages = (int)Math.ceil(code.length/4.0);
                int[] pages = new int[numberOfPages];
                for (int i = 0; i < pages.length; i ++) pages[i] = memory.getPage();

                int pageC = 0;
                for (int offset = 0; offset < code.length; offset ++){
                    memory.write(new Pair(pages[pageC], offset%4), code[offset]);
                    if (offset%4 == 0 && offset >= 4) pageC++;
                }

                PCB p = new PCB(Integer.parseInt(data[0]), State.Ready, new Pair(pages[0], 0), Integer.parseInt(data[1]), pages);

                readyQueue.add(p);
            }

            while(!readyQueue.isEmpty()){
                System.out.println(readyQueue.remove(0));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
