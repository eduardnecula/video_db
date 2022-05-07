package main.Querries;

import actor.ActorsAwards;
import common.Constants;
import fileio.Input;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.json.simple.JSONArray;

public class QueryMain {
    private Input input;
    /**
     *
     * @param input set input
     */
    public QueryMain(final Input input) {
        this.input = input;
    }

    /**
     * The main function that determines what kind of querry we work with
     * @param indexAction action id
     * @param arrayResult JSON array in which I put the objects
     */
    public void query(final int indexAction, final JSONArray arrayResult) {
        String objectType =
                input.getCommands().get(indexAction).getObjectType();
        if (objectType != null) {
            switch (objectType) {
                case Constants.MOVIES:
                    queryMovies(indexAction, arrayResult);
                    break;
                case Constants.ACTORS:
                    queryActors(indexAction, arrayResult);
                    break;
                case Constants.SHOWS:
                    queryShows(indexAction, arrayResult);
                    break;
                case Constants.USERS:
                    queryUsers(indexAction, arrayResult);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * The function that handles the query movies command
     * @param indexAction action id
     * @param arrayResult JSON array in which the objects are put
     */
    public void queryMovies(final int indexAction,
                            final JSONArray arrayResult) {

        ArrayList<String> seriesToRecommend =
                returnQueryMoviesList(indexAction);
        String criteria = input.getCommands().get(indexAction).getCriteria();
        String sortType = input.getCommands().get(indexAction).getSortType();
        ArrayList<String> seriesToRecommendAfterCriteria =
                criteriaFavoriteQuery(seriesToRecommend, criteria);

        if (seriesToRecommend == null || seriesToRecommendAfterCriteria == null) {
            return;
        }
        if (sortType.compareTo(Constants.ASCENDENT) == 0) {
            Collections.sort(seriesToRecommendAfterCriteria);
        }
        if (sortType.compareTo(Constants.DESCENDENT) == 0) {
            seriesToRecommendAfterCriteria.sort(Collections.reverseOrder());
        }
        Query query = new Query(input, indexAction,
                seriesToRecommendAfterCriteria);
        arrayResult.add(query);
    }
    /**
     *
     * @return number of users
     */
    public int getNrUsers() {
        return  input.getUsers().size();
    }

    /**
     * The function deals with command query actors
     * Depending on the type of query actors command, a switch is used,
     * in which a corresponding function will be called. The ultimate goal will be
     * for each function to create an object to put in JSON
     * array
     * @param indexAction action id
     * @param arrayResult JSON array in which the objects are put
     */
    public void queryActors(final int indexAction,
                            final JSONArray arrayResult) {

        // for each actor
        String criteria = input.getCommands().get(indexAction).getCriteria();
        String sortType = input.getCommands().get(indexAction).getSortType();
        if (criteria == null) {
            return;
        }
        switch (criteria) {
            case Constants.AVERAGE:
                criteriaActorsAverage(indexAction, arrayResult);
                break;
            case Constants.FILTERDESCRIPTION:
                criteriaFilterDescription(indexAction, arrayResult, sortType);
                break;
            case Constants.AWARDS:
                criteriaAwards(indexAction, arrayResult, sortType);
            default:
                break;
        }
    }

    /**
     * The quert shows command function. This calls for other functions
     * to apply filtering functions to show items
     * @param indexAction action id
     * @param arrayResult JSON array in which the objects are put
     */
    public void queryShows(final int indexAction, final JSONArray arrayResult) {
        ArrayList<String> seriesToRecommend =
                returnQueryShowsList(indexAction);
        String criteria = input.getCommands().get(indexAction).getCriteria();
        ArrayList<String> seriesToRecommendAfterCriteria =
                criteriaFavoriteQuery(seriesToRecommend, criteria);

        if (seriesToRecommend == null || seriesToRecommendAfterCriteria == null) {
            return;
        }

        Query query = new Query(input, indexAction,
                seriesToRecommendAfterCriteria);
        arrayResult.add(query);
    }

    /**
     *
     * @param indexAction action id
     * @param arrayResult JSON array in which the objects are put
     */
    public void queryUsers(final int indexAction,
                           final JSONArray arrayResult) {
        String criteria = input.getCommands().get(indexAction).getCriteria();
        if (criteria == null) {
            return;
        }
        if (Constants.NUM_RATINGS.equals(criteria)) {
            criteriaUsersNumRatings(indexAction, arrayResult);
        }
    }

    /**
     * The function that sorts according to the awards that an actor has
     * @param indexAction action id
     * @param arrayResult in which the objects are put
     * @param sortType sorting type, descending, ascending
     */
    public void criteriaAwards(final int indexAction,
                                          final JSONArray arrayResult,
                                          final String sortType) {
        int id = input.getCommands().get(indexAction).getActionId();
        ArrayList<String> list = new ArrayList<>();
        ArrayList<Integer> listValues = new ArrayList<>();

        if (sortType == null) {
            return;
        }
        List<List<String>> filters =
                input.getCommands().get(indexAction).getFilters();
        if (filters == null) {
            return;
        }
        //  awards
        List<String> awards = filters.get(Constants.AWARDS_NUMBER);

        int contor = 0;
        int sumPremii = 0;
        if (awards != null) {
            // I have to check on every actor
            // if he has all these awards
            int nrActors = input.getActors().size();
            for (int i = 0; i < nrActors; i++) {
                String nameActor = input.getActors().get(i).getName();
                if (nameActor == null) {
                    continue;
                }

                // I look at his list of awards
                Map<ActorsAwards, Integer> awardsActor =
                        input.getActors().get(i).getAwards();

                if (awardsActor == null) {
                    continue;
                }

                for (Map.Entry<ActorsAwards, Integer> entry : awardsActor.entrySet()) {
                    sumPremii += entry.getValue();
                }

                for (String aux : awards) {
                    ActorsAwards actorsAwards = ActorsAwards.valueOf(aux);
                    if (awardsActor.containsKey(actorsAwards)) {
                        contor++;
                    }
                }

                if (contor == awards.size()) {
                    list.add(nameActor);
                    //  I have to add the sum of all the prizes that
                    // this actor has them
                    listValues.add(sumPremii);
                }
                contor = 0;
                sumPremii = 0;
            }
        }

        //  now in the awardsList map I put <ActorName, How ManyAwardsAre>
        // I will make so many Compare list objects for each value
        // I'll sort it out
        int nrObiecte = list.size();
        QueCompareList[] compareLists = new QueCompareList[nrObiecte];
        Map<String, QueCompareList> newMap = new HashMap<>();
        for (int i = 0; i < nrObiecte; i++) {
            compareLists[i] = new QueCompareList(list.get(i), listValues.get(i));
            newMap.put(compareLists[i].getName(), compareLists[i]);
        }

        List<QueCompareList> listFinal = new ArrayList<>(newMap.values());
        if (sortType.compareTo(Constants.ASCENDENT) == 0) {
            Collections.sort(listFinal);
        } else {
            listFinal.sort(Collections.reverseOrder());
        }

        List<String> listDeTrimis = new ArrayList<>();
        for (QueCompareList queCompareList : listFinal) {
            listDeTrimis.add(queCompareList.getName());
        }

        QueFilter qf = new QueFilter(id, listDeTrimis);
        arrayResult.add(qf);

    }
    /**
     * The filtering function according to the description of an order
     * query
     * @param indexAction action id
     * @param arrayResult JSON array
     */
    public void criteriaFilterDescription(final int indexAction,
                                       final JSONArray arrayResult,
                                       final String sortType) {
        int contor = 0;
        ArrayList<String> numeActori = new ArrayList<>();
        int id = input.getCommands().get(indexAction).getActionId();
        List<List<String>> filters =
                input.getCommands().get(indexAction).getFilters();

        // I go through all the actors
        int nrActori = input.getActors().size();
        List<String> words = filters.get(2);
        for (int i = 0; i < nrActori; i++) {
            String nameActor = input.getActors().get(i).getName();
            if (nameActor == null) {
                continue;
            }
            String descriereActor =
                    input.getActors().get(i).getCareerDescription();
            if (descriereActor == null) {
                continue;
            }
            //  I'm looking for the 2 words in this description
            if (words == null) {
                continue;
            }
            for (String word : words) {
                //  I'm going through every word now
                if (word == null) {
                    continue;
                }

                if (descriereActor.contains(word)) {
                    contor++;
                }
            }
            if (contor == words.size()) {
                numeActori.add(nameActor);
            }
            contor = 0;
        }

        if (sortType.compareTo(Constants.ASCENDENT) == 0) {
            Collections.sort(numeActori);
        } else {
            numeActori.sort(Collections.reverseOrder());
        }

        QueFilter qr = new QueFilter(id, numeActori);
        arrayResult.add(qr);
    }

    /**
     * The sorting function of the actors according to the average of their awards
     * @param indexAction action id
     * @param arrayResult JSON array
     */
    public void criteriaActorsAverage(final int indexAction,
                                       final JSONArray arrayResult) {
        int number = input.getCommands().get(indexAction).getNumber();
        int nrActors = input.getActors().size();
        //  I go through every actor
        Map<String, Double> averageList = new HashMap<>();
        for (int i = 0; i < nrActors; i++) {
            String actor = input.getActors().get(i).getName();
            //  I receive the average for each actor
            double medieActor = averageActor(actor);
            //  I do a map between name and average
            averageList.put(actor, medieActor);
        }

        TreeMap<String, Double> sorted = new TreeMap<>(averageList);
        QueActors qr = new QueActors(input, indexAction, sorted);
        arrayResult.add(qr);
    }

    /**
     * The function calculates the average awards for an actor
     * @param name actor name
     * @return returns the average of an actor's awards
     */
    public double averageActor(final String name) {
        double medie = 0;
        int nrFilme = 0;
        //  I get through all the tasks
        int nrTask = input.getCommands().size();
        for (int i = 0; i < nrTask; i++) {
            String actionType = input.getCommands().get(i).getActionType();
            String rating = input.getCommands().get(i).getType();
            double grade = input.getCommands().get(i).getGrade();
            if (actionType != null && rating != null) {
                if (actionType.compareTo(Constants.COMMAND) == 0) {
                    if (rating.compareTo(Constants.RATING) == 0) {
                        //  now that I'm on the right order
                        // I'll look for the movie
                        String title = input.getCommands().get(i).getTitle();
                        //  is with the received actor
                        int nrActors = input.getActors().size();
                        for (int j = 0; j < nrActors; j++) {
                            String actor = input.getActors().get(j).getName();
                            if (actor != null) {
                                if (actor.compareTo(name) == 0) {
                                    //  if I find the right actor
                                    // I'll see if I have the right title
                                    int nrFilm =
                                            input.getActors().get(j).getFilmography().size();
                                    for (int k = 0; k < nrFilm; k++) {
                                        String filmography =
                                                input.getActors().get(j).getFilmography().get(k);
                                        if (filmography != null) {
                                            if (title != null) {
                                                if (filmography.compareTo(title) == 0) {
                                                    //  if I find I will count it
                                                    nrFilme++;
                                                    medie += grade;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (nrFilme != 0) {
            return medie / nrFilme;
        }
        return 0;
    }

    /**
     * The function for which the query users command is implemented, depending
     * the number of ratings a user has given
     * @param indexAction action id
     * @param arrayResult JSON ARRAY
     */
    public void criteriaUsersNumRatings(final int indexAction,
                                        final JSONArray arrayResult) {
        int id = input.getCommands().get(indexAction).getActionId();
        int number = input.getCommands().get(indexAction).getNumber();
        String sortType = input.getCommands().get(indexAction).getSortType();

        //  I will get through all the commands
        int nrComenzi = input.getCommands().size();
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < nrComenzi; i++) {
            String actiontype =
                    input.getCommands().get(i).getActionType();
            if (actiontype == null) {
                continue;
            }
            String type = input.getCommands().get(i).getType();
            if (type == null) {
                continue;
            }
            String user = input.getCommands().get(i).getUsername();
            if (user == null) {
                continue;
            }
            if (actiontype.compareTo(Constants.COMMAND) == 0 && type.compareTo(
                    Constants.RATING) == 0) {
                boolean eroare = true;
                //  if I gave a rating, I will create a map between user and no
                // ratings
                String movie = input.getCommands().get(i).getTitle();
                if (movie == null) {
                    continue;
                }
                //  I go through all the users to get my history from it
                int sizeUser = input.getUsers().size();
                Map<String, Integer> history = null;
                for (int j = 0; j < sizeUser; j++) {
                   String userName = input.getUsers().get(j).getUsername();
                   if (userName == null) {
                       continue;
                   }
                   if (userName.compareTo(user) == 0) {
                       history = input.getUsers().get(j).getHistory();
                   }
                }
                if (history == null) {
                    continue;
                }

                eroare = history.containsKey(movie);

                if (eroare) {
                    if (map.containsKey(user)) {
                        int aux = map.get(user);
                        aux++;
                        map.put(user, aux);
                    } else {
                        map.put(user, 1);
                    }
                }
            }
        }
        // in the 2 lists I will put key and value from the map above
        ArrayList<String> listKey = new ArrayList<>();
        ArrayList<Integer> listValue = new ArrayList<>();
        Map<String, QueCompareList> newMap = new HashMap<>();
        QueCompareList[] compareList = new QueCompareList[map.size()];
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();
            listKey.add(key);
            listValue.add(value);
        }

        // I make as many objects as I have in the map to sort them
        for (int i = 0; i < listKey.size(); i++) {
            compareList[i] = new QueCompareList(listKey.get(i), listValue.get(i));
            newMap.put(compareList[i].getName(), compareList[i]);
        }

        // I sort the list of objects I have, with the help of compareTo
        List<QueCompareList> list = new ArrayList<>(newMap.values());
        Collections.sort(list);

        if (sortType.compareTo(Constants.ASCENDENT) == 0) {
            Collections.sort(list);
        } else {
            list.sort(Collections.reverseOrder());
        }

        ArrayList<String> newList = new ArrayList<>();
        for (int i = 0;  i < number; i++) {
            if (i < list.size()) {
                newList.add(list.get(i).getName());
            }
        }

        QueFilter qn = new QueFilter(id, newList);
        arrayResult.add(qn);
    }

    /**
     * The function applies the required command filters, and returns a list of
     * movies that respect this
     * @param indexAction action id
     * @return a list of movies that meet the required criteria
     */
    public ArrayList<String> returnQueryMoviesList(final int indexAction) {
        ArrayList<String> seriesToRecommend = new ArrayList<>();
        int nrShows = input.getMovies().size();
        List<List<String>> list = input.getCommands().get(indexAction).getFilters();
        int year;
        if (list != null) {
            List<String> yearCommand = list.get(0);
            List<String> genreCommand = list.get(1);
            String yearCommandValue = yearCommand.get(0);
            try {
                year = Integer.parseInt(yearCommandValue);
            } catch (NumberFormatException ignored) {
                return null;
            }
            for (int i = 0; i < nrShows; i++) {
                int yearShow = input.getMovies().get(i).getYear();
                ArrayList<String> genreShow = input.getMovies().get(i).getGenres();
                String nameShow = input.getMovies().get(i).getTitle();
                for (String s : genreShow) {
                    if (genreCommand != null && nameShow != null
                            && checkGenreExists(genreCommand, s)
                            && yearShow == year) {
                        seriesToRecommend.add(nameShow);
                    }
                }
            }
        }
        return seriesToRecommend;
    }

    /**
     * Returns a list of series that comply with the conditions in the filters in
     * each order
     * @param indexAction action id
     * @return list series
     */
    public ArrayList<String> returnQueryShowsList(final int indexAction) {
        ArrayList<String> seriesToRecommend = new ArrayList<>();
        int nrShows = input.getSerials().size();
        List<List<String>> list = input.getCommands().get(indexAction).getFilters();
        int year;
        if (list != null) {
            List<String> yearCommand = list.get(0);
            List<String> genreCommand = list.get(1);
            String yearCommandValue = yearCommand.get(0);
            try {
                year = Integer.parseInt(yearCommandValue);
            } catch (NumberFormatException ignored) {
                return null;
            }
            for (int i = 0; i < nrShows; i++) {
                int yearShow = input.getSerials().get(i).getYear();
                ArrayList<String> genreShow = input.getSerials().get(i).getGenres();
                String nameShow = input.getSerials().get(i).getTitle();
                for (String s : genreShow) {
                    if (genreCommand != null && nameShow != null
                            && checkGenreExists(genreCommand, s)
                            && yearShow == year) {
                        seriesToRecommend.add(nameShow);
                    }
                }
            }
        }
        return seriesToRecommend;
    }

    /**
     * The function looks at the type of criterion, and according to it
     * will make a specific object
     * @param seriesToRecommend serialele recomandata
     * @param criteria tipul sortarii, crescatoare / descrescatoare
     * @return
     */
    public ArrayList<String> criteriaFavoriteQuery(final ArrayList<String> seriesToRecommend,
                                                   final String criteria) {
        ArrayList<String> finalList = new ArrayList<>();
        if (criteria != null) {
            if (criteria.compareTo(Constants.FAVORITE) == 0) {
                ArrayList<String> favList = getFavoriteList();
                finalList = compareList(favList, seriesToRecommend);
                return finalList;
            }
            if (criteria.compareTo(Constants.LONGEST) == 0) {
                ArrayList<String> favList = getListMovies();
                finalList = compareList(favList, seriesToRecommend);
                return finalList;
            }
            if (criteria.equals(Constants.MOSTVIEWD)) {
                ArrayList<String> favList = getFavoriteList();
                finalList = compareList(favList, seriesToRecommend);
                return finalList;
            }
            if (criteria.equals(Constants.AVERAGE)) {
                ArrayList<String> favList = getAverageActors();
                finalList = compareList(favList, seriesToRecommend);
                return finalList;
            }
        }
        return finalList;
    }

    /**
     * @return returns the list of actors to be used to calculate
     * the average of their prizes
     */
    public ArrayList<String> getAverageActors() {
        ArrayList<String> listActors = new ArrayList<>();
        int nrActors = input.getActors().size();
        for (int i = 0; i < nrActors; i++) {
            String actor = input.getActors().get(i).getName();
            listActors.add(actor);
        }
        return listActors;
    }

    /**
     *
     * @return returns the list of movies that input has
     */
    public ArrayList<String> getListMovies() {
        ArrayList<String> listMovies = new ArrayList<>();
        int nrMovies = input.getMovies().size();
        for (int i = 0; i < nrMovies; i++) {
            String movie = input.getMovies().get(i).getTitle();
            listMovies.add(movie);
        }
        return listMovies;
    }

    /**
     * I check if a certain kind of movie exists
     * @param genre movie type
     * @param genreFromUser kind of user movie
     * @return
     */
    public boolean checkGenreExists(final List<String> genre,
                                    final String genreFromUser) {
        // if I find genreFromUser in the Genre List I return true
        if (genre == null || genreFromUser == null) {
            return false;
        }
        for (String genreS : genre) {
            try {
                if (genreS.compareTo(genreFromUser) == 0) {
                    return true;
                }
            } catch (NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    /**
     *
     * @return returns the user favorites list
     */
    public ArrayList<String> getFavoriteList() {
        ArrayList<String> listFavoriteMovies = new ArrayList<>();
        for (int i = 0; i < getNrUsers(); i++) {
            ArrayList<String> favoriteMovies =
                    input.getUsers().get(i).getFavoriteMovies();
            for (int j = 0; j < favoriteMovies.size(); j++) {
                String favorite =
                        input.getUsers().get(i).getFavoriteMovies().get(j);
                listFavoriteMovies.add(favorite);
            }
        }
        return listFavoriteMovies;
    }

    /**
     * The function compares my favorite list with the querry list and returns
     * a final list depending on whether something is repeated or not
     * @param favoriteList lista de filme favorite
     * @param queryList lista de querry
     * @return intorc o lista
     */
    public ArrayList<String> compareList(final ArrayList<String> favoriteList,
                                         final ArrayList<String> queryList) {
        ArrayList<String> finalList = new ArrayList<>();
        boolean seRepeta = false;
        if (favoriteList == null || queryList == null) {
            return null;
        }
        for (String queryMovie : queryList) {
            for (String favList : favoriteList) {
                if (queryMovie.equals(favList)) {
                    seRepeta = true;
                    break;
                }
            }
            if (seRepeta) {
                finalList.add(queryMovie);
                seRepeta = false;
            }
        }
        return finalList;
    }
}
