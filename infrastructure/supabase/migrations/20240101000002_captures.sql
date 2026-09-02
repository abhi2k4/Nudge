-- Migration: 002 — captures

CREATE TYPE capture_type AS ENUM (
    'VOICE', 'SCREENSHOT', 'PHOTO', 'TEXT', 'LINK', 'DOCUMENT'
);

CREATE TYPE processing_status AS ENUM (
    'PENDING', 'QUEUED', 'PREPROCESSING', 'EXTRACTING_INTENT',
    'RESOLVING_CONTEXT', 'COMPLETE', 'FAILED'
);

CREATE TYPE sync_status AS ENUM (
    'LOCAL_ONLY', 'SYNC_PENDING', 'SYNCED', 'CONFLICT', 'FAILED'
);

CREATE TABLE IF NOT EXISTS public.captures (
    id                TEXT PRIMARY KEY,
    user_id           UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    type              capture_type NOT NULL,
    created_at        BIGINT NOT NULL,                   -- epoch ms (matches Android)
    source            TEXT NOT NULL,
    content_uri       TEXT,
    text              TEXT,
    metadata          JSONB NOT NULL DEFAULT '{}',
    processing_status processing_status NOT NULL DEFAULT 'PENDING',
    sync_status       sync_status NOT NULL DEFAULT 'SYNC_PENDING',
    synced_at         TIMESTAMPTZ
);

CREATE INDEX idx_captures_user_id ON public.captures(user_id);
CREATE INDEX idx_captures_created_at ON public.captures(created_at DESC);
CREATE INDEX idx_captures_processing_status ON public.captures(processing_status);

ALTER TABLE public.captures ENABLE ROW LEVEL SECURITY;
CREATE POLICY "captures_owner" ON public.captures
    USING (auth.uid() = user_id)
    WITH CHECK (auth.uid() = user_id);
