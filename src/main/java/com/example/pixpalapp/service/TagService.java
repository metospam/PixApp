package com.example.pixpalapp.service;

import com.example.pixpalapp.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> createTags(List<String> tagsNames);
    Tag createTag(String tagName);
}
