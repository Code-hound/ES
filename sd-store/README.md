# Bubble Docs SD-STORE

This is the SD-STORE README file for the Bubble Docs project.

The third release of the repository contains the following structure:

    .
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
