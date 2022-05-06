package main.Command;

import common.Constants;
import fileio.Input;
import main.Command.Favorite.FavDuplicate;
import main.Command.Favorite.Favorite;
import main.Command.Favorite.FavNo;
import main.Command.Rating.RatingMain;
import main.Command.View.View;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Map;

/**
 * Aceasta clasa este Clasa principala care se ocupa de analizarea tipului
 * de comanda main. In functie de ce fel de comanda primeste, se duce cu un
 * switch pe fiecare ramura.
 * Fiecare ramura apeleaza o functie, iar fiecare functie va crea obiecte pe
 * care le va pune intr-un JSON array
 */
public class CommandMain {
    private Input input;

    /**
     * @param input input primit de la fisierul de intrare
     */
    public CommandMain(final Input input) {
        this.input = input;
    }

    /**
     * Functia verifica tipul comenzii, si apeleaza functiile necesare pe
     * fiecare ramura
     * @param indexAction id actiune
     * @param arrayResult JSON array in care voi pune obiectele ce se vor afisa
     */
    public void command(final int indexAction, final JSONArray arrayResult) {
        String type = input.getCommands().get(indexAction).getType();
        if (type == null) {
            return;
        }
        switch (type) {
            case Constants.FAVORITE:
                favorite(indexAction, arrayResult);
                break;
            case Constants.VIEW:
                view(indexAction, arrayResult);
                break;
            case Constants.RATING:
                RatingMain rm = new RatingMain(input, indexAction, arrayResult);
                rm.rating();
                break;
            default:
                break;
        }
    }

    /**
     * Pentru comand favorite, in functie daca comanda reuseste sau nu,
     * voi crea obiecte specifice pe care le voi pune in JSON array pentru
     * afisare in fisier
     * @param indexAction  id actiune
     * @param arrayResult JSON array pentru obiecte
     */
    public void favorite(final int indexAction, final JSONArray arrayResult) {
        int id = input.getCommands().get(indexAction).getActionId();

        String user = input.getCommands().get(indexAction).getUsername();
        if (user == null) {
            return;
        }

        String title = input.getCommands().get(indexAction).getTitle();
        if (title == null) {
            return;
        }

        //  ma plimb prin useri
        int nrUseri = input.getUsers().size();
        for (int i = 0; i < nrUseri; i++) {
            String userName = input.getUsers().get(i).getUsername();
            if (userName == null) {
                continue;
            }
            if (userName.compareTo(user) == 0) {
                //  daca gasesc user-ul dorit
                //  ma uit daca are filmul in lista de vazute
                Map<String, Integer> history = input.getUsers().get(i).getHistory();
                if (history.containsKey(title)) {
                    //  adaug in lista de favorite
                    ArrayList<String> list;
                    list = input.getUsers().get(i).getFavoriteMovies();
                    //  daca in lista de favorite se afla deja filmul
                    if (list.contains(title)) {
                        FavDuplicate df = new FavDuplicate(title, id);
                        arrayResult.add(df);
                    } else {
                        list.add(title);
                        input.getUsers().get(i).setFavoriteMovies(list);
                        Favorite favorite  = new Favorite(input, user, title, id);
                        arrayResult.add(favorite);
                    }
                } else {
                    //  daca nu gasesc filmul
                    FavNo nf = new FavNo(title, id);
                    arrayResult.add(nf);
                }
            }
        }
    }

    /**
     * Pentru command view, Voi crea un obiect pe care il voi afisa in
     * JSON array
     * @param indexAction id actiune
     * @param arrayResult JSON array in care pun obiecte
     */
    public void view(final int indexAction, final JSONArray arrayResult) {
        View view = new View(input, getUserAction(indexAction),
                getTitle(indexAction), indexAction);
        if (view.checkView()) {
            view.setActionId();
            view.setNrView();
            arrayResult.add(view);
        }
    }

    /**
     *
     * @param index id actiune
     * @return numele utilizatorului
     */
    public String getUserAction(final int index) {
        return input.getCommands().get(index).getUsername();
    }

    /**
     *
     * @param index id actiune
     * @return titlul filmului
     */
    public String getTitle(final int index) {
        return input.getCommands().get(index).getTitle();
    }

}
