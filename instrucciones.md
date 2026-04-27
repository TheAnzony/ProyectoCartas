# Instrucciones Git: Actualizar y Subir a GitHub

## Requisitos previos

- Tener Git instalado en tu equipo
- Tener una cuenta en [GitHub](https://github.com)
- Tener un repositorio local ya inicializado (`git init`) o clonado

---

## 1. Configuración inicial (solo la primera vez)

Configura tu identidad en Git antes de hacer cualquier commit:

```bash
git config --global user.name "Tu Nombre"
git config --global user.email "tuemail@ejemplo.com"
```

---

## 2. Flujo básico: Actualizar y subir cambios

### Paso 1 — Ver el estado actual del repositorio

```bash
git status
```

Muestra qué archivos han sido modificados, cuáles son nuevos y cuáles están listos para el commit.

---

### Paso 2 — Añadir los cambios al área de preparación (staging)

Para añadir un archivo concreto:

```bash
git add nombre-del-archivo.txt
```

Para añadir todos los archivos modificados y nuevos:

```bash
git add .
```

---

### Paso 3 — Crear el commit

Un commit guarda una "foto" del estado actual de tu proyecto:

```bash
git commit -m "Descripción clara de los cambios realizados"
```

> **Consejo:** Escribe mensajes de commit descriptivos, por ejemplo:
> `"Añade función de login"` o `"Corrige error en el formulario de contacto"`

---

### Paso 4 — Subir los cambios a GitHub (push)

Sube tus commits a la rama principal en GitHub:

```bash
git push origin master
```

---

## 3. Descargar cambios desde GitHub (pull)

El comando `git pull` descarga los cambios del repositorio remoto y los integra en tu rama local.

### Uso básico

```bash
git pull origin master
```

Esto equivale a ejecutar dos comandos seguidos:
1. `git fetch` → descarga los cambios remotos
2. `git merge` → los fusiona con tu rama local

---

### Cuándo usar pull

- Antes de empezar a trabajar, para asegurarte de tener la versión más reciente
- Cuando otros colaboradores hayan subido cambios al repositorio
- Antes de hacer un push, para evitar conflictos

---

### Pull con rebase (alternativa recomendada en equipos)

```bash
git pull --rebase origin master
```

En lugar de crear un commit de fusión, reordena tus commits locales encima de los remotos, manteniendo un historial más limpio.

---

## 4. Flujo de trabajo completo recomendado

```bash
# 1. Descargar últimos cambios antes de empezar
git pull origin master

# 2. Hacer tus modificaciones en los archivos...

# 3. Ver qué ha cambiado
git status

# 4. Añadir los cambios
git add .

# 5. Crear el commit
git commit -m "Descripción de los cambios"

# 6. Subir a GitHub
git push origin master
```

---

## 5. Resolver conflictos

Si al hacer `pull` aparece un conflicto, Git marcará los archivos afectados:

```
<<<<<<< HEAD
Tu versión local
=======
Versión del repositorio remoto
>>>>>>> origin/master
```

**Pasos para resolverlo:**

1. Abre el archivo en conflicto
2. Edita manualmente el contenido, dejando solo lo que quieres conservar
3. Elimina las marcas `<<<<<<<`, `=======` y `>>>>>>>`
4. Añade y haz commit del archivo resuelto:

```bash
git add archivo-con-conflicto.txt
git commit -m "Resuelve conflicto en archivo-con-conflicto.txt"
```

---

## 6. Comandos de consulta útiles

| Comando | Descripción |
|---|---|
| `git log --oneline` | Historial de commits resumido |
| `git diff` | Diferencias entre archivos modificados y el último commit |
| `git branch` | Lista las ramas locales |
| `git remote -v` | Muestra la URL del repositorio remoto |

---

## 7. Conectar un repositorio local a GitHub (si aún no está conectado)

1. Crea un repositorio nuevo en [github.com](https://github.com/new) (sin inicializar con README)
2. En tu terminal, dentro de la carpeta del proyecto:

```bash
git remote add origin https://github.com/tu-usuario/nombre-repositorio.git
git push -u origin master
```

A partir de ahora puedes usar simplemente `git push` y `git pull` sin especificar el remoto.

---

*Documentación generada como referencia rápida para el flujo de trabajo con Git y GitHub.*
