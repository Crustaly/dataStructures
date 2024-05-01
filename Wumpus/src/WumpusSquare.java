public class WumpusSquare {
    private boolean gold, ladder, pit, breeze, wumpus, deadWumpus, stench, visited;

    public WumpusSquare(){
        gold = ladder = pit = breeze = wumpus = deadWumpus = stench = visited = false;
    }

    public boolean getGold() {return gold;}
    public boolean getLadder() {return ladder;}
    public boolean getPit() {return pit;}
    public boolean getBreeze() {return breeze;}
    public boolean getWumpus() {return wumpus;}
    public boolean getDeadWumpus() {return deadWumpus;}
    public boolean getStench() {return stench;}
    public boolean getVisited() {return visited;}

    public void setGold(boolean b) {gold = b;}
    public void setLadder(boolean b) {ladder = b;}
    public void setPit(boolean b) {pit = b;}
    public void setBreeze(boolean b) {breeze = b;}
    public void setWumpus(boolean b) {wumpus= b;}
    public void setDeadWumpus(boolean b) {deadWumpus = b;}
    public void setStench(boolean b) {stench = b;}
    public void setVisited(boolean b) {visited = b;}

    @Override
    public String toString() {
        if(wumpus && gold) return "@";
        if(deadWumpus && gold) return "!";
        if(wumpus) return "W";
        if(deadWumpus) return "D";
        if(ladder) return "L";
        if(pit) return "P";
        if(gold) return "G";
        return "*";
    }
}
