# Venta de agua TDP
Repo a usar para el proyecto final de TDP de **Guido Chia**.

El proyecto consiste en una aplicacion para administrar la venta de agua de una empresa.
Se utilizará Apache POI para leer y escribir archivos Excel donde se guardarán las ventas.
Cabe destacar que esta aplicación está en uso actualmente, desde el 25 de enero del 2019.

## Estructura

El proyecto consiste de dos módulos diferentes: **lógica**, y la **interfaz**.

### Lógica
Se encuentra en coreLib/src/main/java. Consta de diferentes clases organizadas en paquetes que interactuan entre sí.
El propósito de cada clase particular es explicado en el JavaDoc.
Cabe destacar que se han aplicado los siguientes patrones de diseño:
* **Strategy**: es utilizado en la clase Reader, para realizar chequeo de fechas de una forma simple y modular.
* **Visitor**: es usado para recorrer diferentes colecciones de Clientes, y realizar cálculos de manera eficaz y ordenada.
* **Singleton**: si bien es un patrón simple, logra su cometido evitando la creación de diferentes instancias de las clases Reader y Writer.

### Interfaz
La interfaz está compuesta por una actividad principal, donde se realizan cambios de fragments. Estos últimos se utilizan para lograr un diseño que se vea bien en diferentes tamaños y formatos de pantalla.

## Como instalar
En la carpeta APK se encuentra el archivo instalable para Android. Cabe destacar que la aplicación fue desarrollada para dispositivos a partir de Android 5.0  Lollipop. Si bien es un APK signado, es posible que el sistema del teléfono donde se instale no lo permita, ya qué no proviene de la Play Store, ni es de un desarrolador que Google considere *confiable*. De ser el caso, y no se pueda instalar directamente, se recomiendo hacer un pull del repositorio remoto, y luego cargarlo en un dispositivo mediante Android Studio, previamente haciendo el build necesario.

## Anexo
Como bien se ha mencionado, se utiliza la librería [**Apache POI**](https://poi.apache.org/) para acceder a los archivos Excel. Cabe mencionar que esta librería fue desarrolada para Windows, y al ser utilizada en Android directamente, produce errores. Por lo tanto, me vi obligado a usar una versión adaptada por un desarrollador particular. Este [**Apache POI para Android**](https://github.com/centic9/poi-on-android) fue modificado por el usuario [**centic9**](https://github.com/centic9/), a quién agradezco por su trabajo y su dispocisión antes mis dudas.
También quiero hace una especial mención a mi padre, quién fue el encargado de ser tanto el usuario de prueba, así como mi "cliente". 

Ante cualquier duda que se presente, dejo mi direccion de e-mail (<chiaguido@gmail.com>), donde pueden contactarme.
