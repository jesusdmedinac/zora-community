# Zora · El personaje

Zora es la voz del copiloto de Zora Community y, a la vez, su cara visible: **dos arcos de luz inspirados en los medidores del tablero**, que expresan emoción cambiando de forma.

> **Idea central:** Zora se enciende cuando tu auto la necesita. Como un testigo del tablero, está tranquila cuando todo va bien y solo se activa cuando hay algo que vale la pena.

## Archivos

| Archivo | Contenido |
| :--- | :--- |
| [`zora.svg`](zora.svg) | Maestro en reposo (R = 100), fondo transparente |
| [`zora-24.svg`](zora-24.svg) | Versión simplificada para tamaños menores de 32 px |
| [`model-sheet.svg`](model-sheet.svg) | Hoja de modelo: construcción, vocabulario de estados, escalas y paleta |
| [`states.json`](states.json) | Geometría, colores, parpadeo, los 7 estados y la coreografía de *pensando* |
| [`reactions.json`](reactions.json) | Aparecer, tocar, celebrar, recordar y despedirse; sonidos y ritmo según la latencia |
| [`voice-guide.md`](voice-guide.md) | Cómo habla, frases (dice / nunca dice) y paleta de sonidos |
| [`prototype.html`](prototype.html) | Prototipo interactivo autónomo (ábrelo en el navegador) |

## Decisiones de diseño

- **Rol:** Zora es la misma voz del agente. Habla en el chat, piensa mientras genera respuestas y acompaña en el Modo Voz.
- **Tono:** cálido intermedio. Una amiga que sabe de mecánica: amigable y creíble.
- **Comunicación:** expresiones visuales y tonos cortos al reaccionar. En Modo Voz habla con la voz TTS existente.
- **Solo tema oscuro.** Zora se diseña sobre Obsidian `#0D0F12`; el tema claro está fuera de alcance por ahora.
- **Nunca castiga ni alarma:** no usa rojo, no culpa por servicios atrasados, no se pone triste ni "muere".

## Anatomía

- **Sin cuerpo.** Solo dos arcos de luz flotando sobre el fondo oscuro, sin visor ni contorno.
- **Ojos de tacómetro.** Arcos de 270° con la abertura de 90° abajo, trazo de 0.35R con punta redonda y 4R entre centros.
- **Toda la emoción está en el arco:** barrido, dirección, grosor, mirada (desplazamiento) y opacidad. Sin boca, pupilas, rellenos ni núcleos.
- **Mismo tamaño siempre.** La asimetría solo cambia la longitud del arco, nunca el radio.
- **Aguja de nivel.** Solo en Modo Voz: nace del centro del ojo y recorre el arco según la amplitud de la voz.
- **Referencia:** toma el *principio* de los ojos de EVE (WALL·E), ojos de luz sin pupila que cambian de forma, y se diferencia en forma (arcos de medidor), soporte (sin cuerpo) y color (paleta del tablero), con un rasgo propio: el barrido de encendido.

## Estados

| Estado | Disparador | Expresión clave |
| :--- | :--- | :--- |
| En reposo | Sin servicios ni avisos pendientes | Arcos de 270° en cian, respiración lenta, parpadeo |
| Pensando | El agente genera una respuesta | Violeta; los arcos se acortan de forma asimétrica en una coreografía de 6.8 s |
| Contenta | Servicio o check-in completado | Medialunas de 150°, ojos levantados |
| Curiosa | Zora guarda algo en su memoria | Mismo radio, arcos de 320° y 200° que se intercambian una vez |
| Atención | Servicio próximo o refacción por revisar | Anillos completos en ámbar con dos pulsos; aviso, no alarma |
| Modo Voz | Modo Voz en cabina | Ojos más chicos al 75 %; solo se mueve la aguja de nivel |
| Dormida | Auto estacionado sin novedades por días | Arcos de 120° hacia abajo al 45 %; descanso, no tristeza |

## Movimiento

- **Parpadeo:** intermitencia de opacidad en 0.16 s que llega a 0 %, como un testigo que titila. Los arcos nunca se aplastan ni rotan.
- **Firma:** el doble parpadeo, con dos apagones separados 0.26 s.
- **Barrido de encendido:** al aparecer, los arcos crecen hasta pasarse un 12 % y se asientan, como las agujas al girar la llave.
- **Transiciones:** interpolación exponencial de todos los parámetros (`min(1, dt · 6)`), color incluido.
- **Ritmo según la latencia del chat:** consulta `reactions.json` → `latency`.

## Reglas de uso

| Siempre | Nunca |
| :--- | :--- |
| Sobre fondo oscuro | Sobre fondos claros, fotos o degradados intensos |
| Arcos con punta redonda y trazo proporcional a R | Rellenos, núcleos o círculos dentro del arco |
| Ambos ojos con el mismo radio | Ojos de distinto tamaño |
| Colores de la paleta: cian `#4FE3F2`, violeta `#8A4DFF`, ámbar `#F5A623` | Rojo de alarma, verde u otros colores |
| Parpadeo como intermitencia de opacidad | Aplastar, rotar o deformar los arcos para parpadear |
| Versión simplificada por debajo de 32 px | Aguja o respiración en tamaños chicos |
| Animación mínima en Modo Voz | Parpadear o cambiar de estado mientras se conduce |
| Despedirse recogiendo los arcos | Romper, explotar o desaparecer de golpe |

## En `zora-community.pen`

- **Lámina `Zora Character - Component Library`** (junto a las librerías M3 y HIG). Incluye los componentes, los fotogramas clave de *aparecer*, *parpadeo* y *pensando*, y las reglas.
- **Componentes:** `Character / Zora / Large / <Estado>` (R = 20, los 7 estados), `Medium / {Idle, Thinking, Attention}` (R = 8) y `Chip / {Idle, Thinking, Attention}` (versión simplificada para menos de 32 px).
- **Tokens:** `$zoraEyeIdle`, `$zoraEyeThinking` y `$zoraEyeAttention`.
- **Pantallas:** Zora en reposo reemplaza al glifo en la bienvenida de Copilot Home (01) y en su vista previa dentro del drawer (02), en M3, HIG y HIG Pro Max. Zora en Modo Voz encabeza el escenario de la pantalla 10 en las tres variantes.
- **Splash (00):** conserva el glifo de marca; el personaje aparece después, con su barrido de encendido.
- **Instancias que no escalan:** las instancias de pen no escalan a sus hijos, por eso cada tamaño es un componente propio. Para otro tamaño, crea una variante nueva con las medidas en R.

## Notas de implementación

- **Fuente de verdad:** la implementación lee los números de `states.json` y `reactions.json`. `prototype.html` lleva una copia porque un HTML abierto desde disco no puede usar `fetch` sobre archivos locales; si cambias un JSON, actualiza también el prototipo.
- **Compose Multiplatform:** cada ojo es un `drawArc` en un `Canvas` con `useCenter = false`, `style = Stroke(width, cap = StrokeCap.Round)`, `startAngle = direction − sweep/2` y `sweepAngle = sweep`. La convención angular de Compose (0° a la derecha, sentido horario) coincide con la de los JSON.
- **Estado:** un `ZoraCharacterState` en el `ViewModel` (Orbit MVI) emite el estado destino; el composable interpola con `animateFloatAsState` o con un bucle de `withFrameNanos`.
- **Modo Voz:** la aguja lee la amplitud RMS del audio TTS por cuadro (Android `Visualizer` o el envolvente del `AudioTrack`; en iOS, un *tap* de `AVAudioEngine`).
- **Sonidos:** se pueden sintetizar en tiempo real o exportar como archivos cortos a partir de `reactions.json`; respeta el modo silencio y el interruptor de la app.
