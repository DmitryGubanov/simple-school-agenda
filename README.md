simple-school-agenda
====================

An app I made for myself during the winter break after taking a Java course in 2nd year of Computer Science. I got bored of using an Excel spreadsheet for keeping track of my marks, so on my bus rides home I applied the things I learned in that class to this app until it did what I wanted it to. V1.0 was intended to have more features and a fancier interface, but I was satisfied with using v0.7 so I stopped there.

Current Version: 0.70

Changelog:

v0.00:
there's nothing yet... just started the repo

v0.10:
Implemented Course, User, Assessment, and Calculator classes according to v1.00 specification goals. 
TODO: FileManagers and UI.

v0.20:
Implemented all of back end according to v1.00 specification goals, with some minor changes. 
TODO: UI and possible fixes to backend.

v0.30:
App is now launchable and user can add courses with a name, course code, AND weight. 
TODO: individual course display activity.

v0.40:
User can now add assessments to each course and have their marks automatically calculated. Also, the user is able to view all of their marks as well as their CGPA (according to UofT GPA system).
TODO: Improve UI for UserGradesDisplay activity and change method of data storage to database instead of text file.

v0.45:
Improved the interface of the activity which displays all the user's marks. Fixed a bug where the half-/full-year checkboxes remained checked after clicking cancel.
TODO: change method of data storage to database, making changing existing information easier to implement.

v0.50:
Improved the UI in a few areas that I thought needed touching up. Made the UI for modifying assessments, but haven't made any of it work yet.
TODO: make it work.

v0.55:
Made the modify assessment react to clicks, but not actually do anything in the back-end yet.
TODO: make the back-end work.

v0.60:
Modifying assessments now works properly.
TODO: do the same for modifying marks and course info

v0.70:
Courses and Assessments now use IDs to identify themselves instead of their names. Allows for duplicate names. Modifying assessment info, course info, and course mark now works. Fixed all bugs I found with the interface. Added option to calculate mark based on assessments or to set mark as anything the user wants.
TODO: decide what feature to implement next, since the editing milestone is done. Possibly improve UI a bit and make code better (activities have some things that could be handled by back end).
