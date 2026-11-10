import json, math, struct, wave, sys, os, tempfile, filecmp

SR = 22050
# Usage:
#   python3 generate_sounds.py reactions.json OUT_DIR           # write WAVs
#   python3 generate_sounds.py reactions.json OUT_DIR --check   # fail if OUT_DIR is stale


def validate(spec):
    attack = spec["soundRules"]["attackS"]
    for name, tones in spec["sounds"].items():
        for t in tones:
            if t["gain"] <= 0:
                raise ValueError(f"{name}: gain must be > 0, got {t['gain']}")
            if t["durationS"] <= attack:
                raise ValueError(f"{name}: durationS must be > attackS ({attack}), got {t['durationS']}")
    return attack


def render(spec, out):
    attack = validate(spec)
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
                if x < attack:
                    env = gain * (x / attack)
                else:
                    env = gain * ((0.0001 / gain) ** ((x - attack) / (dur - attack)))
                buf[start + i] += env * math.sin(phase)
        path = os.path.join(out, f"{name}.wav")
        with wave.open(path, "wb") as w:
            w.setnchannels(1); w.setsampwidth(2); w.setframerate(SR)
            w.writeframes(b"".join(struct.pack("<h", int(max(-1.0, min(1.0, v)) * 32767)) for v in buf))
        print(f"{name}.wav  {n / SR:.3f}s  bytes={os.path.getsize(path)}")


def main():
    src, out = sys.argv[1], sys.argv[2]
    spec = json.load(open(src))
    if "--check" in sys.argv[3:]:
        with tempfile.TemporaryDirectory() as tmp:
            render(spec, tmp)
            stale = [f for f in sorted(os.listdir(tmp))
                     if not os.path.exists(os.path.join(out, f))
                     or not filecmp.cmp(os.path.join(tmp, f), os.path.join(out, f), shallow=False)]
        if stale:
            print("stale WAVs, re-run generate_sounds.py:", ", ".join(stale))
            sys.exit(1)
        print("WAVs match reactions.json")
    else:
        render(spec, out)


if __name__ == "__main__":
    main()
