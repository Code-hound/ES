# Bubble Docs - Third Release

This is the main README file for the Bubble Docs project.

The third release of the repository contains the following structure:

    .
    |__ .gitignore
    |__ .travis.yml
    |__ README.md
    |__ pom.xml
    |__ bubbledocs-appserver
    |   |__ .classpath
    |   |__ .gitignore
    |   |__ pom.xml
    |   |__ README.md
    |   \__ src
    |       \__ main
    |           |__ dml
    |           |   \__ bubbledocs.dml
    |           |__ java
    |           |   \__ pt
    |           |       \__ tecnico
    |           |           \__ bubbledocs
    |           |               |__ BubbleApplication.java
    |           |               |__ domain
    |           |               |   |__ Access.java
    |           |               |   |__ ADD.java
    |           |               |   |__ AVG.java
    |           |               |   |__ BinaryFunction.java
    |           |               |   |__ BubbleDocs.java
    |           |               |   |__ Cell.java
    |           |               |   |__ Content.java
    |           |               |   |__ DIV.java
    |           |               |   |__ Exporter.java
    |           |               |   |__ FunctionArguments.java
    |           |               |   |__ Function.java
    |           |               |   |__ Getter.java
    |           |               |   |__ .gitignore
    |           |               |   |__ Importer.java
    |           |               |   |__ Literal.java
    |           |               |   |__ MUL.java
    |           |               |   |__ Permission.java
    |           |               |   |__ PRD.java
    |           |               |   |__ Printer.java
    |           |               |   |__ RangedFunction.java
    |           |               |   |__ Reference.java
    |           |               |   |__ Session.java
    |           |               |   |__ SpreadSheet.java
    |           |               |   |__ SUB.java
    |           |               |   \__ User.java
    |           |               |__ exception
    |           |               |   |__ BubbleDocsException.java
    |           |               |   |__ CannotLoadDocumentException.java
    |           |               |   |__ CannotStoreDocumentException.java
    |           |               |   |__ CellNotInSpreadSheetException.java
    |           |               |   |__ DocumentDoesNotExistsException.java
    |           |               |   |__ DuplicateEmailException.java
    |           |               |   |__ DuplicateUsernameException.java
    |           |               |   |__ EmptyEmailException.java
    |           |               |   |__ ExportDocumentException.java
    |           |               |   |__ ImportDocumentException.java
    |           |               |   |__ InvalidAccessException.java
    |           |               |   |__ InvalidEmailException.java
    |           |               |   |__ InvalidUserException.java
    |           |               |   |__ InvalidUsernameException.java
    |           |               |   |__ InvalidValueException.java
    |           |               |   |__ LoginBubbleDocsException.java
    |           |               |   |__ ProtectedCellException.java
    |           |               |   |__ RemoteInvocationException.java
    |           |               |   |__ UnauthorizedOperationException.java
    |           |               |   |__ UnavailableServiceException.java
    |           |               |   |__ UnknownBubbleDocsException.java
    |           |               |   |__ UserAlreadyExistsException.java
    |           |               |   |__ UserAlreadyHasThisDocumentException.java
    |           |               |   |__ UserAlreadyInSessionException.java
    |           |               |   |__ UserCantWriteException.java
    |           |               |   |__ UserNotInSessionException.java
    |           |               |   |__ WrongPasswordException.java
    |           |               |   \__ XMLExceptionException.java
    |           |               \__ service
    |           |                   |__ AssignLiteralToCell.java
    |           |                   |__ AssignReferenceToCell.java
    |           |                   |__ BubbleDocsService.java
    |           |                   |__ CreateSpreadSheet.java
    |           |                   |__ CreateUser.java
    |           |                   |__ dto
    |           |                   |   \__ UserDto.java
    |           |                   |__ ExportDocument.java
    |           |                   |__ LoginUser.java
    |           |                   |   |__ IDRemoteServices.java
    |           |                   |   \__ StoreRemoteServices.java
    |           |                   |__ RemoveUser.java
    |           |                   \__ RenewPassword.java
    |           \__ resources
    |               |__ fenix-framework-jvstm-ojb.properties
    |               \__ log4j.properties
    |__ sd-id
    |   |__ .classpath
    |   |__ id-ws-cli
    |   |   |__ .classpath
    |   |   |__ pom.xml
    |   |   \__ src
    |   |       |__ main
    |   |       |   |__ java
    |   |       |   |   \__ id
    |   |       |   |       |__ cli
    |   |       |   |       |   |__ IdClientException.java
    |   |       |   |       |   \__ IdClient.java
    |   |       |   |       \__ exception
    |   |       |   |           |__ AuthReqFailed_Exception.java
    |   |       |   |           |__ EmailAlreadyExists_Exception.java
    |   |       |   |           |__ InvalidEmail_Exception.java
    |   |       |   |           |__ InvalidUser_Exception.java
    |   |       |   |           |__ UserAlreadyExists_Exception.java
    |   |       |   |           \__ UserDoesNotExist_Exception.java
    |   |       |   \__ resources
    |   |       |       \__ SD-ID.1_1.wsdl
    |   |       \__ test
    |   |           \__ java
    |   |               \__ id
    |   |                   \__ cli
    |   |                       \__ MockTests.java
    |   |__ id-ws_wsdl
    |   |   |__ .classpath
    |   |   |__ pom.xml
    |   |   \__ src
    |   |       |__ main
    |   |       |   |__ java
    |   |       |   |   \__ id
    |   |       |   |       \__ ws
    |   |       |   |           |__ IdImpl.java
    |   |       |   |           \__ IdMain.java
    |   |       |   \__ resources
    |   |       |       |__ SD-ID.1_1.wsdl
    |   |       |       \__ uddi.xml
    |   |       \__ test
    |   |           \__ java
    |   |               \__ id
    |   |                   \__ ws
    |   |                       \__ ImplementationTests.java
    |   |__ README.md
    |   |__ readme.txt
    |   \__ uddi
    |       |__ .classpath
    |       |__ pom.xml
    |       \__ src
    |           \__ main
    |               \__ java
    |                   \__ uddi
    |                       \__ UDDINaming.java
    \__ sd-store
        |__ README.md
        |__ store-ws-cli
        |   |__ .classpath
        |   |__ pom.xml
        |   \__ src
        |       |__ main
        |       |   |__ java
        |       |   |   \__ pt
        |       |   |       \__ ulisboa
        |       |   |           \__ tecnico
        |       |   |               \__ sdis
        |       |   |                   \__ store
        |       |   |                       \__ cli
        |       |   |                           |__ StoreClientException.java
        |       |   |                           \__ StoreClient.java
        |       |   \__ resources
        |       |       \__ SD-STORE.1_1.wsdl
        |       \__ test
        |           \__ java
        |               \__ pt
        |                   \__ ulisboa
        |                       \__ tecnico
        |                           \__ sdis
        |                               \__ store
        |                                   \__ cli
        |                                       \__ test
        |                                           |__ StoreClientMockTest.java
        |                                           \__ StoreClientRealTest.java
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


