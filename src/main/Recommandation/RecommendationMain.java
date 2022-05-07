package main.Recommandation;

import common.Constants;
import fileio.Input;
import main.Command.Rating.Rating;
import org.json.simple.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecommendationMain {

    private Input input;


    public RecommendationMain(final Input input) {
        this.input = input;
    }

    /**
     * The main function, which determines what type of recommendation the order has,
     * then calls a function for each type, and these
     * functions will create objects that will point to a JSOn array
     * @param indexAction id actiune
     * @param arrayResult pentru afisare format JSON
     */
    public void recommendation(final int indexAction, final JSONArray arrayResult) {
        ArrayList<String> arrayList;
        String recommandation =
                input.getCommands().get(indexAction).getType();
        if (recommandation != null) {
            switch (recommandation) {
                case Constants.STANDARD:
                    standard(indexAction, arrayResult);
                    break;
                case Constants.BESTUNSEEN:
                    bestUnseen(indexAction, arrayResult);
                    break;
                case Constants.POPULAR:
                    popular(indexAction, arrayResult);
                    break;
                case Constants.FAVORITE:
                    favoriteRecommandation(indexAction, arrayResult);
                    break;
                case Constants.SEARCH:
                    arrayList = search(indexAction, arrayResult);
                    if (arrayList != null) {
                        arrayList = sortArrayList(sortArrayList(arrayList));
                    }
                    if (arrayList != null) {
                        String checkIfSearchError = arrayList.get(0);
                        if (checkIfSearchError != null
                                && checkIfSearchError.compareTo(Constants.NIMIC) == 0) {
                            RecSearchError searchError = new RecSearchError(input,
                                    indexAction);
                            arrayResult.add(searchError);
                        } else if (checkIfSearchError != null) {
                            RecSearch searchRecommendation =
                                    new RecSearch(input, indexAction,
                                            arrayList);
                            arrayResult.add(searchRecommendation);
                        }
                        break;
                    }
                default:
                    break;
            }
        }
    }

    /**
     *
     * @return nr de actiuni
     */
    public int getNrActions() {
        return input.getCommands().size();
    }
    /**
     *
     * @param index id user
     * @return nume utilizator
     */
    public String getUserAction(final int index) {
        return input.getCommands().get(index).getUsername();
    }
    /**
     *
     * @param index id actiune
     * @return numele filmului de la index
     */
    public String getTitle(final int index) {
        return input.getCommands().get(index).getTitle();
    }

    /**
     * @return return rating
     */
    public Map<String, String> getRating() {
        Map<String, String> map = new HashMap<>();
        int nrActions = getNrActions();
        for (int indexAction = 0; indexAction < nrActions; indexAction++) {
            String user = getUserAction(indexAction);
            String movie = getTitle(indexAction);
            Rating rating = new Rating(input, user, movie, indexAction);
            rating.applyRating();
            if (rating.chechRating()) {
                if (!rating.checkSeen()) {
                    map.put(user, movie);
                }
            }
        }
        return map;
    }

    /**
     * Function that deals with the popular recommendation category
     * @param indexAction id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public void popular(final int indexAction, final JSONArray arrayResult) {
        RecPopular pr = new RecPopular(input,
                indexAction);

        //  daca un standard user cere ceva de la premium
        int nrUser = input.getCommands().get(indexAction).getNumber();
        String subscription =
                input.getUsers().get(nrUser).getSubscriptionType();
        if (subscription.compareTo(Constants.BASIC) == 0) {
            RecStandardInvalid si = new RecStandardInvalid(input, indexAction);
            arrayResult.add(si);
        } else {
            arrayResult.add(pr);
        }
    }

    /**
     * The function sorts a received list
     * @param arrayList  lista de sortat
     * @return lista sortata
     */
    public ArrayList<String> sortArrayList(final ArrayList<String> arrayList) {
        if (arrayList != null) {
            Collections.sort(arrayList);
        }
        //  lista sortata
        assert arrayList != null;
        return new ArrayList<>(arrayList);
    }

    /**
     * @param indexAction id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public void standard(final int indexAction, final JSONArray arrayResult) {
        RecStandard standardRecomandation =
                new RecStandard(input, indexAction,
                        getUserAction(indexAction));
        standardRecomandation.setMovie();
        if (standardRecomandation.checkStandard()) {
            arrayResult.add(standardRecomandation);
        }
    }
    /**
     * @param indexAction id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    public void bestUnseen(final int indexAction, final JSONArray arrayResult) {
        int nrComenzi = input.getCommands().size();
        int nrUsers = input.getUsers().size();
        String user = input.getCommands().get(indexAction).getUsername();
        String movieFinal = null;
        double max = Constants.MAX;
        if (user != null) {
            for (int i = 0; i < nrComenzi; i++) {
                String userFromCommands =
                        input.getCommands().get(i).getUsername();
                if (userFromCommands != null
                        && user.compareTo(userFromCommands) == 0) {
                    // now I only have access to actions related to
                    // the user the problem wants
                    String movieWatchedCommand =
                            input.getCommands().get(i).getTitle();
                    //  I wander through the user's movie list and look for what
                    // I haven't seen any movies
                    for (int j = 0; j < nrUsers; j++) {
                        //  I'm looking for it
                        // movies not seen by the user
                        String cautaUser =
                                input.getUsers().get(j).getUsername();
                        Map<String, Integer> movieWatchedUserHist =
                                input.getUsers().get(j).getHistory();
                        if (cautaUser != null && user.compareTo(cautaUser) == 0) {
                            for (String name : movieWatchedUserHist.keySet()) {
                                if (name != null) {
                                    Integer value =
                                            movieWatchedUserHist.get(name); // 1
                                    if (movieWatchedCommand != null
                                            && value != null
                                            && name.compareTo(movieWatchedCommand)
                                            != 0) {
                                        //  if i find a movie in user in
                                        // command
                                        // which is different from what I have
                                        // take the grade and put it in a max
                                        if (value < max) {
                                            max = value;
                                            movieFinal = movieWatchedCommand;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        RecBestUnseen bestUnseen = new RecBestUnseen(input, movieFinal, indexAction,
                user);
        if (bestUnseen.checkValid()) {
            arrayResult.add(bestUnseen);
        }
    }

    /**
     * I'm looking for a movie in the list of other users
     * A movie I've never seen before
     * @param indexAction
     * @param arrayResult to formally place JSONArray objects
     */
    public void favoriteRecommandation(final int indexAction,
                                       final JSONArray arrayResult) {

        String username = input.getCommands().get(indexAction).getUsername();
        if (username == null) {
            return;
        }
        //  go through all users
        int nrUseri = input.getUsers().size();
        //  nr de filme favorite
        ArrayList<String> listFavorite = null;
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();
            if (user == null) {
                continue;
            }
            //  if I find the user, I look at his history list
            if (user.compareTo(username) == 0) {
                listFavorite = input.getUsers().get(i).getFavoriteMovies();
            }
        }
        // they go through all the users, except the one who gives the order
        Map<String, Integer> map = new HashMap<>();
        //  they go through all the users, except the one who gives the order
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();

            if (user == null) {
                continue;
            }
            if (user.compareTo(username) != 0) {
                //  I look at his list of favorites
                ArrayList<String> listaUserFav =
                        input.getUsers().get(i).getFavoriteMovies();
                if (listaUserFav == null) {
                    continue;
                }
                for (String movie : listaUserFav) {
                    if (movie == null) {
                        continue;
                    }
                    // if I find a movie that isn't there
                    // in my current list
                    // you need a condition
                    // that this film is not already in history

                    assert listFavorite != null;
                    if (!listFavorite.contains(movie)) {
                        if (map.containsKey(movie)) {
                            int aux = map.get(movie);
                            aux++;
                            map.put(movie, aux);
                        } else {
                            map.put(movie, 1);
                        }
                    }
                }
            }

        }
        //  compare what I have in the map with the user's history list
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();
            if (user == null) {
                continue;
            }
            if (user.compareTo(username) == 0) {
                Map<String, Integer> history = input.getUsers().get(i).getHistory();
                if (history == null) {
                    continue;
                }
                for (Map.Entry<String, Integer> entry : history.entrySet()) {
                    map.remove(entry.getKey());
                }

            }
        }

        ArrayList<String> aux = new ArrayList<>(map.keySet());
        aux.sort(Collections.reverseOrder());
        String recommandation = null;
        if (aux.size() != 0) {
            recommandation = aux.get(aux.size() - 1);
        }
        if (recommandation != null)  {
            RecFavorite fr =
                    new RecFavorite(input, indexAction,
                            getUserAction(indexAction), recommandation);
            arrayResult.add(fr);
        } else {
            RecError sr = new RecError(input, indexAction);
            arrayResult.add(sr);
        }
    }
    /**
     * The function searches for the action type and returns a list of results
     * search
     * @param actionId action id
     * @param arrayResultJSONArray
     */
    public ArrayList<String> search(final int actionId,
                                    final JSONArray arrayResult) {
        ArrayList<String> arrayList = new ArrayList<>();
        String username = input.getCommands().get(actionId).getUsername();
        String genre = input.getCommands().get(actionId).getGenre();
        if (username == null && genre == null) {
            return null;
        }
        switch (genre) {
            case Constants.ACTIONADVENTURE:
                arrayList = genreActionAdventure(username, genre);
                return arrayList;
            case Constants.ANIMATION:
                break;
            case Constants.SCIFI:
                searchGenre(genre, actionId, arrayResult);
                return null;
            default:
                arrayList.add(Constants.NIMIC);
                return arrayList;
        }
        return null;
    }

    /**
     * I'm looking for the kind of movie for recommended
     * @param actionId id actiune
     * @param arrayResult pentru a pune obiecte in formal JSONArray
     */
    void searchGenre(final String genre, final int actionId,
                     final JSONArray arrayResult) {
        //  i need user history list
        ArrayList<String> listaDorita = new ArrayList<>();
        String username = input.getCommands().get(actionId).getUsername();
        if (username == null) {
            return;
        }

        //  I'm looking for the user, for his history
        int nrUseri = input.getUsers().size();
        Map<String, Integer> history = new HashMap<>();
        for (int i = 0; i < nrUseri; i++) {
            String user = input.getUsers().get(i).getUsername();
            if (user == null) {
                continue;
            }
            if (user.compareTo(username) == 0) {
                history = input.getUsers().get(i).getHistory();
            }
        }
        ArrayList<String> list = new ArrayList<>(history.keySet());

        //  I have to look through the list of movies for the desired genre
        int nrFilme = input.getMovies().size();
        for (int i = 0; i < nrFilme; i++) {
            ArrayList<String> genreMovies =
                    input.getMovies().get(i).getGenres();
            if (genreMovies == null) {
                continue;
            }
            //  if I find the kind I want
            if (genreMovies.contains(genre)) {
                //  I'll compare the movie here
                String movie = input.getMovies().get(i).getTitle();
                if (movie == null) {
                    continue;
                }
                // I have to compare this movie with the one in
                // user's history list
                if (!list.contains(movie)) {
                    listaDorita.add(movie);
                }
            }
        }

        if (listaDorita.isEmpty()) {
            RecSearchError sr = new RecSearchError(input, actionId);
            arrayResult.add(sr);
        } else {
            RecSearch sr = new RecSearch(input, actionId,
                    listaDorita);
            arrayResult.add(sr);
        }
    }
    /**
     *
     * @param userName user name
     * @param genre movie genre
     * @return
     */
    public ArrayList<String> genreActionAdventure(final String userName,
                                                  final String genre) {
        ArrayList<String> listaFilmeIntoarse = new ArrayList<>();
        int nrSerials = input.getSerials().size();
        for (int i = 0; i < nrSerials; i++) {
            List<String> listGenres = input.getSerials().get(i).getGenres();
            //  for each genre, I see if it's like mine
            if (checkFindGendre(listGenres, genre)) {
                //  if there is such a thing
                ArrayList<String> listaFilme =
                        getListSerialsWatched(userName);
                String nameSeries = input.getSerials().get(i).getTitle();
                if (listaFilme != null && nameSeries != null
                        && checkIfSerialExist(nameSeries, listaFilme)) {
                    //  if my series is not in the list of watched movies
                    listaFilmeIntoarse.add(nameSeries);
                }
            }
        }
        return listaFilmeIntoarse;
    }
    /**
     * in this list I look to see if the genre exists
     * @param list lista received
     * @param genre serch this genre in the list
     * @return return true or false if I find it
     */
    public boolean checkFindGendre(final List<String> list,
                                   final String genre) {
        for (String s : list) {
            if (genre.compareTo(s) == 0) {
                return true;
            }
        }
        return false;
    }
    /**
     * I'm looking to see if a series on a list exists
     * @param title nume film
     * @param serials lista seriale
     * @return intorc true sau false daca un serial exista
     */
    public boolean checkIfSerialExist(final String title,
                                      final List<String> serials) {
        //  if the series exists in the list of viewed series
        for (String serial : serials) {
            if (title.compareTo(serial) == 0) {
                return false;
            }
        }
        return true;
    }
    /**
     *
     * @param user nume utilizator
     * @return
     * the function returns the list of serial commands
     * I only need the list for a specific user
     */
    public ArrayList<String> getListSerialsWatched(final String user) {
        //  I walk through the whole list of actions
        ArrayList<String> serialeDinComanda = new ArrayList<>();

        for (int i = 0; i < getNrActions(); i++) {
            //  acesta este user-ul din comanda
            String userCommand = input.getCommands().get(i).getUsername();
            //  acesta este titlul din comanda
            String titleCommand = input.getCommands().get(i).getTitle();
            if (titleCommand != null && userCommand != null && user.compareTo(userCommand) == 0) {
                serialeDinComanda.add(titleCommand);
            }
        }
        return serialeDinComanda;
    }
}
