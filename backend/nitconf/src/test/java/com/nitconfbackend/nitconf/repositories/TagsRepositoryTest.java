package com.nitconfbackend.nitconf.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.nitconfbackend.nitconf.models.Tag;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class TagsRepositoryTest {

    @Mock
    private TagsRepository tagsRepositoryMock;

    @Test
    public void testFindByTitle() {

        Tag sampleTag = new Tag();
        sampleTag.setTitle("AI");

        when(tagsRepositoryMock.findByTitle("AI")).thenReturn(Optional.of(sampleTag));

        Optional<Tag> foundTag = tagsRepositoryMock.findByTitle("AI");

        verify(tagsRepositoryMock, times(1)).findByTitle("AI");

        assertEquals("AI", foundTag.get().getTitle());
    }
}
