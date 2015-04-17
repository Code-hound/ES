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
[tecnico-softeng-distsys-2015/A_29_59_63-project/sd-id](https://github.com/tecnico-softeng-distsys-2015/A_29_59_63-project/sd-id)


-------------------------------------------------------------------------------

## Serviço SD-ID 

### Instruções de instalação 

[0] Iniciar sistema operativo

> (Escolher o sistema operativo baseado em linux)


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

> (falta indicar a tag)


[4] Construir e executar **servidor**
O servidor Juddi deve ter sido inicializado há cerca de 1 minuto
> cd A_29_59_63-project/sd-id/id-ws-wsdl

> mvn clean package 

> mvn exec:java


[5] Construir **cliente**
Abrir outra janela de terminal
> cd ~/Documents/A_29_59_63-project/sd-id/id-ws-cli

> mvn clean package install


-------------------------------------------------------------------------------

### Instruções de teste: ###

[1] Executar testes de implementação **servidor**
> cd ~/Documents/A_29_59_63-project/sd-id/id-ws-wsdl

> mvn -Dtest=ImplementationTest test

[2] Executar testes mocked **cliente**
> cd ~/Documents/A_29_59_63-project/sd-id/id-ws-cli

> mvn -Dtest=MockTests test

-------------------------------------------------------------------------------
**FIM**
