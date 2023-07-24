
```plantuml
@startuml
title Logout Sequence Diagram

actor User
participant MobileApp
participant Firebase

User -> MobileApp: logout 
activate MobileApp
    MobileApp -> Firebase: Send logout request
        activate Firebase
            Firebase -> Firebase
            Firebase --> MobileApp: Verify logout
        deactivate Firebase    
    MobileApp -> MobileApp: change activity
    MobileApp --> User: display
deactivate MobileApp
@enduml
```
