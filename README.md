# TrustServicesSurfer
All' avvio dell'applicazione l'utente si troverà dinanzi a una finestra implementata tramite l'applicazione scene builder e con l'ausilio del framework di java "javaFX", l'applicazione è stata implementata in modo tale che all' avvio essa metta già a disposizione dell'utente la lista dei paesi, dei tipi di servizio, dei provider e degli stati del servizi presenti.
STRUTTURA APPLICAZIONE:

- I paesi, i tipi di servizi, i provider e gli stati dei servizi vengono racchiusi in una raccolta posta in alto a sinistra dell' applicazione, quest'ultima può essere sfogliata cliccando sull 'etichetta nominativa del corrispettivo campo voluto.
  PER OGNI CAMPO DELLA RACCOLTA:
- vengono visualizzate due liste messe una sopra l'altra, nella parte sinistra dell' interfaccia: La prima lista contiene tutte le voci esistenti per ogni campo, tali voci vengono visualizzate immediatamente all' avvio dell' applicazione.
  La seconda lista è una struttura dati dinamica, posizionata sotto a quella appena illustrata, contiene le selezioni effettuate dall' utente necessarie a interrogare il sistema.
- Il riquadro posto sulla parte destra intitolato " Server response " visualizza la risposta del sistema alla query fatta dall' utente tramite la precedente selzione dei filtri.
  -Nella parte inferiore dell' interfaccia viene visualizzato un button denominato "Search" che quando azionato avvia l'interrogazione del sistema, la quale si conclude con la stampa della risposta del server nella sezione "Server response".

#FUNZIONAMENTO APPLICAZIONE:
L'utente potrà navigare da una lista all' altra selezionando la relativa etichetta della raccolta, e potrà selezionare per ciascuna lista il corrispettivo filtro che si vuole applicare per interrogare il sistema (cliccando con il tasto sinistro del mouse sull'etichetta nominativa dell' elemento della lista voluta).
Inoltre è possibile deselezionare il filtro scelto andando a cliccare l'etichetta nomminativa di quest' ultimo nella Lista dinamica.
L' applicazione per poter ultilmare la ricerca e restituire le risposte volute NON richiede che venga selezionato almeno un filtro per lista, tuttavia richiede che venga selezionato almeno 1 filtro ( lasciando anche vuote le altre liste ).
Una volta selezionati i filtri desiderati si dovrà interagire con il Button "Search" posto nella parte bassa dell' Interfaccia, per poter avviare il processo di interrogazione.

La lista di risposta indicherà i servizi coinvolti indicandone: ID, nome provider, tipo servizio e stato, e nel caso si sia avviata l' interrogazione senza indicare nessun filtro, verrà indicata nella lista "Server response" la dicitura: "ERROR: parameters not found".