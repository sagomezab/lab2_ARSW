Escuela Colombiana de Ingeniería

Arquitecturas de Software – ARSW

__Desarrollado por:__ Daniel Santago Gómez Zabala

__Taller__ – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

__Parte I – Antes de terminar la clase.__

Creación, puesta en marcha y coordinación de hilos.

1. Revise el programa “primos concurrentes” (en la carpeta parte1), dispuesto en el paquete edu.eci.arsw.primefinder. Este es un programa que calcula los números primos entre dos intervalos, distribuyendo la búsqueda de los mismos entre hilos independientes. Por ahora, tiene un único hilo de ejecución que busca los primos entre 0 y 30.000.000. Ejecútelo, abra el administrador de procesos del sistema operativo, y verifique cuantos núcleos son usados por el mismo.

* ![](img/Nucleos1_Parte1.png)

* __Respuesta:__ durante la ejecución del programa con un solo hilo se, al inicio de se utiliza el 100% de los 8 núcleos, después de un tiempo baja el porcentaje de uso en general de los núcleos. 

2. Modifique el programa para que, en lugar de resolver el problema con un solo hilo, lo haga con tres, donde cada uno de éstos hará la tarcera parte del problema original. Verifique nuevamente el funcionamiento, y nuevamente revise el uso de los núcleos del equipo.

* ![](img/Nucleos2_parte1.png)

*__Respuesta:__ en este caso vemos que el el desempeño de cada núcleo baja, casi en un 20% e individualmente no se usan el mismo porcentaje en todos. 

3. Lo que se le ha pedido es: debe modificar la aplicación de manera que cuando hayan transcurrido 5 segundos desde que se inició la ejecución, se detengan todos los hilos y se muestre el número de primos encontrados hasta el momento. Luego, se debe esperar a que el usuario presione ENTER para reanudar la ejecución de los mismo.



#####Parte II 


Para este ejercicio se va a trabajar con un simulador de carreras de galgos (carpeta parte2), cuya representación gráfica corresponde a la siguiente figura:

![](./img/media/image1.png)

En la simulación, todos los galgos tienen la misma velocidad (a nivel de programación), por lo que el galgo ganador será aquel que (por cuestiones del azar) haya sido más beneficiado por el *scheduling* del
procesador (es decir, al que más ciclos de CPU se le haya otorgado durante la carrera). El modelo de la aplicación es el siguiente:

![](./img/media/image2.png)

Como se observa, los galgos son objetos ‘hilo’ (Thread), y el avance de los mismos es visualizado en la clase Canodromo, que es básicamente un formulario Swing. Todos los galgos (por defecto son 17 galgos corriendo en una pista de 100 metros) comparten el acceso a un objeto de tipo
RegistroLLegada. Cuando un galgo llega a la meta, accede al contador ubicado en dicho objeto (cuyo valor inicial es 1), y toma dicho valor como su posición de llegada, y luego lo incrementa en 1. El galgo que
logre tomar el ‘1’ será el ganador.

Al iniciar la aplicación, hay un primer error evidente: los resultados (total recorrido y número del galgo ganador) son mostrados antes de que finalice la carrera como tal. Sin embargo, es posible que una vez corregido esto, haya más inconsistencias causadas por la presencia de condiciones de carrera.

Taller.

1.  Corrija la aplicación para que el aviso de resultados se muestre
    sólo cuando la ejecución de todos los hilos ‘galgo’ haya finalizado.
    Para esto tenga en cuenta:

    a.  La acción de iniciar la carrera y mostrar los resultados se realiza a partir de la línea 38 de MainCanodromo.

    b.  Puede utilizarse el método join() de la clase Thread para sincronizar el hilo que inicia la carrera, con la finalización de los hilos de los galgos.

    ![](img/CorrecionMensaje_Galgos.png)

2.  Una vez corregido el problema inicial, corra la aplicación varias
    veces, e identifique las inconsistencias en los resultados de las
    mismas viendo el ‘ranking’ mostrado en consola (algunas veces
    podrían salir resultados válidos, pero en otros se pueden presentar
    dichas inconsistencias). A partir de esto, identifique las regiones
    críticas () del programa.

    * Al ejecutar el programa y revisar la terminal, encontramos que más de un galgo puede ocupar la misma posición.

    * ![](img/Incosistencia_1.png)

    * Al ejecutar el programa varias veces encontre que no cuenta el total de galgos correctamente.

    * ![](img/Incosistencia_2.png)
    * __Regiones Críticas__ <br>
    Al consultar las posiciones, varios galgos pueden estar consultando al mismo tiempo y esto a su vez permite que se cuenten mal la cantidad de galgos que estan compitiendo.

3.  Utilice un mecanismo de sincronización para garantizar que a dichas
    regiones críticas sólo acceda un hilo a la vez. Verifique los
    resultados.

    * Se realizo en una sincronización en donde se pedian las ubicaciones la finalizar la carrera de los galgos, especifiamente en este sector de la clase Galgos <br>
    ![](img/Sincronizacion_regioncritica.png)<br>
    Además se realizaron diversas pruebas que demuestran que esa sincronización se realizo con exito, ya que se esta realizando de forma secuencial y no recurrente.<br>
    ![](img/Prueba_sincronizacion.png)

4.  Implemente las funcionalidades de pausa y continuar. Con estas,
    cuando se haga clic en ‘Stop’, todos los hilos de los galgos
    deberían dormirse, y cuando se haga clic en ‘Continue’ los mismos
    deberían despertarse y continuar con la carrera. Diseñe una solución que permita hacer esto utilizando los mecanismos de sincronización con las primitivas de los Locks provistos por el lenguaje (wait y notifyAll).

    * __Pausar__<br>
    El codigo se encuentra dentro de la carpeta, acá la prueba del funcionamiento del método.<br>
    ![](img/pausa.png)

    * __Continuar__<br>
    El codigo se encuentra dentro de la carpeta, acá la prueba del funcionamiento del método.<br>
    ![](img/continuar.png)


## Criterios de evaluación

1. Funcionalidad.

    1.1. La ejecución de los galgos puede ser detenida y resumida consistentemente.
    
    1.2. No hay inconsistencias en el orden de llegada registrado.
    
2. Diseño.   

    2.1. Se hace una sincronización de sólo la región crítica (sincronizar, por ejemplo, todo un método, bloquearía más de lo necesario).
    
    2.2. Los galgos, cuando están suspendidos, son reactivados son sólo un llamado (usando un monitor común).

