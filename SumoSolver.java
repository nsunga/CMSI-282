import java.util.ArrayList;
import java.util.Arrays;
import java.lang.Integer;
import java.lang.Boolean;
import java.lang.IllegalArgumentException;

public class SumoSolver {
    public static void main(String args[]){
        try {
            if (args.length % 2 == 0 || args.length == 1) {
                throw new IllegalArgumentException();
            }

            for(int i = 0; i < args.length; i++) {
                if(Integer.parseInt(args[i]) < 0){
                    throw new IllegalArgumentException();
                }
            }

            int costToMake = Integer.parseInt(args[args.length - 1]);
            int[] amt = new int [(args.length - 1) / 2];
            int[] weights = new int[(args.length - 1) / 2];

            fillArray(amt, weights, args);

            ArrayList[][] table = new ArrayList [amt.length][costToMake + 1];

            ArrayList<Boolean> result = optimization(table, amt, weights, costToMake);
            cellContents(result, amt, weights);
        } catch(IllegalArgumentException e) {
            System.out.println("BAD DATA");
        }
    }

    public static void fillArray(int[] cost,int[] weight, String[] args){
        for(int i = 0, n = 0; i < cost.length; i++, n += 2){
            cost[i] = Integer.parseInt(args[n]);
            weight[i] = Integer.parseInt(args[n+1]);
        }
    }

    public static ArrayList<Boolean> addToCell(ArrayList<Boolean> cellA, ArrayList<Boolean> cellB){
        for(int i = 0; i < cellA.size(); i++){
            if(cellA.get(i).booleanValue() == true){
                cellB.set(i, new Boolean(true));
            }else if(cellB.get(i).booleanValue() != true){
                cellB.set(i, new Boolean(false));
            }
        }
        return cellB;
    }

    public static ArrayList<Boolean> bestOf(ArrayList<Boolean> cellA, ArrayList<Boolean> cellB, int[] weights){
        int weightA = 0;
        int weightB = 0;
        for(int i = 0; i < cellA.size(); i++){
            if(cellA.get(i).booleanValue())
                weightA += weights[i];
            if(cellB.get(i).booleanValue())
                weightB += weights[i];
        }
        return weightA >= weightB ? cellA : cellB;
    }

    public static int getCost(ArrayList<Boolean> cell, int[] amt){
        int total = 0;
        for(int i = 0; i < cell.size(); i++){
            if(cell.get(i).booleanValue() == true)
                total += amt[i];
        }
        return total;
    }

    public static ArrayList<Boolean> evaluate(ArrayList<Boolean> cell, int[] amt, int[] weights, int row, int col, ArrayList[][] table, int costToMake){
        cell = new ArrayList<Boolean>(amt.length);
        for(int i = 0; i < amt.length; i++){
            cell.add(new Boolean(false));
        }
        if(col - amt[row] >= 0){
            cell.set(row, new Boolean(true));
            if(table[row][col - amt[row]] == null){
                ArrayList<Boolean> newCell = evaluate(table[row][col - amt[row]], amt, weights, row, col-amt[row], table, costToMake);
                table[row][col-amt[row]] = newCell;
                table[row][col] = addToCell(newCell, cell);
                cell = table[row][col];
            }
            else{
                table[row][col] = addToCell(table[row][col-amt[row]], cell);
                cell = table[row][col];
            }
        }else{
            for(int i = 0; i < cell.size(); i++){
                cell.set(i, new Boolean(false));
            }
        }

        if(row - 1 >= 0 && table[row - 1][col] == null){
            ArrayList<Boolean> upperCell = evaluate(table[row-1][col], amt, weights, row - 1, col, table, costToMake);
            table[row-1][col] = upperCell;

            int upperCost = getCost(upperCell, amt);
            int lowerCost = getCost(cell, amt);

            if(upperCost + lowerCost <= col)
                cell = addToCell(cell, upperCell);

            cell = bestOf(cell, upperCell, weights);
        }else if(row - 1 >= 0)
            cell = bestOf(cell, table[row-1][col], weights);

        table[row][col] = cell;

        return cell;
    }

    public static ArrayList<Boolean> optimization(ArrayList[][] table, int[] amt, int [] weights, int costToMake){
        int col = costToMake;
        int row = amt.length - 1;
        if(table[row][col] == null){
            ArrayList<Boolean> cell = evaluate(table[row][col], amt, weights, row, col, table, costToMake);
            table[row][col] = cell;
            if(table[row-1][col] == null){
                ArrayList<Boolean> upperCell = evaluate(table[row-1][col], amt, weights, row - 1, col, table, costToMake);
                table[row][col] = bestOf(cell, upperCell, weights);
            }
        }
        return table[row][col];
    }

    public static void cellContents(ArrayList<Boolean> cell, int[] amt, int[] weights){
        int itemsBought = 0;
        int moneySpent = 0;
        int totalPounds = 0;
        for(int i = 0; i < cell.size(); i++){
            if(cell.get(i).booleanValue() == true){
                System.out.println("$" + amt[i] +" / " + weights[i] + " pounds");
                itemsBought += 1;
                moneySpent += amt[i];
                totalPounds += weights[i];
            }
        }
        System.out.println(itemsBought + " items / $" + moneySpent + " / " + totalPounds + " pounds");
    }
}
