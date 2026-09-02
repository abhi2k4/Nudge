import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Dashboard — NUDGE",
  description: "Overview of your recent captures, active intents, and upcoming tasks.",
};

export default function DashboardPage() {
  return (
    <div className="max-w-3xl">
      <div className="mb-8">
        <h1 className="text-4xl font-bold tracking-tight mb-2">Dashboard</h1>
        <p className="text-lg" style={{ color: "var(--nudge-subtle)" }}>
          Your NUDGE activity at a glance.
        </p>
      </div>

      
      <div className="grid grid-cols-2 gap-4 mb-8">
                <div
          className="rounded-xl border px-5 py-4"
          style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
        >
          <div className="text-2xl font-bold mb-1">—</div>
          <div className="text-sm font-medium mb-0.5">Captures Today</div>
          <div className="text-xs" style={{ color: "var(--nudge-subtle)" }}>New captures in the last 24h</div>
        </div>
        <div
          className="rounded-xl border px-5 py-4"
          style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
        >
          <div className="text-2xl font-bold mb-1">—</div>
          <div className="text-sm font-medium mb-0.5">Active Intents</div>
          <div className="text-xs" style={{ color: "var(--nudge-subtle)" }}>Intents awaiting action</div>
        </div>
        <div
          className="rounded-xl border px-5 py-4"
          style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
        >
          <div className="text-2xl font-bold mb-1">—</div>
          <div className="text-sm font-medium mb-0.5">Tasks Due</div>
          <div className="text-xs" style={{ color: "var(--nudge-subtle)" }}>Actions due this week</div>
        </div>
        <div
          className="rounded-xl border px-5 py-4"
          style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
        >
          <div className="text-2xl font-bold mb-1">—</div>
          <div className="text-sm font-medium mb-0.5">Nudges Pending</div>
          <div className="text-xs" style={{ color: "var(--nudge-subtle)" }}>Proactive reminders queued</div>
        </div>
      </div>

      <div
        className="rounded-xl border px-6 py-8 text-center"
        style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
      >
        <p className="font-medium mb-1">Coming soon</p>
        <p className="text-sm" style={{ color: "var(--nudge-subtle)" }}>
          Connect your Android device and start capturing to populate this dashboard.
        </p>
      </div>
    </div>
  );
}
