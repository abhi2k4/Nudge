// ─────────────────────────────────────────────────────────────────────────────
// Intent — extracted meaning behind a capture
// ─────────────────────────────────────────────────────────────────────────────

export type IntentType =
  | 'REMINDER'
  | 'TASK'
  | 'NOTE'
  | 'RESEARCH'
  | 'BOOKING'
  | 'PURCHASE'
  | 'MEETING'
  | 'UNKNOWN';

export type IntentStatus =
  | 'NEW'
  | 'ACKNOWLEDGED'
  | 'ACTIONED'
  | 'DISMISSED'
  | 'COMPLETED';

export interface Intent {
  id: string;
  captureId: string;
  type: IntentType;
  title: string;
  description?: string | null;
  project?: string | null;
  deadline?: string | null;
  confidence: number;         // 0.0 – 1.0
  status: IntentStatus;
  createdAt: number;          // epoch ms
}
