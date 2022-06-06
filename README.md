# Trust Service Explorer
## Installazione
Dopo aver clonato la repository del programma da github, vi sono due strade disponibile per la sua esecuzione:
aprire il progetto con un comune ide, come progetto maven, e eseguirlo normalmente
dopo aver navigato tramite linea di comando nella cartella contenente il programma scaricato, eseguire i due seguenti comandi;
>$ mvn compile

>$ mvn exec:java -Dexec.mainClass=com.ids.trustservicesurfer.SurfApplication

Nel caso di sistema Windows, è importante aver scaricato maven dal seguente link: https://maven.apache.org/download.cgi , questo ci permetterà di poter eseguire correttamente il progetto maven.
Dopo aver scaricato il file compresso, lo dovremo estrarre in una cartella a nostro piacimento.
Dopodichè dovremo aggiungere la variabili di sistema $MAVEN_HOME con la seguente procedura:
Recarsi nella sezione variabili d’ambiente del proprio sistema.
Fare clic sul pulsante Nuovo nella sezione Variabili di sistema per aggiungere una nuova variabile di ambiente di sistema.
Immettere MAVEN_HOME come nome della variabile e il percorso della directory Maven appena scaricata come valore della variabile. Fare clic su OK per salvare la nuova variabile di sistema.
Come ultimo passaggio si dovrà aggiornare la variabile di sistema PATH nel seguente modo:
Seleziona la variabile PATH nella sezione Variabili di sistema nella finestra Variabili d'ambiente. Fare clic sul pulsante Modifica per modificare la variabile.
Fare clic sul pulsante Nuovo nella finestra Modifica variabile d'ambiente
Immettere %MAVEN_HOME%\bin nel nuovo campo. Fare clic su OK per salvare le modifiche alla variabile Path.
Fare clic su OK nella finestra Variabili d'ambiente per salvare le modifiche alle variabili di sistema.



Per i sistemi Linux, la soluzione varia a seconda della distribuzione:
- [Maven]https://archlinux.org/packages/community/any/maven/). nel caso di Arch e derivate
- [Maven]https://packages.debian.org/buster-backports/maven). nel caso di Debian e derivate


In generale si può seguire questa guida generale .

Attraverso maven verranno scaricate delle librerie necessarie al corretto funzionamento del software:
- org.openjfx: libreria usata per la parte grafica
- org.json: libreria usata per una più semplice e sicura gestione dei documenti json (sono utilizzate le classi `JSONArray` e `JSONObject`)

## Grafica e Struttura interna generale
All' avvio dell'applicazione si potranno già visualizzare le liste dei paesi presenti. 

La lista dei country iniziale e quelle successive vengono gestite dalla classe JsonProcess, la quale possiede vari metodo per filtrare ed estrarre le informazioni volute dall’oggetto JSONArray, un oggetto contenente la rappresentazione in java di un documento json. Questi documenti sono ottenuti inviando delle apposite query alle API  del portale EU https://esignature.ec.europa.eu/efda/tl-browser attraverso la classe `ConnectionFactory`.
L'applicazione è divisa su due colonne principali:
- colonna sinistra: svolge il ruolo della selezione dei filtri sui servizi ed è divisa in tab: `Country`, `Provider`, `Type` e `State`. La selezione di un filtro avviene mediante click sulla riga del filtro della lista della colonna di destra superiore. Una volta selezionato il filtro voluto, una copia del filtro verrà inserita nella lista inferiore, permettendo così di visualizzare i filtri attivi. Analogamente, selezionando un filtro dalla lista dei filtri attivi, quella inferiore, esso verrà rimosso dai filtri attivi. Due bottoni velocizzano queste azioni nel caso si vogliano attivare tutti i filtri disponibili o rimuovere tutti quelli attivi.

  La selezione dei filtri avviene in modo sequenziale, ad esempio, per poter imporre un filtro sullo stato del servizio è necessario aver imposto i filtri precedenti (l'ordine dei filtri è `Country` -> `Provider` -> `Type` -> `State`). Analizziamo alcuni casi: 
Si vogliono selezionare tutti servizi offerti in Austria, indipendentemente dal provider, dal tipo e dallo stato. Per procedere con la ricerca basterà selezionare “AT : Austria” dalla prima lista nella prima tab, quella relativa ai paesi;

  Si vogliono selezionare tutti i servizi offerti dal provider “Poste Italiane” con stato “granted”. Per procedere basterà selezionare tutti i paesi dalla prima lista della prima tab (manualmente o utilizzando il bottone `Add all`), selezionare “Poste Italiane” tra i provider della seconda tab, selezionare tutti i tipi nella terza tab (manualmente o utilizzando il bottone `Add all`) e selezionare lo stato “granted” nella quarta tab.
In generale vale la seguente “regola”: si selezionano i filtri voluti nelle corrispettive tab, aggiungendo tutti quelli intermedi tra la tab iniziale e quella contenente i filtri voluti.
  La lista dei filtri successivi alla prima (`Country`) viene aggiornata ai filtri realmente utili. Ad esempio, si ha interesse nel sezionare i servizi austriaci che offrono il servizio “NonRegulatory”: dopo aver selezionato “AT : Austria”  dalla prima lista e tutti i provider dalla seconda, “NonRegulatory” tuttavia non sarà presente nella terza lista, perché questo tipo di servizio non è offerto in Austria.
  Cliccando su un elemento della lista superiore, l’evento viene gestito dalla funzione `onXXXFilterAdd` del `MainWindowController` (dove XXX varia a seconda della lista da cui parte l’evento), analogamente per la lista inferiore `onXXXFilterRemove`. Ogni volta che un filtro viene inserito o rimosso, i filtri nelle liste successive vengono aggiornate attraverso `updateAvailableFiltersOnChange` del `MainWindowController`;

- colonna destra: svolge il ruolo di mostrare la lista dei servizi in base ai filtri attivi. Dopo aver selezionati i vari filtri voluti, si può cliccare il bottone `Search` per far iniziare il processo di ricerca. Appariranno nella lista nella colonna di destra i servizi cercati.

Cliccando il bottone, l’evento viene gestito da onSearchStart del `MainWindowController`.

Nella barra del menu vi sono due ulteriori funzioni:
- `Close`: permette di chiudere l’applicazione
- `Reset`: permette di resettare tutti i filtri attivi ed i risultati
