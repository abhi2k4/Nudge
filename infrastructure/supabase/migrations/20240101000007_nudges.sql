-- Migration: 007 — nudges

CREATE TYPE nudge_status AS ENUM (
    'SCHEDULED', 'SHOWN', 'ACKNOWLEDGED', 'DISMISSED', 'EXPIRED'
);

CREATE TABLE IF NOT EXISTS public.nudges (
    id           TEXT PRIMARY KEY,
    user_id      UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    intent_id    TEXT NOT NULL REFERENCES public.intents(id) ON DELETE CASCADE,
    message      TEXT NOT NULL,
    scheduled_at BIGINT NOT NULL,
    shown_at     BIGINT,
    status       nudge_status NOT NULL DEFAULT 'SCHEDULED'
);

CREATE INDEX idx_nudges_user_id ON public.nudges(user_id);
CREATE INDEX idx_nudges_intent_id ON public.nudges(intent_id);
CREATE INDEX idx_nudges_scheduled_at ON public.nudges(scheduled_at);

ALTER TABLE public.nudges ENABLE ROW LEVEL SECURITY;
CREATE POLICY "nudges_owner" ON public.nudges
    USING (auth.uid() = user_id)
    WITH CHECK (auth.uid() = user_id);
