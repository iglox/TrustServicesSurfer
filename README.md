# TrustServicesSurfer
INSTALLAZIONE
Dopo aver clonato la repository del programma da github, vi sono due strade disponibile per la sua esecuzione:
aprire il progetto con un comune ide, come progetto maven, e eseguirlo normalmente
dopo aver navigato tramite linea di comando nella cartella contenente il programma scaricato, eseguire i due seguenti comandi;
$ mvn compile
$ mvn exec:java -Dexec.mainClass=com.ids.trustservicesurfer.SurfApplication
E` importante aver settato in modo corretto la variabile di sistema $JAVA_HOME con il path corretto, inoltre per poter utilizzare il comando mvn da qualsiasi cartella è necessario installare maven dal seguente link: https://maven.apache.org/download.cgi.

Attraverso maven verranno scaricate delle librerie necessarie al corretto funzionamento del software:
org.openjfx: libreria usata per la parte grafica
org.json: libreria usate per una più semplice e sicura gestione dei documenti json (sono utilizzate le classi JSONArray e JSONObject)

STRUTTURA APPLICAZIONE:
All' avvio dell'applicazione si potranno già visualizzare le liste dei paesi, dei tipi di servizio, dei provider e degli stati del servizi presenti.
Questo processo viene eseguito dalla classe connectionFactory che instaura la connessione con il server, al quale viene inviata una GET request utilizzando una stringa costruita con il metodo “buildSearchCriteriaString”, il quale riceve come parametri due array di stringhe necessari a comporre la stringa.


- I paesi, i tipi di servizi, i provider e gli stati dei servizi vengono racchiusi in una raccolta posta in alto a sinistra dell' applicazione, quest'ultima può essere sfogliata cliccando sull 'etichetta nominativa della corrispettiva sotto raccolta scelta.
  PER OGNI SOTTO RACCOLTA:
- vengono visualizzate due liste messe una sopra l'altra nella parte sinistra dell' interfaccia: La prima lista contiene tutte le voci esistenti per ogni sotto raccolta, tali voci vengono visualizzate immediatamente all' avvio dell' applicazione.
  La seconda lista è una struttura dinamica, posizionata sotto a quella appena illustrata, contiene le selezioni effettuate dall' utente necessarie a interrogare il sistema.
  Tramite degli appositi metodi “onFilterAdd”  si fa in modo che quando l’utente clicca su una voce della prima  lista, questa stessa voce venga immessa nella lista dinamica come filtro.
  Nel caso contrario se l’utente ha intenzione di rimuovere un filtro, basterà ricliccarci sopra nella lista dinamica, in modo da interpellare i metodi “onFilterRemove” che lo elimineranno dalla lista dei filtri attivi.
- Una volta che si sono scelti i filtri voluti, e si clicca sul pulsante search posto nella parte bassa dell’ interfaccia, entra in azione il metodo “onSearchStart” della classe MainWindowController che si occuperà di inviare una POST request al server utilizzando i filtri selezionati dall’ utente.
  Il server restituirà un file Json che verrà gestito dalla classe “JsonProcess” per far in modo di stampare sulla sezione “Server Response” dell’ interfaccia solo alcuni campi dei servizi restituiti.

La lista ”ServerResponse” indicherà i servizi che soddisfano i filtri applicati  indicandone: ID, nome provider, tipo servizio e stato, e nel caso si sia avviata l' interrogazione senza indicare nessun parametro, verrà indicata la dicitura: "ERROR: parameters not found" all’ interno di una finestra d’errore.






vizi coinvolti indicandone: ID, nome provider, tipo servizio e stato, e nel caso si sia avviata l' interrogazione senza indicare nessun filtro, verrà indicata nella lista "Server response" la dicitura: "ERROR: parameters not found".