public class PCB {

    private int id, arrivalOrder;
    private  int[] pages;
    private int page_i = 0;
    private long arrivalTime, endTime, firstIO;
    private State state;
    private Pair pcVal;
    private int offset, PAGE_SIZE;

    public PCB(int id, State state, Pair pcVal, int arrivalOrder, int[] pages, int offset, int PAGE_SIZE){
        this.id = id;
        this.arrivalTime = System.currentTimeMillis();
        this.state = state;
        this.pcVal = pcVal;
        this.offset = offset;
        this.arrivalOrder = arrivalOrder;
        this.pages = pages;
        this.PAGE_SIZE = PAGE_SIZE;
    }

    public int getId() {
        return id;
    }

    public Pair getPcVal() {
        return pcVal;
    }

    public void setPcVal(Pair pcVal) {
        this.pcVal = pcVal;
    }

    public boolean incrementPcVal(){
        //need to next page
        if (pcVal.offset() >= PAGE_SIZE - 1){
            page_i++;
            pcVal.setPage(pages[page_i]);
            pcVal.setOffset(0);
        }
        else{
            pcVal.setOffset(pcVal.offset() + 1);
        }

        //if we are at the end of the pages
        if (page_i == pages.length - 1 && pcVal.offset() >= offset % PAGE_SIZE){
            return false;
        }
        return true;
    }

    public long getArrivalTime() {
        return arrivalTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setFirstIO() {
        this.firstIO = System.currentTimeMillis();
    }

    public long getFirstIO() {
        return firstIO;
    }

    public State getState() {
        return state;
    }

    public void setState(State state){
        this.state = state;
    }

    public void setEndTime() {
        this.endTime = System.currentTimeMillis();
    }

    //To do: PCB data structure of a process
    //for example: Process_id, Arrive_time, state,
    //PositionOfNextInstructionToExecute(PC value)
    //and so on
}
