import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class OS {

    private ArrayList<PCB> readyQueue = new ArrayList<>(), waitingQueue = new ArrayList<>();
    private final int PAGE_SIZE;
    public Memory memory;
    private PCB pCurr;

    public OS(int PAGE_SIZE){
        this.PAGE_SIZE = PAGE_SIZE;
        memory = new Memory(this.PAGE_SIZE);

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


        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void deviceDriver(Pair addressVector){
        memory.writeDMA(memory.access(addressVector));
    }

    public Pair scheduler(){
        PCB p1 = readyQueue.remove(0);
        p1.setState(State.Running);
        pCurr = p1;
        return pCurr.getPcVal();
    }

    public void savePCB(Pair PC, State state){
        pCurr.setPcVal(PC);
        if (state == State.Waiting){
            pCurr.setState(State.Waiting);
            waitingQueue.add(pCurr);
        }
        else{
            pCurr.setState(State.Ready);
            readyQueue.add(pCurr);
        }
        pCurr = null;
    }

    public void interruptHandler(){
        PCB p = waitingQueue.remove(0);
        p.setState(State.Ready);
        readyQueue.add(p);
        memory.removeDMA();
        memory.setDMAFlag(true);
    }
}
