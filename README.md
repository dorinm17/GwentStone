# Manea Dorin-Mihai
# 323CA
# Homework - GwentStone

## DESCRIPTION
I have implemented GwentStone, a hybrid card game that implements the best
features of both Gwent and Hearthstone. The game is played on a 4x5 board,
where each player can place their own minions, that fight each other. 
Additionally, some minions possess special abilities. The player's can also use
environment cards, with effects that affect the whole selected row. The game
ends when one of the player's hero is killed.

## IMPLEMENTATION
The game is implemented in Java and encompasses fundamental OOP concepts such
as encapsulation and abstraction. The class Gameplay can be considered the
mother class. It is a singleton instance, and it runs all the games, objects
of the Match class. Furthermore, I have created the auxiliary classes Card and
Player for storing additional necessary information.
The Match class contains a switch class, that checks in the "execute()" method
which action to execute. Each action is implemented in an abstract class, since
there is no need for an instance of the class. Consequently, the methods in
them are static.

## BIBLIOGRAPHY
1. https://attacomsian.com/blog/jackson-create-json-array
2. https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare/tutorial-json-jackson
3. OOP Labs: https://ocw.cs.pub.ro/courses/poo-ca-cd/laboratoare
