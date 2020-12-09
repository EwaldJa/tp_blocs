import exceptions.BlocNotFoundException;
import exceptions.MovementUnavailableException;
import model.Bloc;
import model.MetaBloc;
import model.Table;
import model.World;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        World world = new World(3, 4);


        Table table = new Table();

        List<Bloc> allBlocs = new ArrayList<>();
        Bloc bloc_A = new Bloc("A", table, world);  allBlocs.add(bloc_A);
        Bloc bloc_B = new Bloc("B", bloc_A, world); allBlocs.add(bloc_B);
        Bloc bloc_C = new Bloc("C", bloc_B, world); allBlocs.add(bloc_C);
        Bloc bloc_D = new Bloc("D", bloc_C, world); allBlocs.add(bloc_D);

        List<MetaBloc> initial_state = new ArrayList<>();
        initial_state.add(table);
        initial_state.add(bloc_B); bloc_B.setInferior(table);
        initial_state.add(bloc_A); bloc_A.setInferior(bloc_B);
        initial_state.add(bloc_D); bloc_D.setInferior(bloc_A);
        initial_state.add(bloc_C); bloc_C.setInferior(bloc_D);

        world.initialize(table, initial_state);

        String worldStr = world.toString();
        System.out.println(worldStr);
        String newWorldStr = "";
        int countSameWorld = 0;
        while(countSameWorld < 6) {
            worldStr = new String(newWorldStr);
            StringBuilder blocs = new StringBuilder();
            Collections.shuffle(allBlocs);
            for (Bloc b:allBlocs) {
                try {
                    b.percept();
                    b.action(); }
                catch (BlocNotFoundException | MovementUnavailableException e) {
                    e.printStackTrace(); }
                blocs.append("  [").append(b.getBlocName()).append(", ").append(b.getSatisfaction()).append("]  ");
            }
            newWorldStr = world.toString();
            if (worldStr.equals(newWorldStr)) {
                countSameWorld++; }
            else {
                countSameWorld = 0; }
            System.out.println(newWorldStr);
            System.out.println(blocs.toString());
        }
    }
}
