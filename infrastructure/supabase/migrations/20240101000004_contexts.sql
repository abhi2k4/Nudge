-- Migration: 004 — contexts
-- Enable pgvector for semantic search.
-- Note: pgvector extension must be enabled in Supabase dashboard first.

CREATE EXTENSION IF NOT EXISTS vector;

CREATE TABLE IF NOT EXISTS public.contexts (
    id          TEXT PRIMARY KEY,
    user_id     UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    title       TEXT NOT NULL,
    description TEXT,
    created_at  BIGINT NOT NULL,
    updated_at  BIGINT NOT NULL,
    -- 384-dimensional embedding (all-MiniLM-L6-v2 compatible)
    embedding   vector(384)
);

CREATE INDEX idx_contexts_user_id ON public.contexts(user_id);
-- IVFFlat index for approximate nearest-neighbour search (tune lists as data grows)
CREATE INDEX idx_contexts_embedding ON public.contexts
    USING ivfflat (embedding vector_cosine_ops) WITH (lists = 100);

ALTER TABLE public.contexts ENABLE ROW LEVEL SECURITY;
CREATE POLICY "contexts_owner" ON public.contexts
    USING (auth.uid() = user_id)
    WITH CHECK (auth.uid() = user_id);
