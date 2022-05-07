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
 * This class is the main class that deals with type analysis
 * hand command. Depending on what kind of order he receives, he goes with a
 * switch on each branch.
 * Each branch calls a function, and each function will create objects on
 * which will put them in a JSON array
 */
public class CommandMain {
    private Input input;

    /**
     * @param input input received from the input file
     */
    public CommandMain(final Input input) {
        this.input = input;
    }

    /**
     * The function checks the type of command, and calls the required functions on
     * each branch
     * @param indexAction action id
     * @param arrayResult JSON array in which I will put the objects that will be displayed
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
     * For favorite order, depending on whether the order is successful or not,
     * I will create specific objects that I will put in the JSON array for
     * display in file
     * @param indexAction action id
     * @param arrayResult JSON array for objects
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

        //  I walk through the users
        int nrUseri = input.getUsers().size();
        for (int i = 0; i < nrUseri; i++) {
            String userName = input.getUsers().get(i).getUsername();
            if (userName == null) {
                continue;
            }
            if (userName.compareTo(user) == 0) {
                //  if I find the user I want
                // I see if he has the movie in the list of views
                Map<String, Integer> history = input.getUsers().get(i).getHistory();
                if (history.containsKey(title)) {
                    //  I add to the favorites list
                    ArrayList<String> list;
                    list = input.getUsers().get(i).getFavoriteMovies();
                    //  if the movie is already in the favorites list
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
                    //  If I don't find the movie
                    FavNo nf = new FavNo(title, id);
                    arrayResult.add(nf);
                }
            }
        }
    }

    /**
     * For command view, I will create an object that I will display in
     * JSON array
     * @param indexAction action id
     * @param arrayResult JSON array in which I put the objects
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
     * @param index action id
     * @return user name
     */
    public String getUserAction(final int index) {
        return input.getCommands().get(index).getUsername();
    }

    /**
     *
     * @param index action id
     * @return movie title
     */
    public String getTitle(final int index) {
        return input.getCommands().get(index).getTitle();
    }

}
