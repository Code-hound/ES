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

-------------------------------------------------------------------------------

# Projecto de Sistemas Distribuí­dos #

## Segunda entrega ##

-------------------------------------------------------------------------------

## Serviço SD-ID 

Grupo de SD 59

Francisco Maria Calisto
    N. 70916
    francisco.calisto@tecnico.ulisboa.pt
    
Aline Caliente
    N. 73151
    alinecaliente@hotmail.de
    
João Pedro Clemente Zeferino
    N. 76497
    jp.zef@hotmail.com


Repositório:
[tecnico-softeng-distsys-2015/A_29_59_63-project](https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project)

-------------------------------------------------------------------------------

## Serviço SD-ID 

### Instruções de instalação 

[1] Iniciar sistema operativo

> Escolher o sistema operativo baseado em linux

> Abrir terminal

[2] criar ambiente

Temporário:

```
export SD_ID=`mktemp -d --tmpdir`
```

**ou** Permanente:

```
mkdir $HOME/PROJECTO_ES_SD && \
export SD_ID=$HOME/PROJECTO_ES_SD
```

[3] Iniciar servidores de apoio

JUDDI:

```
cd $SD_ID && \
wget http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/download/juddi-3.2.1_tomcat-7.0.57_port-8081.zip && \
unzip juddi-3.2.1_tomcat-7.0.57_port-8081 && \
export CATALINA_HOME=$SD_ID/juddi-3.2.1_tomcat-7.0.57_port-8081 && \
cd juddi-3.2.1_tomcat-7.0.57_port-8081/bin && \
chmod +x startup.sh catalina.sh && \
./startup.sh
```

[4] Obter versão entregue

```
cd $SD_ID && \
git clone -b R_4 https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/ project
```

[5] Instalar o módulo de Juddi do projecto

```
cd project/sd-id/uddi && \
mvn clean package install
```

[6] Construir e executar **servidor**

```
sleep 1m && \
cd ../id-ws-wsdl && \
mvn clean package exec:java
```

[7] Construir **cliente**

> Fazer um fork no terminal (aka abrir outra janela de terminal, usando `Ctrl+Shift+N`)

```
cd ../id-ws-cli && \
mvn clean package install
```

-------------------------------------------------------------------------------

### Instruções de teste: ###

[1] Executar testes de implementação **servidor**
> cd ~/Documents/A_29_59_63-project/sd-id/id-ws-wsdl

> mvn -Dtest=ImplementationTest test

[2] Executar testes mocked **cliente**
> cd ~/Documents/A_29_59_63-project/sd-id/id-ws-cli

> mvn -Dtest=MockTests test


-------------------------------------------------------------------------------

### Estrutura do projecto: ###

    .
    |__ .classpath
    |__ id-ws-cli
    |   |__ .classpath
    |   |__ pom.xml
    |   \__ src
    |       |__ main
    |       |   |__ java
    |       |   |   \__ id
    |       |   |       |__ cli
    |       |   |       |   |__ IdClientException.java
    |       |   |       |   \__ IdClient.java
    |       |   |       \__ exception
    |       |   |           |__ AuthReqFailed_Exception.java
    |       |   |           |__ EmailAlreadyExists_Exception.java
    |       |   |           |__ InvalidEmail_Exception.java
    |       |   |           |__ InvalidUser_Exception.java
    |       |   |           |__ UserAlreadyExists_Exception.java
    |       |   |           \__ UserDoesNotExist_Exception.java
    |       |   \__ resources
    |       |       \__ SD-ID.1_1.wsdl
    |       \__ test
    |           \__ java
    |               \__ id
    |                   \__ cli
    |                       \__ MockTests.java
    |__ id-ws_wsdl
    |   |__ .classpath
    |   |__ pom.xml
    |   \__ src
    |       |__ main
    |       |   |__ java
    |       |   |   \__ id
    |       |   |       \__ ws
    |       |   |           |__ IdImpl.java
    |       |   |           \__ IdMain.java
    |       |   \__ resources
    |       |       |__ SD-ID.1_1.wsdl
    |       |       \__ uddi.xml
    |       \__ test
    |           \__ java
    |               \__ id
    |                   \__ ws
    |                       \__ ImplementationTests.java
    |__ README.md
    |__ readme.txt
    \__ uddi
        |__ .classpath
        |__ pom.xml
        \__ src
            \__ main
                \__ java
                    \__ uddi
                        \__ UDDINaming.java


-------------------------------------------------------------------------------

## Serviço SD-STORE 

Grupo de SD 63

Luís Ribeiro Gomes
    N. 72904
    luis.ribeiro.gomes@tecnico.ulisboa.pt
    
Francisco de Matos Silveira
    N. 82390
    francisco.silveira@tecnico.ulisboa.pt


Repositório:
[tecnico-softeng-distsys-2015/A_29_59_63-project](https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/)

-------------------------------------------------------------------------------

### Instruções de instalação 

[1] Iniciar sistema operativo

> Escolher o sistema operativo baseado em linux

> Abrir terminal

[2] criar ambiente

Temporário:

```
export SD_STORE=`mktemp -d --tmpdir`
```

**ou** Permanente:

```
mkdir $HOME/PROJECTO_ES_SD && \
export SD_STORE=$HOME/PROJECTO_ES_SD
```

[3] Iniciar servidores de apoio

JUDDI:

```
cd $SD_STORE && \
wget http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/download/juddi-3.2.1_tomcat-7.0.57_port-8081.zip && \
unzip juddi-3.2.1_tomcat-7.0.57_port-8081 && \
export CATALINA_HOME=$SD_STORE/juddi-3.2.1_tomcat-7.0.57_port-8081 && \
cd juddi-3.2.1_tomcat-7.0.57_port-8081/bin && \
chmod +x startup.sh catalina.sh && \
./startup.sh
```

[4] Obter versão entregue

```
cd $SD_STORE && \
git clone -b R_4 https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/ project
```

[5] Instalar módulos do projecto

UDDI:

```
cd project/uddi && \
mvn clean package install
```

ws-handlers:

```
cd ../ws-handlers && \
mvn clean package install
```

[6] Construir e executar **servidor**

```
sleep 1m && \
cd ../store-ws-wsdl && \
mvn clean package exec:java
```

[7] Construir **cliente**

> Fazer um fork no terminal (aka abrir outra janela de terminal, usando `Ctrl+Shift+N`)

```
cd ../store-ws-cli && \
mvn clean package install
```

-------------------------------------------------------------------------------

### Instruções de teste: ###
[1]

```
cd ../uddi && \
mvn clean package install
```

[1] Executar testes de implementação **servidor**

```
cd ../store-ws-wsdl && \
mvn -Dtest=ImplementationTests test
```

[2] Executar testes mocked **cliente**

```
cd ../store-ws-cli && \
mvn -Dtest=StoreClientMockTest test
```

[2] Executar testes de comunicação **cliente**

```
cd ../store-ws-wsdl && \
mvn exec:java
```

> Fazer um fork no terminal (aka abrir outra janela de terminal, usando `Ctrl+Shift+N`)

```
cd ../store-ws-cli && \
mvn -Dtest=StoreClientRealTest test
```

-------------------------------------------------------------------------------

### Estrutura do projecto: ###

    .
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



-------------------------------------------------------------------------------
**FIM**

