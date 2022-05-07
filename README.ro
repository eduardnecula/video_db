TEMA 1 POO

NECULA EDUARD-IONUT 332 CA @2021
Git link: https://github.com/oop-pub/oop-323ca-b-luni-12-14-needuardIonutAcs

Detalii implementare tema: 

Programul incepe in .src/main/Main.java.
Se creeaza in functia Main Obiectul din clasa Actions. De aici se apeleaza functia runActions.

Functia runActions reprezinta startul temei. Aici se verifica tipul comenzii, ex: command, query, recommendation.

Programul itereaza prin fiecare comanda pe care o primeste si se apeleaza pe rand cele 3 clase mari principale: "CommandMain", "QueryMain", "RatingMain" in care se gaseste codul principal.
CommandMain se afla in .src/main/Command
QueryMain se afla in .src/main/Querries
RatingMain se afla in .src/main/Recommandation
Descrierea claselor principale:

1. Clasa "CommandMain": se ocupa cu comenzile de tip: "command favorite", "command view", "command rating".

2. Clasa "QueryMain": se ocupa cu comenzile de tip: "query movies", "query shows", "query actors", "query users".

3. Clasa "RatingMain": se ocupa cu rating movies/ series.

Pentru fiecare comanda se va apela o alta clasa.

	Clasele sunt puse in pachetele sugestive.
	De exemplu, tot ce are legatura cu command, se afla in pachetul
	./src/main/Command.
	Se poate observa ca in acest pachet, se afla alte 3 pachete. Fiecare pentru
	fiecare tip de comanda, de tip command.

	De ex in pachetul ./src/main/Command/Favorite, se vor implementa clasele
	necesare pentru a afisa in fisierul JSON, comenzile de tip command favorite

	In pachetul ./src/main/Command/Rating, se vor implementa clasele
    necesare pentru a afisa in fisierul JSON, comenzile de tip command rating

    In pachetul ./src/main/Command/View, se vor implementa clasele
    necesare pentru a afisa in fisierul JSON, comenzile de tip command view.

    In pachetul ./src/main/Querries. Se afla toate clasele necesare ce au
    legatura cu comenzile de tip query. (query actors, query movies ..)

    In pachetul ./src/main/Recommandation. Se afla toate clasele necesare ce au
    legatura cu comenzile de tip recommendation.


Cum merge codul: 

	1. In main se va face un obiect de tip Actions. In clasa actions exista
	functia
	runActios care itereaza prin toate comenzile pe care le primim pe un anumit
	fisier.

	2. Aceasta functie apeleaza mai departe cate 1 obiect pe rand, care apartine
	unuia din cele 3 mari tipuri de actiuni Command, Query, Recommendation.
	Acest obiect este apelat cu indexul actiunii si cu un JSONArray, in care se
	vor stoca obiectele necesare.

	3. Acest obiect nou creat verifica mai exact tipul comenzii specifice,
	de ex command view, command rating .. si tot asa.

	4. Folosind iar un switch, se va apela o functie specifica pentru fiecare
	caz particular, de ex o functie pentru command view.

	5. Dupa ce am ajuns din swith, in switch si am identificat exact comanda
	exacta, se va verifica daca acesta comanda este si valida.

	6. La final de tot, in marea majoritatea cazurilor se vor crea 2 tipuri de
	obiecte, unul pentru succesul comenzii, caz in care se afiseaza in format
	JSON un mesaj specific de succes, sau un mesaj de eroare specific pentru
	acea comanda.

	7. Obiectul final face @Override pe functia toString(), pentru a respecta
	tipul JSON file, iar acest obiect se va adauga in JSONArray, pentru a fi
	afisat in folderul result.

	Pentru sortarea comenzilor de tip Querry, s-a implementat o clasa
	QueCompareList, ce implementeaza Comparable, ce primeste un String si un
	Integer. Acest String si Integer, este luat de obicei dintr-un map, si
	implementand toCompare(), poate sorta in functie de acest Integer, iar in
	caz de egalitate, folosind acel String.

	Aceasta tema, este o completare a temei pe care am trimis-o in anul 2020.