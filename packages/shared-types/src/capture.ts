// ─────────────────────────────────────────────────────────────────────────────
// Capture — the primary unit of information in NUDGE
// ─────────────────────────────────────────────────────────────────────────────

export type CaptureType =
  | 'VOICE'
  | 'SCREENSHOT'
  | 'PHOTO'
  | 'TEXT'
  | 'LINK'
  | 'DOCUMENT';

export type ProcessingStatus =
  | 'PENDING'
  | 'QUEUED'
  | 'PREPROCESSING'
  | 'EXTRACTING_INTENT'
  | 'RESOLVING_CONTEXT'
  | 'COMPLETE'
  | 'FAILED';

export type SyncStatus =
  | 'LOCAL_ONLY'
  | 'SYNC_PENDING'
  | 'SYNCED'
  | 'CONFLICT'
  | 'FAILED';

export interface Capture {
  id: string;
  type: CaptureType;
  createdAt: number;          // epoch ms
  source: string;
  contentUri?: string | null;
  text?: string | null;
  metadata: Record<string, string>;
  processingStatus: ProcessingStatus;
  syncStatus: SyncStatus;
}
