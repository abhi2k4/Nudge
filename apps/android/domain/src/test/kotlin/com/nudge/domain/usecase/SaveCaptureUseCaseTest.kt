package com.nudge.domain.usecase

import com.nudge.core.common.NudgeResult
import com.nudge.core.model.Capture
import com.nudge.core.model.CaptureType
import com.nudge.domain.repository.CaptureRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SaveCaptureUseCaseTest {

    private lateinit var captureRepository: CaptureRepository
    private lateinit var useCase: SaveCaptureUseCase

    @Before
    fun setUp() {
        captureRepository = mockk()
        useCase = SaveCaptureUseCase(captureRepository)
    }

    @Test
    fun `invoke calls repository save and returns success`() = runTest {
        val capture = makeCapture()
        coEvery { captureRepository.save(capture) } returns NudgeResult.Success(capture)

        val result = useCase(capture)

        assertTrue(result.isSuccess)
        coVerify(exactly = 1) { captureRepository.save(capture) }
    }

    @Test
    fun `invoke returns repository error on failure`() = runTest {
        val capture = makeCapture()
        coEvery { captureRepository.save(capture) } returns NudgeResult.Error(
            message = "Disk full",
            code = com.nudge.core.common.ErrorCode.IO_ERROR,
        )

        val result = useCase(capture)

        assertTrue(result is NudgeResult.Error)
        assertEquals("Disk full", (result as NudgeResult.Error).message)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `invoke throws for blank capture id`() = runTest {
        val capture = makeCapture(id = "  ")
        useCase(capture)
    }

    private fun makeCapture(id: String = "valid-id-001") = Capture(
        id = id,
        type = CaptureType.TEXT,
        createdAt = System.currentTimeMillis(),
        source = "test",
        text = "Buy train tickets for Goa",
    )
}
