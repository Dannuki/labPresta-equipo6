# LabPresta — Sistema de Control de Préstamos de Equipos de Laboratorio

**Equipo:** Equipo 6  
**Escenario:** C — Control de préstamos de equipos de laboratorio (`LabPresta`)  
**Tablero de Trello:** [Ver tablero de seguimiento en Trello](https://trello.com/invite/b/6af88e86e57621a956ceb74f/ATTI79e72c285c7cdba2775f0913ba6582d931D728BF/equipo6-labpresta)

Aplicación de escritorio desarrollada en **Java (Swing)** con persistencia en **SQLite (JDBC)** bajo la metodología **Scrum**, construida en **JDK 17**.

---

## 👥 Integrantes y Roles

| Integrante | Rol Scrum |
| :--- | :--- |
| **Santiago Paz** | Product Owner / Desarrollador |
| **Diego Villagomez** | Scrum Master / Desarrollador | 
| **Danny Ponce** | Desarrollador |
| **Richard Quintana** | Desarrollador |

---

## ⚙️ Requisitos del Sistema

* **Java JDK / JRE:** Versión 17 o superior.
* **Base de datos:** SQLite (embebido mediante `sqlite-jdbc-3.54.1.0.jar`, no requiere instalación adicional).

---

## 🚀 Cómo Ejecutar la Aplicación

### 🪟 En Windows

1. Clonar el repositorio (o descargar el ZIP desde **Code → Download ZIP** y descomprimirlo):
   ```powershell
   git clone https://github.com/Dannuki/labPresta-equipo6.git
   ```
2. Entrar a la carpeta `dist`:
   ```powershell
   cd labPresta-equipo6\dist
   ```
3. **Opción A — Desde terminal (PowerShell o CMD, recomendada):**
   ```powershell
   java -jar LabPresta.jar
   ```
4. **Opción B — Con doble clic desde el Explorador de archivos:**
   * Abre la carpeta `dist` y haz doble clic sobre `LabPresta.jar`.
   * *Si al hacer doble clic se abre como archivo comprimido (WinRAR / 7-Zip):* haz **clic derecho** sobre `LabPresta.jar` → **Abrir con** → **Elegir otra aplicación** → selecciona **Java(TM) Platform SE binary** y marca **Usar siempre esta aplicación para abrir los archivos .jar**.
   * *Si Windows Defender muestra una alerta preventiva:* selecciona **Más información → Ejecutar de todas formas** (o **Permitir** en Seguridad de Windows).

### 🐧 En Linux (Terminal)

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/Dannuki/labPresta-equipo6.git
   ```
2. Entrar a la carpeta `dist`:
   ```bash
   cd labPresta-equipo6/dist
   ```
3. Ejecutar el archivo `.jar`:
   ```bash
   java -jar LabPresta.jar
   ```

> **Importante:** En ambos sistemas operativos, ejecuta siempre `LabPresta.jar` desde dentro de la carpeta `dist` (sin mover el `.jar` suelto a otra ubicación) para que la aplicación reconozca la ruta relativa `datos/labpresta.db` y cargue los registros de prueba precargados[cite: 7].

---





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
│           ├── Main.java
│           ├── modelo/
│           ├── persistencia/
│           ├── logica/
│           ├── vista/
│           └── pruebas/
```

## 📊 Seguimiento de Sprints

### Sprint 1 (Del Domingo 15 de Noviembre al Miercoles 18 de Noviembre)
* **Objetivo:** Implementar la arquitectura base, conexión a SQLite y el módulo completo de registro y gestión de equipos (`HU-C1`).

**Planificación inicial (Trello):**

  ![Tablero Trello - Inicio Sprint 1](evidencias/sprint1-inicio.png)
  
  Asignación de tareas a cada miembro del scrum team

#### 🗓️ Bitácora Daily Scrum — Sprint 1

* **Día 1 — Domingo 15 de Noviembre**
  * **DP (Persistencia):** Creé la estructura del proyecto, configuré SQLite (`ConexionDB.java`), implementé `EquipoDAO.java` y actualicé el `README.md` con la planificación inicial. *Bloqueos:* Ninguno.
  * **D (Vista):** Creé la interfaz gráfica (FrmRegistroEquipo.java), la integré en el Main.java reemplazando la ventana de prueba, configuré el tablero de Trello con las listas requeridas y realicé el commit inicial de la historia HU-C1 en GitHub. *Bloqueos:* Ninguno.
  * **SP (Lógica):** 
  * **RQ (Vista/Persistencia):**


* **Día 2 — Lunes 16 de Noviembre**
  * **DP (Persistencia):** Cerré y se aprobó mi módulo de persistencia de equipos. *Bloqueos:* Ninguno.
  * **D (Vista):** 
  * **SP (Lógica):** 
  * **RQ (Vista/Persistencia):**

* **Día 3 — Martes 17 de Noviembre**
  * **DP (Persistencia):** 
  * **D (Vista):** 
  * **SP (Lógica):** Implementé `EquipoService.java` con las validaciones de campos obligatorios, código único y control de estado para `HU-C1`, y lo conecté con `FrmRegistroEquipo` en `Main.java`. *Bloqueos:* Ninguno.
  * **RQ (Vista/Persistencia):** 

* **Entregables completados:**

