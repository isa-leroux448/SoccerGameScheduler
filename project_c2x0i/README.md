# My Personal Project

## Project Description

**What will the application do?** <br>
My application will represent a *system to view a soccer league’s standings and statistics*. The user will be able to see their team’s position in the league, as well as team rosters and game schedules and information.

**Who will use it?** <br>
The application is meant for *managers, coaches and players*. The managers will be able to report games and add players to the roster. The coaches and players will be able to view standings, rosters, game information and specific statistics for each of those categories.

**Why is this project of interest to you?** <br>
This project is of interest to me because as a soccer player, I have used many systems like this. I am familiar with the type of features that I’d like to see on an application like this and am curious to see if I’m able to recreate and maybe even improve this kind of system.

## Phase 1 User Stories
-As a user, I want to be able to **add a new Player to a team**.<br>
-As a user, I want to be able to select a player and view their statistics.<br>

-As a user, I want to be able to select a team and view a list of the players on that team.<br>
-As a user, I want to be able to select a team and view the top scorers on that team.<br>
-As a user, I want to be able to select a team and see their statistics (games played, wins, ties, losses, goals for, goals against)<br>

-As a user, I want to be able to create a game with teams, location and time.<br>
-As a user, I want to be able to update a game’s information (time or location)<br>
-As a user, I want to be able to select a game and report its statistics (score, goalscorers)<br>
-As a user, I want to be able to select a game and see its information (score, goalscorer, time, location)<br>

## Phase 2 User Stories
-As a user, I want to be able to save my teams, players and games <br>
-As a user, I want to be able to load my teams, players and games. 

## Phase 4: Task 2
Sample log of adding two players and a game in LeagueManager: <br>
<br>
Mon Mar 28 23:23:27 PDT 2022 <br>
Player Isabella Leroux added to Fusion

Mon Mar 28 23:23:36 PDT 2022 <br>
Player Jenna Baxter added to Mountain

Mon Mar 28 23:23:47 PDT 2022 <br>
Game between Fusion and NSGSC at 18:00 at Windsor Secondary has been scheduled.

## Phase 4: Task 3
Possible improvements/refactorings: <br>
-Improve the bidirectional relationship between Player and Team 
by adding "guards" that would prevent duplicates from being added to a team. 
This would also allow for easier setup of other features like 
removing a player or allowing a player to switch teams. <br>
-Make the Game Tab more cohesive by creating different classes for each "page". 
At the moment the GameTab is a few hundred lines long and contains the functionality of 4 different subpages. 
Making each page an individual class would adhere to the "Single Responsibility Principle." <br>
-Refactor the fields in the Game class as a hashmap. This would reduce the number of unnecessary fields in the Game class. <br>
<!---Remove the unnecessary coupling in GameTab, by taking out its references/relations to the Game and Player classes.---> 
