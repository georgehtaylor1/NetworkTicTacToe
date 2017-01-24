Tic-Tac-Toe Assignment: George Taylor (gxt568)
==============================================

Compilation and Execution
-------------------------

To compile the program use:
	
	$ javac *java
	
in the directory containing the java files.
To first start the server use:

	$ java Server <port number>
e.g.  java Server 4444

Then to start the client:

	$ java Client <nick name> <port number> <machine address>
e.g.  java Client gxt568 4444 ug34-12202

Once the clients have started they will run until the lobby is closed. the server will run until
it is interrupted, e.g. with ctrl+c.


Implementation
--------------

On the client side my solution has been fully implemented in a GUI however console outputs have been
included for development and testing purposes.

The server is only implemented with console output as it would be totally transparent to any users
and so a GUi would seem unnecessary. However upon further consideration, I have realised that anyone
in charge of managing the server would benefit from a GUI with some basic commands such as kicking
users or having the ability to change user details.

As far as my testing has been able to determine, the system works well and any errors are caught and
handled appropriately.

Whilst implementing the system I decided that it would be a reasonably trivial modification to allow
users to play multiple games at any one time. Since implementing this I have realised that the number
of different windows and message boxes can often be quite confusing and so if the implementation were
to be pushed further I would first redesign the GUI.

When it came to handling multiple users with the same nickname, I initially considered assigning each
user an IDwhen they connected, however these IDs would need displaying in order to allow the user to
identify themselves. Therefore it was a simpler and more user friendly solution of appending (1), (2),
etc. to any repeated nicknames.