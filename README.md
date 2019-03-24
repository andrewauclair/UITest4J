[![Master Status](https://travis-ci.org/andrewauclair/UITest4J.svg?branch=master)](https://travis-ci.org/andrewauclair/UITest4J)
[![JavaFX Status](https://travis-ci.org/andrewauclair/UITest4J.svg?branch=javafx)](https://travis-ci.org/andrewauclair/UITest4J)

This project provides a simple and intuitive API for functional testing of Swing user interfaces, resulting in tests 
that are compact, easy to write, and read like a specification. Tests written using UITest4J are also robust.
UITest4J simulates actual user gestures at the operating system level, ensuring that the application will behave correctly in
front of the user. It also provides a reliable mechanism for GUI component lookup that ensures that changes in the GUI's 
layout or look-and-feel will not break your tests.

This project is a fork of https://github.com/joel-costigliola/assertj-swing, a project that I used extensively and wanted
to bring use together with the latest AssertJ Core, which I was unable to do. For this reason I decided to create UITest4J
which would be a more up-to-date version of AssertJ Swing and eventually provide the same style testing for JavaFX GUI
applications.

<!--
[Maven Central TestNG]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-testng
[Maven Central TestNG img]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-testng/badge.svg

[Maven Central JUnit]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-junit
[Maven Central JUnit img]:https://maven-badges.herokuapp.com/maven-central/org.assertj/assertj-swing-junit/badge.svg
-->
