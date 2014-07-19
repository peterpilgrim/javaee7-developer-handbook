README
=======

Standalone CDI - Java SE project
--------------------------------

For Windows 7 and 8 users, there seems to be funky issue occurring whilst running
the unit test via Gradle.

```
$ gradle test
```

Sometimes, you will see an error in the unit test where the `AbstractCDIContainerTest`
fails to instantiate the container. I upgraded the `build.gradle` to the latest
JBoss Weld SE version (2.2.3) and SpikeDelta (1.0.0) and the issue vanishes for a bit.
If you get this error, try cleaning the build.

```
$ gradle clean
```

*PP* July 2014

