# asw-840-kafka/a-simple-messaging

Questo progetto � una semplice applicazione con un produttore di messaggi ed un consumatore di messaggi.

## Componenti eseguibili 

Questa applicazione � composta da due componenti eseguibili: 

* **simple-producer** � un componente produttore di messaggi 

* **simple-consumer** � un componente consumatore di messaggi 

## Ambiente di esecuzione 

Queste applicazioni vanno eseguite nell'ambiente [workstation](../../environments/workstation/), sul nodo **workstation**. 
Vanno per� utilizzate pi� finestre (terminali) diverse, una per ciascun componente eseguibile dell'applicazione. 

## Preparazione 

Preliminarmente all'esecuzione dell'applicazione � necessario avviare **Kafka**, come segue: 

* sul nodo **workstation**, posizionarsi nella cartella principale del progetto `~/projects/asw-840-kafka/` 

* eseguire il comando `source set-docker-host-ip.sh` 

* eseguire lo script `start-kafka.sh` 

Inoltre, dopo aver eseguito l'applicazione � necessario arrestare **Kafka**, come segue: 

* sul nodo **workstation**, posizionarsi nella cartella principale del progetto `~/projects/asw-840-kafka/` 

* eseguire lo script `stop-kafka.sh` 

## Esecuzione 

Per eseguire l'applicazione (dopo la *Preparazione*), vanno utilizzate due (o pi�) finestre (terminali) diverse: una per il **consumatore** ed una per il **produttore**. 

Si proceda in questo modo: 

1. sulla finestra (terminale) nodo **consumatore** 

   a. posizionarsi nella cartella principale dell'applicazione `~/projects/asw-840-kafka/a-simple-messaging`

   b. eseguire il comando `gradle simple-consumer:bootRun` 
   
   c. il consumatore pu� essere arrestato con CTRL-C 

2. sulla finestra **produttore** 

   a. posizionarsi nella cartella principale dell'applicazione `~/projects/asw-840-kafka/a-simple-messaging`

   b. eseguire il comando `gradle simple-producer:bootRun` 

Inoltre: 

* se si vuole usare un altro gruppo per il consumatore, avviare il consumatore con il comando `ASW_KAFKA_GROUPID=pluto gradle simple-consumer:bootRun`

* se si vuole far ricevere i messaggi al consumatore da un altro canale, avviare il consumatore con il comando `ASW_KAFKA_CHANNEL_IN=beta gradle simple-consumer:bootRun`

* se si vuole far inviare i messaggi al produttore su un altro canale, avviare il produttore con il comando `ASW_KAFKA_CHANNEL_OUT=beta gradle simple-producer:bootRun`
