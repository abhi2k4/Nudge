// Action, NudgeContext, Relationship, Nudge shared types

export type ActionType =
  | 'SET_REMINDER'
  | 'CREATE_CALENDAR_EVENT'
  | 'SEND_MESSAGE'
  | 'OPEN_LINK'
  | 'ADD_TO_LIST'
  | 'SEARCH'
  | 'CUSTOM';

export type ActionStatus =
  | 'PENDING'
  | 'SCHEDULED'
  | 'COMPLETED'
  | 'CANCELLED'
  | 'FAILED';

export interface Action {
  id: string;
  intentId: string;
  type: ActionType;
  title: string;
  description?: string | null;
  scheduledAt?: number | null; // epoch ms
  status: ActionStatus;
  createdAt: number;
  payload: Record<string, string>;
}

// ─────────────────────────────────────────────────────────────────────────────

export interface NudgeContext {
  id: string;
  title: string;
  description?: string | null;
  createdAt: number;
  updatedAt: number;
}

// ─────────────────────────────────────────────────────────────────────────────

export type RelationshipType =
  | 'SEMANTIC_SIMILAR'
  | 'REFERENCES'
  | 'SAME_PROJECT'
  | 'FOLLOWS_UP'
  | 'DUPLICATE';

export interface Relationship {
  id: string;
  sourceId: string;
  targetId: string;
  type: RelationshipType;
  confidence: number;
  createdAt: number;
}

// ─────────────────────────────────────────────────────────────────────────────

export type NudgeStatus =
  | 'SCHEDULED'
  | 'SHOWN'
  | 'ACKNOWLEDGED'
  | 'DISMISSED'
  | 'EXPIRED';

export interface Nudge {
  id: string;
  intentId: string;
  message: string;
  scheduledAt: number;
  shownAt?: number | null;
  status: NudgeStatus;
}
