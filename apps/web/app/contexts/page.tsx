import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Contexts — NUDGE",
  description: "Explore how NUDGE connects your ideas into meaningful contexts.",
};

export default function ContextsPage() {
  return (
    <div className="max-w-3xl">
      <div className="mb-8">
        <h1 className="text-4xl font-bold tracking-tight mb-2">Contexts</h1>
        <p className="text-lg" style={{ color: "var(--nudge-subtle)" }}>
          Thematic clusters of related captures and intents.
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
        <p className="font-medium mb-2">No contexts yet</p>
        <p className="text-sm max-w-xs mx-auto" style={{ color: "var(--nudge-subtle)" }}>
          Contexts are built automatically as your capture library grows.
        </p>
      </div>
    </div>
  );
}
