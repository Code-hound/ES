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
> cd [pasta onde o servidor JUDDI se encontra]

> chmod +x startup.sh

> ./startup.sh


[2] Criar pasta temporária
> cd ~/Documents

> mkdir ES-SD_Project

> cd ~/Documents


[3] Obter versão entregue
> git clone https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/

//falta indicar a tag


[4] Construir e executar **servidor**
O servidor Juddi deve ter sido inicializado há cerca de 1 minuto
> cd A_29_59_63-project/sd-store/store-ws-wsdl

> mvn clean package 

> mvn exec:java


[5] Construir **cliente**
Abrir outra janela de terminal
> cd ~/Documents/A_29_59_63-project/sd-store/store-ws-cli

> mvn clean package install


-------------------------------------------------------------------------------

### Instruções de teste: ###

[1] Executar testes de implementação **servidor**
> cd ~/Documents/A_29_59_63-project/sd-store/store-ws-wsdl

> mvn -Dtest=ImplementationTest test

[2] Executar testes mocked **cliente**
> cd ~/Documents/A_29_59_63-project/sd-store/store-ws-cli

> mvn -Dtest=StoreClientMockTest test

[2] Executar testes de comunicação **cliente**
> cd ~/Documents/A_29_59_63-project/sd-store/store-ws-wsdl

> mvn exec:java

> (Abrir outra janela de terminal)

> cd ~/Documents/A_29_59_63-project/sd-store/store-ws-cli

> mvn -Dtest=StoreClientRealTest test

Não existe publicado um serviço de remoção de ficheiros, logo o 
sucesso pode ser confirmado por verificar que foi criado, em
~/Documents/A_29_59_63-project/sd-store/store-ws-wsdl/stored-files
O repositório 'username' que contém o ficheiro 'docId' com
conteúdo

-------------------------------------------------------------------------------
**FIM**
