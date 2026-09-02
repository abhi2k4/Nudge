import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Tasks — NUDGE",
  description: "View and manage tasks that NUDGE has extracted from your captures.",
};

export default function TasksPage() {
  return (
    <div className="max-w-3xl">
      <div className="mb-8">
        <h1 className="text-4xl font-bold tracking-tight mb-2">Tasks</h1>
        <p className="text-lg" style={{ color: "var(--nudge-subtle)" }}>
          Actions derived from your captured intents.
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
        <p className="font-medium mb-2">No tasks yet</p>
        <p className="text-sm max-w-xs mx-auto" style={{ color: "var(--nudge-subtle)" }}>
          Tasks appear here when NUDGE extracts actionable intents from your captures.
        </p>
      </div>
    </div>
  );
}
