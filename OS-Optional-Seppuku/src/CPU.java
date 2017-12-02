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

            //remember old pc val
            Pair pcValOld = pcVal.clone();

            //check offset and increment
            if (pcVal.offset() >= PAGE_SIZE){
                try {
                    pcVal.setPage(pcb.nextPage());
                    pcVal.setOffset(0);
                }
                catch (ArrayIndexOutOfBoundsException e){
                    os.processTerminated();
                }
            }
            else pcVal.setOffset(pcVal.offset() + 1);

            //check to see what the data is
            if (data == 1){
                new BubbleSort().run();
                os.savePCB(pcVal, State.Ready);
            }
            else{
                os.deviceDriver(pcValOld);
                os.savePCB(pcVal, State.Waiting);
            }

            //check for interruption from DMA
            if (os.memory.isDMAFlag()){
                //call os interrupt handler
                os.interruptHandler();
            }


        }

        System.out.println("finish in cpu");

        os.memory.killDMA();
    }
}
