public class Comparator implements java.util.Comparator {

    public static int compareBySteps(ConcretePiece o1, ConcretePiece o2) {
        int ans = 0;
        // who did the less steps
        if (o1.getAllPositions().size() > o2.getAllPositions().size())
            ans = 1;
        if (o2.getAllPositions().size() > o1.getAllPositions().size())
            ans = -1;
        // is they are equals so who have the minimal number in  his name
        if (ans == 0){
            int name1 = Integer.parseInt(o1.getName().substring(1));
            int name2 = Integer.parseInt(o2.getName().substring(1));
            if (name1 > name2)
                ans = 1;
            else ans = -1;
        }
        return ans;
    }

    public static int compareByKills(ConcretePiece o1, ConcretePiece o2){
        int ans = 0;
        // who kill the most
        if (o1.getNum_of_kills() > o2.getNum_of_kills())
            ans = -1;
        if (o2.getNum_of_kills() > o1.getNum_of_kills())
            ans = 1;
        // if they are equals so who have the smallest number in his name
        if (ans == 0){
            int name1 = Integer.parseInt(o1.getName().substring(1));
            int name2 = Integer.parseInt(o2.getName().substring(1));
            if (name1 > name2)
                ans = 1;
            if (name2 > name1)
                ans = -1;
        }
        // if they are equals so who win
        if (ans == 0){
            if (o1.getOwner().isWin())
                ans = -1;
            else ans = 1;
        }
        return ans;
    }

    public static int compareByDistance(ConcretePiece o1, ConcretePiece o2){
        int ans = 0;
        // who did the biggest distance
        if (o1.getDistance() > o2.getDistance())
            ans = -1;
        if (o2.getDistance() > o1.getDistance())
            ans = 1;
        // if they are equals so who have the smallest number in his name
        if (ans == 0){
            int name1 = Integer.parseInt(o1.getName().substring(1));
            int name2 = Integer.parseInt(o2.getName().substring(1));
            if (name1 > name2)
                ans = 1;
            if (name2 > name1)
                ans = -1;
        }
        // if they are equals so who win
        if (ans == 0){
            if (o1.getOwner().isWin())
                ans = -1;
            else ans = 1;
        }
        return ans;
    }
    public static int compareThePositions(Position o1, Position o2){
        int ans = 0;
        // who have the biggest num of stepping
        if (o1.getNum_of_stepping() > o2.getNum_of_stepping())
            ans = -1;
        if (o2.getNum_of_stepping() > o1.getNum_of_stepping())
            ans = 1;

        // compare by x
        if (ans == 0){
            if (o1.getX() > o2.getX())
                ans = 1;
            if (o2.getX() > o1.getX())
                ans = -1;
        }
        // compare by y
        if (ans == 0){
            if (o1.getY() > o2.getY())
                ans = 1;
            if (o2.getY() > o2.getY())
                ans = -1;
        }
        return ans;
    }

    @Override
    public int compare(Object o1, Object o2) {
        return 0;
    }
}
