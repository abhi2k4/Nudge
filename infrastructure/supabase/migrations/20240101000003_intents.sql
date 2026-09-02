-- Migration: 003 — intents

CREATE TYPE intent_type AS ENUM (
    'REMINDER', 'TASK', 'NOTE', 'RESEARCH',
    'BOOKING', 'PURCHASE', 'MEETING', 'UNKNOWN'
);

CREATE TYPE intent_status AS ENUM (
    'NEW', 'ACKNOWLEDGED', 'ACTIONED', 'DISMISSED', 'COMPLETED'
);

CREATE TABLE IF NOT EXISTS public.intents (
    id          TEXT PRIMARY KEY,
    user_id     UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    capture_id  TEXT NOT NULL REFERENCES public.captures(id) ON DELETE CASCADE,
    type        intent_type NOT NULL,
    title       TEXT NOT NULL,
    description TEXT,
    project     TEXT,
    deadline    TEXT,
    confidence  REAL NOT NULL,
    status      intent_status NOT NULL DEFAULT 'NEW',
    created_at  BIGINT NOT NULL
);

CREATE INDEX idx_intents_user_id ON public.intents(user_id);
CREATE INDEX idx_intents_capture_id ON public.intents(capture_id);
CREATE INDEX idx_intents_status ON public.intents(status);

ALTER TABLE public.intents ENABLE ROW LEVEL SECURITY;
CREATE POLICY "intents_owner" ON public.intents
    USING (auth.uid() = user_id)
    WITH CHECK (auth.uid() = user_id);
