# Elaborato Esame di Stato 2019/2020
# Made By Lorenzo Orlando

<h4>Cosa riguarda </h4>
Quest'applicazione utilizza l'SDK di Google "Firebase Machine Learning Kit" per eseguire l'operazione di riconoscitore del viso e riconoscimento di un oggetto. L'utente farà l'accesso al servizio inserendo email e password che verranno successivamente inseriti in un database locale creato con SQLite. Appena eseguito l'accesso, l'utente può scegliere se utilizzare il servizio di riconoscimento facciale o il servizio di riconoscimento di un'immagine.
Il servizio di tracciamento facciale permette all'utente di aprire la fotocamera e scattare una foto e riconosce se la faccia trovata nell'immagine stia sorridendo e se abbia gli occhi aperti e traccia le varie parti del viso con dei piccoli puntini. 
Il servizio di riconoscimento di un'immagine, invece, permette all'utente di aprire la galleria e di selezionare una foto. Successivamente il modello tenterà di riconoscere che tipo di oggetto viene rappresentato, pure se il modello gratuito offerto da Google è stato allenato con sole 400 immagini, quindi non risulta sempre preciso.

<h4>Tecnologie impiegate </h4>
L'SDK Firebase Machine Learning Kit offre gratuitamente vai servizi per lo sviluppo di applicazione Android e IoS che puntano verso l'innovazione e allo sviluppo software i modelli da loro offerti. Il Firebase Machine Learning Kit offre diverse opzioni come il riconoscimento facciale, riconoscimento testuale, scannering di codici a barre, identificazione di una lingua, traduzioni, risposte intelligenti e molto altro. 
Per quest'applicazione, verrano utilizzati il riconoscimento facciale e il riconoscimento di un oggetto. 
