import Link from "next/link";
import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "NUDGE — AI Productivity Companion",
  description: "Capture, understand, remember, and act — with on-device AI.",
};

const coreLoop = [
  { step: "Capture", desc: "Voice, text, screenshot, link, photo" },
  { step: "Understand", desc: "On-device AI extracts intent" },
  { step: "Remember", desc: "Contextual storage, not just search" },
  { step: "Connect", desc: "Relates ideas across time" },
  { step: "Act", desc: "Turns intent into scheduled actions" },
  { step: "Follow Up", desc: "Surfaces forgotten commitments" },
];

const navCards = [
  { href: "/dashboard", label: "Dashboard", desc: "Overview of your capture activity" },
  { href: "/captures", label: "Captures", desc: "Browse all your captured moments" },
  { href: "/contexts", label: "Contexts", desc: "Explore thematic clusters" },
  { href: "/tasks", label: "Tasks", desc: "Actions derived from your intents" },
  { href: "/nudges", label: "Nudges", desc: "Proactive follow-ups" },
];

export default function HomePage() {
  return (
    <div className="max-w-3xl">
      {/* Hero */}
      <div className="mb-12">
        <div
          className="inline-block text-xs font-semibold px-3 py-1 rounded-full mb-4"
          style={{ background: "var(--nudge-yellow)", color: "var(--nudge-charcoal)" }}
        >
          Hackathon Foundation v0.1
        </div>
        <h1 className="text-5xl font-bold tracking-tight leading-tight mb-4">
          Don't let good<br />ideas disappear.
        </h1>
        <p className="text-lg" style={{ color: "var(--nudge-subtle)" }}>
          NUDGE captures your ideas, extracts intent, and resurfaces what matters —
          entirely on-device. Your phone is the capture surface. Your laptop is where action happens.
        </p>
      </div>

      {/* Core loop */}
      <div className="mb-12">
        <h2 className="text-sm font-semibold uppercase tracking-widest mb-5" style={{ color: "var(--nudge-subtle)" }}>
          Core Loop
        </h2>
        <div className="flex flex-col gap-2">
          {coreLoop.map((item, i) => (
            <div
              key={item.step}
              className="flex items-center gap-4 px-4 py-3 rounded-xl border"
              style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
            >
              <span
                className="w-7 h-7 rounded-full flex items-center justify-center text-xs font-bold flex-shrink-0"
                style={{ background: "var(--nudge-yellow)", color: "var(--nudge-charcoal)" }}
              >
                {i + 1}
              </span>
              <div>
                <span className="font-semibold">{item.step}</span>
                <span className="text-sm ml-2" style={{ color: "var(--nudge-subtle)" }}>{item.desc}</span>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Quick nav */}
      <div>
        <h2 className="text-sm font-semibold uppercase tracking-widest mb-5" style={{ color: "var(--nudge-subtle)" }}>
          Explore
        </h2>
        <div className="grid grid-cols-2 gap-3">
          {navCards.map((card) => (
            <Link
              key={card.href}
              href={card.href}
              className="block px-5 py-4 rounded-xl border transition-all hover:shadow-sm hover:-translate-y-0.5"
              style={{ background: "var(--nudge-surface)", borderColor: "var(--nudge-border)" }}
            >
              <div className="font-semibold mb-1">{card.label}</div>
              <div className="text-sm" style={{ color: "var(--nudge-subtle)" }}>{card.desc}</div>
            </Link>
          ))}
        </div>
      </div>
    </div>
  );
}
