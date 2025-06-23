# ⚽ Uno Más - Plataforma de Encuentros Deportivos (Version 1.0)

Es una aplicación diseñada para ayudar a deportistas a organizar y completar partidos. Los usuarios pueden crear encuentros deportivos, buscar jugadores compatibles y recibir notificaciones automáticas.  
Este proyecto fue desarrollado como **Trabajo Práctico Obligatorio** para la materia **Proceso de Desarrollo de Software (1C 2025)**.

---

## 🧾 Tabla de Contenidos

- [✨ Funcionalidades](#-funcionalidades)
- [🧠 Patrones de Diseño](#-patrones-de-diseño)
- [🧱 Arquitectura](#-arquitectura)
- [📌 Requisitos Técnicos](#-requisitos-técnicos)
- [👥 Integrantes](#-integrantes)
- [🛠️ Bugs](#-bugs)

---

## ✨ Funcionalidades

- ✅ Registro de usuarios con preferencias deportivas
- 🔍 Búsqueda de partidos cercanos por tipo de deporte y nivel
- 🏗️ Creación de partidos con:
  - Deporte, ubicación, cantidad de jugadores, horario, duración
- 🔄 Gestión de estados del partido:
  - `Necesitamos jugadores`, `Partido armado`, `Confirmado`, `En juego`, `Finalizado`, `Cancelado`
- 🤝 Emparejamiento inteligente de jugadores por:
  - Nivel (`Principiante`, `Intermedio`, `Avanzado`)
  - Ubicación
  - Historial de partidos
- 📲 Notificaciones automáticas:
  - Firebase Push Notifications
  - Correos electrónicos (JavaMail u otra librería)

---

## 🧠 Patrones de Diseño

Se aplican al menos 4 de los siguientes patrones:

| Patrón     | Uso en el sistema                                       |
|------------|---------------------------------------------------------|
| `Strategy` | Estrategias de emparejamiento de jugadores              |
| `State`    | Transición de estados de los partidos                   |
| `Observer` | Notificaciones ante cambios de estado                   |
| `Adapter`  | Selección de proveedor de notificaciones                |

---

## 🧱 Arquitectura

- 🎯 Patrón **MVC** (Modelo - Vista - Controlador)
- 🧩 Programación Orientada a Objetos
- 📐 Diagrama de Clases UML incluido
- 💬 Documentación técnica de patrones aplicados

---

## 📌 Requisitos Técnicos

- 💻 Java 8+ (u otro lenguaje orientado a objetos)
- 🔧 IDE recomendado: IntelliJ IDEA / Eclipse IDE
- 🔗 Firebase SDK (para notificaciones push)
- 📧 JavaMail API (para emails)
- 📄 JDK + Maven 

---

## 👥 Integrantes

- Gianfranco Mazzei
- Facundo Conde
- Juan Cruz Iuliano
- Tomás Lescuenis
- Theo Ruschanoff
- Valen Fernandez

---

## 🛠️ Bugs

- Vista "Partidos Disponibles" un partido aparece multiples veces en la misma tabla (solo debe figurar una sola vez)
- Observer no envía notificaciones por cambio de estado (No se informa en la vista)
- Actualmente te deja unirte a partidos cancelador (no debería)
- No se muestra el ID del partido en la notificación (Se puede agregar)
- En Estado "Confimado" se pude pasar a "Cancelado" desde la vista (No afecta al backend, es solo tema de la UI)

