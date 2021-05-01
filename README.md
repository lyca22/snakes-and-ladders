snakes-and-ladders (for Windows)
------------

Authors: Laura Daniela Martínez & Ariel Eduardo Pabón (lyca22)

This software was built using Java 8.

UI only supports English.

You can download this software from: https://github.com/lyca22/snakes-and-ladders

You can access the project's documentation from: https://drive.google.com/file/d/1wwjEYmkhBeI2_DPW1EjIM_yJmO_FTXuC/view?usp=sharing


What is this software?
------------

snakes-and-ladders is a software built to replicate the famous board game 'Snakes & Ladders', which uses a console UI to represent the board and some other elements that are part of the game.


Requirements
------------

At least the following environment is required to run this software.

 * OS: Any machine capable of using JVM.
 * Java (https://www.java.com/en/download/).


How to use?
------------

Once you boot up the program, you'll see the menu's options and you'll be able to enter commands to interact with the game:

## While in menu

-Enter '1' to start a game.
-Enter '2' to see the scoreboards.
-Enter '3' to exit the program.

## Starting the game

After choosing the option of starting a game, the program will ask you to enter the following parameters between spaces:

-Width: the width of the game board. You are required to enter an integer.
-Length: the length of the game board. You are required to enter an intenger.
-Snake amount: the amount of snakes you want to add to the board. You are required to enter an integer.
-Ladder amount: the amount of ladders you want to add to the board. You are required to enter an integer.
-Player amount or their symbols: the amount of players you want to be in the game, or the symbols you want each player to have (the amount of symbols you input will define how many players will be in the game).

For example:

The line '5 5 2 1 2' will create a game with a 5x5 board, 2 snakes, 1 ladder and 2 players with the default symbols.
The line '7 4 2 2 @OX' will create a game with a 7x4 board, 2 snakes, 2 ladders and 3 players with symbols @, O and X.

Things to keep in mind:
-Choosing the symbols yourself will allow you to have as many players as you want. Choosing a number of players will limit the amount of players to 9.
-The maximum amount of snakes you can have in a match is 26. Same with the ladders.

## Playing the game

Once you start the game, the program will show you 2 boards, for example:
<pre>
[ 1 ][ 2 ][ 3A]
[ 6A][ 5 ][ 41]
[ 71][ 8 ][ 9 ]

[*! ][   ][  A]
[  A][   ][  1]
[  1][   ][   ]
</pre>
The first one shows you the board with each field number, and it will appear just once (unless you enter a command, see the next section to see all the commands you can do).
The second board will appear everytime a player makes a move, showing their current position.
Both boards show you the snakes (represented by capital letters on the board) and ladders (represented by the numbers on the board). These will always appear in the right of each field.

Commands:

-Press enter without inputing anything to make a move.
-Enter 'menu' to go back to the menu.
-Enter 'simul' to enable simulation mode.
-Enter 'num' to show the board with field numbers.

## Moving

-The players will move between 1 and 6 fields everytime they press enter without inputing anything.
-If you entered a number for the player amount, they'll always move in this order:
<pre>
* - First
! - Second
O - Third
X - Fourth
% - Fifth
$ - Sixth
# - Seventh
+ - Eigthth
& - Nineth
</pre>
-If you entered your own symbols, the players will always move in the order you entered the symbols, for example:
<pre>
input : '!@#$'

! - First
@ - Second
# - Third
$ - Fourth
</pre>
-If you fall in a field with a snake, you'll go down through it and your new position will be the snake's end, for example:
<pre>
[ 1 ][ 2 ][ 3A]
[ 6A][ 5 ][ 41]
[ 71][ 8 ][ 9 ]

[*! ][   ][  A]
[  A][   ][  1]
[  1][   ][   ]
</pre>
Here * is gonna move, let's say he gets a 5:
<pre>
[ ! ][   ][  A]
[ *A][   ][  1]
[  1][   ][   ]
</pre>
Normally he would end in that field, but since he fell into a snake, he will go through the snake and his new position will be this one:
<pre>
[ ! ][   ][ *A]
[  A][   ][  1]
[  1][   ][   ]
</pre>

-If you fall in a field with a ladder, you'll go down through it and your new position will be the ladders's end, for example:
<pre>
[ 1 ][ 2 ][ 32]
[ 62][ 5 ][ 41]
[ 71][ 8 ][ 9 ]

[*! ][   ][  2]
[  2][   ][  1]
[  1][   ][   ]
</pre>
Here * is gonna move, let's say he gets a 2:
<pre>
[ ! ][   ][ *2]
[  2][   ][  1]
[  1][   ][   ]
</pre>
Normally he would end in that field, but since he fell into a ladder, he will go through the ladder and his new position will be this one:
<pre>
[ ! ][   ][  2]
[ *2][   ][  1]
[  1][   ][   ]
</pre>

-If you fall in the last field, you win the game.

## Simulation mode

-In any point in the match, a player can enter 'simul' to enable the simulation mode.
-The game will start making moves every 2 seconds without the need of pressing enter until the game ends.

## Seeing the scoreboard

-While in the menu, the user can enter '2' to see the scoreboard.
-The scoreboard will contain the following information of the match:

-The match's winner, his symbol and his score. This score is calculated by multiplying the number of moves the player did by the number of fields the board had.
-Board width and length.
-Snake amount in the board.
-Ladder amount in the board.
-Player amount of the match.
-The symbols of each player besides the winner.

For example:
<pre>
Match winner: Lyca(*) | Winner's score: 18 | Board width: 3 | Board length: 3 | Snakes: 1 | Ladders: 1 | Player amount: 2 | Remaining player symbols: !
Match winner: Laura(!) | Winner's score: 8 | Board width: 2 | Board length: 2 | Snakes: 1 | Ladders: 1 | Player amount: 2 | Remaining player symbols: *

Press enter to continue
</pre>

Note
------------

This software is not guaranteed safe.
Please use it at the discretion of the user.
The creator does not assume any obligation.


Acknowledgment
------------

Thanks to Juan Manuel Reyes for guiding us in the process of making this tiny program.