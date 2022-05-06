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
     * @param input seteaza input-ul
     */
    public QueryMain(final Input input) {
        this.input = input;
    }

    /**
     * Functia principala care stabileste cu ce fel de querry lucram
     * @param indexAction id actiune
     * @param arrayResult JSON array in care se vor pune obiectele
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
     * Functia care se ocupa de comanda query movies
     * @param indexAction id actiune
     * @param arrayResult JSON array in care se vor pune obiectele
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
     * @return intoarce nr de useri
     */
    public int getNrUsers() {
        return  input.getUsers().size();
    }

    /**
     * Functia se ocupa de comanda query actors
     * In functie de tipul comenzii query actors, se foloseste un switch,
     * in care se va apela o functie corespunzatoare. Scopul final va fi
     * ca fiecare functie sa creeze un obiect pe care sa il puna in JSON
     * array
     * @param indexAction id actiune
     * @param arrayResult JSON array in care se vor pune obiectele
     */
    public void queryActors(final int indexAction,
                            final JSONArray arrayResult) {

        // pentru fiecare actor
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
     * Functia ce se ocupa de comanda quert shows. Aceasta apeleaza alte functii
     * pentru a aplica functii de filtrare pentru elemente de tip show
     * @param indexAction id actiune
     * @param arrayResult JSON array in care se vor pune obiectele
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
     * @param indexAction id actiune
     * @param arrayResult JSON array in care se vor pune obiectele
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
     * Functia ce sorteaza in functie de premiile pe care le are un actor
     * @param indexAction id actiune
     * @param arrayResult JSON arrat in care voi pune obiectele
     * @param sortType tipu sortarii, crescatoare, descrescatoare
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
            // trebuie sa verific la fiecare actor
            // daca are toate aceste award-uri
            int nrActors = input.getActors().size();
            for (int i = 0; i < nrActors; i++) {
                String nameActor = input.getActors().get(i).getName();
                if (nameActor == null) {
                    continue;
                }

                // ma uit in lista lui de premii
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
                    //  trebuie sa adaug suma tuturo premiilor pe care
                    //  acest actor le are
                    listValues.add(sumPremii);
                }
                contor = 0;
                sumPremii = 0;
            }
        }

        //  acum in map-ul awardsList am puse <NumeActor, CatePremiiAre>
        //  voi face atatea obiecte Compare list pentru fiecare valoare
        //  si o voi sorta
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
     * Functia ce filtreaza in functie de descrierea pe care o are o comanda
     * query
     * @param indexAction id actiune
     * @param arrayResult JSON array in care se vor pune obiectele
     */
    public void criteriaFilterDescription(final int indexAction,
                                       final JSONArray arrayResult,
                                       final String sortType) {
        int contor = 0;
        ArrayList<String> numeActori = new ArrayList<>();
        int id = input.getCommands().get(indexAction).getActionId();
        List<List<String>> filters =
                input.getCommands().get(indexAction).getFilters();

        // trec prin toti actorii
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
            //  caut in aceasta descriere cele 2 cuvinte
            if (words == null) {
                continue;
            }
            for (String word : words) {
                //  trec prin fiecare cuvant acum
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
     * Functia de sortare a actorilor in functie de media premiilor lor
     * @param indexAction id actiune
     * @param arrayResult JSON array in care voi pune obiectele
     */
    public void criteriaActorsAverage(final int indexAction,
                                       final JSONArray arrayResult) {
        int number = input.getCommands().get(indexAction).getNumber();
        int nrActors = input.getActors().size();
        //  trec prin fiecare actor
        Map<String, Double> averageList = new HashMap<>();
        for (int i = 0; i < nrActors; i++) {
            String actor = input.getActors().get(i).getName();
            //  primesc media pt un actor
            double medieActor = averageActor(actor);
            //  fac un map intre nume si medie
            averageList.put(actor, medieActor);
        }

        TreeMap<String, Double> sorted = new TreeMap<>(averageList);
        QueActors qr = new QueActors(input, indexAction, sorted);
        arrayResult.add(qr);
    }

    /**
     * Functia calculeaza media premiilor pentru un actor
     * @param name nume actor
     * @return intoarce media premiilor unui actor
     */
    public double averageActor(final String name) {
        double medie = 0;
        int nrFilme = 0;
        //  trec prin toate task-urile
        int nrTask = input.getCommands().size();
        for (int i = 0; i < nrTask; i++) {
            String actionType = input.getCommands().get(i).getActionType();
            String rating = input.getCommands().get(i).getType();
            double grade = input.getCommands().get(i).getGrade();
            if (actionType != null && rating != null) {
                if (actionType.compareTo(Constants.COMMAND) == 0) {
                    if (rating.compareTo(Constants.RATING) == 0) {
                        //  acum ca sunt pe comanda potrivita
                        //  voi cauta daca filmul
                        String title = input.getCommands().get(i).getTitle();
                        //  se afla la actorul primit
                        int nrActors = input.getActors().size();
                        for (int j = 0; j < nrActors; j++) {
                            String actor = input.getActors().get(j).getName();
                            if (actor != null) {
                                if (actor.compareTo(name) == 0) {
                                    //  daca gasesc actorul potrivit
                                    //  ma voi uita daca am si titlul potrivit
                                    int nrFilm =
                                            input.getActors().get(j).getFilmography().size();
                                    for (int k = 0; k < nrFilm; k++) {
                                        String filmography =
                                                input.getActors().get(j).getFilmography().get(k);
                                        if (filmography != null) {
                                            if (title != null) {
                                                if (filmography.compareTo(title) == 0) {
                                                    //  daca gasesc il numar
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
     * Functia pentru care se implementeaza comanda query users, in functie
     * de numarul de rating-uri pe care un user le-a oferit
     * @param indexAction id actiune
     * @param arrayResult JSON ARRAY unde pun obiectele
     */
    public void criteriaUsersNumRatings(final int indexAction,
                                        final JSONArray arrayResult) {
        int id = input.getCommands().get(indexAction).getActionId();
        int number = input.getCommands().get(indexAction).getNumber();
        String sortType = input.getCommands().get(indexAction).getSortType();

        //  trebuie sa trec prin toate comenzile
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
                //  daca am dat rating, voi crea un map intre user si nr de
                //  rating-uri
                String movie = input.getCommands().get(i).getTitle();
                if (movie == null) {
                    continue;
                }
                //  trec prin toti userii ca sa imi iau history de la el
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
        // in cele 2 liste voi pune key si value din map-ul de mai sus
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

        // fac atatea obiecte cate elemente am in map, pentru a le sorta
        for (int i = 0; i < listKey.size(); i++) {
            compareList[i] = new QueCompareList(listKey.get(i), listValue.get(i));
            newMap.put(compareList[i].getName(), compareList[i]);
        }

        // sortez lista de obiecte pe care o am, cu ajutorul lui compareTo
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
     * Functia aplica filtrele cerute de comanda, si returneaza o lista cu
     * filmele ce respecta acest lucru
     * @param indexAction id actiune
     * @return o lista cu filme ce respecta criteriile cerute
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
     * Intoarce o lista cu serialele ce respecta conditile din filters din
     * fiecare comanda
     * @param indexAction id actiune
     * @return lista seriale
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
     * Functia se uita la tipul criteriei, si in functie de aceasta
     * va face un obiect specific
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
     * @return intoarce lista de actori, pentru a fi folosita pentru a calcula
     * media premiilor lor
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
     * @return intoarce lista de filme pe care input le are
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
     * Verific daca un anumit gen de filme exista
     * @param genre tipul filmului
     * @param genreFromUser genul filmul de la utilizator
     * @return
     */
    public boolean checkGenreExists(final List<String> genre,
                                    final String genreFromUser) {
        // daca gasesc genreFromUser in Lista genre returnez true
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
     * @return intoarce lista de favorite a userilor
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
     * Functia compara lista mea de favorite, cu cea din querry si intoarce
     * o lista finala in functie daca ceva se repeta sau nu
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
