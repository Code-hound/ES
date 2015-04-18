# Projecto de Sistemas Distribuí­dos #

## Primeira entrega ##

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

## Serviço SD-STORE 

### Instruções de instalação 

[0] Iniciar sistema operativo
Escolher o sistema operativo baseado em linux


[1] Iniciar servidores de apoio
JUDDI:
<<<<<<< HEAD
> $CATALINA_HOME/bin/startup.sh
=======
> cd $CATALINA_HOME/bin/
>>>>>>> Fixed wrong directory in README instruction

> chmod +x startup.sh

> ./startup.sh


[2] Criar pasta temporária
> cd ~/Documents

> mkdir ES-SD_Project

> cd ES-SD_Project


[3] Obter versão entregue
<<<<<<< HEAD
> git clone https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/R_3

//falta indicar a tag
=======
> git clone -b R_3 https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/
>>>>>>> Fixed wrong directory in README instruction


[4] Construir e executar **servidor**
O servidor Juddi deve ter sido inicializado há cerca de 1 minuto
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-wsdl

> mvn clean package

> mvn exec:java


[5] Construir **cliente**
Abrir outra janela de terminal
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-cli

> mvn clean package install


-------------------------------------------------------------------------------

### Instruções de teste: ###

[1] Executar testes de implementação **servidor**
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-wsdl

> mvn -Dtest=ImplementationTests test

[2] Executar testes mocked **cliente**
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-cli

> mvn -Dtest=StoreClientMockTest test

[2] Executar testes de comunicação **cliente**
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-wsdl

> mvn exec:java

> (Abrir outra janela de terminal)

> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-cli

> mvn -Dtest=StoreClientRealTest test



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
