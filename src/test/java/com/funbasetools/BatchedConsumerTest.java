package com.funbasetools;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.funbasetools.collections.Streams;
import java.util.List;
import org.junit.Test;

public class BatchedConsumerTest {

    @Test
    public void testBatchingAccordingToBatchSize() {
        // given
        final int batchSize = 5;

        @SuppressWarnings("unchecked")
        final Consumer<List<Object>> downStreamConsumer = mock(Consumer.class);
        doAnswer(invocationOnMock -> {
            final List<Integer> arg = invocationOnMock.getArgument(0);

            assertEquals(batchSize, arg.size());
            assertTrue(Streams.of(arg).corresponds(Streams.range(1, 5)));

            return null;
        }).when(downStreamConsumer).accept(anyList());

        final BatchedConsumer<Object> batchedConsumer = BatchedConsumer
            .builder()
            .downStreamConsumer(downStreamConsumer)
            .batchSize(batchSize)
            .build();

        // when
        batchedConsumer.accept(1);
        batchedConsumer.accept(2);
        batchedConsumer.accept(3);
        batchedConsumer.accept(4);

        // then
        verify(downStreamConsumer, never()).accept(any());

        // and when
        batchedConsumer.accept(5);

        // then
        verify(downStreamConsumer, times(1)).accept(any());
    }
}
