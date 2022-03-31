# Spotlight: My Personal Project
## A fill-in-the-blank game

Play if you dare! Spotlight is a game that encourages
bullying... **healthy** bullying between friends.

 ---
Game instructions:

Each round, one player is assigned to be the judge, and
one player will be in the spotlight. Everyone but the judge
will be given a prompt **regarding** the player in the spotlight
to answer as they please. Once they answer the prompt,
the judge will award a point to the person whose answer
they like the best.

For example:
* Player A, B, C, J, and S are playing a game!
* This round, J is the Judge and S is in the Spotlight.
* A, B, C, and S are given the prompt: "S's most embarassing
  memory is..."
* Once A, B, and C, and S have all given their response, J
  will award a point to the player whose answer they like
  the best.

Players are also free to make prompts that they'd like
to add to the game! These prompts are randomly selected
at the beginning of every round.

 ---

This application is for those who love party games like
*Cards Against Humanity*, *Quiplash* from *The Jackbox
Party Packs*, *Apples to Apples*, etc.

I chose to create this program because I really enjoy playing
the above games, but I felt like they would benefit
from a more personal feel. Because Spotlight focuses
on one person at a time while moving through the group,
there will be plenty of shots fired between friends and
funny moments to be had.


## User Stories
* As a user, I want to be able to add a prompt to the current library of prompts before the game starts.
* As a user, I want to add myself to the list of players before the game starts.
* As a user, I also want to be able to **remove** a player from the list of players before the game starts.
* As a user, I want the non-judge players to be able to input a response to the prompt every round.
* As a user, I want to be able to give a point to the player who wins the round (the judge decides).

* As a user, I want to be able to save the prompts in the library
  and the players who are playing, as well as the round number and any points collected so far
* As a user, I want to be able to load a previously saved game in the state that it was saved in

## References
- Spotlight jpeg is from https://www.istockphoto.com/photo/blue-spotlight-on-stage-performance-gm1187168424-335242802
- Some of my beginning UI code in previous iterations was based off of the
  TellerApp project from the CPSC 210 professors:
  https://github.students.cs.ubc.ca/CPSC210/TellerApp
- The persistence package is largely based off of the
  JsonSerializationDemo project from the CPSC 210 professors:
  https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
- Event and EventLog are from the AlarmSystem project from the
  CPSC 210 professors: https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

## Phase 4 Task 2: Log example
Tue Mar 29 23:57:03 PDT 2022
Prompt #1 was added!

Tue Mar 29 23:57:18 PDT 2022
Player doggy was added!

Tue Mar 29 23:57:22 PDT 2022
Player doggy was removed.

Tue Mar 29 23:57:25 PDT 2022
Player froggy was added!

Tue Mar 29 23:57:28 PDT 2022
Player doggy was added!

Tue Mar 29 23:57:31 PDT 2022
Player froggy was removed.

Process finished with exit code 0

## Phase 4 Task 3: Refactoring Wishes
### A list of things that I would have done in this project, given more time
* I would have abstracted a lot of the methods that iterates
through the PlayerList/PromptLibrary - there's a ton of duplication
that could have been avoided there
  * Maybe could have done this by making an abstract class
* I would have tried to figure out how to save the entire game
state as one .json file, rather than the 3 separate files I have
right now
* I would have tried to make it so that used Prompts wouldn't be
seen for the rest of the game and ending the game when all prompts
had been used up. 
  * I could probably do this by working with a separate list
  of unused prompts when a game starts, and removing the prompt
  from there when it gets used.
* I would have abstracted some label/button-making code, since it
was repeated a lot to create the GUI
* I could have split up the Spotlight class into different classes
to represent the different states of the game: Main menu, game menu,
and gameplay (although I'm not sure if this would 100% be a better design)