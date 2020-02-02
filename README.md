rtc4idea
========

Intellij IDEA plugin for support <a href="http://www-03.ibm.com/software/products/en/rtc/">IBM Team Concert</a>


Allow you:

- Integrating RTC WorkItems with Intellij IDEA Task plugin

How to build
============

- git clone https://github.com/renatn/rtc4idea.git
- Go to https://jazz.net/downloads/rational-team-concert/
    - Select your RTC server version
    - Download *Plain Java Client Libraries*
- Go to `https://www.jetbrains.com/intellij-repository/releases/com/jetbrains/intellij/idea/ideaIC/$VERSION/ideaIC-$VERSION.zip` where `$VERSION` is the IDEA Community you want to build your plugin  against. 
    - unzip in to *lib* directory the content
- Open project in Intellij IDEA
- open your `pom.xml` file and configure your locations:
    - for the  *Plain Java Client Libraries* put the path into the `<rtc.client.directory>`  property
    - in `<INTELLIJ_HOME>` place a folder that you can use as a sandbox for deploying development versions for your plugin changes.
- Build plugin by running a maven install
    - Opening the terminal and executing `mvn install`
    - or.. using the maven view double-clicking install
    
    ![install option](imgs/mvn%20install.PNG)

How to Use
====

* Open your project
* Go to settings and place your credentials
![introduce credentials](imgs/enter%20credentials.PNG)

To Do
=====

* RTC Vcs support 

License
=======

Copyright 2013, Renat Nasyrov

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.