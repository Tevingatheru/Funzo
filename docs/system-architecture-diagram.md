```plantuml
!include <tupadr3/common>
!include <tupadr3/devicons/mysql>!define Database(iconUrl) <img:iconUrl>

skinparam componentStyle uml2
skinparam backgroundColor #FAFAFA

rectangle "Funzo" #FFC300 {
  component "Proxy" as proxy {
    
  }
  
  component "Web application" as web {
    [Exam module] as exam      
    [Authentication] as webAuth
    [Subject module] as subject     
  }
  
  DEV_MYSQL(db,Mysql,database,red) 
  component "Mobile App" as app {
   [Authentication] as auth
   [Subject Selection] as subject
   [Reports]
   [Quiz] as quiz
   subject -down-> quiz
  }
  note left of proxy: Data Access
}

component "Firebase" as firebase {
}

auth -up-> firebase
app <-left-> proxy: Sends requests
web <-up-> proxy
proxy <-right-> db: Manages

note right of firebase: User Authentication

proxy ..> firebase : Controls
firebase ..> auth : Handles authentication

@enduml
```
