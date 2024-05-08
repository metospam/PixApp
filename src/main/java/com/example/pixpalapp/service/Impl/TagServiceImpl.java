package com.example.pixpalapp.service.Impl;

import com.example.pixpalapp.entity.Tag;
import com.example.pixpalapp.repository.TagRepository;
import com.example.pixpalapp.service.TagService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    
    TagRepository tagRepository;

    @Override
    public List<Tag> createTags(List<String> tagsNames) {
        return tagsNames.stream()
                .map(this::createOrGetTag)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public Tag createTag(String tagName){
        Tag tag = new Tag();
        tag.setName(tagName);

        return tagRepository.save(tag);
    }

    private Tag createOrGetTag(String tagName) {
        return tagRepository.findByName(tagName)
                .orElseGet(() -> createTag(tagName));
    }
}
