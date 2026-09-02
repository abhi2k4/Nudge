"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";

const navItems = [
  { href: "/", label: "Home", icon: "⌂" },
  { href: "/dashboard", label: "Dashboard", icon: "◉" },
  { href: "/captures", label: "Captures", icon: "◎" },
  { href: "/contexts", label: "Contexts", icon: "⬡" },
  { href: "/tasks", label: "Tasks", icon: "☑" },
  { href: "/nudges", label: "Nudges", icon: "◆" },
];

export function Sidebar() {
  const pathname = usePathname();

  return (
    <aside
      className="fixed top-0 left-0 h-full w-64 flex flex-col"
      style={{ background: "var(--nudge-charcoal)" }}
    >
      {/* Logo */}
      <div className="px-6 py-8 border-b" style={{ borderColor: "#2C2C2C" }}>
        <div className="flex items-center gap-2">
          <span
            className="text-sm font-bold px-2 py-1 rounded"
            style={{ background: "var(--nudge-yellow)", color: "var(--nudge-charcoal)" }}
          >
            N
          </span>
          <span className="text-white font-bold text-lg tracking-tight">NUDGE</span>
        </div>
        <p className="text-xs mt-2" style={{ color: "var(--nudge-subtle)" }}>
          Your AI productivity companion
        </p>
      </div>

      {/* Navigation */}
      <nav className="flex-1 px-3 py-4 space-y-1">
        {navItems.map((item) => {
          const isActive = pathname === item.href;
          return (
            <Link
              key={item.href}
              href={item.href}
              className={`flex items-center gap-3 px-3 py-2.5 rounded-lg text-sm font-medium transition-all ${
                isActive
                  ? "text-charcoal"
                  : "text-gray-400 hover:text-white hover:bg-white/5"
              }`}
              style={
                isActive
                  ? { background: "var(--nudge-yellow)", color: "var(--nudge-charcoal)" }
                  : {}
              }
            >
              <span className="text-base w-5 text-center">{item.icon}</span>
              {item.label}
            </Link>
          );
        })}
      </nav>

      {/* Status pill */}
      <div className="px-6 py-5 border-t" style={{ borderColor: "#2C2C2C" }}>
        <div className="flex items-center gap-2">
          <span className="w-2 h-2 rounded-full bg-green-400 inline-block" />
          <span className="text-xs" style={{ color: "var(--nudge-subtle)" }}>
            Local-only mode
          </span>
        </div>
        <p className="text-xs mt-1" style={{ color: "#444" }}>
          No data leaves your device
        </p>
      </div>
    </aside>
  );
}
