import java.util.Arrays;

public class CPU {

    public  static void main(String[] args) throws IndexOutOfBoundsException{
        final int PAGE_SIZE = 4;
        OS os = new OS(PAGE_SIZE);
        PCB pcb = os.scheduler();
        Pair pcVal = pcb.getPcVal();
        while (os.terminated()){

            if (os.ready()) {
                //get data from memory
                int data = os.memory.access(pcVal);

                //check to see what the data is
                if (data == 1) {
                    new BubbleSort().run();
                } else {
                    os.deviceDriver(pcVal);
                    os.savePCB(State.Waiting);
                }


                //check for interruption from DMA
                if (!os.memory.isDMAFlag()) {
                    os.savePCB(State.Ready);
                    //call os interrupt handler
                    os.interruptHandler();
                    //get new pcb
                    pcb = os.scheduler();
                    pcVal = pcb.getPcVal();
                } else {
                    if (data == 1) {
                        if (os.incrementPcVal()) {
                            pcb = os.getpCurr();
                            pcVal = pcb.getPcVal();
                        } else {
                            if (os.ready()) {
                                pcb = os.scheduler();
                                pcVal = pcb.getPcVal();
                            }
                        }
                    } else {
                        if (os.ready()) {
                            pcb = os.scheduler();
                            pcVal = pcb.getPcVal();
                        }
                    }
                }
            }
            if (!os.memory.isDMAFlag()) {
                os.savePCB(State.Ready);
                //call os interrupt handler
                os.interruptHandler();
                //get new pcb
                pcb = os.scheduler();
                pcVal = pcb.getPcVal();
            }
                //os.interruptHandler();


        }

        System.out.println("finish");

        os.memory.killDMA();

        System.exit(0);
    }
}
