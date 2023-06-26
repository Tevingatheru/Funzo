```plantuml
!include <tupadr3/common>
!include <tupadr3/devicons/mysql>!define Database(iconUrl) <img:iconUrl>

skinparam componentStyle uml2
skinparam backgroundColor #FAFAFA

rectangle "Funzo" #FFC300 {
  component "Proxy" as proxy {
    
  }
  
  DEV_MYSQL(db,Mysql,database,red) 
  component "Mobile App" as app {
   [Authentication] as auth
   [Subject Selection] as subject
   [Reports]
   [Quiz] as quiz
   subject -down-> quiz
  }
  note bottom of proxy: Data Access
  note bottom of app: User Interaction and Content
}

component "Firebase" as firebase {
}

auth -up-> firebase
app <-> proxy: Sends requests
proxy <-right-> db: Manages

note left of firebase: User Authentication

proxy ..> firebase : Controls
firebase ..> auth : Handles authentication

@enduml
```
