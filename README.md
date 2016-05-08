### README ###

### What is this repository for? ###

This is Mad Pirates game repository. 
For now (8th of May 2016) the game is in its very infancy, assets are 100% open game art and I foresee it will take some time to build what I want (android/PC sandbox 2D pirate game).

Version: I can't really call it a game right now.

### How do I get set up? ###

- [Set up your environment](https://github.com/libgdx/libgdx/wiki/Setting-up-your-Development-Environment-(Eclipse,-Intellij-IDEA,-NetBeans))
- Clone the project to whatever place you want 
- Import the "core" project via gradle and build the game for any platform you want by importing "desktop" or "android" etc.

I'm using libgdx-contribs library for shaders (I explain benefits in [one of my blog posts](https://dbeef.wordpress.com/2016/05/01/easy-shaders-via-libgdx-contribs-postprocessing/)), so:

- Make steps from [this](https://github.com/manuelbua/libgdx-contribs) site
(Cloning and importing the projects in Eclipse), in summary: clone all these libgdx-contribs projects wherever you want and import into your workspace by gradle 

these additional projects may have a problem - then put a "gdx-1.3.0.jar" into their classpaths
and put these projects into the "core" project's classpath

- It should be working now