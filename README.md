Proyecto 
Estructuras de Datos 
enero – mayo 2023
Hacer una calculadora que pueda realizar operaciones aritméticas sobre números reales, 
tanto negativos como positivos, y que cuente con una interfaz gráfica amigable para el 
usuario. El usuario indica las operaciones a realizar a través de expresiones en notación 
infija las cuales pueden incluir suma, resta, multiplicación y división. Además, pueden 
tener paréntesis para alterar el orden estándar de ejecución de los operadores. La 
interfaz necesita contar con botones para las cuatro operaciones mencionadas 
anteriormente y los dos paréntesis (izquierdo y derecho), también debe tener un botón 
para cada uno de los diez dígitos 0-9, uno para el punto decimal, uno para indicar 
“cambio de signo” (para poder expresar valores negativos), y uno con el símbolo = para 
que el programa comience a evaluar la expresión que el usuario haya ingresado. Puede 
ser conveniente que la calculadora también tenga un botón para “limpiar el contenido” 
de la ventana de despliegue (y así pueda el usuario empezar a indicar una nueva 
expresión a evaluar).

Cuando el usuario apriete el botón = se deben realizar tres pasadas sobre la expresión 
que ingresó: 
  1) Verificar su validez (por ejemplo, asegurarse de que los paréntesis estén
  balanceados, asegurarse de que no se hayan puesto dos operadores seguidos 
  como “1+*53.2”, y filtrar otros posibles errores sintácticos parecidos a éste).
  2) Convertir la expresión (que ahora se sabe que es sintácticamente válida, y que 
  está en notación infija) a su equivalente en notación postfija.
  3) Evaluar la expresión en notación postfija para conocer su resultado final. El 
  resultado debe mostrarse en la ventana de despliegue de la calculadora (la cual 
  también debe utilizarse para irle mostrando al usuario los botones que ha ido 
  apretando antes de apretar el botón = y para mostrar cualquier mensaje de error 
  que pueda ser necesario desplegar).
  
Especificaciones extras:
 • Para desarrollar el proyecto los alumnos deben formar equipos de trabajo (de 3 o 
 4 integrantes, máximo).
 • Los equipos deben usar GitHub (repositorio y trabajo colaborativo).
 Fecha de entrega del proyecto: 
 - 09/03/2023: versión completa del proyecto.

Entregables:
 1. El proyecto de Netbeans completo comprimido subirlo a Canvas. Si se entrega
 el proyecto incompleto o las clases por separado, se considerará NO entregado.
 2. Enviar a el profesor la liga del repositorio de GitHub donde está almacenado el 
 proyecto, por medio del correo de Canvas. 
 3. Todo el código debe estar debidamente documentado usando Javadoc.
 4. El código debe ser probado usando JUnit.
 5. Se debe subir a Canvas el reporte (en formato pdf) sobre el proyecto. 
 6. El reporte debe tener un apéndice con el código (debidamente documentado) del 
 programa. En el apéndice NO se debe incluir el código correspondiente a la 
 interface PilaADT y a la clase PilaA (hechas en clase).
