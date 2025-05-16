package com.blogapp.blog.util;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MarkdownProcessor {

    private final Parser parser;
    private final HtmlRenderer renderer;

    public MarkdownProcessor() {
        List<org.commonmark.Extension> extensions = Arrays.asList(
            TablesExtension.create(),
            HeadingAnchorExtension.create()
        );
        
        parser = Parser.builder()
                .extensions(extensions)
                .build();
                
        renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .softbreak("<br />")
                .build();
    }

    /**
     * Converts Markdown text to HTML
     * 
     * @param markdown The Markdown text to convert
     * @return The HTML representation of the Markdown text
     */
    public String convertMarkdownToHtml(String markdown) {
        if (markdown == null) {
            return "";
        }
        
        markdown = markdown.replaceAll("(?<!\n)\n(?!\n)", "\n\n");
        
        Node document = parser.parse(markdown);
        return renderer.render(document);
    }
} 