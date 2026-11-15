# LabPresta

Aplicación de escritorio desarrollada en **Java (Swing)** con persistencia en **SQLite (JDBC)** bajo la metodología **Scrum**, correspondiente al **Escenario C**.
El proyeto esta construido en **JDK17**

## 👥 Equipo y Roles Scrum
* **Product Owner:** [Santiago Paz]
* **Scrum Master:** [Diego Villagomez]
* **Desarrolladores:** [Danny Ponce], [Richard Quintana], [Diego Villagomez], [Santiago Paz]
* **Tablero Trello:** [https://trello.com/invite/b/6af88e86e57621a956ceb74f/ATTI79e72c285c7cdba2775f0913ba6582d931D728BF/equipo6-labpresta]

---

## 📁 Estructura del Proyecto

El código está organizado siguiendo una arquitectura por capas dentro de `src/com/labpresta/` para separar la interfaz gráfica de la lógica de negocio y la base de datos:

```text
labPresta-equipo6/
├── datos/                     # Base de datos SQLite para pruebas locales durante el desarrollo
│   └── labpresta.db
├── dist/                      # Carpeta de entrega final (ejecutable y datos precargados)
│   ├── LabPresta.jar
│   └── datos/
│       └── labpresta.db
├── lib/                       # Librerías externas permitidas
│   └── sqlite-jdbc-3.54.1.0.jar
└── src/
    └── com/
        └── labpresta/
            ├── Main.java          # Punto de entrada que inicia la aplicación
            ├── modelo/            # Clases entidad (POJOs): Equipo.java, Usuario.java, Prestamo.java
            ├── persistencia/      # Conexión SQLite y consultas SQL (DAOs): ConexionDB.java, EquipoDAO.java, etc.
            ├── logica/            # Validaciones y reglas de negocio: EquipoService.java, PrestamoService.java, etc.
            └── vista/             # Pantallas y formularios en Java Swing: VentanaPrincipal.java, PanelEquipos.java, etc.
```

## 📊 Seguimiento de Sprints

### Sprint 1 (Del Domingo 15 de Noviembre al Miercoles 18 de Noviembre)
* **Objetivo:** Implementar la arquitectura base, conexión a SQLite y el módulo completo de registro y gestión de equipos (`HU-C1`).

**Planificación inicial (Trello):**
  ![Tablero Trello - Inicio Sprint 1](evidencias/sprint1-inicio.png)
  Asignación de tareas a cada miembro del scrum team

* **Entregables completados:**
