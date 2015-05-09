# Bubble Docs Application Server

## Terceira entrega ##

Grupo de ES 29

Francisco Maria Calisto
    N. 70916
    francisco.calisto@tecnico.ulisboa.pt
    
Luís Ribeiro Gomes
    N. 72904
    luis.ribeiro.gomes@tecnico.ulisboa.pt
    
Aline Caliente
    N. 73151
    alinecaliente@hotmail.de
    
João Pedro Clemente Zeferino
    N. 76497
    jp.zef@hotmail.com
    
Francisco de Matos Silveira
    N. 82390
    francisco.silveira@tecnico.ulisboa.pt


Repositório:
[tecnico-softeng-distsys-2015/A_29_59_63-project](https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project)


-------------------------------------------------------------------------------

## Serviço BubbleDocs-Appserver

### Instruções de instalação 

[0] Iniciar sistema operativo

> (Escolher o sistema operativo baseado em linux)

[1] Obter versão entregue

> git clone -b R_4 https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/

-------------------------------------------------------------------------------

### Instruções de teste: ###

[1] Executar testes
> cd ~/Documents/A_29_59_63-project/bubbledocs-appserver

> mvn test


-------------------------------------------------------------------------------

### Estrutura do projecto: ###

    .
    |__ .classpath
    |__ .gitignore
    |__ pom.xml
    |__ README.md
    \__ src
        \__ main
            |__ dml
            |   \__ bubbledocs.dml
            |__ java
            |   \__ pt
            |       \__ tecnico
            |           \__ bubbledocs
            |               |__ BubbleApplication.java
            |               |__ domain
            |               |   |__ Access.java
            |               |   |__ ADD.java
            |               |   |__ AVG.java
            |               |   |__ BinaryFunction.java
            |               |   |__ BubbleDocs.java
            |               |   |__ Cell.java
            |               |   |__ Content.java
            |               |   |__ DIV.java
            |               |   |__ Exporter.java
            |               |   |__ FunctionArguments.java
            |               |   |__ Function.java
            |               |   |__ Getter.java
            |               |   |__ .gitignore
            |               |   |__ Importer.java
            |               |   |__ Literal.java
            |               |   |__ MUL.java
            |               |   |__ Permission.java
            |               |   |__ PRD.java
            |               |   |__ Printer.java
            |               |   |__ RangedFunction.java
            |               |   |__ Reference.java
            |               |   |__ Session.java
            |               |   |__ SpreadSheet.java
            |               |   |__ SUB.java
            |               |   \__ User.java
            |               |__ exception
            |               |   |__ BubbleDocsException.java
            |               |   |__ CannotLoadDocumentException.java
            |               |   |__ CannotStoreDocumentException.java
            |               |   |__ CellNotInSpreadSheetException.java
            |               |   |__ DocumentDoesNotExistsException.java
            |               |   |__ DuplicateEmailException.java
            |               |   |__ DuplicateUsernameException.java
            |               |   |__ EmptyEmailException.java
            |               |   |__ ExportDocumentException.java
            |               |   |__ ImportDocumentException.java
            |               |   |__ InvalidAccessException.java
            |               |   |__ InvalidEmailException.java
            |               |   |__ InvalidUserException.java
            |               |   |__ InvalidUsernameException.java
            |               |   |__ InvalidValueException.java
            |               |   |__ LoginBubbleDocsException.java
            |               |   |__ ProtectedCellException.java
            |               |   |__ RemoteInvocationException.java
            |               |   |__ UnauthorizedOperationException.java
            |               |   |__ UnavailableServiceException.java
            |               |   |__ UnknownBubbleDocsException.java
            |               |   |__ UserAlreadyExistsException.java
            |               |   |__ UserAlreadyHasThisDocumentException.java
            |               |   |__ UserAlreadyInSessionException.java
            |               |   |__ UserCantWriteException.java
            |               |   |__ UserNotInSessionException.java
            |               |   |__ WrongPasswordException.java
            |               |   \__ XMLExceptionException.java
            |               \__ service
            |                   |__ AssignLiteralToCell.java
            |                   |__ AssignReferenceToCell.java
            |                   |__ BubbleDocsService.java
            |                   |__ CreateSpreadSheet.java
            |                   |__ CreateUser.java
            |                   |__ dto
            |                   |   \__ UserDto.java
            |                   |__ ExportDocument.java
            |                   |__ LoginUser.java
            |                   |   |__ IDRemoteServices.java
            |                   |   \__ StoreRemoteServices.java
            |                   |__ RemoveUser.java
            |                   \__ RenewPassword.java
            \__ resources
                |__ fenix-framework-jvstm-ojb.properties
                \__ log4j.properties


-------------------------------------------------------------------------------
**FIM**
