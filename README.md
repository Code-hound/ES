# Bubble Docs

This is the main README file for the Bubble Docs project.

The initial version of the repository contains the following structure:

    .
    |__ .gitignore
    |__ .travis.yml
    |__ README.md
    |__ pom.xml
    |__ bubbledocs-appserver
    |   |__ .gitignore
    |   |__ README.md
    |   |__ pom.xml
    |   \__ src
    |       \__ main
    |           |__ dml
    |           |   \__ bubbledocs.dml
    |           \__ resources
    |               |__ fenix-framework-jvstm-ojb.properties
    |               \__ log4j.properties
    |__ sd-id
    |   \__ README.md
    \__ sd-store
        |__ README.md
        |__ store-ws-cli
        |   |__ .classpath
        |   |__ pom.xml
        |   \__ src
        |       \__ main
        |           |__ java
        |           |   \__ store
        |           |       \__ cli
        |           |           |__ StoreClientException.java
        |           |           \__ StoreClient.java
        |           \__ resources
        |               \__ SD-STORE.1_1.wsdl
        |__ store-ws_wsdl
        |   |__ .classpath
        |   |__ .gitignore
        |   |__ pom.xml
        |   \__ src
        |       |__ main
        |       |   |__ java
        |       |   |   \__ store
        |       |   |       \__ ws
        |       |   |           |__ StoreImpl.java
        |       |   |           \__ StoreMain.java
        |       |   \__ resources
        |       |       |__ SD-STORE.1_1.wsdl
        |       |       \__ uddi.xml
        |       \__ test
        |           \__ java
        |               \__ store
        |                   \__ ws
        |                       \__ ImplementationTests.java
        \__ uddi
            |__ .classpath
            |__ .gitignore
            |__ pom.xml
            \__ src
                \__ main
                    \__ java
                        \__ store
                            \__ uddi
                                \__ UDDINaming.java


