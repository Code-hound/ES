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
//FALTA ESPECIFICAR AS VERSOES DO JAVA AO MAVEN
//CASO CONTRÁRIO PENSA QUE É A 1.3

-------------------------------------------------------------------------------

## Serviço SD-STORE 

### Instruções de instalação 

[1] Iniciar sistema operativo

Escolher o sistema operativo baseado em linux

Abrir terminal

[2] criar ambiente temporário

> export SD_STORE=`mktemp -d --tmpdir`

> cd $SD_STORE

[3] Iniciar servidores de apoio

JUDDI:

> wget http://disciplinas.tecnico.ulisboa.pt/leic-sod/2014-2015/download/juddi-3.2.1_tomcat-7.0.57_port-8081.zip

> unzip ./juddi-3.2.1_tomcat-7.0.57_port-8081

> export CATALINA_HOME=$SD_STORE/juddi-3.2.1_tomcat-7.0.57_port-8081

> cd ./juddi-3.2.1_tomcat-7.0.57_port-8081/bin

> chmod +x ./startup.sh ./catalina.sh

> ./startup.sh

> cd $SD_STORE

[4] Obter versão entregue

> git clone -b R_3 https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/ project

[5] Instalar o módulo de Juddi do projecto

> cd ./project/sd-store/store-ws-wsdl

> mvn clean package install

[6] Construir e executar **servidor**

> O servidor Juddi deve ter sido inicializado há cerca de 1 minuto

> cd ../store-ws-wsdl

> mvn clean package

> mvn exec:java


[7] Construir **cliente**

> Abrir outra janela de terminal

> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-cli

> mvn clean package install


-------------------------------------------------------------------------------

### Instruções de teste: ###
[1]
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/uddi

> mvn clean package install

[1] Executar testes de implementação **servidor**
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-wsdl

> mvn -Dtest=ImplementationTests test

[2] Executar testes mocked **cliente**
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-cli

> mvn -Dtest=StoreClientMockTest test

[2] Executar testes de comunicação **cliente**
> cd ~/Documents/ES-SD_Project/A_29_59_63-project/sd-store/store-ws-wsdl

> mvn exec:java

> Abrir outra janela de terminal

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
