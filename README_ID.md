# Projecto de Sistemas Distribuí­dos #

## Primeira entrega ##

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
**FIM**