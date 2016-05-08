### README ###

### What is this repository for? ###

This is Mad Pirates game repository. 
For now (8th of May 2016) the game is in its very infancy, assets are 100% open game art and I foresee it will take some time to build what I want (android/PC sandbox 2D pirate game).

Version: I can't really call it a game right now.

### How do I get set up? ###

- [Set up your environment](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-(Eclipse,-Intellij-IDEA,-NetBeans))
- Clone the project to whatever place you want 
- Import the "core" project via gradle and build the game for any platform you want by importing "desktop" or "android" etc.
- Now make steps from [this](https://github.com/manuelbua/libgdx-contribs) site
(Cloning and importing the projects in Eclipse), in summary: clone all these libgdx-contribs projects into the blank folder "libgdx-contribs" in the previously cloned game project and import by gradle 
(well you can clone it anywhere but It would be much cleaner)
these additional projects may have a problem - then put a "gdx-1.3.0.jar" into their classpaths
and put these projects into the "core" project's classpath
- It should be working now
