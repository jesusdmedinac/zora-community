import json, math, struct, wave, sys, os
SR = 22050
src, out = sys.argv[1], sys.argv[2]
spec = json.load(open(src))
ATTACK = spec["soundRules"]["attackS"]
for name, tones in spec["sounds"].items():
    total = max(t["delayS"] + t["durationS"] for t in tones)
    n = int(math.ceil(total * SR)) + 1
    buf = [0.0] * n
    for t in tones:
        start = int(round(t["delayS"] * SR))
        dur = t["durationS"]
        ln = int(round(dur * SR))
        f0, f1, gain = t["fromHz"], t["toHz"], t["gain"]
        phase = 0.0
        for i in range(ln):
            x = i / SR
            # exponential frequency sweep from f0 to f1 over the duration
            f = f0 * (f1 / f0) ** (x / dur) if f0 != f1 else f0
            phase += 2 * math.pi * f / SR
            # amplitude: linear attack, then exponential decay to ~0 at the end
            if x < ATTACK:
                env = gain * (x / ATTACK)
            else:
                env = gain * ((0.0001 / gain) ** ((x - ATTACK) / (dur - ATTACK)))
            buf[start + i] += env * math.sin(phase)
    peak = max(1e-9, max(abs(v) for v in buf))
    scale = 1.0  # keep the gains from reactions.json
    path = os.path.join(out, f"{name}.wav")
    with wave.open(path, "wb") as w:
        w.setnchannels(1); w.setsampwidth(2); w.setframerate(SR)
        frames = b"".join(struct.pack("<h", int(max(-1, min(1, v * scale)) * 32767)) for v in buf)
        w.writeframes(frames)
    print(f"{name}.wav  {n/SR:.3f}s  peak_in={peak:.4f}  bytes={os.path.getsize(path)}")
