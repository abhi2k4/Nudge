# AI Runtime Integration

NUDGE uses an abstraction layer for all AI capabilities, ensuring the product logic remains decoupled from specific AI implementations.

## Interface Contracts

All AI interactions flow through core interfaces:

- `OnDeviceLanguageModel`: Core LLM inference.
- `IntentExtractor`: Parses structured JSON intents from LLM output.
- `ContextResolver`: Uses embeddings to find semantic relationships.
- `SpeechRecognizer`: On-device ASR.
- `EmbeddingProvider`: Text-to-vector embedding.

## Runtime Capabilities Model

The `LocalAIManager` probes the device at startup and populates a `RuntimeCapabilities` model:

- `supportsCpu`
- `supportsGpu`
- `supportsNpu`
- `maxContextLength`
- `availableMemoryMb`

## Backend Selection Logic

The `RuntimeSelector` chooses the best backend based on capabilities. 

Priority:
1. **NPU Backend** (Not implemented yet - requires QNN / NNAPI)
2. **GPU Backend** (Not implemented yet)
3. **CPU Backend** (`llama.cpp` stub - implementation pending JNI bindings)
4. **Mock Backend** (Currently active for development)

## llama.cpp Integration Path

1. Submodule `llama.cpp` under `ai/runtime/src/main/cpp/`.
2. Write JNI bindings in `native/llama_bridge.cpp`.
3. Implement `CpuLanguageModel` to call the JNI methods.
