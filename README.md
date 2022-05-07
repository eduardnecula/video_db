TOPIC 1 POO

NECULA EDUARD-IONUT 332 CA @ 2021

Theme implementation details:

The program starts in .src / main / Main.java.
Creates the Main Object in the Actions class function. This is where the runActions function is called.

The runActions function is the start of the theme. Here the type of command is checked, ex: command, query, recommendation.

The program iterates through each command it receives and calls in turn the 3 main major classes: "CommandMain", "QueryMain", "RatingMain" in which the main code is found.
CommandMain is in .src / main / Command
QueryMain is in .src / main / Querries
RatingMain is in .src / main / Recommendation
Description of the main classes:

1. "CommandMain" class: deals with commands such as: "command favorite", "command view", "command rating".

2. "QueryMain" class: deals with commands such as: "query movies", "query shows", "query actors", "query users".

3. "RatingMain" class: deals with movie / series rating.

A different class will be called for each order.

The classes are put in suggestive packages.
For example, everything related to the command is in the package
./src/main/Command.
It can be seen that in this package, there are 3 other packages. Each for
each type of command, command type.

For example, in the ./src/main/Command/Favorite package, classes will be implemented
required to display in the JSON file the favorite command commands

In the ./src/main/Command/Rating package, the classes will be implemented
    required to display command rating commands in the JSON file

    In the ./src/main/Command/View package, the classes will be implemented
    required to display command view commands in the JSON file.

    In the ./src/main/Querries package. There are all the necessary classes they have
    connection to query commands. (query actors, query movies ..)

    In the package ./src/main/Recommandation. There are all the necessary classes they have
    connection with recommendation type commands.


How the code works:

1. An Actions object will be made in the main. There are actions in the class
function
runActious that iterates through all the commands we receive on a particular
file.

2. This function forwards 1 object per row, which belongs
one of the 3 main types of actions Command, Query, Recommendation.
This object is called with the action index and a JSONArray, in which
will store the necessary items.

3. This newly created object verifies the exact order type,
for example command view, command rating .. and so on.

4. Using a switch again, a specific function will be called for each one
particular case, eg a command view function.

5. After I arrived from the swith, in the switch and I identified exactly the command
exact, it will be checked if this order is also valid.

6. At the end of it all, in most cases 2 types of
objects, one for order success, in which case it is displayed in format
JSON a specific success message, or a specific error message for
that command.

7. The final object makes @Override the toString () function, to respect
type JSON file, and this object will be added to JSONArray, to be
displayed in the result folder.

A class has been implemented to sort Querry commands
QueCompareList, which implements Comparable, which receives a String and a
Integer. This String and Integer is usually taken from a map, and
implementing toCompare (), can sort by this Integer, and in
tie case using that String.

This theme is a supplement to the theme I sent in 2020.

