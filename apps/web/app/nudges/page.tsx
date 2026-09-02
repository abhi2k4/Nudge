import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Nudges — NUDGE",
  description: "NUDGE surfaces what you meant to do but forgot — your personal follow-up engine.",
};

export default function NudgesPage() {
  return (
    <div className="max-w-3xl">
      <div className="mb-8">
        <h1 className="text-4xl font-bold tracking-tight mb-2">Nudges</h1>
        <p className="text-lg" style={{ color: "var(--nudge-subtle)" }}>
          Proactive reminders for forgotten or time-sensitive commitments.
        </p>
      </div>

      
      <div
        className="rounded-xl border px-6 py-12 text-center"
        style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
      >
        <div
          className="w-10 h-10 rounded-full mx-auto mb-4 flex items-center justify-center text-lg"
          style={{ background: "var(--nudge-yellow)" }}
        >
          ◎
        </div>
        <p className="font-medium mb-2">No nudges yet</p>
        <p className="text-sm max-w-xs mx-auto" style={{ color: "var(--nudge-subtle)" }}>
          NUDGE will surface forgotten intentions here as it learns your patterns.
        </p>
      </div>
    </div>
  );
}
