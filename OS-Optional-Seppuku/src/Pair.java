public class Pair {
    private int k, v;

    public Pair(int k, int v){
        this.k = k;
        this.v = v;
    }

    public int page(){
        return k;
    }

    public int offset(){
        return v;
    }

    public void setPage(int k){
        this.k = k;
    }

    public void setOffset(int v){
        this.v = v;
    }

    public Pair clone(){
        return new Pair(this.k, this.v);
    }
}
