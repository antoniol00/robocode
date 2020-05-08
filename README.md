# Robocode 🚀

### Participantes 👦

* Antonio Lara Gutiérrez
* Víctor Ramírez Mármol

### Instalación 🔧

* Versión [robocode](https://robocode.sourceforge.io/) 1.9.3.4
* Eclipse IDE

### Ejecución hitos 🗺

* **Hito 1**: Robot 1. Usado para batallas en app principal de Robocode
* **Hito 2**: Robot 2. Modela problema y genera mapa, el robot únicamente gira sobre sí mismo
  * En clase RouteFinder, variable hito2 permite ejecutar hito2(*true) o hito3(*false)
* **Hito 3**: Robot 3. Modela problema y encuentra solución, el robot ejecuta solución. En terminal, escribe toda la información relevante
  * En clase Bot 3, modificar variable value para ejecutar distintos hitos:
    * value = 1 -> Solución Amplitud
    * value = 2 -> Solución Voraz (Greedy)
    * value = 3 -> Solución A*
    
#### 🚨🚨**Importante! Variables `semilla` y `numObstaculos` deben coincidir en clases Bot3/Bot2 y RouteFinder. Si no coinciden, la ejecución resultará errónea**🚨🚨

