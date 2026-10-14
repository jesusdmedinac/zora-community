# Zora Dual Design Systems: Foundations, Tokens & Native Specifications

> **Versión:** 2.0 (Dual Native Architecture: Material 3 & Apple Human Interface Guidelines)  
> **Lienzo Fuente:** `zora-community.pen` en Pen Desktop  
> **Alcance de Pantallas Analizadas:** 10 pantallas activas (Pares 00 a 04 para M3 y HIG)  
> **Plataforma Tecnológica:** Kotlin Multiplatform + Compose Multiplatform (Android & iOS)

---

## 1. Visión y Creative North Star: "Precision Automotive Intelligence"

Zora es el copiloto automotriz de IA diseñado para conductores que valoran la alta ingeniería, el mantenimiento predictivo y el control de su vehículo. 

En lugar de un diseño híbrido genérico o un compromiso visual que degrade la experiencia de usuario, Zora implementa **dos sistemas de diseño nativos paralelos**:
1. **Zora Material 3 (`Zora-M3`)**: Rigor al 100% con Google Material 3 Expressive para el ecosistema Android.
2. **Zora Human Interface (`Zora-HIG`)**: Rigor al 100% con Apple Human Interface Guidelines para el ecosistema iOS.

Ambos sistemas comparten el ADN automotriz de Zora: el lienzo oscuro **Obsidian Titanium** (`#0D0F12`), el espectro de ignición **135° Core Spectrum** (`#38E5FF` → `#894CFF` → `#EC168A`) y la autoridad técnica tipográfica.

---

## 2. Tokens Fundacionales (Foundations & Tokens)

### 2.1. Paleta de Superficies y Contraste Oscuro (Dark Surface Hierarchy)

| Token Semántico | Valor Hex | Rol en Zora M3 | Rol en Zora HIG |
| :--- | :--- | :--- | :--- |
| `$canvas` | `#0D0E11` / `#0D0F12` | Fondo base de titanio negro profundo | Lienzo base del sistema |
| `$surface` | `#12151C` | Contenedores base y fondo de navegación | Fondo de vistas secundarias |
| `$surfaceContainerLow` | `#171A21` | Opciones inactivas, fondos de chips | Filas inactivas en listas |
| `$surfaceContainer` | `#1B1B1F` | Tarjetas estándar (`Cards`) | Inset Grouped Cards base |
| `$surfaceContainerHigh` | `#25262B` | Burbujas de usuario, opciones activas | Burbujas iMessage, opciones seleccionadas |
| `$surfaceContainerHighest` | `#30323A` | Cajas de métricas (Odómetro digital) | Cajas métricas y modales |
| `$outlineVariant` | `#4A4455` | Bordes ultrafinos de tarjetas y radios inactivos | Bordes de tarjetas y separadores de lista |

### 2.2. Acentos de Marca (135° Core Spectrum)

| Token | Hex | Rol y Comportamiento |
| :--- | :--- | :--- |
| `$primary` | `#D1BCFF` / `#38E5FF` | Acento interactivo principal, Cyan Eléctrico / Violeta de Ignición |
| `$primaryContainer` | `#894CFF` | Chips activos, botones prominentes |
| `$onPrimary` | `#0D0F12` / `#3D008F` | Texto y glifos sobre elementos `$primary` |
| `$onPrimaryContainer` | `#FFFCFF` / `#EADDFF` | Texto de alto contraste sobre `$primaryContainer` |
| `$secondary` | `#E1B6FF` | Acciones secundarias, metadatos destacados |
| `$tertiaryContainer` | `#EC168A` | Insignias de diagnóstico crítico y acentos deportivos |

### 2.3. Jerarquía Tipográfica de Tres Niveles

1. **`$fontHeadline` (Manrope / SF Pro Display):** Títulos, números grandes de odómetro (`75,000 km`) y nombres de vehículos (`Honda Civic EX 2018`).
2. **`$fontBody` (Inter / SF Pro Text):** Texto de conversación, opciones de motor, subtítulos técnicos y etiquetas de botones.
3. **`$fontMono` (JetBrains Mono / SF Mono):** Especificaciones de cilindrada, caballos de fuerza, tolerancias mecánicas y códigos OEM.

### 2.4. Escala de Curvatura Geométrica (Corner Radii)

| Token | Valor | Aplicación en Interfaz |
| :--- | :--- | :--- |
| `$radiusSm` | `8 px` | Badges, etiquetas métricas compactas |
| `$radiusMd` | `12 px` | Opciones de lista interna, cajas de odómetro |
| `$radiusLg` | `16 px` | Tarjetas principales (`Vehicle Card`, `Engine Card`, `Mileage Card`) |
| `$radiusDevice` | `54 px` | Curvatura estándar de dispositivo móvil (iPhone base 390 pt) |
| `$radiusDeviceProMax` | `62 px` | **Curvatura nativa del chasis iPhone 18 Pro Max (440 pt de viewport)** |

---

## 2.5. Especificaciones Físicas: Chasis iPhone 18 Pro Max (`490 × 1000 px`)

Para maquetas de presentación fotorrealistas de alta fidelidad, se integró el marco de chasis `iPhone 18 Pro Max`:

- **Dimensiones Exteriores:** `440 px` de ancho visible + biseles = `490 × 1000 px`.
- **Viewport de Pantalla Interior:** `440 × 956 px` (resolución nativa de la familia Pro Max en puntos HIG).
- **Radio de Curvatura Interior (`$radiusDeviceProMax`):** `62 px` squircle / radio continuo.
- **Geometría Calibrada de la Dynamic Island:**
  - Ancho de cápsula: `94 px` (expandible a `120 px`).
  - Alto de cápsula: `36 px`.
  - Centrado horizontal: `center x = 220.0 px` (`x = 173 px` en viewport), balanceado con alas laterales idénticas de `70 px` (Reloj a la izquierda y Señal/Batería a la derecha).
  - Offset superior desde el viewport: `14 px` (`y = 36 px` en coordenadas del chasis).
  - Encabezado de barra de estado: Alto `56 px`, padding horizontal `24 px`.
- **Arquitectura de Encabezado Fijo (Onboarding Chat 03 y 04):**
  - Encapsulado en `Fixed Top Header` (`layoutPosition: "absolute"`, `x: 0, y: 0, width: 440, height: 104`) con fondo esmerilado `#0D0F12E6`.
  - `Scrollable Chat Feed` (`x: 0, y: 0, width: 440, height: 864`) con `clip: true` y `padding: [116, 16, 16, 16]`. El contenido pasa por debajo de la barra sin ocultar el status bar ni los controles de navegación.
- **Proporciones de Navigation Drawer (02):**
  - Menú lateral ampliado a `330 px` para mantener el ancho de vista previa de la derecha en `110 px`.
  - Vista previa de fondo actualizada a `440 px` de ancho nativo Pro Max con dimmer completo de `110 × 876 px`.
- **Home Indicator Calibrado:** Ancho `140 px`, alto `5 px`, centrado a `x = 150 px` en todas las pantallas.
- **Pantallas Duplicadas Activas:**
  1. `Screen / HIG (iPhone 18 Pro Max) - 00 Splash Ignition` (`W8AYzQ`, `x: 12019, y: 2500`)
  2. `Screen / HIG (iPhone 18 Pro Max) - 01 Copilot Home` (`Dp6Pv`, `x: 12959, y: 2500`)
  3. `Screen / HIG (iPhone 18 Pro Max) - 02 Navigation Drawer` (`V3Qxm3`, `x: 13899, y: 2500`)
  4. `Screen / HIG (iPhone 18 Pro Max) - 03 Onboarding Chat` (`bfUKh`, `x: 14839, y: 2500`)
  5. `Screen / HIG (iPhone 18 Pro Max) - 04 Onboarding - Kilometraje y Uso` (`y5T3Mq`, `x: 15779, y: 2500`)
| `$radiusFull` | `9999 px` | **Botones de acción principales (Cápsula de 44 px en M3 e HIG), chips de filtro y píldoras** |

---

## 3. Matriz Comparativa: Paradigmas Nativos M3 vs. Apple HIG

Derivada del análisis riguroso de las 10 pantallas del producto:

| Dimensión de UI | Zora Material 3 (`Android`) | Zora Apple HIG (`iOS`) |
| :--- | :--- | :--- |
| **Marcos de Pantalla** | 390 × 844 / 1840 px (`cornerRadius: 54`, `clip: true`) | 390 × 844 / 1850 px (`cornerRadius: 54`, `clip: true`) |
| **Top Shell** | `Android Status Bar` (28 dp) + `Top App Bar` (64 dp) | `Dynamic Island Header` (54 pt) + `Floating Top Bar` (48 pt) |
| **Bottom Shell** | `Docked Composer Container` (92 dp) con M3 Composer | `Frosted Composer` (68 pt) + `Home Indicator` (24 pt) |
| **Controles de Selección** | **Radio Buttons canónicos:** Anillo de 20 dp con borde `$primary` + punto central de 10 dp (activo); anillo `$outlineVariant` (inactivo) | **Leading Checkmark en lista:** Icono de checkmark de 18 pt en `$primary` a la izquierda (activo); sangría de 22 pt vacía (inactivos). **En iOS no existen radio buttons.** |
| **Botones de Acción** | Botón cápsula (`$radiusFull`, 44 dp), texto `$onPrimary` (`#0D0F12`) | Botón prominente cápsula (`$radiusFull`, 44 pt), texto `$onPrimary` (`#0D0F12`) |
| **Indicadores de Navegación** | Flecha de avance directo: `arrow-right` | Indicador de revelación nativo de iOS: `chevron-right` |
| **Superficies y Materiales** | **Tonal Elevation:** Contenedores sólidos sin opacidad (`surfaceContainer`, `high`, `highest`) | **Translucent Materials:** Cristal esmerilado translúcido (`#FFFFFF14`, `#0D0F12CC`) con *Backdrop Blur* |
| **Burbuja de Usuario** | Alineada a la derecha, fondo `$surfaceContainerHigh`, `cornerRadius: 20` | Alineada a la derecha estilo iMessage, fondo `$surfaceContainerHigh`, `cornerRadius: 18` |
| **Respuestas de Zora** | **Containerless:** Sin burbuja ni caja gris; texto e interacciones reposan directamente sobre el `$canvas` | **Containerless:** Sin burbuja contenedora; texto directo sobre el lienzo oscuro |
| **Barra de Asistente** | 6 acciones de microinteracción: `copy`, `volume-2`, `thumbs-up`, `thumbs-down`, `share-2`, `ellipsis` (32 × 32 px) | 6 acciones de microinteracción con acabado circular translúcido iOS (32 × 32 px) |

---

## 4. Inventario de Pantallas Diseñadas y Sincronizadas

1. **`Screen / M3 - 00 Splash Ignition`** (`cIS12`, `x: 51517`): Pantalla de ignición Android con botón circular reactivo.
2. **`Screen / HIG - 00 Splash Ignition`** (`r1zOk4`, `x: 51987`): Pantalla de ignición iOS con Dynamic Island y botón cápsula de ignición.
3. **`Screen / M3 - 01 Copilot Home`** (`mXIqC`, `x: 52457`): Home copiloto Android con Top App Bar, chips de sugerencias y M3 Composer.
4. **`Screen / HIG - 01 Copilot Home`** (`LAvNU`, `x: 52927`): Home copiloto iOS con Dynamic Island, Floating Nav Bar, Frosted Composer y Home Indicator.
5. **`Screen / M3 - 02 Navigation Drawer`** (`VeQvH`, `x: 53397`): Drawer de navegación lateral Android con fondo modal scrim.
6. **`Screen / HIG - 02 Navigation Drawer`** (`RktHH`, `x: 53867`): Modal Sheet de navegación iOS Inset Grouped con cierre gestual.
7. **`Screen / M3 - 03 Onboarding Chat`** (`s6jMW`, `x: 54337`): Conversación M3: motor con radio buttons, tarjeta de auto y chips de decisión rápida.
8. **`Screen / HIG - 03 Onboarding Chat`** (`mVHOd`, `x: 54807`): Conversación iOS: lista con leading checkmark, tarjeta de auto y botones cápsula con chevrons.
9. **`Screen / M3 - 04 Onboarding - Kilometraje y Uso`** (`D7vZmb`, `x: 55277`): Flujo continuado M3: odómetro digital, chips de kilometraje y hábitos con radio buttons M3.
10. **`Screen / HIG - 04 Onboarding - Kilometraje y Uso`** (`A3buen`, `x: 55747`): Flujo continuado iOS: odómetro con badge de estimado, chips de rango y hábitos con leading checkmark iOS.
