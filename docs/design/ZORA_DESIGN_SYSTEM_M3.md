# Zora M3 Design System: The Automotive Intelligence Framework

> **Versión:** 1.0 (Mobile First)  
> **Inspirado en:** `Material 3 Design Kit (Community)` (`ebIo5yv107VcfIVEYZKgPx`)  
> **Archivo Destino Figma:** `Zora` (`3dRkQFyFTmitTwGat0Gm2f`)  
> **Lienzo Objetivo:** `Page 4 - Zora M3 Design System & Mobile App`  
> **Plataforma Core:** Kotlin Multiplatform + Compose Multiplatform (`zora-community`)

---

## 1. Visión y Creative North Star: "Precision Automotive Intelligence"

El **Zora M3 Design System** es una evolución del sistema **Google Material 3 Expressive**, diseñado específicamente para el ecosistema automotriz de alta ingeniería, privacidad local-first y asistencia por IA en tiempo real.

Rompe con las plantillas genéricas de SaaS mediante:
1. **Asimetría Tonal y Sin Bordes Duros:** Las secciones no se delimitan con líneas sólidas de 1px; se definen mediante transiciones de superficie tonal (`surface` a `surface-container-low` a `surface-container-high`).
2. **Textura de Titanio y Gradiente de Ignición:** Fondo negro titanio profundo (`#0D0E11`) enriquecido con el gradiente de marca *Electric Violet to Neon Rose* (`linear-gradient(135deg, #894CFF 0%, #A833FD 50%, #EC168A 100%)`).
3. **Trío Tipográfico de Autoridad Técnica:**
   - **Manrope:** Display y titulares con carácter geométrico y alta tecnología.
   - **Inter:** Texto de cuerpo e interfaz para máxima legibilidad en cabina y entornos de poca luz.
   - **JetBrains Mono:** Códigos OEM, VIN, telemetría y especificaciones de motor.

---

## 2. Tokens Fundacionales (M3 Architecture)

### 2.1. Paleta de Color (Modo Oscuro Zora M3)

| Token M3 | Hex | Rol y Aplicación en Zora |
| :--- | :--- | :--- |
| `primary` | `#D1BCFF` | Acento interactivo principal, texto en contenedores oscuros. |
| `on_primary` | `#3D008F` | Texto y glifos sobre elementos `primary`. |
| `primary_container` | `#894CFF` | Botones de acción destacados, insignias activas. |
| `on_primary_container` | `#FFFCFF` | Texto de alto contraste sobre `primary_container`. |
| `secondary` | `#E1B6FF` | Acciones secundarias, chips de filtrado seleccionados. |
| `secondary_container` | `#9201E7` | Superficies de selección activa. |
| `tertiary` | `#FFB0CB` | Acento de alta visibilidad para estados de IA y radar. |
| `tertiary_container` | `#E20182` | Insignias de superpoderes Pro y alertas de ahorro máximo. |
| `background` / `surface` | `#0D0E11` | Canvas base de titanio negro profundo. |
| `surface_container_lowest`| `#090A0D` | Fondo de áreas de código y lienzos inmersivos. |
| `surface_container_low` | `#12151C` | Contenedores secundarios y hojas de fondo. |
| `surface_container` | `#1B1B1F` | Tarjetas estándar (Cards), módulos de chat. |
| `surface_container_high`| `#25262B` | Tarjetas elevadas, campos de entrada y barras fijas. |
| `surface_container_highest`| `#2E3036` | Estados hover y modales emergentes. |
| `on_surface` | `#E3E2E6` | Texto principal (legibilidad AAA). |
| `on_surface_variant` | `#A3AAC4` | Metadatos técnicos, etiquetas y texto de apoyo. |
| `outline` | `#958DA1` | Líneas de división sutiles (opacidad 20%). |
| `outline_variant` | `#4A4455` | Bordes ultrafinos de 0.5px (*Ghost Borders*). |

#### Semántica Automotriz Funcional
* **Direct Fit / Nivel Óptimo (Success):** Esmeralda `#10B981` (`on_success`: `#003822`).
* **Maintenance Warning / Nivel Bajo (Warning):** Ámbar `#F59E0B` (`on_warning`: `#422500`).
* **Critical Fault / Alerta de Falla (Error):** Carmesí `#EF4444` (`on_error`: `#690005`).

---

### 2.2. Sistema de Elevación y Profundidad (Elevation Scale)

A diferencia de las sombras genéricas, Zora combina **Tonal Stacking** con **Glows de Neón Ambiental**:
* **Elevation 1 (Surface Container):** `surface_container` (`#1B1B1F`) + sombra sutil `0px 1px 3px 0px rgba(0,0,0,0.4)`.
* **Elevation 2 (Interactive Card):** `surface_container_high` (`#25262B`) + sombra `0px 4px 6px -1px rgba(0,0,0,0.5)`.
* **Elevation 3 (Floating Bar / Modal):** `rgba(18, 21, 28, 0.85)` + `backdrop-filter: blur(16px)` + borde superior `rgba(74, 68, 85, 0.3)`.
* **Brand Glow Level (Active / Pro CTA):** `box-shadow: 0px 0px 20px -3px rgba(137, 76, 255, 0.45)`.

---

### 2.3. Escala de Formas (Shape System)

* **Capsule (`rounded-full` / `9999px`):** Botones principales CTA, píldoras de modelo IA, chips de sugerencias.
* **Large Card (`rounded-2xl` / `16px`):** Tarjeta principal de vehículo activo (*Hero Showcase*), Widget de telemetría de 30 segundos.
* **Standard Container (`rounded-xl` / `12px`):** Tarjetas de refacciones, ranuras de memoria del Copilot, campos de búsqueda.
* **Micro Chip (`rounded-lg` / `8px`):** Etiquetas de donante de yonke (Salvage Yard), códigos de VIN, selectores de motor.

---

## 3. Catálogo de Componentes M3 Adaptados a Zora

### 3.1. Top App Bar (Header Unificado)
* **Altura:** `56dp` / `64dp`.
* **Estructura:**
  * Izquierda: Botón Menú hamburguesa / Volver (`36x36dp`, `rounded-lg`).
  * Centro: **Model Selector Pill** (`rounded-full`, borde fino, muestra el modelo activo del Copilot e.g., `⚡ Gemini 3.8 Flash`).
  * Derecha: **Avatar de Usuario** circular con borde degradado de marca (*Brand Gradient Border*) e indicador de nivel de suscripción (Free / Pro).

### 3.2. Action Buttons
* **Glow CTA Button:** Fondo con gradiente degradado *Electric Violet to Neon Rose*, texto en mayúsculas/bold en Inter, radio completo `rounded-full` y resplandor violeta en reposo.
* **Tonal Action Button:** Fondo `surface_container_high`, texto `on_surface`, radio `rounded-xl`.
* **Ghost Outlined Button:** Fondo transparente, borde de 1px `outline_variant` con 30% opacidad.

### 3.3. AI Copilot Conversational Bubbles
* **Copilot Bubble:** Alineada a la izquierda, fondo `surface_container`, borde sutil izquierdo con gradiente violeta de 2px, avatar del Copilot integrado.
* **Driver Bubble:** Alineada a la derecha, fondo `surface_container_high`, texto claro, radio adaptado.
* **Docked Bottom Composer Bar:** Fijada en la parte inferior, traslúcida con `backdrop-blur(12px)`, contenedor de texto redondeado con icono de micrófono y botón de envío activo.

### 3.4. Widgets de Telemetría y Garage
* **30-Second Quick Diagnostic Widget:** Tres medidores horizontales rápidos con barras de progreso animadas:
  * Nivel de Aceite (Óptimo / Esmeralda).
  * Presión de Llantas (32 PSI / Verde).
  * Nivel de Refrigerante (Advertencia / Ámbar).
* **Copilot Memory Vault Card:** 3 ranuras libres en modo Free, visualización de datos recordados (e.g., *"Aceite 5W-30 Sintético cambiado a 82,400 km"*), botón de purga manual por ítem para privacidad.

---

## 4. Estructura de Lienzo Figma: `Page 4`

En el archivo `3dRkQFyFTmitTwGat0Gm2f`, `Page 4` organiza dos zonas principales:

```
+----------------------------------------------------------------------------------------------------+
|                                  PAGE 4: ZORA M3 DESIGN SYSTEM & APP                               |
+----------------------------------------------------------------------------------------------------+
|  [ZONA A: SISTEMA DE DISEÑO (TOKENS Y COMPONENTES)]                                                |
|  ├── 1. Color System (Paleta Tonal M3: Primary, Surface, Semantic Status)                          |
|  ├── 2. Typography Scale (Manrope, Inter, JetBrains Mono)                                          |
|  ├── 3. Elevation & Glows (Niveles 1-5 + Brand Neon Glows)                                         |
|  ├── 4. Shape Scale (Capsule 9999px, Sheet 28px, Card 16px/12px, Micro 8px)                       |
|  └── 5. Component Master Library (TopAppBar, CTA, Chat Bubbles, Memory Vault, Telemetry)          |
|                                                                                                    |
|  [ZONA B: EXPERIENCIA MOBILE APP REDISEÑADA (390px Viewports)]                                    |
|  ├── Screen 00: Zora - Splash Ignition Screen                                                     |
|  ├── Screen 01: Zora Copilot - Empty State (Welcoming & Quick Prompts)                             |
|  ├── Screen 02: Zora Copilot - Onboarding Conversational Flow                                      |
|  ├── Screen 03: Zora - Virtual Garage & Care Telemetry Vault                                       |
|  ├── Screen 04: Zora - Parts & Salvage Intelligence Feed                                           |
|  └── Screen 05: Zora Pro - Multi-Agent Network & Paywall                                           |
+----------------------------------------------------------------------------------------------------+
```

---

## 5. Script Ejecutable para Crear `Page 4` en Figma (Plugin API / Scripter)

Puedes copiar y pegar este script en la consola de Figma (o ejecutarlo mediante el plugin **Scripter** / Figma Plugin API) para generar automáticamente **Page 4** con todas las secciones, marcos de tokens y pantallas de la aplicación en tu archivo `3dRkQFyFTmitTwGat0Gm2f`:

```typescript
// ============================================================================
// FIGMA SCRIPT: Generador de Page 4 - Zora M3 Design System & Mobile App
// Archivo Destino: 3dRkQFyFTmitTwGat0Gm2f
// ============================================================================

async function generatePage4() {
  // 1. Crear o localizar Page 4
  let targetPage = figma.root.children.find(p => p.name.includes("Page 4"));
  if (!targetPage) {
    targetPage = figma.createPage();
    targetPage.name = "Page 4 - Zora M3 Design System & Mobile App";
  }
  figma.currentPage = targetPage;

  // Cargar fuentes necesarias
  await Promise.all([
    figma.loadFontAsync({ family: "Inter", style: "Regular" }),
    figma.loadFontAsync({ family: "Inter", style: "Medium" }),
    figma.loadFontAsync({ family: "Inter", style: "Semi Bold" }),
    figma.loadFontAsync({ family: "Inter", style: "Bold" }),
  ]);

  // Paleta de colores Hex to RGB helper
  const hex = (h: string) => {
    const num = parseInt(h.replace("#", ""), 16);
    return {
      r: ((num >> 16) & 255) / 255,
      g: ((num >> 8) & 255) / 255,
      b: (num & 255) / 255
    };
  };

  const C = {
    canvas: hex("#0D0E11"),
    surface: hex("#12151C"),
    surfaceContainer: hex("#1B1B1F"),
    surfaceHigh: hex("#25262B"),
    primary: hex("#D1BCFF"),
    primaryContainer: hex("#894CFF"),
    secondary: hex("#E1B6FF"),
    tertiary: hex("#FFB0CB"),
    tertiaryContainer: hex("#E20182"),
    onSurface: hex("#E3E2E6"),
    onSurfaceVariant: hex("#A3AAC4"),
    outline: hex("#4A4455"),
    success: hex("#10B981"),
    warning: hex("#F59E0B"),
    error: hex("#EF4444")
  };

  // --------------------------------------------------------------------------
  // SECCIÓN 1: CABECERA DE DESIGN SYSTEM (ZONA A)
  // --------------------------------------------------------------------------
  const dsSection = figma.createSection();
  dsSection.name = "ZORA M3 DESIGN SYSTEM - TOKENS & COMPONENTS";
  dsSection.x = 0;
  dsSection.y = 0;
  dsSection.resizeWithoutConstraints(2800, 1600);
  dsSection.fills = [{ type: "SOLID", color: C.canvas }];

  // 1.1 Paleta de Color Frame
  const colorFrame = figma.createFrame();
  colorFrame.name = "Color Tokens (Material 3 Dark Palette)";
  colorFrame.x = 80;
  colorFrame.y = 100;
  colorFrame.resize(800, 420);
  colorFrame.fills = [{ type: "SOLID", color: C.surface }];
  colorFrame.cornerRadius = 16;
  dsSection.appendChild(colorFrame);

  const colorsList = [
    { name: "Primary", hex: "#D1BCFF", color: C.primary },
    { name: "Primary Container", hex: "#894CFF", color: C.primaryContainer },
    { name: "Secondary", hex: "#E1B6FF", color: C.secondary },
    { name: "Tertiary / Neon Rose", hex: "#EC168A", color: C.tertiaryContainer },
    { name: "Surface Titanio", hex: "#12151C", color: C.surface },
    { name: "Surface Container", hex: "#1B1B1F", color: C.surfaceContainer },
    { name: "Direct Fit / Success", hex: "#10B981", color: C.success },
    { name: "Warning / Amber", hex: "#F59E0B", color: C.warning },
    { name: "Critical / Carmesí", hex: "#EF4444", color: C.error },
  ];

  colorsList.forEach((c, idx) => {
    const chip = figma.createRectangle();
    chip.resize(160, 80);
    chip.cornerRadius = 12;
    chip.fills = [{ type: "SOLID", color: c.color }];
    chip.x = 30 + (idx % 4) * 185;
    chip.y = 40 + Math.floor(idx / 4) * 115;
    colorFrame.appendChild(chip);

    const label = figma.createText();
    label.characters = `${c.name}\n${c.hex}`;
    label.fontSize = 11;
    label.fills = [{ type: "SOLID", color: C.onSurface }];
    label.x = chip.x;
    label.y = chip.y + 86;
    colorFrame.appendChild(label);
  });

  // 1.2 Componentes Maestros Frame
  const compFrame = figma.createFrame();
  compFrame.name = "Master Components (TopAppBar, CTA, Telemetry, Memory)";
  compFrame.x = 940;
  compFrame.y = 100;
  compFrame.resize(1700, 1400);
  compFrame.fills = [{ type: "SOLID", color: C.surface }];
  compFrame.cornerRadius = 16;
  dsSection.appendChild(compFrame);

  // Botón Glow CTA Master
  const btnCta = figma.createFrame();
  btnCta.name = "Component: Glow CTA Button";
  btnCta.resize(320, 52);
  btnCta.cornerRadius = 9999;
  btnCta.fills = [{
    type: "GRADIENT_LINEAR",
    gradientStops: [
      { position: 0, color: { r: 0.54, g: 0.30, b: 1.0, a: 1 } },
      { position: 0.6, color: { r: 0.66, g: 0.20, b: 0.99, a: 1 } },
      { position: 1, color: { r: 0.92, g: 0.08, b: 0.54, a: 1 } }
    ],
    gradientTransform: [[1, 0, 0], [0, 1, 0]]
  }];
  btnCta.x = 60;
  btnCta.y = 60;
  compFrame.appendChild(btnCta);

  const btnText = figma.createText();
  btnText.characters = "ACTIVAR ZORA PRO COPILOT";
  btnText.fontSize = 14;
  btnText.fills = [{ type: "SOLID", color: { r: 1, g: 1, b: 1 } }];
  btnText.x = 55;
  btnText.y = 18;
  btnCta.appendChild(btnText);

  // --------------------------------------------------------------------------
  // SECCIÓN 2: PANTALLAS REDISEÑADAS DE LA MOBILE APP (ZONA B)
  // --------------------------------------------------------------------------
  const screens = [
    { name: "00_Splash_Ignition", title: "ZORA IGNITION", subtitle: "Automotive Precision Copilot" },
    { name: "01_Copilot_Empty_State", title: "HOLA JESÚS", subtitle: "¿Qué revisamos en tu garaje hoy?" },
    { name: "02_Onboarding_Chat", title: "ONBOARDING CHAT", subtitle: "Sincronizando perfil del auto..." },
    { name: "03_Garage_Telemetry_Vault", title: "GARAJE Y BÓVEDA", subtitle: "BMW Serie 3 2021 (84,200 km)" },
    { name: "04_Parts_Intelligence_Feed", title: "REFACCIONES OEM / YONKE", subtitle: "Compatibilidad Direct Fit 100%" },
    { name: "05_Zora_Pro_Paywall", title: "SUPERPODERES PRO", subtitle: "Multi-Agent Network & Diagnostics" },
  ];

  screens.forEach((sc, i) => {
    const screenFrame = figma.createFrame();
    screenFrame.name = sc.name;
    screenFrame.x = 80 + i * 440;
    screenFrame.y = 1750;
    screenFrame.resize(390, 844); // iPhone / Modern Android Viewport
    screenFrame.fills = [{ type: "SOLID", color: C.canvas }];
    screenFrame.cornerRadius = 32;
    screenFrame.clipsContent = true;

    // Header superior estándar
    const topBar = figma.createFrame();
    topBar.name = "TopAppBar";
    topBar.resize(390, 56);
    topBar.fills = [{ type: "SOLID", color: C.surfaceContainer }];
    screenFrame.appendChild(topBar);

    const titleText = figma.createText();
    titleText.characters = sc.title;
    titleText.fontSize = 15;
    titleText.fills = [{ type: "SOLID", color: C.onSurface }];
    titleText.x = 20;
    titleText.y = 18;
    topBar.appendChild(titleText);

    // Contenido representativo
    const cardContent = figma.createFrame();
    cardContent.name = "Main Content Card";
    cardContent.resize(350, 220);
    cardContent.x = 20;
    cardContent.y = 80;
    cardContent.cornerRadius = 16;
    cardContent.fills = [{ type: "SOLID", color: C.surfaceContainer }];
    screenFrame.appendChild(cardContent);

    const subText = figma.createText();
    subText.characters = sc.subtitle;
    subText.fontSize = 13;
    subText.fills = [{ type: "SOLID", color: C.primary }];
    subText.x = 20;
    subText.y = 20;
    cardContent.appendChild(subText);
  });

  figma.notify("¡Page 4 generada exitosamente con el Zora M3 Design System!");
}

generatePage4();
```

---

## 6. Sincronización en Código con Compose Multiplatform

En el proyecto `zora-community`, este sistema de diseño se mapea 1:1 con la arquitectura de Jetpack Compose:

```kotlin
// composeApp/src/commonMain/kotlin/com/jesusdmedinac/zora/ui/theme/ZoraTheme.kt

val ZoraDarkColorScheme = darkColorScheme(
    primary = Color(0xFFD1BCFF),
    onPrimary = Color(0xFF3D008F),
    primaryContainer = Color(0xFF894CFF),
    onPrimaryContainer = Color(0xFFFFFCFF),
    secondary = Color(0xFFE1B6FF),
    secondaryContainer = Color(0xFF9201E7),
    tertiary = Color(0xFFFFB0CB),
    tertiaryContainer = Color(0xFFE20182),
    background = Color(0xFF0D0E11),
    surface = Color(0xFF12151C),
    surfaceVariant = Color(0xFF1B1B1F),
    onSurface = Color(0xFFE3E2E6),
    onSurfaceVariant = Color(0xFFA3AAC4),
    outline = Color(0xFF958DA1),
    outlineVariant = Color(0xFF4A4455),
)
```
