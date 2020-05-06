# avaBackend
Testing framework for Ava interviewing process

To start the test, use `mvn clean test` in the command line to run tests. It will download all the dependencies from `pom.xml` file.
**Note:** chromedriver used is the one for Mac and Chrome version  81.0.4044.138. It might need one line command to be executable like: `xattr -d com.apple.quarantine <name-of-executable>`

There are two Story files and in `TestStories` class, you can configure which one to run by editing included Story name in `storyPaths()` method.
