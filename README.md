# Com S 309 - Spring 2021 - Puzzle Game App

This is a group project between Levi Jansen, Eric Kirch, Justin Le, and Cris Medina.
We are mostly - if not all - newbies.
Levi Jansen has taken SE 185 and 186x, ComS 227 and 228. He has basic experience in C, Java, and using Git. 
Eric Kirch has taken SE 185, COM S 104, 227, and 228 at Iowa State. On top of this, he took AP Computer Science and AP Computer Science Principles in HS. 
He has a rather good understanding of Java, a decent understanding of C, and has dabbled in Python and JavaScript. 
Cristofer Medina has taken CPR E 288, COM S 227 and 228. He has also taken web development courses in the pasted working with html and css. 
He has a solid grasp of coding in java and in c.

With that being said, welcome to Puzzle Game (tentative title)
Puzzle Game aims to be a game with multiple types of puzzles of varying difficulties, and the ability to share them with others. 
Instead of needing to go to 3 different apps for 3 different types of puzzles, now you only need one. 
On top of that, the puzzles won't just be defaults, they will be ones that the player had a hand in creating.
In here we plan to have a few different types of puzzles:

Jigsaw
Slider
Picross

With the slider puzzle having 3 difficulties

Easy (3x3)
Medium (5x5)
Hard (9x9)

The plan is to be able to use pictures off of your camera roll for the puzzles. If you don't have any, sample puzzles will be provided.
Puzzle Game will be able to automatically create a jigsaw or slider puzzle out of an image you upload to it. 
It will also be able to scan an image and make a more involved type of puzzle, such as picross. 
Players will be able to give their friends a "puzzle code" that can be used for them to play the puzzle too.
After they're done, they would be able to download the image.
Daily puzzles and Leaderboard are planned as well to keep players coming back for more
The plan is to obviously use Android + Springboot, however, we'll need some sort of server for the puzzle sharing feature, maybe SQL. 
Since we have next to no experience creating an application like this, we aren't entirely sure what we'll need yet.
Upon talking to the team, none of us have ever worked on Android or with Springboot before, so it will be a lot of learning on those two fronts for sure.
Making sure the app can create a puzzle and be solved, while also storing puzzles would be a large amount of work on the client side - 
especially when making the puzzles from your camera roll, and maybe from a URL.
All three types of puzzles will need creation algorithms, and a way to store the puzzle data. 
We'll need to make sure that picture data can be uploaded to a server, and housed with a code for someone else to find, making for shareable puzzles. 
On top of that, the leaderboard will need to be on some kind of server, and will hopefully be a live leaderboard. 
We'll also need to store the time it takes for a user to finish a puzzle for the leaderboard.
