-- Migration: 006 — actions

CREATE TYPE action_type AS ENUM (
    'SET_REMINDER', 'CREATE_CALENDAR_EVENT', 'SEND_MESSAGE',
    'OPEN_LINK', 'ADD_TO_LIST', 'SEARCH', 'CUSTOM'
);

CREATE TYPE action_status AS ENUM (
    'PENDING', 'SCHEDULED', 'COMPLETED', 'CANCELLED', 'FAILED'
);

CREATE TABLE IF NOT EXISTS public.actions (
    id           TEXT PRIMARY KEY,
    user_id      UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    intent_id    TEXT NOT NULL REFERENCES public.intents(id) ON DELETE CASCADE,
    type         action_type NOT NULL,
    title        TEXT NOT NULL,
    description  TEXT,
    scheduled_at BIGINT,
    status       action_status NOT NULL DEFAULT 'PENDING',
    created_at   BIGINT NOT NULL,
    payload      JSONB NOT NULL DEFAULT '{}'
);

CREATE INDEX idx_actions_user_id ON public.actions(user_id);
CREATE INDEX idx_actions_intent_id ON public.actions(intent_id);
CREATE INDEX idx_actions_status ON public.actions(status);
CREATE INDEX idx_actions_scheduled_at ON public.actions(scheduled_at)
    WHERE scheduled_at IS NOT NULL;

ALTER TABLE public.actions ENABLE ROW LEVEL SECURITY;
CREATE POLICY "actions_owner" ON public.actions
    USING (auth.uid() = user_id)
    WITH CHECK (auth.uid() = user_id);
