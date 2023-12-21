USAGE
1) Launch the Web App 
>> a) From sources: Run JMancalaApplication.java


2) Go to http://localhost:8080/

3) Type in a unique username
4) Click Login
5) Click Create Game

6) Open another tab at the same address
7) Do steps [3-4] again. You should see a list of created games 
8) Click on any of the game room elements that appear 
9) In the host player's tab, click Start Game (button should become enabled)
10) Click on the pits below to make your moves

<h1>Features</h1>

1) Game Rooms 
2) Potentially several Game Modes
3) Server-side logging to file

<h1>Limitations</h1>

1) Currently, navigation is not supported - neither in browser, nor within the game itself (you have to re-login to start another game for example)
2) Game Over state is not properly handled yet 

<h1>
Technology Overview </h1>
Java 17+

Springboot 3.2.0

Thymeleaf

HTMX
<h1> Potential Improvements (backend) </h1>

1) Flexible navigation to enter/leave rooms at will
2) Implement other variants of the game via Game Modes system
3) Real user system 

<h1> Potential Improvements (frontend) </h1>

1) Partial state updates to make the game feel more animated and player turns more "readable"
2) Actual UI elements - scrollable log panel
3) Game chat to communicate with opponents 
