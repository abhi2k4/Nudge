-- Migration: 008 — sync metadata
-- Tracks per-device sync checkpoints to enable incremental sync.

CREATE TABLE IF NOT EXISTS public.sync_metadata (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    device_id       TEXT NOT NULL,
    entity_type     TEXT NOT NULL,        -- 'capture' | 'intent' | 'action' | etc.
    last_synced_at  BIGINT NOT NULL,      -- epoch ms of last successful sync
    sync_cursor     TEXT,                 -- opaque cursor for incremental sync
    UNIQUE (user_id, device_id, entity_type)
);

CREATE INDEX idx_sync_metadata_user_device ON public.sync_metadata(user_id, device_id);

ALTER TABLE public.sync_metadata ENABLE ROW LEVEL SECURITY;
CREATE POLICY "sync_metadata_owner" ON public.sync_metadata
    USING (auth.uid() = user_id)
    WITH CHECK (auth.uid() = user_id);
