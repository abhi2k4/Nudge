-- Migration: 005 — relationships

CREATE TYPE relationship_type AS ENUM (
    'SEMANTIC_SIMILAR', 'REFERENCES', 'SAME_PROJECT', 'FOLLOWS_UP', 'DUPLICATE'
);

CREATE TABLE IF NOT EXISTS public.relationships (
    id          TEXT PRIMARY KEY,
    user_id     UUID NOT NULL REFERENCES public.profiles(id) ON DELETE CASCADE,
    source_id   TEXT NOT NULL,
    target_id   TEXT NOT NULL,
    type        relationship_type NOT NULL,
    confidence  REAL NOT NULL,
    created_at  BIGINT NOT NULL
);

CREATE INDEX idx_relationships_user_id ON public.relationships(user_id);
CREATE INDEX idx_relationships_source_id ON public.relationships(source_id);
CREATE INDEX idx_relationships_target_id ON public.relationships(target_id);

ALTER TABLE public.relationships ENABLE ROW LEVEL SECURITY;
CREATE POLICY "relationships_owner" ON public.relationships
    USING (auth.uid() = user_id)
    WITH CHECK (auth.uid() = user_id);
