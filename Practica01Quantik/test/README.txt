Orden sugerido de implementación para la resolución de la práctica (y consiguiente ejecución de los tests correspondientes).

Level 1: Color || Figura
Level 2: Pieza - Celda
Level 3: Caja || Grupo || Tablero
Level 4: GestorGrupos
Level 5: Partida

Para ejecutar los tests de cada nivel ("Level") se proporcionan "suites" correspondientes (e.g. SuiteLevel1Test).

Los símbolos || indican que no hay dependencias entre las clases y pueden ser implementadas/probadas en cualquier orden, aunque se sugiere seguir el orden planteado.

En el caso de Partida, se plantean test unitarios y de integración en dos clases separadas.