import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Memory {

    private int PAGE_SIZE;
    private int pageNumber = 0;
    private DMA dma = new DMA();
    public int[] memory1 = new int[128]; //if each page holds 4 items then one pages corresponds to 4 places in memory

    public Memory(int PAGE_SIZE){
        this.PAGE_SIZE = PAGE_SIZE;

        new Thread(dma).start();
    }

    public int getPage(){
        return pageNumber ++;
    }

    public void write(Pair addressVector, int data){ // takes logical address and writes data to physical address
        int pageNumber = addressVector.page();
        int offset = addressVector.offset();
        int physicalAddress = pageNumber * PAGE_SIZE + offset;
        while (physicalAddress > memory1.length) memory1 = Arrays.copyOf(memory1, memory1.length * 2); //just in case
        memory1[physicalAddress] = data;
    }

    public int access(Pair addressVector){  // access the data in the physical memory at an address.
        int pageNumber = addressVector.page();
        int offset = addressVector.offset();
        int physicalAddress = pageNumber * PAGE_SIZE + offset;
        return memory1[physicalAddress];
    }

    // dma class wrapper methods


    public int accessDMA(){
       return dma.accessDMA();
    }

    public void writeDMA(int data, int process){
        dma.writeDMA(data, process);
    }

    public void setDMAFlag(boolean flag){
        dma.setDmaFlag(flag);
    }

    public void setIOFlag(boolean flag){
        dma.setIOFlag(flag);
    }

    public boolean isDMAFlag(){
        return dma.isDmaFlag();
    }

    public boolean isIOFlag(){
        return dma.isIOFlag();
    }

    public void killDMA(){
        dma.setDone(true);
    }
}

class DMA implements Runnable{
    private boolean dmaFlag = true, IOFlag = true, done = false;
    private List<Integer> memory2 = Collections.synchronizedList(new ArrayList<Integer>());

    @Override
    public void run() {
        while(!done) {
            if (memory2.size() > 0) {
                int y = accessDMA();
                System.out.println("data: " + y);
                setDmaFlag(false);
            }
        }
    }

    boolean isDmaFlag() {
        return dmaFlag;
    }

    boolean isIOFlag() {
        return IOFlag;
    }

    void setDone(boolean flag){
        done = flag;
    }

    void setDmaFlag(boolean dmaFlag) {
        this.dmaFlag = dmaFlag;
    }

    void setIOFlag(boolean IOFlag) {
        this.IOFlag = IOFlag;
    }

    int accessDMA(){
        return memory2.remove(0);
    }

    void writeDMA(int data, int process){
        memory2.add(process);
    }
}
