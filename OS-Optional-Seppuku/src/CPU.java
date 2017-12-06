import java.util.Arrays;

public class CPU {

    public  static void main(String[] args){
        final int PAGE_SIZE = 4;
        OS os = new OS(PAGE_SIZE);

        while (os.terminated()){
            PCB pcb;
            Pair pcVal;

            pcb = os.scheduler();
            pcVal = pcb.getPcVal();

            //get data from memory
            int data = os.memory.access(pcVal);

            //check to see what the data is
            if (data == 1){
                new BubbleSort().run();
                os.savePCB(State.Ready);
            }
            else{
                os.deviceDriver(pcVal);
                os.savePCB(State.Waiting);
            }

            //check for interruption from DMA
            /*if (os.memory.isDMAFlag()){
                //call os interrupt handler
                os.interruptHandler();
            }*/
            os.interruptHandler();


        }

        System.out.println("finish");

        os.memory.killDMA();

        System.exit(0);
    }
}
